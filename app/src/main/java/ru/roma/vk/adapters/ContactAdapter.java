package ru.roma.vk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.roma.vk.holders.PhoneBook;
import ru.roma.vk.R;

/**
 * Created by Ilan on 26.09.2017.
 */

public class ContactAdapter extends BaseAdapter {

    private ArrayList<PhoneBook.Contact> contactArrayList;

    @Override
    public int getCount() {
        return contactArrayList == null ?0 :contactArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.itemcontact, viewGroup, false);
        }
        PhoneBook.Contact con = (PhoneBook.Contact) getItem(i);
        TextView name = view.findViewById(R.id.contact);
        name.setText(con.getName());

        ImageView imagev = view.findViewById(R.id.image_contact);
        if (con.getPhoto() != null) {
            imagev.setImageBitmap(con.getPhoto());
        } else {
            imagev.setImageResource(R.drawable.contact_nophoto);
        }

        return view;
    }
    public void setContactArrayList(ArrayList<PhoneBook.Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
        notifyDataSetChanged();
    }
}
