package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSteelRoof;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class RenderRoofBlock implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
        /*Tessellator.instance.draw();
        
		GL11.glPushMatrix();

		GL11.glTranslatef(x, y, z);
		
        ModelSteelRoof model = new ModelSteelRoof();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelRoof.png"));
        model.renderModel(0.0625F);
        
        GL11.glPopMatrix();
        
        Tessellator.instance.startDrawing(0);*/
        
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return 334078;
	}

}
