package com.example.anh.anhnguyen_pset6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Anh on 16-12-2016.
 */

class FavoriteAdapter extends BaseAdapter {
    private ArrayList<String> artlist;
    private ArrayList<String> idlist;
    private Context context;

    FavoriteAdapter(Context context, ArrayList<String> artlist, ArrayList<String> idlist){
        this.artlist = artlist;
        this.context = context;
        this.idlist = idlist;
    }

    //size
    @Override
    public int getCount() {
        return artlist.size();
    }

    // Gets a certain position.
    @Override
    public Object getItem(int position) {
        return artlist.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.item_favorite, null);
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        // Textview with the title of the art.
        title.setText(artlist.get(position));
        TextView art_id = (TextView) view.findViewById(R.id.artID);
        //set id
        art_id.setText(idlist.get(position));
        return view;
    }
}
