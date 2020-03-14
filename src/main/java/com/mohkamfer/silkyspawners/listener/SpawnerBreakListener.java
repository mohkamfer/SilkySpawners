package com.mohkamfer.silkyspawners.listener;

import com.mohkamfer.silkyspawners.SilkySpawners;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SpawnerBreakListener implements Listener {

    private SilkySpawners instance;

    public SpawnerBreakListener(SilkySpawners instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawnerBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        boolean isFakeEvent = !BlockBreakEvent.class.equals(event.getClass());
        if (isFakeEvent) {
            return;
        }

        Block block = event.getBlock();

        if (block.getType() != Material.SPAWNER) {
            return;
        }

        instance.getLogger().info("Spawner broken!");
    }
}
