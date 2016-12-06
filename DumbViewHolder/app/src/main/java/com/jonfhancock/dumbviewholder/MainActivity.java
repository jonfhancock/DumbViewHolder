package com.jonfhancock.dumbviewholder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

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

        List<ExcellentAdventure> adventures = new ArrayList<>();
        adventures.add(new ExcellentAdventure(420,ExcellentAdventure.ERA_BC,"Visit Socrates","37.97155","23.7245479","Parthenon, Athens, Greece", "Socrates"));
        adventures.add(new ExcellentAdventure(1815,ExcellentAdventure.ERA_AD,"Battle of Waterloo","50.7120367","4.4143367","Waterloo, Belgium", "Battle_of_Waterloo"));
        adapter.updateItems(adventures);
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
