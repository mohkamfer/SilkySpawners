package com.mohkamfer.silkyspawners;

import org.bukkit.plugin.java.JavaPlugin;

public final class SilkySpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        getLogger().info("onEnable has been invoked!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info("onDisable HAS BEEN INVOOOOOOOOOOOOOOOOOOOOOOOKED!!!!!!!!!!! XDDDDDDDDDDDD");
    }
}
