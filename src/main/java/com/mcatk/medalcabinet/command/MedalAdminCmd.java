package com.mcatk.medalcabinet.command;

import com.mcatk.medalcabinet.Factory;
import com.mcatk.medalcabinet.medal.Medal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MedalAdminCmd implements CommandExecutor {
    private CommandSender sender;
    private String[] args;
    
    // usage: /medaladmin
    
    public MedalAdminCmd() {
    }
    
    private void printHelp() {
        sender.sendMessage("§e------------帮助------------");
        sender.sendMessage("§a/medaladmin create <medalId> <Name> §2创建勋章");
        sender.sendMessage("§a/medaladmin addlore <medalId> <des> ... §2添加描述");
        sender.sendMessage("§a/medaladmin setmat <medalId> <Material> §2设置材质");
        sender.sendMessage("§a/medaladmin give <player> <medalId> §2添加勋章");
        sender.sendMessage("§a/medaladmin reload §2重载");
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
        switch (args[0].toLowerCase()) {
            case "create":
                create();
                break;
            case "addlore":
                addDes();
                break;
            case "give":
                give();
                break;
            case "setmat":
                setMat();
                break;
            case "reload":
                reload();
                break;
            default:
        }
        return true;
    }
    
    private void create() {
        if (args.length == 3) {
            String id = args[1];
            String name = args[2];
            Factory.getFactory().getMedals().getMedalHashMap()
                    .put(id, new Medal(id, name));
            sender.sendMessage("Ok");
            Factory.getFactory().saveFile();
        }
    }
    
    private void addDes() {
        if (args.length >= 3) {
            String id = args[1];
            if (!Factory.getFactory().getMedals().
                    getMedalHashMap().containsKey(id)) {
                sender.sendMessage("无该id");
                return;
            }
            for (int i = 2; i < args.length; i++) {
                Factory.getFactory().getMedals().getMedalHashMap()
                        .get(id).addDescription(args[i]);
            }
            sender.sendMessage("Ok");
            Factory.getFactory().saveFile();
        }
    }
    
    private void give() {
        if (args.length == 3) {
            String playerId = args[1];
            String medalId = args[2];
            Factory.getFactory().getPlayers().addMedal(playerId, medalId);
            sender.sendMessage("Ok");
            Factory.getFactory().saveFile();
        }
    }
    
    private void reload() {
        Factory.getFactory().loadFile();
    }
    
    private void setMat() {
        String id = args[1];
        String mat = args[2];
        if (!Factory.getFactory().getMedals().
                getMedalHashMap().containsKey(id)) {
            sender.sendMessage("无该id");
            return;
        }
        Factory.getFactory().getMedals().getMedalHashMap().get(id)
                .setMaterial(mat);
        sender.sendMessage("Ok");
    }
}
