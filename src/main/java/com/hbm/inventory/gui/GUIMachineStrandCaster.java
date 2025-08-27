package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerMachineStrandCaster;
import com.hbm.inventory.material.Mats;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineStrandCaster;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIMachineStrandCaster extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_strand_caster.png");
	private TileEntityMachineStrandCaster caster;

	public GUIMachineStrandCaster(InventoryPlayer invPlayer, TileEntityMachineStrandCaster tedf) {
		super(new ContainerMachineStrandCaster(invPlayer, tedf));
		caster = tedf;

		this.xSize = 176;
		this.ySize = 214;
	}

	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		drawStackInfo(x, y, 16, 17);

		caster.water.renderTankInfo(this, x, y, guiLeft + 82, guiTop + 14, 16, 24);
		caster.steam.renderTankInfo(this, x, y, guiLeft + 82, guiTop + 65, 16, 24);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.caster.hasCustomInventoryName() ? this.caster.getInventoryName() : I18n.format(this.caster.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(caster.amount != 0) {

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

			int targetHeight = Math.min((caster.amount) * 79 / caster.getCapacity(), 92);

			int hex = caster.type.moltenColor;
			// hex = 0xC18336;
			Color color = new Color(hex);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(guiLeft + 17, guiTop + 93 - targetHeight, 176, 89 - targetHeight, 34, targetHeight);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1F, 1F, 1F, 0.3F);
			drawTexturedModalRect(guiLeft + 17, guiTop + 93 - targetHeight, 176, 89 - targetHeight, 34, targetHeight);
			GL11.glDisable(GL11.GL_BLEND);

		}
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor3f(255, 255, 255);

		caster.water.renderTank(guiLeft + 82, guiTop + 38, this.zLevel, 16, 24);
		caster.steam.renderTank(guiLeft + 82, guiTop + 89, this.zLevel, 16, 24);

	}

	protected void drawStackInfo(int mouseX, int mouseY, int x, int y) {

		List<String> list = new ArrayList();

		if(caster.type == null)
			list.add(EnumChatFormatting.RED + "Empty");
		else
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey(caster.type.getUnlocalizedName()) + ": " + Mats.formatAmount(caster.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + x, guiTop + y, 36, 81, mouseX, mouseY, list);
	}

}
