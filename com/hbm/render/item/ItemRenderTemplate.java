package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.render.misc.RenderDecoItem;
import com.hbm.render.misc.RenderItemStack;

import codechicken.lib.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTemplate implements IItemRenderer {
	
	private ItemStack currentItem;
	private RenderItem itemRenderer;
	private RenderManager renderManager = RenderManager.instance;

	public boolean handleRenderType(ItemStack stack, IItemRenderer.ItemRenderType type) {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && (type == IItemRenderer.ItemRenderType.INVENTORY)) {

			if(stack.getItem() == ModItems.assembly_template)
				this.currentItem = MachineRecipes.getOutputFromTempate(stack);
			if(stack.getItem() == ModItems.chemistry_template)
				this.currentItem = new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage());
			
			if(this.currentItem != null) {
				return true;
			}
			
			
		}
		return false;
	}

	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack stack, IItemRenderer.ItemRendererHelper renderHelper) {
		return false;
	}

	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object... args) {
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		if(currentItem != null)
			RenderItemStack.renderItemStack(0, 0, 1.0F, currentItem);
		else
			RenderItemStack.renderItemStack(0, 0, 1.0F, stack);
		
		GL11.glPopMatrix();
	}
}
