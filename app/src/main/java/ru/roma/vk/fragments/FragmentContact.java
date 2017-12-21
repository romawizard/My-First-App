package ru.roma.vk.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ru.roma.vk.holders.PhoneBook;
import ru.roma.vk.R;
import ru.roma.vk.adapters.ContactAdapter;

/**
 * Created by Ilan on 26.09.2017.
 */

public class FragmentContact extends Fragment {

    ListView listContact;
    ContactAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ContactAdapter();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentcontact,null);

        listContact = view.findViewById(R.id.list_contact);


        listContact.setAdapter(adapter);

        AsynContact contact = new AsynContact();
        contact.execute();

        return view;
    }

    private class AsynContact extends AsyncTask<Void,Void,ArrayList<PhoneBook.Contact>> {

        @Override
        protected ArrayList<PhoneBook.Contact> doInBackground(Void... voids) {
            PhoneBook book = new PhoneBook();
            return book.getAll();
        }

        @Override
        protected void onPostExecute(ArrayList<PhoneBook.Contact> contacts) {
            super.onPostExecute(contacts);
            adapter.setContactArrayList(contacts);
            Log.d("my log",String.valueOf(contacts.size()));
        }
    }
}
