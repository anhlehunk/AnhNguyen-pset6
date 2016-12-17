package com.example.anh.anhnguyen_pset6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Anh on 12-12-2016.
 */

class ArtAdapter extends BaseAdapter {
private ArrayList<String> artlist;
private ArrayList<String> artposters;
private ArrayList<String> idlist;
private Context context;

        ArtAdapter(Context context, ArrayList<String> artlist, ArrayList<String> artposters, ArrayList<String> idlist){
        this.artlist = artlist;
        this.context = context;
        this.artposters = artposters;
        this.idlist = idlist;
        }

        // Gets the size of the artlist
        @Override
        public int getCount() {
                return artlist.size();
                }

        // Gets the art at a  position.
        @Override
        public Object getItem(int position) {
                return artlist.get(position);
                }




        @Override
        public long getItemId(int position) {
                return position;
                }

        // Sets adapter view for the search result listview.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = convertView;
                if(view == null){
                view = inflater.inflate(R.layout.item_search, null);
                }
                TextView title = (TextView) view.findViewById(R.id.title);
                // Textview with the title of the art.
                title.setText(artlist.get(position));
                TextView art_id = (TextView) view.findViewById(R.id.artID);
                //textview with invisible ID
                art_id.setText(idlist.get(position));
                ImageView image = (ImageView) view.findViewById(R.id.image);
                // Imageview with the url for the image
                String url = artposters.get(position);
                // If there is no url, then a drawable of a no image will be set to the imageview.
                if(url.equals("")){
                image.setImageResource(R.drawable.no_image);
                }
                else {
                //set the image
                Picasso.with(context).load(url).fit().into(image);
                }
                return view;
                }
                }
