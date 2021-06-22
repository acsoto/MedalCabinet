package com.mcatk.medalcabinet;

import com.mcatk.medalcabinet.medal.Medal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class MedalsGui {
    
    public Inventory getGui(String playerId) {
        Inventory gui = Bukkit.createInventory(null, 54, playerId + " §6的勋章墙");
        HashMap<String, Medal> medals = Factory.getFactory().getPlayersAllMedal(playerId);
        if (medals.size() < 54) {
            for (Medal medal : medals.values()) {
                gui.addItem(getIcon(medal));
            }
        }
        gui.setItem(53, getQuitIcon());
        return gui;
    }
    
    private ItemStack getIcon(Medal medal) {
        ItemStack icon = new ItemStack(Material.getMaterial(medal.getMaterial()), 1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(medal.getName());
        meta.setLore(medal.getDescriptions());
        icon.setItemMeta(meta);
        return icon;
    }
    
    private ItemStack getQuitIcon() {
        ItemStack icon = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("返回");
        icon.setItemMeta(meta);
        return icon;
    }
    
}
