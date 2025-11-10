package com.hbm.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class BrokenItem extends Item {

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		if(pass == 1) return this.itemIcon;
		if(stack.stackTagCompound == null) return this.itemIcon;

		String id = stack.stackTagCompound.getString("itemID");
		int meta = stack.stackTagCompound.getInteger("itemMeta");
		
		Item item = (Item) Item.itemRegistry.getObject(id);
		if(item == null) return this.itemIcon;
		
		return item.getIconFromDamage(meta);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.stackTagCompound == null) return super.getItemStackDisplayName(stack);

		String id = stack.stackTagCompound.getString("itemID");
		int meta = stack.stackTagCompound.getInteger("itemMeta");
		
		Item item = (Item) Item.itemRegistry.getObject(id);
		if(item == null) return super.getItemStackDisplayName(stack);
		
		ItemStack sta = new ItemStack(item, 1, meta);
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".prefix", sta.getDisplayName());
	}
	
	public static ItemStack make(ItemStack stack) {		return make(stack.getItem(), stack.stackSize, stack.getItemDamage()); }
	public static ItemStack make(Item item) {			return make(item, 1, 0); }
	public static ItemStack make(Item item, int meta) {	return make(item, 1, meta); }
	
	public static ItemStack make(Item item, int stacksize, int meta) {
		ItemStack stack = new ItemStack(ModItems.broken_item, stacksize);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("itemID", Item.itemRegistry.getNameForObject(item));
		nbt.setInteger("itemMeta", meta);
		stack.stackTagCompound = nbt;
		return stack;
	}
}
