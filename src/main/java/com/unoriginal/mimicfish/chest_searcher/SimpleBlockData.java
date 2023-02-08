package com.unoriginal.mimicfish.chest_searcher;

public class SimpleBlockData {

    private String name;
    private String blockName;
    private int order;
    private boolean drawing;


    public SimpleBlockData(String name, String blockName, boolean drawing, int order) {
        this.name = name;
        this.blockName = blockName;
        this.drawing = drawing;
        this.order = order;

    }

    public String getName() {
        return this.name;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public int getOrder() {
        return this.order;
    }

}
