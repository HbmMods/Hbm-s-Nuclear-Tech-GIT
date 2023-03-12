package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemPlateCast extends Item {

	public ItemPlateCast() {
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, Mats.MAT_IRON.id));
		list.add(new ItemStack(item, 1, Mats.MAT_STEEL.id));
		list.add(new ItemStack(item, 1, Mats.MAT_COPPER.id));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int layer) {
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat != null) {
			return mat.solidColor;
		}
		
		return 0xffffff;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		
		if(mat == null) {
			return "UNDEFINED";
		}
		
		String matName = I18nUtil.resolveKey(mat.getUnlocalizedName());
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", matName);
	}
	
	public static MaterialStack getMats(ItemStack stack) {
		
		if(stack.getItem() != ModItems.plate_cast) return null;
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		if(mat == null) return null;
		
		int amount = MaterialShapes.INGOT.q(1);
		
		if(stack.hasTagCompound()) {
			amount = stack.getTagCompound().getInteger("amount");
		}
		
		return new MaterialStack(mat, amount);
	}
	
	public static ItemStack create(MaterialStack stack) {
		if(stack.material == null)
			return new ItemStack(ModItems.nothing); //why do i bother adding checks for fucking everything when they don't work
		ItemStack scrap = new ItemStack(ModItems.plate_cast, 1, stack.material.id);
		scrap.stackTagCompound = new NBTTagCompound();
		scrap.stackTagCompound.setInteger("amount", stack.amount);
		return scrap;
	}
}
