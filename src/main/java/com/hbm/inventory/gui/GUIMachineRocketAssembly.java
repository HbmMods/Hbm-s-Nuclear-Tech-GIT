package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRocketAssembly;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.MissilePart;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRocketAssembly extends GuiInfoContainerLayered {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rocket_assembly.png");

	private TileEntityMachineRocketAssembly machine;

	public GUIMachineRocketAssembly(InventoryPlayer invPlayer, TileEntityMachineRocketAssembly machine) {
		super(new ContainerMachineRocketAssembly(invPlayer, machine));
		this.machine = machine;
		
		this.xSize = 256;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		GL11.glPushMatrix();
		{

			GL11.glTranslatef(guiLeft + 139, guiTop + 146, 100);
			GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
			
			double size = 8 * 16;
			double height = machine.rocket.getHeight();
			double scale = size / Math.max(height, 6);
			
			GL11.glScaled(-scale, -scale, -scale);

			MissilePronter.prontRocket(machine.rocket, Minecraft.getMinecraft().getTextureManager());

		}
		GL11.glPopMatrix();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
		// Stage up
    	if(checkClick(x, y, 4, 38, 18, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(getLayer() > 0) {
				setLayer(getLayer() - 1);
			}
    	}

		// Stage down
		if(checkClick(x, y, 4, 102, 18, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(getLayer() < 5) {
				setLayer(getLayer() + 1);
			}
    	}
    }
		
}
