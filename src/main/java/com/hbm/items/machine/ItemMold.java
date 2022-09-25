package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemMold extends Item {
	
	public List<Mold> molds = new ArrayList();
	
	public ItemMold() {
		this.molds.add(new Mold(0, 0, "nugget", MaterialShapes.NUGGET));
		this.molds.add(new Mold(1, 0, "billet", MaterialShapes.BILLET));
		this.molds.add(new Mold(2, 0, "ingot", MaterialShapes.INGOT));
		this.molds.add(new Mold(3, 0, "plate", MaterialShapes.PLATE));
		this.molds.add(new Mold(4, 0, "wire", MaterialShapes.WIRE, 8));
		this.molds.add(new Mold(5, 1, "ingots", MaterialShapes.INGOT, 9));
		this.molds.add(new Mold(6, 1, "plates", MaterialShapes.PLATE, 9));
		this.molds.add(new Mold(7, 1, "block", MaterialShapes.BLOCK));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < molds.size(); i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	protected IIcon[] icons;

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		icons = new IIcon[molds.size()];
		
		for(int i = 0; i < molds.size(); i++) {
			Mold mold = molds.get(i);
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":mold_" + mold.name);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[Math.abs(meta % icons.length)];
	}
	
	/*@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = Math.abs(stack.getItemDamage() % molds.size());
		return super.getUnlocalizedName(stack) + "_" + molds.get(meta).name;
	}*/
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		Mold mold = getMold(stack);
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("shape." + mold.shape.name().toLowerCase()) + " x" + mold.amount);
		
		if(mold.size == 0) list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey(ModBlocks.foundry_mold.getUnlocalizedName() + ".name"));
		if(mold.size == 1) list.add(EnumChatFormatting.RED + I18nUtil.resolveKey(ModBlocks.foundry_basin.getUnlocalizedName() + ".name"));
	}
	
	public Mold getMold(ItemStack stack) {
		int meta = Math.abs(stack.getItemDamage() % molds.size());
		Mold mold = molds.get(meta);
		return mold;
	}

	public class Mold {
		
		public MaterialShapes shape;
		public int amount;
		public int id;
		public int size;
		public String name;
		
		public Mold(int id, int size, String name, MaterialShapes shape) {
			this(id, size, name, shape, 1);
		}
		
		public Mold(int id, int size, String name, MaterialShapes shape, int amount) {
			this.id = id;
			this.size = size;
			this.shape = shape;
			this.amount = amount;
			this.name = name;
		}
		
		public int getCost() {
			return shape.q(amount);
		}
	}
}
