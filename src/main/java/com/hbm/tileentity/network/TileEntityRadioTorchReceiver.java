package com.hbm.tileentity.network;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;

import net.minecraft.util.MathHelper;

public class TileEntityRadioTorchReceiver extends TileEntityRadioTorchBase {

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(!this.channel.isEmpty()) {
				
				RTTYChannel chan = RTTYSystem.listen(worldObj, this.channel);
				
				if(chan != null && (this.polling || (chan.timeStamp > this.lastUpdate - 1 && chan.timeStamp != -1))) { // if we're either polling or a new message has come in
					String msg = "" + chan.signal;
					this.lastUpdate = worldObj.getTotalWorldTime();
					int nextState = 0; //if no remap apply, default to 0
					
					if("selfdestruct".equals(msg)) {
						worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
						ExplosionVNT vnt = new ExplosionVNT(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, null);
						vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 50).setupPiercing(5F, 0.5F));
						vnt.setPlayerProcessor(new PlayerProcessorStandard());
						vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
						vnt.explode();
						return;
					}
					
					if(this.customMap) {
						for(int i = 15; i >= 0; i--) { // highest to lowest, if duplicates exist for some reason
							if(msg.equals(this.mapping[i])) {
								nextState = i;
								break;
							}
						}
					} else {
						int sig = 0;
						try { sig = Integer.parseInt(msg); } catch(Exception x) { };
						nextState = MathHelper.clamp_int(sig, 0, 15);
					}
					
					if(chan.timeStamp < this.lastUpdate - 2 && this.polling) {
						nextState = 0;
					}
					
					if(this.lastState != nextState) {
						this.lastState = nextState;
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
						this.markDirty();
					}
				}
			}
		}
		
		super.updateEntity();
	}
}
