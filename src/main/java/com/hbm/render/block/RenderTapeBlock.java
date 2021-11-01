package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.DecoTapeRecorder;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderTapeBlock implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

        if (renderer.hasOverrideBlockTexture())
        {
            iicon = renderer.overrideBlockTexture;
        }
        
        GL11.glTranslated(0, -0.5, 0);
        tessellator.startDrawingQuads();
        if(block == ModBlocks.tape_recorder)
        	ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.taperecorder, iicon, tessellator, 0, false);
        if(block == ModBlocks.steel_poles)
        	ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.pole, iicon, tessellator, 0, false);
		tessellator.draw();
		
        GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(0, world.getBlockMetadata(x, y, z));
		
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

        if (renderer.hasOverrideBlockTexture())
        {
            iicon = renderer.overrideBlockTexture;
        }
        
        float rotation = 0;

        if(world.getBlockMetadata(x, y, z) == 2)
        	rotation = 90F / 180F * (float)Math.PI;
        
        if(world.getBlockMetadata(x, y, z) == 3)
        	rotation = 270F / 180F * (float)Math.PI;
        
        if(world.getBlockMetadata(x, y, z) == 4)
        	rotation = 180F / 180F * (float)Math.PI;
        
        tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
        if(block == ModBlocks.tape_recorder)
        	ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.taperecorder, iicon, tessellator, rotation, true);
        if(block == ModBlocks.steel_poles)
        	ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.pole, iicon, tessellator, rotation - 90F / 180F * (float)Math.PI, true);
        tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
        
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return DecoTapeRecorder.renderID;
	}
}
