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
		S3(null, null),			// 3s timed
		S7(null, null),			// 7s times
		S15(null, null),		// 15s timed
		IMPACT(null, null),		// on block/entity impact, 1s safety
		AIRBURST(null, null);	// still have to figure out the mechanics, whether it should be an arc angle or fixed height over the floor, 2s safety

		public Consumer<EntityGrenadeUniversal> updateTick;
		public BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact;
		
		private EnumGrenadeFuze(Consumer<EntityGrenadeUniversal> updateTick, BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact) {
			this.updateTick = updateTick;
			this.onImpact = onImpact;
		}
	}
}
