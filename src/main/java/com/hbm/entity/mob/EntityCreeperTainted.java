package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.ServerConfig;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityCreeperTainted extends EntityCreeper implements IRadiationImmune {

	public EntityCreeperTainted(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.isEntityAlive()) {

			if(this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0) {
				this.heal(1.0F);
			}
		}
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.tnt);
	}

	@Override
	public void func_146077_cc() {
		if(!this.worldObj.isRemote) {
			boolean griefing = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

			worldObj.newExplosion(this, posX, posY, posZ, 5.0F, false, false);

			if(griefing) {
				if(this.getPowered()) {
	
					for(int i = 0; i < 255; i++) {
						int a = rand.nextInt(15) + (int) posX - 7;
						int b = rand.nextInt(15) + (int) posY - 7;
						int c = rand.nextInt(15) + (int) posZ - 7;
						Block block = worldObj.getBlock(a, b, c);
						if(block.isNormalCube() && !block.isAir(worldObj, a, b, c)) {
							if(!ServerConfig.TAINT_TRAILS.get()) {
								worldObj.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3) + 5, 2);
							} else {
								worldObj.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3), 2);
							}
						}
					}
	
				} else {
	
					for(int i = 0; i < 85; i++) {
						int a = rand.nextInt(7) + (int) posX - 3;
						int b = rand.nextInt(7) + (int) posY - 3;
						int c = rand.nextInt(7) + (int) posZ - 3;
						Block block = worldObj.getBlock(a, b, c);
						if(block.isNormalCube() && !block.isAir(worldObj, a, b, c)) {
							if(!ServerConfig.TAINT_TRAILS.get()) {
								worldObj.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(6) + 10, 2);
							} else {
								worldObj.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3) + 4, 2);
							}
						}
					}
				}
			}

			this.setDead();
		}
	}

	public static boolean hasPosNeightbour(World world, int x, int y, int z) {
		Block b0 = world.getBlock(x + 1, y, z);
		Block b1 = world.getBlock(x, y + 1, z);
		Block b2 = world.getBlock(x, y, z + 1);
		Block b3 = world.getBlock(x - 1, y, z);
		Block b4 = world.getBlock(x, y - 1, z);
		Block b5 = world.getBlock(x, y, z - 1);
		boolean b = (b0.renderAsNormalBlock() && b0.getMaterial().isOpaque()) || (b1.renderAsNormalBlock() && b1.getMaterial().isOpaque()) || (b2.renderAsNormalBlock() && b2.getMaterial().isOpaque()) || (b3.renderAsNormalBlock() && b3.getMaterial().isOpaque()) || (b4.renderAsNormalBlock() && b4.getMaterial().isOpaque()) || (b5.renderAsNormalBlock() && b5.getMaterial().isOpaque());
		return b;
	}
}
