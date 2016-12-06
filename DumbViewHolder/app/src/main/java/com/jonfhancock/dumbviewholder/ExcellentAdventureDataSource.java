package com.jonfhancock.dumbviewholder;

import android.util.Log;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jonhancock on 12/6/16.
 */

public class ExcellentAdventureDataSource {
    List<ExcellentAdventure> adventures = new ArrayList<>();
    static private Random random = new Random();

    public ExcellentAdventureDataSource() {
        adventures.add(new ExcellentAdventure(420,ExcellentAdventure.ERA_BC,"Visit Socrates","37.97155","23.7245479","Parthenon, Athens, Greece", "Socrates"));
        adventures.add(new ExcellentAdventure(1815,ExcellentAdventure.ERA_AD,"Battle of Waterloo","50.7120367","4.4143367","Waterloo, Belgium", "Battle_of_Waterloo"));
        adventures.add(new ExcellentAdventure(0,ExcellentAdventure.ERA_AD,"Meet Atilla the Hun","0","0","Mongolia","Atilla_the_hun"));
    }

    public List<ExcellentAdventure> getAdventures() {
        return adventures;
    }
    public List<ExcellentAdventure> getRandomAdventures(){
        List<ExcellentAdventure> randAdventures = new ArrayList<>();
        int size = getRandomSize();
        SparseBooleanArray usedAdventures = new SparseBooleanArray();

        for(int i = 0;i<size;i++){
            int desiredIndex = random.nextInt(adventures.size());
            Log.d("DataSource","desired index is: " + desiredIndex);
            while (usedAdventures.get(desiredIndex,false)){
                desiredIndex = random.nextInt(adventures.size());
                Log.d("DataSource","next desired index is: " + desiredIndex);
            }
            usedAdventures.put(desiredIndex,true);
            randAdventures.add(adventures.get(desiredIndex));
        }
        return randAdventures;
    }

    private int getRandomSize() {
        int size = random.nextInt(adventures.size());
        while(size < 1){
            size = random.nextInt(adventures.size());
        }
        return size;
    }
}
