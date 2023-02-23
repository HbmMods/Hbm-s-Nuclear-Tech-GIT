package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.container.ContainerBook;
import com.hbm.inventory.gui.GUIBook;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBook extends Item implements IGUIProvider {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add("Edition 4, gold lined pages");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote)
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		
		return stack;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerBook(player.inventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBook(player.inventory);
	}

	/*@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		if(!player.isSneaking()) {
			List list = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX - 10, player.posY - 2, player.posZ - 10, player.posX + 10, player.posY + 2, player.posZ + 10));
			
			for(Object o : list) {
				
				if(o instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase)o;
					
					entity.addPotionEffect(new PotionEffect(HbmPotion.telekinesis.id, 20, 0));
				}
			}
		} else {
			if(player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 1)) &&
					player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 2)) &&
					player.inventory.hasItemStack(new ItemStack(ModItems.ingot_u238m2, 1, 3))) {
				player.inventory.clearInventory(ModItems.ingot_u238m2, 1);
				player.inventory.clearInventory(ModItems.ingot_u238m2, 2);
				player.inventory.clearInventory(ModItems.ingot_u238m2, 3);
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ingot_u238m2));
				player.inventoryContainer.detectAndSendChanges();
			}
		}
		
		return stack;
	}*/

}
