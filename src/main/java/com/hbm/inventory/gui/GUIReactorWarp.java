package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorWarp;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorWarp;
import com.hbm.util.I18nUtil;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
/**
 * Intended to supersede {@link#GUIReactorAmat}
 * @author UFFR
 *
 */
public class GUIReactorWarp extends GuiInfoContainer
{
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/reactors/gui_reactor_amat_new.png");
	private final TileEntityReactorWarp warpCore;
	private final NumberDisplay disp = new NumberDisplay(29, 20, 218, 18).setMaxMin(100, 0);
	private final FluidTankGUI[] guiTanks = new FluidTankGUI[3];
	public GUIReactorWarp(TileEntityReactorWarp te)
	{
		super(new ContainerReactorWarp(te));
		warpCore = te;
		xSize = 175;
		ySize = 226;
		guiTanks[0] = new FluidTankGUI(te.getTanks().get(0), 8, 16, 16, 122);
		guiTanks[1] = new FluidTankGUI(te.getTanks().get(1), 151, 16, 16, 122);
		guiTanks[2] = new FluidTankGUI(te.getTanks().get(2), 80, 16, 16, 122);
	}
	static final float scale = 2;
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		final String name = I18nUtil.resolveKey(warpCore.getName());
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, color0);
		final String[] data = I18nUtil.resolveKeyArray("desc.gui.reactorAmat.box", warpCore.assembleStats());
		GL11.glScalef(1F / scale, 1F / scale, 1F / scale);
		for (int i = 0; i < data.length; i++)
			fontRendererObj.drawString(data[i], 80 * 2, (40 + (i * 5)) * 2, color1);
		GL11.glScalef(1F * scale, 1F * scale, 1F * scale);
	}
	
	@Override
	public void drawScreen(int x, int y, float f)
	{
		super.drawScreen(x, y, f);
		drawElectricityInfo(x, y, 62, 103, 52, 16, warpCore.getPower(), warpCore.getMaxPower());
		for (FluidTankGUI tank : guiTanks)
			tank.renderTankInfo(x, y);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int i)
	{
		super.mouseClicked(x, y, i);
		if (getButtonBool(x, y, 25, 108, 18, 18, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(warpCore, 0, 0));
		if (getButtonBool(x, y, 100, 108, 18, 18, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(warpCore, 1, 0));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		disp.setNumber(100 - warpCore.getDelayLevel());
		disp.drawNumber();
		final int pow = (int) getScaledBar(warpCore.getPower(), 52, warpCore.getMaxPower());
		drawTexturedModalRect(guiLeft + 62, guiTop + 106, 198, 0, pow, 16);
		
		for (FluidTankGUI tank : guiTanks)
			tank.renderTank();
	}

}
