package com.hbm.items.machine;

import java.util.List;
import java.util.Locale;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemDrillbit extends ItemEnumMulti {

	public ItemDrillbit() {
		super(EnumDrillType.class, true, true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		
		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + num.name().toLowerCase(Locale.US));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		Enum num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		return super.getUnlocalizedName() + "_" + num.name().toLowerCase(Locale.US);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		EnumDrillType type = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());

		list.add(EnumChatFormatting.YELLOW + "Speed: " + ((int) (type.speed * 100)) + "%");
		list.add(EnumChatFormatting.YELLOW + "Tier: " + type.tier);
		if(type.fortune > 0) list.add(EnumChatFormatting.LIGHT_PURPLE + "Fortune " + type.fortune);
		if(type.vein) list.add(EnumChatFormatting.GREEN + "Vein miner");
		if(type.silk) list.add(EnumChatFormatting.GREEN + "Silk touch");
	}
	
	public static enum EnumDrillType {
		STEEL			(1.0D, 1, 0, false, false),
		STEEL_DIAMOND	(1.0D, 1, 2, false, true),
		HSS				(1.2D, 2, 0, true, false),
		HSS_DIAMOND		(1.2D, 2, 3, true, true),
		DESH			(1.5D, 3, 1, true, true),
		DESH_DIAMOND	(1.5D, 3, 4, true, true),
		TCALLOY			(2.0D, 4, 1, true, true),
		TCALLOY_DIAMOND	(2.0D, 4, 4, true, true),
		FERRO			(2.5D, 5, 1, true, true),
		FERRO_DIAMOND	(2.5D, 5, 4, true, true),
		CMB				(3.0D, 6, 2, true, true),
		CMB_DIAMOND		(3.0D, 6, 5, true, true);
		
		public double speed;
		public int tier;
		public int fortune;
		public boolean vein;
		public boolean silk;
		
		private EnumDrillType(double speed, int tier, int fortune, boolean vein, boolean silk) {
			this.speed = speed;
			this.tier = tier;
			this.fortune = fortune;
			this.vein = vein;
			this.silk = silk;
		}
	}
}
