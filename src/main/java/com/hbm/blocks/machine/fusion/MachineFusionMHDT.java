package com.hbm.blocks.machine.fusion;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionMHDT;
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

public class MachineFusionMHDT extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineFusionMHDT() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionMHDT();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 2, 0, 6, 7, 2, 2 };
	}

	@Override
	public int getOffset() {
		return 7;
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, 6, 2, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, -6, 7, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -2, -3, 5, 2, 2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -3, -3, 5, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * (o + 3), y, z + dir.offsetZ * (o + 3), new int[] {1, 0, 0, 1, 3, 3}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {3, -2, 6, 2, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {3, -2, -6, 7, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {3, -2, -3, 5, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {4, -3, -3, 5, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * (o + 3), y, z + dir.offsetZ * (o + 3), new int[] {1, 0, 0, 1, 3, 3}, this, dir);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * 4 + rot.offsetX * 3, y, z + dir.offsetZ * 4 + rot.offsetZ * 3);
		this.makeExtra(world, x + dir.offsetX * 4 - rot.offsetX * 3, y, z + dir.offsetZ * 4 - rot.offsetZ * 3);
		this.makeExtra(world, x + dir.offsetX * 7, y + 1, z + dir.offsetZ * 7);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityFusionMHDT)) return;
		TileEntityFusionMHDT turbine = (TileEntityFusionMHDT) te;
		
		boolean isCool = turbine.isCool();
		
		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(turbine.plasmaEnergy) + " TU/t");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(!isCool ? 0 : (long) Math.floor(turbine.plasmaEnergy * turbine.PLASMA_EFFICIENCY)) + "HE/t");

		for(int i = 0; i < turbine.getAllTanks().length; i++) {
			FluidTank tank = turbine.getAllTanks()[i];
			text.add((i == 0 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + tank.getTankType().getLocalizedName() + ": " + tank.getFill() + "/" + tank.getMaxFill() + "mB");
		}
		
		if(!isCool) text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! INSUFFICIENT COOLING ! ! !");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
