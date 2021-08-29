package com.hbm.blocks.bomb;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlockVolcano extends Block {

	public BlockVolcano() {
		super(Material.iron);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < 4; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int tickRate(World world) {
		return 5;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		if(!world.isRemote)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote) {
			
			int meta = world.getBlockMetadata(x, y, z);
			blastMagmaChannel(world, x, y, z, rand);
			raiseMagma(world, x, y, z, rand);
			spawnBlobs(world, x, y, z, rand);
			spawnSmoke(world, x, y, z, rand);
			
			updateVolcano(world, x, y, z, rand, meta);
		}
	}
	
	private void blastMagmaChannel(World world, int x, int y, int z, Random rand) {
		
		List<ExAttrib> attribs = Arrays.asList(new ExAttrib[] {ExAttrib.NODROP, ExAttrib.LAVA_V, ExAttrib.NOSOUND, ExAttrib.ALLMOD, ExAttrib.NOHURT});
		
		ExplosionNT explosion = new ExplosionNT(world, null, x + 0.5, y + rand.nextInt(15) + 1.5, z + 0.5, 7);
		explosion.addAllAttrib(attribs);
		explosion.explode();
		
		ExplosionNT explosion2 = new ExplosionNT(world, null, x + 0.5 + rand.nextGaussian() * 3, rand.nextInt(y), z + 0.5 + rand.nextGaussian() * 3, 10);
		explosion2.addAllAttrib(attribs);
		explosion2.explode();
	}
	
	private void raiseMagma(World world, int x, int y, int z, Random rand) {

		int rX = x - 10 + rand.nextInt(21);
		int rY = y + rand.nextInt(11);
		int rZ = z - 10 + rand.nextInt(21);
		
		if(world.getBlock(rX, rY, rZ) == Blocks.air && world.getBlock(rX, rY - 1, rZ) == ModBlocks.volcanic_lava_block)
			world.setBlock(rX, rY, rZ, ModBlocks.volcanic_lava_block);
	}
	
	private void spawnBlobs(World world, int x, int y, int z, Random rand) {
		
		for(int i = 0; i < 3; i++) {
			EntityShrapnel frag = new EntityShrapnel(world);
			frag.setLocationAndAngles(x + 0.5, y + 1.5, z + 0.5, 0.0F, 0.0F);
			frag.motionY = 1D + rand.nextDouble();
			frag.motionX = rand.nextGaussian() * 0.2D;
			frag.motionZ = rand.nextGaussian() * 0.2D;
			frag.setVolcano(true);
			world.spawnEntityInWorld(frag);
		}
	}
	
	/*
	 * I SEE SMOKE, AND WHERE THERE'S SMOKE THERE'S FIRE!
	 */
	private void spawnSmoke(World world, int x, int y, int z, Random rand) {
		NBTTagCompound dPart = new NBTTagCompound();
		dPart.setString("type", "vanillaExt");
		dPart.setString("mode", "volcano");
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, x + 0.5, y + 10, z + 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 10, z + 0.5, 250));
	}
	
	private void updateVolcano(World world, int x, int y, int z, Random rand, int meta) {
		
		if(rand.nextDouble() < this.getProgressChance(world, x, y, z, rand, meta)) {
			
			//if there's progress, check if the volcano can grow or not
			if(shouldGrow(world, x, y, z, rand, meta)) {
				
				//raise the level for growing volcanos, spawn lava, schedule update at the new position
				y++;
				world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
				
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j <= 1; j++) {
						for(int k = -1; k <= 1; k++) {
							
							if(i + j + k == 0) {
								world.setBlock(x, y, z, this, meta, 3);
							} else {
								world.setBlock(x + i, y + j, z + k, ModBlocks.volcanic_lava_block);
							}
						}
					}
				}
				
			//a progressing volcano that can't grow will extinguish
			} else if(isExtinguishing(meta)) {
				world.setBlock(x, y, z, ModBlocks.volcanic_lava_block);
			}
			
		//if there's no progress, schedule an update on the current position
		}
		
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	public static final int META_STATIC_ACTIVE = 0;
	public static final int META_STATIC_EXTINGUISHING = 1;
	public static final int META_GROWING_ACTIVE = 2;
	public static final int META_GROWING_EXTINGUISHING = 3;
	
	public static boolean isGrowing(int meta) {
		return meta == META_GROWING_ACTIVE || meta == META_GROWING_EXTINGUISHING;
	}
	
	public static boolean isExtinguishing(int meta) {
		return meta == META_STATIC_EXTINGUISHING || meta == META_GROWING_EXTINGUISHING;
	}
	
	private boolean shouldGrow(World world, int x, int y, int z, Random rand, int meta) {
		
		//non-growing volcanoes should extinguish
		if(!isGrowing(meta))
			return false;
		
		//growing volcanoes extinguish when exceeding 200 blocks
		return y < 200;
	}
	
	private double getProgressChance(World world, int x, int y, int z, Random rand, int meta) {

		if(meta == META_STATIC_EXTINGUISHING)
			return 0.00003D; //about once every hour
		
		if(isGrowing(meta)) {
			
			if(meta != META_GROWING_ACTIVE || y < 199)
				return 0.007D; //about 250x an hour
		}
		
		return 0;
	}
}
