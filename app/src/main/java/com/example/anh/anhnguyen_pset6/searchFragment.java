package com.example.anh.anhnguyen_pset6;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anh on 12-12-2016.
 */

public class searchFragment extends android.support.v4.app.Fragment {

    // Creates all variables used in the class.
    ArrayList<String> artlist = new ArrayList<>();
    ArrayList<String> urllist = new ArrayList<>();
    ArrayList<String> idlist = new ArrayList<>();
    SearchArt retrieve;
    String retrieved_art;
    JSONObject json_art;
    JSONArray art_array;
    ListView list;
    EditText search_text;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        search_text = (EditText) view.findViewById(R.id.searchArtEdit);
        list = (ListView) view.findViewById(R.id.searchlist);
        TextView empty = (TextView) view.findViewById(R.id.emptysearch);
        list.setEmptyView(empty);

        return view;
    }}

  /*  public void search() throws ExecutionException, InterruptedException, JSONException {

        idlist.clear();
        artlist.clear();
        urllist.clear();

;

            retrieve = new SearchArt();
            retrieved_art = retrieve.execute(text).get();
            json_art = new JSONObject(retrieved_art);
            art_array = json_art.getJSONArray("artObjects");
            // Stores all the retrieved art in arraylists with titles and urls.
            for(int i=0; i<art_array.length(); i++){
                JSONObject object = art_array.getJSONObject(i);
                String title = object.get("title").toString();
                String id = object.get("objectNumber").toString();
                if(object.get("webImage").equals(null)){
                    String url = "";
                    urllist.add(i, url);
                }
                else {
                    JSONObject image = object.getJSONObject("webImage");
                    String url = image.get("url").toString();
                    urllist.add(i, url);
                }
                idlist.add(i, id);
                artlist.add(i, title);
            }
            // Sets the listview with all the retrieved art from the search, with the use of the art adapter.
            ArtAdapter adapter = new ArtAdapter(artlist, urllist, getActivity());
            list.setAdapter(adapter);
            // When clicked on an item in the retrieved list it shows the information page of this piece of art.
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity.myBundle.putString("id", idlist.get(position));
                    MainActivity.myBundle.putString("url", urllist.get(position));
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, artinfofragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

        });}}


public class searchFragment extends Fragment {

    private EditText searchText;
    ArrayList<String> artlist = new ArrayList<>();
    ArrayList<String> urllist = new ArrayList<>();
    ArrayList<String> idlist = new ArrayList<>();
    SearchArt retrieve;
    String retrieved_art;
    JSONObject json_art;
    JSONArray art_array;
    ListView list;
    ArtInfoFragment artinfofragment = new ArtInfoFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        searchText = (EditText) view.findViewById(R.id.searchArtEdit);
        list = (ListView) view.findViewById(R.id.searchlist);
        TextView empty = (TextView) view.findViewById(R.id.emptysearch);
        list.setEmptyView(empty);
        return view;
    }

    public void search() throws ExecutionException, InterruptedException, JSONException {
        //String userText = searchText.getText().toString();
        Log.d("LOGG", searchText.getText().toString());

        idlist.clear();
        artlist.clear();
        urllist.clear();


        if(text.equals("")){
            Toast.makeText(getActivity(),"Search was empty", Toast.LENGTH_SHORT).show();
        }

            retrieve = new SearchArt();
            retrieved_art = retrieve.execute(userText).get();
            json_art = new JSONObject(retrieved_art);
            art_array = json_art.getJSONArray("artObjects");
            // Stores all the retrieved art in arraylists with titles and urls.
            for(int i=0; i<art_array.length(); i++){
                JSONObject object = art_array.getJSONObject(i);
                String title = object.get("title").toString();
                String id = object.get("objectNumber").toString();
                if(object.get("webImage").equals(null)){
                    String url = "";
                    urllist.add(i, url);
                }
                else {
                    JSONObject image = object.getJSONObject("webImage");
                    String url = image.get("url").toString();
                    urllist.add(i, url);
                }
                idlist.add(i, id);
                artlist.add(i, title);
            }
            // Sets the listview with all the retrieved art from the search, with the use of the art adapter.
            ArtAdapter adapter = new ArtAdapter(artlist, urllist, getActivity());
            list.setAdapter(adapter);
            // When clicked on an item in the retrieved list it shows the information page of this piece of art.
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity.myBundle.putString("id", idlist.get(position));
                    MainActivity.myBundle.putString("url", urllist.get(position));
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, artinfofragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

    }
}
 */