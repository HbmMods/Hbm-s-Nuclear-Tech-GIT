package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityICFController extends TileEntityTickingBase implements IEnergyReceiverMK2 {
	
	public long power;
	
	public int cellCount;
	public int emitterCount;
	public int capacitorCount;
	public int turbochargerCount;

	protected List<BlockPos> ports = new ArrayList();
	
	public boolean assembled;
	
	public void setup(HashSet<BlockPos> ports, HashSet<BlockPos> cells, HashSet<BlockPos> emitters, HashSet<BlockPos> capacitors, HashSet<BlockPos> turbochargers) {

		this.cellCount = 0;
		this.emitterCount = 0;
		this.capacitorCount = 0;
		this.turbochargerCount = 0;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		BlockPos pos = new BlockPos(0, 0, 0);
		
		for(int i = 0; i < cells.size(); i++) {
			int j = i + 1;
			
			if(cells.contains(pos.mutate(xCoord + dir.offsetX * j, yCoord, zCoord + dir.offsetZ * j))) {
				this.cellCount++;
			} else {
				break;
			}
		}
		
		for(BlockPos emitter : emitters) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(emitter.getX() + offset.offsetX, emitter.getY() + offset.offsetY, emitter.getZ() + offset.offsetZ);
				if(cells.contains(pos)) { this.emitterCount++; break; }
			}
		}
		
		for(BlockPos capacitor : capacitors) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(capacitor.getX() + offset.offsetX, capacitor.getY() + offset.offsetY, capacitor.getZ() + offset.offsetZ);
				if(emitters.contains(pos)) { this.capacitorCount++; break; }
			}
		}
		
		for(BlockPos turbo : turbochargers) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(turbo.getX() + offset.offsetX, turbo.getY() + offset.offsetY, turbo.getZ() + offset.offsetZ);
				if(capacitors.contains(pos)) { this.turbochargerCount++; break; }
			}
		}
		
		this.ports.addAll(ports);
	}

	@Override
	public String getInventoryName() {
		return "container.icfController";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.assembled) {
				for(BlockPos pos : ports) {
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						BlockPos portPos = pos.offset(dir);
						if(this.getMaxPower() > 0) this.trySubscribe(worldObj, portPos.getX(), portPos.getY(), portPos.getZ(), dir);
					}
				}
			}
			
			this.networkPackNT(50);
		}
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeInt(capacitorCount);
		buf.writeInt(turbochargerCount);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.capacitorCount = buf.readInt();
		this.turbochargerCount = buf.readInt();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.assembled = nbt.getBoolean("assembled");
		this.cellCount = nbt.getInteger("cellCount");
		this.emitterCount = nbt.getInteger("emitterCount");
		this.capacitorCount = nbt.getInteger("capacitorCount");
		this.turbochargerCount = nbt.getInteger("turbochargerCount");
		
		ports.clear();
		int portCount = nbt.getInteger("portCount");
		for(int i = 0; i < portCount; i++) {
			int[] port = nbt.getIntArray("p" + i);
			ports.add(new BlockPos(port[0], port[1], port[2]));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("assembled", assembled);
		nbt.setInteger("cellCount", cellCount);
		nbt.setInteger("emitterCount", emitterCount);
		nbt.setInteger("capacitorCount", capacitorCount);
		nbt.setInteger("turbochargerCount", turbochargerCount);
		
		nbt.setInteger("portCount", ports.size());
		for(int i = 0; i < ports.size(); i++) {
			BlockPos pos = ports.get(i);
			nbt.setIntArray("p" + i, new int[] { pos.getX(), pos.getY(), pos.getZ() });
		}
	}

	@Override
	public long getPower() {
		return Math.min(power, this.getMaxPower());
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return capacitorCount * 1_000_000 + turbochargerCount * 2_500_000; //TEMP
	}
}
