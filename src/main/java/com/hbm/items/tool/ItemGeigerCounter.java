package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemGeigerCounter extends Item {
	
	Random rand = new Random();

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(entity instanceof EntityPlayer) {
			
			if(ArmorFSB.hasFSBArmor((EntityPlayer)entity) && ((ArmorFSB)((EntityPlayer)entity).inventory.armorInventory[2].getItem()).geigerSound)
				return;
		}
		
		setInt(stack, getInt(stack, "timer") + 1, "timer");
		if(getInt(stack, "timer") == 10) {
			setInt(stack, 0, "timer");
			setInt(stack, check(world, (int)entity.posX, (int)entity.posY, (int)entity.posZ), "ticker");
		}
		
		int x = getInt(stack, "ticker");
		
		if(getInt(stack, "timer") % 5 == 0) {
			if(x > 0) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1)
					list.add(0);
				if(x < 5)
					list.add(0);
				if(x < 10)
					list.add(1);
				if(x > 5 && x < 15)
					list.add(2);
				if(x > 10 && x < 20)
					list.add(3);
				if(x > 15 && x < 25)
					list.add(4);
				if(x > 20 && x < 30)
					list.add(5);
				if(x > 25)
					list.add(6);
			
				int r = list.get(rand.nextInt(list.size()));
				
				if(r > 0)
					world.playSoundAtEntity(entity, "hbm:item.geiger" + r, 1.0F, 1.0F);
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
	
	public static int getInt(ItemStack stack, String name) {
		if(stack.hasTagCompound())
			return stack.stackTagCompound.getInteger(name);
		
		return 0;
	}

	public static int check(World world, int x, int y, int z) {
		
		RadiationSavedData data = RadiationSavedData.getData(world);
		
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		int rads = (int)Math.ceil(data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition));
		
		return rads;
	}
	
	//what?!
    /*@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2)
    {
    	if(world.getBlock(x, y, z) == ModBlocks.block_red_copper) {
    		world.func_147480_a(x, y, z, false);
    		player.inventory.consumeInventoryItem(ModItems.geiger_counter);
    		player.inventory.addItemStackToInventory(new ItemStack(ModItems.survey_scanner));
    		return true;
    	}
    	
    	return false;
    }*/

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
	    	world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
	    	ContaminationUtil.printGeigerData(player);
		}
		
		return stack;
	}

}
