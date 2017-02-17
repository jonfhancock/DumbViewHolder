package com.jonfhancock.dumbviewholder;

import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;


public class QueueAdapter extends BaseAdapter {
    private Queue<List<Item>> pendingUpdates =
            new ArrayDeque<>();

    public QueueAdapter(LayoutInflater inflater, SmartViewHolder.ExcellentAdventureListener adventureListener) {
        super(inflater, adventureListener);
        pendingUpdates = new ArrayDeque<>();
    }

    @Override
    public void updateItems(final List<Item> newItems) {
        pendingUpdates.add(newItems);
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateItemsInternal(newItems);
    }

    @Override
    protected void applyDiffResult(List<Item> newItems,
                                   DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove();
        dispatchUpdates(newItems, diffResult);
        if (pendingUpdates.size() > 0) {
            updateItemsInternal(pendingUpdates.peek());
        }
    }
}
