package com.hbm.inventory.gui;

import java.util.Random;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerLaunchPadRusted;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.render.item.ItemRenderMissileGeneric;
import com.hbm.tileentity.bomb.TileEntityLaunchPadRusted;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUILaunchPadRusted extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_launch_pad_rusted.png");
	private TileEntityLaunchPadRusted launchpad;

	public GUILaunchPadRusted(InventoryPlayer invPlayer, TileEntityLaunchPadRusted tedf) {
		super(new ContainerLaunchPadRusted(invPlayer, tedf));
		launchpad = tedf;
		
		this.xSize = 176;
		this.ySize = 236;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 26, guiTop + 36, 16, 16, mouseX, mouseY, EnumChatFormatting.YELLOW + "Release Missile", "Missile is locked in lauch position,", "releasing may cause damage to the missile.", "Damaged missile can not be put back", "into launching position.");
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		if(guiLeft + 26 <= x && guiLeft + 26 + 16 > x && guiTop + 36 < y && guiTop + 36 + 16 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("release", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, launchpad.xCoord, launchpad.yCoord, launchpad.zCoord));
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

		boolean hasCodes = launchpad.slots[1] != null && launchpad.slots[1].getItem() == ModItems.launch_code;
		boolean hasKey = launchpad.slots[2] != null && launchpad.slots[2].getItem() == ModItems.launch_key;

		if(hasCodes) drawTexturedModalRect(guiLeft + 121, guiTop + 32, 192, 0, 6, 8);
		if(hasKey) drawTexturedModalRect(guiLeft + 139, guiTop + 32, 192, 0, 6, 8);
		
		if(hasCodes && hasKey && launchpad.missileLoaded) {
			
			Random rand = new Random(launchpad.xCoord * 131_071 + launchpad.zCoord);
			int launchCodes = rand.nextInt(100_000_000);
			
			for(int i = 0; i < 8; i++) {
				int magnitude = (int) Math.pow(10, i);
				int digit = (launchCodes % (magnitude * 10)) / magnitude;
				drawTexturedModalRect(guiLeft + 109 + 6 * i, guiTop + 85, 192 + 6 * digit, 8, 6, 8);
			}
		}
		
		if(launchpad.missileLoaded) {
			Consumer<TextureManager> renderer = ItemRenderMissileGeneric.renderers.get(new ComparableStack(ModItems.missile_doomsday_rusted).makeSingular());
			if(renderer != null) {
				GL11.glPushMatrix();
				
				GL11.glTranslatef(guiLeft + 70, guiTop + 120, 100);

				double scale = 0.875D;
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
	}
}
