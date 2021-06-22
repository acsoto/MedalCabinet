package com.mcatk.medalcabinet.command;

import com.mcatk.medalcabinet.Factory;
import com.mcatk.medalcabinet.MedalsGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MedalUsualCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        String id;
        if (args.length == 0) {
            id = sender.getName();
        } else {
            id = args[0];
        }
        if (Factory.getFactory().containsPlayer(id)) {
            ((Player) sender).openInventory(new MedalsGui().getGui(id));
        } else {
            sender.sendMessage("§e该玩家没有任何勋章");
        }
        return true;
    }
}
