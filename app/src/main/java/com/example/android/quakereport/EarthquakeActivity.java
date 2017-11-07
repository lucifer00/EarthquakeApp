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

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final int EARTHQUAKE_LOADER_ID=1;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=50";
    Earthquake_customAdapter eathadapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        // Find a reference to the {@link ListView} in the layout

        // Create a new {@link ArrayAdapter} of earthquakes
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
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

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this,USGS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        if(eathadapter!=null)eathadapter.clear();
        if(earthquakes!=null&&!earthquakes.isEmpty())
        {
            eathadapter=new Earthquake_customAdapter(EarthquakeActivity.this,earthquakes);
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
            earthquakeListView.setAdapter(eathadapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        eathadapter.clear();
    }
}
