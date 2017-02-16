package com.jonfhancock.dumbviewholder;

import android.os.Handler;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FirstWinsAdapter extends BaseAdapter {
    boolean operationPending;

    public FirstWinsAdapter(LayoutInflater inflater, SmartViewHolder.ExcellentAdventureListener adventureListener) {
        super(inflater, adventureListener);
    }

    @Override
    public void updateItems(final List<ExcellentAdventure> newItems) {
        if (operationPending) {
            return;
        }
        operationPending = true;
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(1));
        updateItemsInternal(newItems);
    }

    @Override
    protected void applyDiffResult(List<ExcellentAdventure> newItems, DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(FirstWinsAdapter.this);
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        operationPending = false;
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(0));
    }

}
