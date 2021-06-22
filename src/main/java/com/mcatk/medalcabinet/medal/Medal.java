package com.mcatk.medalcabinet.medal;

import com.mcatk.medalcabinet.Factory;

import java.util.ArrayList;

public class Medal {
    private String id;
    private String name;
    private String material;
    private ArrayList<String> descriptions;
    
    public Medal(String id, String name) {
        this.id = id;
        this.name = Factory.colorFormat(name);
        this.material = "DIAMOND";
        this.descriptions = new ArrayList<>();
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    public String getName() {
        return name;
    }
    
    public String getMaterial() {
        return material;
    }
    
    public void addDescription(String str) {
        descriptions.add(Factory.colorFormat(str));
    }
    
    public ArrayList<String> getDescriptions() {
        return descriptions;
    }
}
