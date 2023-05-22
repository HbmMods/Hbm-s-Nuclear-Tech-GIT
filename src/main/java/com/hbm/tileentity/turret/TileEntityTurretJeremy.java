package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.inventory.gui.GUITurretJeremy;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretJeremy extends TileEntityTurretBaseNT {

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
		return "container.turretJeremy";
	}
	
	@Override
	public double getDecetorGrace() {
		return 16D;
	}

	@Override
	public double getTurretDepression() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 4.25D;
	}

	@Override
	public double getDecetorRange() {
		return 80D;
	}
	
	int timer;
	int reload;
	
	@Override
	public void updateEntity() {
		
		if(reload > 0)
			reload--;
		
		if(reload == 1)
			this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.jeremy_reload", 2.0F, 1.0F);
		
		super.updateEntity();
	}

	@Override
	public void updateFiringTick() {
		
		timer++;
		
		if(timer % 40 == 0) {
			
			BulletConfiguration conf = this.getFirstConfigLoaded();
			
			if(conf != null) {
				this.cachedCasingConfig = conf.spentCasing;
				this.spawnBullet(conf);
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.jeremy_fire", 4.0F, 1.0F);
				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				reload = 20;
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 0F);
				data.setByte("count", (byte)5);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			}
		}
	}

	@Override
	protected Vec3 getCasingSpawnPos() {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(-2, 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		return Vec3.createVectorHelper(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord);
	}

	protected static CasingEjector ejector = new CasingEjector().setAngleRange(0.01F, 0.01F).setMotion(0, 0, -0.2);
	
	@Override
	protected CasingEjector getEjector() {
		return ejector;
	}
	
	@Override
	public boolean usesCasings() {
		return true;
	}

	@Override
	public int casingDelay() {
		return 22;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretJeremy(player.inventory, this);
	}
}
