package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerLaunchPadLarge;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.ItemRenderMissileGeneric;
import com.hbm.tileentity.bomb.TileEntityLaunchPadBase;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUILaunchPadLarge extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_launch_pad_large.png");
	private TileEntityLaunchPadBase launchpad;

	public GUILaunchPadLarge(InventoryPlayer invPlayer, TileEntityLaunchPadBase tedf) {
		super(new ContainerLaunchPadLarge(invPlayer, tedf));
		launchpad = tedf;
		
		this.xSize = 176;
		this.ySize = 236;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 107, guiTop + 88 - 52, 16, 52, launchpad.power, launchpad.maxPower);
		launchpad.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 125, guiTop + 88 - 52, 16, 52);
		launchpad.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 88 - 52, 16, 52);

		if(this.mc.thePlayer.inventory.getItemStack() == null && this.isMouseOverSlot(this.inventorySlots.getSlot(1), mouseX, mouseY) && !this.inventorySlots.getSlot(1).getHasStack()) {
			ItemStack[] list = new ItemStack[] { new ItemStack(ModItems.designator), new ItemStack(ModItems.designator_range), new ItemStack(ModItems.designator_manual) };
			List<Object[]> lines = new ArrayList();
			ItemStack selected = list[(int) ((System.currentTimeMillis() % (1000 * list.length)) / 1000)];
			selected.stackSize = 0;
			lines.add(list);
			
			lines.add(new Object[] {I18nUtil.resolveKey(selected.getDisplayName())});
			this.drawStackText(lines, mouseX, mouseY, this.fontRendererObj);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.launchpad.hasCustomInventoryName() ? this.launchpad.getInventoryName() : I18n.format(this.launchpad.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int fuel = launchpad.getFuelState();
		int oxidizer = launchpad.getOxidizerState();

		if(fuel == 1) drawTexturedModalRect(guiLeft + 130, guiTop + 23, 192, 0, 6, 8);
		if(fuel == -1) drawTexturedModalRect(guiLeft + 130, guiTop + 23, 198, 0, 6, 8);
		if(oxidizer == 1) drawTexturedModalRect(guiLeft + 148, guiTop + 23, 192, 0, 6, 8);
		if(oxidizer == -1) drawTexturedModalRect(guiLeft + 148, guiTop + 23, 198, 0, 6, 8);
		if(launchpad.isMissileValid()) {
			drawTexturedModalRect(guiLeft + 112, guiTop + 23, launchpad.power >= 75_000 ? 192 : 198, 0, 6, 8);
		}

		int power = (int) (launchpad.power * 52 / launchpad.maxPower);
		drawTexturedModalRect(guiLeft + 107, guiTop + 88 - power, 176, 52 - power, 16, power);
		launchpad.tanks[0].renderTank(guiLeft + 125, guiTop + 88,this.zLevel, 16, 52);
		launchpad.tanks[1].renderTank(guiLeft + 143, guiTop + 88,this.zLevel, 16, 52);
		
		if(launchpad.slots[0] != null) {
			Consumer<TextureManager> renderer = ItemRenderMissileGeneric.renderers.get(new ComparableStack(launchpad.slots[0]).makeSingular());
			if(renderer != null) {
				GL11.glPushMatrix();
				
				GL11.glTranslatef(guiLeft + 70, guiTop + 120, 100);

				double scale = 1D;
				
				if(launchpad.slots[0].getItem() instanceof ItemMissile) {
					ItemMissile missile = (ItemMissile) launchpad.slots[0].getItem();
					switch(missile.formFactor) {
					case ABM: scale = 1.45D; break;
					case MICRO: scale = 2.5D; break;
					case V2: scale = 1.75D; break;
					case STRONG: scale = 1.375D; break;
					case HUGE: scale = 0.925D; break;
					case ATLAS: scale = 0.875D; break;
					case OTHER: break;
					}
					if(missile == ModItems.missile_stealth) scale = 1.125D;
				}
				
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glScaled(scale, scale, scale);
				GL11.glScalef(-8, -8, -8);

				GL11.glPushMatrix();
				GL11.glRotatef(75, 0.0F, 1.0F, 0.0F);
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
				
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				renderer.accept(Minecraft.getMinecraft().getTextureManager());
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
			}
		}
		
		GL11.glPushMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glTranslated(guiLeft + 34, guiTop + 107, 0);
		String text = "";
		int color = 0xffffff;
		if(launchpad.state == launchpad.STATE_MISSING) {
			GL11.glScaled(0.5, 0.5, 1);
			text = "Not ready";
			color = 0xff0000;
		}
		if(launchpad.state == launchpad.STATE_LOADING) {
			GL11.glScaled(0.6, 0.6, 1);
			text = "Loading...";
			color = 0xff8000;
		}
		if(launchpad.state == launchpad.STATE_READY) {
			GL11.glScaled(0.8, 0.8, 1);
			text = "Ready";
			color = 0x00ff000;
		}
		this.fontRendererObj.drawString(text, -this.fontRendererObj.getStringWidth(text) / 2, -this.fontRendererObj.FONT_HEIGHT / 2, color);
		GL11.glPopMatrix();
	}
}
