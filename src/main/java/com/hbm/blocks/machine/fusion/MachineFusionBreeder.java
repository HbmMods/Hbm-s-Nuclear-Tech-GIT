package com.hbm.blocks.machine.fusion;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionBreeder;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFusionBreeder extends BlockDummyable implements ITooltipProvider {

	public MachineFusionBreeder() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionBreeder();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 2, 2, 1, 1 };
	}

	@Override
	public int getOffset() {
		return 2;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
		this.makeExtra(world, x - rot.offsetX, y, z - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX + rot.offsetX, y, z + dir.offsetZ + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX - rot.offsetX, y, z + dir.offsetZ - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 2, y + 2, z + dir.offsetZ * 2);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
