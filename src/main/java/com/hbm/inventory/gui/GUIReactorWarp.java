package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorWarp;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorWarp;
import com.hbm.util.I18nUtil;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
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
	private final NumberDisplay disp = new NumberDisplay(29, 20, 218, 18).setMaxMin(100, 0).setDigitLength(3);
	private final FluidTankGUI[] guiTanks = new FluidTankGUI[3];
	private final GuiTextField tInput;
	public GUIReactorWarp(TileEntityReactorWarp te)
	{
		super(new ContainerReactorWarp(te));
		warpCore = te;
		xSize = 175;
		ySize = 226;
		guiTanks[0] = new FluidTankGUI(te.getTanks().get(0), 8, 16, 16, 106);
		guiTanks[1] = new FluidTankGUI(te.getTanks().get(1), 151, 16, 16, 106);
		guiTanks[2] = new FluidTankGUI(te.getTanks().get(2), 80, 16, 16, 70);
		tInput = new GuiTextField(fontRendererObj, guiLeft + 27, guiTop + 39, 37, 7);
	}
	@Override
	public void initGui()
	{
		super.initGui();
		tInput.setTextColor(-1);
		tInput.setDisabledTextColour(-1);
		tInput.setEnableBackgroundDrawing(false);
		tInput.setMaxStringLength(3);
	}
	static final float scale1 = 1.5F;
	static final float scale2 = 2F;
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		final String name = I18nUtil.resolveKey(warpCore.getName());
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, color0);
		final int[] stats = warpCore.assembleStats();
		final String[] data = I18nUtil.resolveKeyArray("desc.gui.reactorAmat.box", stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], warpCore.currCore.isPresent() ? warpCore.currCore.get() : "NULL", warpCore.currCore.isPresent() ? warpCore.currCore.get().getDoesDecay() ? warpCore.currCore.get().getLife() : "∞" : "N/A", warpCore.currCatalyst.isPresent() ? warpCore.currCatalyst.get() : "NULL", warpCore.currCatalyst.isPresent() ? warpCore.currCatalyst.get().getLife() : "N/A", warpCore.currBooster.isPresent() ? warpCore.currBooster.get() : "NULL", warpCore.currBooster.isPresent() ? warpCore.currBooster.get().getLife() : "N/A");
		GL11.glScalef(1F / scale1, 1F / scale1, 1F / scale1);
		for (int i = 0; i < data.length; i++)
			fontRendererObj.drawString(data[i], (int) (12 * scale1), (int) ((131 + (i * 8)) * scale1), color1);
		GL11.glScalef(1F * scale1, 1F * scale1, 1F * scale1);
		GL11.glScalef(1F / scale2, 1F / scale2, 2F / scale2);
		String currText = tInput.getText();
		char cursor = getCursor();
		if (tInput.isFocused())
			currText = currText.substring(0, tInput.getCursorPosition()) + cursor + currText.substring(tInput.getCursorPosition(), currText.length());
		fontRendererObj.drawString(currText, (int) (27 * scale2), (int) (39 * scale2), color1);
		GL11.glScalef(1F * scale2, 1F * scale2, 1F * scale2);
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
		if (getButtonBool(x, y, 25, 105, 18, 18, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(warpCore, 0, 0));
		if (getButtonBool(x, y, 132, 105, 18, 18, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(warpCore, 1, 0));
		tInput.setFocused(getTextBool(x, y, tInput));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		disp.setNumber(100 - warpCore.getDelayLevel());
//		System.out.println(disp.getDispNumber());
		disp.drawNumber();
		if (warpCore.isCoreValid())
			drawTexturedModalRect(guiLeft + 79, guiTop + 87, 194, 18, 18, 18);
		if (warpCore.magnetsActivated())
			drawTexturedModalRect(guiLeft + 25, guiTop + 105, 180, 0, 18, 18);
		if (warpCore.isRunning())
			drawTexturedModalRect(guiLeft + 132, guiTop + 105, 180, 0, 18, 18);
		final int bos = getScaledBar(warpCore.currBooster.isPresent() ? warpCore.currBooster.get().getLife() : 0, 16, warpCore.currBooster.isPresent() ? warpCore.currBooster.get().getMaxLife() : 16);
		drawTexturedModalRect(guiLeft + 62, guiTop + 104 - bos, 176, 17 - bos, 3, bos);
		final int cat = getScaledBar(warpCore.currCatalyst.isPresent() ? warpCore.currCatalyst.get().getLife() : 0, 16, warpCore.currCatalyst.isPresent() ? warpCore.currCatalyst.get().getMaxLife() : 16);
		drawTexturedModalRect(guiLeft + 134, guiTop + 104 - cat, 176, 17 - cat, 3, cat);
		final int cor = getScaledBar(warpCore.currCore.isPresent() ? warpCore.currCore.get().getDoesDecay() ? warpCore.currCore.get().getLife() : 16 : 0, 16, warpCore.currCore.isPresent() ? warpCore.currCore.get().getDoesDecay() ? warpCore.currCore.get().getMaxLife() : 16 : 16);
		drawTexturedModalRect(guiLeft + 98, guiTop + 104 - cor, 176, 17 - cor, 3, cor);
		final int pow = (int) getScaledBar(warpCore.getPower(), 53, warpCore.getMaxPower());
		drawTexturedModalRect(guiLeft + 62, guiTop + 106, 198, 0, pow, 16);
		for (byte i = 0; i < 3; i++)
			guiTanks[i].updateTank(warpCore.getTanks().get(i));
		
		for (FluidTankGUI tank : guiTanks)
			tank.renderTank();
	}
	@Override
	protected void keyTyped(char c, int i)
	{
		if (tInput.textboxKeyTyped(c, i))
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(RefStrings.MODID, "misc.keyPress"), 1.0F));
		else
			super.keyTyped(c, i);
	}
}
