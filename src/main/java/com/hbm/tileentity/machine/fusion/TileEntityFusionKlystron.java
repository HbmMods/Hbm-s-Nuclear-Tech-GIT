package com.hbm.tileentity.machine.fusion;

import java.util.Map.Entry;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.KlystronNetwork;
import com.hbm.uninos.networkproviders.KlystronNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionKlystron extends TileEntity {

	protected GenNode klystronNode;

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(klystronNode == null || klystronNode.expired) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getOpposite();
				klystronNode = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 4, yCoord + 2, zCoord + dir.offsetZ * 4, KlystronNetworkProvider.THE_PROVIDER);
				
				if(klystronNode == null) {
					klystronNode = new GenNode(KlystronNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord + dir.offsetX * 4, yCoord + 2, zCoord + dir.offsetZ * 4))
							.setConnections(new DirPos(xCoord + dir.offsetX * 5, yCoord + 2, zCoord + dir.offsetZ * 5, dir));
					
					UniNodespace.createNode(worldObj, klystronNode);
				}
				
				if(klystronNode.net != null) klystronNode.net.addProvider(this);
			}
			
			if(klystronNode != null && klystronNode.net != null) {
				KlystronNetwork net = (KlystronNetwork) klystronNode.net;
				
				if(!net.receiverEntries.isEmpty()) {
					//worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				}
			}
		}
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 4,
					xCoord + 5,
					yCoord + 5,
					zCoord + 5
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
