package com.hbm.tileentity.machine.fusion;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionCollector extends TileEntity {

	protected GenNode plasmaNode;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(plasmaNode == null || plasmaNode.expired) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getOpposite();
				plasmaNode = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 2, yCoord + 2, zCoord + dir.offsetZ * 2, PlasmaNetworkProvider.THE_PROVIDER);
				
				if(plasmaNode == null) {
					plasmaNode = new GenNode(PlasmaNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord + dir.offsetX * 2, yCoord + 2, zCoord + dir.offsetZ * 2))
							.setConnections(new DirPos(xCoord + dir.offsetX * 3, yCoord + 2, zCoord + dir.offsetZ * 3, dir));
					
					UniNodespace.createNode(worldObj, plasmaNode);
				}
			}
			
			if(plasmaNode != null && plasmaNode.hasValidNet()) plasmaNode.net.addReceiver(this);
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.plasmaNode != null) UniNodespace.destroyNode(worldObj, plasmaNode);
		}
	}
	
	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 4,
					zCoord + 3
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
