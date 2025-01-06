package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.CasingEjector;
import com.hbm.inventory.gui.GUITurretChekhov;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.XFactory50;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretChekhov extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList();
	
	//because cramming it into the ArrayList's constructor with nested curly brackets and all that turned out to be not as pretty
	//also having a floaty `static` like this looks fun
	//idk if it's just me though
	static {
		configs.add(XFactory50.bmg50_sp.id);
		configs.add(XFactory50.bmg50_fmj.id);
		configs.add(XFactory50.bmg50_jhp.id);
		configs.add(XFactory50.bmg50_ap.id);
		configs.add(XFactory50.bmg50_du.id);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretChekhov";
	}

	@Override
	public double getTurretElevation() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 3.5D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}
	
	int timer;

	@Override
	public void updateFiringTick() {
		
		timer++;
		
		if(timer > 20 && timer % getDelay() == 0) {
			
			BulletConfig conf = this.getFirstConfigLoaded();
			
			if(conf != null) {
				this.cachedCasingConfig = conf.casing;
				this.spawnBullet(conf, 10F);
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.chekhov_fire", 2.0F, 1.0F);
				
				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1.5F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			}
		}
	}

	@Override
	protected Vec3 getCasingSpawnPos() {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(-1.125, 0.125, 0.25);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		return Vec3.createVectorHelper(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord);
	}

	protected static CasingEjector ejector = new CasingEjector().setMotion(-0.8, 0.8, 0).setAngleRange(0.1F, 0.1F);
	
	@Override
	protected CasingEjector getEjector() {
		return ejector;
	}
	
	public int getDelay() {
		return 2;
	}
	
	public float spin;
	public float lastSpin;
	private float accel;
	private boolean manual;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			
			if(this.tPos != null || manual) {
				this.accel = Math.min(45F, this.accel += 2);
			} else {
				this.accel = Math.max(0F, this.accel -= 2);
			}
			
			manual = false;
			
			this.lastSpin = this.spin;
			this.spin += this.accel;
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		} else {
			
			if(this.tPos == null && !manual) {
				
				this.timer--;
				
				if(timer > 20)
					timer = 20;
				
				if(timer < 0)
					timer = 0;
			}
		}
	}
	
	@Override
	public void manualSetup() {
		manual = true;
	}
	
	@Override
	public boolean usesCasings() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretChekhov(player.inventory, this);
	}
}
