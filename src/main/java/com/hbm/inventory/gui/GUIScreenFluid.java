package com.hbm.inventory.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIDMulti;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTItemControlPacket;
import com.hbm.util.Bookmarks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIScreenFluid extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_fluid.png");
	protected int xSize = 176;
	protected int ySize = 72;
	protected int guiLeft;
	protected int guiTop;
	private GuiTextField search;
	
	private final EntityPlayer player;
	private FluidType primary = Fluids.NONE;
	private FluidType secondary = Fluids.NONE;
	private FluidType[] searchArray = new FluidType[18];
	private Bookmarks<FluidType> bookmarks = new Bookmarks<>(searchArray.length);

	public GUIScreenFluid(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void updateScreen() {
		if(player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.fluid_identifier_multi)
			player.closeScreen();
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, guiLeft + 46, guiTop + 11, 86, 12);
		this.search.setTextColor(-1);
		this.search.setDisabledTextColour(-1);
		this.search.setEnableBackgroundDrawing(false);
		this.search.setFocused(true);

		ItemStack heldItem = player.getHeldItem();
		if(heldItem != null && heldItem.getItem() == ModItems.fluid_identifier_multi) {
			this.primary = ItemFluidIDMulti.getType(heldItem, true);
			this.secondary = ItemFluidIDMulti.getType(heldItem, false);
			
			if (heldItem.hasTagCompound() && heldItem.stackTagCompound.hasKey("bookmarks")) {
				int[] fluidIds = heldItem.stackTagCompound.getIntArray("bookmarks");
				for (int i = 0; i < Math.min(fluidIds.length, this.searchArray.length); i++) {
					FluidType fluid = Fluids.fromID(fluidIds[i]);
					this.bookmarks.add(fluid);
					this.searchArray[i] = fluid;
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int button) {
		this.search.mouseClicked(i, j, button);

		for(int k = 0; k < this.searchArray.length; k++) {
			
			if(this.searchArray[k] == null)
				return;
			
			if(guiLeft + 7 + (k % 9) * 18 <= i && guiLeft + 7 + (k % 9) * 18 + 18 > i && guiTop + 29 + (k / 9 * 18) < j && guiTop + 29 + (k / 9 * 18) + 18 >= j) {
				if(button == 0) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					this.primary = this.searchArray[k];
					NBTTagCompound data = new NBTTagCompound();
					data.setInteger("primary", this.primary.getID());
					PacketDispatcher.wrapper.sendToServer(new NBTItemControlPacket(data));
				} else if(button == 1) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					this.secondary = this.searchArray[k];
					NBTTagCompound data = new NBTTagCompound();
					data.setInteger("secondary", this.secondary.getID());
					PacketDispatcher.wrapper.sendToServer(new NBTItemControlPacket(data));
				} else if (button == 2) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					FluidType fluid = this.searchArray[k];
					if (this.bookmarks.add(fluid)) this.bookmarks.remove(fluid);
					if (this.search.getText().trim().isEmpty())
						this.searchArray = this.bookmarks.set().toArray(new FluidType[this.searchArray.length]);
				}
			}
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.search.drawTextBox();

		for(int k = 0; k < this.searchArray.length; k++) {
			
			if(this.searchArray[k] == null)
				return;
			
			if(guiLeft + 7 + (k % 9) * 18 <= i && guiLeft + 7 + (k % 9) * 18 + 18 > i && guiTop + 29 + (k / 9 * 18) < j && guiTop + 29 + (k / 9 * 18) + 18 >= j) {
				List<String> tooltip = new ArrayList();
				tooltip.add(this.searchArray[k].getLocalizedName());
				this.searchArray[k].addInfo(tooltip);
				func_146283_a(tooltip, i, j);
			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(this.search.isFocused())
			drawTexturedModalRect(guiLeft + 43, guiTop + 7, 166, 72, 90, 18);
		
		for(int k = 0; k < this.searchArray.length; k++) {
			FluidType type = this.searchArray[k];
			
			if(type == null)
				return;
			
			Color color = new Color(type.getColor());
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(guiLeft + 12 + (k % 9) * 18, guiTop + 31 + (k / 9 * 18), 12 + (k % 9) * 18, 74, 8, 14);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			if(type == this.primary && type == this.secondary) {
				drawTexturedModalRect(guiLeft + 7 + (k % 9) * 18, guiTop + 29 + (k / 9 * 18), 176, 36, 18, 18);
			} else if(type == this.primary) {
				drawTexturedModalRect(guiLeft + 7 + (k % 9) * 18, guiTop + 29 + (k / 9 * 18), 176, 0, 18, 18);
			} else if(type == this.secondary) {
				drawTexturedModalRect(guiLeft + 7 + (k % 9) * 18, guiTop + 29 + (k / 9 * 18), 176, 18, 18, 18);
			}
		}
	}

	@Override
	protected void keyTyped(char c, int key) {

		if(this.search.textboxKeyTyped(c, key)) {
			updateSearch();
		} else {
			super.keyTyped(c, key);
		}

		if(key == 1) {
			this.mc.thePlayer.closeScreen();
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private void updateSearch() {
		this.searchArray = new FluidType[this.searchArray.length];
		String searchText = this.search.getText();

		if (this.bookmarks.size() > 0 && searchText.trim().isEmpty()) {
			this.searchArray = this.bookmarks.set().toArray(this.searchArray);
			return;
		}
		
		int next = 0;
		String subs = searchText.toLowerCase(Locale.US);
		
		for(FluidType type : Fluids.getInNiceOrder()) {
			String name = type.getLocalizedName().toLowerCase(Locale.US);
			
			if(name.contains(subs) && !type.hasNoID()) {
				this.searchArray[next] = type;
				next++;
				
				if(next >= this.searchArray.length)
					return;
			}
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);

		NBTTagCompound data = new NBTTagCompound();
		data.setIntArray("bookmarks", this.bookmarks.set().stream().mapToInt(f -> f.getID()).toArray());
		PacketDispatcher.wrapper.sendToServer(new NBTItemControlPacket(data));
	}
}
