package com.jonfhancock.dumbviewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class SmartViewHolder extends RecyclerView.ViewHolder {
    TextView textTitle;
    TextView textLocation;
    TextView textDate;
    ImageView mapIcon;
    View textContainer;

    ExcellentAdventure item;
    ExcellentAdventureListener listener;

    public interface ExcellentAdventureListener{
        void onMapClicked(ExcellentAdventure item);
        void onTitleClicked(ExcellentAdventure item);
    }

    public SmartViewHolder(View itemView, final ExcellentAdventureListener listener) {
        super(itemView);
        this.listener = listener;
        textTitle = (TextView) itemView.findViewById(R.id.text_title);
        textLocation = (TextView) itemView.findViewById(R.id.text_location);
        textDate = (TextView) itemView.findViewById(R.id.text_date);
        mapIcon = (ImageView) itemView.findViewById(R.id.map_icon);
        textContainer = itemView.findViewById(R.id.text_container);

        textContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTitleClicked(item);
            }
        });
        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMapClicked(item);
            }
        });
    }

    public void setItem(ExcellentAdventure item) {
        this.item = item;
        textTitle.setText(item.getTitle());
        textLocation.setText(item.getLocationName());
        textDate.setText(getFormattedDate(item));
    }
    private String getFormattedDate(ExcellentAdventure item) {
        String date = item.getYear() + " " + item.getEra();
        return date;
    }

}
