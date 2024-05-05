package com.hbm.items.machine;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemICFPellet extends Item {
	
	protected IIcon iconBG;

	public ItemICFPellet() {
		this.setMaxStackSize(1);
	}
	
	public static long getMaxDepletion(ItemStack stack) {
		if(!stack.hasTagCompound()) return 10_000_000_000L;
		//TODO: type-dependent
		return 50_000_000_000L;
	}
	
	public static long getDepletion(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0L;
		return stack.stackTagCompound.getLong("depletion");
	}
	
	public static long react(ItemStack stack, long heat) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		//TODO: type-dependent
		stack.stackTagCompound.setLong("depletion", stack.stackTagCompound.getLong("depletion") + heat);
		return heat * 2;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double) getDepletion(stack) / (double) getMaxDepletion(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.iconBG = reg.registerIcon(RefStrings.MODID + ":icf_pellet_bg");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 1 ? super.getIconFromDamageForRenderPass(meta, pass) : this.iconBG;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) return 0x4040ff;
		return 0xffffff;
	}
}
