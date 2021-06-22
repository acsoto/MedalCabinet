package com.mcatk.medalcabinet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcatk.medalcabinet.medal.Medals;
import com.mcatk.medalcabinet.player.Players;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperation {
    
    private final File medalsFile;
    private final File playersFile;
    private final Gson gson;
    
    public FileOperation() {
        medalsFile = new File(MedalCabinet.getPlugin().getDataFolder(), "medals.json");
        playersFile = new File(MedalCabinet.getPlugin().getDataFolder(), "players.json");
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public void saveMedals(Medals medals) {
        try {
            FileWriter writer = new FileWriter(medalsFile);
            gson.toJson(medals, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Medals loadMedals() {
        try {
            if (medalsFile.exists()) {
                FileReader reader = new FileReader(medalsFile);
                return gson.fromJson(reader, Medals.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Medals();
    }
    
    public void savePlayers(Players players) {
        try {
            FileWriter writer = new FileWriter(playersFile);
            gson.toJson(players, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Players loadPlayers() {
        try {
            if (playersFile.exists()) {
                FileReader reader = new FileReader(playersFile);
                return gson.fromJson(reader, Players.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Players();
    }
}
