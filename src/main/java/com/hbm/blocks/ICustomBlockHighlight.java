package com.hbm.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.opengl.GL11;

public interface ICustomBlockHighlight {

	@SideOnly(Side.CLIENT) public boolean shouldDrawHighlight(World world, int x, int y, int z);
	@SideOnly(Side.CLIENT) public void drawHighlight(DrawBlockHighlightEvent event, World world, int x, int y, int z);

	@SideOnly(Side.CLIENT)
	public static void setup() {
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
	}

	@SideOnly(Side.CLIENT)
	public static void cleanup() {
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
