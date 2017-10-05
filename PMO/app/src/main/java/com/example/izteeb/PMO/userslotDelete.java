package com.example.izteeb.PMO;

/**
 * Created by Izteeb on 9/25/2017.
 */

public class userslotDelete {

    String username;
    int slot;

    public userslotDelete(int slot, String username) {

        this.username = username;
        this.slot = slot;
    }

    public String getUsername() {
        return username;
    }

    public int getSlot() {
        return slot;
    }
}
