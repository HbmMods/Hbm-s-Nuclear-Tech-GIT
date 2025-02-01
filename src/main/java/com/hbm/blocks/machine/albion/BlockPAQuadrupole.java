package com.hbm.blocks.machine.albion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.albion.TileEntityPAQuadrupole;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPAQuadrupole extends BlockDummyable {

	public BlockPAQuadrupole() {
		super(Material.iron);
		this.setCreativeTab(MainRegistry.machineTab);
		this.setBlockTextureName(RefStrings.MODID + ":block_steel");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityPAQuadrupole();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {1, 1, 1, 1, 1, 1}; }
	@Override public int getOffset() { return 0; }
	@Override public int getHeightOffset() { return 1; }

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x + dir.offsetX, y, z + dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ);
		this.makeExtra(world, x, y + 1, z);
		this.makeExtra(world, x, y - 1, z);
	}
}
