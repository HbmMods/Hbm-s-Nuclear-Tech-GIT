package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFluidIdentifier extends Item implements IItemFluidIdentifier {

	IIcon overlayIcon;

	public ItemFluidIdentifier() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public ItemStack getContainerItem(ItemStack stack) {
		return stack.copy();
	}

	public boolean hasContainerItem() {
		return true;
	}

	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		FluidType[] order = Fluids.getInNiceOrder();
		for(int i = 1; i < order.length; ++i) {
			if(!order[i].hasNoID()) {
				list.add(new ItemStack(item, 1, order[i].getID()));
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(!(stack.getItem() instanceof ItemFluidIdentifier))
			return;

		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("info.templatefolder", I18nUtil.resolveKey(ModItems.template_folder.getUnlocalizedName() + ".name")));
		list.add("");
		list.add(I18nUtil.resolveKey(getUnlocalizedName() + ".info"));
		list.add("   " + Fluids.fromID(stack.getItemDamage()).getLocalizedName());
		list.add("");
		list.add(I18nUtil.resolveKey(getUnlocalizedName() + ".usage0"));
		list.add(I18nUtil.resolveKey(getUnlocalizedName() + ".usage1"));
		list.add(I18nUtil.resolveKey(getUnlocalizedName() + ".usage2"));
	}

	public static FluidType getType(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof ItemFluidIdentifier)
			return Fluids.fromID(stack.getItemDamage());
		else
			return Fluids.NONE;
	}

	@Override
	public FluidType getType(World world, int x, int y, int z, ItemStack stack) {
		return Fluids.fromID(stack.getItemDamage());
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);

		this.overlayIcon = p_94581_1_.registerIcon("hbm:fluid_identifier_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
		return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int p_82790_2_) {
		if(p_82790_2_ == 0) {
			return 16777215;
		} else {
			int j = Fluids.fromID(stack.getItemDamage()).getColor();

			if(j < 0) {
				j = 16777215;
			}

			return j;
		}
	}
}
