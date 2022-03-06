package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface ILookOverlay {

	@SideOnly(Side.CLIENT)
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z);

	@SideOnly(Side.CLIENT)
	public static void printGeneric(RenderGameOverlayEvent.Pre event, String title, int titleCol, int bgCol, List<String> text) {

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution resolution = event.resolution;

		GL11.glPushMatrix();

		int pX = resolution.getScaledWidth() / 2 + 8;
		int pZ = resolution.getScaledHeight() / 2;

		List<String> exceptions = new ArrayList();
		exceptions.add("x");
		exceptions.add("y");
		exceptions.add("z");
		exceptions.add("items");
		exceptions.add("id");

		mc.fontRenderer.drawString(title, pX + 1, pZ - 9, bgCol);
		mc.fontRenderer.drawString(title, pX, pZ - 10, titleCol);

		for(String line : text) {

			if(exceptions.contains(line))
				continue;

			mc.fontRenderer.drawStringWithShadow(line, pX, pZ, 0xFFFFFF);
			pZ += 10;
		}

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
}
