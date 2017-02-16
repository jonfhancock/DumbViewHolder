package com.jonfhancock.dumbviewholder;

import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;

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
        updateItemsInternal(newItems);
    }

    @Override
    protected void applyDiffResult(List<ExcellentAdventure> newItems,
                                   DiffUtil.DiffResult diffResult) {
        dispatchUpdates(newItems, diffResult);
        operationPending = false;
    }
}
