package com.hbm.items.special;

import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemKitCustom extends ItemKitNBT {

	@SideOnly(Side.CLIENT)
	IIcon overlay1;
	@SideOnly(Side.CLIENT)
	IIcon overlay2;
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);

		this.overlay1 = reg.registerIcon(this.getIconString() + "_1");
		this.overlay2 = reg.registerIcon(this.getIconString() + "_2");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 1 ? this.overlay1 : pass == 2 ? this.overlay2 : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 1)
			return getColor(stack, 1);
		if(pass == 2)
			return getColor(stack, 2);
		return 0xffffff;
	}
	
	public static ItemStack create(String name, String lore, int color1, int color2, ItemStack... contents) {
		ItemStack stack = new ItemStack(ModItems.kit_custom);
		
		stack.stackTagCompound = new NBTTagCompound();

		setColor(stack, color1, 1);
		setColor(stack, color2, 2);
		
		if(lore != null) ItemStackUtil.addTooltipToStack(stack, lore.split("\\$"));
		stack.setStackDisplayName(EnumChatFormatting.RESET + name);
		ItemStackUtil.addStacksToNBT(stack, contents);
		
		return stack;
	}
	
	public static void setColor(ItemStack stack, int color, int index) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("color" + index, color);
	}
	
	public static int getColor(ItemStack stack, int index) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.stackTagCompound.getInteger("color" + index);
	}
}
