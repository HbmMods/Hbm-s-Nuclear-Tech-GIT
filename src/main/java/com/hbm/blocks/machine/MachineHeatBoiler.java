package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeatBoiler;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineHeatBoiler extends BlockDummyable implements ILookOverlay, ITooltipProvider, IBlockMulti {

	public MachineHeatBoiler() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityHeatBoiler();
		if(meta >= extra) return new TileEntityProxyCombo().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				int[] pos = this.findCore(world, x, y, z);
					
				if(pos == null)
					return false;
				
				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
				
				if(!(te instanceof TileEntityHeatBoiler))
					return false;
				
				TileEntityHeatBoiler boiler = (TileEntityHeatBoiler) te;
				
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, pos[0], pos[1], pos[2], player.getHeldItem());
				
				if(type.hasTrait(FT_Heatable.class) && type.getTrait(FT_Heatable.class).getEfficiency(HeatingType.BOILER) > 0) {
					boiler.tanks[0].setTankType(type);
					boiler.markDirty();
					player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
				}
				return true;
			}
			return false;
			
		} else {
			return true;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
		
		if(itemStack.getItemDamage() == 1) {

			int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			int o = -getOffset();
			
			ForgeDirection dir = ForgeDirection.NORTH;
			if(i == 0) dir = ForgeDirection.getOrientation(2);
			if(i == 1) dir = ForgeDirection.getOrientation(5);
			if(i == 2) dir = ForgeDirection.getOrientation(3);
			if(i == 3) dir = ForgeDirection.getOrientation(4);
			
			dir = getDirModified(dir);

			TileEntity te = world.getTileEntity(x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o);
			
			if(te instanceof TileEntityHeatBoiler) {
				((TileEntityHeatBoiler) te).hasExploded = true;
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		int dmg = 0;

		int[] pos = this.findCore(world, x, y, z);
		
		if(pos != null) {
			TileEntityHeatBoiler boiler = (TileEntityHeatBoiler)world.getTileEntity(pos[0], pos[1], pos[2]);
			if(boiler.hasExploded) {
				//dmg = 1;
				ret.add(new ItemStack(ModItems.ingot_steel, 4));
				ret.add(new ItemStack(ModItems.plate_copper, 8));
				return ret;
			}
		}
		
		for(int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if(item != null) {
				ret.add(new ItemStack(item, 1, dmg));
			}
		}
		return ret;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
		this.makeExtra(world, x - rot.offsetX, y, z - rot.offsetZ);
		this.makeExtra(world, x, y + 3, z);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityHeatBoiler))
			return;
		
		TileEntityHeatBoiler boiler = (TileEntityHeatBoiler) te;
		
		if(boiler.hasExploded) return;

		List<String> text = new ArrayList();
		text.add(String.format(Locale.US, "%,d", boiler.heat) + "TU");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + boiler.tanks[0].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", boiler.tanks[0].getFill()) + " / " + String.format(Locale.US, "%,d", boiler.tanks[0].getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + boiler.tanks[1].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", boiler.tanks[1].getFill()) + " / " + String.format(Locale.US, "%,d", boiler.tanks[1].getMaxFill()) + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public int getSubCount() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
