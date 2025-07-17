package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineAssemblyMachine;
import com.hbm.util.BobMathUtil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderAssemblyMachine extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public static EntityItem dummy;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineAssemblyMachine assembler = (TileEntityMachineAssemblyMachine) tileEntity;
		
		bindTexture(ResourceManager.assembly_machine_tex);
		ResourceManager.assembly_machine.renderPart("Base");
		if(assembler.frame) ResourceManager.assembly_machine.renderPart("Frame");
		
		GL11.glPushMatrix();

		double spin = BobMathUtil.interp(assembler.prevRing, assembler.ring, interp);
		double[] arm1 = assembler.arms[0].getPositions(interp);
		double[] arm2 = assembler.arms[1].getPositions(interp);
		
		// arm1 = arm2 = new double[] {60, -15, 15, -0.25}; // heart
		
		GL11.glRotated(spin, 0, 1, 0);
		ResourceManager.assembly_machine.renderPart("Ring");

		GL11.glPushMatrix(); {
			GL11.glTranslated(0, 1.625, 0.9375);
			GL11.glRotated(arm1[0], 1, 0, 0);
			GL11.glTranslated(0, -1.625, -0.9375);
			ResourceManager.assembly_machine.renderPart("ArmLower1");
	
			GL11.glTranslated(0, 2.375, 0.9375);
			GL11.glRotated(arm1[1], 1, 0, 0);
			GL11.glTranslated(0, -2.375, -0.9375);
			ResourceManager.assembly_machine.renderPart("ArmUpper1");
	
			GL11.glTranslated(0, 2.375, 0.4375);
			GL11.glRotated(arm1[2], 1, 0, 0);
			GL11.glTranslated(0, -2.375, -0.4375);
			ResourceManager.assembly_machine.renderPart("Head1");
			GL11.glTranslated(0, arm1[3], 0);
			ResourceManager.assembly_machine.renderPart("Spike1");
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			GL11.glTranslated(0, 1.625, -0.9375);
			GL11.glRotated(-arm2[0], 1, 0, 0);
			GL11.glTranslated(0, -1.625, 0.9375);
			ResourceManager.assembly_machine.renderPart("ArmLower2");
	
			GL11.glTranslated(0, 2.375, -0.9375);
			GL11.glRotated(-arm2[1], 1, 0, 0);
			GL11.glTranslated(0, -2.375, 0.9375);
			ResourceManager.assembly_machine.renderPart("ArmUpper2");
	
			GL11.glTranslated(0, 2.375, -0.4375);
			GL11.glRotated(-arm2[2], 1, 0, 0);
			GL11.glTranslated(0, -2.375, 0.4375);
			ResourceManager.assembly_machine.renderPart("Head2");
			GL11.glTranslated(0, arm2[3], 0);
			ResourceManager.assembly_machine.renderPart("Spike2");
		} GL11.glPopMatrix();
		
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GenericRecipe recipe = AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(assembler.assemblerModule.recipe);
		if(recipe != null && MainRegistry.proxy.me().getDistanceSq(tileEntity.xCoord + 0.5, tileEntity.yCoord + 1, tileEntity.zCoord + 0.5) < 35 * 35) {

			GL11.glRotated(90, 0, 1, 0);
			GL11.glTranslated(0, 1.0625, 0);
			
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			ItemStack stack = recipe.getIcon();

			if(!(stack.getItemSpriteNumber() == 0 && stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType()))) {
				GL11.glRotated(-90, 1, 0, 0);
				GL11.glTranslated(0, -0.25, 0);
			}
			
			GL11.glScaled(1.25, 1.25, 1.25);

			if(dummy == null || dummy.worldObj != tileEntity.getWorldObj()) dummy = new EntityItem(tileEntity.getWorldObj(), 0, 0, 0, stack);
			dummy.setEntityItemStack(stack);
			dummy.hoverStart = 0.0F;

			RenderItem.renderInFrame = true;
			RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_assembly_machine);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -2.75, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.assembly_machine_tex);
				ResourceManager.assembly_machine.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
