package com.hbm.sound;

<<<<<<< HEAD
import com.hbm.entity.mob.EntityHunterChopper;
=======
import com.hbm.entity.EntityHunterChopper;
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MovingSoundCrashing extends MovingSoundPlayerLoop {

	public MovingSoundCrashing(ResourceLocation p_i45104_1_, Entity player, EnumHbmSound type) {
		super(p_i45104_1_, player, type);
	}

	@Override
	public void update() {
		super.update();
		
		if(player instanceof EntityHunterChopper && !((EntityHunterChopper)player).getIsDying())
			this.stop();
	}
}
