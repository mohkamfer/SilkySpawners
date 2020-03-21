package com.mohkamfer.silkyspawners.listener;

import com.mohkamfer.silkyspawners.SilkySpawners;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerPlaceListener implements Listener {

    public static final String SILKY_SPAWNERS_MAX_SPAWNERS = "max-spawners-per-chunk";
    private SilkySpawners instance;

    public SpawnerPlaceListener(SilkySpawners instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawnerPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        boolean isFakeEvent = !BlockPlaceEvent.class.equals(event.getClass());
        if (isFakeEvent) {
            return;
        }

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (!player.hasPermission("silkspawners.place")) {
            event.setCancelled(true);
            return;
        }

        if (block.getType() == Material.SPAWNER) {
            Chunk chunk = block.getChunk();
            BlockState[] tileEntities = chunk.getTileEntities();
            int spawnerCount = 0;
            for (BlockState tileEntity : tileEntities) {
                if (tileEntity.getType() == Material.SPAWNER) spawnerCount++;
            }

            int maxSpawnerCount;
            FileConfiguration config = instance.getConfig();
            if (config.contains(SILKY_SPAWNERS_MAX_SPAWNERS)) {
                maxSpawnerCount = config.getInt(SILKY_SPAWNERS_MAX_SPAWNERS);
            } else {
                maxSpawnerCount = 10;
                config.set(SILKY_SPAWNERS_MAX_SPAWNERS, 10);
                instance.saveConfig();
            }

            if (spawnerCount > maxSpawnerCount - 1) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "" +
                        ChatColor.BOLD + "Maximum spawner count reached in this chunk (" + spawnerCount + ")");
            }

            // set spawner entity if not op
            ItemStack itemInHand = player.getItemInHand();
            ItemMeta itemMeta = itemInHand.getItemMeta();
            BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;
            BlockState blockState = blockStateMeta.getBlockState();
            CreatureSpawner spawner = (CreatureSpawner) blockState;

            Block blockPlaced = event.getBlockPlaced();
            BlockState placedBlockState = blockPlaced.getState();
            CreatureSpawner placedSpawner = (CreatureSpawner) placedBlockState;

            placedSpawner.setSpawnedType(spawner.getSpawnedType());

            placedBlockState.update(true);
        }
    }
}
