package com.hbm.items.weapon.sedna.hud;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class HUDComponentDurabilityBar implements IHUDComponent {
	
	private static final ResourceLocation misc = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_misc.png");
	
	protected boolean mirrored = false;

	public HUDComponentDurabilityBar() {
		this(false);
	}
	public HUDComponentDurabilityBar(boolean mirror) {
		this.mirrored = mirror;
	}

	@Override
	public int getComponentHeight(EntityPlayer player, ItemStack stack) {
		return 5;
	}

	@Override
	public void renderHUDComponent(Pre event, ElementType type, EntityPlayer player, ItemStack stack, int bottomOffset, int gunIndex) {

		if(type != type.HOTBAR) return;
		ScaledResolution resolution = event.resolution;
		Minecraft mc = Minecraft.getMinecraft();

		int pX = resolution.getScaledWidth() / 2 + (mirrored ? -(62 + 36 + 52) : (62 + 36));
		int pZ = resolution.getScaledHeight() - 21;
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		int dura = (int) (50 * gun.getWear(stack, gunIndex) / gun.getConfig(stack, gunIndex).getDurability(stack));
		
		GL11.glColor4f(1F, 1F, 1F, 1F);

		mc.renderEngine.bindTexture(misc);
		mc.ingameGUI.drawTexturedModalRect(pX, pZ + 16, 94, 0, 52, 3);
		mc.ingameGUI.drawTexturedModalRect(pX + 1, pZ + 16, 95, 3, 50 - dura, 3);
	}
}
