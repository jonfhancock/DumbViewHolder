package com.jonfhancock.dumbviewholder;

import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.jonfhancock.dumbviewholder.Item.ERA_AD;
import static com.jonfhancock.dumbviewholder.Item.ERA_BC;

public class ExcellentAdventureDataSource {
    List<Item> adventures = new ArrayList<>();
    static private Random random = new Random();

    public ExcellentAdventureDataSource() {
        populateInitialAdventures();

    }

    private void populateInitialAdventures() {
        adventures.add(new Item(1988, ERA_AD, "Strange things are afoot at the Circle K", "34.1069239", "-117.8023196", "San Dimas, CA", "San_Dimas,_California"));
        adventures.add(new Item(410, ERA_BC, "Visit Socrates", "37.97155", "23.7245479", "Parthenon, Athens, Greece", "Socrates"));
        adventures.add(new Item(1815, ERA_AD, "Battle of Waterloo", "50.7120367", "4.4143367", "Waterloo, Belgium", "Battle_of_Waterloo"));
        adventures.add(new Item(1209, ERA_AD, "Meet Genghis Khan", "47.887221", "106.9094643", "Mongolia", "Genghis_Khan"));
        adventures.add(new Item(1879, ERA_AD, "Meet Billy the Kid", "34.4775884", "-104.2711666", "Fort Sumner, MN", "Billy_the_Kid"));
        adventures.add(new Item(1461, ERA_AD, "Visit Princess Elizabeth. Watch out for the king!", "51.501364", "-0.14189", "London, England", "Henry_VI_of_England"));
        adventures.add(new Item(1901, ERA_AD, "Visit Sigmund Freud", "48.2185966", "16.3608401", "Vienna, Austria", "Sigmund_Freud"));
        adventures.add(new Item(1810, ERA_AD, "Visit Beethoven", "51.3053004", "9.4814412", "Kassel, Germany", "Ludwig_van_Beethoven"));
        adventures.add(new Item(1492, ERA_AD, "Visit Joan of Arc", "47.9014983", "1.9054311", "Orleans, France", "Joan_of_Arc"));
        adventures.add(new Item(1863, ERA_AD, "Visit Abraham Lincoln", "38.8976094", "-77.0389236", "Washington, DC", "Abraham_Lincoln"));
    }

    public List<Item> getAdventures() {
        return adventures;
    }

    public List<Item> getRandomAdventures() {
        List<Item> randAdventures = new ArrayList<>();
        int size = getRandomSize();
        SparseBooleanArray usedAdventures = new SparseBooleanArray();

        for (int i = 0; i < size; i++) {
            int desiredIndex = random.nextInt(adventures.size());
            while (usedAdventures.get(desiredIndex, false)) {
                desiredIndex = random.nextInt(adventures.size());
            }
            usedAdventures.put(desiredIndex, true);
            randAdventures.add(adventures.get(desiredIndex));
        }
        return randAdventures;
    }

    private int getRandomSize() {
        int size = random.nextInt(adventures.size());
        while (size < 1) {
            size = random.nextInt(adventures.size());
        }
        return size;
    }
}
