package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageAccess;
import com.hbm.inventory.gui.GUIPneumoStorageAccess;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.ntl.IPneumaticConnector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

// throwing the towel - for now. there's a test i could run, but it's a lot of work and will likely just confirm my suspicions about performance
// this demands another sidequest: fucking multi threading
public class TileEntityPneumoStorageAccess extends TileEntity implements IPneumaticConnector, IGUIProvider {
	
	protected PneumaticNode node;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.node == null || this.node.expired) {
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
				
				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setConnections(
							new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
							new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
							new DirPos(xCoord, yCoord + 1, zCoord, Library.POS_Y),
							new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
							new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
							new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z)
							);
					UniNodespace.createNode(worldObj, this.node);
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.node != null) {
				UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
			}
		}
	}

	@Override
	public boolean canConnectPneumatic(ForgeDirection dir) {
		ForgeDirection selfdir = ForgeDirection.getOrientation(getBlockMetadata());
		return dir == selfdir.getOpposite();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPneumoStorageAccess(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPneumoStorageAccess(player.inventory, this); }
}
