package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineGasFlare extends BlockDummyable implements ITooltipProvider {

	public MachineGasFlare(Material mat) {
		super(mat);

		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 0D, -1.5D, 1.5D, 3.875D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.75D, 3.875D, -0.75D, 0.75D, 9, 0.75D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 9D, -1.5D, 1.5D, 9.375D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.75D, 9.375D, -0.75D, 0.75D, 12, 0.75D));
		this.maxY = 0.999D; //item bounce prevention
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityMachineGasFlare();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {11, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o);
		this.makeExtra(world, x + dir.offsetX * o, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o, y, z + dir.offsetZ * o - 1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		list.add(EnumChatFormatting.GOLD + "Can burn fluids and vent gasses");
		list.add(EnumChatFormatting.GOLD + "Burns up to " + EnumChatFormatting.RED + "10mB/t");
		list.add(EnumChatFormatting.GOLD + "Vents up to " + EnumChatFormatting.RED + "50mB/t");
		list.add("");
		list.add(EnumChatFormatting.YELLOW + "Fuel efficiency:");
		list.add(EnumChatFormatting.YELLOW + "-Flammable Gasses: " + EnumChatFormatting.RED + "20%");
		list.add(EnumChatFormatting.YELLOW + "-Flammable Liquids: " + EnumChatFormatting.RED + "10%");
	}
}
