package com.hbm.blocks.machine.rbmk;

import org.lwjgl.opengl.GL11;

import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKTerminal;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKTerminal extends RBMKMiniPanelBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKTerminal();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) return false;
		if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {
		super.renderInventoryBlock(block, meta, modelId, renderBlocks);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotated(-90, 0, 1, 0);
		
		GL11.glTranslated(0.25, 0, 0);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rbmk_terminal_tex);
		ResourceManager.rbmk_terminal.renderAll();
		
		GL11.glPopMatrix();
	}
}
