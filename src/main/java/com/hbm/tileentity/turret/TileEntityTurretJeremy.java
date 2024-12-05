package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.CasingEjector;
import com.hbm.inventory.gui.GUITurretJeremy;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo240Shell;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.Lego;
import com.hbm.items.weapon.sedna.factory.XFactoryCatapult;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretJeremy extends TileEntityTurretBaseNT {

	public static SpentCasing CASINNG240MM = new SpentCasing(CasingType.BOTTLENECK).setScale(7.5F).setBounceMotion(0.02F, 0.05F).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(1F, 0.5D, 60, 20);
	public static BulletConfig shell_normal = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.STOCK)).setDamage(1F).setCasing(CASINNG240MM).setOnImpact((bullet, mop) -> {
		Lego.standardExplode(bullet, mop, 10F); bullet.setDead();
	});
	public static BulletConfig shell_explosive = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.EXPLOSIVE)).setDamage(1.5F).setCasing(CASINNG240MM).setOnImpact((bullet, mop) -> {
		ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 10F);
		vnt.setBlockAllocator(new BlockAllocatorStandard());
		vnt.setBlockProcessor(new BlockProcessorStandard());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
		bullet.setDead();
	});
	public static BulletConfig shell_ap = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.APFSDS_T)).setDamage(2F).setDoesPenetrate(true).setCasing(CASINNG240MM);
	public static BulletConfig shell_du = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.APFSDS_DU)).setDamage(2.5F).setDoesPenetrate(true).setDamageFalloutByPen(false).setCasing(CASINNG240MM);
	public static BulletConfig shell_w9 = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.W9)).setDamage(2.5F).setCasing(CASINNG240MM).setOnImpact(XFactoryCatapult.LAMBDA_NUKE_STANDARD);
	static List<Integer> configs = new ArrayList();
	
	static {
		configs.add(shell_normal.id);
		configs.add(shell_explosive.id);
		configs.add(shell_ap.id);
		configs.add(shell_du.id);
		configs.add(shell_w9.id);
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
			
			BulletConfig conf = this.getFirstConfigLoaded();
			
			if(conf != null) {
				this.cachedCasingConfig = conf.casing;
				this.spawnBullet(conf, 50F);
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
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretJeremy(player.inventory, this);
	}
}
