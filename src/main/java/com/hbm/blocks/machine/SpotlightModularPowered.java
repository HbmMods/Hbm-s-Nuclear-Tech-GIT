package com.hbm.blocks.machine;

import com.hbm.blocks.BlockEnums.LightType;
import com.hbm.blocks.ModBlocks;

import api.hbm.energymk2.IEnergyReceiverMK2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SpotlightModularPowered extends SpotlightPowered {
    
    public SpotlightModularPowered(Material mat, int beamLength, LightType type, long powerConsumption, boolean isOn) {
		super(mat, beamLength, type, powerConsumption, isOn);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySpotlightModularPowered();
	}

    @Override
    public String getPartName(int connectionCount) {
        if (connectionCount == 0) return "FluoroSingle";
        if (connectionCount == 1) return "FluoroCap";
        return "FluoroMid";
    }

    public boolean canConnectTo(IBlockAccess world, int x, int y, int z, int myX, int myY, int myZ) {
        if(world.getBlock(x, y, z) != this) return false;
        
        // Check if both lights are mounted in the same direction (parallel)
        int myMeta = world.getBlockMetadata(myX, myY, myZ);
        int theirMeta = world.getBlockMetadata(x, y, z);
        
        ForgeDirection myMount = ForgeDirection.getOrientation(myMeta >> 1);
        ForgeDirection theirMount = ForgeDirection.getOrientation(theirMeta >> 1);
        
        return myMount == theirMount;
    }

	public static class TileEntitySpotlightModularPowered extends TileEntitySpotlightPowered {

		public TileEntitySpotlightModularPowered() {
			super();
			this.powerConsumption = 20;
		}

		@Override
		public void updateEntity() {
			if(!worldObj.isRemote) {
				ForgeDirection dir = getDirection().getOpposite();
				this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

				Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
				if(!(block instanceof SpotlightPowered)) return;
				
				SpotlightPowered spotlight = (SpotlightPowered) block;
				this.powerConsumption = spotlight.powerConsumption;

				sharePowerWithConnected();

				if(power >= powerConsumption) {
					power -= powerConsumption;
					if(!spotlight.isOn) {
						switchToState(true);
					}
				} else {
					if(spotlight.isOn) {
						switchToState(false);
					}
				}
			}
		}

		private void sharePowerWithConnected() {
			if(power <= 0) return;
			
			ForgeDirection mountDir = getDirection();
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(dir == mountDir || dir == mountDir.getOpposite()) continue;
				
				int x = xCoord + dir.offsetX;
				int y = yCoord + dir.offsetY;
				int z = zCoord + dir.offsetZ;
				
				Block adjacentBlock = worldObj.getBlock(x, y, z);
				if(adjacentBlock == ModBlocks.spotlight_fluoro_powered || adjacentBlock == ModBlocks.spotlight_fluoro_powered_off) {
					int adjacentMeta = worldObj.getBlockMetadata(x, y, z);
					ForgeDirection adjacentMountDir = ForgeDirection.getOrientation(adjacentMeta >> 1);
					
					if(adjacentMountDir == mountDir) {
						TileEntity te = worldObj.getTileEntity(x, y, z);
						if(te instanceof TileEntitySpotlightModularPowered) {
							TileEntitySpotlightModularPowered adjacent = (TileEntitySpotlightModularPowered) te;
							if(adjacent.power < adjacent.maxPower && this.power > 0) {
								long transfer = Math.min(this.power / 2, adjacent.maxPower - adjacent.power);
								if(transfer > 0) {
									this.power -= transfer;
									adjacent.power += transfer;
									adjacent.markDirty();
								}
							}
						}
					}
				}
			}
		}
		
		private ForgeDirection getDirection() {
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			return ForgeDirection.getOrientation(metadata >> 1);
		}
	}
}
