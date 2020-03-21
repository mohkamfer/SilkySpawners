package com.mohkamfer.silkyspawners;

import com.mohkamfer.silkyspawners.listener.SpawnerBreakListener;
import com.mohkamfer.silkyspawners.listener.SpawnerPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SilkySpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new SpawnerBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(this), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
