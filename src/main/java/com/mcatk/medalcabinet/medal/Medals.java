package com.mcatk.medalcabinet.medal;

import java.util.HashMap;

public class Medals {
    private HashMap<String, Medal> medalHashMap;
    
    public Medals() {
        this.medalHashMap = new HashMap<>();
    }
    
    public HashMap<String, Medal> getMedalHashMap() {
        return medalHashMap;
    }
}
