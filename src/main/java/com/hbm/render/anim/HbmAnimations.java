package com.hbm.render.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HbmAnimations {
	
	//in flans mod and afaik also MW, there's an issue that there is only one
	//single animation timer for each client. this is fine for the most part,
	//but once you reload and switch weapons while the animation plays, the
	//other weapon will too play the animation, even though it is not reloading.
	//my approach adds 9 timers, one for every inventory slot. you can still
	//"trick" the system by putting a weapon into a different slot while an
	//animation is playing, though this will cancel the animation entirely.
	public static final Animation[] hotbar = new Animation[9];
	
	public static enum AnimType {
		RELOAD,		//animation for every reload cycle
		CYCLE,		//animation for every firing cycle
		ALT_CYCLE,	//animation for alt fire cycles
		SPINUP,		//animation for actionstart
		SPINDOWN,	//animation for actionend
		EQUIP		//animation for drawing the weapon
	}
	
	public static class Animation {
		
		//the "name" of the animation slot. if the item has a different key than
		//the animation, the animation will be canceled.
		public String key;
		//the starting time of the animation
		public long startMillis;
		//the animation bus
		public BusAnimation animation;
		
		public Animation(String key, long startMillis, BusAnimation animation) {
			this.key = key;
			this.startMillis = startMillis;
			this.animation = animation;
		}
	}
	
	public static Animation getRelevantAnim() {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		int slot = player.inventory.currentItem;
		ItemStack stack = player.getHeldItem();
		
		if(stack == null)
			return null;
		
		if(slot < 0 || slot > 8) { //for freak of nature hotbars, probably won't work right but at least it doesn't crash
			slot = Math.abs(slot) % 9;
		}
		
		if(hotbar[slot] == null)
			return null;
		
		if(hotbar[slot].key.equals(stack.getItem().getUnlocalizedName())) {
			return hotbar[slot];
		}
		
		return null;
	}
	
	public static double[] getRelevantTransformation(String bus) {
		
		Animation anim = HbmAnimations.getRelevantAnim();
		
		if(anim != null) {
			
			BusAnimation buses = anim.animation;
			int millis = (int)(System.currentTimeMillis() - anim.startMillis);

			BusAnimationSequence seq = buses.getBus(bus);
			
			if(seq != null) {
				double[] trans = seq.getTransformation(millis);
				
				if(trans != null)
					return trans;
			}
		}
		
		return new double[] {0, 0, 0};
	}

}
