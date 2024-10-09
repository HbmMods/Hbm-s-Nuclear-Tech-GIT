package com.hbm.items.weapon.sedna.hud;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class HUDComponentAmmoCounter implements IHUDComponent {

	protected static final RenderItem itemRenderer = RenderItem.getInstance();
	protected int receiver;
	protected boolean mirrored;
	
	public HUDComponentAmmoCounter(int receiver) {
		this(receiver, false);
	}
	
	public HUDComponentAmmoCounter(int receiver, boolean mirror) {
		this.receiver = receiver;
		this.mirrored = mirror;
	}

	@Override
	public int getComponentHeight(EntityPlayer player, ItemStack stack){
		return 22;
	}

	@Override
	public void renderHUDComponent(Pre event, ElementType type, EntityPlayer player, ItemStack stack, int bottomOffset, int gunIndex) {

		ScaledResolution resolution = event.resolution;
		Minecraft mc = Minecraft.getMinecraft();
		
		int pX = resolution.getScaledWidth() / 2 + (mirrored ? -(62 + 36 + 52) : (62 + 36));
		int pZ = resolution.getScaledHeight() - bottomOffset - 21;
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		IMagazine mag = gun.getConfig(stack, gunIndex).getReceivers(stack)[this.receiver].getMagazine(stack);
		
		mc.fontRenderer.drawString(mag.reportAmmoStateForHUD(stack), pX + 17, pZ + 6, 0xFFFFFF);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), mag.getIconForHUD(stack), pX, pZ);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
