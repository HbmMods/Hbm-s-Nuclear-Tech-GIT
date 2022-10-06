package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretHIMARS;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretHIMARS extends TileEntityTurretBaseArtillery implements IGUIProvider {
	
	public short mode = 0;
	public static final short MODE_AUOT = 0;
	public static final short MODE_MANUAL = 1;
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {
		
		if(ammoStacks != null)
			return ammoStacks;
		
		ammoStacks = new ArrayList();

		List list = new ArrayList();
		ModItems.ammo_arty.getSubItems(ModItems.ammo_arty, MainRegistry.weaponTab, list);
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
		return 3D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 0;
	}
	
	@Override
	public double getHeightOffset() {
		return 3D;
	}

	@Override
	public double getDecetorRange() {
		return 5000D;
	}
	
	@Override
	public double getDecetorGrace() {
		return 32D;
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
	
	@Override
	public void updateEntity() {
		
		if(this.mode == this.MODE_MANUAL) {
			if(!this.targetQueue.isEmpty()) {
				this.tPos = this.targetQueue.get(0);
			}
		} else {
			this.targetQueue.clear();
		}
		
		if(worldObj.isRemote) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
		}

		this.aligned = false;
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.stattrak++;
			}
		}
		
		if(target != null && this.mode != this.MODE_MANUAL) {
			if(!this.entityInLOS(this.target)) {
				this.target = null;
			}
		}
		
		if(!worldObj.isRemote) {
			
			if(target != null) {
				this.tPos = this.getEntityPos(target);
			} else {
				if(this.mode != this.MODE_MANUAL) {
					this.tPos = null;
				}
			}
		}
		
		if(isOn() && hasPower()) {
			
			if(tPos != null)
				this.alignTurret();
		} else {

			this.target = null;
			this.tPos = null;
		}
		
		if(!worldObj.isRemote) {
			
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
			
			if(this.aligned) {
				this.updateFiringTick();
			}
			
			this.power = Library.chargeTEFromItems(slots, 10, this.power, this.getMaxPower());
			
			NBTTagCompound data = this.writePacket();
			this.networkPack(data, 250);
			
		} else {
			
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
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
	public void updateFiringTick() {
		// *chirp* *chirp* *chirp*
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
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", this.mode);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretHIMARS(player.inventory, this);
	}
}
