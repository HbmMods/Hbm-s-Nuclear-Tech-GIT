package com.hbm.items.weapon.grenade;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.items.ItemEnumMulti;
import com.hbm.util.Vec3NT;

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
		AIRBURST(FUZE_AIRBURST,	0x56A137);	// 2s safety, explodes 10 blocks above ground

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

	public static Consumer<EntityGrenadeUniversal> FUZE_3S = (grenade) -> { if(grenade.getTimer() >= 60) grenade.explode(); };
	public static Consumer<EntityGrenadeUniversal> FUZE_7S = (grenade) -> { if(grenade.getTimer() >= 140) grenade.explode(); };
	public static Consumer<EntityGrenadeUniversal> FUZE_15S = (grenade) -> { if(grenade.getTimer() >= 300) grenade.explode(); };
	public static BiConsumer<EntityGrenadeUniversal, MovingObjectPosition> FUZE_IMPACT = (grenade, mop) -> {
		if(grenade.getTimer() >= 10) {
			grenade.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
			grenade.explode();
		};
	};
	public static Consumer<EntityGrenadeUniversal> FUZE_AIRBURST = (grenade) -> {
		if(grenade.getTimer() >= 40) {
			Vec3NT start = new Vec3NT(grenade);
			Vec3NT end = new Vec3NT(grenade).add(0, -10, 0);
			MovingObjectPosition mop = grenade.worldObj.func_147447_a(start, end, false, false, true);
			if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) grenade.explode();
		};
	};
}
