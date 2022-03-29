package com.mcatk.medalcabinet;

import com.mcatk.medalcabinet.command.MedalAdminCmd;
import com.mcatk.medalcabinet.command.MedalShowCmd;
import com.mcatk.medalcabinet.command.MedalUsualCmd;
import com.mcatk.medalcabinet.papi.MedalPapi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MedalCabinet extends JavaPlugin {

    private static MedalCabinet plugin;

    public static MedalCabinet getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        regCommand();
        regListener();
        regDependency();
    }

    @Override
    public void onDisable() {

    }

    private void regListener() {
        Bukkit.getPluginManager().
                registerEvents(new MedalListener(), this);
        getLogger().info("监听器注册完毕");
    }

    private void regCommand() {
        Bukkit.getPluginCommand("medal").
                setExecutor(new MedalUsualCmd());
        Bukkit.getPluginCommand("medalshow").
                setExecutor(new MedalShowCmd());
        Bukkit.getPluginCommand("medaladmin").
                setExecutor(new MedalAdminCmd());
        getLogger().info("注册指令注册完毕");
    }

    private void regDependency() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MedalPapi().register();
            getLogger().info("检测到PlaceholderAPI，已启动PAPI变量");
        }
    }
}
