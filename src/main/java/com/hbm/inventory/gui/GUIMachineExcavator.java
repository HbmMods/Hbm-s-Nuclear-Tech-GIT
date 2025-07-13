package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineExcavator extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_mining_drill.png");
	private TileEntityMachineExcavator drill;

	public GUIMachineExcavator(InventoryPlayer inventory, TileEntityMachineExcavator tile) {
		super(new ContainerMachineExcavator(inventory, tile));
		
		this.drill = tile;
		
		this.xSize = 242;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		this.drawCustomInfoStat(x, y, guiLeft + 6, guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.drill"));
		this.drawCustomInfoStat(x, y, guiLeft + 30, guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.crusher"));
		this.drawCustomInfoStat(x, y, guiLeft + 54, guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.walling"));
		this.drawCustomInfoStat(x, y, guiLeft + 78, guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.veinminer"));
		this.drawCustomInfoStat(x, y, guiLeft + 102, guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.silktouch"));
		
		this.drawElectricityInfo(this, x, y, guiLeft + 220, guiTop + 18, 16, 52, drill.getPower(), drill.maxPower);
		this.drill.tank.renderTankInfo(this, x, y, guiLeft + 202, guiTop + 18, 16, 52);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		String toggle = null;
		
		if(guiLeft + 6 <= x && guiLeft + 6 + 20 > x && guiTop + 42 < y && guiTop + 42 + 40 >= y) toggle = "drill";
		if(guiLeft + 30 <= x && guiLeft + 30 + 20 > x && guiTop + 42 < y && guiTop + 42 + 40 >= y) toggle = "crusher";
		if(guiLeft + 54 <= x && guiLeft + 54 + 20 > x && guiTop + 42 < y && guiTop + 42 + 40 >= y) toggle = "walling";
		if(guiLeft + 78 <= x && guiLeft + 78 + 20 > x && guiTop + 42 < y && guiTop + 42 + 40 >= y) toggle = "veinminer";
		if(guiLeft + 102 <= x && guiLeft + 102 + 20 > x && guiTop + 42 < y && guiTop + 42 + 40 >= y) toggle = "silktouch";

		if(toggle != null) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.leverLarge"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean(toggle, true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, drill.xCoord, drill.yCoord, drill.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8 + 33, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 242, 96);
		drawTexturedModalRect(guiLeft + 33, guiTop + 104, 33, 104, 176, 100);
		
		int i = (int) (drill.getPower() * 52 / drill.getMaxPower());
		drawTexturedModalRect(guiLeft + 220, guiTop + 70 - i, 229, 156 - i, 16, i);
		
		if(drill.getPower() > drill.getPowerConsumption()) {
			drawTexturedModalRect(guiLeft + 224, guiTop + 4, 239, 156, 9, 12);
		}
		
		if(drill.getInstalledDrill() == null && System.currentTimeMillis() % 1000 < 500) {
			drawTexturedModalRect(guiLeft + 171, guiTop + 74, 209, 154, 18, 18);
		}
		
		if(drill.enableDrill) {
			drawTexturedModalRect(guiLeft + 6, guiTop + 42, 209, 114, 20, 40);
			if(drill.getInstalledDrill() != null && drill.getPower() >= drill.getPowerConsumption()) drawTexturedModalRect(guiLeft + 11, guiTop + 5, 209, 104, 10, 10);
			else if(System.currentTimeMillis() % 1000 < 500) drawTexturedModalRect(guiLeft + 11, guiTop + 5, 219, 104, 10, 10);
		}
		
		if(drill.enableCrusher) {
			drawTexturedModalRect(guiLeft + 30, guiTop + 42, 209, 114, 20, 40);
			drawTexturedModalRect(guiLeft + 35, guiTop + 5, 209, 104, 10, 10);
		}
		
		if(drill.enableWalling) {
			drawTexturedModalRect(guiLeft + 54, guiTop + 42, 209, 114, 20, 40);
			drawTexturedModalRect(guiLeft + 59, guiTop + 5, 209, 104, 10, 10);
		}
		
		if(drill.enableVeinMiner) {
			drawTexturedModalRect(guiLeft + 78, guiTop + 42, 209, 114, 20, 40);
			if(drill.canVeinMine()) drawTexturedModalRect(guiLeft + 83, guiTop + 5, 209, 104, 10, 10);
			else if(System.currentTimeMillis() % 1000 < 500) drawTexturedModalRect(guiLeft + 83, guiTop + 5, 219, 104, 10, 10);
			
		}
		
		if(drill.enableSilkTouch) {
			drawTexturedModalRect(guiLeft + 102, guiTop + 42, 209, 114, 20, 40);
			if(drill.canSilkTouch()) drawTexturedModalRect(guiLeft + 107, guiTop + 5, 209, 104, 10, 10);
			else if(System.currentTimeMillis() % 1000 < 500) drawTexturedModalRect(guiLeft + 107, guiTop + 5, 219, 104, 10, 10);
		}
		
		drill.tank.renderTank(guiLeft + 202, guiTop + 70, this.zLevel, 16, 52);
	}
}
