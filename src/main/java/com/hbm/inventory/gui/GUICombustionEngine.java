package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCombustionEngine;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPistons.EnumPistonType;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityMachineCombustionEngine;
import com.hbm.util.EnumUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUICombustionEngine extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_combustion.png");
	private TileEntityMachineCombustionEngine engine;
	private int setting = 0;
	private boolean isMouseLocked = false;

	public GUICombustionEngine(InventoryPlayer invPlayer, TileEntityMachineCombustionEngine tedf) {
		super(new ContainerCombustionEngine(invPlayer, tedf));
		engine = tedf;
		this.setting = engine.setting;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		if(!isMouseLocked) {
			this.drawElectricityInfo(this, x, y, guiLeft + 143, guiTop + 17, 16, 52, engine.getPower(), engine.maxPower);
			engine.tank.renderTankInfo(this, x, y, guiLeft + 35, guiTop + 17, 16, 52);
		}
		
		if(isMouseLocked || (guiLeft + 80 <= x && guiLeft + 80 + 34 > x && guiTop + 38 < y && guiTop + 38 + 8 >= y)) {
			drawCreativeTabHoveringText(((setting * 2) / 10D) + "mB/t", MathHelper.clamp_int(x, guiLeft + 80, guiLeft + 114), MathHelper.clamp_int(y, guiTop + 38, guiTop + 46));
		}
		
		if(engine.slots[2] != null && engine.slots[2].getItem() == ModItems.piston_set) {
			double power = 0;
			if(engine.tank.getTankType().hasTrait(FT_Combustible.class)) {
				FT_Combustible trait = engine.tank.getTankType().getTrait(FT_Combustible.class);
				int i = engine.slots[2].getItemDamage();
				EnumPistonType piston = EnumUtil.grabEnumSafely(EnumPistonType.class, i);
				power = setting * 0.2 * trait.getCombustionEnergy() / 1_000D * piston.eff[trait.getGrade().ordinal()];
			}
			String c = EnumChatFormatting.YELLOW + "";
			drawCustomInfoStat(x, y, guiLeft + 79, guiTop + 50, 35, 14, x, y, c + String.format(Locale.US, "%,d", (int)(power)) + " HE/t", c + String.format(Locale.US, "%,d", (int)(power * 20)) + " HE/s");
		}
		
		drawCustomInfoStat(x, y, guiLeft + 79, guiTop + 13, 35, 15, x, y, "Ignition");

		if(isMouseLocked) {
			
			int setting = (x - guiLeft - 81) * 30 / 32;
			setting = MathHelper.clamp_int(setting, 0, 30);
			
			if(this.setting != setting) {
				this.setting = setting;
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("setting", setting);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, engine.xCoord, engine.yCoord, engine.zCoord));
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 89 <= x && guiLeft + 89 + 16 > x && guiTop + 13 < y && guiTop + 13 + 14 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("turnOn", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, engine.xCoord, engine.yCoord, engine.zCoord));
		}

		if(guiLeft + 79 <= x && guiLeft + 79 + 36 > x && guiTop + 38 < y && guiTop + 38 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			isMouseLocked = true;
		}
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int i) {
		super.mouseMovedOrUp(x, y, i);
		
		if(isMouseLocked) {
			
			if(i == 0 || i == 1) {
				isMouseLocked = false;
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(engine.slots[2] != null && engine.slots[2].getItem() == ModItems.piston_set) {
			int i = engine.slots[2].getItemDamage();
			drawTexturedModalRect(guiLeft + 80, guiTop + 51, 176, 52 + i * 12, 25, 12);
		}
		
		drawTexturedModalRect(guiLeft + 79 + (setting * 32 / 30), guiTop + 38, 192, 15, 4, 8);
		
		if(engine.isOn) {
			drawTexturedModalRect(guiLeft + 79, guiTop + 13, 192, 0, 35, 15);
		}
		
		int i = (int) (engine.power * 53 / engine.maxPower);
		drawTexturedModalRect(guiLeft + 143, guiTop + 69 - i, 176, 52 - i, 16, i);

		engine.tank.renderTank(guiLeft + 35, guiTop + 69, this.zLevel, 16, 52);
	}
}
