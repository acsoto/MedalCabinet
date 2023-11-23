package com.mcatk.medalcabinet;

import com.mcatk.medalcabinet.medal.Medal;
import com.mcatk.medalcabinet.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MedalsGui implements Listener {

    private final Inventory gui;
    private final ItemStack back;
    private final HashMap<ItemStack, Medal> iconMap = new HashMap<>();

    public MedalsGui(String playerID) {
        Bukkit.getPluginManager().registerEvents(this, MedalCabinet.getPlugin());
        gui = Bukkit.createInventory(null, 54, playerID + " §6的勋章墙");
        // medals icon
        ArrayList<Medal> medals = SQLManager.getInstance().getPlayerMedals(playerID);
        if (medals.size() < 54) {
            for (Medal medal : medals) {
                ItemStack icon = getIcon(medal);
                gui.addItem(icon);
                iconMap.put(icon, medal);
            }
        }
        // back button
        back = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("返回");
        back.setItemMeta(meta);
        gui.setItem(53, back);
    }

    public void openGUI(Player player) {
        player.openInventory(gui);
    }

    private ItemStack getIcon(Medal medal) {
        ItemStack icon = new ItemStack(Material.getMaterial(medal.getMaterial()), 1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(medal.getName());
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(medal.getDescriptions().split("\n")));
        lore.add("§0" + medal.getId());
        meta.setLore(lore);
        icon.setItemMeta(meta);
        return icon;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(gui)) return;
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        if (clickedItem.equals(back)) {
            ((Player) e.getWhoClicked()).chat("/menu");
            return;
        }
        Medal medal = iconMap.get(clickedItem);
        SQLManager.getInstance().setMainMedal(e.getWhoClicked().getName(), medal.getId());
        e.getWhoClicked().sendMessage("§e已将勋章 " + medal.getName() + " §e设为主勋章");
    }


}
