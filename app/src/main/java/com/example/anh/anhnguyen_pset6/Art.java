package com.example.anh.anhnguyen_pset6;

import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by Anh on 12-12-2016.
 */

class Art implements Serializable {
private String title, url, id;

    public Art() {    }

            // Creates Art object with title, url and unique id.
            Art(String title, String url, String id) throws JSONException {
            this.title = title;
            this.url = url;
            this.id = id;
            }

    // Retrieves title of art object.
    public String getTitle() {
            return title;
            }

    // Retrieves url of art object.
    public String getUrl() {
            return url;
            }

    // Retrieves id of art object
    public String getId() {
            return id;
            }
            }
