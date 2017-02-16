package com.jonfhancock.dumbviewholder;

import android.os.Handler;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter extends RecyclerView.Adapter {
    protected List<ExcellentAdventure> items;
    private LayoutInflater inflater;
    private SmartViewHolder.ExcellentAdventureListener adventureListener;


    public BaseAdapter(LayoutInflater inflater, SmartViewHolder.ExcellentAdventureListener adventureListener) {
        this.inflater = inflater;
        this.adventureListener = adventureListener;
        items = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_excellent_adventure, parent, false);
        return new SmartViewHolder(v, adventureListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SmartViewHolder vh = (SmartViewHolder) holder;
        vh.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateItemsInternal(final List<ExcellentAdventure> newItems) {
        final List<ExcellentAdventure> oldItems = new ArrayList<>(this.items);

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ExcellentDiffCallback(oldItems, newItems));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyDiffResult(newItems, diffResult);
                    }
                });

            }
        }).start();
    }
    protected void dispatchUpdates(List<ExcellentAdventure> newItems, DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(this);
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
    }
    protected abstract void applyDiffResult(List<ExcellentAdventure> newItems, DiffUtil.DiffResult diffResult);
    protected abstract void updateItems(List<ExcellentAdventure> newItems);


}
