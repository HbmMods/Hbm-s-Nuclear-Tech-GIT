package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockPipe;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderPipe implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
		if(!(block instanceof BlockPipe))
			return;

		BlockPipe pipe = (BlockPipe) block;
		
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iiconTop = block.getIcon(0, 0);
		IIcon iiconSide = block.getIcon(4, 0);
		tessellator.setColorOpaque_F(1, 1, 1);
		
		tessellator.startDrawingQuads();
		
		if(pipe.rType == 0) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe, "Top", iiconTop, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe, "Side", iiconSide, tessellator, 0, false);
		}

		if(pipe.rType == 1) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Top", iiconTop, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Side", iiconSide, tessellator, 0, false);
		}

		if(pipe.rType == 2) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_quad, "Top", iiconTop, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_quad, "Side", iiconSide, tessellator, 0, false);
		}
		
		if(pipe.rType == 3) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Top", iiconTop, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Side", iiconSide, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_frame, "Frame", pipe.frameIcon, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_frame, "Mesh", pipe.meshIcon, tessellator, 0, false);
		}
		
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		if(!(block instanceof BlockPipe))
			return true;

		BlockPipe pipe = (BlockPipe) block;

		Tessellator tessellator = Tessellator.instance;
		IIcon iiconTop = block.getIcon(0, world.getBlockMetadata(x, y, z));
		IIcon iiconSide = block.getIcon(4, world.getBlockMetadata(x, y, z));
		int meta = world.getBlockMetadata(x, y, z);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		
		float rot = 0;
		float pitch = 0;
		
		if(meta == 8) {
			rot = (float)(Math.PI * 0.5);
			pitch = (float)(Math.PI * 0.5);
		}
		
		if(meta == 4) {
			pitch = (float)(Math.PI * 0.5);
		}

		if(pipe.rType == 0) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe, "Top", iiconTop, tessellator, rot, pitch, true);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe, "Side", iiconSide, tessellator, rot, pitch, true);
		}

		if(pipe.rType == 1) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Top", iiconTop, tessellator, rot, pitch, true);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Side", iiconSide, tessellator, rot, pitch, true);
		}

		if(pipe.rType == 2) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_quad, "Top", iiconTop, tessellator, rot, pitch, true);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_quad, "Side", iiconSide, tessellator, rot, pitch, true);
		}

		if(pipe.rType == 3) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Top", iiconTop, tessellator, rot, pitch, true);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_rim, "Side", iiconSide, tessellator, rot, pitch, true);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_frame, "Frame", pipe.frameIcon, tessellator, rot, pitch, true);
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_frame, "Mesh", pipe.meshIcon, tessellator, rot, pitch, true);
		}
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockPipe.renderID;
	}
}
