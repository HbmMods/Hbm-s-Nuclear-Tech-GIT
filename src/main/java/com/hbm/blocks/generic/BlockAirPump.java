package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CBT_Atmosphere.FluidEntry;
import com.hbm.handler.atmosphere.IBlockSealable;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityAirPump;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

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

public class BlockAirPump extends BlockContainer implements ILookOverlay, IBlockSealable {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockAirPump(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":vent_chlorine_seal_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":vent_chlorine_seal_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		return side == 1 ? this.iconTop : this.blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityAirPump();
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(!(tile instanceof TileEntityAirPump)) return;
		
		TileEntityAirPump pump = (TileEntityAirPump) tile;
		
		CBT_Atmosphere atmosphere = pump.currentAtmosphere;

		List<String> text = new ArrayList<>();

		text.add(I18nUtil.resolveKey("hbmfluid." + pump.tank.getTankType().getName().toLowerCase()) + ": " + pump.tank.getFill() + "/" + pump.tank.getMaxFill() + "mB");

		if(pump.tank.getFill() <= 10) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.noTank") + " ! ! !");
		} else if(!pump.hasSeal()) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.noSeal") + " ! ! !");
		}

		text.add(I18nUtil.resolveKey("atmosphere.name") + ": ");

		boolean hasPressure = false;

		if(atmosphere != null) {
			for(FluidEntry entry : atmosphere.fluids) {
				if(entry.pressure > 0.01) {
					double pressure = BobMathUtil.roundDecimal(entry.pressure, 3);
					text.add(EnumChatFormatting.AQUA + " - " + I18nUtil.resolveKey("hbmfluid." + entry.fluid.getName().toLowerCase()) + " - " + pressure + "atm");
					hasPressure = true;
				}
			}
		}

		if(!hasPressure) {
			text.add(EnumChatFormatting.AQUA + " - " + I18nUtil.resolveKey("atmosphere.vacuum"));
		}
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public boolean isSealed(World world, int x, int y, int z) {
		return false;
	}
}

