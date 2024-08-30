package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKControlAuto;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlAuto;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKControlAuto extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_control_auto.png");
	private TileEntityRBMKControlAuto rod;
	
	private GuiTextField[] fields;

	public GUIRBMKControlAuto(InventoryPlayer invPlayer, TileEntityRBMKControlAuto tedf) {
		super(new ContainerRBMKControlAuto(invPlayer, tedf));
		rod = tedf;
		
		fields = new GuiTextField[4];
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		
		for(int i = 0; i < 4; i++) {
			this.fields[i] = new GuiTextField(this.fontRendererObj, guiLeft + 30, guiTop + 27 + 11 * i, 26, 6);
			this.fields[i].setTextColor(-1);
			this.fields[i].setDisabledTextColour(-1);
			this.fields[i].setEnableBackgroundDrawing(false);
			
			if(i < 2)
				this.fields[i].setMaxStringLength(3);
			else
				this.fields[i].setMaxStringLength(4);
		}

		this.fields[0].setText(String.valueOf((int)rod.levelUpper));
		this.fields[1].setText(String.valueOf((int)rod.levelLower));
		this.fields[2].setText(String.valueOf((int)rod.heatUpper));
		this.fields[3].setText(String.valueOf((int)rod.heatLower));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 124, guiTop + 29, 16, 56, mouseX, mouseY, new String[]{ (int)(rod.level * 100) + "%" } );
		
		String func = "Function: ";
		
		switch(rod.function) {
		case LINEAR: func += "Linear"; break;
		case QUAD_UP: func += "Quadratic"; break;
		case QUAD_DOWN: func += "Inverse Quadratic"; break;
		}

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 58, guiTop + 26, 28, 19, mouseX, mouseY, new String[]{ func } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 61, guiTop + 48, 22, 10, mouseX, mouseY, new String[]{ "Select linear interpolation" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 61, guiTop + 59, 22, 10, mouseX, mouseY, new String[]{ "Select quadratic interpolation" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 61, guiTop + 70, 22, 10, mouseX, mouseY, new String[]{ "Select inverse quadratic interpolation" } );

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 26, 30, 10, mouseX, mouseY, new String[]{ "Level at max heat", "Should be smaller than level at min heat" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 37, 30, 10, mouseX, mouseY, new String[]{ "Level at min heat", "Should be larger than level at min heat" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 48, 30, 10, mouseX, mouseY, new String[]{ "Max heat", "Must be larger than min heat" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 59, 30, 10, mouseX, mouseY, new String[]{ "Min heat", "Must be smaller than max heat" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 70, 30, 10, mouseX, mouseY, new String[]{ "Save parameters" } );
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		for(int j = 0; j < 4; j++) {
			this.fields[j].mouseClicked(x, y, i);
		}
		
		if(guiLeft + 28 <= x && guiLeft + 28 + 30 > x && guiTop + 70 < y && guiTop + 70 +10 >= y) {
			
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			
			double[] vals = new double[] {0D ,0D, 0D, 0D};

			for(int k = 0; k < 4; k++) {
				
				double clamp = k < 2 ? 100 : 9999;
				
				if(NumberUtils.isNumber(fields[k].getText())) {
					int j = (int)MathHelper.clamp_double(Double.parseDouble(fields[k].getText()), 0, clamp);
					fields[k].setText(j + "");
					vals[k] = j;
				} else {
					fields[k].setText("0");
				}
			}

			data.setDouble("levelUpper", vals[0]);
			data.setDouble("levelLower", vals[1]);
			data.setDouble("heatUpper", vals[2]);
			data.setDouble("heatLower", vals[3]);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rod.xCoord, rod.yCoord, rod.zCoord));
		}
		
		for(int k = 0; k < 3; k++) {

			//manual rod control
			if(guiLeft + 61 <= x && guiLeft + 61 + 22 > x && guiTop + 48 + k * 11 < y && guiTop + 48 + 10 + k * 11 >= y) {
	
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("function", k);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rod.xCoord, rod.yCoord, rod.zCoord));
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.rod.hasCustomInventoryName() ? this.rod.getInventoryName() : I18n.format(this.rod.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int height = (int)(56 * (1D - rod.level));
		
		if(height > 0)
			drawTexturedModalRect(guiLeft + 124, guiTop + 29, 176, 56 - height, 8, height);
		
		int f = rod.function.ordinal();
		drawTexturedModalRect(guiLeft + 59, guiTop + 27, 184, f * 19, 26, 19);
		
		for(int i = 0; i < 4; i++) {
			this.fields[i].drawTextBox();
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		for(int j = 0; j < 4; j++) {
			if(this.fields[j].textboxKeyTyped(c, i))
				return;
		}
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
