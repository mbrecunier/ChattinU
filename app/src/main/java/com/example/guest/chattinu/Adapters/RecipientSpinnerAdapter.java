package com.example.guest.chattinu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.guest.chattinu.Constants;
import com.example.guest.chattinu.models.User;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 5/5/16.
 */
public class RecipientSpinnerAdapter extends ArrayAdapter {

    public RecipientSpinnerAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
//    public Firebase mFirebaseUserRef = new Firebase(Constants.FIREBASE_URL_USERS);
//
//
//    String[] names = {"bob", "tom", "nance", "france", "steve"};

}
