package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.container.ContainerWeaponTable;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.weapon.sedna.ItemRenderWeaponBase;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class GUIWeaponTable extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_weapon_modifier.png");
	public int left;
	public int top;
	
	public double yaw = 20;
	public double pitch = -10;

	public GUIWeaponTable(InventoryPlayer player) {
		super(new ContainerWeaponTable(player));

		this.xSize = 176;
		this.ySize = 240;

		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		if(guiLeft + 8 <= x && guiLeft + 8 + 160 > x && guiTop + 18 < y && guiTop + 18 + 79 >= y) {
			if(Mouse.isButtonDown(0)) {
				double distX = (guiLeft + 8 + 80) - x;
				double distY = (guiTop + 18 + 39.5) - y;
				yaw = distX / 80D * -180D;
				pitch = distY / 39.5D * 90D;
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int key) {
		super.mouseClicked(x, y, key);
		
		if(guiLeft + 26 <= x && guiLeft + 26 + 7 > x && guiTop + 111 < y && guiTop + 111 + 10 >= y) {
			ContainerWeaponTable container = (ContainerWeaponTable) this.inventorySlots;
			ItemStack gun = container.gun.getStackInSlot(0);
			if(gun != null && gun.getItem() instanceof ItemGunBaseNT) {
				int configs = ((ItemGunBaseNT) gun.getItem()).getConfigCount();
				if(configs > 1) {
					container.index++;
					container.index %= configs;
					this.handleMouseClick(null, 0, container.index, 999_999);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mX, int mY) {

		String name = I18n.format("container.weaponsTable");
		this.fontRendererObj.drawString(name, (this.xSize) / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		ContainerWeaponTable container = (ContainerWeaponTable) this.inventorySlots;
		ItemStack gun = container.gun.getStackInSlot(0);
		
		if(gun != null && gun.getItem() instanceof ItemGunBaseNT) {
			drawTexturedModalRect(guiLeft + 35, guiTop + 112, 176 + 6 * container.index, 0, 6, 8);
			
			GL11.glPushMatrix();
			GL11.glTranslated(guiLeft + 88, guiTop + 57, 100);
			
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(gun, IItemRenderer.ItemRenderType.INVENTORY);
			
			if(customRenderer instanceof ItemRenderWeaponBase) {
				ItemRenderWeaponBase renderGun = (ItemRenderWeaponBase) customRenderer;

				GL11.glPushMatrix();
				GL11.glRotated(180, 1, 0, 0);
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();

				GL11.glRotated(yaw, 0, 1, 0);
				GL11.glRotated(pitch, 1, 0, 0);
				
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				renderGun.setupModTable(gun);
				renderGun.renderModTable(gun, container.index);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			}
			GL11.glPopMatrix();
		}
	}
}
