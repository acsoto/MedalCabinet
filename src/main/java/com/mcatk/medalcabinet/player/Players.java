package com.mcatk.medalcabinet.player;

import java.util.HashMap;

public class Players {
    private HashMap<String, MedalPlayer> players;
    
    public Players() {
        players = new HashMap<>();
    }
    
    public void addMedal(String playerId, String medalId) {
        if (!players.containsKey(playerId)) {
            players.put(playerId, new MedalPlayer(playerId));
        }
        players.get(playerId).addMedal(medalId);
    }
    
    public HashMap<String, MedalPlayer> getPlayers() {
        return players;
    }
}