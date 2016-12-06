package com.jonfhancock.dumbviewholder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements SmartViewHolder.ExcellentAdventureListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SuperChillAdapter adapter = new SuperChillAdapter(getLayoutInflater(),this);
        recyclerView.setAdapter(adapter);

        ExcellentAdventureDataSource dataSource = new ExcellentAdventureDataSource();
        adapter.updateItems(dataSource.getRandomAdventures());
    }

    @Override
    public void onMapClicked(ExcellentAdventure item) {
        String url = String.format("geo:%s,%s?q=%s",item.getLat(),item.getLon(),item.getLocationName());
        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void onTitleClicked(ExcellentAdventure item) {
        String url = "http://en.wikipedia.com/wiki/" + item.getWikipediaTitle();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }
}
