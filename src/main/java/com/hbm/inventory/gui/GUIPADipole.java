package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPADipole;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.albion.TileEntityPADipole;
import com.hbm.util.Vec3NT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPADipole extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/particleaccelerator/gui_dipole.png");
	private TileEntityPADipole dipole;
	
	protected GuiTextField threshold;

	public GUIPADipole(InventoryPlayer player, TileEntityPADipole dipole) {
		super(new ContainerPADipole(player, dipole));
		this.dipole = dipole;
		
		this.xSize = 176;
		this.ySize = 204;
	}

	@Override
	public void initGui() {
		super.initGui();

		Keyboard.enableRepeatEvents(true);

		this.threshold = new GuiTextField(this.fontRendererObj, guiLeft + 47, guiTop + 77, 66, 8);
		this.threshold.setTextColor(0x00ff00);
		this.threshold.setDisabledTextColour(0x00ff00);
		this.threshold.setEnableBackgroundDrawing(false);
		this.threshold.setMaxStringLength(9);
		this.threshold.setText("" + dipole.threshold);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		dipole.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 36, 16, 52);
		dipole.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 36, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 52, dipole.power, dipole.getMaxPower());
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 29, 12, 12, mouseX, mouseY, EnumChatFormatting.BLUE + "Player orientation", EnumChatFormatting.RED + "Output orientation:", dipole.ditToForgeDir(dipole.dirLower).name());
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 43, 12, 12, mouseX, mouseY, EnumChatFormatting.BLUE + "Player orientation", EnumChatFormatting.RED + "Output orientation:", dipole.ditToForgeDir(dipole.dirUpper).name());
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 57, 12, 12, mouseX, mouseY, EnumChatFormatting.BLUE + "Player orientation", EnumChatFormatting.RED + "Output orientation:", dipole.ditToForgeDir(dipole.dirRedstone).name());
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		this.threshold.mouseClicked(x, y, i);

		if(guiLeft + 62 <= x && guiLeft + 62 + 12 > x && guiTop + 29 < y && guiTop + 29 + 12 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("lower", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, dipole.xCoord, dipole.yCoord, dipole.zCoord));
		}

		if(guiLeft + 62 <= x && guiLeft + 62 + 12 > x && guiTop + 43 < y && guiTop + 43 + 12 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("upper", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, dipole.xCoord, dipole.yCoord, dipole.zCoord));
		}

		if(guiLeft + 62 <= x && guiLeft + 62 + 12 > x && guiTop + 57 < y && guiTop + 57 + 12 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("redstone", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, dipole.xCoord, dipole.yCoord, dipole.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.dipole.hasCustomInventoryName() ? this.dipole.getInventoryName() : I18n.format(this.dipole.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 9, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		this.fontRendererObj.drawString(EnumChatFormatting.AQUA + "/123K", 136, 22, 4210752);
		int heat = (int) Math.ceil(dipole.temperature);
		String label = (heat > 123 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA) + "" + heat + "K";
		this.fontRendererObj.drawString(label, 166 - this.fontRendererObj.getStringWidth(label), 12, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j = (int) (dipole.power * 52 / dipole.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 184, 52 - j, 16, j);

		int heat = (int) Math.ceil(dipole.temperature);
		if(heat <= 123) drawTexturedModalRect(guiLeft + 93, guiTop + 54, 176, 8, 8, 8);
		if(dipole.slots[1] != null && dipole.slots[1].getItem() == ModItems.pa_coil) drawTexturedModalRect(guiLeft + 103, guiTop + 54, 176, 8, 8, 8);
		if(dipole.power >= dipole.usage) drawTexturedModalRect(guiLeft + 83, guiTop + 54, 176, 8, 8, 8);
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLineWidth(3F);
		
		Vec3NT vec = new Vec3NT(0, 0, 0);
		vec.rotateAroundZDeg(MainRegistry.proxy.me().rotationYaw);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(1);
		addLine(tessellator, 68, 35, 0x8080ff, vec, 180);
		addLine(tessellator, 68, 35, 0xff0000, vec, MainRegistry.proxy.me().rotationYaw - dipole.dirLower * 90);
		addLine(tessellator, 68, 49, 0x8080ff, vec, 180);
		addLine(tessellator, 68, 49, 0xff0000, vec, MainRegistry.proxy.me().rotationYaw - dipole.dirUpper * 90);
		addLine(tessellator, 68, 63, 0x8080ff, vec, 180);
		addLine(tessellator, 68, 63, 0xff0000, vec, MainRegistry.proxy.me().rotationYaw - dipole.dirRedstone * 90);
		tessellator.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		
		this.threshold.drawTextBox();

		dipole.tanks[0].renderTank(guiLeft + 134, guiTop + 88, this.zLevel, 16, 52);
		dipole.tanks[1].renderTank(guiLeft + 152, guiTop + 88, this.zLevel, 16, 52);
	}
	
	public void addLine(Tessellator tess, int x, int y, int color, Vec3NT vec, float yaw) {
		vec.setComponents(0, 6, 0);
		vec.rotateAroundZDeg(yaw);
		tess.setColorOpaque_I(color);
		tess.addVertex(guiLeft + x, guiTop + y, this.zLevel);
		tess.addVertex(guiLeft + x + vec.xCoord, guiTop + y + vec.yCoord, this.zLevel);
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(this.threshold.textboxKeyTyped(c, i)) {
			String text = this.threshold.getText();
			if(text.startsWith("0")) this.threshold.setText(text.substring(1));
			if(this.threshold.getText().isEmpty()) this.threshold.setText("0");
			if(NumberUtils.isDigits(this.threshold.getText())) {
				int num = NumberUtils.toInt(this.threshold.getText());
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("threshold", num);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, dipole.xCoord, dipole.yCoord, dipole.zCoord));
			}
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
}
