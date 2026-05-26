package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageAccess;
import com.hbm.inventory.gui.GUIPneumoStorageAccess;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube.PneumaticNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.ntl.IPneumaticConnector;
import api.hbm.ntl.StackCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPneumoStorageAccess extends TileEntityLoadedBase implements IPneumaticConnector, IGUIProvider {
	
	protected PneumaticNode node;
	public StackCache cache;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.node == null || this.node.expired) {
				if(this.cache != null) this.cache.dissolveCache();
				
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
				
				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setStandardConnections(xCoord, yCoord, zCoord);
					UniNodespace.createNode(worldObj, this.node);
				}
			}
			
			if(this.cache == null) {
				this.cache = new StackCache(xCoord, yCoord, zCoord);
			}
			
			if(this.node != null && this.node.hasValidNet()) {
				this.node.net.addStackCache(cache);
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
		}
		
		if(this.cache != null) this.cache.dissolveCache();
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(!worldObj.isRemote && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
		}
		
		if(this.cache != null) this.cache.dissolveCache();
	}

	@Override
	public boolean canConnectPneumatic(ForgeDirection dir) {
		ForgeDirection selfdir = ForgeDirection.getOrientation(getBlockMetadata());
		return dir == selfdir.getOpposite();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPneumoStorageAccess(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPneumoStorageAccess(player.inventory, this); }
}
