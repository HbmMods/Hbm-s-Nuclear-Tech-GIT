package com.hbm.items.weapon.grenade;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectTiny;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.Lego;

import net.minecraft.util.MovingObjectPosition;

public class ItemGrenadeFilling extends ItemEnumMulti {

	public static BulletConfig fragmentation;
	public static BulletConfig pellets;

	public ItemGrenadeFilling() {
		super(EnumGrenadeFilling.class, true, true);
		fragmentation = new BulletConfig().setLife(3).setThresholdNegation(5F).setRicochetAngle(90).setRicochetCount(2);
		pellets = new BulletConfig().setLife(100).setGrav(0.04).setVel(1.5F).setOnImpact(LAMBDA_TINY_EXPLODE);
	}
	
	public static enum EnumGrenadeFilling {
		POWDER(EXPLODE_POWDER),	// gunpowder
		HE(EXPLODE_HE),				// high explosive
		FRAG(EXPLODE_FRAG),			// high explosive with fragmentation
		DEMO(EXPLODE_DEMO),			// demolition
		INC(null),					// incendiary
		CLUSTER(EXPLODE_CLUSTER),	// explosive pellets
		NUCLEAR(null);				// nuka grenade
		
		// and more which i haven't decided. probably plasma, EMP, perhaps laser(?)

		public Consumer<EntityGrenadeUniversal> explode;
		public Set<EnumGrenadeShell> compatibleShells = new HashSet();
		
		private EnumGrenadeFilling(Consumer<EntityGrenadeUniversal> explode, EnumGrenadeShell... compatibleShells) {
			this.explode = explode;
			for(EnumGrenadeShell shell : compatibleShells) this.compatibleShells.add(shell);
		}
	}
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_POWDER = (grenade) -> {
		standardExplode(grenade, 5F, 10F, 5F, 0F);
	};
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_HE = (grenade) -> {
		standardExplode(grenade, 7.5F, 25F, 10F, 0.1F);
	};
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_FRAG = (grenade) -> {
		standardExplode(grenade, 7.5F, 15F, 10F, 0.1F);
		int frags = 50;
		if(grenade.getShell() == EnumGrenadeShell.FRAG) frags *= 1.5;
		for(int i = 0; i < frags; i++) {
			EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(grenade.worldObj, fragmentation, 10F, 0F, grenade.worldObj.rand.nextFloat() * 2F * (float) Math.PI, (grenade.worldObj.rand.nextFloat() - 0.5F) * 2F * (float) Math.PI);
			bullet.setPosition(grenade.posX, grenade.posY + 0.05, grenade.posZ);
			grenade.worldObj.spawnEntityInWorld(bullet);
		}
	};
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_CLUSTER = (grenade) -> {
		standardExplode(grenade, 7.5F, 15F, 10F, 0.1F);
		int frags = 30;
		if(grenade.getShell() == EnumGrenadeShell.FRAG) frags *= 1.25;
		for(int i = 0; i < frags; i++) {
			EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(grenade.worldObj, pellets, 10F, 0F, grenade.worldObj.rand.nextFloat() * 2F * (float) Math.PI, (grenade.worldObj.rand.nextFloat() * 0.5F + 0.5F) * (float) Math.PI);
			bullet.setPosition(grenade.posX, grenade.posY + 0.05, grenade.posZ);
			bullet.motionX *= 0.5;
			bullet.motionY *= 0.75;
			bullet.motionZ *= 0.5;
			grenade.worldObj.spawnEntityInWorld(bullet);
		}
	};
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_DEMO = (grenade) -> {
		ExplosionVNT vnt = new ExplosionVNT(grenade.worldObj, grenade.posX, grenade.posY, grenade.posZ, 5F, grenade.getThrower());
		vnt.setBlockAllocator(new BlockAllocatorStandard());
		vnt.setBlockProcessor(new BlockProcessorStandard());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 10F));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
	};

	public static BiConsumer<EntityBulletBaseMK4, MovingObjectPosition> LAMBDA_TINY_EXPLODE = (bullet, mop) -> {
		if(bullet.ticksExisted < 2) return;
		Lego.tinyExplode(bullet, mop, 1.5F);
		bullet.setDead();
	};

	public static void standardExplode(EntityGrenadeUniversal grenade, float range, float damage) { standardExplode(grenade, range, damage, 0F, 0F); }
	public static void standardExplode(EntityGrenadeUniversal grenade, float range, float damage, float dt, float dr) {
		ExplosionVNT vnt = new ExplosionVNT(grenade.worldObj, grenade.posX, grenade.posY, grenade.posZ, range, grenade.getThrower());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, damage).setupPiercing(dt, dr));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
		vnt.explode();
	}

	public static void tinyExplode(EntityGrenadeUniversal grenade, float range, float damage) { tinyExplode(grenade, range, damage, 0F, 0F); }
	public static void tinyExplode(EntityGrenadeUniversal grenade, float range, float damage, float dt, float dr) {
		ExplosionVNT vnt = new ExplosionVNT(grenade.worldObj, grenade.posX, grenade.posY, grenade.posZ, range, grenade.getThrower());
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, damage).setupPiercing(dt, dr).setKnockback(0.25D));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectTiny());
		vnt.explode();
	}
}
