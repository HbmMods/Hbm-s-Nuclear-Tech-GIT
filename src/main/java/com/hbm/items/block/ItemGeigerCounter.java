package com.hbm.items.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.util.ContaminationUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemGeigerCounter extends ItemBlock {

	public ItemGeigerCounter(Block block) {
		super(block);
	}
	
	Random rand = new Random();
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(!(entity instanceof EntityLivingBase) || world.isRemote)
			return;
		
		if(entity instanceof EntityPlayer) {
			
			if(ArmorFSB.hasFSBArmor((EntityPlayer)entity) && ((ArmorFSB)((EntityPlayer)entity).inventory.armorInventory[2].getItem()).geigerSound)
				return;
		}
		
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
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			 return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		}
		
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote && !player.isSneaking()) {
			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
			ContaminationUtil.printGeigerData(player);
		}
		
		return stack;
	}
}
