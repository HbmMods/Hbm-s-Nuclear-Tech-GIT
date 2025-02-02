package com.hbm.blocks.machine.albion;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.albion.TileEntityPADetector;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPADetector extends BlockDummyable implements ITooltipProvider {

	public BlockPADetector() {
		super(Material.iron);
		this.setCreativeTab(MainRegistry.machineTab);
		this.setBlockTextureName(RefStrings.MODID + ":block_steel");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityPADetector();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {2, 2, 2, 2, 4, 4}; }
	@Override public int getOffset() { return 0; }
	@Override public int getHeightOffset() { return 2; }

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - rot.offsetX * 4, y, z - rot.offsetZ * 4);
		this.makeExtra(world, x - rot.offsetX * 4, y + 1, z - rot.offsetZ * 4);
		this.makeExtra(world, x - rot.offsetX * 4, y - 1, z - rot.offsetZ * 4);
		this.makeExtra(world, x - rot.offsetX * 4 + dir.offsetX, y, z - rot.offsetZ * 4 + dir.offsetZ);
		this.makeExtra(world, x - rot.offsetX * 4 - dir.offsetX, y, z - rot.offsetZ * 4 - dir.offsetZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
