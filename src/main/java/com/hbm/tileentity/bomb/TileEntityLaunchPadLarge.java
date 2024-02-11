package com.hbm.tileentity.bomb;

import com.hbm.inventory.container.ContainerLaunchPadLarge;
import com.hbm.inventory.gui.GUILaunchPadLarge;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityLaunchPadLarge extends TileEntityMachineBase implements IEnergyUser, IGUIProvider, IRadarCommandReceiver {

	public ItemStack toRender;
	public int formFactor = -1;
	/** Whether the missile has already been placed on the launchpad. Missile will render statically on the pad if true */
	public boolean erected = false;
	/** Whether the missile can be lifted. Missile will not render at all if false and not erected */
	public boolean readyToLoad = false;
	/** Instead of setting erected to true outright, this makes it so that it ties into the delay,
	 * which prevents a jerky transition due to the animation of the erector lagging behind a bit */
	public boolean scheduleErect = false;
	public float lift = 1F;
	public float erector = 90F;
	public float prevLift = 1F;
	public float prevErector = 90F;
	public float syncLift;
	public float syncErector ;
	public int delay = 20;
	private int sync;
	
	public long power;
	public final long maxPower = 100_000;

	public TileEntityLaunchPadLarge() {
		super(7);
	}

	@Override
	public String getName() {
		return "container.launchPad";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			float erectorSpeed = 1.5F;
			float liftSpeed = 0.025F;
			
			if(slots[0] != null) {
				if(slots[0].getItem() instanceof ItemMissile) {
					this.formFactor = ((ItemMissile) slots[0].getItem()).formFactor.ordinal();
				}
				
				if(this.erector == 90F && this.lift == 1F) {
					this.readyToLoad = true;
				}
			} else {
				readyToLoad = false;
				erected = false;
				delay = 20;
			}
			
			if(delay > 0) {
				delay--;
				
				if(delay < 10 && scheduleErect) {
					this.erected = true;
					this.scheduleErect = false;
				}
				
				// if there is no missile or the missile isn't ready (i.e. the erector hasn't returned to zero position yet), retract
				if(slots[0] == null || !readyToLoad) {
					//fold back erector
					if(erector < 90F) {
						erector = Math.min(erector + erectorSpeed, 90F);
						if(erector == 90F) delay = 20;
					//extend lift
					} else if(lift < 1F) {
						lift = Math.min(lift + liftSpeed, 1F);
						if(erector == 1F) {
							//if the lift is fully extended, the loading can begin
							readyToLoad = true;
							delay = 20;
						}
					}
				}
				
			} else {
				
				//only extend if the erector isn't up yet and the missile can be loaded
				if(!erected && readyToLoad) {
					//first, rotate the erector
					if(erector != 0F) {
						erector = Math.max(erector - erectorSpeed, 0F);
						if(erector == 0F) delay = 20;
					//then retract the lift
					} else if(lift > 0) {
						lift = Math.max(lift - liftSpeed, 0F);
						if(lift == 0F) {
							//once the lift is at the bottom, the missile is deployed
							scheduleErect = true;
							delay = 20;
						}
					}
				} else {
					//first, fold back the erector
					if(erector < 90F) {
						erector = Math.min(erector + erectorSpeed, 90F);
						if(erector == 90F) delay = 20;
					//then extend the lift again
					} else if(lift < 1F) {
						lift = Math.min(lift + liftSpeed, 1F);
						if(erector == 1F) {
							//if the lift is fully extended, the loading can begin
							readyToLoad = true;
							delay = 20;
						}
					}
				}
			}

			this.networkPackNT(250);
			
		} else {
			this.prevLift = this.lift;
			this.prevErector = this.erector;
			
			if(this.sync > 0) {
				this.lift = this.lift + ((this.syncLift - this.lift) / (float) this.sync);
				this.erector = this.erector + ((this.syncErector - this.erector) / (float) this.sync);
				--this.sync;
			} else {
				this.lift = this.syncLift;
				this.erector = this.syncErector;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		
		if(slots[0] != null) {
			buf.writeBoolean(true);
			buf.writeInt(Item.getIdFromItem(slots[0].getItem()));
			buf.writeShort((short) slots[0].getItemDamage());
		} else {
			buf.writeBoolean(false);
		}

		buf.writeBoolean(erected);
		buf.writeBoolean(readyToLoad);
		buf.writeByte((byte) this.formFactor);
		buf.writeFloat(this.lift);
		buf.writeFloat(this.erector);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		
		if(buf.readBoolean()) {
			this.toRender = new ItemStack(Item.getItemById(buf.readInt()), 1, buf.readShort());
		} else {
			this.toRender = null;
		}

		this.erected = buf.readBoolean();
		this.readyToLoad = buf.readBoolean();
		this.formFactor = buf.readByte();

		this.syncLift = buf.readFloat();
		this.syncErector = buf.readFloat();
		
		if(this.lift != this.syncLift || this.erector != this.syncErector) {
			this.sync = 3;
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public boolean sendCommandPosition(int x, int y, int z) {
		return false;
	}

	@Override
	public boolean sendCommandEntity(Entity target) {
		return false;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLaunchPadLarge(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadLarge(player.inventory, this);
	}
}
