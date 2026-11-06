package com.hbm.render.world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.render.world.skyfx.SkyEffect;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class RenderSkyEffects {
	
	public static final RenderSkyEffects RENDER_INSTANCE = new RenderSkyEffects();
	
	protected int activeDimensionId = 0;
	
	protected List<SkyEffect> effectList = new ArrayList();
	
	/// EXTERNAL ///
	public void addEffect(SkyEffect fx) {
		if(fx.world.provider.dimensionId != activeDimensionId) return;
		effectList.add(fx);
	}
	
	/// EVENT HANDLING ///
	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if(event.phase == event.phase.START && !mc.isGamePaused() && mc.theWorld != null) {
			effectList.removeIf(x -> { return x.getExpired(); });
			
			for(SkyEffect fx : effectList) fx.updateTick();
		}
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		
		float interp = event.partialTicks;
		World world = Minecraft.getMinecraft().theWorld;
		
		if(world.provider.dimensionId != activeDimensionId) {
			this.activeDimensionId = world.provider.dimensionId;
			this.clearRenderers();
		}
		
		updateRenderer(interp);
	}
	
	/// UPDATE TICKERS ///
	public void clearRenderers() {
		effectList.clear();
	}

	public void updateRenderer(float interp) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog) GL11.glDisable(GL11.GL_FOG);
		
		for(SkyEffect fx : effectList) {
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			fx.renderTick(interp);
		}
		
		if(fog) GL11.glEnable(GL11.GL_FOG);
	}
}
