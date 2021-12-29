package com.mcatk.medalcabinet.medal;

public class Medal {
    private String id;
    private String name;
    private String material;
    private String descriptions;

    public Medal(String id, String name, String material, String descriptions) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.descriptions = descriptions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public String getDescriptions() {
        return descriptions;
    }

    @Override
    public String toString() {
        return "§7[" + name + "§7]";
    }
}
