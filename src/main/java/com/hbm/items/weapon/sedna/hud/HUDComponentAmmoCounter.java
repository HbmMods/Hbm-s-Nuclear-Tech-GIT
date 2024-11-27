package com.hbm.items.weapon.sedna.hud;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class HUDComponentAmmoCounter implements IHUDComponent {
	
	private static final ResourceLocation misc = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_misc.png");
	protected static final RenderItem itemRenderer = RenderItem.getInstance();
	protected int receiver;
	protected boolean mirrored;
	protected boolean noCounter;
	
	public HUDComponentAmmoCounter(int receiver) {
		this.receiver = receiver;
	}
	
	public HUDComponentAmmoCounter mirror() {
		this.mirrored = true;
		return this;
	}
	
	public HUDComponentAmmoCounter noCounter() {
		this.noCounter = true;
		return this;
	}

	@Override
	public int getComponentHeight(EntityPlayer player, ItemStack stack){
		return 24;
	}

	@Override
	public void renderHUDComponent(Pre event, ElementType type, EntityPlayer player, ItemStack stack, int bottomOffset, int gunIndex) {

		if(type != type.HOTBAR) return;
		ScaledResolution resolution = event.resolution;
		Minecraft mc = Minecraft.getMinecraft();
		
		int pX = resolution.getScaledWidth() / 2 + (mirrored ? -(62 + 36 + 52) : (62 + 36)) + (noCounter ? 14 : 0);
		int pZ = resolution.getScaledHeight() - bottomOffset - 23;
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		IMagazine mag = gun.getConfig(stack, gunIndex).getReceivers(stack)[this.receiver].getMagazine(stack);
		
		if(!noCounter) mc.fontRenderer.drawString(mag.reportAmmoStateForHUD(stack, player), pX + 17, pZ + 6, 0xFFFFFF);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), mag.getIconForHUD(stack, player), pX, pZ);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		mc.renderEngine.bindTexture(misc);
	}
}
