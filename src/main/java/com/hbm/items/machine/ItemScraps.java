package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.inventory.material.NTMMaterial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemScraps extends Item {

	public ItemScraps() {
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(NTMMaterial mat : Mats.orderedList) {
			list.add(new ItemStack(item, 1, mat.id));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		MaterialStack contents = getMats(stack);
		
		if(contents != null) {
			list.add(contents.material.names[0] + ", " + Mats.formatAmount(contents.amount));
		}
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int layer) {
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat != null) {
			return mat.moltenColor;
		}
		
		return 0xffffff;
	}
	
	public static MaterialStack getMats(ItemStack stack) {
		
		if(stack.getItem() != ModItems.scraps) return null;
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		if(mat == null) return null;
		
		int amount = MaterialShapes.INGOT.q(1);
		
		if(stack.hasTagCompound()) {
			amount = stack.getTagCompound().getInteger("amount");
		}
		
		return new MaterialStack(mat, amount);
	}
	
	public static ItemStack create(MaterialStack stack) {
		ItemStack scrap = new ItemStack(ModItems.scraps, 1, stack.material.id);
		scrap.stackTagCompound = new NBTTagCompound();
		scrap.stackTagCompound.setInteger("amount", stack.amount);
		return scrap;
	}
}
