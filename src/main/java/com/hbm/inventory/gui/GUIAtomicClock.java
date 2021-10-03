package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAtomicClock;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityAtomicClock;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAtomicClock extends GuiInfoContainer
{
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/machine/gui_atomic_clock.png");
	private final TileEntityAtomicClock clock;
	private final FluidTankGUI clockTank;
	private final NumberDisplay[] displays = new NumberDisplay[3];
	public GUIAtomicClock(InventoryPlayer invPlayer, TileEntityAtomicClock te)
	{
		super(new ContainerAtomicClock(invPlayer, te));
		clock = te;
		xSize = 256;
		ySize = 187;
		displays[0] = new NumberDisplay(49, 23, 43, 187).setDigitLength(6).setMaxMin(9.9999F, 0);
		displays[1] = new NumberDisplay(101, 23, 43, 187).setMaxMin(100, 0);
		displays[2] = new NumberDisplay(49, 73, 43, 187).setDigitLength(28).setPadding(1);
		clockTank = new FluidTankGUI(clock.getTanks().get(0), 26, 89, 16, 73);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 16, 16, 55, clock.getPower(), clock.getMaxPower());
//		clock.getTanks().get(0).renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 16, 16, 73);
//		clockTank.updateTank(clock.getTanks().get(0));
		clockTank.renderTankInfo(mouseX, mouseY);
		if (clock.isOn && clock.getTanks().get(0).getFill() > 0 && clock.getPower() >= clock.consumption)
		{
			drawCustomInfoStat(mouseX, mouseY, guiLeft + 47, guiTop + 21, 46, 16, mouseX, mouseY, String.valueOf(displays[0].getNumber()));
			drawCustomInfoStat(mouseX, mouseY, guiLeft + 100, guiTop + 21, 28, 16, mouseX, mouseY, String.valueOf(displays[1].getNumber().byteValue()));
			drawCustomInfoStat(mouseX, mouseY, guiLeft + 47, guiTop + 71, 198, 16, mouseX, mouseY, String.valueOf(displays[2].getNumber().longValue()));
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i)
	{
		super.mouseClicked(mouseX, mouseY, i);
		if (getButtonBool(mouseX, mouseY, 44, 43, 18, 18))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(clock, 0, 0));
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
		
		int pow = (int) getScaledBar(clock.getPower(), 54, clock.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - pow, 49, 241 - pow, 16, pow);
		if (clock.isOn)
		{
			drawTexturedModalRect(guiLeft + 44, guiTop + 43, 67, 187, 18, 18);
			if (clock.getTanks().get(0).getFill() > 0 && clock.getPower() >= clock.consumption)
				for (int i = 0; i < 3; i++)
					displays[i].drawNumber();
		}
//		clockTank.updateTank(clock.getTanks().get(0));
		clockTank.renderTank();
		
//		FluidTank cTank = clock.getTanks().get(0);
//		mc.getTextureManager().bindTexture(cTank.getSheet());
//		System.out.println(displays[0].getDispNumber());
//		cTank.renderTank(this, guiLeft + 26, guiTop + 89, cTank.getTankType().textureX() * FluidTank.x, cTank.getTankType().textureY() * FluidTank.y, 16, 73);
	}
}
