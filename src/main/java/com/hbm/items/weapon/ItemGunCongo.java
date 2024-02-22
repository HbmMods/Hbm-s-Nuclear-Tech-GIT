package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public class ItemGunCongo extends ItemGunBase {

	public ItemGunCongo(GunConfiguration config) {
		super(config);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {

		 if(type == AnimType.CYCLE) {
				return new BusAnimation()
						.addBus("RECOIL", new BusAnimationSequence()
								.addKeyframePosition(1, 0, 0, 50)
								.addKeyframePosition(0, 0, 0, 100))
						.addBus("PUMP", new BusAnimationSequence()
								.addKeyframePosition(0, 0, 0, 500)
								.addKeyframePosition(1, 0, 0, 100)
								.addKeyframePosition(0, 0, 0, 200));
							
		 }
		 
		 return null;
	}
}
