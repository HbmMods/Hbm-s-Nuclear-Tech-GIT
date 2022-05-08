package com.hbm.items.machine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.tileentity.conductor.TileEntityFluidDuctSimple;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
		list.add("   " + I18n.format(Fluids.fromID(stack.getItemDamage()).getUnlocalizedName()));
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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f1, float f2, float f3) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityFluidDuctSimple) {

			TileEntityFluidDuctSimple duct = (TileEntityFluidDuctSimple) te;
			
			if(!world.isRemote) {
				FluidType type = Fluids.fromID(stack.getItemDamage());
				
				if (player.isSneaking()) {
					markDuctsRecursively(world, x, y, z, type);
				} else {
					duct.setType(type);
				}
			}
			
			world.markBlockForUpdate(x, y, z);

			player.swingItem();
		}
		return false;
	}

	private void markDuctsRecursively(World world, int x, int y, int z, FluidType type) {
		markDuctsRecursively(world, x, y, z, type, 64);
	}

	@Deprecated
	private void markDuctsRecursively(World world, int x, int y, int z, FluidType type, int maxRecursion) {
		TileEntity start = world.getTileEntity(x, y, z);
		
		if (!(start instanceof TileEntityFluidDuctSimple))
			return;
		
		TileEntityFluidDuctSimple startDuct = (TileEntityFluidDuctSimple) start;
		FluidType oldType = startDuct.getType();
		
		if (oldType == type)
			return; // prevent infinite loops
		
		startDuct.setType(type);

		directionLoop: for (ForgeDirection direction : ForgeDirection.values()) {
			for (int currentRecursion = 1; currentRecursion <= maxRecursion; currentRecursion++) {
				
				int nextX = x + direction.offsetX * currentRecursion;
				int nextY = y + direction.offsetY * currentRecursion;
				int nextZ = z + direction.offsetZ * currentRecursion;

				TileEntity te = world.getTileEntity(nextX, nextY, nextZ);
				if (te instanceof TileEntityFluidDuctSimple && ((TileEntityFluidDuctSimple) te).getType() == oldType) {
					
					TileEntityFluidDuctSimple nextDuct = (TileEntityFluidDuctSimple) te;
					long connectionsCount = Arrays.stream(nextDuct.connections).filter(Objects::nonNull).count(); // (o -> Objects.nonNull(o))
					
					if (connectionsCount > 1) {
						markDuctsRecursively(world, nextX, nextY, nextZ, type, maxRecursion - currentRecursion);
						continue directionLoop;
					} else {
						nextDuct.setType(type);
					}
				} else {
					break;
				}
			}
		}
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
