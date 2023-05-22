package com.hbm.items.food;

import java.util.List;
import java.util.Locale;

import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemCrayon extends ItemFood {

	@SideOnly(Side.CLIENT) protected IIcon overlayIcon;

	public ItemCrayon() {
		super(3, false);
		this.setHasSubtypes(true);
		this.setAlwaysEdible();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < EnumChemDye.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	protected IIcon[] icons;

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.overlayIcon = reg.registerIcon(RefStrings.MODID + ":crayon_overlay");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		Enum num = EnumUtil.grabEnumSafely(EnumChemDye.class, stack.getItemDamage());
		return super.getUnlocalizedName() + "." + num.name().toLowerCase(Locale.US);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		
		if(pass == 1) {
			EnumChemDye dye = EnumUtil.grabEnumSafely(EnumChemDye.class, stack.getItemDamage());
			return dye.color;
		}
		
		return 0xffffff;
	}
}
