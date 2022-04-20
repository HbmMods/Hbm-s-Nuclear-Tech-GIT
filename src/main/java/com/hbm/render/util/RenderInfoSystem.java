package com.hbm.render.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class RenderInfoSystem {
	
	private static Random rand = new Random();
	private static HashMap<Integer, InfoEntry> inbox = new HashMap();
	private static HashMap<Integer, InfoEntry> messages = new HashMap();
	
	@SubscribeEvent
	public void clentTick(ClientTickEvent event) {
		messages.putAll(inbox);
		inbox.clear();
		
		List<Integer> keys = new ArrayList(messages.keySet());
		
		for(int i = 0; i < keys.size(); i++) {
			Integer key = keys.get(i);
			InfoEntry entry = messages.get(key);
			
			if(entry.start + entry.millis < System.currentTimeMillis()) {
				messages.remove(key);
				i--;
			}
		}
	}
	
	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
		
		if(event.type != ElementType.CROSSHAIRS)
			return;

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution resolution = event.resolution;

		int pX = 15; //resolution.getScaledWidth() / 2 + 8;
		int pZ = 15; //resolution.getScaledHeight() / 2;
		
		List<InfoEntry> entries = new ArrayList(messages.entrySet());
		Collections.sort(entries);

		GL11.glPushMatrix();
		
		int off = 0;
		long now = System.currentTimeMillis();
		
		for(InfoEntry entry : messages.values()) {
			
			int elapsed = (int) (now - entry.start);
			
			int alpha = Math.min(510 * (entry.millis - elapsed) / entry.millis, 255); //smoothly scales down from 510 to 0, then caps at 255
			int color = entry.color + (alpha << 24 & -0xffffff);
			mc.fontRenderer.drawString(entry.text, pX, pZ + off, color);
			
			off += 10;
		}

		/*mc.fontRenderer.drawString(title, pX + 1, pZ - 9, bgCol);
		mc.fontRenderer.drawString(title, pX, pZ - 10, titleCol);

		try {
			for(String line : text) {
	
				int color = 0xFFFFFF;
				if(line.startsWith("&[")) {
					int end = line.lastIndexOf("&]");
					color = Integer.parseInt(line.substring(2, end));
					line = line.substring(end + 2);
				}
				
				mc.fontRenderer.drawStringWithShadow(line, pX, pZ, color);
				pZ += 10;
			}
		} catch(Exception ex) {
			mc.fontRenderer.drawStringWithShadow(ex.getClass().getSimpleName(), pX, pZ + 10, 0xff0000);
		}*/

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor3f(1F, 1F, 1F);

		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static void push(InfoEntry entry) {
		push(entry, rand.nextInt()); //range is so large, collisions are unlikely and if they do occur, not a big deal
	}
	
	public static void push(InfoEntry entry, int id) {
		inbox.put(id, entry);
	}

	public static class InfoEntry implements Comparable {
		
		String text;
		int color = 0xffffff;
		long start;
		int millis;
		
		public InfoEntry(String text) {
			this(text, 3000);
		}
		
		public InfoEntry(String text, int millis) {
			this.text = text;
			this.millis = millis;
			this.start = System.currentTimeMillis();
		}
		
		public InfoEntry withColor(int color) {
			this.color = color;
			return this;
		}

		@Override
		public int compareTo(Object o) {
			
			if(!(o instanceof InfoEntry)) {
				return 0;
			}
			
			InfoEntry other = (InfoEntry) o;
			
			return this.millis < other.millis ? -1 : this.millis > other.millis ? 1 : 0;
		}
	}
}
