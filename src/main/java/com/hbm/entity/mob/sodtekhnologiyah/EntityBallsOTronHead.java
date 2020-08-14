package com.hbm.entity.mob.sodtekhnologiyah;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class EntityBallsOTronHead extends EntityBallsOTronBase implements IBossDisplayData {
	
	/*   ___   _   _    _    ___           ___           _____ ___  ___ _  _
	 *  | _ ) /_\ | |  | |  / __|   ___   |   |   ___   |_   _| _ )|   | \| |
	 *  | _ \/ _ \| |__| |__\__ \  |___|  | | |  |___|    | | |   \| | |    |
	 *  |___/_/ \_\____|____|___/         |___|           |_| |_|\_\___|_|\_|
	 */

	public EntityBallsOTronHead(World world) {
		super(world);
	}

	@Override
	public IChatComponent func_145748_c_() {
		return null;
	}

	@Override
	public float getAttackStrength(Entity target) {
		return 0;
	}

}
