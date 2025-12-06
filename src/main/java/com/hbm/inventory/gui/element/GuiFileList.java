package com.hbm.inventory.gui.element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public class GuiFileList extends GuiListExtended {

	private List<Row> rows = new ArrayList<>();
	private int selectedId = -1;

	public GuiFileList(Minecraft mc, File[] files, Consumer<File> onSelect, String nameFilter, int width, int height, int top, int bottom, int slotHeight) {
		super(mc, width, height, top, bottom, slotHeight);

		for(File file : files) {
			if(file.getName().equals(nameFilter + ".nbt")) {
				selectedId = rows.size();
			}
			rows.add(new Row(file, onSelect, width, slotHeight));
		}
	}

	@Override
	public IGuiListEntry getListEntry(int id) {
		return rows.get(id);
	}

	@Override
	protected int getSize() {
		return rows.size();
	}

	@Override
	protected boolean isSelected(int id) {
		return id == selectedId;
	}

	public void select(String nameFilter) {
		for(int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i);
			if(row.file.getName().equals(nameFilter + ".nbt")) {
				selectedId = i;
				return;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static class Row implements IGuiListEntry {

		private final Minecraft mc;
		private final File file;

		private final int width;
		private final int height;

		private final Consumer<File> onSelect;

		public Row(File file, Consumer<File> onSelect, int width, int height) {
			this.mc = Minecraft.getMinecraft();
			this.file = file;

			this.width = width;
			this.height = height;

			this.onSelect = onSelect;
		}

		@Override
		public void drawEntry(int id, int x, int y, int width, int height, Tessellator tess, int mouseX, int mouseY, boolean isVisible) {
			mc.fontRenderer.drawString(file.getName(), x + 20, y + 1, 0xFFFFFF);
		}

		@Override
		public boolean mousePressed(int id, int mouseX, int mouseY, int button, int hoverX, int hoverY) {
			if(hoverX < 0 || hoverX > width) return false;
			if(hoverY < 0 || hoverY > height) return false;

			onSelect.accept(file);

			return true;
		}

		@Override
		public void mouseReleased(int id, int mouseX, int mouseY, int button, int hoverX, int hoverY) {

		}

	}

}
