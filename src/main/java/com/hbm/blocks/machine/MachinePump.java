package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachinePumpBase;
import com.hbm.tileentity.machine.TileEntityMachinePumpElectric;
import com.hbm.tileentity.machine.TileEntityMachinePumpSteam;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachinePump extends BlockDummyable implements ITooltipProvider, ILookOverlay {

	public MachinePump() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) {
			if(this == ModBlocks.pump_steam) return new TileEntityMachinePumpSteam();
			if(this == ModBlocks.pump_electric) return new TileEntityMachinePumpElectric();
		}
		if(meta >= 6)  {
			if(this == ModBlocks.pump_steam) return new TileEntityProxyCombo().fluid();
			if(this == ModBlocks.pump_electric) return new TileEntityProxyCombo().fluid().power();
		}
		return null;
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

		this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX - 1, y, z - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ + 1);
		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ - 1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityMachinePumpBase)) return;

		List<String> text = new ArrayList();
		
		if(te instanceof TileEntityMachinePumpSteam) {
			TileEntityMachinePumpSteam pump = (TileEntityMachinePumpSteam) te;
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + pump.steam.getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", pump.steam.getFill()) + " / " + String.format(Locale.US, "%,d", pump.steam.getMaxFill()) + "mB");
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + pump.lps.getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", pump.lps.getFill()) + " / " + String.format(Locale.US, "%,d", pump.lps.getMaxFill()) + "mB");
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + pump.water.getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", pump.water.getFill()) + " / " + String.format(Locale.US, "%,d", pump.water.getMaxFill()) + "mB");
		}
		
		if(te instanceof TileEntityMachinePumpElectric) {
			TileEntityMachinePumpElectric pump = (TileEntityMachinePumpElectric) te;
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + String.format(Locale.US, "%,d", pump.power) + " / " + String.format(Locale.US, "%,d", pump.maxPower) + "HE");
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + pump.water.getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", pump.water.getFill()) + " / " + String.format(Locale.US, "%,d", pump.water.getMaxFill()) + "mB");
		}
		
		if(pos[1] > 70) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! ALTITUDE ! ! !");
		}
		
		if(!((TileEntityMachinePumpBase) te).onGround) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! NO VALID GROUND ! ! !");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
