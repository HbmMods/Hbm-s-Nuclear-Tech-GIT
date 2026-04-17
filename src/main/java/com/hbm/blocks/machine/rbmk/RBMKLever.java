package com.hbm.blocks.machine.rbmk;

import org.lwjgl.opengl.GL11;

import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKLever;

import api.hbm.block.IToolable;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKLever extends RBMKMiniPanelBase implements IToolable {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKLever();
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;
		
		if(hitX != 0 && hitX != 1 && hitZ != 0 && hitZ != 1 && side != 0 && side != 1) {
			
			TileEntityRBMKLever tile = (TileEntityRBMKLever) world.getTileEntity(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			int indexHit = 0;

			if(meta == 2 && hitX < 0.5) indexHit = 1;
			if(meta == 3 && hitX > 0.5) indexHit = 1;
			if(meta == 4 && hitZ > 0.5) indexHit = 1;
			if(meta == 5 && hitZ < 0.5) indexHit = 1;
			
			if(!tile.levers[indexHit].active) return false;
			tile.levers[indexHit].click();
		}
		
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {
		super.renderInventoryBlock(block, meta, modelId, renderBlocks);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotated(-90, 0, 1, 0);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rbmk_lever_tex);
		
		for(int i = 0; i < 2; i++) {
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, 0, i * -0.5 + 0.25);
			ResourceManager.rbmk_lever.renderPart("Base");
			ResourceManager.rbmk_lever.renderPart("Lever");
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}
