package com.hbm.tileentity.deco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.items.ModItems;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTrappedBrick extends TileEntity {
	
	AxisAlignedBB detector = null;
	ForgeDirection dir = ForgeDirection.UNKNOWN;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(detector == null) {
				setDetector();
			}
			
			List players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, detector);
			
			if(!players.isEmpty())
				trigger();
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	private void trigger() {
		
		Trap trap = Trap.get(this.getBlockMetadata());
		
		switch(trap) {
		case FALLING_ROCKS:
			for(int x = 0; x < 3; x++) {
				for(int z = 0; z < 3; z++) {
					EntityRubble rubble = new EntityRubble(worldObj, xCoord - 0.5 + x, yCoord - 0.5, zCoord - 0.5 + z);
					rubble.setMetaBasedOnBlock(ModBlocks.reinforced_stone, 0);
					worldObj.spawnEntityInWorld(rubble);
				}
			}
			break;
		case ARROW:
			EntityArrow arrow = new EntityArrow(worldObj);
			arrow.setPosition(xCoord + 0.5 + dir.offsetX, yCoord + 0.5, zCoord + 0.5 + dir.offsetZ);
			arrow.motionX = dir.offsetX;
			arrow.motionZ = dir.offsetZ;
			worldObj.spawnEntityInWorld(arrow);
			break;
		case FLAMING_ARROW:
			EntityArrow farrow = new EntityArrow(worldObj);
			farrow.setPosition(xCoord + 0.5 + dir.offsetX, yCoord + 0.5, zCoord + 0.5 + dir.offsetZ);
			farrow.motionX = dir.offsetX;
			farrow.motionZ = dir.offsetZ;
			farrow.setFire(60);
			worldObj.spawnEntityInWorld(farrow);
			break;
		case PILLAR:
			for(int i = 0; i < 3; i++)
				worldObj.setBlock(xCoord, yCoord - 1 - i, zCoord, ModBlocks.concrete_pillar);
			break;
		case POISON_DART:
			//TBI
			break;
		case ZOMBIE:
			EntityZombie zombie = new EntityZombie(worldObj);
			zombie.setPosition(xCoord + 0.5, yCoord + 1, zCoord + 0.5);
			switch(worldObj.rand.nextInt(3)) {
			case 0: zombie.setCurrentItemOrArmor(0, new ItemStack(ModItems.chernobylsign)); break;
			case 1: zombie.setCurrentItemOrArmor(0, new ItemStack(ModItems.cobalt_sword)); break;
			case 2: zombie.setCurrentItemOrArmor(0, new ItemStack(ModItems.cmb_hoe)); break;
			}
			zombie.setEquipmentDropChance(0, 1.0F);
			worldObj.spawnEntityInWorld(zombie);
			break;
		case SPIDERS:
			for(int i = 0; i < 3; i++) {
				EntityCaveSpider spider = new EntityCaveSpider(worldObj);
				spider.setPosition(xCoord + 0.5, yCoord - 1, zCoord + 0.5);
				worldObj.spawnEntityInWorld(spider);
			}
			break;
		}

		worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.click", 0.3F, 0.6F);
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.brick_jungle);
	}
	
	@SuppressWarnings("incomplete-switch")
	private void setDetector() {
		
		Trap trap = Trap.get(this.getBlockMetadata());
		
		switch(trap) {
		case FALLING_ROCKS:
			detector = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 3, zCoord - 1, xCoord + 2, yCoord, zCoord + 2);
			return;
			
		case PILLAR: 
			detector = AxisAlignedBB.getBoundingBox(xCoord + 0.2, yCoord - 3, zCoord + 0.2, xCoord + 0.8, yCoord, zCoord + 0.8);
			return;
			
		case ARROW:
		case FLAMING_ARROW:
		case POISON_DART:
			setDetectorDirectional();
			return;
			
		case ZOMBIE:
			detector = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord + 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
			return;
			
		case SPIDERS:
			detector = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 3, zCoord - 1, xCoord + 2, yCoord, zCoord + 2);
			return;
		}
		
		detector = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
	
	private void setDetectorDirectional() {
		
		List<ForgeDirection> dirs = new ArrayList() {{
			add(ForgeDirection.NORTH);
			add(ForgeDirection.SOUTH);
			add(ForgeDirection.EAST);
			add(ForgeDirection.WEST);
		}};
		
		Collections.shuffle(dirs);
		
		for(ForgeDirection dir : dirs) {
			
			if(worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ) == Blocks.air) {

				double minX = xCoord + 0.4;
				double minY = yCoord + 0.4;
				double minZ = zCoord + 0.4;
				double maxX = xCoord + 0.6;
				double maxY = yCoord + 0.6;
				double maxZ = zCoord + 0.6;
				
				if(dir.offsetX > 0)
					maxX += 3;
				else if(dir.offsetX < 0)
					minX -= 3;
				
				if(dir.offsetZ > 0)
					maxZ += 3;
				else if(dir.offsetZ < 0)
					minZ -= 3;
				
				detector = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
				this.dir = dir;
				return;
			}
		}
		
		detector = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
}
