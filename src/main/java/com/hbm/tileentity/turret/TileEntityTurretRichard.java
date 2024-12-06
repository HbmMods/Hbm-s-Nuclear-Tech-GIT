package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.inventory.gui.GUITurretRichard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.XFactoryRocket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretRichard extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList();
	
	static {
		for(BulletConfig cfg : XFactoryRocket.rocket_ml) configs.add(cfg.id);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretRichard";
	}

	@Override
	public double getTurretDepression() {
		return 25D;
	}

	@Override
	public double getTurretElevation() {
		return 25D;
	}

	@Override
	public double getBarrelLength() {
		return 1.25D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}
	
	@Override
	public double getDecetorGrace() {
		return 8D;
	}

	@Override
	public double getDecetorRange() {
		return 64D;
	}
	
	int timer;
	public int loaded;
	int reload;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(reload > 0) {
				reload--;
				
				if(reload == 0)
					this.loaded = 17;
			}
			
			if(loaded <= 0 && reload <= 0 && this.getFirstConfigLoaded() != null) {
				reload = 100;
			}
			
			if(this.getFirstConfigLoaded() == null) {
				this.loaded = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("loaded", this.loaded);
			this.networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		if(nbt.hasKey("loaded"))
			this.loaded = nbt.getInteger("loaded");
		else
			super.networkUnpack(nbt);
	}

	@Override
	public void updateFiringTick() {
		
		if(reload > 0)
			return;
		
		timer++;
		
		if(timer > 0 && timer % 10 == 0) {
			
			BulletConfig conf = this.getFirstConfigLoaded();
			
			if(conf != null) {
				this.spawnBullet(conf, 30F);
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.richard_fire", 2.0F, 1.0F);
				this.loaded--;
				
				//if(conf.ammo.equals(new ComparableStack(ModItems.ammo_standard, EnumAmmo.ROCKET_DEMO))) timer = -50;
				
			} else {
				this.loaded = 0;
			}
		}
	}

	@Override
	public void spawnBullet(BulletConfig bullet, float baseDamage) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		EntityBulletBaseMK4 proj = new EntityBulletBaseMK4(worldObj, bullet, baseDamage, bullet.spread, (float) rotationYaw, (float) rotationPitch);
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, proj.rotationYaw, proj.rotationPitch);
		proj.lockonTarget = this.target;
		worldObj.spawnEntityInWorld(proj);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.loaded = nbt.getInteger("loaded");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("loaded", this.loaded);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretRichard(player.inventory, this);
	}
}
