package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityArtilleryRocket;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretHIMARS;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretHIMARS extends TileEntityTurretBaseArtillery implements IGUIProvider {
	
	public short mode = 0;
	public static final short MODE_AUTO = 0;
	public static final short MODE_MANUAL = 1;
	
	public int typeLoaded = -1;
	public int ammo = 0;
	public float crane;
	public float lastCrane;
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {
		
		if(ammoStacks != null)
			return ammoStacks;
		
		ammoStacks = new ArrayList();

		List list = new ArrayList();
		ModItems.ammo_himars.getSubItems(ModItems.ammo_himars, MainRegistry.weaponTab, list);
		this.ammoStacks.addAll(list);
		
		return ammoStacks;
	}

	@Override
	protected List<Integer> getAmmoList() {
		return new ArrayList();
	}

	@Override
	public String getName() {
		return "container.turretHIMARS";
	}

	@Override
	public long getMaxPower() {
		return 1_000_000;
	}

	@Override
	public double getBarrelLength() {
		return 0.5D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 5D; //they're guided missiles so who gives a shit
	}
	
	@Override
	public double getHeightOffset() {
		return 5D;
	}

	@Override
	public double getDecetorRange() {
		return 5000D;
	}
	
	@Override
	public double getDecetorGrace() {
		return 250D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 1D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 0.5D;
	}

	@Override
	public boolean doLOSCheck() {
		return false;
	}
	
	@Override
	protected void alignTurret() {

		Vec3 pos = this.getTurretPos();
		
		Vec3 delta = Vec3.createVectorHelper(tPos.xCoord - pos.xCoord, tPos.yCoord - pos.yCoord, tPos.zCoord - pos.zCoord);
		double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);
		double targetPitch = Math.PI / 4D;
		
		this.turnTowardsAngle(targetPitch, targetYaw);
	}
	
	public int getSpareRocket() {
		
		for(int i = 1; i < 10; i++) {
			if(slots[i] != null) {
				if(slots[i].getItem() == ModItems.ammo_himars) {
					return slots[i].getItemDamage();
				}
			}
		}
		
		return -1;
	}
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
			this.lastCrane = this.crane;
			this.rotationPitch = this.syncRotationPitch;
			this.rotationYaw = this.syncRotationYaw;
		}
		
		if(!worldObj.isRemote) {
			
			if(this.mode == this.MODE_MANUAL) {
				if(!this.targetQueue.isEmpty()) {
					this.tPos = this.targetQueue.get(0);
				}
			} else {
				this.targetQueue.clear();
			}
			
			this.aligned = false;
			
			this.updateConnections();
			
			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.stattrak++;
			}
		
			if(target != null && this.mode != this.MODE_MANUAL) {
				if(!this.entityInLOS(this.target)) {
					this.target = null;
				}
			}
			
			if(target != null) {
				this.tPos = this.getEntityPos(target);
			} else {
				if(this.mode != this.MODE_MANUAL) {
					this.tPos = null;
				}
			}
		
			if(isOn() && hasPower()) {
				
				if(!this.hasAmmo() || this.crane > 0) {
					
					this.turnTowardsAngle(0, this.rotationYaw);
					
					if(this.aligned) {
						
						if(this.hasAmmo()) {
							this.crane -= 0.0125F;
						} else {
							this.crane += 0.0125F;
							
							if(this.crane >= 1F) {
								int available = this.getSpareRocket();
								
								if(available != -1) {
									HIMARSRocket type = ItemAmmoHIMARS.itemTypes[available];
									this.typeLoaded = available;
									this.ammo = type.amount;
									this.conusmeAmmo(new ComparableStack(ModItems.ammo_himars, 1, available));
								}
							}
						}
					}
					
					this.crane = MathHelper.clamp_float(this.crane, 0F, 1F);
					
				} else {
					
					if(tPos != null) {
						this.alignTurret();
					}
				}
				
			} else {
	
				this.target = null;
				this.tPos = null;
			}
			
			if(!isOn()) this.targetQueue.clear();
			
			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.tPos = null;
				this.stattrak++;
			}
			
			if(isOn() && hasPower()) {
				searchTimer--;
				
				this.setPower(this.getPower() - this.getConsumption());
				
				if(searchTimer <= 0) {
					searchTimer = this.getDecetorInterval();
					
					if(this.target == null && this.mode != this.MODE_MANUAL)
						this.seekNewTarget();
				}
			} else {
				searchTimer = 0;
			}
			
			if(this.aligned && crane <= 0) {
				this.updateFiringTick();
			}
			
			this.power = Library.chargeTEFromItems(slots, 10, this.power, this.getMaxPower());

			this.networkPackNT(250);
			
		} else {
			
			//this will fix the interpolation error when the turret crosses the 360° point
			if(Math.abs(this.lastRotationYaw - this.rotationYaw) > Math.PI) {
				
				if(this.lastRotationYaw < this.rotationYaw)
					this.lastRotationYaw += Math.PI * 2;
				else
					this.lastRotationYaw -= Math.PI * 2;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeShort(this.mode);
		buf.writeShort(this.typeLoaded);
		buf.writeInt(this.ammo);
		buf.writeFloat(this.crane);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.mode = buf.readShort();
		this.typeLoaded = buf.readShort();
		this.ammo = buf.readInt();
		this.crane = buf.readFloat();
	}
	
	public boolean hasAmmo() {
		return this.typeLoaded >= 0 && this.ammo > 0;
	}

	int timer;
	
	@Override
	public void updateFiringTick() {
		
		timer++;
		
		int delay = 40;
		
		if(timer % delay == 0) {
			
			if(this.hasAmmo() && this.tPos != null) {
				this.spawnShell(this.typeLoaded);
				this.ammo--;
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.rocketFlame", 25.0F, 1.0F);
			}
			
			if(this.mode == this.MODE_MANUAL && !this.targetQueue.isEmpty()) {
				this.targetQueue.remove(0);
				this.tPos = null;
			}
		}
	}

	public void spawnShell(int type) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		EntityArtilleryRocket proj = new EntityArtilleryRocket(worldObj);
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 25F, 0.0F);
		
		if(this.target != null)
			proj.setTarget(this.target);
		else
			proj.setTarget(tPos.xCoord, tPos.yCoord, tPos.zCoord);
		
		proj.setType(type);
		
		worldObj.spawnEntityInWorld(proj);
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		if(meta == 5) {
			this.mode++;
			if(this.mode > 1)
				this.mode = 0;
			
			this.tPos = null;
			this.targetQueue.clear();
			
		} else{
			super.handleButtonPacket(value, meta);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.mode = nbt.getShort("mode");
		this.typeLoaded = nbt.getShort("type");
		this.ammo = nbt.getInteger("ammo");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", this.mode);
		nbt.setInteger("type", this.typeLoaded);
		nbt.setInteger("ammo", this.ammo);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretHIMARS(player.inventory, this);
	}
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] addCoords(Context context, Arguments args) {
		this.mode = MODE_MANUAL;
		targetQueue.add(Vec3.createVectorHelper(args.checkDouble(0), args.checkDouble(1), args.checkDouble(2)));
		return new Object[] {};
	}
}
