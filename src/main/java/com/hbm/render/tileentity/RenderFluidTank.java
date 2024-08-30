package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.DiamondPronter;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderFluidTank extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineFluidTank tank = (TileEntityMachineFluidTank) tileEntity;
		FluidType type = tank.tank.getTankType();

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.tank_tex);
		
		if(!tank.hasExploded) {
			ResourceManager.fluidtank.renderPart("Frame");
			bindTexture(new ResourceLocation(RefStrings.MODID, getTextureFromType(tank.tank.getTankType())));
			ResourceManager.fluidtank.renderPart("Tank");
		} else {
			ResourceManager.fluidtank_exploded.renderPart("Frame");
			bindTexture(ResourceManager.tank_inner_tex);
			ResourceManager.fluidtank_exploded.renderPart("TankInner");
			bindTexture(new ResourceLocation(RefStrings.MODID, getTextureFromType(tank.tank.getTankType())));
			ResourceManager.fluidtank_exploded.renderPart("Tank");
		}

		GL11.glColor3d(1D, 1D, 1D);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		if(type != null && type != Fluids.NONE) {

			RenderHelper.disableStandardItemLighting();
			GL11.glPushMatrix();
			GL11.glTranslated(-0.25, 0.5, -1.501);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScalef(1.0F, 0.375F, 0.375F);
			DiamondPronter.pront(type.poison, type.flammability, type.reactivity, type.symbol);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, 0.5, 1.501);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScalef(1.0F, 0.375F, 0.375F);
			DiamondPronter.pront(type.poison, type.flammability, type.reactivity, type.symbol);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}
	
	public String getTextureFromType(FluidType type) {
		
		if(type.customFluid) {
			int color = type.getTint();
			double r = ((color & 0xff0000) >> 16) / 255D;
			double g = ((color & 0x00ff00) >> 8) / 255D;
			double b = ((color & 0x0000ff) >> 0) / 255D;
			GL11.glColor3d(r, g, b);
			return "textures/models/tank/tank_NONE.png";
		}
		
		String s = type.getName();
		
		if(type.isAntimatter() || (type.hasTrait(FT_Corrosive.class) && type.getTrait(FT_Corrosive.class).isHighlyCorrosive()))
			s = "DANGER";
		
		return "textures/models/tank/tank_" + s + ".png";
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_fluidtank);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_CULL_FACE);
				
				FluidTank tank = new FluidTank(Fluids.NONE, 0);
				boolean exploded = false;
				if(item.hasTagCompound() && item.getTagCompound().hasKey(IPersistentNBT.NBT_PERSISTENT_KEY)) {
					tank.readFromNBT(item.getTagCompound().getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY), "tank");
					exploded = item.getTagCompound().getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY).getBoolean("hasExploded");
				}
				
				if(!exploded) {
					bindTexture(ResourceManager.tank_tex); ResourceManager.fluidtank.renderPart("Frame");
					bindTexture(new ResourceLocation(RefStrings.MODID, getTextureFromType(tank.getTankType())));
					ResourceManager.fluidtank.renderPart("Tank");
				} else {
					bindTexture(ResourceManager.tank_tex); ResourceManager.fluidtank_exploded.renderPart("Frame");
					bindTexture(ResourceManager.tank_inner_tex); ResourceManager.fluidtank_exploded.renderPart("TankInner");
					bindTexture(new ResourceLocation(RefStrings.MODID, getTextureFromType(tank.getTankType())));
					ResourceManager.fluidtank_exploded.renderPart("Tank");
				}
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
