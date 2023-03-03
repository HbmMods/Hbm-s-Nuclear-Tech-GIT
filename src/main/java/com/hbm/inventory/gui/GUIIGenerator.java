package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIIGenerator extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_igen.png");
	private TileEntityMachineIGenerator igen;

	public GUIIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator tedf) {
		super(new ContainerIGenerator(invPlayer, tedf));
		igen = tedf;

		this.xSize = 176;
		this.ySize = 237;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);

		this.drawElectricityInfo(this, x, y, guiLeft + 26, guiTop + 134, 142, 16, igen.power, igen.maxPower);
		
		for(int i = 0; i < 4; i++) {
			int fire = igen.burn[i];
			
			this.drawCustomInfoStat(x, y, guiLeft + 68 + (i % 2) * 18, guiTop + 34 + (i / 2) * 36, 14, 14, x, y, new String[] {(fire / 20) + "s"});
		}
		
		this.drawCustomInfoStat(x, y, guiLeft + 113, guiTop + 4, 54, 18, x, y, new String[] {"Heat generated"});

		igen.tanks[0].renderTankInfo(this, x, y, guiLeft + 80, guiTop + 112, 72, 16);
		igen.tanks[1].renderTankInfo(this, x, y, guiLeft + 114, guiTop + 33, 16, 70);
		igen.tanks[2].renderTankInfo(this, x, y, guiLeft + 150, guiTop + 33, 18, 70);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.igen.hasCustomInventoryName() ? this.igen.getInventoryName() : I18n.format(this.igen.getInventoryName());
		
		GL11.glPushMatrix();
		double scale = 0.75D;
		GL11.glScaled(scale, scale, 1);
		this.fontRendererObj.drawString(name, 22, 18, 0x303030);
		GL11.glPopMatrix();
		
		String spin = this.igen.spin + "";
		this.fontRendererObj.drawString(spin, 139 - this.fontRendererObj.getStringWidth(spin) / 2, 10, 0x00ff00);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float iinterpolation, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int water = igen.tanks[0].getFill() * 72 / igen.tanks[0].getMaxFill();
		drawTexturedModalRect(guiLeft + 80, guiTop + 112, 184, 14, water, 16);

		int power = (int) (igen.power * 142 / igen.maxPower);
		drawTexturedModalRect(guiLeft + 26, guiTop + 134, 0, 237, power, 16);
		
		for(int i = 0; i < 4; i++) {
			int fire = igen.burn[i];
			
			if(fire > 0) {
				drawTexturedModalRect(guiLeft + 68 + (i % 2) * 18, guiTop + 34 + (i / 2) * 36, 184, 0, 14, 14);
			}
		}
		
		if(igen.hasRTG) {
			drawTexturedModalRect(guiLeft + 9, guiTop + 34, 176, 0, 4, 89);
			drawTexturedModalRect(guiLeft + 51, guiTop + 34, 180, 0, 4, 89);
		}

		igen.tanks[1].renderTank(guiLeft + 114, guiTop + 103, this.zLevel, 16, 70);
		igen.tanks[2].renderTank(guiLeft + 150, guiTop + 103, this.zLevel, 16, 70);
	}
}
