package com.mohkamfer.silkyspawners.listener;

import com.mohkamfer.silkyspawners.SilkySpawners;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

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

        event.setExpToDrop(0);

        BlockState state = block.getState();
        if (state instanceof CreatureSpawner) {
            String entity = ((CreatureSpawner) state).getSpawnedType().getKey() + "";
            instance.getLogger().info(entity);
            ItemStack drop = new ItemStack(state.getType());
            ItemMeta itemMeta = drop.getItemMeta();
            BlockStateMeta blockStateMeta = ((BlockStateMeta) itemMeta);
            BlockState blockState = blockStateMeta.getBlockState();
            CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;
            creatureSpawner.setSpawnedType(((CreatureSpawner) state).getSpawnedType());
            blockStateMeta.setBlockState(creatureSpawner);
            itemMeta.setDisplayName(getSpawnerNameFromEntity(entity));
            drop.setItemMeta(itemMeta);
            player.getWorld().dropItemNaturally(block.getLocation(), drop);
        }
    }

    private String getSpawnerNameFromEntity(String entity) {
        return capitalizeWords(entity
                .replace("minecraft:", "")
                .replace("_", " ") + " spawner");
    }

    private String capitalizeWords(String s) {
        String[] s2 = s.split(" ");
        StringBuilder result = new StringBuilder();
        for (String s1 : s2)
            result.append(Character.toTitleCase(s1.charAt(0))).append(s1.substring(1)).append(' ');

        return result.toString();
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
