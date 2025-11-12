package com.hbm.blocks.machine.fusion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.fusion.TileEntityFusionTorus;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFusionTorus extends BlockDummyable {
	
	public static final int[][][] layout = new int[][][] {

		new int[][] {
			new int[] {0,0,0,0,3,3,3,3,3,3,3,0,0,0,0},
			new int[] {0,0,0,3,1,1,1,1,1,1,1,3,0,0,0},
			new int[] {0,0,3,1,1,1,1,1,1,1,1,1,3,0,0},
			new int[] {0,3,1,1,1,1,1,1,1,1,1,1,1,3,0},
			new int[] {3,1,1,1,1,3,3,3,3,3,1,1,1,1,3},
			new int[] {3,1,1,1,3,3,3,3,3,3,3,1,1,1,3},
			new int[] {3,1,1,1,3,3,3,3,3,3,3,1,1,1,3},
			new int[] {3,1,1,1,3,3,3,3,3,3,3,1,1,1,3},
			new int[] {3,1,1,1,3,3,3,3,3,3,3,1,1,1,3},
			new int[] {3,1,1,1,3,3,3,3,3,3,3,1,1,1,3},
			new int[] {3,1,1,1,1,3,3,3,3,3,1,1,1,1,3},
			new int[] {0,3,1,1,1,1,1,1,1,1,1,1,1,3,0},
			new int[] {0,0,3,1,1,1,1,1,1,1,1,1,3,0,0},
			new int[] {0,0,0,3,1,1,1,1,1,1,1,3,0,0,0},
			new int[] {0,0,0,0,3,3,3,3,3,3,3,0,0,0,0},
		},
		new int[][] {
			new int[] {0,0,0,0,1,1,3,3,3,1,1,0,0,0,0},
			new int[] {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			new int[] {0,0,1,1,2,2,2,2,2,2,2,1,1,0,0},
			new int[] {0,1,1,2,1,1,1,1,1,1,1,2,1,1,0},
			new int[] {1,1,2,1,1,1,1,1,1,1,1,1,2,1,1},
			new int[] {1,1,2,1,1,3,3,3,3,3,1,1,2,1,1},
			new int[] {3,1,2,1,1,3,3,3,3,3,1,1,2,1,3},
			new int[] {3,1,2,1,1,3,3,3,3,3,1,1,2,1,3},
			new int[] {3,1,2,1,1,3,3,3,3,3,1,1,2,1,3},
			new int[] {1,1,2,1,1,3,3,3,3,3,1,1,2,1,1},
			new int[] {1,1,2,1,1,1,1,1,1,1,1,1,2,1,1},
			new int[] {0,1,1,2,1,1,1,1,1,1,1,2,1,1,0},
			new int[] {0,0,1,1,2,2,2,2,2,2,2,1,1,0,0},
			new int[] {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			new int[] {0,0,0,0,1,1,3,3,3,1,1,0,0,0,0},
		},
		new int[][] {
			new int[] {0,0,0,0,1,1,3,3,3,1,1,0,0,0,0},
			new int[] {0,0,0,1,2,2,2,2,2,2,2,1,0,0,0},
			new int[] {0,0,1,2,2,2,2,2,2,2,2,2,1,0,0},
			new int[] {0,1,2,2,2,2,2,2,2,2,2,2,2,1,0},
			new int[] {1,2,2,2,1,1,1,1,1,1,1,2,2,2,1},
			new int[] {1,2,2,2,1,3,3,3,3,3,1,2,2,2,1},
			new int[] {3,2,2,2,1,3,3,3,3,3,1,2,2,2,3},
			new int[] {3,2,2,2,1,3,3,3,3,3,1,2,2,2,3},
			new int[] {3,2,2,2,1,3,3,3,3,3,1,2,2,2,3},
			new int[] {1,2,2,2,1,3,3,3,3,3,1,2,2,2,1},
			new int[] {1,2,2,2,1,1,1,1,1,1,1,2,2,2,1},
			new int[] {0,1,2,2,2,2,2,2,2,2,2,2,2,1,0},
			new int[] {0,0,1,2,2,2,2,2,2,2,2,2,1,0,0},
			new int[] {0,0,0,1,2,2,2,2,2,2,2,1,0,0,0},
			new int[] {0,0,0,0,1,1,3,3,3,1,1,0,0,0,0},
		}
	};

	public MachineFusionTorus() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionTorus();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 4, 0, 7, 7, 7, 7 };
	}

	@Override
	public int getOffset() {
		return 7;
	}

	@Override
	public boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		for(int iy = 0; iy < 5; iy++) {
			
			int l = iy > 2 ? 4 - iy : iy;
			int[][] layer = layout[l];
			
			for(int ix = 0; ix < layer.length; ix++) {

				for(int iz = 0; iz < layer.length; iz++) {

					int ex = ix - layer.length / 2;
					int ez = iz - layer.length / 2;
					
					if(!world.getBlock(x + ex, y + iy, z + ez).canPlaceBlockAt(world, x + ex, y + iy, z + ez)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		for(int iy = 0; iy < 5; iy++) {
			
			int l = iy > 2 ? 4 - iy : iy;
			int[][] layer = layout[l];
			
			for(int ix = 0; ix < layer.length; ix++) {

				for(int iz = 0; iz < layer[0].length; iz++) {

					int ex = ix - layer.length / 2;
					int ez = iz - layer.length / 2;
					
					int meta = 0;
					
					if(iy > 0) {
						meta = ForgeDirection.UP.ordinal();
					} else if(ex < 0) {
						meta = ForgeDirection.WEST.ordinal();
					} else if(ex > 0) {
						meta = ForgeDirection.EAST.ordinal();
					} else if(ez < 0) {
						meta = ForgeDirection.NORTH.ordinal();
					} else if(ez > 0) {
						meta = ForgeDirection.SOUTH.ordinal();
					} else {
						continue;
					}
					
					if(layout[l][ix][iz] > 0)
						world.setBlock(x + ex, y + iy, z + ez, this, meta, 3);
				}
			}
		}
	}
}
