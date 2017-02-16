package com.jonfhancock.dumbviewholder;

import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by jhancock on 2/15/17.
 */
class ExcellentDiffCallback extends DiffUtil.Callback {
    private final List<ExcellentAdventure> oldItems;
    private final List<ExcellentAdventure> newItems;

    public ExcellentDiffCallback(List<ExcellentAdventure> oldItems, List<ExcellentAdventure> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        // Simulate a really long running diff calculation.
        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }
}
