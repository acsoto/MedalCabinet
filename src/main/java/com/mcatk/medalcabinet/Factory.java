package com.mcatk.medalcabinet;

import com.mcatk.medalcabinet.medal.Medal;
import com.mcatk.medalcabinet.medal.Medals;
import com.mcatk.medalcabinet.player.Players;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class Factory {
    private static Factory factory;
    private Medals medals;
    private Players players;
    
    public Factory() {
        factory = this;
        loadFile();
    }
    
    public Medals getMedals() {
        return medals;
    }
    
    public Players getPlayers() {
        return players;
    }
    
    public static Factory getFactory() {
        return factory;
    }
    
    public void loadFile() {
        medals = new FileOperation().loadMedals();
        players = new FileOperation().loadPlayers();
        //load from json
        //if nothing return a new Object
    }
    
    public void saveFile() {
        new FileOperation().saveMedals(medals);
        new FileOperation().savePlayers(players);
    }
    
    public boolean containsPlayer(String id) {
        return players.getPlayers().containsKey(id);
    }
    
    public HashMap<String, Medal> getPlayersAllMedal(String id) {
        HashMap<String, Medal> map = new HashMap<>();
        for (String s : players.getPlayers().get(id).getMedalsId()) {
            if (medals.getMedalHashMap().containsKey(s)) {
                map.put(s, medals.getMedalHashMap().get(s));
            }
        }
        return map;
    }
    
    //color
    public static String colorFormat(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    
}
