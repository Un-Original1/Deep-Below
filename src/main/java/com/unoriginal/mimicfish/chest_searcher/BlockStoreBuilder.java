package com.unoriginal.mimicfish.chest_searcher;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockStoreBuilder {
    public static SimpleBlockData CHEST = new SimpleBlockData("chest", "minecraft:chest", false, 0);
    public static SimpleBlockData CHEST_TRAP = new SimpleBlockData("chest_trap", "minecraft:trapped_chest", false, 0);
    public static void init() {
        List<SimpleBlockData> list = new ArrayList<>();
        list.add(CHEST);
        list.add(CHEST_TRAP);
        Controller.getBlockStore().setStore(BlockStore.getFromSimpleBlockList(list));
    }

}
