package com.unoriginal.mimicfish.chest_searcher;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BlockStore {

    private HashMap<UUID, BlockData> store = new HashMap<>();

    private HashMap<String, UUID> storeReference = new HashMap<>();

    public void put(BlockData data) {
        if (this.storeReference.containsKey(data.getBlockName()))
            return;
        UUID uniqueId = UUID.randomUUID();
        this.store.put(uniqueId, data);
        this.storeReference.put(data.getBlockName(), uniqueId);
    }

    public HashMap<UUID, BlockData> getStore() {
        return this.store;
    }

    public void setStore(ArrayList<BlockData> store) {
        this.store.clear();
        this.storeReference.clear();
        store.forEach(this::put);
    }

    public BlockDataWithUUID getStoreByReference(String name) {
        UUID uniqueId = this.storeReference.get(name);
        if (uniqueId == null)
            return null;
        BlockData blockData = this.store.get(uniqueId);
        if (blockData == null)
            return null;
        return new BlockDataWithUUID(blockData, uniqueId);
    }

    public static ArrayList<BlockData> getFromSimpleBlockList(List<SimpleBlockData> simpleList) {
        ArrayList<BlockData> blockData = new ArrayList<>();
        for (SimpleBlockData e : simpleList) {
            if (e == null)
                continue;
            ResourceLocation location = null;
            try {
                location = new ResourceLocation(e.getBlockName());
            } catch (Exception exception) {}
            if (location == null)
                continue;
            Block block =  ForgeRegistries.BLOCKS.getValue(location);
            if (block == null)
                continue;
            blockData.add(new BlockData(e.getName(), e.getBlockName(), new ItemStack(block, 1), e.isDrawing(), e.getOrder()));
        }
        return blockData;
    }

    public static final class BlockDataWithUUID {
        BlockData blockData;

        UUID uuid;

        public BlockDataWithUUID(BlockData blockData, UUID uuid) {
            this.blockData = blockData;
            this.uuid = uuid;
        }

        public BlockData getBlockData() {
            return this.blockData;
        }

        public UUID getUuid() {
            return this.uuid;
        }
    }
}
