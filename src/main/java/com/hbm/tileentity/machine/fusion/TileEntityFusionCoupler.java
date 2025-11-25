package com.hbm.tileentity.machine.fusion;

import java.util.Map.Entry;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.KlystronNetwork;
import com.hbm.uninos.networkproviders.KlystronNetworkProvider;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionCoupler extends TileEntityLoadedBase implements IFusionPowerReceiver {

	protected GenNode klystronNode;
	protected GenNode plasmaNode;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getOpposite();
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			// christ on the cross why didn't i just make a baseclass to shove this crap into
			if(klystronNode == null || klystronNode.expired) {
				klystronNode = UniNodespace.getNode(worldObj, xCoord + rot.offsetX, yCoord + 2, zCoord + rot.offsetZ, KlystronNetworkProvider.THE_PROVIDER);
				
				if(klystronNode == null) {
					klystronNode = new GenNode(KlystronNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord + rot.offsetX, yCoord + 2, zCoord + rot.offsetZ))
							.setConnections(new DirPos(xCoord + rot.offsetX * 2, yCoord + 2, zCoord + rot.offsetZ * 2, rot));
					
					UniNodespace.createNode(worldObj, klystronNode);
				}
			}
			
			if(plasmaNode == null || plasmaNode.expired) {
				plasmaNode = UniNodespace.getNode(worldObj, xCoord - rot.offsetX, yCoord + 2, zCoord - rot.offsetZ, PlasmaNetworkProvider.THE_PROVIDER);
				
				if(plasmaNode == null) {
					plasmaNode = new GenNode(PlasmaNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord - rot.offsetX, yCoord + 2, zCoord - rot.offsetZ))
							.setConnections(new DirPos(xCoord - rot.offsetX * 2, yCoord + 2, zCoord - rot.offsetZ * 2, rot.getOpposite()));
					
					UniNodespace.createNode(worldObj, plasmaNode);
				}
			}
			
			if(klystronNode.net != null) klystronNode.net.addProvider(this);
			if(plasmaNode.net != null) plasmaNode.net.addReceiver(this);
		}
	}

	@Override public boolean receivesFusionPower() { return true; }

	@Override
	public void receiveFusionPower(long fusionPower, double neutronPower) {
		
		// more copy pasted crap code ! ! !
		if(klystronNode != null && klystronNode.net != null) {
			KlystronNetwork net = (KlystronNetwork) klystronNode.net;
			
			for(Object o : net.receiverEntries.entrySet()) {
				Entry e = (Entry) o;
				if(e.getKey() instanceof TileEntityFusionTorus) {
					TileEntityFusionTorus torus = (TileEntityFusionTorus) e.getKey();
					
					if(torus.isLoaded() && !torus.isInvalid()) {
						torus.klystronEnergy += fusionPower;
						break;
					}
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.klystronNode != null) UniNodespace.destroyNode(worldObj, klystronNode);
			if(this.plasmaNode != null) UniNodespace.destroyNode(worldObj, plasmaNode);
		}
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 4,
					zCoord + 2
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
