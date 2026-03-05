package com.hbm.blocks.machine.albion;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.albion.TileEntityPABeamline;

import api.hbm.block.IToolable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPABeamline extends BlockDummyable implements ITooltipProvider, IToolable {

	public BlockPABeamline() {
		super(Material.iron);
		this.setCreativeTab(MainRegistry.machineTab);
		this.setBlockTextureName(RefStrings.MODID + ":block_steel");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityPABeamline();
		return null;
	}

	@Override public int[] getDimensions() { return new int[] {0, 0, 0, 0, 1, 1}; }
	@Override public int getOffset() { return 0; }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		if(world.isRemote) return true;
		
		int[] pos = this.findCore(world, x, y, z);
		if(pos != null) {
			TileEntityPABeamline tile = (TileEntityPABeamline) world.getTileEntity(pos[0], pos[1], pos[2]);
			tile.window = !tile.window;
			tile.markDirty();
		}
		
		return false;
	}
}
