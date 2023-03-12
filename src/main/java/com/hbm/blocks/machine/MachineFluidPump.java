package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFluidPump;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.inventory.fluid.FluidType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;

public class MachineFluidPump extends BlockContainer implements ILookOverlay, ITooltipProvider {

	public MachineFluidPump(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		meta = 0;
		return new TileEntityFluidPump();
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconBottom;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fluid_pump_side");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":fluid_pump_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":fluid_pump_bottom");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 0) {
			return iconBottom;
		} else if (side == 1) {
			return iconTop;
		} else {
			return blockIcon;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				
				TileEntity te = world.getTileEntity(x, y, z);
				
				if(!(te instanceof TileEntityFluidPump))
					return false;
				
				TileEntityFluidPump pump = (TileEntityFluidPump) te;
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, x, y, z, player.getHeldItem());
				pump.tanks[0].setTankType(type);
				pump.markDirty();
				player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation("hbmfluid." + type.getName().toLowerCase())).appendSibling(new ChatComponentText("!")));
				
				return true;
			}
			return false;
			
		} else {
			return true;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityFluidPump))
			return;
		
		TileEntityFluidPump pump = (TileEntityFluidPump) te;
		
		List<String> text = new ArrayList();
		text.add((pump.power < pump.getMaxPower() / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(pump.power) + "HE");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + pump.tanks[0].getTankType().getName().toLowerCase()) + ": " + pump.tanks[0].getFill() + "/" + pump.tanks[0].getMaxFill() + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}