package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import scala.Int;

public class ItemGeigerCounter extends Item {
	
	Random rand = new Random();

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		setInt(stack, getInt(stack, "timer") + 1, "timer");
		if(getInt(stack, "timer") == 10) {
			setInt(stack, 0, "timer");
			setInt(stack, check(world, (int)entity.posX, (int)entity.posY, (int)entity.posZ, 15), "ticker");
		}
		
		int x = getInt(stack, "ticker");
		
		if(getInt(stack, "timer") % 5 == 0) {
			if(x > 0) {
				List<Integer> list = new ArrayList<Integer>();
			
				if(x < 30)
					list.add(1);
				if(x > 10 && x < 40)
					list.add(2);
				if(x > 30 && x < 60)
					list.add(3);
				if(x > 50 && x < 80)
					list.add(4);
				if(x > 70 && x < 100)
					list.add(5);
				if(x > 90)
					list.add(6);
			
				world.playSoundAtEntity(entity, "hbm:item.geiger" + list.get(rand.nextInt(list.size())), 1.0F, 1.0F);
			} else if(rand.nextInt(50) == 0) {
				world.playSoundAtEntity(entity, "hbm:item.geiger"+ (1 + rand.nextInt(1)), 1.0F, 1.0F);
			}
		}
	}
	
	static void setInt(ItemStack stack, int i, String name) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger(name, i);
	}
	
	static int getInt(ItemStack stack, String name) {
		if(stack.hasTagCompound())
			return stack.stackTagCompound.getInteger(name);
		
		return 0;
	}

	public static int check(World world, int x, int y, int z, int radius) {
		int rads = 0;
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if(ZZ < r22 / 3) {
						rads += getRad(world.getBlock(X, Y, Z), 3);
					} else if(ZZ < r22 / 3 * 2) {
						rads += getRad(world.getBlock(X, Y, Z), 2);
					} else if(ZZ < r22) {
						rads += getRad(world.getBlock(X, Y, Z), 1);
					}
				}
			}
		}
		
		return rads;
	}
	
	//level 1: farthest, 3: nearest
	public static int getRad(Block b, int level) {
		int i = 0;

		if(b == ModBlocks.waste_trinitite) {
			i = 3;
		}
		if(b == ModBlocks.waste_trinitite_red) {
			i = 3;
		}
		if(b == ModBlocks.ore_uranium) {
			i = 2;
		}
		if(b == ModBlocks.ore_nether_plutonium) {
			i = 15;
		}
		if(b == ModBlocks.block_trinitite) {
			i = 20;
		}
		if(b == ModBlocks.block_waste) {
			i = 25;
		}
		if(b == ModBlocks.waste_earth) {
			i = 2;
		}
		if(b == ModBlocks.waste_mycelium) {
			i = 7;
		}
		if(b == ModBlocks.block_uranium) {
			i = 10;
		}
		if(b == ModBlocks.yellow_barrel) {
			i = 30;
		}
		
		return i * level;
	}
	
    @Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2)
    {
    	if(world.getBlock(x, y, z) == ModBlocks.block_red_copper) {
    		player.inventory.consumeInventoryItem(ModItems.geiger_counter);
    		player.inventory.addItemStackToInventory(new ItemStack(ModItems.survey_scanner));
    		return true;
    	}
    	
    	return false;
    }

}
