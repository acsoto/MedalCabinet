package com.mcatk.medalcabinet.command;

import com.mcatk.medalcabinet.MedalCabinet;
import com.mcatk.medalcabinet.medal.Medal;
import com.mcatk.medalcabinet.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MedalShowCmd implements CommandExecutor {
    private CommandSender sender;
    private String[] args;

    private final String prefix = "§7[§6勋章墙§7]§7 ";

    private void printHelp() {
        sender.sendMessage("§e------------帮助------------");
        sender.sendMessage("§a/medalshow all §2展示你全部的勋章（全服可见）");
        sender.sendMessage("§a/medalshow me §2展示你全部的勋章（仅自己可见）");
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
        if ("all".equals(args[0])) {
            showAll();
        } else if ("me".equals(args[0])) {
            showMe();
        }
        return false;
    }

    private void showAll() {
        Bukkit.getScheduler().runTaskAsynchronously(MedalCabinet.getPlugin(), () -> {
            StringBuilder stringBuilder = new StringBuilder(prefix).append("§e").append(sender.getName())
                    .append(" 展示了他的勋章：\n");
            for (Medal medal : SQLManager.getInstance().getPlayerMedals(sender.getName())) {
                stringBuilder.append(medal).append(" ");
            }
            MedalCabinet.getPlugin().getServer().broadcastMessage(stringBuilder.toString());
        });
    }

    private void showMe() {
        Bukkit.getScheduler().runTaskAsynchronously(MedalCabinet.getPlugin(), () -> {
            StringBuilder stringBuilder = new StringBuilder(prefix).append("§e").append(sender.getName())
                    .append(" 的勋章：\n");
            for (Medal medal : SQLManager.getInstance().getPlayerMedals(sender.getName())) {
                stringBuilder.append(medal).append(" ");
            }
            sender.sendMessage(stringBuilder.toString());
        });
    }

}
