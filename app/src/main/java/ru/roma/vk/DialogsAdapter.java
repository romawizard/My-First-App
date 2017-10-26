package ru.roma.vk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static ru.roma.vk.R.layout.itemdialog;

/**
 * Created by Ilan on 17.09.2017.
 */

public class DialogsAdapter extends BaseAdapter implements Filterable {

    private Filter filter;
    public ArrayList<Dialogs> dialogs = new ArrayList<Dialogs>();
    private ArrayList<Dialogs> filterDialogs = new ArrayList<Dialogs>();

    @Override
    public int getCount() {
        return filterDialogs == null ? 0 : filterDialogs.size();
    }

    @Override
    public Object getItem(int i) {
        return dialogs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(itemdialog, viewGroup, false);
            }


            Dialogs dialog = filterDialogs.get(i);

            LinearLayout mainll = view.findViewById(R.id.mainLL_dialog);
            TextView title = ((TextView) view.findViewById(R.id.title_dialog));
            TextView body = ((TextView) view.findViewById(R.id.body));
            TextView time = view.findViewById(R.id.time_dialog);
            ImageView photo_dialog = view.findViewById(R.id.photo_dialog);
            ImageView myPhoto = view.findViewById(R.id.my_photo);
            ImageView onLine = view.findViewById(R.id.online_dialog);
            onLine.setVisibility(View.GONE);
            myPhoto.setVisibility(View.GONE);

            String lastMsg = new TimeHelper().getTime(dialog.getTime());
            time.setText(lastMsg);

            body.setText(dialog.getBody());
            new DownloadFile().downloadInList(dialog.getURLPhoto(), photo_dialog);

            String chapter = dialog.getTitle();
            if (TextUtils.isEmpty(chapter)) {
                title.setText(dialog.getFirst_name() + " " + dialog.getLast_name());
            } else
                title.setText(chapter);


            if (dialog.getOut() == 1) {
                myPhoto.setVisibility(View.VISIBLE);
                DownloadFile.downloadInList(dialog.getMyphoto(), myPhoto);
            }


            if (dialog.getReadeState() == 0) {
                if (dialog.getOut() == 0) {
                    mainll.setBackgroundColor(view.getResources().getColor(R.color.light_blue));
                    body.setBackgroundColor(view.getResources().getColor(R.color.light_blue));
                } else {
                    body.setBackgroundColor(view.getResources().getColor(R.color.light_blue));
                    body.setText(" " + dialog.getBody());
                }
            }else {
                mainll.setBackgroundColor(view.getResources().getColor(R.color.white));
                body.setBackgroundColor(view.getResources().getColor(R.color.white));
            }


            if (dialog.getOnLine() == 1){
                onLine.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setDialog(ArrayList<Dialogs> dialog) {
        this.dialogs = dialog;
        filterDialogs = dialogs;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    charSequence = charSequence.toString().toLowerCase();
                    FilterResults result = new FilterResults();
                    if (charSequence.length()>0){
                        ArrayList<Dialogs> filterItems = new ArrayList<Dialogs>();

                        for (int i=0;i<dialogs.size();i++){

                            String s = dialogs.get(i).getFirst_name().toLowerCase()+ " " + dialogs.get(i).getLast_name().toLowerCase();

                            Log.d("my log", s + " " + charSequence);
                            if (s.contains(charSequence)){
                                filterItems.add(dialogs.get(i));
                            }
                            result.count = filterItems.size();
                            result.values = filterItems;
                        }
                    }else {
                        synchronized (this){
                            result.values = dialogs;
                            result.count = dialogs == null ? 0 : dialogs.size();
                        }
                    }
                    return result;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                    filterDialogs = (ArrayList<Dialogs>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
        return filter;
    }
}
