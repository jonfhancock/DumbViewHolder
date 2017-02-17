package com.jonfhancock.dumbviewholder;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhancock on 2/16/17.
 */

public class MainThreadAdapter extends RecyclerView.Adapter {
    protected List<Item> items;

    public void updateItems(final List<Item> newItems) {
        final List<Item> oldItems = new ArrayList<>(items);
        DiffUtil.DiffResult diffResult =
          DiffUtil.calculateDiff(new DiffCb(oldItems,newItems));
        items.clear();
        items.addAll(newItems);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
