package com.hbm.wiaj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ISpecialActor;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;
import com.hbm.wiaj.cannery.*;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

//krass
public class GuiWorldInAJar extends GuiScreen {
	
	private static final ResourceLocation guiUtil =  new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_utility.png");
	
	RenderBlocks renderer;
	JarScript jarScript;
	ItemStack icon;
	ActorFancyPanel titlePanel;
	CanneryBase[] seeAlso;
	ActorFancyPanel[] seeAlsoTitles;

	public GuiWorldInAJar(JarScript script, String title, ItemStack icon, CanneryBase... seeAlso) {
		super();
		this.fontRendererObj = Minecraft.getMinecraft().fontRenderer;
		
		this.jarScript = script;
		this.icon = icon;
		this.seeAlso = seeAlso;
		this.renderer = new RenderBlocks(jarScript.world);
		this.renderer.enableAO = true;
		
		this.titlePanel = new ActorFancyPanel(fontRendererObj, 40, 27, new Object[][] {{I18nUtil.resolveKey(title)}}, 0).setColors(CanneryBase.colorGold).setOrientation(Orientation.LEFT);
		this.seeAlsoTitles = new ActorFancyPanel[seeAlso.length];
		
		for(int i = 0; i < seeAlso.length; i++) {
			this.seeAlsoTitles[i] = new ActorFancyPanel(fontRendererObj, 40, 27 + 36 * (i + 1), new Object[][] {{I18nUtil.resolveKey(seeAlso[i].getName())}}, 0).setColors(CanneryBase.colorGrey).setOrientation(Orientation.LEFT);
		}
		
		
		/*WorldInAJar world = new WorldInAJar(15, 15, 15);
		
		testScript = new JarScript(world);
		JarScene startingScene = new JarScene(testScript);

		for(int y = 0; y < 15; y++) {
			for(int x = 0; x < 15; x++) {
				for(int z = 0; z < 15; z++) {
					
					if(y == 14) {
						startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.glass_boron));
						continue;
					}
					
					if(y > 0) {
						if(x == 0 || x == 14 || z == 0 || z == 14) {
							startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.glass_boron));
							continue;
						}
					}
					
					if(y == 0) {
						if(x == 0 || x == 14 || z == 0 || z == 14) {
							startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.concrete_colored, 6));
						} else {
							startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.concrete_colored, 0));
						}
					}
				}
			}
			startingScene.add(new ActionWait(1));
		}
		
		startingScene.add(new ActionWait(10));
		startingScene.add(new ActionSetBlock(2, 1, 2, ModBlocks.fallout, 0));
		startingScene.add(new ActionWait(10));
		startingScene.add(new ActionSetBlock(4, 1, 4, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 5, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 6, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 7, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 8, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 9, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 10, ModBlocks.conveyor, 6));
		startingScene.add(new ActionSetBlock(5, 1, 10, ModBlocks.conveyor, 4));
		
		startingScene.add(new ActionWait(5));
		startingScene.add(new ActionRotateBy(90, 0, 10));

		JarScene brickScene = new JarScene(testScript);

		for(int y = 1; y < 7; y++) {
			for(int x = 9; x < 12; x++) {
				for(int z = 6; z < 9; z++) {
					brickScene.add(new ActionSetBlock(x, y, z, Blocks.brick_block));
				}
			}
			brickScene.add(new ActionWait(2));
		}
		
		brickScene.add(new ActionRotateBy(-90, 0, 10));
		
		brickScene.add(new ActionCreateActor(0, new ActorTileEntity(new RenderStirling())));
		NBTTagCompound stirling = new NBTTagCompound();
		stirling.setDouble("x", 7);
		stirling.setDouble("y", 1);
		stirling.setDouble("z", 7);
		stirling.setInteger("rotation", 2);
		stirling.setBoolean("hasCog", true);
		brickScene.add(new ActionSetActorData(0, stirling));
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionUpdateActor(0, "speed", 5F));
		brickScene.add(new ActionWait(10));
		brickScene.add(new ActionUpdateActor(0, "speed", 10F));
		brickScene.add(new ActionOffsetBy(1, 0, 0, 10));
		brickScene.add(new ActionWait(10));
		brickScene.add(new ActionOffsetBy(0, 0, 1, 10));
		
		//brickScene.add(new ActionCreateActor(1, new ActorBasicPanel(0, 0, new Object[]{ new ItemStack(ModItems.ammo_arty, 1, 5)," shit *and* piss" })));

		brickScene.add(new ActionCreateActor(1, new ActorFancyPanel(this.fontRendererObj, 0, 30, new Object[][] {{"I've come to make an announcement: Shadow the Hedgehog's a"
				+ " bitch-ass motherfucker. He pissed on my fucking wife. That's right. He took his hedgehog fuckin' quilly dick out and he pissed on my FUCKING wife, and he"
				+ " said his dick was THIS BIG, and I said that's disgusting. So I'm making a callout post on my Twitter.com. Shadow the Hedgehog, you got a small dick. It's"
				+ " the size of this walnut except WAY smaller. And guess what? Here's what my dong looks like. That's right, baby. Tall points, no quills, no pillows, look "
				+ "at that, it looks like two balls and a bong. He fucked my wife, so guess what, I'm gonna fuck the earth. That's right, this is what you get! My SUPER LASE"
				+ "R PISS! Except I'm not gonna piss on the earth. I'm gonna go higher. I'm pissing on the MOOOON! How do you like that, OBAMA? I PISSED ON THE MOON, YOU IDI"
				+ "OT! You have twenty-three hours before the piss DROPLETS hit the fucking earth, now get out of my fucking sight before I piss on you too! "}}, 450)
				.setColors(0xFFFDCA88, 0xFFD57C4F, 0xFFAB4223, 0xff1A1F22).setOrientation(Orientation.BOTTOM)));
		brickScene.add(new ActionWait(200));
		
		this.testScript.addScene(startingScene).addScene(brickScene);*/
		//SKY BLUE: 0xffA5D9FF, 0xff39ACFF, 0xff1A6CA7, 0xff1A1F22
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		//shittyHack = this;
		
		try {
			if(jarScript != null) {
				jarScript.run();
			}
			this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.drawGuiContainerForegroundLayer(mouseX, mouseY);
			GL11.glEnable(GL11.GL_LIGHTING);
		} catch(Exception ex) {
			
			for(StackTraceElement line : ex.getStackTrace()) {
				this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + line.toString()));
			}
			
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
		}
		
		/*try {
			Tessellator.instance.draw(); //why, oh why is Tessellator.isDrawing private and without a getter?
		} catch(Exception x) {}*/
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {

		if(width / 2 - 12 <= mouseX && width / 2 - 12 + 24 > mouseX && height - 36 < mouseY && height - 36 + 24 >= mouseY) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			if(this.jarScript.isPaused()) {
				this.jarScript.unpause();
			} else {
				this.jarScript.pause();
			}
		}

		if(width / 2 - 12 - 36 <= mouseX && width / 2 - 12 - 36 + 24 > mouseX && height - 36 < mouseY && height - 36 + 24 >= mouseY) {
			
			if(this.jarScript.sceneNumber > 0) {
				this.jarScript.rewindOne();
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			}
		}

		if(width / 2 - 12 + 36 <= mouseX && width / 2 - 12 + 36 + 24 > mouseX && height - 36 < mouseY && height - 36 + 24 >= mouseY) {
			
			if(this.jarScript.sceneNumber < this.jarScript.scenes.size()) {
				this.jarScript.forwardOne();
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			}
		}
		
		for(int i = 0; i < seeAlso.length; i++) {
			
			if(15 <= mouseX && 39 > mouseX && 15 + 36 * (i + 1) < mouseY && 39 + 36 * (i + 1) >= mouseY) {
				CanneryBase cannery = seeAlso[i];
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				Minecraft.getMinecraft().thePlayer.closeScreen();
				FMLCommonHandler.instance().showGuiScreen(new GuiWorldInAJar(cannery.createScript(), cannery.getName(), cannery.getIcon(), cannery.seeAlso()));
				return;
			}
		}
	}

	private void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		for(Entry<Integer, ISpecialActor> actor : this.jarScript.actors.entrySet()) {
			GL11.glPushMatrix();
			actor.getValue().drawForegroundComponent(this.width, this.height, this.jarScript.ticksElapsed, this.jarScript.interp);
			GL11.glPopMatrix();
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
			List<Object[]> list = new ArrayList();
			list.add(new Object[] { (mouseX - width / 2) + " / " + (mouseY - height / 2) });
			this.drawStackText(list, mouseX - width / 2, mouseY - height / 2, this.fontRendererObj);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiUtil);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		int playButton = this.jarScript.isPaused() ? 64 : 40;
		
		if(width / 2 - 12 <= mouseX && width / 2 - 12 + 24 > mouseX && height - 36 < mouseY && height - 36 + 24 >= mouseY)
			this.drawTexturedModalRect(width / 2 - 12, height - 36, playButton, 24, 24, 24);
		else
			this.drawTexturedModalRect(width / 2 - 12, height - 36, playButton, 48, 24, 24);
		
		if(this.jarScript.sceneNumber == 0)
			this.drawTexturedModalRect(width / 2 - 12 - 36, height - 36, 88, 72, 24, 24);
		else if(width / 2 - 12 - 36 <= mouseX && width / 2 - 12 - 36 + 24 > mouseX && height - 36 < mouseY && height - 36 + 24 >= mouseY)
			this.drawTexturedModalRect(width / 2 - 12 - 36, height - 36, 88, 24, 24, 24);
		else
			this.drawTexturedModalRect(width / 2 - 12 - 36, height - 36, 88, 48, 24, 24);

		if(this.jarScript.sceneNumber >= this.jarScript.scenes.size())
			this.drawTexturedModalRect(width / 2 - 12 + 36, height - 36, 112, 72, 24, 24);
		else if(width / 2 - 12 + 36 <= mouseX && width / 2 - 12 + 36 + 24 > mouseX && height - 36 < mouseY && height - 36 + 24 >= mouseY)
			this.drawTexturedModalRect(width / 2 - 12 + 36, height - 36, 112, 24, 24, 24);
		else 
			this.drawTexturedModalRect(width / 2 - 12 + 36, height - 36, 112, 48, 24, 24);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		this.drawTexturedModalRect(15, 15, 136, 48, 24, 24);
		RenderHelper.enableGUIStandardItemLighting();
		itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, this.icon, 19, 19);
		itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, this.icon, 19, 19, null);
		RenderHelper.disableStandardItemLighting();

		if(15 <= mouseX && 39 > mouseX && 15 < mouseY && 39 >= mouseY) {
			this.titlePanel.drawForegroundComponent(0, 0, this.jarScript.ticksElapsed, this.jarScript.interp);
		}
		
		for(int i = 0; i < seeAlso.length; i++) {
			CanneryBase also = seeAlso[i];

			Minecraft.getMinecraft().getTextureManager().bindTexture(guiUtil);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.drawTexturedModalRect(15, 15 + 36 * (i + 1), 136, 72, 24, 24);
			RenderHelper.enableGUIStandardItemLighting();
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, also.getIcon(), 19, 19 + 36 * (i + 1));
			itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, also.getIcon(), 19, 19 + 36 * (i + 1), null);
			RenderHelper.disableStandardItemLighting();

			if(15 <= mouseX && 39 > mouseX && 15 + 36 * (i + 1) < mouseY && 39 + 36 * (i + 1) >= mouseY) {
				this.seeAlsoTitles[i].drawForegroundComponent(0, 0, this.jarScript.ticksElapsed, this.jarScript.interp);
			}
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {

		GL11.glPushMatrix();
		setupRotation();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator.instance.startDrawingQuads();
		
		for(int x = 0; x < jarScript.world.sizeX; x++) {
			for(int y = 0; y < jarScript.world.sizeY; y++) {
				for(int z = 0; z < jarScript.world.sizeZ; z++) {
					renderer.renderBlockByRenderType(jarScript.world.getBlock(x, y, z), x, y, z);
				}
			}
		}
		
		Tessellator.instance.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPushMatrix();
		setupRotation();
		RenderHelper.enableStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
		for(Entry<Integer, ISpecialActor> actor : this.jarScript.actors.entrySet()) {
			GL11.glPushMatrix();
			actor.getValue().drawBackgroundComponent(this.jarScript.world, this.jarScript.ticksElapsed, this.jarScript.interp);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	private void setupRotation() {
		
		double scale = -10;
		
		GL11.glTranslated(width / 2, height / 2, 400);
		GL11.glScaled(scale, scale, scale);
		GL11.glScaled(1, 1, 0.5); //incredible flattening power
		
		double zoom = jarScript.zoom();
		GL11.glScaled(zoom, zoom, zoom);

		GL11.glRotated(jarScript.pitch(), 1, 0, 0);
		GL11.glRotated(jarScript.yaw(), 0, 1, 0);
		GL11.glTranslated(jarScript.world.sizeX / -2D, -jarScript.world.sizeY / 2D , jarScript.world.sizeZ / -2D);
		GL11.glTranslated(jarScript.offsetX(), jarScript.offsetY(), jarScript.offsetZ());
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//public static GuiWorldInAJar shittyHack;

	public void drawStackText(List<Object[]> lines, int x, int y, FontRenderer font) {

		x += width / 2;
		y += height / 2;
		
		if(!lines.isEmpty()) {
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);

			int height = 0;
			int longestline = 0;
			Iterator iterator = lines.iterator();

			while(iterator.hasNext()) {
				Object[] line = (Object[]) iterator.next();
				int lineWidth = 0;
				
				boolean hasStack = false;
				
				for(Object o : line) {
					
					if(o instanceof String) {
						lineWidth += font.getStringWidth((String) o);
					} else {
						lineWidth += 18;
						hasStack = true;
					}
				}
				
				if(hasStack) {
					height += 18;
				} else {
					height += 10;
				}

				if(lineWidth > longestline) {
					longestline = lineWidth;
				}
			}

			int minX = x + 12;
			int minY = y - 12;

			if(minX + longestline > this.width) {
				minX -= 28 + longestline;
			}

			if(minY + height + 6 > this.height) {
				minY = this.height - height - 6;
			}

			this.zLevel = 300.0F;
			itemRender.zLevel = 300.0F;
			//int j1 = -267386864;
			int colorBg = 0xF0100010;
			this.drawGradientRect(minX - 3, minY - 4, minX + longestline + 3, minY - 3, colorBg, colorBg);
			this.drawGradientRect(minX - 3, minY + height + 3, minX + longestline + 3, minY + height + 4, colorBg, colorBg);
			this.drawGradientRect(minX - 3, minY - 3, minX + longestline + 3, minY + height + 3, colorBg, colorBg);
			this.drawGradientRect(minX - 4, minY - 3, minX - 3, minY + height + 3, colorBg, colorBg);
			this.drawGradientRect(minX + longestline + 3, minY - 3, minX + longestline + 4, minY + height + 3, colorBg, colorBg);
			//int k1 = 1347420415;
			int color0 = 0x505000FF;
			//int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
			int color1 = (color0 & 0xFEFEFE) >> 1 | color0 & 0xFF000000;
			this.drawGradientRect(minX - 3, minY - 3 + 1, minX - 3 + 1, minY + height + 3 - 1, color0, color1);
			this.drawGradientRect(minX + longestline + 2, minY - 3 + 1, minX + longestline + 3, minY + height + 3 - 1, color0, color1);
			this.drawGradientRect(minX - 3, minY - 3, minX + longestline + 3, minY - 3 + 1, color0, color0);
			this.drawGradientRect(minX - 3, minY + height + 2, minX + longestline + 3, minY + height + 3, color1, color1);

			for(int index = 0; index < lines.size(); ++index) {
				
				Object[] line = (Object[]) lines.get(index);
				int indent = 0;
				boolean hasStack = false;
				
				for(Object o : line) {
					if(!(o instanceof String)) {
						hasStack = true;
					}
				}
				
				for(Object o : line) {
					
					if(o instanceof String) {
						font.drawStringWithShadow((String) o, minX + indent, minY + (hasStack ? 4 : 0), -1);
						indent += font.getStringWidth((String) o) + 2;
					} else {
						ItemStack stack = (ItemStack) o;
						GL11.glColor3f(1F, 1F, 1F);

						if(stack.stackSize == 0) {
							this.drawGradientRect(minX + indent - 1, minY - 1, minX + indent + 17, minY + 17, 0xffff0000, 0xffff0000);
							this.drawGradientRect(minX + indent, minY, minX + indent + 16, minY + 16, 0xffb0b0b0, 0xffb0b0b0);
						}
						itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, minX + indent, minY);
						itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, minX + indent, minY, null);
						RenderHelper.disableStandardItemLighting();
						GL11.glDisable(GL11.GL_DEPTH_TEST);
						indent += 18;
					}
				}

				if(index == 0) {
					minY += 2;
				}

				minY += hasStack ? 18 : 10;
			}

			this.zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
}
