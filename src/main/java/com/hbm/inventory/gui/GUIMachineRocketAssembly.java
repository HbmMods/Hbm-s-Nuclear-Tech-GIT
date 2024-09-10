package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.RocketStruct;
import com.hbm.inventory.container.ContainerMachineRocketAssembly;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ItemVOTVdrive.Target;
import com.hbm.lib.RefStrings;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRocketAssembly extends GuiInfoContainerLayered {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rocket_assembly.png");

	private TileEntityMachineRocketAssembly machine;

	private double currentOffset = 0;
	private double currentScale = 1;
	private long lastTime = 0;

	public GUIMachineRocketAssembly(InventoryPlayer invPlayer, TileEntityMachineRocketAssembly machine) {
		super(new ContainerMachineRocketAssembly(invPlayer, machine));
		this.machine = machine;
		
		this.xSize = 192;
		this.ySize = 224;
	}

	@Override
	public void initGui() {
		super.initGui();
		lastTime = System.nanoTime();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int stage = Math.max(machine.rocket.stages.size() - 1 - getLayer(), -1);

		drawTexturedModalRect(guiLeft + 47, guiTop + 39, xSize + 18 + (stage + 1) * 6, 0, 6, 8);

		stage = Math.max(stage, 0);

		double dt = (double)(System.nanoTime() - lastTime) / 1000000000;
		lastTime = System.nanoTime();
		
		GL11.glPushMatrix();
		{

			pushScissor(65, 5, 90, 106);

			GL11.glTranslatef(guiLeft + 116, guiTop + 103, 100);
			GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
			
			double size = 86;
			double height = machine.rocket.getHeight(stage);
			double targetScale = size / Math.max(height, 6);
			currentScale = currentScale + (targetScale - currentScale) * dt * 4;

			double targetOffset = machine.rocket.getOffset(stage);
			currentOffset = currentOffset + (targetOffset - currentOffset) * dt * 4;
			
			GL11.glScaled(-currentScale, -currentScale, -currentScale);
			GL11.glTranslated(0, -currentOffset, 0);

			MissilePronter.prontRocket(machine.rocket, Minecraft.getMinecraft().getTextureManager());

			popScissor();

		}
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		{

			GL11.glTranslatef(0, 0, 150);
			GL11.glScalef(0.5F, 0.5F, 0.5F);

			ItemStack fromStack = machine.slots[machine.slots.length - 2];
			ItemStack toStack = machine.slots[machine.slots.length - 1];

			Target from = ItemVOTVdrive.getTarget(fromStack, machine.getWorldObj());
			Target to = ItemVOTVdrive.getTarget(toStack, machine.getWorldObj());

			List<String> issues = machine.rocket.findIssues(stage, from.body, to.body, from.inOrbit, to.inOrbit);
			for(int i = 0; i < issues.size(); i++) {
				String issue = issues.get(i);
				fontRendererObj.drawStringWithShadow(issue, (guiLeft + 65) * 2, (guiTop + 5) * 2 + i * 8, 0xFFFFFF);
			}

			if(from.body != null) fontRendererObj.drawString(I18nUtil.resolveKey("body." + from.body.name), (guiLeft + 162) * 2, (guiTop + 75) * 2, 0x00FF00);
			if(to.body != null) fontRendererObj.drawString(I18nUtil.resolveKey("body." + to.body.name), (guiLeft + 162) * 2, (guiTop + 108) * 2, 0x00FF00);

		}
		GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		if(checkClick(mouseX, mouseY, 17, 34, 18, 8)) {
			drawTexturedModalRect(17, 34, xSize, 36, 18, 8);
		}
		
		if(checkClick(mouseX, mouseY, 17, 98, 18, 8)) {
			drawTexturedModalRect(17, 98, xSize, 44, 18, 8);
		}

		if(machine.rocket.validate()) {
			drawTexturedModalRect(41, 62, xSize + 18, 8, 18, 18);
		}

		if(machine.canDeconstruct()) {
			drawTexturedModalRect(39, 52, xSize + 36, 8, 20, 38);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		// Stage up
		if(checkClick(x, y, 17, 34, 18, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(getLayer() > 0) {
				setLayer(getLayer() - 1);
			}
		}

		// Stage down
		if(checkClick(x, y, 17, 98, 18, 8)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(getLayer() < Math.min(machine.rocket.stages.size(), RocketStruct.MAX_STAGES - 1)) {
				setLayer(getLayer() + 1);
			}
		}

		// Construct rocket
		if(checkClick(x, y, 41, 62, 18, 18)) {
			if(machine.rocket.validate()) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setBoolean("construct", true);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, machine.xCoord, machine.yCoord, machine.zCoord));
			} else if(machine.canDeconstruct()) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setBoolean("deconstruct", true);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, machine.xCoord, machine.yCoord, machine.zCoord));
			}
		}
	}
		
}
