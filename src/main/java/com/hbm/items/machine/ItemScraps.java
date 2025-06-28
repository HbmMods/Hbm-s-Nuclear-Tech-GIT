package com.hbm.items.machine;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemAutogen;
import com.hbm.lib.RefStrings;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemScraps extends ItemAutogen {
	
	@SideOnly(Side.CLIENT) public IIcon liquidIcon;
	@SideOnly(Side.CLIENT) public IIcon addiviceIcon;

	public ItemScraps() {
		super(null);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.liquidIcon = reg.registerIcon(RefStrings.MODID + ":scraps_liquid");
		this.addiviceIcon = reg.registerIcon(RefStrings.MODID + ":scraps_additive");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.smeltable == SmeltingBehavior.SMELTABLE || mat.smeltable == SmeltingBehavior.ADDITIVE) {
				list.add(new ItemStack(item, 1, mat.id));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int layer) {
		
		if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("liquid")) {
			
			NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
			
			if(mat != null) {
				return mat.moltenColor;
			}
		}
		
		return super.getColorFromItemStack(stack, layer);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		
		if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("liquid")) {
			
			NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
			
			if(mat != null) {
				if(mat.smeltable == mat.smeltable.SMELTABLE) return this.liquidIcon;
				if(mat.smeltable == mat.smeltable.ADDITIVE) return this.addiviceIcon;
			}
		}
		
		return this.getIconFromDamage(stack.getItemDamage());
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("liquid")) {
			MaterialStack contents = getMats(stack);
			if(contents != null) {
				return I18nUtil.resolveKey(contents.material.getUnlocalizedName());
			}
		}
		
		return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		MaterialStack contents = getMats(stack);
		
		if(contents != null) {
			
			if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("liquid")) {
				list.add(Mats.formatAmount(contents.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
				if(contents.material.smeltable == contents.material.smeltable.ADDITIVE) list.add(EnumChatFormatting.DARK_RED + "Additive, not castable!");
			} else {
				list.add(I18nUtil.resolveKey(contents.material.getUnlocalizedName()) + ", " + Mats.formatAmount(contents.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
			}
		}
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
		return create(stack, false);
	}
	
	public static ItemStack create(MaterialStack stack, boolean liquid) {
		if(stack.material == null)
			return new ItemStack(ModItems.nothing); //why do i bother adding checks for fucking everything when they don't work
		ItemStack scrap = new ItemStack(ModItems.scraps, 1, stack.material.id);
		scrap.stackTagCompound = new NBTTagCompound();
		scrap.stackTagCompound.setInteger("amount", stack.amount);
		if(liquid) scrap.stackTagCompound.setBoolean("liquid", true);
		return scrap;
	}
}
