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
		S3(FUZE_3S,				0x000000),	// 3s timed
		S7(FUZE_7S,				0x404040),	// 7s times
		S15(FUZE_15S,			0x808080),	// 15s timed
		IMPACT(FUZE_IMPACT,		0xE36C17),	// on block/entity impact, 0.5s safety
		AIRBURST(null, null,	0xD11EB8);	// still have to figure out the mechanics, whether it should be an arc angle or fixed height over the floor, 2s safety

		public Consumer<EntityGrenadeUniversal> updateTick;
		public BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact;
		public int bandColor;
		
		private EnumGrenadeFuze(Consumer<EntityGrenadeUniversal> updateTick, int color) { this(updateTick, null, color); }
		private EnumGrenadeFuze(BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact, int color) { this(null, onImpact, color); }
		private EnumGrenadeFuze(Consumer<EntityGrenadeUniversal> updateTick, BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> onImpact, int color) {
			this.updateTick = updateTick;
			this.onImpact = onImpact;
			this.bandColor = color;
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
