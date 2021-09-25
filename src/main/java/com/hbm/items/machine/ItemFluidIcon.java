package com.hbm.items.machine;

import java.text.NumberFormat;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.hbm.handler.FluidTypeHandler.FluidHazards;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.HbmCollection;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemFluidIcon extends Item {
	
	IIcon overlayIcon;

    public ItemFluidIcon()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < FluidType.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
	@Spaghetti("Yikes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(stack.hasTagCompound())
			if(stack.getTagCompound().getInteger("fill") > 0)
				list.add(NumberFormat.getInstance().format(stack.getTagCompound().getInteger("fill")) + "mB");
		
		FluidType fluid = FluidType.getEnum(stack.getItemDamage());
		final String tColor = fluid.temperature > 0 ? EnumChatFormatting.RED.toString() : EnumChatFormatting.BLUE.toString();
		final ImmutableSet<FluidHazards> fHazards = fluid.getHazardSet();
		if (fluid.temperature != 0 || fHazards.contains(FluidHazards.HOT) || fHazards.contains(FluidHazards.CRYO))
			list.add(tColor + NumberFormat.getInstance().format(fluid.temperature) + "°C");
		if (fluid.isAntimatter())
			list.add(I18nUtil.resolveKey(HbmCollection.antimatter));
		if (fHazards.contains(FluidHazards.CORROSIVE_STRONG) && fHazards.contains(FluidHazards.CORROSIVE))
			list.add(I18nUtil.resolveKey(HbmCollection.corrosiveStrong));
		else if (!fHazards.contains(FluidHazards.CORROSIVE_STRONG) && fHazards.contains(FluidHazards.CORROSIVE))
			list.add(I18nUtil.resolveKey(HbmCollection.corrosive));
		if (fHazards.contains(FluidHazards.BIOHAZARD))
			list.add(I18nUtil.resolveKey(HbmCollection.biohazard));
		if (fHazards.contains(FluidHazards.CHEMICAL))
			list.add(I18nUtil.resolveKey(HbmCollection.chemical));
		if (fHazards.contains(FluidHazards.RADIOACTIVE))
			list.add(I18nUtil.resolveKey(HbmCollection.radioactiveFluid));
		if (fHazards.contains(FluidHazards.TOXIC))
			list.add(I18nUtil.resolveKey(HbmCollection.toxicGeneric));
	}
	
	public static ItemStack addQuantity(ItemStack stack, int i) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.getTagCompound().setInteger("fill", i);
		
		return stack.copy();
	}

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = (I18n.format(FluidType.getEnum(stack.getItemDamage()).getUnlocalizedName())).trim();

        if (s != null)
        {
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
		int j = FluidType.getEnum(stack.getItemDamage()).getMSAColor();

		if (j < 0) {
			j = 16777215;
		}

		return j;
	}

}
