package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

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
			if(stack.getTagCompound().getInteger("fill") > 0)
				list.add(stack.getTagCompound().getInteger("fill") + "mB");
		}
		
		Fluids.fromID(stack.getItemDamage()).addInfo(list);
	}

	public static ItemStack addQuantity(ItemStack stack, int i) {

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.getTagCompound().setInteger("fill", i);

		return stack;
	}

	public static ItemStack make(FluidStack stack) {
		return make(stack.type, stack.fill);
	}

	public static ItemStack make(FluidType fluid, int i) {
		return addQuantity(new ItemStack(ModItems.fluid_icon, 1, fluid.ordinal()), i);
	}

	public static int getQuantity(ItemStack stack) {

		if(!stack.hasTagCompound())
			return 0;

		return stack.getTagCompound().getInteger("fill");
	}

	public String getItemStackDisplayName(ItemStack stack) {
		String s = (I18n.format(Fluids.fromID(stack.getItemDamage()).getUnlocalizedName())).trim();

		if(s != null) {
			return s;
		}

		return "Unknown";
	}

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public boolean requiresMultipleRenderPasses() {
	 * return true; }
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public void registerIcons(IIconRegister
	 * p_94581_1_) { super.registerIcons(p_94581_1_);
	 * 
	 * this.overlayIcon =
	 * p_94581_1_.registerIcon("hbm:fluid_identifier_overlay"); }
	 * 
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public IIcon getIconFromDamageForRenderPass(int
	 * p_77618_1_, int p_77618_2_) { return p_77618_2_ == 1 ? this.overlayIcon :
	 * super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_); }
	 */

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
