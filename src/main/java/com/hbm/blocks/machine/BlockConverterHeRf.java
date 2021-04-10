package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityConverterHeRf;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockConverterHeRf extends BlockContainer {

	public BlockConverterHeRf(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityConverterHeRf();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityConverterHeRf entity = (TileEntityConverterHeRf) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				player.addChatComponentMessage(new ChatComponentText("Note: Buffer may not accuratly represent current conversion rate, keep tact rates in mind."));
				player.addChatComponentMessage(new ChatComponentText("HE: " + (entity.buf / 4)));
				player.addChatComponentMessage(new ChatComponentText("RF: " + entity.buf));
				//FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_converter_he_rf, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

}
