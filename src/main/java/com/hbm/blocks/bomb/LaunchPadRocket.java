package com.hbm.blocks.bomb;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.bomb.TileEntityLaunchPadRocket;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class LaunchPadRocket extends BlockDummyable implements ILookOverlay {

	public LaunchPadRocket(Material mat) {
		super(mat);
		this.bounding.add(AxisAlignedBB.getBoundingBox(-4.5D, 0D, -4.5D, 4.5D, 1D, -0.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-4.5D, 0D, 0.5D, 4.5D, 1D, 4.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-4.5D, 0.875D, -0.5D, 4.5D, 1D, 0.5D));
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
		return new int[] {0, 0, 4, 4, 4, 4};
	}

	@Override
	public int getOffset() {
		return 4;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 4, y, z + 2);
		this.makeExtra(world, x + 4, y, z - 2);
		this.makeExtra(world, x - 4, y, z + 2);
		this.makeExtra(world, x - 4, y, z - 2);
		this.makeExtra(world, x + 2, y, z + 4);
		this.makeExtra(world, x - 2, y, z + 4);
		this.makeExtra(world, x + 2, y, z - 4);
		this.makeExtra(world, x - 2, y, z - 4);
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
	
}
