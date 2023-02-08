package com.unoriginal.mimicfish.chest_searcher;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Render {
    public static List<BlockInfo> ores = Collections.synchronizedList(new ArrayList<>());
    private static final int GL_FRONT_AND_BACK = 1032;
    private static final int GL_LINE = 6913;
    private static final int GL_FILL = 6914;
    private static final int GL_LINES = 1;

    @SideOnly(Side.CLIENT)
    public static void drawOres( float playerX, float playerY, float playerZ )
    {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        Profile.BLOCKS.apply(); // Sets GL state for block drawing
        buffer.setTranslation( -playerX, -playerY, -playerZ );
        ores.forEach(b -> {
        buffer.begin( GL_LINES, DefaultVertexFormats.POSITION_COLOR );
        Render.renderBlockBounding( buffer, b, (int) b.alpha );
        tessellator.draw();
        });
        buffer.setTranslation( 0, 0, 0 );

        Profile.BLOCKS.clean();
    }

    /**
     * OpenGL Profiles used for rendering blocks and entities
     */
    @SideOnly(Side.CLIENT)
    private enum Profile
    {
        BLOCKS {
            @Override
            public void apply()
            {
                GlStateManager.disableTexture2D();
                GlStateManager.disableDepth();
                GlStateManager.depthMask( false );
                GlStateManager.glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
                GlStateManager.blendFunc( GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA );
                GlStateManager.enableBlend();
                GlStateManager.glLineWidth( 3F);
            }

            @Override
            public void clean()
            {
                GlStateManager.glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
                GlStateManager.disableBlend();
                GlStateManager.enableDepth();
                GlStateManager.depthMask( true );
                GlStateManager.enableTexture2D();
            }
        };
        Profile() {}
        public abstract void apply();
        public abstract void clean();
    }

    public static void renderBlockBounding(BufferBuilder buffer, BlockInfo b, int opacity) {
        if( b == null )
            return;

        final float size = 1.0f;
        int red = 255;
        int green = 255;
        int blue = 255;
        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();

        // TOP
        buffer.pos(x, y + size, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y + size, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y + size, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y + size, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y + size, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y + size, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y + size, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y + size, z).color(red, green, blue, opacity).endVertex();

        // BOTTOM
        buffer.pos(x + size, y, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y, z).color(red, green, blue, opacity).endVertex();

        // Edge 1
        buffer.pos(x + size, y, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y + size, z + size).color(red, green, blue, opacity).endVertex();

        // Edge 2
        buffer.pos(x + size, y, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x + size, y + size, z).color(red, green, blue, opacity).endVertex();

        // Edge 3
        buffer.pos(x, y, z + size).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y + size, z + size).color(red, green, blue, opacity).endVertex();

        // Edge 4
        buffer.pos(x, y, z).color(red, green, blue, opacity).endVertex();
        buffer.pos(x, y + size, z).color(red, green, blue, opacity).endVertex();
    }
}
