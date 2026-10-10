package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.gui.element.GUIScrollingList;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;

public class GUITurretMobFilter extends GuiScreen {
	private final static ResourceLocation backgroundTexture = new ResourceLocation(RefStrings.MODID, "textures/gui/gui_turret_mob_filter.png");

	private static final int sizeX = 242;
	private static final int sizeY = 124;

	@SuppressWarnings("unchecked")
	private final List<String> mobList = ((Map<String, Class<? extends Entity>>) EntityList.stringToClassMapping).entrySet().stream().filter(entry -> {
		Class<? extends Entity> clazz = entry.getValue();
		return clazz != null && EntityLiving.class.isAssignableFrom(clazz);
	}).map(Map.Entry::getKey).sorted().collect(Collectors.toList());
	private final List<String> filteredMobList = new ArrayList<>();

	private int guiLeft;
	private int guiTop;

	private GUIScrollingList mobScrollList;
	private GUIScrollingList filterScrollingList;
	private GuiTextField mobSearchField;

	private final TileEntityTurretBaseNT turret;

	public GUITurretMobFilter(TileEntityTurretBaseNT turret) {
		this.turret = turret;
	}

	@Override protected void keyTyped(char c, int i) {
		if(this.mobSearchField.textboxKeyTyped(c, i)) {
			updateMobList();
			return;
		}

		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override public void initGui() {
		super.initGui();

		filteredMobList.addAll(mobList);

		this.guiLeft = (this.width - sizeX) / 2;
		this.guiTop = (this.height - sizeY) / 2;

		this.mobScrollList = new GUIScrollingList(fontRendererObj, filteredMobList, this::getMobName, backgroundTexture, guiLeft, guiTop, 6, 31, 4, 6, 86, 83, 8, 97, 32, 12, 15, 83, 1, 10, 0.7F);
		this.filterScrollingList = new GUIScrollingList(fontRendererObj, turret.mobFilter, this::getMobName, backgroundTexture, guiLeft, guiTop, 132, 31, 4, 6, 86, 83, 8, 223, 32, 12, 15, 83, 1, 10, 0.7F);

		this.mobSearchField = new GuiTextField(this.fontRendererObj, guiLeft + 8, guiTop + 14, 84, 14);
		this.mobSearchField.setEnableBackgroundDrawing(false);
		this.mobSearchField.setTextColor(-1);
		this.mobSearchField.setFocused(true);
		this.mobSearchField.setMaxStringLength(20);

		Keyboard.enableRepeatEvents(true);
	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer();
		drawGuiContainerForegroundLayer();

		mobScrollList.drawScreen(mouseX, mouseY);
		filterScrollingList.drawScreen(mouseX, mouseY);
		mobSearchField.drawTextBox();

		drawTooltips(mouseX, mouseY);
	}

	private String getMobName(String id) {
		if(!EntityList.stringToClassMapping.containsKey(id)) {
			return id;
		}

		String translationKey = "entity." + id + ".name";

		if(!StatCollector.canTranslate(translationKey)) {
			return id;
		}

		String result = EnumChatFormatting.getTextWithoutFormattingCodes(StatCollector.translateToLocal(translationKey));

		if(result == null) {
			return id;
		}

		return result;
	}

	private void drawGuiContainerBackgroundLayer() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, sizeX, sizeY);
	}

	private void drawGuiContainerForegroundLayer() {
		int textureX = 0;

		if(!turret.isBlacklistMobFilter) {
			textureX = 10;
		}

		drawTexturedModalRect(guiLeft + 227, guiTop + 5, textureX, 124, 10, 12);
	}

	private void drawTooltips(int mouseX, int mouseY) {
		if(mouseX >= guiLeft + 199 && mouseX <= guiLeft + 211 && mouseY >= guiTop + 5 && mouseY <= guiTop + 17) {
			drawHoveringText(Collections.singletonList(I18n.format("desc.gui.turret_mob_filter.import")), mouseX, mouseY, fontRendererObj);
		}

		if(mouseX >= guiLeft + 213 && mouseX <= guiLeft + 225 && mouseY >= guiTop + 5 && mouseY <= guiTop + 17) {
			drawHoveringText(Collections.singletonList(I18n.format("desc.gui.turret_mob_filter.export")), mouseX, mouseY, fontRendererObj);
		}
	}

	@Override protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		boolean mobDoubleClick = mobScrollList.mouseClicked(mouseX, mouseY, mouseButton);
		boolean filterDoubleClick = filterScrollingList.mouseClicked(mouseX, mouseY, mouseButton);
		mobSearchField.mouseClicked(mouseX, mouseY, mouseButton);

		// toggle (black/white)list
		if(mouseX >= guiLeft + 227 && mouseX <= guiLeft + 237 && mouseY >= guiTop + 5 && mouseY <= guiTop + 17) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(turret.xCoord, turret.yCoord, turret.zCoord, 0, 6));
		}

		// add entity to filter
		if(mouseX >= guiLeft + 112 && mouseX <= guiLeft + 130 && mouseY >= guiTop + 31 && mouseY <= guiTop + 49 || mobDoubleClick) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(mobScrollList.selectedSlot == -1) {
				return;
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setString("addMobFilter", filteredMobList.get(mobScrollList.selectedSlot));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, turret.xCoord, turret.yCoord, turret.zCoord));
		}

		// remove entity from filter
		if(mouseX >= guiLeft + 112 && mouseX <= guiLeft + 130 && mouseY >= guiTop + 52 && mouseY <= guiTop + 70 || filterDoubleClick) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			if(filterScrollingList.selectedSlot == -1) {
				return;
			}

			int turretMobFilterSize = turret.mobFilter.size();

			NBTTagCompound data = new NBTTagCompound();
			data.setString("removeMobFilter", turret.mobFilter.get(filterScrollingList.selectedSlot));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, turret.xCoord, turret.yCoord, turret.zCoord));

			turretMobFilterSize--;

			if(turretMobFilterSize > 0) {
				filterScrollingList.selectedSlot = 0;
			} else {
				filterScrollingList.selectedSlot = -1;
			}
		}

		// import mob list
		if(mouseX >= guiLeft + 199 && mouseX <= guiLeft + 211 && mouseY >= guiTop + 5 && mouseY <= guiTop + 17) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			ItemStack stack = player.getHeldItem();

			if(stack.getItem() != ModItems.turret_mob_filter) {
				return;
			}

			NBTTagCompound itemNbt = stack.getTagCompound();

			if(itemNbt != null && itemNbt.hasKey("mobs", Constants.NBT.TAG_LIST)) {
				NBTTagList mobs = itemNbt.getTagList("mobs", Constants.NBT.TAG_STRING);

				NBTTagCompound data = new NBTTagCompound();
				data.setTag("setMobFilter", mobs.copy());
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, turret.xCoord, turret.yCoord, turret.zCoord));
			}
		}

		// export mob list
		if(mouseX >= guiLeft + 213 && mouseX <= guiLeft + 225 && mouseY >= guiTop + 5 && mouseY <= guiTop + 17) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			ItemStack stack = player.getHeldItem();

			if(stack.getItem() != ModItems.turret_mob_filter) {
				return;
			}

			NBTTagCompound itemNbt = stack.getTagCompound();

			if(itemNbt == null) {
				itemNbt = new NBTTagCompound();
				stack.setTagCompound(itemNbt);
			}

			NBTTagList list = new NBTTagList();

			for(String mob : turret.mobFilter) {
				addMobToItem(itemNbt, list, mob);
			}
		}
	}

	public void addMobToItem(NBTTagCompound itemNbt, NBTTagList list, String value) {
		list.appendTag(new NBTTagString(value));
		itemNbt.setTag("mobs", list);
	}

	@Override protected void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
		super.mouseMovedOrUp(mouseX, mouseY, mouseButton);

		mobScrollList.mouseReleased();
		filterScrollingList.mouseReleased();
	}

	@Override public void handleMouseInput() {
		super.handleMouseInput();

		mobScrollList.handleMouseInput();
		filterScrollingList.handleMouseInput();
	}

	private void updateMobList() {
		filteredMobList.clear();
		filteredMobList.addAll(mobList.stream().filter(id -> {
			String name = getMobName(id);
			String searchName = mobSearchField.getText();
			return id.toLowerCase().contains(searchName) || name.toLowerCase().contains(searchName);
		}).collect(Collectors.toList()));

		mobScrollList.selectedSlot = -1;
		mobScrollList.resetScroll();
	}

	@Override public boolean doesGuiPauseGame() {
		return false;
	}

	@Override public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
