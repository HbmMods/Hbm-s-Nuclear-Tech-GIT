package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGeigerCounter extends Item {
	
	Random rand = new Random();

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(!(entity instanceof EntityLivingBase) || world.isRemote)
			return;
		
		float x = HbmLivingProps.getRadBuf((EntityLivingBase)entity);
		
		if(world.getTotalWorldTime() % 5 == 0) {
			if(x > 1E-5) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1) list.add(0);
				if(x < 5) list.add(0);
				if(x < 10) list.add(1);
				if(x > 5 && x < 15) list.add(2);
				if(x > 10 && x < 20) list.add(3);
				if(x > 15 && x < 25) list.add(4);
				if(x > 20 && x < 30) list.add(5);
				if(x > 25) list.add(6);
			
				int r = list.get(rand.nextInt(list.size()));
				
				if(r > 0)
					world.playSoundAtEntity(entity, "hbm:item.geiger" + r, 1.0F, 1.0F);
			} else if(rand.nextInt(50) == 0) {
				world.playSoundAtEntity(entity, "hbm:item.geiger"+ (1 + rand.nextInt(1)), 1.0F, 1.0F);
			}
		}
	}
	
	static void setFloat(ItemStack stack, float i, String name) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setFloat(name, i);
	}
	
	public static float getFloat(ItemStack stack, String name) {
		if(stack.hasTagCompound())
			return stack.stackTagCompound.getFloat(name);
		
		return 0;
	}

	public static int check(World world, int x, int y, int z) {
		int rads = (int)Math.ceil(ChunkRadiationManager.proxy.getRadiation(world, x, y, z));
		return rads;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
			ContaminationUtil.printGeigerData(player);
		}
		
		return stack;
	}
}
