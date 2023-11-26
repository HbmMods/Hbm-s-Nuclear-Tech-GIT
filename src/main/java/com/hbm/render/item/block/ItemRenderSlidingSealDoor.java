package com.hbm.render.item.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.tileentity.IItemRendererProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderSlidingSealDoor implements IItemRendererProvider {
    @Override
    public Item getItemForRenderer() {
        return Item.getItemFromBlock(ModBlocks.sliding_seal_door);
    }

    @Override
    public IItemRenderer getRenderer() {
        return new ItemRenderBase(){
            public void renderInventory() {
                GL11.glTranslated(0, -5, 0);
                GL11.glScaled(7, 7, 7);
            }
            public void renderCommon() {
                Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.sliding_seal_door_tex);
                GL11.glShadeModel(GL11.GL_SMOOTH);
                ResourceManager.sliding_seal_door.renderAll();
                GL11.glShadeModel(GL11.GL_FLAT);
            }
        };
    }
}
