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
import java.util.Random;


public class SuperChillAdapter extends RecyclerView.Adapter {
    private List<ExcellentAdventure> items;
    private LayoutInflater inflater;
    private SmartViewHolder.ExcellentAdventureListener adventureListener;
    private Queue<List<ExcellentAdventure>> pendingUpdates;

    public SuperChillAdapter(LayoutInflater inflater, SmartViewHolder.ExcellentAdventureListener adventureListener) {
        this.inflater = inflater;
        this.adventureListener = adventureListener;
        items = new ArrayList<>();
        pendingUpdates = new ArrayDeque<>();

    }
    public void updateItems(final List<ExcellentAdventure> newItems) {
        pendingUpdates.add(newItems);
        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(pendingUpdates.size()));
        if(pendingUpdates.size() > 1){
            return;
        }

        updateItemsInternal(newItems);


    }

    private void updateItemsInternal(final List<ExcellentAdventure> newItems) {
        final List<ExcellentAdventure> oldItems = new ArrayList<>(this.items);

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
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
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pendingUpdates.remove();
                        BusDriver.getInstance().getBus().post(new PendingUdatesChangeEvent(pendingUpdates.size()));
                        diffResult.dispatchUpdatesTo(SuperChillAdapter.this);
                        items.clear();
                        if (newItems != null) {
                            items.addAll(newItems);
                        }
                        if(pendingUpdates.size() > 0){
                            updateItemsInternal(pendingUpdates.peek());
                        }
                    }
                });

            }
        }).start();
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


    public static class PendingUdatesChangeEvent{
        int count;

        public PendingUdatesChangeEvent(int count) {
            this.count = count;
        }
    }
}
