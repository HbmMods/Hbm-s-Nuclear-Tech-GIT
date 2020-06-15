package com.hbm.packet;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.TileEntityBarrel;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.hbm.tileentity.machine.TileEntityForceField;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityRadioRec;
import com.hbm.tileentity.machine.TileEntityReactorControl;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

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

		@Override
		public IMessage onMessage(AuxButtonPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			//try {
				TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);
				
				if (te instanceof TileEntityMachineReactorSmall) {
					TileEntityMachineReactorSmall reactor = (TileEntityMachineReactorSmall)te;
					
					if(m.id == 0)
						reactor.retracting = m.value == 1;
					
					if(m.id == 1) {
						FluidType type = FluidType.STEAM;
						int fill = reactor.tanks[2].getFill();
						
						switch(m.value) {
						case 0: type = FluidType.HOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
						case 1: type = FluidType.SUPERHOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
						case 2: type = FluidType.STEAM; fill = (int)Math.floor(fill * 100); break;
						}
						
						if(fill > reactor.tanks[2].getMaxFill())
							fill = reactor.tanks[2].getMaxFill();
						
						reactor.tanks[2].setTankType(type);
						reactor.tanks[2].setFill(fill);
					}
				}
				
				if (te instanceof TileEntityRadioRec) {
					TileEntityRadioRec radio = (TileEntityRadioRec)te;
					
					if(m.id == 0) {
						radio.isOn = (m.value == 1);
					}
					
					if(m.id == 1) {
						radio.freq = ((double)m.value) / 10D;
					}
				}
				
				if (te instanceof TileEntityForceField) {
					TileEntityForceField field = (TileEntityForceField)te;
					
					field.isOn = !field.isOn;
				}
				
				if (te instanceof TileEntityReactorControl) {
					TileEntityReactorControl control = (TileEntityReactorControl)te;
					
					if(m.id == 1)
						control.auto = m.value == 1;
					
					if(control.linkY > -1) {
						TileEntity reac = p.worldObj.getTileEntity(control.linkX, control.linkY, control.linkZ);
						
						if (reac instanceof TileEntityMachineReactorSmall) {
							TileEntityMachineReactorSmall reactor = (TileEntityMachineReactorSmall)reac;
							
							if(m.id == 0)
								reactor.retracting = m.value == 0;
							
							if(m.id == 2) {
								FluidType type = FluidType.STEAM;
								int fill = reactor.tanks[2].getFill();
								
								switch(m.value) {
								case 0: type = FluidType.STEAM; fill = (int)Math.floor(fill * 100); break;
								case 1: type = FluidType.HOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
								case 2: type = FluidType.SUPERHOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
								}
								
								if(fill > reactor.tanks[2].getMaxFill())
									fill = reactor.tanks[2].getMaxFill();
								
								reactor.tanks[2].setTankType(type);
								reactor.tanks[2].setFill(fill);
							}
						}
						
						if (reac instanceof TileEntityMachineReactorLarge) {
							TileEntityMachineReactorLarge reactor = (TileEntityMachineReactorLarge)reac;
							
							if(m.id == 0) {
								reactor.rods = m.value;
							}
							
							if(m.id == 2) {
								FluidType type = FluidType.STEAM;
								int fill = reactor.tanks[2].getFill();
								
								switch(m.value) {
								case 0: type = FluidType.STEAM; fill = (int)Math.floor(fill * 100); break;
								case 1: type = FluidType.HOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
								case 2: type = FluidType.SUPERHOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
								}
								
								if(fill > reactor.tanks[2].getMaxFill())
									fill = reactor.tanks[2].getMaxFill();
								
								reactor.tanks[2].setTankType(type);
								reactor.tanks[2].setFill(fill);
							}
						}
					}
				}
				
				if (te instanceof TileEntityMachineReactorLarge) {
					TileEntityMachineReactorLarge reactor = (TileEntityMachineReactorLarge)te;
					
					if(m.id == 0)
						reactor.rods = m.value;
					
					if(m.id == 1) {
						FluidType type = FluidType.STEAM;
						int fill = reactor.tanks[2].getFill();
						
						switch(m.value) {
						case 0: type = FluidType.HOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
						case 1: type = FluidType.SUPERHOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
						case 2: type = FluidType.STEAM; fill = (int)Math.floor(fill * 100); break;
						}
						
						if(fill > reactor.tanks[2].getMaxFill())
							fill = reactor.tanks[2].getMaxFill();
						
						reactor.tanks[2].setTankType(type);
						reactor.tanks[2].setFill(fill);
					}
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
				
			//} catch (Exception x) { }
			
			return null;
		}
	}
}
