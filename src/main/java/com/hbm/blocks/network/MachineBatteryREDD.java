package com.hbm.blocks.network;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.storage.TileEntityBatteryREDD;
import com.hbm.util.BobMathUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineBatteryREDD extends BlockDummyable implements IPersistentInfoProvider {

	public MachineBatteryREDD() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityBatteryREDD();
		if(meta >= 6) return new TileEntityProxyCombo().power().conductor();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {9, 0, 2, 2, 4, 4}; }
	@Override public int getOffset() { return 2; }
	
	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * 2 + rot.offsetX * 2, y, z + dir.offsetZ * 2 + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * 2 - rot.offsetX * 2, y, z + dir.offsetZ * 2 - rot.offsetZ * 2);
		this.makeExtra(world, x - dir.offsetX * 2 + rot.offsetX * 2, y, z - dir.offsetZ * 2 + rot.offsetZ * 2);
		this.makeExtra(world, x - dir.offsetX * 2 - rot.offsetX * 2, y, z - dir.offsetZ * 2 - rot.offsetZ * 2);
		this.makeExtra(world, x + rot.offsetX * 4, y, z + rot.offsetZ * 4);
		this.makeExtra(world, x - rot.offsetX * 4, y, z - rot.offsetZ * 4);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		if(persistentTag != null && persistentTag.hasKey("power"))
			list.add(EnumChatFormatting.YELLOW + "" + BobMathUtil.format(new BigInteger(persistentTag.getByteArray("power"))) + " HE");
	}
}
