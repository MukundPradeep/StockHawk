package com.sam_chordas.android.stockhawk.rest;

import java.io.Serializable;

/**
 * Created by Admin on 22-03-2016.
 */
public class GraphData implements Serializable {
    private String[] prices;
    private String[] dates;

    public GraphData(String[] prices, String[] dates) {
        this.prices = prices;
        this.dates = dates;
    }

    public String[] getPrices(){
        return this.prices;
    }

    public String[] getDates(){
        return this.dates;
    }
}
