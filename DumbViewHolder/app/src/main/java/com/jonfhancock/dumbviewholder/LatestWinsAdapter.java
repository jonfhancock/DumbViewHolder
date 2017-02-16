package com.jonfhancock.dumbviewholder;

import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;


public class LatestWinsAdapter extends BaseAdapter {
    private Deque<List<ExcellentAdventure>> pendingUpdates;

    public LatestWinsAdapter(LayoutInflater inflater, SmartViewHolder.ExcellentAdventureListener adventureListener) {
        super(inflater, adventureListener);
        pendingUpdates = new ArrayDeque<>();
    }

    @Override
    public void updateItems(final List<ExcellentAdventure> newItems) {
        pendingUpdates.push(newItems);
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateItemsInternal(newItems);
    }

    @Override
    protected void applyDiffResult(List<ExcellentAdventure> newItems, DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove(newItems);
        dispatchUpdates(newItems,diffResult);
        if (pendingUpdates.size() > 0) {
            List<ExcellentAdventure> latest = pendingUpdates.pop();
            pendingUpdates.clear();
            updateItemsInternal(latest);
        }
    }

}
