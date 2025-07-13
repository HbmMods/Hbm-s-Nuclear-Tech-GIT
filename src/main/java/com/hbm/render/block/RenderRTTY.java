package com.hbm.render.block;

import com.hbm.blocks.network.RadioTorchBase;
import com.hbm.interfaces.NotableComments;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;
import com.hbm.tileentity.network.TileEntityRadioTorchLogic;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

@NotableComments
public class RenderRTTY implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(1, 1, 1);
		
		IIcon icon = block.getIcon(0, 0);
		int meta = world.getBlockMetadata(x, y, z);
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityRadioTorchBase) {
			TileEntityRadioTorchBase rtty = (TileEntityRadioTorchBase) tile;
			
			if(rtty.lastState > 0) {
				icon = block.getIcon(1, 0);
			}
		}
		//consequences of my actions
		if(tile instanceof TileEntityRadioTorchLogic) {
			TileEntityRadioTorchLogic rtty = (TileEntityRadioTorchLogic) tile;
			
			if(rtty.lastState > 0) {
				icon = block.getIcon(1, 0);
			}
		}

		float flip = 0;
		float rotation = 0;
		
		if(meta == 0)
			flip = (float)Math.PI;

		if(meta == 2)
			rotation = 90F / 180F * (float) Math.PI;

		if(meta == 3)
			rotation = 270F / 180F * (float) Math.PI;

		if(meta == 4)
			rotation = 180F / 180F * (float)Math.PI;
		
		if(rotation != 0F || meta == 5)
			flip = (float)Math.PI * 0.5F;

		//using OBJ here because vanilla's block renderer is so broken it's not even funny anymore
		//mojang genuinely doesn't know how on earth UVs work
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rtty, icon, tessellator, rotation, flip, false);
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return RadioTorchBase.renderID;
	}
}
