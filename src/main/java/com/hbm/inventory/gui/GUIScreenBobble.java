package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.util.Tuple.Pair;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GUIScreenBobble extends GuiScreen {
	
	TileEntityBobble bobble;

	public GUIScreenBobble(TileEntityBobble bobble) {
		this.bobble = bobble;
	}

	@Override
	public void initGui() {
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.bobble"), 1.0F));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		
		this.drawDefaultBackground();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		double sizeX = 300;
		double sizeY = 150;
		double left = (this.width - sizeX) / 2;
		double top = (this.height - sizeY) / 2;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorRGBA_F(0F, 0.2F, 0F, 0.8F);
		tess.addVertex(left + sizeX, top, this.zLevel);
		tess.addVertex(left, top, this.zLevel);
		tess.addVertex(left, top + sizeY, this.zLevel);
		tess.addVertex(left + sizeX, top + sizeY, this.zLevel);
		tess.draw();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		
		int nextLevel = (int)top + 10;

		String bobbleTitle = "Nuclear Tech Commemorative Bobblehead";
		this.fontRendererObj.drawStringWithShadow(bobbleTitle, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(bobbleTitle) / 2), nextLevel, 0x00ff00);
		
		nextLevel += 10;
		
		String bobbleName = this.bobble.type.name;
		if(this.bobble.type == BobbleType.MELLOW)
			bobbleName = anagramIt(bobbleName, "GEORGEWILLIAMPATON");
		this.fontRendererObj.drawStringWithShadow(bobbleName, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(bobbleName) / 2), nextLevel, 0x009900);
		
		nextLevel += 20;
		
		if(this.bobble.type.contribution != null) {

			String title = "Has contributed";
			this.fontRendererObj.drawStringWithShadow(title, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(title) / 2), nextLevel, 0x00ff00);
			
			nextLevel += 10;


			String[] list = this.bobble.type.contribution.split("\\$");
			for(String text : list) {
				this.fontRendererObj.drawStringWithShadow(text, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(text) / 2), nextLevel, 0x009900);
				nextLevel += 10;
			}

			nextLevel += 10;
		}
		
		if(this.bobble.type.inscription != null) {

			String title = "On the bottom is the following inscription:";
			this.fontRendererObj.drawStringWithShadow(title, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(title) / 2), nextLevel, 0x00ff00);
			
			nextLevel += 10;

			String[] list = this.bobble.type.inscription.split("\\$");
			for(String text : list) {
				this.fontRendererObj.drawStringWithShadow(text, (int)(left + sizeX / 2 - this.fontRendererObj.getStringWidth(text) / 2), nextLevel, 0x009900);
				nextLevel += 10;
			}

			nextLevel += 10;
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	// Animates the letters (from -> to) back and forth over 1.5 seconds
	private String anagramIt(String from, String to) {
		double t = Math.sin((double)System.currentTimeMillis() / 1500.0) * 0.75 + 0.5;

		char[] lettersFrom = from.toCharArray();
		char[] lettersTo = to.toCharArray();
		boolean[] hasPairedLetter = new boolean[lettersFrom.length];
		List<Pair<Double, Character>> letterTargets = new ArrayList<Pair<Double, Character>>();

		for(int i = 0; i < lettersFrom.length; i++) {
			char letterFrom = lettersFrom[i];
			for(int o = 0; o < lettersTo.length; o++) {
				char letterTo = lettersTo[o];
				if(letterFrom == letterTo && !hasPairedLetter[o]) {
					double v = lerp((double)i, (double)o, t);
					letterTargets.add(new Pair<Double,Character>(v, lettersFrom[i]));
					hasPairedLetter[o] = true;
					break;
				}
			}
		}

		for(int i = 0; i < letterTargets.size(); i++) {
			for (int j = i + 1; j < letterTargets.size(); j++) {
				if (letterTargets.get(i).key > letterTargets.get(j).key) {
					Pair<Double, Character> temp = letterTargets.get(i);
					letterTargets.set(i, letterTargets.get(j));
					letterTargets.set(j, temp);
				}
			}
		}

		String anagrammedText = "";
		for(Pair<Double, Character> in : letterTargets) {
			anagrammedText += in.value;
		}

		return anagrammedText;
	}

	private double lerp(double a, double b, double t) {
		t = Math.max(Math.min(t, 1), 0);
		return a * (1 - t) + b * t;
	}
}
