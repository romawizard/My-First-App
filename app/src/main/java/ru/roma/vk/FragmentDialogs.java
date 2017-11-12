package ru.roma.vk;

import android.app.Fragment;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilan on 17.09.2017.
 */

public class FragmentDialogs extends Fragment implements SwipeRefreshLayout.OnRefreshListener,Paginable {

    private ListView listDialog;
    private  DialogsAdapter dialogsAdapter;
    private  SwipeRefreshLayout swipeRefreshLayout;
    private  Pagination p;
    private boolean loadind = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogsAdapter = new DialogsAdapter();
        p = new Pagination<Dialogs>(20,this);
        setRetainInstance(true);
        Log.d("my log", "onCreate in the Dialog");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        Log.d("my log", "onCreateView in the Dialog");

        View v = inflater.inflate(R.layout.fragmentdialogs, null);

        EditText search = v.findViewById(R.id.search_dialog);
        listDialog = v.findViewById(R.id.list_dialogs);

        swipeRefreshLayout = v.findViewById(R.id.swipe_dialog);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.blue));

        listDialog.setAdapter(dialogsAdapter);
        if (p.hasCash()){
            dialogsAdapter.setDialog((ArrayList<Dialogs>) p.getCash());
        }else {
            LoadDialogAsyn dialogAsyn = new LoadDialogAsyn();
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
                int lastPosition = listDialog.getLastVisiblePosition() + 1 ;

                if (lastPosition < Dialogs.getCount() && lastPosition == dialogsAdapter.getCount() && !isLoadind()){
                   doLoading();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialogs d = (Dialogs) dialogsAdapter.getItem(i);
                String name = d.getFirst_name() +" " + d.getLast_name();
                String URL = d.getURLPhoto();
                int online = d.getOnLine();

                Intent intent = new Intent(container.getContext(),MessageActivity.class);
                intent.putExtra(Keys.KEY_ID,l);
                intent.putExtra(Keys.KEY_NAME, name);
                intent.putExtra(Keys.KEY_PHOTO,URL);
                intent.putExtra(Keys.KEY_ONLINE,online);

                Log.d("my log" , "ID adapter " + i +" "+ l);
                startActivity(intent);
            }
        });
        return v;
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        p.deletCash();
        doLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        p.deletCash();
    }

    @Override
    public List getData(int offset) {
        return ApiVK.getInstance().getAllDialogs(offset);
    }

    @Override
    public int getCount() {
        return Dialogs.getCount();
    }

    private boolean isLoadind(){
        return loadind;
    }

    private void  doLoading(){
        LoadDialogAsyn dialogAsyn = new LoadDialogAsyn();
        dialogAsyn.execute();
    }


    public  class LoadDialogAsyn extends AsyncTask<Void, Void, List<Dialogs>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadind = true;

        }

        @Override
        protected ArrayList<Dialogs> doInBackground(Void... voids) {
            return (ArrayList<Dialogs>) p.next();
        }

        @Override
        protected void onPostExecute(List<Dialogs> dialogs) {
            super.onPostExecute(dialogs);

            dialogsAdapter.setDialog((ArrayList<Dialogs>) dialogs);

            Log.d("my log", "my dialogs: " + String.valueOf(dialogs.size()));

            swipeRefreshLayout.setRefreshing(false);
            loadind = false;
        }
    }
}
