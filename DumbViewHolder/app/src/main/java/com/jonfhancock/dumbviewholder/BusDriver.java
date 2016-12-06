package com.jonfhancock.dumbviewholder;

import com.squareup.otto.Bus;

/**
 * Created by jonhancock on 12/6/16.
 */

public class BusDriver {
    static private BusDriver instance;
    private final Bus bus;

    private BusDriver(){
        bus = new Bus();
    }
    public static BusDriver getInstance(){
        if(instance == null){
            instance = new BusDriver();
        }
        return instance;
    }

    public Bus getBus() {
        return bus;
    }
}
