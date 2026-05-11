package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.DiamondPronter;
import com.hbm.render.util.EnumSymbol;
import com.hbm.tileentity.machine.storage.TileEntityMachineBigAssTank;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderBigAssTank extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		TileEntityMachineBigAssTank bat = (TileEntityMachineBigAssTank) te;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		if(bat.tilted) {
			GL11.glTranslated(0, -1, 0);
			GL11.glRotated(10, 0, 0, 1);
			GL11.glRotated(5, 0, 1, 0);
		}
		
		switch(te.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.bigasstank_tex);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.bigasstank.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		
		FluidType type = bat.tank.getTankType();
		
		if(type != null && type != Fluids.NONE) {

			GL11.glPushMatrix();
			int poison = type.poison;
			int flammability = type.flammability;
			int reactivity = type.reactivity;
			EnumSymbol symbol = type.symbol;
			
			GL11.glRotatef(22.5F, 0, 1, 0);
			
			for(int j = 0; j < 2; j++) {
				
				GL11.glPushMatrix();
				GL11.glTranslated(5.5, 2, 0);
				DiamondPronter.pront(poison, flammability, reactivity, symbol);
				GL11.glPopMatrix();
				GL11.glRotatef(180, 0, 1, 0);
			}
			GL11.glPopMatrix();
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor3f(1F, 1F, 1F);

		bindTexture(bat.tank.getTankType().getTexture());
		
		Tessellator tess = Tessellator.instance;
		
		double height = bat.tank.getFill() * 1.5D / bat.tank.getMaxFill();
		double off = 5.9375;
		double speed = 250D;
		
		double minU = -((te.getWorldObj().getTotalWorldTime() % speed + interp) / speed) % 1D;
		double maxU = minU + 1;
		
		tess.startDrawingQuads();
		
		tess.addVertexWithUV(-off, 1.75, -0.25, minU, 0);
		tess.addVertexWithUV(-off, 1.75 + height, -0.25, minU, -height * 2);
		tess.addVertexWithUV(-off, 1.75 + height, 0.25, maxU, -height * 2);
		tess.addVertexWithUV(-off, 1.75, 0.25, maxU, 0);

		tess.addVertexWithUV(off, 1.75, -0.25, maxU, 0);
		tess.addVertexWithUV(off, 1.75 + height, -0.25, maxU, -height * 2);
		tess.addVertexWithUV(off, 1.75 + height, 0.25, minU, -height * 2);
		tess.addVertexWithUV(off, 1.75, 0.25, minU, 0);
		
		tess.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_bigasstank);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.bigasstank_tex);
				ResourceManager.bigasstank.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
