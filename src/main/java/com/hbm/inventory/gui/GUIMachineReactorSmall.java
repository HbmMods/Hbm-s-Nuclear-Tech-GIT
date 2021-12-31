package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineReactorSmall;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIMachineReactorSmall extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_experimental.png");
	private TileEntityMachineReactorSmall reactor;
	private final NumberDisplay[] displays = new NumberDisplay[2];
	
	private GuiTextField field;

	public GUIMachineReactorSmall(InventoryPlayer invPlayer, TileEntityMachineReactorSmall te) {
		super(new ContainerMachineReactorSmall(invPlayer, te));
		reactor = te;
		this.xSize = 176;
		this.ySize = 222;
		displays[0] = new NumberDisplay(12, 19).setDigitLength(4);
		displays[1] = new NumberDisplay(12, 55).setDigitLength(3);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(this.fontRendererObj, guiLeft + 11, guiTop + 86, 35, 9);
		this.field.setTextColor(0x00ff00);
		this.field.setDisabledTextColour(0x008000);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 120, 88, 4, new String[] { "Core Temperature:", "   " + Math.round((reactor.heat) * 0.00002 * 980 + 20) + "°C" });
		
		String[] text = new String[] { "Coolant will move heat from the core to",
				"the hull. Water will use that heat and",
				"generate steam.",
				"Water consumption rate:",
				" 100 mB/t",
				" 2000 mB/s",
				"Coolant consumption rate:",
				" 10 mB/t",
				" 200 mB/s",
				"Water next to the reactor's open",
				"sides will pour into the tank." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Raise/lower the control rods",
				"using the button next to the",
				"fluid gauges." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.reactor.hasCustomInventoryName() ? this.reactor.getInventoryName() : I18n.format(this.reactor.getInventoryName());
		final String[] labels = { "Flux", "Heat", "Control Rods" };
		
		this.fontRendererObj.drawString(name, 124 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(labels[0], 11, 9, 15066597);
		this.fontRendererObj.drawString(labels[1], 11, 45, 15066597);
		this.fontRendererObj.drawString(labels[2], 9, 74, 4210752);
	}

    protected void mouseClicked(int mouseX, int mouseY, int i) {
    	super.mouseClicked(mouseX, mouseY, i);
    	this.field.mouseClicked(mouseX, mouseY, i);
    	
    	if(guiLeft + 51 <= mouseX && guiLeft + 51 + 12 > mouseX && guiTop + 84 < mouseY && guiTop + 84 + 12 >= mouseY) {
			
    		double level;
    		
			if(NumberUtils.isNumber(field.getText())) {
				int j = (int)MathHelper.clamp_double(Double.parseDouble(field.getText()), 0, 100);
				field.setText(j + "");
				level = j * 0.01D;
			} else {
				return;
			}
			
			NBTTagCompound control = new NBTTagCompound();
			control.setDouble("level", level);
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, reactor.xCoord, reactor.yCoord, reactor.zCoord));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
		}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(reactor.heat > 0) {
			int i = reactor.getHeatScaled(88);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 120, 0, 230, i, 4);
		}
		
		if(reactor.level >= 100) {	
			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(guiLeft + 79 + 36 * x, guiTop + 17 + 36 * y, 176, 0, 18, 18);
				
		} else if(reactor.level > 0) {
			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(guiLeft + 79 + 36 * x, guiTop + 17 + 36 * y, 194, 0, 18, 18);
				
		}
		
		for(byte i = 0; i < 2; i++)
			displays[i].drawNumber(reactor.getDisplayData()[i]);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		this.field.drawTextBox();
	}
	
	@Override
	protected void keyTyped(char c, int i) {

		if(this.field.textboxKeyTyped(c, i))
			return;
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			return;
		}
		
		super.keyTyped(c, i);
	}
}
