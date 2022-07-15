package com.hbm.items.machine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.hbm.handler.EnumGUI;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.IItemControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.tileentity.conductor.TileEntityFluidDuctSimple;
import com.hbm.util.ChatBuilder;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemFluidIDMulti extends Item implements IItemFluidIdentifier, IItemControlReceiver {

	IIcon overlayIcon;

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote && !player.isSneaking()) {
			FluidType primary = getType(stack, true);
			FluidType secondary = getType(stack, false);
			setType(stack, secondary, true);
			setType(stack, primary, false);
			world.playSoundAtEntity(player, "random.orb", 0.25F, 1.25F);
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.startTranslation(secondary.getUnlocalizedName()).flush(), /*MainRegistry.proxy.ID_DETONATOR*/ 7, 3000), (EntityPlayerMP) player);
		}
		
		if(world.isRemote && player.isSneaking()) {
			player.openGui(MainRegistry.instance, EnumGUI.ITEM_FLUID.ordinal(), world, 0, 0, 0);
		}
		
		return stack;
	}

	@Override
	public void receiveControl(ItemStack stack, NBTTagCompound data) {
		if(data.hasKey("primary")) {
			setType(stack, Fluids.fromID(data.getInteger("primary")), true);
		}
		if(data.hasKey("secondary")) {
			setType(stack, Fluids.fromID(data.getInteger("secondary")), false);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(I18nUtil.resolveKey(getUnlocalizedName() + ".info"));
		list.add("   " + I18n.format(getType(stack, true).getUnlocalizedName()));
		list.add(I18nUtil.resolveKey(getUnlocalizedName() + ".info2"));
		list.add("   " + I18n.format(getType(stack, false).getUnlocalizedName()));
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return stack.copy();
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	public FluidType getType(World world, int x, int y, int z, ItemStack stack) {
		return getType(stack, true);
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
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) {
			return 16777215;
		} else {
			int j = getType(stack, true).getColor();

			if(j < 0) {
				j = 16777215;
			}

			return j;
		}
	}
	
	public static void setType(ItemStack stack, FluidType type, boolean primary) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("fluid" + (primary ? 1 : 2), type.getID());
	}
	
	public static FluidType getType(ItemStack stack, boolean primary) {
		if(!stack.hasTagCompound())
			return Fluids.NONE;
		
		int type = stack.stackTagCompound.getInteger("fluid" + (primary ? 1 : 2));
		return Fluids.fromID(type);
	}
	
	/*
	 * CRAPPY COMPAT SECTION
	 */

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f1, float f2, float f3) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityFluidDuctSimple) {

			TileEntityFluidDuctSimple duct = (TileEntityFluidDuctSimple) te;
			
			if(!world.isRemote) {
				FluidType type = getType(world, x, y ,z, stack);
				
				if (player.isSneaking()) {
					markDuctsRecursively(world, x, y, z, type);
				} else {
					duct.setType(type);
				}
			}
			
			world.markBlockForUpdate(x, y, z);

			player.swingItem();
			return true;
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
}
