package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemFluidIcon extends Item {

	IIcon overlayIcon;

	public ItemFluidIcon() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		FluidType[] order = Fluids.getInNiceOrder();
		for(int i = 1; i < order.length; ++i) {
			list.add(new ItemStack(item, 1, order[i].getID()));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(stack.hasTagCompound()) {
			if(getQuantity(stack) > 0) list.add(getQuantity(stack) + "mB");
			if(getPressure(stack) > 0) {
				list.add(EnumChatFormatting.RED + "" + getPressure(stack) + "PU");
				list.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED) + "Pressurized, use compressor!");
			}
		}
		
		Fluids.fromID(stack.getItemDamage()).addInfo(list);
	}

	public static ItemStack addQuantity(ItemStack stack, int i) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.getTagCompound().setInteger("fill", i);
		return stack;
	}

	public static ItemStack addPressure(ItemStack stack, int i) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.getTagCompound().setInteger("pressure", i);
		return stack;
	}

	public static ItemStack make(FluidStack stack) {
		return make(stack.type, stack.fill, stack.pressure);
	}

	public static ItemStack make(FluidType fluid, int i) {
		return make(fluid, i, 0);
	}

	public static ItemStack make(FluidType fluid, int i, int pressure) {
		return addPressure(addQuantity(new ItemStack(ModItems.fluid_icon, 1, fluid.ordinal()), i), pressure);
	}

	public static int getQuantity(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;
		return stack.getTagCompound().getInteger("fill");
	}

	public static int getPressure(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;
		return stack.getTagCompound().getInteger("pressure");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String s = (StatCollector.translateToLocal(Fluids.fromID(stack.getItemDamage()).getConditionalName())).trim();

		if(s != null) {
			return s;
		}

		return "Unknown";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int p_82790_2_) {
		int j = Fluids.fromID(stack.getItemDamage()).getColor();

		if(j < 0) {
			j = 16777215;
		}

		return j;
	}

}
