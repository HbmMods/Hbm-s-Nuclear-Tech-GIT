package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretArty;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretArty extends TileEntityTurretBaseNT implements IGUIProvider {
	
	public boolean directMode = false;

	static List<Integer> configs = new ArrayList();
	
	static {
		configs.add(BulletConfigSyncingUtil.SHELL_NORMAL);
		configs.add(BulletConfigSyncingUtil.SHELL_EXPLOSIVE);
		configs.add(BulletConfigSyncingUtil.SHELL_AP);
		configs.add(BulletConfigSyncingUtil.SHELL_DU);
		configs.add(BulletConfigSyncingUtil.SHELL_W9);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretArty";
	}

	@Override
	public long getMaxPower() {
		return 100000;
	}

	@Override
	public double getBarrelLength() {
		return 9D;
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
	protected void seekNewTarget() {
		
		if(this.directMode) {
			super.seekNewTarget();
			return;
		}
		
		
		/* TODO: large field artillery target search */
	}
	
	@Override
	protected void alignTurret() {
		/* TODO: calculate angles */
		this.turnTowards(tPos);
	}

	@Override
	public void spawnBullet(BulletConfiguration bullet) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		/* TODO: replace bullet base entity with a chunkloading artillery shell */
		EntityBulletBase proj = new EntityBulletBase(worldObj, BulletConfigSyncingUtil.getKey(bullet));
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, bullet.velocity, bullet.spread);
		worldObj.spawnEntityInWorld(proj);
	}

	@Override
	public void updateFiringTick() {
		
		BulletConfiguration conf = this.getFirstConfigLoaded();
		
		if(conf != null) {
			this.spawnBullet(conf);
			this.conusmeAmmo(conf.ammo);
			this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.jeremy_fire", 4.0F, 1.0F);
			Vec3 pos = this.getTurretPos();
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaExt");
			data.setString("mode", "largeexplode");
			data.setFloat("size", 0F);
			data.setByte("count", (byte)5);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		if(meta == 5) {
			this.directMode = !this.directMode;
		} else{
			super.handleButtonPacket(value, meta);
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretArty(player.inventory, this);
	}
}
