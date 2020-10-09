package com.hbm.render.item;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderLibrary {

	public static HashMap<Item, ItemRenderBase> renderers = new HashMap();
	
	public static void init() {
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_cyclotron), new ItemRenderBase() {
			public void renderInventory() {
					GL11.glScaled(2.25, 2.25, 2.25);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.cyclotron_tex); ResourceManager.cyclotron.renderPart("Body");
	        	bindTexture(ResourceManager.cyclotron_ashes);  ResourceManager.cyclotron.renderPart("B1");
	        	bindTexture(ResourceManager.cyclotron_book); ResourceManager.cyclotron.renderPart("B2");
	        	bindTexture(ResourceManager.cyclotron_gavel); ResourceManager.cyclotron.renderPart("B3");
	        	bindTexture(ResourceManager.cyclotron_coin); ResourceManager.cyclotron.renderPart("B4");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_centrifuge), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.centrifuge_new_tex); ResourceManager.centrifuge_new.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_gascent), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.centrifuge_gas_tex); ResourceManager.centrifuge_new.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.iter), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.iter_glass); ResourceManager.iter.renderPart("Windows");
		        bindTexture(ResourceManager.iter_motor); ResourceManager.iter.renderPart("Motors");
		        bindTexture(ResourceManager.iter_rails); ResourceManager.iter.renderPart("Rails");
		        bindTexture(ResourceManager.iter_toroidal); ResourceManager.iter.renderPart("Toroidal");
		        bindTexture(ResourceManager.iter_torus); ResourceManager.iter.renderPart("Torus");
		        bindTexture(ResourceManager.iter_solenoid); ResourceManager.iter.renderPart("Solenoid");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_press), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.press_body_tex); ResourceManager.press_body.renderAll();
				GL11.glTranslated(0, 0.5, 0);
		        bindTexture(ResourceManager.press_head_tex); ResourceManager.press_head.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_epress), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.epress_body_tex); ResourceManager.epress_body.renderAll();
				GL11.glTranslated(0, 1.5, 0);
		        bindTexture(ResourceManager.epress_head_tex); ResourceManager.epress_head.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_crystallizer), new ItemRenderBase() {
			public void renderNonInv() {
				GL11.glScaled(0.5, 0.5, 0.5);
			}
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(1.75, 1.75, 1.75);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.crystallizer_tex); ResourceManager.crystallizer.renderPart("Body");
		        bindTexture(ResourceManager.crystallizer_window_tex); ResourceManager.crystallizer.renderPart("Windows");
		        bindTexture(ResourceManager.crystallizer_spinner_tex); ResourceManager.crystallizer.renderPart("Spinner");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_reactor), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.breeder_tex); ResourceManager.breeder.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_large_turbine), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.turbine_tex); ResourceManager.turbine.renderPart("Body");
		        bindTexture(ResourceManager.turbofan_blades_tex); ResourceManager.turbine.renderPart("Blades");
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
	}
	
	private static void bindTexture(ResourceLocation res) {
		Minecraft.getMinecraft().renderEngine.bindTexture(res);
	}
}
