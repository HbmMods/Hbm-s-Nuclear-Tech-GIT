package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemZirnoxRod extends ItemEnumMulti {

	public ItemZirnoxRod() {
		super(EnumZirnoxType.class, true, true);
		this.setMaxStackSize(1);
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("life", time);
	}
	
	public static int getLifeTime(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("life");
	}
    
    public boolean showDurabilityBar(ItemStack stack) {
        return getDurabilityForDisplay(stack) > 0D;
    }
    
    public double getDurabilityForDisplay(ItemStack stack) {
    	EnumZirnoxType num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
        return (double)getLifeTime(stack) / (double)num.maxLife;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		
		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + num.name().toLowerCase());
		}
	}
    
    @Override
	public String getUnlocalizedName(ItemStack stack) {
		Enum num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		return super.getUnlocalizedName() + "_" + num.name().toLowerCase();
	}
    
	public static enum EnumZirnoxType {
		NATURAL_URANIUM_FUEL(250_000, 30),
		URANIUM_FUEL(200_000, 50),
		TH232(20_000, 0),
		THORIUM_FUEL(200_000, 40),
		MOX_FUEL(165_000, 75),
		PLUTONIUM_FUEL(175_000, 65),
		U233_FUEL(150_000, 100),
		U235_FUEL(165_000, 85),
		LES_FUEL(150_000, 150),
		LITHIUM(20_000, 0),
		ZFB_MOX(50_000, 35);
		
		public int maxLife;
		public int heat;
		
		private EnumZirnoxType(int life, int heat) {
			this.maxLife = life;
			this.heat = heat;
		}
	}
}
