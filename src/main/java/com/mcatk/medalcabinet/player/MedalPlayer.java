package com.mcatk.medalcabinet.player;

import java.util.HashSet;

public class MedalPlayer {
    private String id;
    private HashSet<String> medalsId;
    
    public MedalPlayer(String id) {
        this.id = id;
        medalsId = new HashSet<>();
    }
    
    public void addMedal(String id) {
        medalsId.add(id);
    }
    
    public HashSet<String> getMedalsId() {
        return medalsId;
    }
}
