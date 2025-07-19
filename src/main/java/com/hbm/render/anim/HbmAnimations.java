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
	public static final Animation[][] hotbar = new Animation[9][8]; //now with 8 parallel rails per slot! time to get railed!
	
	public static enum AnimType {
		RELOAD,			//either a full reload or start of a reload
		@Deprecated RELOAD_EMPTY,	//same as reload, but the mag is completely empty
		RELOAD_CYCLE,	//animation that plays for every individual round (for shotguns and similar single round loading weapons)
		RELOAD_END,		//animation for transitioning from our RELOAD_CYCLE to idle
		CYCLE,			//animation for every firing cycle
		CYCLE_EMPTY,	//animation for the final shot in the magazine
		CYCLE_DRY,		//animation for trying to fire, but no round is available
		ALT_CYCLE,		//animation for alt fire cycles
		SPINUP,			//animation for actionstart
		SPINDOWN,		//animation for actionend
		EQUIP,			//animation for drawing the weapon
		INSPECT,		//animation for inspecting the weapon
		JAMMED			//animation for jammed weapons
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
		// so we know what type of animation we're playing, only used rarely
		public AnimType type;
		
		public Animation(String key, long startMillis, BusAnimation animation, AnimType type) {
			this.key = key;
			this.startMillis = startMillis;
			this.animation = animation;
			this.type = type;
		}
		
		public Animation(String key, long startMillis, BusAnimation animation, AnimType type, boolean holdLastFrame) {
			this.key = key;
			this.startMillis = startMillis;
			this.animation = animation;
			this.holdLastFrame = holdLastFrame;
			this.type = type;
		}
	}

	public static Animation getRelevantAnim() { return getRelevantAnim(0); }
	public static Animation getRelevantAnim(int index) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		int slot = player.inventory.currentItem;
		ItemStack stack = player.getHeldItem();
		
		if(stack == null)
			return null;
		
		if(slot < 0 || slot > 8) { //for freak of nature hotbars, probably won't work right but at least it doesn't crash
			slot = Math.abs(slot) % 9;
		}
		
		if(hotbar[slot][index] == null)
			return null;
		
		if(hotbar[slot][index].key.equals(stack.getItem().getUnlocalizedName())) {
			return hotbar[slot][index];
		}
		
		return null;
	}

	public static double[] getRelevantTransformation(String bus) { return getRelevantTransformation(bus, 0); }
	public static double[] getRelevantTransformation(String bus, int index) {
		
		Animation anim = HbmAnimations.getRelevantAnim(index);
		
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
			0, 0, 0, // offset
			0, 1, 2, // XYZ order
		};
	}

	public static void applyRelevantTransformation(String bus) { applyRelevantTransformation(bus, 0); }
	public static void applyRelevantTransformation(String bus, int index) {
		double[] transform = getRelevantTransformation(bus, index);
		int[] rot = new int[] { (int)transform[12], (int)transform[13], (int)transform[14] };
		
		GL11.glTranslated(transform[0], transform[1], transform[2]);
		GL11.glRotated(transform[3 + rot[0]], rot[0] == 0 ? 1 : 0, rot[0] == 1 ? 1 : 0, rot[0] == 2 ? 1 : 0);
		GL11.glRotated(transform[3 + rot[1]], rot[1] == 0 ? 1 : 0, rot[1] == 1 ? 1 : 0, rot[1] == 2 ? 1 : 0);
		GL11.glRotated(transform[3 + rot[2]], rot[2] == 0 ? 1 : 0, rot[2] == 1 ? 1 : 0, rot[2] == 2 ? 1 : 0);
		GL11.glTranslated(-transform[9], -transform[10], -transform[11]);
		GL11.glScaled(transform[6], transform[7], transform[8]);
	}

}
