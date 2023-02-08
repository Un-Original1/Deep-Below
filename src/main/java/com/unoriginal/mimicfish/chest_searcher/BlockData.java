package com.unoriginal.mimicfish.chest_searcher;

import net.minecraft.item.ItemStack;

public class BlockData {
    private String entryName;

    private String blockName;


    private ItemStack itemStack;

    private boolean drawing;

    private int order;

    public BlockData(String entryName, String blockName, ItemStack itemStack, boolean drawing, int order) {
        this.entryName = entryName;
        this.blockName = blockName;
        this.itemStack = itemStack;
        this.drawing = drawing;
        this.order = order;
    }

    public String getEntryName() {
        return this.entryName;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public void setDrawing(boolean drawing) {
        this.drawing = drawing;
    }

    public int getOrder() {
        return this.order;
    }
}
