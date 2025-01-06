package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.Fluids.CD_Gastank;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemGasTank extends Item {

	IIcon overlayIcon;
	IIcon labelIcon;

	public ItemGasTank() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		
		FluidType[] order = Fluids.getInNiceOrder();
		for(int i = 1; i < order.length; ++i) {
			FluidType type = order[i];
			
			if(type.getContainer(CD_Gastank.class) != null) {
				list.add(new ItemStack(item, 1, type.getID()));
			}
		}
	}

	public String getItemStackDisplayName(ItemStack stack) {
		String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = ("" + StatCollector.translateToLocal(Fluids.fromID(stack.getItemDamage()).getConditionalName())).trim();

		if(s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int meta) {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.overlayIcon = reg.registerIcon(RefStrings.MODID + ":gas_bottle");
		this.labelIcon = reg.registerIcon(RefStrings.MODID + ":gas_label");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
		return pass == 2 ? this.labelIcon : pass == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(damage, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) {
			return 16777215;
		} else {
			
			CD_Gastank tank = Fluids.fromID(stack.getItemDamage()).getContainer(CD_Gastank.class);
			
			if(tank == null) return 0xffffff;
			
			return pass == 1 ? tank.bottleColor : tank.labelColor;
		}
	}
}
