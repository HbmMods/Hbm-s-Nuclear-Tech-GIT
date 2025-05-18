package com.hbm.render.item;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.ResourceManager;
import com.hbm.render.tileentity.RenderBobble;
import com.hbm.render.tileentity.RenderDemonLamp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Deprecated // implement IItemRendererProvider for TESRs instead!
public class ItemRenderLibrary {

	public static HashMap<Item, ItemRenderBase> renderers = new HashMap();

	public static void init() {

		renderers.put(Item.getItemFromBlock(ModBlocks.obj_tester), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.soyuz_module_dome_tex); ResourceManager.soyuz_module.renderPart("Dome");
		        bindTexture(ResourceManager.soyuz_module_lander_tex); ResourceManager.soyuz_module.renderPart("Capsule");
		        bindTexture(ResourceManager.soyuz_module_propulsion_tex); ResourceManager.soyuz_module.renderPart("Propulsion");
		        bindTexture(ResourceManager.soyuz_module_solar_tex); ResourceManager.soyuz_module.renderPart("Solar");
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

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_reactor_breeding), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
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
				bindTexture(ResourceManager.universal_bright); ResourceManager.turbine.renderPart("Blades");
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.reactor_research), new ItemRenderBase() {
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
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4, 4, 4);
				GL11.glRotated(90, 0, 1, 0);
			}
			public void renderCommon() {
				GL11.glTranslated(0, 0, -0.5);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.igen_tex);
				ResourceManager.igen.renderPart("Body");
				ResourceManager.igen.renderPart("Rotor");
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_radgen), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslated(0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.radgen_tex);
				ResourceManager.radgen.renderPart("Base");
				ResourceManager.radgen.renderPart("Rotor");
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor3f(0F, 1F, 0F);
				ResourceManager.radgen.renderPart("Light");
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glShadeModel(GL11.GL_FLAT);
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
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.chemplant_body_tex); ResourceManager.chemplant_body.renderAll();
		        GL11.glShadeModel(GL11.GL_FLAT);
		        bindTexture(ResourceManager.chemplant_piston_tex); ResourceManager.chemplant_piston.renderAll();
		        bindTexture(ResourceManager.chemplant_spinner_tex);
				GL11.glTranslated(-0.625, 0, 0.625);
		        ResourceManager.chemplant_spinner.renderAll();
				GL11.glTranslated(1.25, 0, 0);
		        ResourceManager.chemplant_spinner.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_well), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.derrick_tex); ResourceManager.derrick.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
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
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.pumpjack_tex); ResourceManager.pumpjack.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_flare), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.oilflare_tex); ResourceManager.oilflare.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
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

		renderers.put(Item.getItemFromBlock(ModBlocks.plasma_heater), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, 14);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.iter_microwave); ResourceManager.iter.renderPart("Microwave");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.tesla), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(6, 6, 6);
			}
			public void renderCommon() {
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.tesla_tex); ResourceManager.tesla.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.boxcar), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
	        	bindTexture(ResourceManager.boxcar_tex); ResourceManager.boxcar.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.boat), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glRotated(-90, 0, 1, 0);
				GL11.glTranslated(0, 1, 0);
				GL11.glScaled(1.75, 1.75, 1.75);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, -3);
				bindTexture(ResourceManager.duchessgambit_tex); ResourceManager.duchessgambit.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.nuke_boy), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glTranslated(-1, 0, 0);
		        bindTexture(ResourceManager.bomb_boy_tex);
		        ResourceManager.bomb_boy.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.nuke_n2), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
		        bindTexture(ResourceManager.n2_tex);
		        ResourceManager.n2.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.nuke_fstbmb), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			public void renderCommon() {
				GL11.glTranslated(1, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        bindTexture(ResourceManager.fstbmb_tex);
		        ResourceManager.fstbmb.renderPart("Body");
		        ResourceManager.fstbmb.renderPart("Balefire");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.nuke_custom), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glTranslated(-1, 0, 0);
		        bindTexture(ResourceManager.bomb_custom_tex);
		        ResourceManager.bomb_boy.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.bomb_multi), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glTranslated(0.75, 0, 0);
				GL11.glScaled(3, 3, 3);
				GL11.glTranslated(0, 0.5, 0);
		        GL11.glRotatef(180, 1F, 0F, 0F);
		        GL11.glRotatef(90, 0F, 1F, 0F);
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.bomb_multi_tex);
		        ResourceManager.bomb_multi.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.mine_ap), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(8, 8, 8);
			}
			public void renderCommon() {
				GL11.glScaled(1.25, 1.25, 1.25);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.mine_ap_grass_tex); ResourceManager.mine_ap.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.mine_he), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(6, 6, 6);
			}
			public void renderNonInv() {
				GL11.glTranslated(0.25, 0.625, 0);
				GL11.glRotated(45, 0, 1, 0);
				GL11.glRotated(-15, 0, 0, 1);
			}
			public void renderCommon() {
				GL11.glScaled(4, 4, 4);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.mine_marelet_tex); ResourceManager.mine_marelet.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		});

		renderers.put(Item.getItemFromBlock(ModBlocks.mine_shrap), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(8, 8, 8);
			}
			public void renderCommon() {
				GL11.glScaled(1.25, 1.25, 1.25);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.mine_shrap_tex); ResourceManager.mine_ap.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.mine_naval), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, 2, -1);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glTranslated(0, 0, 0);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.mine_naval_tex);
				ResourceManager.mine_naval.renderAll();
				GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.mine_fat), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(7, 7, 7);
			}
			public void renderCommon() {
				GL11.glTranslated(0.25, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
	            GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.mine_fat_tex);
	        	ResourceManager.mine_fat.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_forcefield), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(6, 6, 6);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.forcefield_base_tex); ResourceManager.radar_body.renderAll();
		        GL11.glTranslated(0, 1D, 0);
		        bindTexture(ResourceManager.forcefield_top_tex); ResourceManager.forcefield_top.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_missile_assembly), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(10, 10, 10);
			}
			public void renderCommon() {
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        bindTexture(ResourceManager.missile_assembly_tex); ResourceManager.missile_assembly.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.launch_pad), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.missile_pad_tex); ResourceManager.missile_pad.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.compact_launcher), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				bindTexture(ResourceManager.compact_launcher_tex); ResourceManager.compact_launcher.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.launch_table), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				bindTexture(ResourceManager.launch_table_base_tex); ResourceManager.launch_table_base.renderAll();
				bindTexture(ResourceManager.launch_table_small_pad_tex); ResourceManager.launch_table_small_pad.renderAll();
				GL11.glTranslatef(0F, 0F, 2.5F);
				for(int i = 0; i < 8; i++) {
					GL11.glTranslatef(0F, 1F, 0.F);
					if(i < 6) {
						bindTexture(ResourceManager.launch_table_small_scaffold_base_tex); ResourceManager.launch_table_small_scaffold_base.renderAll();
					}
					if(i == 6) {
						bindTexture(ResourceManager.launch_table_small_scaffold_connector_tex); ResourceManager.launch_table_small_scaffold_connector.renderAll();
					}
					if(i > 6) {
						bindTexture(ResourceManager.launch_table_small_scaffold_base_tex); ResourceManager.launch_table_small_scaffold_empty.renderAll();
					}
				}
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.soyuz_capsule), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
	            GL11.glShadeModel(GL11.GL_SMOOTH);
	        	bindTexture(ResourceManager.soyuz_lander_tex); ResourceManager.soyuz_lander.renderPart("Capsule");
	            GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_radar), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.radar_base_tex); ResourceManager.radar.renderPart("Base");
				GL11.glTranslated(-0.125, 0, 0);
				bindTexture(ResourceManager.radar_dish_tex); ResourceManager.radar.renderPart("Dish");
				GL11.glEnable(GL11.GL_CULL_FACE);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(6, 6, 6);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, -1, 0);
		        bindTexture(ResourceManager.uf6_tex); ResourceManager.tank.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(6, 6, 6);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, -1, 0);
		        bindTexture(ResourceManager.puf6_tex); ResourceManager.tank.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.sat_dock), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, -1, 0);
		        bindTexture(ResourceManager.satdock_tex); ResourceManager.satDock.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.vault_door), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
		        bindTexture(ResourceManager.vault_cog_tex); ResourceManager.vault_cog.renderAll();
		        bindTexture(ResourceManager.vault_label_101_tex); ResourceManager.vault_label.renderAll();
			}});
		renderers.put(Item.getItemFromBlock(ModBlocks.secure_access_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2.4, 2.4, 2.4);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.secure_access_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.secure_access_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		renderers.put(Item.getItemFromBlock(ModBlocks.large_vehicle_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(1.8, 1.8, 1.8);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.large_vehicle_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.large_vehicle_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		renderers.put(Item.getItemFromBlock(ModBlocks.water_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.water_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.water_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		renderers.put(Item.getItemFromBlock(ModBlocks.silo_hatch), new ItemRenderBase(){
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
		renderers.put(Item.getItemFromBlock(ModBlocks.silo_hatch_large), new ItemRenderBase(){
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
		renderers.put(Item.getItemFromBlock(ModBlocks.qe_containment), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(3.8, 3.8, 3.8);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.qe_containment_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.qe_containment.renderAllExcept("decal");
				bindTexture(ResourceManager.qe_containment_decal);
				ResourceManager.qe_containment.renderPart("decal");
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		renderers.put(Item.getItemFromBlock(ModBlocks.qe_sliding_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(6, 6, 6);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.qe_sliding_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.qe_sliding_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		renderers.put(Item.getItemFromBlock(ModBlocks.round_airlock_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.round_airlock_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.round_airlock_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		renderers.put(Item.getItemFromBlock(ModBlocks.sliding_seal_door), new ItemRenderBase(){
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(7, 7, 7);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.sliding_seal_door_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.sliding_seal_door.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		renderers.put(Item.getItemFromBlock(ModBlocks.blast_door), new ItemRenderBase() {
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

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_microwave), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 4);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glTranslated(-2, -2, 1);
				GL11.glScaled(3, 3, 3);
				bindTexture(ResourceManager.microwave_tex);
		        ResourceManager.microwave.renderPart("mainbody_Cube.001");
		        ResourceManager.microwave.renderPart("window_Cube.002");
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_solar_boiler), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
	            GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.solar_tex); ResourceManager.solar_boiler.renderPart("Base");
	            GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.solar_mirror), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(8, 8, 8);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.solar_mirror_tex);
				ResourceManager.solar_mirror.renderPart("Base");
				GL11.glTranslated(0, 1, 0);
				GL11.glRotated(45, 0, 0, -1);
				GL11.glTranslated(0, -1, 0);
				ResourceManager.solar_mirror.renderPart("Mirror");
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_chekhov), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				bindTexture(ResourceManager.turret_chekhov_tex); ResourceManager.turret_chekhov.renderPart("Body");
				bindTexture(ResourceManager.turret_chekhov_barrels_tex); ResourceManager.turret_chekhov.renderPart("Barrels");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_friendly), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_friendly_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_friendly_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				bindTexture(ResourceManager.turret_chekhov_tex); ResourceManager.turret_chekhov.renderPart("Body");
				bindTexture(ResourceManager.turret_chekhov_barrels_tex); ResourceManager.turret_chekhov.renderPart("Barrels");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_jeremy), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glTranslated(-0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				bindTexture(ResourceManager.turret_jeremy_tex); ResourceManager.turret_jeremy.renderPart("Gun");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_tauon), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				bindTexture(ResourceManager.turret_tauon_tex); ResourceManager.turret_tauon.renderPart("Cannon");
				ResourceManager.turret_tauon.renderPart("Rotor");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_richard), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				bindTexture(ResourceManager.turret_richard_tex); ResourceManager.turret_richard.renderPart("Launcher");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_howard), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_ciws_tex); ResourceManager.turret_howard.renderPart("Carriage");
				bindTexture(ResourceManager.turret_howard_tex); ResourceManager.turret_howard.renderPart("Body");
				bindTexture(ResourceManager.turret_howard_barrels_tex); ResourceManager.turret_howard.renderPart("BarrelsTop");
				bindTexture(ResourceManager.turret_howard_barrels_tex); ResourceManager.turret_howard.renderPart("BarrelsBottom");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_howard_damaged), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_rusted); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_ciws_rusted); ResourceManager.turret_howard.renderPart("Carriage");
				bindTexture(ResourceManager.turret_howard_rusted); ResourceManager.turret_howard_damaged.renderPart("Body");
				bindTexture(ResourceManager.turret_howard_barrels_rusted); ResourceManager.turret_howard_damaged.renderPart("BarrelsTop");
				bindTexture(ResourceManager.turret_howard_barrels_rusted); ResourceManager.turret_howard_damaged.renderPart("BarrelsBottom");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_silex), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.silex_tex); ResourceManager.silex.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_fel), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glTranslated(1, 0, 0);
				GL11.glRotated(90, 0, -1, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.fel_tex); ResourceManager.fel.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.rbmk_console), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.rbmk_console_tex);
				ResourceManager.rbmk_console.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.rbmk_crane_console), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.rbmk_crane_console_tex);
				ResourceManager.rbmk_crane_console.renderPart("Console_Coonsole");
				ResourceManager.rbmk_crane_console.renderPart("JoyStick");
				ResourceManager.rbmk_crane_console.renderPart("Meter1");
				ResourceManager.rbmk_crane_console.renderPart("Meter2");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.lamp_demon), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(8, 8, 8);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(RenderDemonLamp.tex);
				RenderDemonLamp.demon_lamp.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_storage_drum), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
				bindTexture(ResourceManager.waste_drum_tex);
				ResourceManager.waste_drum.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_chungus), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0.5, 0, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotated(90, 0, 1, 0);
				bindTexture(ResourceManager.chungus_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.chungus.renderPart("Body");
				ResourceManager.chungus.renderPart("Lever");
				ResourceManager.chungus.renderPart("Blades");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_maxwell), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(-1, -3, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_ciws_tex); ResourceManager.turret_howard.renderPart("Carriage");
				bindTexture(ResourceManager.turret_maxwell_tex); ResourceManager.turret_maxwell.renderPart("Microwave");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_fritz), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(4, 4, 4);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				bindTexture(ResourceManager.turret_fritz_tex); ResourceManager.turret_fritz.renderPart("Gun");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_bat9000), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.bat9000_tex); ResourceManager.bat9000.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_orbus), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.orbus_tex); ResourceManager.orbus.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.watz), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.watz_tex); ResourceManager.watz.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_fraction_tower), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				bindTexture(ResourceManager.fraction_tower_tex); ResourceManager.fraction_tower.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.fraction_spacer), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				bindTexture(ResourceManager.fraction_spacer_tex); ResourceManager.fraction_spacer.renderAll();
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_tower_small), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.tower_small_tex); ResourceManager.tower_small.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_tower_large), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(4 * 0.95, 4 * 0.95, 4 * 0.95);
			}
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.tower_large_tex); ResourceManager.tower_large.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_fracking_tower), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.fracking_tower_tex); ResourceManager.fracking_tower.renderAll();
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.bobblehead), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(10, 10, 10);
			}
			public void renderCommonWithStack(ItemStack stack) {
				GL11.glScaled(0.5, 0.5, 0.5);
				RenderBobble.instance.renderBobble(BobbleType.values()[stack.getItemDamage()]);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_deuterium_tower), new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(3, 3, 3);
			}

			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.deuterium_tower_tex); ResourceManager.deuterium_tower.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});

		renderers.put(Item.getItemFromBlock(ModBlocks.reactor_zirnox), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.8, 2.8, 2.8);
			}
			public void renderCommon() {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.zirnox_tex); ResourceManager.zirnox.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_catalytic_cracker), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(1.8, 1.8, 1.8);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.cracking_tower_tex); ResourceManager.cracking_tower.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_liquefactor), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.liquefactor_tex); ResourceManager.liquefactor.renderPart("Main");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_solidifier), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.solidifier_tex); ResourceManager.solidifier.renderPart("Main");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_radiolysis), new ItemRenderBase( ) {
		public void renderInventory() {
			GL11.glTranslated(0, -2.5, 0);
			GL11.glScaled(3, 3, 3);
		}
		public void renderCommon() {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.radiolysis_tex); ResourceManager.radiolysis.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_chemfac), new ItemRenderBase( ) {
		public void renderInventory() {
			GL11.glScaled(2.5, 2.5, 2.5);
		}
		public void renderCommon() {
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.chemfac_tex); ResourceManager.chemfac.renderPart("Main");
			GL11.glShadeModel(GL11.GL_FLAT);
		}});

		renderers.put(Item.getItemFromBlock(ModBlocks.red_pylon_large), new ItemRenderBase( ) {
		public void renderInventory() {
			GL11.glTranslated(0, -5, 0);
			GL11.glScaled(2.25, 2.25, 2.25);
		}
		public void renderCommon() {
			GL11.glScaled(0.5, 0.5, 0.5);
			bindTexture(ResourceManager.pylon_large_tex); ResourceManager.pylon_large.renderAll();
		}});

		renderers.put(Item.getItemFromBlock(ModBlocks.substation), new ItemRenderBase( ) {
		public void renderInventory() {
			GL11.glTranslated(0, -2.5, 0);
			GL11.glScaled(4.5, 4.5, 4.5);
		}
		public void renderCommon() {
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.substation_tex); ResourceManager.substation.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}});

		renderers.put(Item.getItemFromBlock(ModBlocks.charger), new ItemRenderBase( ) {
		public void renderInventory() {
			GL11.glTranslated(0, -7, 0);
			GL11.glScaled(10, 10, 10);
		}
		public void renderCommon() {
			GL11.glScaled(2, 2, 2);
			GL11.glTranslated(0.5, 0, 0);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.charger_tex);
			ResourceManager.charger.renderPart("Base");
			ResourceManager.charger.renderPart("Slide");
			GL11.glShadeModel(GL11.GL_FLAT);
		}});

		renderers.put(Item.getItemFromBlock(ModBlocks.machine_assemfac), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.assemfac_tex); ResourceManager.assemfac.renderPart("Factory");
				for(int i = 1; i < 7; i++) {
					ResourceManager.assemfac.renderPart("Pivot" + i);
					ResourceManager.assemfac.renderPart("Arm" + i);
					ResourceManager.assemfac.renderPart("Piston" + i);
					ResourceManager.assemfac.renderPart("Striker" + i);
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.furnace_iron), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				bindTexture(ResourceManager.furnace_iron_tex);
				ResourceManager.furnace_iron.renderPart("Main");
				ResourceManager.furnace_iron.renderPart("Off");
			}});

		renderers.put(Item.getItemFromBlock(ModBlocks.turret_arty), new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(-3, -4, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glRotated(-90, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_arty_tex);
				ResourceManager.turret_arty.renderPart("Base");
				ResourceManager.turret_arty.renderPart("Carriage");
				GL11.glTranslated(0, 3, 0);
				GL11.glRotated(45, 1, 0, 0);
				GL11.glTranslated(0, -3, 0);
				ResourceManager.turret_arty.renderPart("Cannon");
				ResourceManager.turret_arty.renderPart("Barrel");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		renderers.put(ModItems.gear_large, new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -7, 0);
				GL11.glScaled(6, 6, 6);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glRotated(30, 1, 0, 0);
				GL11.glTranslated(0, 1.375, 0);
				GL11.glRotated(System.currentTimeMillis() % 3600 * 0.1F, 0, 0, 1);
				GL11.glTranslated(0, -1.375, 0);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 0, -0.875);

				if(item.getItemDamage() == 0)
					bindTexture(ResourceManager.stirling_tex);
				else
					bindTexture(ResourceManager.stirling_steel_tex);
				ResourceManager.stirling.renderPart("Cog");
			}});

		renderers.put(ModItems.sawblade, new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -7, 0);
				GL11.glScaled(6, 6, 6);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glRotated(30, 1, 0, 0);
				GL11.glTranslated(0, 1.375, 0);
				GL11.glRotated(System.currentTimeMillis() % 3600 * 0.2F, 0, 0, 1);
				GL11.glTranslated(0, -1.375, 0);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 0, -0.875);
				bindTexture(ResourceManager.sawmill_tex);
				ResourceManager.sawmill.renderPart("Blade");
			}});

		renderers.put(ModItems.ammo_himars, new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				double scale = 2.75D;
				GL11.glScaled(scale, scale, scale);
				GL11.glRotated(System.currentTimeMillis() % 3600 / 10D, 0, 1, 0);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 1.5, 0);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glRotated(90, 1, 0, 0);
				HIMARSRocket type = ItemAmmoHIMARS.itemTypes[item.getItemDamage()];
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(type.texture);
				if(type.modelType == 0) {
					GL11.glTranslated(0.75, 0, 0);
					ResourceManager.turret_himars.renderPart("RocketStandard");
					GL11.glTranslated(-1.5, 0, 0);
					GL11.glTranslated(0, -3.375D, 0);
					ResourceManager.turret_himars.renderPart("TubeStandard");
				} else {
					GL11.glTranslated(0.75, 0, 0);
					ResourceManager.turret_himars.renderPart("RocketSingle");
					GL11.glTranslated(-1.5, 0, 0);
					GL11.glTranslated(0, -3.375D, 0);
					ResourceManager.turret_himars.renderPart("TubeSingle");
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			}});

		//hi there! it seems you are trying to register a new item renderer, most likely for a tile entity.
		//please refer to the comment at the start of the file on how to do this without adding to this gigantic pile of feces.
	}

	private static void bindTexture(ResourceLocation res) {
		Minecraft.getMinecraft().renderEngine.bindTexture(res);
	}
}
