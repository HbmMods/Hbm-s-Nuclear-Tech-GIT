package com.hbm.items.weapon.grenade;

import java.util.function.Consumer;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectTiny;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.ItemEnumMulti;

public class ItemGrenadeFilling extends ItemEnumMulti {

	public ItemGrenadeFilling() {
		super(EnumGrenadeFilling.class, true, true);
	}
	
	public static enum EnumGrenadeFilling {
		POWDER(EXPLODE_POWDER),		// gunpowder
		HE(null),			// high explosive
		FRAG(null),		// high explosive with fragmentation
		DEMO(null),		// demolition
		INC(null),		// incendiary
		CLUSTER(null),	// explosive pellets
		NUCLEAR(null);	// nuka grenade
		
		// and more which i haven't decided. probably plasma, EMP, perhaps laser(?)
		
		public Consumer<EntityGrenadeUniversal> explode;
		
		private EnumGrenadeFilling(Consumer<EntityGrenadeUniversal> explode) {
			this.explode = explode;
		}
	}
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_POWDER = (grenade) -> {
		standardExplode(grenade, 5F, 10F, 5F, 0F);
	};
	
	public static Consumer<EntityGrenadeUniversal> EXPLODE_HE = (grenade) -> {
		standardExplode(grenade, 7.5F, 25F, 10F, 0.1F);
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
