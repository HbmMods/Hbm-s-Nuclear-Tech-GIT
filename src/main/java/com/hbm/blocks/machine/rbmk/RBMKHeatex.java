package com.hbm.blocks.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.rbmk.TileEntityHeatex;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;
import com.hbm.util.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class RBMKHeatex extends BlockContainer implements ILookOverlay {

	public RBMKHeatex(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityHeatex();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {
		
		if(world.isRemote)
			return true;
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) player.getHeldItem().getItem();
			FluidType type = id.getType(world, x, y, z, player.getHeldItem());
			FluidType convert = TileEntityRBMKHeater.getConversion(type);
			
			if(!player.isSneaking() && convert != Fluids.NONE) {
				
				TileEntity te = world.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityHeatex) {
					TileEntityHeatex heatex = (TileEntityHeatex) te;
					heatex.coolantIn.setTankType(convert);
					heatex.coolantOut.setTankType(type);
					heatex.markDirty();
					player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation("hbmfluid." + type.getName().toLowerCase())).appendSibling(new ChatComponentText("!")));
				}
			}
		}
		
		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityHeatex))
			return;
		
		TileEntityHeatex extractor = (TileEntityHeatex) te;
		
		List<String> text = new ArrayList();
		addLine(text, extractor.coolantIn, true);
		addLine(text, extractor.waterIn, true);
		addLine(text, extractor.coolantOut, false);
		addLine(text, extractor.waterOut, false);
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	private void addLine(List<String> text, FluidTank tank, boolean in) {
		text.add((in ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + tank.getTankType().getName().toLowerCase()) + ": " + tank.getFill() + "/" + tank.getMaxFill() + "mB");
	}
}
