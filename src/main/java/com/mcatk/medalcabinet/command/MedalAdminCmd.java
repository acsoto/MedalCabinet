package com.mcatk.medalcabinet.command;

import com.mcatk.medalcabinet.MedalCabinet;
import com.mcatk.medalcabinet.medal.Medal;
import com.mcatk.medalcabinet.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MedalAdminCmd implements CommandExecutor {
    private CommandSender sender;
    private String[] args;

    public MedalAdminCmd() {
    }

    private void printHelp() {
        sender.sendMessage("§e------------帮助------------");
        sender.sendMessage("§a/medaladmin create <medalId> <Name> <Material> <Description> §2创建勋章");
        sender.sendMessage("§a/medaladmin add <player> <medalId> §2添加勋章");
        sender.sendMessage("§a/medaladmin check <player> §2查看玩家勋章");
        sender.sendMessage("§a/medaladmin list §2勋章列表");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        this.sender = sender;
        this.args = args;
        if (args.length == 0) {
            printHelp();
            return true;
        }
        Bukkit.getScheduler().runTaskAsynchronously(MedalCabinet.getPlugin(), () -> {
            switch (args[0].toLowerCase()) {
                case "create":
                    create();
                    break;
                case "add":
                    add();
                    break;
                case "list":
                    list();
                    break;
                case "check":
                    check();
                    break;
                default:
            }
        });
        return true;
    }

    private void create() {
        if (args.length == 5) {
            String id = args[1];
            String name = args[2];
            String mat = args[3];
            String des = args[4];
            SQLManager.getInstance().createMedal(id, name, mat, des);
            sender.sendMessage("Ok");
        }
    }

    private void add() {
        if (args.length == 3) {
            String playerID = args[1];
            String medalID = args[2];
            SQLManager.getInstance().addPlayerMedal(playerID, medalID);
            sender.sendMessage("Ok");
        }
    }

    private void list() {
        for (Medal medal : SQLManager.getInstance().getAllMedals()) {
            sender.sendMessage(medal.toString() + "\n");
        }
    }

    private void check() {
        String playerID = args[1];
        StringBuilder stringBuilder = new StringBuilder("§d§l勋章系统 §7>>> §e")
                .append(playerID)
                .append("的勋章：\n");
        for (Medal medal : SQLManager.getInstance().getPlayerMedals(playerID)) {
            stringBuilder.append(medal).append(" ");
        }
        sender.sendMessage(stringBuilder.toString());
    }
}
