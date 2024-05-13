package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityICFController extends TileEntityTickingBase implements IEnergyReceiverMK2 {
	
	public long power;
	public int laserLength;
	
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

		HashSet<BlockPos> validCells = new HashSet();
		HashSet<BlockPos> validEmitters = new HashSet();
		HashSet<BlockPos> validCapacitors = new HashSet();
		
		for(int i = 0; i < cells.size(); i++) {
			int j = i + 1;
			
			if(cells.contains(pos.mutate(xCoord + dir.offsetX * j, yCoord, zCoord + dir.offsetZ * j))) {
				this.cellCount++;
				validCells.add(pos.clone());
			} else {
				break;
			}
		}
		
		for(BlockPos emitter : emitters) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(emitter.getX() + offset.offsetX, emitter.getY() + offset.offsetY, emitter.getZ() + offset.offsetZ);
				if(validCells.contains(pos)) { this.emitterCount++; validEmitters.add(emitter.clone()); break; }
			}
		}
		
		for(BlockPos capacitor : capacitors) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(capacitor.getX() + offset.offsetX, capacitor.getY() + offset.offsetY, capacitor.getZ() + offset.offsetZ);
				if(validEmitters.contains(pos)) { this.capacitorCount++; validCapacitors.add(capacitor.clone()); break; }
			}
		}
		
		for(BlockPos turbo : turbochargers) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(turbo.getX() + offset.offsetX, turbo.getY() + offset.offsetY, turbo.getZ() + offset.offsetZ);
				if(validCapacitors.contains(pos)) { this.turbochargerCount++; break; }
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
			
			this.networkPackNT(50);
			
			if(this.assembled) {
				for(BlockPos pos : ports) {
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						BlockPos portPos = pos.offset(dir);
						if(this.getMaxPower() > 0) this.trySubscribe(worldObj, portPos.getX(), portPos.getY(), portPos.getZ(), dir);
					}
				}
				
				if(this.power > 0) {
		
					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
					
					for(int i = 1; i < 50; i++) {
						this.laserLength = i;
						
						Block b = worldObj.getBlock(xCoord + dir.offsetX * i, yCoord, zCoord + dir.offsetZ * i);
						if(b == ModBlocks.icf) {
							TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX * (i + 8), yCoord - 3, zCoord + dir.offsetZ * (i + 8));
							if(tile instanceof TileEntityICF) {
								TileEntityICF icf = (TileEntityICF) tile;
								icf.laser += this.getPower();
								icf.maxLaser += this.getMaxPower();
								break;
							}
						}
						
						if(!b.isAir(worldObj, xCoord + dir.offsetX * i, yCoord, zCoord + dir.offsetZ * i)) {
							float hardness = b.getExplosionResistance(null);
							if(hardness < 6000) worldObj.func_147480_a(xCoord + dir.offsetX * i, yCoord, zCoord + dir.offsetZ * i, false);
							break;
						}
					}
		
					double blx = Math.min(xCoord, xCoord + dir.offsetX * laserLength) + 0.2;
					double bux = Math.max(xCoord, xCoord + dir.offsetX * laserLength) + 0.8;
					double bly = Math.min(yCoord, yCoord + dir.offsetY * laserLength) + 0.2;
					double buy = Math.max(yCoord, yCoord + dir.offsetY * laserLength) + 0.8;
					double blz = Math.min(zCoord, zCoord + dir.offsetZ * laserLength) + 0.2;
					double buz = Math.max(zCoord, zCoord + dir.offsetZ * laserLength) + 0.8;
					
					List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(blx, bly, blz, bux, buy, buz));
					
					for(Entity e : list) {
						e.attackEntityFrom(DamageSource.inFire, 50);
						e.setFire(5);
					}
					
					this.setPower(0);
				} else {
					this.laserLength = 0;
				}
				
			} else {
				this.laserLength = 0;
			}
		} else {
			
			if(this.laserLength > 0 && worldObj.rand.nextInt(5) == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				double offXZ = worldObj.rand.nextDouble() * 0.25 - 0.125;
				double offY = worldObj.rand.nextDouble() * 0.25 - 0.125;
				double dist = 0.55;
				worldObj.spawnParticle("reddust", xCoord + 0.5 + dir.offsetX * dist + rot.offsetX * offXZ, yCoord + 0.5 + offY, zCoord + 0.5 + dir.offsetZ * dist + rot.offsetZ * offXZ, 0, 0, 0);
			}
		}
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeInt(capacitorCount);
		buf.writeInt(turbochargerCount);
		buf.writeInt(laserLength);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.capacitorCount = buf.readInt();
		this.turbochargerCount = buf.readInt();
		this.laserLength = buf.readInt();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		
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
		
		nbt.setLong("power", power);
		
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
		return (long) (Math.sqrt(capacitorCount) * 2_500_000 + Math.sqrt(Math.min(turbochargerCount, capacitorCount)) * 5_000_000);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 50,
					yCoord,
					zCoord + 0.5 - 50,
					xCoord + 0.5 + 50,
					yCoord + 1,
					zCoord + 0.5 + 50
					);
		}
		
		return bb;
	}
}
