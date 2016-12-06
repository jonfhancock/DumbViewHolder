package com.jonfhancock.dumbviewholder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SmartViewHolder.ExcellentAdventureListener {

    private ExcellentAdventureDataSource dataSource;
    private SuperChillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuperChillAdapter(getLayoutInflater(),this);

        dataSource = new ExcellentAdventureDataSource();
        adapter.updateItems(dataSource.getAdventures());
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.updateItems(dataSource.getRandomAdventures());
            }
        });

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
