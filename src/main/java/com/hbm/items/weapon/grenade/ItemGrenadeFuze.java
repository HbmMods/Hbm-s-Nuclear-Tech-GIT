package com.hbm.items.weapon.grenade;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.items.ItemEnumMulti;

import net.minecraft.util.MovingObjectPosition;

public class ItemGrenadeFuze extends ItemEnumMulti {

	public ItemGrenadeFuze() {
		super(EnumGrenadeFuze.class, true, true);
	}

	public static enum EnumGrenadeFuze {
		S3(FUZE_3S),			// 3s timed
		S7(FUZE_7S),			// 7s times
		S15(FUZE_15S),			// 15s timed
		IMPACT(FUZE_IMPACT),	// on block/entity impact, 0.5s safety
		AIRBURST(null, null);	// still have to figure out the mechanics, whether it should be an arc angle or fixed height over the floor, 2s safety

		public Consumer<EntityGrenadeUniversal> updateTick;
		public BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact;
		
		private EnumGrenadeFuze(Consumer<EntityGrenadeUniversal> updateTick) { this(updateTick, null); }
		private EnumGrenadeFuze(BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact) { this(null, onImpact); }
		private EnumGrenadeFuze(Consumer<EntityGrenadeUniversal> updateTick, BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact) {
			this.updateTick = updateTick;
			this.onImpact = onImpact;
		}
	}

	public static Consumer<EntityGrenadeUniversal> FUZE_3S = (grenade) -> { if(grenade.ticksInAir >= 60) grenade.explode(); };
	public static Consumer<EntityGrenadeUniversal> FUZE_7S = (grenade) -> { if(grenade.ticksInAir >= 140) grenade.explode(); };
	public static Consumer<EntityGrenadeUniversal> FUZE_15S = (grenade) -> { if(grenade.ticksInAir >= 300) grenade.explode(); };
	public static BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> FUZE_IMPACT = (grenade, mop) -> {
		if(grenade.ticksInAir >= 10) {
			grenade.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
			grenade.explode();
		};
	};
}
