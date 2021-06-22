package com.mcatk.medalcabinet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MedalListener implements Listener {
    //禁止玩家拿走物品
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        if (event.getInventory().getTitle().contains("勋章墙")) {
            event.setCancelled(true);
            ItemStack icon = event.getCurrentItem();
            if (icon != null) {
                if (icon.getItemMeta().getDisplayName() != null) {
                    if (icon.getItemMeta().getDisplayName().equals("返回")) {
                        ((Player) event.getWhoClicked()).chat("/menu");
                    }
                }
            }
        }
    }
    
}
