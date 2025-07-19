package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKConsole extends GuiScreen {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_console.png");
	private TileEntityRBMKConsole console;
	protected int guiLeft;
	protected int guiTop;
	protected int xSize;
	protected int ySize;
	
	private boolean[] selection = new boolean[15 * 15];
	private boolean az5Lid = true;
	private long lastPress = 0;
	
	private GuiTextField field;

	public GUIRBMKConsole(InventoryPlayer invPlayer, TileEntityRBMKConsole tedf) {
		super();
		this.console = tedf;
		
		this.xSize = 244;
		this.ySize = 172;
	}
	
	public void initGui() {
		super.initGui();
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(this.fontRendererObj, guiLeft + 9, guiTop + 84, 35, 9);
		this.field.setTextColor(0x00ff00);
		this.field.setDisabledTextColour(0x008000);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		
		int bX = 86;
		int bY = 11;
		int size = 10;

		if(guiLeft + 86 <= mouseX && guiLeft + 86 + 150 > mouseX && guiTop + 11 < mouseY && guiTop + 11 + 10150 >= mouseY) {
			int index = ((mouseX - bX - guiLeft) / size + (mouseY - bY - guiTop) / size * 15);
			
			if(index > 0 && index < console.columns.length) {
				RBMKColumn col = console.columns[index];
				
				if(col != null) {
					
					List<String> list = new ArrayList();
					list.add(col.type.toString());
					list.addAll(col.getFancyStats());
					this.func_146283_a(list, mouseX, mouseY);
				}
			}
		}

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 61, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select all control rods" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 72, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Deselect all" } );
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 2; j++) {
				int id = i * 2 + j + 1;
				this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 6 + 40 * j, guiTop + 8 + 21 * i, 18, 18, mouseX, mouseY, new String[]{ EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.console." + console.screens[id - 1].type.name().toLowerCase(Locale.US), id) } );
				this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 24 + 40 * j, guiTop + 8 + 21 * i, 18, 18, mouseX, mouseY, new String[]{ I18nUtil.resolveKey("rbmk.console.assign", id) } );
			}
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 6, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ EnumChatFormatting.RED + "Left click: Select red group", EnumChatFormatting.RED + "Right click: Assign red group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 17, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ EnumChatFormatting.YELLOW + "Left click: Select yellow group", EnumChatFormatting.YELLOW + "Right click: Assign yellow group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ EnumChatFormatting.GREEN + "Left click: Select green group", EnumChatFormatting.GREEN + "Right click: Assign green group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 39, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ EnumChatFormatting.BLUE + "Left click: Select blue group", EnumChatFormatting.BLUE + "Right click: Assign blue group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 50, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ EnumChatFormatting.LIGHT_PURPLE + "Left click: Select purple group", EnumChatFormatting.LIGHT_PURPLE + "Right click: Assign purple group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 70, guiTop + 82, 12, 12, mouseX, mouseY, new String[]{ "Cycle steam channel compressor setting" } );
	}
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, String[] text) {
		
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(Arrays.asList(text), tPosX, tPosY);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int key) {
		super.mouseClicked(mouseX, mouseY, key);
		this.field.mouseClicked(mouseX, mouseY, key);
		
		int LEFT_CLICK = 0;
		int RIGTH_CLICK = 1;
		
		int bX = 86;
		int bY = 11;
		int size = 10;

		//toggle column selection
		if(guiLeft + 86 <= mouseX && guiLeft + 86 + 150 > mouseX && guiTop + 11 < mouseY && guiTop + 11 + 150 >= mouseY) {
			
			int index = ((mouseX - bX - guiLeft) / size + (mouseY - bY - guiTop) / size * 15);
			
			if(index >= 0 && index < selection.length && console.columns[index] != null) {
				this.selection[index] = !this.selection[index];
				
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.75F + (this.selection[index] ? 0.25F : 0.0F)));
				return;
			}
		}
		
		//clear selection
		if(guiLeft + 72 <= mouseX && guiLeft + 72 + 10 > mouseX && guiTop + 70 < mouseY && guiTop + 70 + 10 >= mouseY) {
			this.selection = new boolean[15 * 15];
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F));
			return;
		}
		
		//select all control rods
		if(guiLeft + 61 <= mouseX && guiLeft + 61 + 10 > mouseX && guiTop + 70 < mouseY && guiTop + 70 + 10 >= mouseY) {
			this.selection = new boolean[15 * 15];

			for(int j = 0; j < console.columns.length; j++) {
				
				if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL) {
					this.selection[j] = true;
				}
			}
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.5F));
			return;
		}
		
		//compressor
		if(guiLeft + 70 <= mouseX && guiLeft + 70 + 12 > mouseX && guiTop + 82 < mouseY && guiTop + 82 + 12 >= mouseY) {
			NBTTagCompound control = new NBTTagCompound();
			control.setBoolean("compressor", true);
			List<Integer> ints = new ArrayList();
			for(int j = 0; j < console.columns.length; j++) {
				if(console.columns[j] != null && console.columns[j].type == ColumnType.BOILER && this.selection[j]) {
					ints.add(j);
				}
			}
			int[] cols = new int[ints.size()];
			for(int i = 0; i < cols.length; i++) cols[i] = ints.get(i);
			control.setIntArray("cols", cols);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.xCoord, console.yCoord, console.zCoord));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
		}
		
		//select color groups
		for(int k = 0; k < 5; k++) {
			
			if(guiLeft + 6 + k * 11 <= mouseX && guiLeft + 6 + k * 11 + 10 > mouseX && guiTop + 70 < mouseY && guiTop + 70 + 10 >= mouseY) {

				if(key == LEFT_CLICK) {
					this.selection = new boolean[15 * 15];
					
					for(int j = 0; j < console.columns.length; j++) {
						
						if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL && console.columns[j].data.getShort("color") == k) {
							this.selection[j] = true;
						}
					}
				}
				
				if(key == RIGTH_CLICK) {
					NBTTagCompound control = new NBTTagCompound();
					control.setByte("assignColor", (byte) k);
					List<Integer> ints = new ArrayList();
					for(int j = 0; j < console.columns.length; j++) {
						if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL && this.selection[j]) {
							ints.add(j);
						}
					}
					int[] cols = new int[ints.size()];
					for(int i = 0; i < cols.length; i++) cols[i] = ints.get(i);
					control.setIntArray("cols", cols);
					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.xCoord, console.yCoord, console.zCoord));
				}
				
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.8F + k * 0.1F));
				return;
			}
		}

		//AZ-5
		if(guiLeft + 30 <= mouseX && guiLeft + 30 + 28 > mouseX && guiTop + 138 < mouseY && guiTop + 138 + 28 >= mouseY) {
			
			if(az5Lid) {
				az5Lid = false;
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
			} else if(lastPress + 3000 < System.currentTimeMillis()) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.shutdown"), 1));
				lastPress = System.currentTimeMillis();
				
				NBTTagCompound control = new NBTTagCompound();
				control.setDouble("level", 0);

				for(int j = 0; j < console.columns.length; j++) {
					if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL)
						control.setInteger("sel_" + j, j);
				}
				
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.xCoord, console.yCoord, console.zCoord));
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
			}
			return;
		}

		//save control rod setting
		if(guiLeft + 48 <= mouseX && guiLeft + 48 + 12 > mouseX && guiTop + 82 < mouseY && guiTop + 82 + 12 >= mouseY) {
			
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

			for(int j = 0; j < selection.length; j++) {
				if(selection[j])
					control.setInteger("sel_" + j, j);
			}
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.xCoord, console.yCoord, console.zCoord));
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
		}
		
		//submit selection for status screen
		
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 2; k++) {
				
				int id = j * 2 + k;
				
				if(guiLeft + 6 + 40 * k <= mouseX && guiLeft + 6 + 40 * k + 18 > mouseX && guiTop + 8 + 21 * j < mouseY && guiTop + 8 + 21 * j + 18 >= mouseY) {
					NBTTagCompound control = new NBTTagCompound();
					control.setByte("toggle", (byte) id);
					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.xCoord, console.yCoord, console.zCoord));
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F));
					return;
				}
				
				if(guiLeft + 24 + 40 * k <= mouseX && guiLeft + 24 + 40 * k + 18 > mouseX && guiTop + 8 + 21 * j < mouseY && guiTop + 8 + 21 * j + 18 >= mouseY) {

					NBTTagCompound control = new NBTTagCompound();
					control.setByte("id", (byte) id);

					for(int s = 0; s < selection.length; s++) {
						if(selection[s]) {
							control.setBoolean("s" + s, true);
						}
					}

					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.xCoord, console.yCoord, console.zCoord));
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.75F));
					return;
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch") //shut up
	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(az5Lid) {
			drawTexturedModalRect(guiLeft + 30, guiTop + 138, 228, 172, 28, 28);
		}
		
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 2; k++) {
				int id = j * 2 + k;
				drawTexturedModalRect(guiLeft + 6 + 40 * k, guiTop + 8 + 21 * j, this.console.screens[id].type.offset, 238, 18, 18);
			}
		}
		
		int bX = 86;
		int bY = 11;
		int size = 10;
		
		for(int i = 0; i < console.columns.length; i++) {
			
			RBMKColumn col = console.columns[i];
			
			if(col == null)
				continue;

			int x = bX + size * (i % 15);
			int y = bY + size * (i / 15);
			
			int tX = col.type.offset;
			int tY = 172;
			
			drawTexturedModalRect(guiLeft + x, guiTop + y, tX, tY, size, size);
			
			int h = Math.min((int)Math.ceil((col.data.getDouble("heat") - 20) * 10 / col.data.getDouble("maxHeat")), 10);
			drawTexturedModalRect(guiLeft + x, guiTop + y + size - h, 0, 192 - h, 10, h);
			
			switch(col.type) {
			case ABSORBER: break;
			case BLANK: break;
			case MODERATOR: break;
			case REFLECTOR: break;
			case OUTGASSER: break;
			case BREEDER: break;
			
			case CONTROL:
				int color = col.data.getShort("color");
				if(color > -1)
					drawTexturedModalRect(guiLeft + x, guiTop + y, color * size, 202, 10, 10);
				
			case CONTROL_AUTO:
				int fr = 8 - (int)Math.ceil((col.data.getDouble("level") * 8));
				drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 1, 24, 183, 2, fr);
				break;

			case FUEL:
			case FUEL_SIM:
				if(col.data.hasKey("c_heat")) {
					int fh = (int)Math.ceil((col.data.getDouble("c_heat") - 20) * 8 / col.data.getDouble("c_maxHeat"));
					if(fh > 8) fh = 8;
					drawTexturedModalRect(guiLeft + x + 1, guiTop + y + size - fh - 1, 11, 191 - fh, 2, fh);
					
					int fe = (int)Math.ceil((col.data.getDouble("enrichment")) * 8);
					if(fe > 8) fe = 8;
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + size - fe - 1, 14, 191 - fe, 2, fe);
					
					int fx = (int)Math.ceil((col.data.getDouble("xenon")) * 8 / 100);
					if(fx > 8) fx = 8;
					drawTexturedModalRect(guiLeft + x + 7, guiTop + y + size - fx - 1, 17, 191 - fx, 2, fx);
				}
				break;
				
			case BOILER:
				int fw = (int)Math.ceil((col.data.getInteger("water")) * 8 / col.data.getDouble("maxWater"));
				drawTexturedModalRect(guiLeft + x + 1, guiTop + y + size - fw - 1, 41, 191 - fw, 3, fw);
				int fs = (int)Math.ceil((col.data.getInteger("steam")) * 8 / col.data.getDouble("maxSteam"));
				drawTexturedModalRect(guiLeft + x + 6, guiTop + y + size - fs - 1, 46, 191 - fs, 3, fs);

				if(col.data.getShort("type") == Fluids.STEAM.ordinal())
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 1, 44, 183, 2, 2);
				if(col.data.getShort("type") == Fluids.HOTSTEAM.ordinal())
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 3, 44, 185, 2, 2);
				if(col.data.getShort("type") == Fluids.SUPERHOTSTEAM.ordinal())
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 5, 44, 187, 2, 2);
				if(col.data.getShort("type") == Fluids.ULTRAHOTSTEAM.ordinal())
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 7, 44, 189, 2, 2);
				
				break;
				
			case HEATEX:
				int cc = (int)Math.ceil((col.data.getInteger("water")) * 8 / col.data.getDouble("maxWater"));
				drawTexturedModalRect(guiLeft + x + 1, guiTop + y + size - cc - 1, 131, 191 - cc, 3, cc);
				int hc = (int)Math.ceil((col.data.getInteger("steam")) * 8 / col.data.getDouble("maxSteam"));
				drawTexturedModalRect(guiLeft + x + 6, guiTop + y + size - hc - 1, 136, 191 - hc, 3, hc);
				break;
			}
			
			if(this.selection[i])
				drawTexturedModalRect(guiLeft + x, guiTop + y, 0, 192, 10, 10);
		}
		
		int highest = Integer.MIN_VALUE;
		int lowest = Integer.MAX_VALUE;
		
		for(int i : console.fluxBuffer) {
			if(i > highest) highest = i;
			if(i < lowest) lowest = i;
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(2F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_LINES);
		tess.setColorOpaque_I(0x00ff00);
		int range = highest - lowest;
		for(int i = 0; i < console.fluxBuffer.length - 1; i++) {
			for(int j = 0; j < 2; j++) {
				int k = i + j;
				int flux = console.fluxBuffer[k];
				double x = guiLeft + 7 + k * 74D / console.fluxBuffer.length;
				double y = guiTop + 127 - (flux - lowest) * 24D / Math.max(range, 1);
				tess.addVertex(x, y, this.zLevel + 10);
			}
		}
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPushMatrix();
		double scale = 0.5D;
		GL11.glScaled(scale, scale, 1);
		this.fontRendererObj.drawString(highest + "", (int) ((guiLeft + 8) / scale), (int) ((guiTop + 98) / scale), 0x00ff00);
		this.fontRendererObj.drawString(highest + "", (int) ((guiLeft + 80 - this.fontRendererObj.getStringWidth(highest + "") * scale) / scale), (int) ((guiTop + 98) / scale), 0x00ff00);
		this.fontRendererObj.drawString(lowest + "", (int) ((guiLeft + 8) / scale), (int) ((guiTop + 133 - this.fontRendererObj.FONT_HEIGHT * scale) / scale), 0x00ff00);
		this.fontRendererObj.drawString(lowest + "", (int) ((guiLeft + 80 - this.fontRendererObj.getStringWidth(lowest + "") * scale) / scale), (int) ((guiTop + 133 - this.fontRendererObj.FONT_HEIGHT * scale) / scale), 0x00ff00);
		GL11.glPopMatrix();
		
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
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
