package com.hbm.blocks;

import com.hbm.lib.RefStrings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBase extends Block {

	private boolean beaconable = false;
	private boolean canSpawn = true;

	public BlockBase() {
		super(Material.rock);
	}

	public BlockBase(Material material) {
		super(material);
	}

	@Override
	public Block setBlockName(String name) {
		super.setBlockName(name);
		this.setBlockTextureName(RefStrings.MODID + ":" + name);
		return this;
	}

	/**
	 * Daisychainable setter for making the block a beacon base block
	 * @return
	 */
	public BlockBase setBeaconable() {
		this.beaconable = true;
		return this;
	}

	public BlockBase noMobSpawn() {
		this.canSpawn = false;
		return this;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return this.canSpawn ? super.canCreatureSpawn(type, world, x, y, z) : false;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return this.beaconable;
	}

	/**
	 * Sets the block to air and drops it
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public void dismantle(World world, int x, int y, int z) {

		world.setBlockToAir(x, y, z);

		ItemStack itemstack = new ItemStack(this, 1);
		float f = world.rand.nextFloat() * 0.6F + 0.2F;
		float f1 = world.rand.nextFloat() * 0.2F;
		float f2 = world.rand.nextFloat() * 0.6F + 0.2F;

		EntityItem entityitem = new EntityItem(world, x + f, y + f1 + 1, z + f2, itemstack);

		float f3 = 0.05F;
		entityitem.motionX = (float) world.rand.nextGaussian() * f3;
		entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
		entityitem.motionZ = (float) world.rand.nextGaussian() * f3;

		if(!world.isRemote)
			world.spawnEntityInWorld(entityitem);
	}
}
