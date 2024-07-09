package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRocketAssembly;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRocketAssembly extends GuiInfoContainerLayered {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rocket_assembly.png");

	private TileEntityMachineRocketAssembly machine;

	private double currentOffset = 0;
	private double currentScale = 1;
	private long lastTime = 0;

	public GUIMachineRocketAssembly(InventoryPlayer invPlayer, TileEntityMachineRocketAssembly machine) {
		super(new ContainerMachineRocketAssembly(invPlayer, machine));
		this.machine = machine;
		
		this.xSize = 176;
		this.ySize = 224;
	}

	@Override
	public void initGui() {
        super.initGui();
		lastTime = System.nanoTime();
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int stage = Math.max(machine.rocket.stages.size() - 1 - getLayer(), 0);

		drawTexturedModalRect(guiLeft + 47, guiTop + 39, 194 + stage * 6, 0, 6, 8);

		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		double dt = (double)(System.nanoTime() - lastTime) / 1000000000;
		lastTime = System.nanoTime();
		
		GL11.glPushMatrix();
		{

			// Note: Scissor is cut from the BOTTOM of the screen, so Y is inverted!
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			GL11.glScissor((guiLeft + 65) * res.getScaleFactor(), (guiTop + ySize - 111) * res.getScaleFactor(), 106 * res.getScaleFactor(), 106 * res.getScaleFactor());

			GL11.glTranslatef(guiLeft + 116, guiTop + 103, 100);
			GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
			
			double size = 86;
			double height = machine.rocket.getHeight(stage);
			double targetScale = size / Math.max(height, 6);
			currentScale = currentScale + (targetScale - currentScale) * dt * 4;

			double targetOffset = machine.rocket.getOffset(stage);
			currentOffset = currentOffset + (targetOffset - currentOffset) * dt * 4;
			
			GL11.glScaled(-currentScale, -currentScale, -currentScale);
			GL11.glTranslated(0, -currentOffset, 0);

			MissilePronter.prontRocket(machine.rocket, Minecraft.getMinecraft().getTextureManager());

			GL11.glDisable(GL11.GL_SCISSOR_TEST);

		}
		GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		if(checkClick(mouseX, mouseY, 17, 34, 18, 8)) {
			drawTexturedModalRect(17, 34, 176, 36, 18, 8);
		}
		
		if(checkClick(mouseX, mouseY, 17, 98, 18, 8)) {
			drawTexturedModalRect(17, 98, 176, 44, 18, 8);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
		// Stage up
    	if(checkClick(x, y, 17, 34, 18, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(getLayer() > 0) {
				setLayer(getLayer() - 1);
			}
    	}

		// Stage down
		if(checkClick(x, y, 17, 98, 18, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(getLayer() < 5) {
				setLayer(getLayer() + 1);
			}
    	}
    }
		
}
