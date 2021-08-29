package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerAtomicClock;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityAtomicClock;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUIAtomicClock extends GuiInfoContainer
{
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/machine/gui_atomic_clock.png");
	private TileEntityAtomicClock clock;
	private NumberDisplay[] displays = new NumberDisplay[3];
	public GUIAtomicClock(InventoryPlayer invPlayer, TileEntityAtomicClock te)
	{
		super(new ContainerAtomicClock(invPlayer, te));
		clock = te;
		xSize = 256;
		ySize = 187;
		displays[0] = new NumberDisplay(49, 23, 43, 187).setDigitLength(6).setMaxMin(9.9999F, 0);
		displays[1] = new NumberDisplay(101, 23, 43, 187).setMaxMin(100, 0);
		displays[2] = new NumberDisplay(49, 73, 43, 187).setDigitLength(28).setPadding(1);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 16, 16, 55, clock.getPower(), clock.getMaxPower());
		clock.getTanks().get(0).renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 16, 16, 73);
		final String[] time = new String[] {String.valueOf(displays[0].getNumber())};
		final String[] day = new String[] {String.valueOf(displays[1].getNumber().byteValue())};
		final String[] year = new String[] {String.valueOf(displays[2].getNumber().longValue())};
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 47, guiTop + 21, 46, 16, mouseX, mouseY, time);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 100, guiTop + 21, 28, 16, mouseX, mouseY, day);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 47, guiTop + 71, 198, 16, mouseX, mouseY, year);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		final String name = I18nUtil.resolveKey(clock.getInventoryName());
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, color0);
		fontRendererObj.drawString(I18nUtil.resolveKey("container.inventory"), 47, ySize - 96 + 2, color0);
		
		final String[] labels = I18nUtil.resolveKeyArray("desc.gui.atomicClock");
		final float scale = 2F;
		GL11.glScalef(1F / scale, 1F / scale, 1F);
		fontRendererObj.drawString(labels[0], 69 * 2 - fontRendererObj.getStringWidth(labels[0]) / 2, 17 * 2, 0);
		fontRendererObj.drawString(labels[1], 112 * 2 - fontRendererObj.getStringWidth(labels[1]) / 2, 17 * 2, 0);
		fontRendererObj.drawString(labels[2], 146 * 2 - fontRendererObj.getStringWidth(labels[2]) / 2, 67 * 2, 0);
		GL11.glScalef(scale, scale, 1F);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		displays[0].setNumber(clock.time);
		displays[0].setFloat((byte) 4);
		displays[1].setNumber(clock.day);
		displays[2].setNumber(clock.year);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		FluidTank cTank = clock.getTanks().get(0);
//		System.out.println(displays[0].getDispNumber());
		cTank.renderTank(this, guiLeft + 26, guiTop + 88, cTank.getTankType().textureX() * FluidTank.x, cTank.getTankType().textureY() * FluidTank.y, 16, 73);
		for (int i = 0; i < 3; i++)
			displays[i].drawNumber();
	}
}
