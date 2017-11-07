/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=50";
    private NetworkDownloaderClass networkDownloaderClass;
    Earthquake_customAdapter eathadapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        networkDownloaderClass=new NetworkDownloaderClass();
        networkDownloaderClass.execute(USGS_URL);
        // Find a reference to the {@link ListView} in the layout

        // Create a new {@link ArrayAdapter} of earthquakes
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake currentEarthquake=eathadapter.getItem(i);
                Uri earthquakeuri=Uri.parse(currentEarthquake.getEarthquakeurl());
                Intent websiteIntent=new Intent(Intent.ACTION_VIEW,earthquakeuri);
                startActivity(websiteIntent);
            }
        });
    }
    private void updateUI(ArrayList<Earthquake> earthquakes)
    {

    }
    private class NetworkDownloaderClass extends AsyncTask<String,Void,ArrayList<Earthquake>>{

        @Override
        protected ArrayList<Earthquake> doInBackground(String... strings) {
            if(strings.length<1||strings[0]==null)return null;
            ArrayList<Earthquake> earth=QueryUtils.extractEarthquakes(strings[0]);
            return earth;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
                if(earthquakes==null)return;
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
            if(earthquakes==null)System.out.println("Yes");
            eathadapter=new Earthquake_customAdapter(EarthquakeActivity.this,earthquakes);
            if(eathadapter==null)System.out.println("Yes");
            earthquakeListView.setAdapter(eathadapter);
        }
    }
}
