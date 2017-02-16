package com.jonfhancock.dumbviewholder;

import android.os.Handler;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class QueueAdapter extends BaseAdapter {
    private Queue<List<ExcellentAdventure>> pendingUpdates;

    public QueueAdapter(LayoutInflater inflater, SmartViewHolder.ExcellentAdventureListener adventureListener) {
        super(inflater, adventureListener);
        pendingUpdates = new ArrayDeque<>();
    }

    @Override
    public void updateItems(final List<ExcellentAdventure> newItems) {
        pendingUpdates.add(newItems);
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(pendingUpdates.size()));
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateItemsInternal(newItems);
    }

    @Override
    protected void applyDiffResult(List<ExcellentAdventure> newItems, DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove();
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(pendingUpdates.size()));
        diffResult.dispatchUpdatesTo(QueueAdapter.this);
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        if (pendingUpdates.size() > 0) {
            updateItemsInternal(pendingUpdates.peek());
        }
    }

}
