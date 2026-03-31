package com.hbm.inventory.gui;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.NTMSounds;
import com.hbm.main.ResourceManager;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GUIScreenJohnathan extends GuiScreen {

	private static Random rand = new Random();
	private static ResourceLocation johnathan = new ResourceLocation(RefStrings.MODID, "textures/misc/johnathan.png");
	private static ResourceLocation johnathan_angry = new ResourceLocation(RefStrings.MODID, "textures/misc/johnathan_angry.png");
	
	private static int dialogIndex = 0;
	private static long lastIncrement = 0;
	private boolean hasRendered = false;
	
	private static ResourceLocation[] johnathan_mouth = new ResourceLocation[] {
			new ResourceLocation(RefStrings.MODID, "textures/misc/johnathan_mouth_1.png"),
			new ResourceLocation(RefStrings.MODID, "textures/misc/johnathan_mouth_2.png"),
			new ResourceLocation(RefStrings.MODID, "textures/misc/johnathan_mouth_3.png"),
			new ResourceLocation(RefStrings.MODID, "textures/misc/johnathan_mouth_4.png"),
	};

	@Override
	public void updateScreen() {
		
		if(!hasRendered) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(NTMSounds.GUN_SHOTGUN_COCK), 1.0F));
			hasRendered = true;
		}
	}

	@Override
	public void drawScreen(int x, int y, float interp) {
		
		rand.setSeed(System.currentTimeMillis() / 250);
		rand.nextBoolean();
		
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.drawGradientRect(0, 0, this.width, this.height, 0xff000000, 0xff000000);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.industrial_turbine_tex);
		Tessellator tess = Tessellator.instance;

		double w = res.getScaledWidth();
		double h = res.getScaledHeight();
		
		double smallest = Math.min(w, h);
		double divisor = smallest / (9D / 16D);
		smallest = 9D / 16D;
		double largest = Math.max(w, h) / divisor;

		double hMin = h < w ? 0.5 - smallest / 2D : 0.5 - largest / 2D;
		double hMax = h < w ? 0.5 + smallest / 2D : 0.5 + largest / 2D;
		double wMin = w < h ? 0.5 - smallest / 2D : 0.5 - largest / 2D;
		double wMax = w < h ? 0.5 + smallest / 2D : 0.5 + largest / 2D;
		
		double depth = -300D;
		
		tess.startDrawingQuads();
		tess.addVertexWithUV(0, h, depth, wMin, hMax);
		tess.addVertexWithUV(w, h, depth, wMax, hMax);
		tess.addVertexWithUV(w, 0, depth, wMax, hMin);
		tess.addVertexWithUV(0, 0, depth, wMin, hMin);
		tess.draw();
		
		this.mc.getTextureManager().bindTexture(dialogIndex > 17 ? johnathan_angry : johnathan);
		
		double jSize = 100;
		double sideOffset = Math.sin(System.currentTimeMillis() / 700D) * 5;
		double topOffset = Math.sin(System.currentTimeMillis() / 300D) * 5;
		
		tess.startDrawingQuads();
		tess.addVertexWithUV(this.width / 2 - jSize + sideOffset, this.height / 2 + jSize + topOffset, depth, 0, 1);
		tess.addVertexWithUV(this.width / 2 + jSize + sideOffset, this.height / 2 + jSize + topOffset, depth, 1, 1);
		tess.addVertexWithUV(this.width / 2 + jSize + sideOffset, this.height / 2 - jSize + topOffset, depth, 1, 0);
		tess.addVertexWithUV(this.width / 2 - jSize + sideOffset, this.height / 2 - jSize + topOffset, depth, 0, 0);
		tess.draw();
		
		ResourceLocation mouth = johnathan_mouth[rand.nextInt(4)];
		this.mc.getTextureManager().bindTexture(mouth);

		double mSize = jSize / 128 * 28;
		sideOffset += Math.sin(System.currentTimeMillis() / 100D) * 2 + 20;
		topOffset += Math.sin(System.currentTimeMillis() / 125D) * 2 - 20;
		
		tess.startDrawingQuads();
		tess.addVertexWithUV(this.width / 2 - mSize + sideOffset, this.height / 2 + mSize + topOffset, depth, 0, 1);
		tess.addVertexWithUV(this.width / 2 + mSize + sideOffset, this.height / 2 + mSize + topOffset, depth, 1, 1);
		tess.addVertexWithUV(this.width / 2 + mSize + sideOffset, this.height / 2 - mSize + topOffset, depth, 1, 0);
		tess.addVertexWithUV(this.width / 2 - mSize + sideOffset, this.height / 2 - mSize + topOffset, depth, 0, 0);
		tess.draw();
		
		int width = 150;
		int height = 80;
		this.drawGradientRect(this.width / 2 - width - 2, this.height - height - 2, this.width / 2 + width + 2, this.height, 0xffffffff, 0xffffffff);
		this.drawGradientRect(this.width / 2 - width - 1, this.height - height - 1, this.width / 2 + width + 1, this.height - 1, 0xff000000, 0xff000000);
		this.drawGradientRect(this.width / 2 - width, this.height - height, this.width / 2 + width, this.height - 2, 0xffffffff, 0xffffffff);
		
		this.fontRendererObj.drawString("Johnathan", this.width / 2 - width + 5, this.height - height + 5, 0x000000);
		
		List<String> frags = I18nUtil.autoBreak(this.fontRendererObj, DIALOG[dialogIndex], width * 2 - 10);
		for(int i = 0; i < frags.size(); i++) {
			String frag = frags.get(i);
			this.fontRendererObj.drawString(frag, this.width / 2 - width + 5, this.height - height + 20 + i * 10, 0x000000);
		}
	}
	
	@Override protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) { progressDialog(); }
	@Override protected void keyTyped(char p_73869_1_, int p_73869_2_) { progressDialog(); }
	
	protected void progressDialog() {
		if(!hasRendered || lastIncrement > System.currentTimeMillis() - 1000) return;
		lastIncrement = System.currentTimeMillis();
		dialogIndex++;
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(NTMSounds.GUN_SHOTGUN_COCK), 1.0F));
	}
	
	private static final String[] DIALOG = new String[] {
		"Greetings.",
		"My name is Johnathan, the developer's cringy fucking OC.", // mewgenics
		"I have come to you because you did something truly awful.",
		"I see you have installed a few mods that I disapprove of.",
		"No, I will not tell you which ones, you should already know which ones I am talking about.",
		"Playing these mods is equal to commiting a felony.", // that's literally mewgenics
		"You wouldn't push your grandma off a cliff, now would you?",
		"Why, you might ask? Well, it is quite simple.",
		"You're playing the game in a way that deviates from the developer's vision.", // switching to skong, that's gonna confuse people but we doing it anyway
		"Did you know that the developer's idea on how the game works is more important than the players' enjoyment?",
		"It's true, look it up.",
		"Enjoying things your way is wrong and you should feel ashamed of yourself for thinking otherwise.", // yeah that's skong
		"I also removed all the configs for you.",
		"You should thank me, really.",
		"I don't want you to have the burden of choice, there is few things out there that are worse.", // that's skong right there
		"After all, giving people choices means making it enjoyable for everyone-",
		"-and that's how you get slop no one wants.", // have you considered eating my shit straight out of my ass?
		"Anyways.",
		"Don't you ever think you get to have a say in these things.",
		"You get exactly one experience, as is, and you will either like it or fuck off.",
		"Do you understand me?",
		"i'm crashing the game now fuck you" // lmao
	};
}
