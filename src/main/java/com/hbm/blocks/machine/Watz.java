package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityWatz;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Watz extends BlockDummyable {

	public Watz() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityWatz();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) player.addChatComponentMessage(new ChatComponentText("Oh gee, I wonder if the nameless uncraftable thing that isn't even in the changelog works yet, maybe if I click often enough it will work!!"));
		return true;
		//return super.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 3, 3, 3, 3};
	}

	@Override
	public int getOffset() {
		return 3;
	}
}
