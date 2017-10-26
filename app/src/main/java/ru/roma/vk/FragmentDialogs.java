package ru.roma.vk;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ilan on 17.09.2017.
 */

public class FragmentDialogs extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listDialog;
    private DialogsAdapter dialogsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Pagination p;
    private View v;
    private static final String KEY = "key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogsAdapter = new DialogsAdapter();
        p = Pagination.getInstans();
        setRetainInstance(true);
        Log.d("my log", "onCreate in the Dialog");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.d("my log", "onCreateView in the Dialog");

        if (v == null)
        v = inflater.inflate(R.layout.fragmentdialogs, null);

        EditText search = v.findViewById(R.id.search_dialog);
        listDialog = v.findViewById(R.id.list_dialogs);

        swipeRefreshLayout = v.findViewById(R.id.swipe_dialog);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.blue));

        listDialog.setAdapter(dialogsAdapter);
        if (p.hasCash()){
            dialogsAdapter.setDialog(p.getCash());
        }else {
            DialogAsyn dialogAsyn = new DialogAsyn();
            dialogAsyn.execute();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dialogsAdapter.getFilter().filter(editable.toString());
            }
        });

        listDialog.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int lastPosition = listDialog.getLastVisiblePosition() +1;

                Log.d("my log", "lastposition " + (lastPosition ));
                Log.d("my log", "count" + Dialogs.getCount());
                if (lastPosition < Dialogs.getCount() && lastPosition == dialogsAdapter.getCount()){
                    DialogAsyn dialogAsyn = new DialogAsyn();
                    dialogAsyn.execute();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        return v;
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        p.deletCash();
        DialogAsyn dialogAsyn = new DialogAsyn();
        dialogAsyn.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        p.deletCash();
    }

    public class DialogAsyn extends AsyncTask<Void, Void, ArrayList<Dialogs>> {

        @Override
        protected ArrayList<Dialogs> doInBackground(Void... voids) {
            return p.next();
        }

        @Override
        protected void onPostExecute(ArrayList<Dialogs> dialogs) {
            super.onPostExecute(dialogs);

            dialogsAdapter.setDialog(dialogs);

            Log.d("my log", "my dialogs: " + String.valueOf(dialogs.size()));

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
