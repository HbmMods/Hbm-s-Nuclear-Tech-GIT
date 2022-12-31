package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.render.util.RenderItemStack;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTemplate implements IItemRenderer {
	
	private ItemStack currentItem;

	public boolean handleRenderType(ItemStack stack, IItemRenderer.ItemRenderType type) {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && (type == IItemRenderer.ItemRenderType.INVENTORY)) {

			if(stack.getItem() == ModItems.assembly_template) {
				ComparableStack st = ItemAssemblyTemplate.readType(stack);
				this.currentItem = st != null ? st.toStack() : AssemblerRecipes.recipeList.get(stack.getItemDamage()).toStack();
			}
			if(stack.getItem() == ModItems.chemistry_template)
				this.currentItem = new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage());
			
			if(stack.getItem() == ModItems.crucible_template)
				this.currentItem = CrucibleRecipes.indexMapping.get(stack.getItemDamage()).icon;
			
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
