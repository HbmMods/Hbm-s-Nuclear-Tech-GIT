package com.hbm.particle.psys.engine;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;

@SideOnly(Side.CLIENT)
public class EventHandlerParticleEngine {

	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		
		if(event.phase == event.phase.START) {
			
		}
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		//float interp = event.partialTicks;
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		/* create new engine instance on every new world load (when joining servers, switching dimensions, etc), prevents particles from persisting between worlds */
		ParticleEngine.INSTANCE = new ParticleEngine(event.world, Minecraft.getMinecraft().renderEngine);
	}
}
