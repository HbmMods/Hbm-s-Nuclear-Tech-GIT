package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLunarOni;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.turret.TileEntityLunarOni;
import com.hbm.util.I18nUtil;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUILunarOni extends GuiInfoContainer
{
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/weapon/gui_turret_lunar_oni.png");
	private static final int color1 = 0x00ff00;
	private TileEntityLunarOni luna;
	private NumberDisplay[] displays = new NumberDisplay[3];
	private GuiTextField tField;
	private byte timer = 0;
	public GUILunarOni(InventoryPlayer invPlayer, TileEntityLunarOni te)
	{
		super(new ContainerLunarOni(invPlayer, te));
		luna = te;
		xSize = 191;
		ySize = 215;
		displays[0] = new NumberDisplay(69, 26, 251, 142).setMaxMin(360, 0);
		displays[1] = new NumberDisplay(69, 51, 251, 142).setMaxMin(50, -50);
		displays[2] = new NumberDisplay(98, 26, 251, 142).setDigitLength((byte) 4).setMaxMin(100, 0);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		tField = new GuiTextField(fontRendererObj, guiLeft + 112, guiTop + 45, 29, 12);
		tField.setTextColor(-1);
		tField.setDisabledTextColour(-1);
		tField.setEnableBackgroundDrawing(false);
		tField.setMaxStringLength(3);
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		drawElectricityInfo(this, mouseX, mouseY, guiLeft + 166, guiTop + 19, 16, 95, luna.getPower(), luna.getMaxPower());
		drawElectricityInfo(this, mouseX, mouseY, guiLeft + 148, guiTop + 19, 16, 95, luna.getBuffer(), luna.maxBuffer);
		final String[] dir = new String[] {new Float(luna.getDirection()).toString()};
		final String[] ele = new String[] {new Float(luna.getElevation()).toString()};
		final String[] pow = new String[] {new Byte(displays[2].getNumber().byteValue()).toString() + "%"};
		final String[] purify = I18nUtil.resolveKeyArray("twr.gui.purify");
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 24, 37, 16, mouseX, mouseY, dir);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 67, guiTop + 49, 28, 16, mouseX, mouseY, ele);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 111, guiTop + 24, 32, 16, mouseX, mouseY, pow);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 92, 35, 16, guiLeft + 85, guiTop + 107, purify);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i)
	{
		super.mouseClicked(mouseX, mouseY, i);
		tField.setFocused(getTextBool(mouseX, mouseY, tField));
		if (getButtonBool(mouseX, mouseY, 20, 18, 35, 13))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(luna, 0, 0));
		if (getButtonBool(mouseX, mouseY, 20, 45, 35, 13))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(luna, 0, 1));
		if (getButtonBool(mouseX, mouseY, 20, 72, 35, 13))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(luna, 0, 2));
		if (getButtonBool(mouseX, mouseY, 20, 99, 35, 13))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(luna, 0, 3));
		if (getButtonBool(mouseX, mouseY, 109, 59, 35, 13))
		{
			NBTTagCompound data = new NBTTagCompound();
			data.setByte("input", validateTextInput(tField.getText(), 0, 100).byteValue());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, luna));
			tField.setText("");
		}
		if (getButtonBool(mouseX, mouseY, 63, 92, 35, 13))
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(luna, 1, 0));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		final String name = I18nUtil.resolveKey(luna.getInventoryName());
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, color0);
		fontRendererObj.drawString(I18nUtil.resolveKey("container.inventory"), 14, ySize - 96 + 2, color0);
		
		String currText = tField.getText();
		String cursor = System.currentTimeMillis() % 1000 < 500 ? " " : "│";
		if (tField.isFocused())
			currText = currText.substring(0, tField.getCursorPosition()) + cursor + currText.substring(tField.getCursorPosition(), currText.length());
		fontRendererObj.drawString(currText, 116, 47, color1);

		final float scale = 2F;
		GL11.glScalef(1F / scale, 1F / scale, 1F);
		final String[] modes = I18nUtil.resolveKeyArray("desc.gui.lunarOni.mode");
		for (int i = 0; i < 4; i++)
			fontRendererObj.drawString(modes[i], 37 * 2 - fontRendererObj.getStringWidth(modes[i]) / 2, ((luna.currMode.ordinal() == i ? 23 : 21) + (i * 27)) * 2, 0);
		final String dir = I18nUtil.resolveKey("desc.gui.lunarOni.direction");
		final String ele = I18nUtil.resolveKey("desc.gui.lunarOni.elevation");
		final String per = I18nUtil.resolveKey("desc.gui.lunarOni.percentage");
		final String buf = I18nUtil.resolveKey("desc.gui.lunarOni.buffer");
		final String pow = I18nUtil.resolveKey("desc.gui.lunarOni.power");
		final String cor = I18nUtil.resolveKey("desc.gui.lunarOni.coordinates");
		fontRendererObj.drawString(dir, 80 * 2 - fontRendererObj.getStringWidth(dir) / 2, 20 * 2, 0);
		fontRendererObj.drawString(ele, 80 * 2 - fontRendererObj.getStringWidth(ele) / 2, 45 * 2, 0);
		fontRendererObj.drawString(per, 127 * 2 - fontRendererObj.getStringWidth(per) / 2, 14 * 2, 0);
		fontRendererObj.drawString(buf, 156 * 2 - fontRendererObj.getStringWidth(buf) / 2, 14 * 2, 0);
		fontRendererObj.drawString(pow, 174 * 2 - fontRendererObj.getStringWidth(pow) / 2, 14 * 2, 0);
		
		fontRendererObj.drawString(I18nUtil.resolveKey("twr.target", ""), 108 * 2, 87 * 2, color1);
		fontRendererObj.drawString(luna.getEntityName(), 108 * 2, 92 * 2, color1);
		fontRendererObj.drawString(cor, 108 * 2, 102 * 2, color1);
		fontRendererObj.drawString(luna.getEntityCoordinates(), 108 * 2, 107 * 2, color1);
		GL11.glScalef(scale, scale, 1F);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		displays[0].setNumber((int) luna.getDirection());
		displays[1].setNumber((int) luna.getElevation());
		displays[2].setNumber(luna.getLevel());

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int lightOffset = 0;
		int buttonOffset = 0;
		switch(luna.currMode)
		{
		case DISABLED:
			lightOffset = 22;
			buttonOffset = 18;
			break;
		case TARG_ENTITY:
			lightOffset = 49;
			buttonOffset = 45;
			break;
		case TARG_MISC:
			lightOffset = 76;
			buttonOffset = 72;
			break;
		case FLOOD:
			lightOffset = 103;
			buttonOffset = 99;
			break;
		}
		drawTexturedModalRect(guiLeft + 8, guiTop + lightOffset, 213, 90, 7, 7);
		drawTexturedModalRect(guiLeft + 19, guiTop + buttonOffset, 219, 127, 37, 15);
		if (luna.isCharging())
			drawTexturedModalRect(guiLeft + 62, guiTop + 92, 219, 97, 37, 15);
		
		for (int i = 0; i < 3; i++)
			displays[i].drawNumber();
		
		int pow = (int) getScaledBar(luna.getPower(), 95, luna.getMaxPower());
		drawTexturedModalRect(guiLeft + 165, guiTop + 114 - pow, 238, 96 - pow, 18, pow);
		int buff = (int) getScaledBar(luna.getBuffer(), 95, luna.maxBuffer);
		drawTexturedModalRect(guiLeft + 147, guiTop + 114 - buff, 220, 96 - buff, 18, buff);
	}
	@Override
	protected void keyTyped(char c, int i)
	{
		if (tField.textboxKeyTyped(c, i))
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(RefStrings.MODID, "misc.keyPress"), 1.0F));
		else
			super.keyTyped(c, i);
	}
}
