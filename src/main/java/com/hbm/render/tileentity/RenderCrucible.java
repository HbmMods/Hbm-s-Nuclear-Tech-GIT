package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityCrucible;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actors.ITileActorRenderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderCrucible extends TileEntitySpecialRenderer implements IItemRendererProvider, ITileActorRenderer {
	
	public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID, "textures/models/machines/lava.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		ITileActorRenderer.bindTexture(ResourceManager.crucible_tex);
		ResourceManager.crucible_heat.renderAll();
		
		TileEntityCrucible crucible = (TileEntityCrucible) tile;
		
		if(!crucible.recipeStack.isEmpty() || !crucible.wasteStack.isEmpty()) {
			int totalCap = crucible.recipeZCapacity + crucible.wasteZCapacity;
			int totalMass = 0;

			for(MaterialStack stack : crucible.recipeStack) totalMass += stack.amount;
			for(MaterialStack stack : crucible.wasteStack) totalMass += stack.amount;

			double level = ((double) totalMass / (double) totalCap) * 0.875D;
	
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

			ITileActorRenderer.bindTexture(lava);
			Tessellator tess = Tessellator.instance;
			tess.setNormal(0F, 1F, 0F);
			tess.startDrawingQuads();
			tess.addVertexWithUV(-1, 0.5 + level, -1, 0, 0);
			tess.addVertexWithUV(-1, 0.5 + level, 1, 0, 1);
			tess.addVertexWithUV(1, 0.5 + level, 1, 1, 1);
			tess.addVertexWithUV(1, 0.5 + level, -1, 1, 0);
			tess.draw();
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_crucible);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.crucible_tex);
				ResourceManager.crucible_heat.renderAll();
			}};
	}

	@Override
	public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
		int x = data.getInteger("x");
		int y = data.getInteger("y");
		int z = data.getInteger("z");
		renderTileEntityAt(world.getTileEntity(x, y, z), x, y, z, interp);
	}

	@Override
	public void updateActor(int ticks, NBTTagCompound data) { }
}
