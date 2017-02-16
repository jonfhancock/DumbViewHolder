package com.jonfhancock.dumbviewholder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements SmartViewHolder.ExcellentAdventureListener {

    private ExcellentAdventureDataSource dataSource;
    private BaseAdapter adapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String adapterType;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        BusDriver.getInstance().getBus().register(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueueAdapter(getLayoutInflater(), this);
        adapterType = "Queue";
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_first_wins){
            adapterType = "First Wins";
            adapter = new FirstWinsAdapter(getLayoutInflater(),this);
            recyclerView.setAdapter(adapter);
            adapter.updateItems(dataSource.getRandomAdventures());
            return true;
        }
        if(item.getItemId() == R.id.action_latest_wins){
            adapterType = "Latest Wins";
            adapter = new LatestWinsAdapter(getLayoutInflater(),this);
            recyclerView.setAdapter(adapter);
            adapter.updateItems(dataSource.getRandomAdventures());
            return true;
        }
        if(item.getItemId() == R.id.action_queue){
            adapterType = "Queue";
            adapter = new QueueAdapter(getLayoutInflater(),this);
            recyclerView.setAdapter(adapter);
            adapter.updateItems(dataSource.getRandomAdventures());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        BusDriver.getInstance().getBus().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onMapClicked(ExcellentAdventure item) {
        String url = String.format("geo:%s,%s?q=%s", item.getLat(), item.getLon(), item.getLocationName());
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

    @Subscribe
    public void onPendingUpdateCountChanged(final PendingUdatesChangeEvent event) {
        collapsingToolbarLayout.setTitle(adapterType + ": Pending Updates: " + event.count);
    }

}
