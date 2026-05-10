package com.hbm.blocks.test;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TestEventTester extends BlockContainer {
	
	public static final int SLOT_CLICK_ID_REFRESH = -666;
	
	public TestEventTester(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World worlb, int mettaton) {
		return new TileEntityTestStorage();
	}

	@Override
	public boolean onBlockActivated(World worlb, int x, int y, int z, EntityPlayer playor, int side, float hitX, float hitY, float hitZ) {
		if(!worlb.isRemote) FMLNetworkHandler.openGui(playor, MainRegistry.instance, 0, worlb, x, y, z);
		return true;
	}
	
	public static class TileEntityTestStorage extends TileEntityMachineBase implements IGUIProvider {

		public TileEntityTestStorage() {
			super(8 * 50); // just eat fucking shit at this point
		}

		@Override public String getName() { return "Balls"; }
		@Override public void updateEntity() { }

		@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerTestStorage(player.inventory, this); }
		@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUITestStorage(player.inventory, this); }
	}
	
	public static class ContainerTestStorage extends Container {
		
		protected TileEntityTestStorage tile;
		
		public ContainerTestStorage(InventoryPlayer invPlayer, TileEntityTestStorage tile) {
			this.tile = tile;
			setupWithScroll(tile, invPlayer, 0);
		}
		
		@Override
		public ItemStack slotClick(int slotIndex, int button, int mode, EntityPlayer player) {
			
			if(slotIndex == SLOT_CLICK_ID_REFRESH) {
				this.setupWithScroll(tile, player.inventory, mode);
				return null;
			}
			
			return super.slotClick(slotIndex, button, mode, player);
		}
		
		public void setupWithScroll(TileEntityTestStorage tile, InventoryPlayer invPlayer, int rowOffset) {
			this.inventorySlots.clear();
			this.inventoryItemStacks.clear();

			for(int row = 0; row < 6; row++) {
				for(int col = 0; col < 8; col++) {
					int index = col + (row + rowOffset) * 8;
					if(index >= tile.slots.length) continue;
					this.addSlotToContainer(new Slot(tile, index, 8 + col * 18, 18 + row * 18));
				}
			}

			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
				}
			}

			for(int i = 0; i < 9; i++) {
				this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 198));
			}
		}

		@Override
		public ItemStack transferStackInSlot(EntityPlayer player, int index) {
			return null;
		}

		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return tile.isUseableByPlayer(player);
		}
	}
	
	public static class GUITestStorage extends GuiContainer {

		public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_test_storage.png");
		protected int scrollIndex = 0;
		protected static final int scrollBounds = (50 - 7); // 50 rows total, minus 6 visible rows, minus 1 because we start at index 0
		protected boolean wasClicking = false;
		protected boolean draggingScroll = false;

		public GUITestStorage(InventoryPlayer invPlayer, TileEntityTestStorage tile) {
			super(new ContainerTestStorage(invPlayer, tile));
			
			this.xSize = 176;
			this.ySize = 222;
		}

		@Override
		public void drawScreen(int x, int y, float interp) {
			boolean isClicking = Mouse.isButtonDown(0);
			if(!isClicking) this.draggingScroll = false;
			
			if(!wasClicking && isClicking && guiLeft + 153 <= x && guiLeft + 153 + 14 > x && guiTop + 17 < y && guiTop + 17 + 108 >= y) {
				draggingScroll = true;
			}
			
			if(draggingScroll) {
				int range = 92; // 106 scroll bar size, -7 pixels on top and bottom
				int sY = MathHelper.clamp_int(y - guiTop - 25, 0, 92);
				double scrollFrac = (double) sY / (double) range;
				int row = (int) (scrollBounds * scrollFrac);
				this.setScroll(row);
			}
			
			this.wasClicking = isClicking;
			super.drawScreen(x, y, interp);
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			
			drawTexturedModalRect(guiLeft + getScrollBarXPos(), guiTop + getScrollBarYPos(), draggingScroll ? 188 : 176, 0, 12, 15);
		}

		@Override
		public void handleMouseInput() {
			super.handleMouseInput();
			
			int scrollDir = Mouse.getEventDWheel();

			if(scrollDir != 0) {

				if(scrollDir > 0) scrollDir = 1;
				if(scrollDir < 0) scrollDir = -1;
				this.setScroll(this.getScroll() - scrollDir);
			}
		}

		public int getScrollBarXPos() {
			return 154;
		}
		
		public int getScrollBarYPos() {
			int scrollArea = 106 - 15; // bar height minus the scroll knob's height
			double scrollProgress = (double) scrollIndex / (double) scrollBounds;
			int scrollYPos = 18 + (int) (scrollProgress * scrollArea);
			return scrollYPos;
		}
		
		public int getScroll() {
			return MathHelper.clamp_int(scrollIndex, 0, scrollBounds);
		}
		
		public void setScroll(int scroll) {
			int prevScroll = getScroll();
			this.scrollIndex = MathHelper.clamp_int(scroll, 0, scrollBounds);
			if(prevScroll != this.scrollIndex) refreshContainer();
		}
		
		public void refreshContainer() {
			this.mc.playerController.windowClick(this.inventorySlots.windowId, SLOT_CLICK_ID_REFRESH, 0, this.scrollIndex, this.mc.thePlayer);
		}
	}
}
