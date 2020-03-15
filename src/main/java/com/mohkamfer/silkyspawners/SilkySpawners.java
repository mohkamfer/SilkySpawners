package com.mohkamfer.silkyspawners;

import com.mohkamfer.silkyspawners.listener.SpawnerBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SilkySpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(new SpawnerBreakListener(this), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
