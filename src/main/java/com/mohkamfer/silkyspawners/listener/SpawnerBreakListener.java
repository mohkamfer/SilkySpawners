package com.mohkamfer.silkyspawners.listener;

import com.mohkamfer.silkyspawners.SilkySpawners;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

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

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (!isSilkPickaxe(itemInHand)) return;

        instance.getLogger().info("Spawner broken!");
    }

    private boolean isSilkPickaxe(ItemStack itemStack) {
        return isPickaxe(itemStack.getType()) && isSilkTouch(itemStack);
    }

    private boolean isPickaxe(Material material) {
        List<Material> pickaxes = Arrays.asList(Material.DIAMOND_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.IRON_PICKAXE,
                Material.STONE_PICKAXE,
                Material.WOODEN_PICKAXE);

        return pickaxes.contains(material);
    }

    private boolean isSilkTouch(ItemStack itemStack) {
        return itemStack.containsEnchantment(Enchantment.SILK_TOUCH);
    }
}
