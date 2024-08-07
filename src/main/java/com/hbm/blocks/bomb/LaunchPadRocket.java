package com.hbm.blocks.bomb;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.bomb.TileEntityLaunchPadRocket;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class LaunchPadRocket extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public LaunchPadRocket(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLaunchPadRocket();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return new TileEntityProxyCombo().inventory();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 7, 6, 6, 6};
	}

	@Override
	public int getOffset() {
		return 6;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		// Main body
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, 0, 6, 6, 4, 4}, this, dir);
		
		MultiblockHandlerXR.fillSpace(world, x - dir.offsetX * 2, y, z - dir.offsetZ * 2, new int[] {2, 0, 4, 0, 6, 6}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * 2, y, z + dir.offsetZ * 2, new int[] {2, 0, 0, 4, 6, 6}, this, dir);
		
		// Inputs
		BlockDummyable.safeRem = true;
		for(int or = 1; or < 5; or++) {
			for(int oy = 0; oy < 3; oy++) {
				world.setBlock(x - rot.offsetX * or - dir.offsetX * 7, y + oy, z - rot.offsetZ * or - dir.offsetZ * 7, this, dir.getOpposite().ordinal() + extra, 3);
			}
		}
		world.setBlock(x + rot.offsetX * 3 - dir.offsetX * 7, y, z + rot.offsetZ * 3 - dir.offsetZ * 7, this, dir.getOpposite().ordinal() + extra, 3);
		BlockDummyable.safeRem = false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityLaunchPadRocket))
			return;
		
		TileEntityLaunchPadRocket pad = (TileEntityLaunchPadRocket) te;

		if(pad.rocket == null) return;

		if(y - pos[1] > 2) return; // Don't show tooltip on support tower

		List<String> text = new ArrayList<String>();
		text.add("Required fuels:");

		for(int i = 0; i < pad.tanks.length; i++) {
			FluidTank tank = pad.tanks[i];
			if(tank.getTankType() == Fluids.NONE) continue;
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + tank.getTankType().getLocalizedName() + ": " + tank.getFill() + "/" + tank.getMaxFill() + "mB");
		}

		if(pad.maxSolidFuel > 0) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + I18nUtil.resolveKey(ModItems.rocket_fuel.getUnlocalizedName() + ".name") + ": " + pad.solidFuel + "/" + pad.maxSolidFuel + "kg");
		}

		if(text.size() <= 1) return;

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
	
}
