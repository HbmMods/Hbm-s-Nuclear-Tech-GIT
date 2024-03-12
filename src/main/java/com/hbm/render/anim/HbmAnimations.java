package com.hbm.render.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

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
		RELOAD,			//animation for reloading the weapon
		RELOAD_EMPTY,	//animation for reloading from empty
		RELOAD_CYCLE,	//animation that plays for every individual round (for shotguns and similar single round loading weapons)
		RELOAD_END,		//animation for transitioning from our RELOAD_CYCLE to idle
		CYCLE,			//animation for every firing cycle
		CYCLE_EMPTY,	//animation for the final shot in the magazine
		ALT_CYCLE,		//animation for alt fire cycles
		SPINUP,			//animation for actionstart
		SPINDOWN,		//animation for actionend
		EQUIP			//animation for drawing the weapon
	}

	// A NOTE ON SHOTGUN STYLE RELOADS
	// Make sure the RELOAD and RELOAD_EMPTY adds shells, not just RELOAD_CYCLE, they all proc once for each loaded shell
	
	public static class Animation {
		
		//the "name" of the animation slot. if the item has a different key than
		//the animation, the animation will be canceled.
		public String key;
		//the starting time of the animation
		public long startMillis;
		//the animation bus
		public BusAnimation animation;
		// If set, don't cancel this animation when the timer ends, instead wait for the next to start
		public boolean holdLastFrame = false;
		
		public Animation(String key, long startMillis, BusAnimation animation) {
			this.key = key;
			this.startMillis = startMillis;
			this.animation = animation;
		}
		
		public Animation(String key, long startMillis, BusAnimation animation, boolean holdLastFrame) {
			this.key = key;
			this.startMillis = startMillis;
			this.animation = animation;
			this.holdLastFrame = holdLastFrame;
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

		return new double[] {
			0, 0, 0, // position
			0, 0, 0, // rotation
			1, 1, 1, // scale
			0, 0, 0  // offset
		};
	}

	public static void applyRelevantTransformation(String bus) {
		double[] transform = getRelevantTransformation(bus);
		
		GL11.glTranslated(transform[0], transform[1], transform[2]);
		GL11.glRotated(transform[3], 1, 0, 0);
		GL11.glRotated(transform[4], 0, 1, 0);
		GL11.glRotated(transform[5], 0, 0, 1);
		GL11.glTranslated(-transform[9], -transform[10], -transform[11]);
		GL11.glScaled(transform[6], transform[7], transform[8]);
	}

}
