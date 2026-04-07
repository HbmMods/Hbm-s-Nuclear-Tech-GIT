package com.hbm.items.weapon.grenade;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.util.Vec3NT;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class ItemGrenadeExtra extends ItemEnumMulti {

	public ItemGrenadeExtra() {
		super(EnumGrenadeExtra.class, true, true);
	}

	public static enum EnumGrenadeExtra {
		GLUE(null, EXTRA_GLUE, null),			// sticky bombs!
		PROXY_FUZE(EXTRA_PROXY, null, null),	// additional 10m EntityLivingBase triggered fuze
		FRAG_SLEEVE(null, null, EXTRA_FRAG),	// 25 extra frags
		TRIPLEX(null, null, EXTRA_TRIPLEX);		// [THE BIG ONE]
		
		public Consumer<EntityGrenadeUniversal> updateTick;
		public BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact;
		public Consumer<EntityGrenadeUniversal> onExplode;
		
		private EnumGrenadeExtra(Consumer<EntityGrenadeUniversal> updateTick,
				BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact,
				Consumer<EntityGrenadeUniversal> onExplode) {
			this.updateTick = updateTick;
			this.onImpact = onImpact;
			this.onExplode = onExplode;
		}
	}
	
	public static BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> EXTRA_GLUE = (grenade, mop) -> {
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			grenade.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
			grenade.getStuck(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
		}
	};
	
	public static Consumer<EntityGrenadeUniversal> EXTRA_PROXY = (grenade) -> {
		if(grenade.getTimer() >= 10 && grenade.getTimer() % 3 == 0) {
			List<EntityLivingBase> living = grenade.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(grenade.posX, grenade.posY, grenade.posZ, grenade.posX, grenade.posY, grenade.posZ).expand(10, 10, 10));
			for(EntityLivingBase e : living) {
				if(e == grenade.getThrower()) continue;
				if(e.getDistanceToEntity(grenade) <= 10) {
					grenade.explode();
					return;
				}
			}
		}
	};
	
	public static Consumer<EntityGrenadeUniversal> EXTRA_FRAG = (grenade) -> {
		ItemGrenadeFilling.standardFragmentation(grenade, 25);
	};
	
	public static Consumer<EntityGrenadeUniversal> EXTRA_TRIPLEX = (grenade) -> {
		ItemStack frag = ItemGrenadeUniversal.make(grenade.getShell(), grenade.getFilling(), EnumGrenadeFuze.S3);
		
		Vec3NT vec = new Vec3NT(0.25, 0, 0).rotateAroundYDeg(grenade.worldObj.rand.nextDouble() * 360);
		
		for(int i = 0; i < 3; i++) {
			EntityGrenadeUniversal triplet = new EntityGrenadeUniversal(grenade.worldObj, frag).setTrail(EntityGrenadeUniversal.TRAIL_TRIPLET);
			triplet.setPosition(grenade.posX, grenade.posY, grenade.posZ);
			triplet.motionX = vec.xCoord;
			triplet.motionY = 0.75D;
			triplet.motionZ = vec.zCoord;
			grenade.worldObj.spawnEntityInWorld(triplet);
			vec.rotateAroundYDeg(120);
		}
	};
}
