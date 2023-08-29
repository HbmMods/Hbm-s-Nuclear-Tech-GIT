package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHazard extends Block implements ITooltipProvider {
		
	protected float rad = 0.0F;
	private ExtDisplayEffect extEffect = null;
	
	private boolean beaconable = false;

	public BlockHazard() {
		this(Material.iron);
	}

	public BlockHazard(Material mat) {
		super(mat);
	}
	
	public BlockHazard setDisplayEffect(ExtDisplayEffect extEffect) {
		this.extEffect = extEffect;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		if(extEffect == null)
			return;
		
		switch(extEffect) {
		case RADFOG:
		case SCHRAB:
		case FLAMES:
			sPart(world, x, y, z, rand);
			break;
			
		case SPARKS:
			break;
			
		case LAVAPOP:
			world.spawnParticle("lava", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			break;
			
		default: break;
		}
	}
	
	private void sPart(World world, int x, int y, int z, Random rand) {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			if(dir == ForgeDirection.DOWN && this.extEffect == ExtDisplayEffect.FLAMES)
				continue;

			if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.air) {

				double ix = x + 0.5F + dir.offsetX + rand.nextDouble() * 3 - 1.5D;
				double iy = y + 0.5F + dir.offsetY + rand.nextDouble() * 3 - 1.5D;
				double iz = z + 0.5F + dir.offsetZ + rand.nextDouble() * 3 - 1.5D;

				if(dir.offsetX != 0)
					ix = x + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * dir.offsetX;
				if(dir.offsetY != 0)
					iy = y + 0.5F + dir.offsetY * 0.5 + rand.nextDouble() * dir.offsetY;
				if(dir.offsetZ != 0)
					iz = z + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * dir.offsetZ;

				if(this.extEffect == ExtDisplayEffect.RADFOG) {
					world.spawnParticle("townaura", ix, iy, iz, 0.0, 0.0, 0.0);
				}
				if(this.extEffect == ExtDisplayEffect.SCHRAB) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "schrabfog");
					data.setDouble("posX", ix);
					data.setDouble("posY", iy);
					data.setDouble("posZ", iz);
					MainRegistry.proxy.effectNT(data);
				}
				if(this.extEffect == ExtDisplayEffect.FLAMES) {
					world.spawnParticle("flame", ix, iy, iz, 0.0, 0.0, 0.0);
					world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.0, 0.0);
					world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.1, 0.0);
				}
			}
		}
	}

	public BlockHazard makeBeaconable() {
		this.beaconable = true;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return beaconable;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(this.rad > 0) {
			ChunkRadiationManager.proxy.incrementRad(world, x, y, z, rad);
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		}
	}

	@Override
	public int tickRate(World world) {

		if(this.rad > 0)
			return 20;

		return super.tickRate(world);
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		// who wrote this???
		rad = HazardSystem.getHazardLevelFromStack(new ItemStack(this), HazardRegistry.RADIATION) * 0.1F;

		if(this.rad > 0)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
	
	public static enum ExtDisplayEffect {
		RADFOG,
		SPARKS,
		SCHRAB,
		FLAMES,
		LAVAPOP
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) { }

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		
		if(this == ModBlocks.block_schraranium
				|| this == ModBlocks.block_schraranium
				|| this == ModBlocks.block_schrabidate
				|| this == ModBlocks.block_solinium
				|| this == ModBlocks.block_schrabidium_fuel)
			return EnumRarity.rare;
		
		return EnumRarity.common;
	}
}
