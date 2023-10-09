package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids.CD_Canister;
import com.hbm.inventory.fluid.Fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemCanister extends Item {

	IIcon overlayIcon;

	public ItemCanister() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		
		FluidType[] order = Fluids.getInNiceOrder();
		for(int i = 1; i < order.length; ++i) {
			FluidType type = order[i];
			
			if(type.getContainer(CD_Canister.class) != null) {
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.overlayIcon = reg.registerIcon("hbm:canister_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
		return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) {
			return 16777215;
		} else {
			
			CD_Canister canister = Fluids.fromID(stack.getItemDamage()).getContainer(CD_Canister.class);
			int j = canister == null ? -1 : canister.color;

			if(j < 0) {
				j = 16777215;
			}

			return j;
		}
	}
}
