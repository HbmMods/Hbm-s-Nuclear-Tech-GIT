package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.NotableComments;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.DoorDecl;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

// the ItemRenderLibrary system sucks and is ugly as hell but it's the quickest way of doing it
// due to the sheer size if this stupid fucking class i just janked this part out into its own thing
// that way, code hotswap doesn't take 5 years to work while vomitting out error messages like
// i vomitted out that 20 pack of soggy chicken nuggets last year
// still a better use of €15 than hollow knight
@NotableComments
public class ItemRenderLibraryDoors {

	public static void init() {

		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.vault_door), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.vault_cog_tex); ResourceManager.vault_cog.renderAll();
				bindTexture(ResourceManager.vault_label_101_tex); ResourceManager.vault_label.renderAll();
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.secure_access_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3.75, 0);
				GL11.glScaled(2.4, 2.4, 2.4);
			}
			public void renderCommon() {
				GL11.glTranslated(0, 1, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(DoorDecl.SECURE_ACCESS_DOOR.getCyclingSkins());
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_secure_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.fire_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				Minecraft.getMinecraft().getTextureManager().bindTexture(DoorDecl.FIRE_DOOR.getCyclingSkins());
				GL11.glRotated(90, 0, 1, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_fire_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.sliding_blast_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -2.75, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.pheo_blast_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_blast_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.large_vehicle_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(1.8, 1.8, 1.8);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				bindTexture(ResourceManager.pheo_vehicle_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_vehicle_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.water_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				bindTexture(ResourceManager.pheo_water_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_water_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.silo_hatch), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.silo_hatch_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glRotated(90, 0, 1, 0);
				ResourceManager.silo_hatch.renderPart("Frame");
				GL11.glTranslated(0, 0.875, -1.875);
				GL11.glRotated(-120, 1, 0, 0);
				GL11.glTranslated(0, -0.875, 1.875);
				GL11.glTranslated(0, 0.25, 0);
				ResourceManager.silo_hatch.renderPart("Hatch");
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.silo_hatch_large), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(1.5, 1.5, 1.5);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.silo_hatch_large_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glTranslated(1, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
				ResourceManager.silo_hatch_large.renderPart("Frame");
				GL11.glTranslated(0, 0.875, -2.875);
				GL11.glRotated(-120, 1, 0, 0);
				GL11.glTranslated(0, -0.875, 2.875);
				GL11.glTranslated(0, 0.25, 0);
				ResourceManager.silo_hatch_large.renderPart("Hatch");
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.qe_containment), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(3.8, 3.8, 3.8);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.pheo_containment_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_containment_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.qe_sliding_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.pheo_sliding_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_sliding_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.round_airlock_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3.75, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				Minecraft.getMinecraft().getTextureManager().bindTexture(DoorDecl.ROUND_AIRLOCK_DOOR.getCyclingSkins());
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_airlock_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.sliding_seal_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(7, 7, 7);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.pheo_seal_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.pheo_seal_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.blast_door), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.blast_door_base_tex); ResourceManager.blast_door_base.renderAll();
				bindTexture(ResourceManager.blast_door_tooth_tex); ResourceManager.blast_door_tooth.renderAll();
				bindTexture(ResourceManager.blast_door_slider_tex); ResourceManager.blast_door_slider.renderAll();
				bindTexture(ResourceManager.blast_door_block_tex); ResourceManager.blast_door_block.renderAll();
			}});
	}

	private static void bindTexture(ResourceLocation res) {
		Minecraft.getMinecraft().renderEngine.bindTexture(res);
	}
}
