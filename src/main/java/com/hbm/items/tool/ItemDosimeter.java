package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDosimeter extends Item {
	
	Random rand = new Random();

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(!(entity instanceof EntityLivingBase) || world.isRemote)
			return;
		
		float x = HbmLivingProps.getRadBuf((EntityLivingBase)entity);
		
		if(world.getTotalWorldTime() % 5 == 0) {
			
			if(x > 1E-5) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 0.5)
					list.add(0);
				if(x < 1)
					list.add(1);
				if(x >= 0.5 && x < 2)
					list.add(2);
				if(x >= 1 && x >= 2)
					list.add(3);
			
				int r = list.get(rand.nextInt(list.size()));
				
				if(r > 0)
					world.playSoundAtEntity(entity, "hbm:item.geiger" + r, 1.0F, 1.0F); //TODO: rip new sounds either from BM or FO3
				
			} else if(rand.nextInt(100) == 0) {
				world.playSoundAtEntity(entity, "hbm:item.geiger"+ (1 + rand.nextInt(1)), 1.0F, 1.0F);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
			ContaminationUtil.printDosimeterData(player);
		}
		
		return stack;
	}
}
