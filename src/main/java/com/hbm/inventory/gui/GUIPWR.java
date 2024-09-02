package com.hbm.inventory.gui;

import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPWR;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.TileEntityPWRController;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIPWR extends GuiInfoContainer {

	protected TileEntityPWRController controller;
	private final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_pwr.png");
	
	private GuiTextField field;
	
	public GUIPWR(InventoryPlayer inventory, TileEntityPWRController controller) {
		super(new ContainerPWR(inventory, controller));
		this.controller = controller;
		
		this.xSize = 176;
		this.ySize = 188;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(this.fontRendererObj, guiLeft + 57, guiTop + 63, 30, 8);
		this.field.setTextColor(0x00ff00);
		this.field.setDisabledTextColour(0x008000);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
		
		this.field.setText((100 - controller.rodTarget) + "");
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawCustomInfoStat(x, y, guiLeft + 115, guiTop + 31, 18, 18, x, y, new String[] { "Core: " + String.format(Locale.US, "%,d", controller.coreHeat) + " / " + String.format(Locale.US, "%,d", controller.coreHeatCapacity) + " TU" });
		this.drawCustomInfoStat(x, y, guiLeft + 151, guiTop + 31, 18, 18, x, y, new String[] { "Hull: " + String.format(Locale.US, "%,d", controller.hullHeat) + " / " + String.format(Locale.US, "%,d", controller.hullHeatCapacityBase) + " TU" });

		this.drawCustomInfoStat(x, y, guiLeft + 52, guiTop + 31, 36, 18, x, y, new String[] { ((int) (controller.progress * 100 / controller.processTime)) + "%" });
		this.drawCustomInfoStat(x, y, guiLeft + 52, guiTop + 53, 54, 4, x, y, "Control rod level: " + (100 - (Math.round(controller.rodLevel * 100)/100)) + "%");
		
		if(controller.typeLoaded != -1 && controller.amountLoaded > 0) {
			ItemStack display = new ItemStack(ModItems.pwr_fuel, 1, controller.typeLoaded);
			if(guiLeft + 88 <= x && guiLeft + 88 + 18 > x && guiTop + 4 < y && guiTop + 4 + 18 >= y) this.renderToolTip(display, x, y);
		}
		
		controller.tanks[0].renderTankInfo(this, x, y, guiLeft + 8, guiTop + 5, 16, 52);
		controller.tanks[1].renderTankInfo(this, x, y, guiLeft + 26, guiTop + 5, 16, 52);
	}

	@Override
	protected void drawItemStack(ItemStack stack, int x, int y, String label) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if(stack != null) font = stack.getItem().getFontRenderer(stack);
		if(font == null) font = fontRendererObj;
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
		GL11.glScaled(0.5, 0.5, 0.5);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, (x + font.getStringWidth(label) / 4) * 2, (y + 15) * 2, label);
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		double scale = 1.25;
		String flux = String.format(Locale.US, "%,.1f", controller.flux);
		GL11.glScaled(1 / scale, 1 / scale, 1);
		this.fontRendererObj.drawString(flux, (int) (165 * scale - this.fontRendererObj.getStringWidth(flux)), (int)(64 * scale), 0x00ff00);
		GL11.glScaled(scale, scale, 1);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(controller.hullHeat > controller.hullHeatCapacityBase * 0.8 || controller.coreHeat > controller.coreHeatCapacity * 0.8)
			drawTexturedModalRect(guiLeft + 147, guiTop, 176, 14, 26, 26);

		int p = (int) (controller.progress * 33 / controller.processTime);
		drawTexturedModalRect(guiLeft + 54, guiTop + 33, 176, 0, p, 14);

		int c = (int) (controller.rodLevel * 52 / 100);
		drawTexturedModalRect(guiLeft + 53, guiTop + 54, 176, 40, c, 2);

		//GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 115, guiTop + 31, this.zLevel, (double) controller.coreHeat / (double) controller.coreHeatCapacity);
		//GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 151, guiTop + 31, this.zLevel, (double) controller.hullHeat / (double) controller.hullHeatCapacity);

		GaugeUtil.drawSmoothGauge(guiLeft + 124, guiTop + 40, this.zLevel, (double) controller.coreHeat / (double) controller.coreHeatCapacity, 5, 2, 1, 0x7F0000);
		GaugeUtil.drawSmoothGauge(guiLeft + 160, guiTop + 40, this.zLevel, (double) controller.hullHeat / (double) controller.hullHeatCapacityBase, 5, 2, 1, 0x7F0000);
		
		if(controller.typeLoaded != -1 && controller.amountLoaded > 0) {
			ItemStack display = new ItemStack(ModItems.pwr_fuel, 1, controller.typeLoaded);
			this.drawItemStack(display, guiLeft + 89, guiTop + 5, EnumChatFormatting.YELLOW + "" + controller.amountLoaded + "/" + controller.rodCount);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		GL11.glDisable(GL11.GL_LIGHTING);

		controller.tanks[0].renderTank(guiLeft + 8, guiTop + 57, this.zLevel, 16, 52);
		controller.tanks[1].renderTank(guiLeft + 26, guiTop + 57, this.zLevel, 16, 52);
		
		this.field.drawTextBox();
	}
	
	/*private void drawGauge(int x, int y, double d) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		d = MathHelper.clamp_double(d, 0, 1);
		
		float angle = (float) Math.toRadians(-d * 270 - 45);
		Vec3 tip = Vec3.createVectorHelper(0, 5, 0);
		Vec3 left = Vec3.createVectorHelper(1, -2, 0);
		Vec3 right = Vec3.createVectorHelper(-1, -2, 0);

		tip.rotateAroundZ(angle);
		left.rotateAroundZ(angle);
		right.rotateAroundZ(angle);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_TRIANGLES);
		tess.setColorOpaque_F(0F, 0F, 0F);
		double mult = 1.5;
		tess.addVertex(x + tip.xCoord * mult, y + tip.yCoord * mult, this.zLevel);
		tess.addVertex(x + left.xCoord * mult, y + left.yCoord * mult, this.zLevel);
		tess.addVertex(x + right.xCoord * mult, y + right.yCoord * mult, this.zLevel);
		tess.setColorOpaque_F(0.75F, 0F, 0F);
		tess.addVertex(x + tip.xCoord, y + tip.yCoord, this.zLevel);
		tess.addVertex(x + left.xCoord, y + left.yCoord, this.zLevel);
		tess.addVertex(x + right.xCoord, y + right.yCoord, this.zLevel);
		tess.draw();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}*/

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i) {
		super.mouseClicked(mouseX, mouseY, i);
		this.field.mouseClicked(mouseX, mouseY, i);
		
		if(guiLeft + 88 <= mouseX && guiLeft + 88 + 18 > mouseX && guiTop + 58 < mouseY && guiTop + 58 + 18 >= mouseY) {
			
			if(NumberUtils.isNumber(field.getText())) {
				int level = (int)MathHelper.clamp_double(Double.parseDouble(field.getText()), 0, 100);
				field.setText(level + "");
				
				NBTTagCompound control = new NBTTagCompound();
				control.setInteger("control", 100 - level);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, controller.xCoord, controller.yCoord, controller.zCoord));
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
				
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(this.field.textboxKeyTyped(c, i)) return;
		super.keyTyped(c, i);
	}
}
