package com.hbm.packet.toserver;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.items.weapon.ItemCustomMissilePart.PartSize;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.hbm.tileentity.machine.TileEntityForceField;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;
import com.hbm.tileentity.machine.storage.TileEntityMachineBattery;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

@Deprecated //use the NBT control packet instead
public class AuxButtonPacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int id;

	public AuxButtonPacket()
	{
		
	}

	public AuxButtonPacket(int x, int y, int z, int value, int id)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readInt();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(value);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<AuxButtonPacket, IMessage> {

		@SuppressWarnings("incomplete-switch")
		@Override
		public IMessage onMessage(AuxButtonPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			//try {
				TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);
				
				if (te instanceof TileEntityForceField) {
					TileEntityForceField field = (TileEntityForceField)te;
					
					field.isOn = !field.isOn;
				}
				
				if (te instanceof TileEntityMachineMissileAssembly) {
					TileEntityMachineMissileAssembly assembly = (TileEntityMachineMissileAssembly)te;
					
					assembly.construct();
				}
				
				if (te instanceof TileEntityLaunchTable) {
					TileEntityLaunchTable launcher = (TileEntityLaunchTable)te;
					
					launcher.padSize = PartSize.values()[m.value];
				}
				
				if (te instanceof TileEntityCoreEmitter) {
					TileEntityCoreEmitter core = (TileEntityCoreEmitter)te;

					if(m.id == 0) {
						core.watts = m.value;
					}
					if(m.id == 1) {
						core.isOn = !core.isOn;
					}
				}
				
				if (te instanceof TileEntityCoreStabilizer) {
					TileEntityCoreStabilizer core = (TileEntityCoreStabilizer)te;

					if(m.id == 0) {
						core.watts = m.value;
					}
				}
				
				if (te instanceof TileEntityBarrel) {
					TileEntityBarrel barrel = (TileEntityBarrel)te;

					barrel.mode = (short) ((barrel.mode + 1) % barrel.modes);
					barrel.markDirty();
				}
				
				if (te instanceof TileEntityMachineBattery) {
					TileEntityMachineBattery bat = (TileEntityMachineBattery)te;

					if(m.id == 0) {
						bat.redLow = (short) ((bat.redLow + 1) % 4);
						bat.markDirty();
					}

					if(m.id == 1) {
						bat.redHigh = (short) ((bat.redHigh + 1) % 4);
						bat.markDirty();
					}

					if(m.id == 2) {
						switch(bat.priority) {
						case LOW: bat.priority = ConnectionPriority.NORMAL; break;
						case NORMAL: bat.priority = ConnectionPriority.HIGH; break;
						case HIGH: bat.priority = ConnectionPriority.LOW; break;
						}
						bat.markDirty();
					}
				}
				
				if (te instanceof TileEntitySoyuzLauncher) {
					TileEntitySoyuzLauncher launcher = (TileEntitySoyuzLauncher)te;

					if(m.id == 0)
						launcher.mode = (byte) m.value;
					if(m.id == 1)
						launcher.startCountdown();
				}
				
				if (te instanceof TileEntityMachineMiningLaser) {
					TileEntityMachineMiningLaser laser = (TileEntityMachineMiningLaser)te;

					laser.isOn = !laser.isOn;
				}
				
				/// yes ///
				//no fuck off
				if(te instanceof TileEntityMachineBase) {
					TileEntityMachineBase base = (TileEntityMachineBase)te;
					base.handleButtonPacket(m.value, m.id);
				}
				if(te instanceof TileEntityTickingBase) {
					TileEntityTickingBase base = (TileEntityTickingBase)te;
					base.handleButtonPacket(m.value, m.id);
				}
				
				//why make new packets when you can just abuse and uglify the existing ones?
				if(te == null && m.value == 999) {
					
					NBTTagCompound perDat = p.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
					
					if(MobConfig.enableDucks && !perDat.getBoolean("hasDucked")) {
						EntityDuck ducc = new EntityDuck(p.worldObj);
						ducc.setPosition(p.posX, p.posY + p.eyeHeight, p.posZ);
						
						Vec3 vec = p.getLookVec();
						ducc.motionX = vec.xCoord;
						ducc.motionY = vec.yCoord;
						ducc.motionZ = vec.zCoord;
						
						p.worldObj.spawnEntityInWorld(ducc);
						p.worldObj.playSoundAtEntity(p, "hbm:entity.ducc", 1.0F, 1.0F);
						
						perDat.setBoolean("hasDucked", true);
						
						p.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, perDat);
					}
				}
				
			//} catch (Exception x) { }
			
			return null;
		}
	}
}
