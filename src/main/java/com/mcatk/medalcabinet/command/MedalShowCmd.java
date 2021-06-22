package com.mcatk.medalcabinet.command;

import com.mcatk.medalcabinet.Factory;
import com.mcatk.medalcabinet.MedalCabinet;
import com.mcatk.medalcabinet.medal.Medal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MedalShowCmd implements CommandExecutor {
    private CommandSender sender;
    private String[] args;
    
    private void printHelp() {
        sender.sendMessage("§e------------帮助------------");
        sender.sendMessage("§a/medalshow all §2展示你全部的勋章");
        sender.sendMessage("§a/medalshow <ID> §2展示你的某个勋章");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.args = args;
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length == 0) {
            printHelp();
            return true;
        }
        if (!Factory.getFactory().containsPlayer(sender.getName())) {
            sender.sendMessage("§e你没有任何勋章");
            return true;
        }
        switch (args[0]) {
            case "all":
                showAll();
                break;
            default:
                show();
                break;
        }
        return false;
    }
    
    private void showAll() {
        StringBuilder stringBuilder = new StringBuilder("§d§l勋章系统 §7>>> §e")
                .append(sender.getName())
                .append("展示了他的勋章：\n");
        for (Medal medal : Factory.getFactory().getPlayersAllMedal(sender.getName()).values()) {
            stringBuilder.append("§7[").append(medal.getName()).append("§7] ");
        }
        MedalCabinet.getPlugin().getServer().broadcastMessage(stringBuilder.toString());
    }
    
    private void show() {
        Medal medal = Factory.getFactory().getMedals().getMedalHashMap().get(args[1]);
        if (medal == null) {
            sender.sendMessage("无此勋章");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("§d§l勋章系统 §7>>> §e")
                .append(sender.getName())
                .append("展示了他的勋章：\n")
                .append("§7[").append(medal.getName()).append("§7]");
        MedalCabinet.getPlugin().getServer().broadcastMessage(stringBuilder.toString());
    }
    
}
