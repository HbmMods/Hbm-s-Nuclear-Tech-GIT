package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTsukuyomi;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.turret.TileEntityTsukuyomi;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import scala.annotation.meta.field;

public class GUITsukuyomi extends GuiInfoContainer
{
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/weapon/gui_turret_twr.png");
	private static final int color1 = 0x00ff00;
	private TileEntityTsukuyomi twr;
	private GuiTextField tField;
	private NumberDisplay disp;
	public GUITsukuyomi(InventoryPlayer invPlayer, TileEntityTsukuyomi tedf)
	{
		super(new ContainerTsukuyomi(invPlayer, tedf));
		twr = tedf;
		xSize = 191;
		ySize = 215;
		disp = new NumberDisplay(127, 22, 251, 159).setPadNumber().setDigitLength((byte) 4);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		tField = new GuiTextField(fontRendererObj, guiLeft + 15, guiTop + 24, 46, 10);
		tField.setTextColor(-1);
		tField.setDisabledTextColour(-1);
		tField.setEnableBackgroundDrawing(false);
		tField.setMaxStringLength(16);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		drawElectricityInfo(this, mouseX, mouseY, guiLeft + 166, guiTop + 18, 16, 77, twr.getPower(), twr.maxPower);
		final String[] search = I18nUtil.resolveKeyArray("twr.gui.search");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 69, guiTop + 18, 37, 16, guiLeft + 95, guiTop + 33, search);
		final String[] calibrate = I18nUtil.resolveKeyArray("twr.gui.calibrate");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 69, guiTop + 69, 37, 16, guiLeft + 95, guiTop + 83, calibrate);
		final String[] purify = I18nUtil.resolveKeyArray("twr.gui.purify");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 128, guiTop + 69, 37, 16, guiLeft + 154, guiTop + 83, purify);
		final String[] add = I18nUtil.resolveKeyArray("twr.gui.add");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 70, guiTop + 35, 16, 16, guiLeft + 77, guiTop + 50, add);
		final String[] remove = I18nUtil.resolveKeyArray("twr.gui.remove");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 89, guiTop + 35, 16, 16, guiLeft + 95, guiTop + 50, remove);
		final String[] up = I18nUtil.resolveKeyArray("twr.gui.up");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 70, guiTop + 52, 16, 16, guiLeft + 77, guiTop + 68, up);
		final String[] down = I18nUtil.resolveKeyArray("twr.gui.down");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 89, guiTop + 52, 16, 16, guiLeft + 95, guiTop + 68, down);
		final String[] ammo = new String[] {disp.getNumber().toString()};
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 134, guiTop + 20, 28, 16, mouseX, mouseY, ammo);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		String name = I18nUtil.resolveKey(twr.getInventoryName());
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		fontRendererObj.drawString(I18nUtil.resolveKey("container.inventory"), 14, ySize - 96 + 2, 4210752);
		
		String currText = tField.getText();
		String cursor = System.currentTimeMillis() % 1000 < 500 ? " " : "│";//"█";
		if (tField.isFocused())
			currText = currText.substring(0, tField.getCursorPosition()) + cursor + currText.substring(tField.getCursorPosition(), currText.length());
		
		GL11.glScaled(0.5D, 0.5D, 1D);
		String[] recal = I18nUtil.resolveKeyArray("twr.gui.recalibrating", twr.getCooldown());
		if (twr.getCooldown() == 0)
		{
			fontRendererObj.drawString(currText, 15 * 2, 24 * 2, color1);
			fontRendererObj.drawString(I18nUtil.resolveKey("twr.target", twr.currPlayer == null ? I18nUtil.resolveKey("twr.target.none") : twr.currPlayer.getDisplayName()), 72 * 2, 90 * 2, color1);
			fontRendererObj.drawString(I18nUtil.resolveKey("twr.result", twr.isReady ? twr.getAttackResult() : "N/A"), 72 * 2, 95 * 2, color1);
			fontRendererObj.drawString(I18nUtil.resolveKey("twr.sign", twr.currPlayer == null ? "N/A" : ""), 72 * 2, 100 * 2, color1);
		}
		else
			for (int i = 0; i < recal.length; i++)
				fontRendererObj.drawString(recal[i], 72 * 2, (90 + (i * 5)) * 2, color1);

		if (twr.currPlayer != null)
			mc.standardGalacticFontRenderer.drawString(twr.getPlayerHash(), 72 * 2, 105 * 2, color1);
		fontRendererObj.drawString(">", 12 * 2, (38 + (twr.index * 5)) * 2, color1);
		if (!twr.getTargets().isEmpty())
			for (int i = 0; i < twr.getTargets().size(); i++)
				fontRendererObj.drawString(twr.getTargets().get(i), 15 * 2, (38 + (i * 5)) * 2, color1);
		GL11.glScaled(2F, 2F, 1D);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i)
	{
		super.mouseClicked(mouseX, mouseY, i);
		tField.setFocused(getTextBool(mouseX, mouseY, tField));
		// Search button
		if (getButtonBool(mouseX, mouseY, 70, 18, 35, 13, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(twr, 0, 0));
		// Calibrate button
		if (getButtonBool(mouseX, mouseY, 70, 69, 35, 13, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(twr, 1, 0));
		// Purify button
		if (getButtonBool(mouseX, mouseY, 129, 69, 35, 13, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(twr, 2, 0));
		// Index up
		if (getButtonBool(mouseX, mouseY, 70, 52, 16, 16, guiLeft, guiTop) || i == 3)
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(twr, 3, 0));
		// Index down
		if (getButtonBool(mouseX, mouseY, 89, 52, 16, 16, guiLeft, guiTop) || i == 4)
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(twr, 3, 1));
		// Add player to list
		if (getButtonBool(mouseX, mouseY, 70, 35, 16, 16, guiLeft, guiTop))
		{
//			System.out.println("Sent commands");
			NBTTagCompound data = new NBTTagCompound();
			data.setString("name", tField.getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, twr));
			tField.setText("");
		}
		// Remove player from list
		if (getButtonBool(mouseX, mouseY, 89, 35, 16, 16, guiLeft, guiTop))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(twr, 4, 1));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		// Buttons and checklist lights
		if (twr.getPower() == twr.getMaxPower())
			drawTexturedModalRect(guiLeft + 106, guiTop + 18, 227, 72, 7, 7);
		if (twr.gotTargets)
		{
			drawTexturedModalRect(guiLeft + 106, guiTop + 26, 227, 72, 7, 7);
			drawTexturedModalRect(guiLeft + 69, guiTop + 18, 219, 109, 37, 15);
		}
		if (twr.isReady)
		{
			drawTexturedModalRect(guiLeft + 106, guiTop + 34, 227, 72, 7, 7);
			drawTexturedModalRect(guiLeft + 69, guiTop + 69, 219, 94, 37, 15);
		}
		if (twr.hasTachyon)
			drawTexturedModalRect(guiLeft + 106, guiTop + 42, 227, 72, 7, 7);
		if (twr.isOn)
			drawTexturedModalRect(guiLeft + 128, guiTop + 69, 219, 79, 37, 15);
		// Number display
		disp.setNumber(twr.getCooldown() > 0 ? twr.getCooldown() : twr.getAmmoCount());
		disp.drawNumber();
		
		int pow = (int) getScaledBar(twr.getPower(), 77, twr.maxPower);
		drawTexturedModalRect(guiLeft + 165, guiTop + 95 - pow, 238, 78 - pow, 18, pow);
	}
	
	@Override
	protected void keyTyped(char c, int i)
	{
		if (tField.textboxKeyTyped(c, i))
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:misc.keyPress"), 1.0F));
		else
			super.keyTyped(c, i);
	}
}
