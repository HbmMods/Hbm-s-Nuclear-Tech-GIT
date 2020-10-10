package com.hbm.render.item;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemRenderLibrary {

	public static HashMap<Item, ItemRenderBase> renderers = new HashMap();
	
	public static void init() {
		
		renderers.put(Item.getItemFromBlock(ModBlocks.obj_tester), new ItemRenderBase() {
			public void renderInventory() {
			}
			public void renderCommon() {
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.soyuz_module_dome_tex);
		        ResourceManager.soyuz_module.renderPart("Dome");
		        bindTexture(ResourceManager.soyuz_module_lander_tex);
		        ResourceManager.soyuz_module.renderPart("Capsule");
		        bindTexture(ResourceManager.soyuz_module_propulsion_tex);
		        ResourceManager.soyuz_module.renderPart("Propulsion");
		        bindTexture(ResourceManager.soyuz_module_solar_tex);
		        ResourceManager.soyuz_module.renderPart("Solar");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
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
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_selenium), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.selenium_body_tex); ResourceManager.selenium_body.renderAll();
		        GL11.glTranslated(0.0D, 1.0D, 0.0D);
		        bindTexture(ResourceManager.selenium_rotor_tex); ResourceManager.selenium_rotor.renderAll();
		        bindTexture(ResourceManager.selenium_piston_tex);
		        for(int i = 0; i < 7; i++) {
		            ResourceManager.selenium_piston.renderAll(); GL11.glRotatef(360F/7F, 0, 0, 1);
		        }
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_reactor_small), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.reactor_small_base_tex); ResourceManager.reactor_small_base.renderAll();
		        bindTexture(ResourceManager.reactor_small_rods_tex); ResourceManager.reactor_small_rods.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_industrial_generator), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(4, 4, 4);
				GL11.glRotated(90, 0, 1, 0);
			}
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.igen_tex); ResourceManager.igen.renderPart("Base");
		        bindTexture(ResourceManager.igen_rotor); ResourceManager.igen.renderPart("Rotor");
		        bindTexture(ResourceManager.igen_cog); ResourceManager.igen.renderPart("CogLeft"); ResourceManager.igen.renderPart("CogRight");
		        bindTexture(ResourceManager.igen_pistons); ResourceManager.igen.renderPart("Pistons");
		        bindTexture(ResourceManager.igen_arm); ResourceManager.igen.renderPart("ArmLeft"); ResourceManager.igen.renderPart("ArmRight");
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_radgen), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslated(0, 0, 1.5);
		        bindTexture(ResourceManager.radgen_body_tex); ResourceManager.radgen_body.renderAll();
				GL11.glTranslated(0, 1.5, 0);
		        bindTexture(ResourceManager.turbofan_blades_tex); ResourceManager.radgen_rotor.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_fensu), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.fensu_tex); ResourceManager.fensu.renderPart("Base"); ResourceManager.fensu.renderPart("Disc");
		        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		        GL11.glDisable(GL11.GL_LIGHTING);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		        ResourceManager.fensu.renderPart("Lights");
		        GL11.glEnable(GL11.GL_LIGHTING);
		        GL11.glPopAttrib();
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_assembler), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.assembler_body_tex); ResourceManager.assembler_body.renderAll();
		        bindTexture(ResourceManager.assembler_slider_tex); ResourceManager.assembler_slider.renderAll();
		        bindTexture(ResourceManager.assembler_arm_tex); ResourceManager.assembler_arm.renderAll();
		        bindTexture(ResourceManager.assembler_cog_tex);
		        GL11.glPushMatrix();
				GL11.glTranslated(-0.6, 0.75, 1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
		        GL11.glPushMatrix();
				GL11.glTranslated(0.6, 0.75, 1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
		        GL11.glPushMatrix();
				GL11.glTranslated(-0.6, 0.75, -1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
		        GL11.glPushMatrix();
				GL11.glTranslated(0.6, 0.75, -1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_chemplant), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.chemplant_body_tex); ResourceManager.chemplant_body.renderAll();
		        bindTexture(ResourceManager.chemplant_piston_tex); ResourceManager.chemplant_piston.renderAll();
		        bindTexture(ResourceManager.chemplant_spinner_tex);
				GL11.glTranslated(-0.625, 0, 0.625);
		        ResourceManager.chemplant_spinner.renderAll();
				GL11.glTranslated(1.25, 0, 0);
		        ResourceManager.chemplant_spinner.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_fluidtank), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.tank_tex); ResourceManager.tank_body.renderAll();
				bindTexture(ResourceManager.tank_label_tex); ResourceManager.tank_label.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_well), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				bindTexture(ResourceManager.derrick_tex); ResourceManager.derrick.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_pumpjack), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
		        GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, 3);
				bindTexture(ResourceManager.pumpjack_base_tex); ResourceManager.pumpjack_base.renderAll();
		        GL11.glTranslated(0, 3.5, -2.5);
				bindTexture(ResourceManager.pumpjack_head_tex); ResourceManager.pumpjack_head.renderAll();
		        GL11.glTranslated(0, -2, -3);
				bindTexture(ResourceManager.pumpjack_rotor_tex); ResourceManager.pumpjack_rotor.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_flare), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
		        GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.oilflare_tex); ResourceManager.oilflare.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_refinery), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.refinery_tex);  ResourceManager.refinery.renderAll();
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_drill), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
		        GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.drill_body_tex); ResourceManager.drill_body.renderAll();
				bindTexture(ResourceManager.drill_bolt_tex); ResourceManager.drill_bolt.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_mining_laser), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -0.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.mining_laser_base_tex); ResourceManager.mining_laser.renderPart("Base");
				bindTexture(ResourceManager.mining_laser_pivot_tex); ResourceManager.mining_laser.renderPart("Pivot");
				GL11.glTranslated(0, -1, 0.75);
				GL11.glRotated(90, 1, 0, 0);
				bindTexture(ResourceManager.mining_laser_laser_tex); ResourceManager.mining_laser.renderPart("Laser");
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.machine_turbofan), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.turbofan_body_tex); ResourceManager.turbofan_body.renderAll();
				GL11.glTranslated(0, 1.5, 0);
		        bindTexture(ResourceManager.turbofan_blades_tex); ResourceManager.turbofan_blades.renderAll();
			}});
		
		renderers.put(Item.getItemFromBlock(ModBlocks.plasma_heater), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, 14);
		        bindTexture(ResourceManager.iter_microwave);
		        ResourceManager.iter.renderPart("Microwave");
			}});
	}
	
	private static void bindTexture(ResourceLocation res) {
		Minecraft.getMinecraft().renderEngine.bindTexture(res);
	}
}
