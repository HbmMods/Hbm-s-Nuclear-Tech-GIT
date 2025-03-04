package com.hbm.blocks.bomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityCreeperTainted;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTaint extends Block implements ITooltipProvider {

	public BlockTaint(Material mat) {
		super(mat);
		this.setTickRandomly(true);
	}

	@Override public MapColor getMapColor(int meta) { return MapColor.grayColor; }
	@Override public Item getItemDropped(int i, Random rand, int j) { return null; }

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 15) return;
		
		for(int i = -3; i <= 3; i++) for(int j = -3; j <= 3; j++) for(int k = -3; k <= 3; k++) {
			if(Math.abs(i) + Math.abs(j) + Math.abs(k) > 4) continue;
			if(rand.nextFloat() > 0.25F) continue;
			Block b = world.getBlock(x + i, y + j, z + k);
			if(!b.isNormalCube() || b.isAir(world, x + i, y + j, z + k)) continue;
			int targetMeta = meta + 1;
			boolean hasAir = false;
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(world.getBlock(x + i + dir.offsetX, y + j + dir.offsetY, z + k + dir.offsetZ).isAir(world, x + i + dir.offsetX, y + j + dir.offsetY, z + k + dir.offsetZ)) {
					hasAir = true;
					break;
				}
			}
			if(!hasAir) targetMeta = meta + 3;
			if(targetMeta > 15) continue;
			if(b == this && world.getBlockMetadata(x + i, y + j, z + k) >= targetMeta) continue;
			world.setBlock(x + i, y + j, z + k, this, targetMeta, 3);
			if(rand.nextFloat() < 0.25F && BlockFalling.func_149831_e(world, x + i, y + j - 1, z + k)) {
				EntityFallingBlock falling = new EntityFallingBlock(world, x + i + 0.5, y + j + 0.5, z + k + 0.5, this, targetMeta);
				world.spawnEntityInWorld(falling);
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.75, z + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		
		int meta = world.getBlockMetadata(x, y, z);
		int level = 15 - meta;

		entity.motionX *= 0.6;
		entity.motionZ *= 0.6;

		List<ItemStack> list = new ArrayList<ItemStack>();
		PotionEffect effect = new PotionEffect(HbmPotion.taint.id, 15 * 20, level);
		effect.setCurativeItems(list);

		if(entity instanceof EntityLivingBase) {
			if(world.rand.nextInt(50) == 0) {
				((EntityLivingBase) entity).addPotionEffect(effect);
			}
		}

		if(entity != null && entity.getClass().equals(EntityCreeper.class)) {
			EntityCreeperTainted creep = new EntityCreeperTainted(world);
			creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

			if(!world.isRemote) {
				entity.setDead();
				world.spawnEntityInWorld(creep);
			}
		}

		if(entity instanceof EntityTeslaCrab) {
			EntityTaintCrab crab = new EntityTaintCrab(world);
			crab.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

			if(!world.isRemote) {
				entity.setDead();
				world.spawnEntityInWorld(crab);
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("DO NOT TOUCH, BREATHE OR STARE AT.");
	}
}
