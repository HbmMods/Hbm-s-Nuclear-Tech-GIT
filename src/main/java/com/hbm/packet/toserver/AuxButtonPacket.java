package com.hbm.packet.toserver;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.interfaces.NotableComments;
import com.hbm.packet.PacketSecurity;
import com.hbm.items.weapon.ItemCustomMissilePart.PartSize;
import com.hbm.tileentity.TileEntityMachineBase;
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
import net.minecraft.util.MathHelper;

@NotableComments
@Deprecated //use the NBT control packet instead
public class AuxButtonPacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int id;

	public AuxButtonPacket() { }

	public AuxButtonPacket(int x, int y, int z, int value, int id) {
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
			if(p == null || p.worldObj == null || !PacketSecurity.allow(p, "aux_button", 40)) return null;

			// The duck key is the only non-tile operation carried by this legacy packet.
			if(m.value == 999) {
				if(!PacketSecurity.allow(p, "duck", 4)) return null;
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
				return null;
			}

			// Every remaining operation originates from a machine GUI.
			if(p.openContainer == null || p.openContainer == p.inventoryContainer) return null;
			if(m.y < 0 || m.y >= p.worldObj.getHeight()) return null;
			if(p.getDistanceSq(m.x + 0.5D, m.y + 0.5D, m.z + 0.5D) > PacketSecurity.MAX_TILE_DISTANCE_SQ) return null;

			TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);
			if(!PacketSecurity.canAccessTile(p, te)) return null;
			if(!PacketSecurity.containerTargetsTile(p.openContainer, te)) return null;
				
				if(te instanceof TileEntityForceField) {
					if(!((TileEntityForceField) te).isUseableByPlayer(p)) return null;
					TileEntityForceField field = (TileEntityForceField)te;
					field.isOn = !field.isOn;
				}
				
				if(te instanceof TileEntityMachineMissileAssembly) {
					if(!((TileEntityMachineMissileAssembly) te).isUseableByPlayer(p)) return null;
					TileEntityMachineMissileAssembly assembly = (TileEntityMachineMissileAssembly)te;
					assembly.construct();
				}
				
				if(te instanceof TileEntityLaunchTable) {
					if(!((TileEntityLaunchTable) te).isUseableByPlayer(p)) return null;
					if(m.value < 0 || m.value >= PartSize.values().length) return null;
					TileEntityLaunchTable launcher = (TileEntityLaunchTable)te;
					launcher.padSize = PartSize.values()[m.value];
				}
				
				if(te instanceof TileEntityCoreEmitter) {
					TileEntityCoreEmitter core = (TileEntityCoreEmitter)te;
					if(!core.isUseableByPlayer(p)) return null;
					if(m.id == 0) core.watts = MathHelper.clamp_int(m.value, 1, 100);
					if(m.id == 1) core.isOn = !core.isOn;
				}
				
				if(te instanceof TileEntityCoreStabilizer) {
					TileEntityCoreStabilizer core = (TileEntityCoreStabilizer)te;
					if(!core.isUseableByPlayer(p)) return null;
					if(m.id == 0) core.watts = MathHelper.clamp_int(m.value, 1, 100);
				}
				
				if(te instanceof TileEntityBarrel) {
					TileEntityBarrel barrel = (TileEntityBarrel)te;
					barrel.mode = (short) ((barrel.mode + 1) % barrel.modes);
					barrel.markDirty();
				}
				
				if(te instanceof TileEntityMachineBattery) {
					TileEntityMachineBattery bat = (TileEntityMachineBattery)te;
					if(!bat.isUseableByPlayer(p)) return null;

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
				
				if(te instanceof TileEntitySoyuzLauncher) {
					TileEntitySoyuzLauncher launcher = (TileEntitySoyuzLauncher)te;
					if(!launcher.isUseableByPlayer(p)) return null;
					if(m.id == 0 && (m.value == 0 || m.value == 1)) launcher.mode = (byte) m.value;
					if(m.id == 1) launcher.startCountdown();
				}
				
				if(te instanceof TileEntityMachineMiningLaser) {
					TileEntityMachineMiningLaser laser = (TileEntityMachineMiningLaser)te;
					laser.isOn = !laser.isOn;
				}
				
				/// yes ///
				//no fuck off
				if(te instanceof TileEntityMachineBase) {
					TileEntityMachineBase base = (TileEntityMachineBase)te;
					if(!base.isUseableByPlayer(p)) return null;
					base.handleButtonPacket(m.value, m.id);
				}
			
			return null;
		}
	}
}
