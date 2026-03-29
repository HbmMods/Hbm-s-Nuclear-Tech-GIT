package com.hbm.blocks.machine.rbmk;

import org.lwjgl.opengl.GL11;

import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKGraph;

import api.hbm.block.IToolable;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKGraph extends RBMKMiniPanelBase implements IToolable {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKGraph();
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {
		super.renderInventoryBlock(block, meta, modelId, renderBlocks);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotated(-90, 0, 1, 0);
		
		for(int i = 0; i < 2; i++) {
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, i * -0.5 + 0.25, 0);
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rbmk_numitron_tex);
			ResourceManager.rbmk_numitron.renderAll();
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}
