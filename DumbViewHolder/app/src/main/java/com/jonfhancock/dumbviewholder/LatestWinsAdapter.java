package com.jonfhancock.dumbviewholder;

import android.os.Handler;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(pendingUpdates.size()));
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateItemsInternal(newItems);
    }

    @Override
    protected void applyDiffResult(List<ExcellentAdventure> newItems, DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove(newItems);
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(pendingUpdates.size()));
        diffResult.dispatchUpdatesTo(LatestWinsAdapter.this);
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        if (pendingUpdates.size() > 0) {
            List<ExcellentAdventure> latest = pendingUpdates.pop();
            pendingUpdates.clear();
            updateItemsInternal(latest);
        }
    }

}
