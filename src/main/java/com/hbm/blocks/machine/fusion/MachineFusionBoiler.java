package com.hbm.blocks.machine.fusion;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionBoiler;
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

public class MachineFusionBoiler extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineFusionBoiler() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionBoiler();
		if(meta >= 6) return new TileEntityProxyCombo().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 4, 4, 1, 1 };
	}

	@Override
	public int getOffset() {
		return 4;
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		//this.makeExtra(world, x + dir.offsetX * 4, y + 2, z + dir.offsetZ * 4);
		this.makeExtra(world, x - dir.offsetX * 1 + rot.offsetX, y, z - dir.offsetZ * 1 + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 1 - rot.offsetX, y, z - dir.offsetZ * 1 - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 2 + rot.offsetX, y, z + dir.offsetZ * 2 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 2 - rot.offsetX, y, z + dir.offsetZ * 2 - rot.offsetZ);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityFusionBoiler)) return;
		TileEntityFusionBoiler boiler = (TileEntityFusionBoiler) te;
		
		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + BobMathUtil.format(boiler.plasmaEnergy) + " TU");

		for(int i = 0; i < boiler.getAllTanks().length; i++) {
			FluidTank tank = boiler.getAllTanks()[i];
			text.add((i == 0 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + tank.getTankType().getLocalizedName() + ": " + tank.getFill() + "/" + tank.getMaxFill() + "mB");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
