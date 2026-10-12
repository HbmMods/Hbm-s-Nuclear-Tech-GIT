package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.network.FlowControlPump.TileEntityFlowControlPump;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFlowControlPump extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityFlowControlPump pump = (TileEntityFlowControlPump) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.flow_control_pump_tex);
		ResourceManager.flow_control_pump.renderPart("Pump");
		if(pump.topRail) ResourceManager.flow_control_pump.renderPart("RailTop");
		if(pump.bottomRail) ResourceManager.flow_control_pump.renderPart("RailBottom");
		
		float rotor = pump.prevRotor + (pump.rotor - pump.prevRotor) * interp;

		GL11.glTranslated(0, 0.5, 0);
		GL11.glRotatef(rotor, 0, 0, -1);
		GL11.glTranslated(0, -0.5, 0);
		ResourceManager.flow_control_pump.renderPart("Rotor");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.flow_control_pump);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.flow_control_pump_tex);
				ResourceManager.flow_control_pump.renderPart("Pump");
				ResourceManager.flow_control_pump.renderPart("Rotor");
				ResourceManager.flow_control_pump.renderPart("RailBottom");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
