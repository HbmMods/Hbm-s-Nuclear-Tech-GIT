package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.machine.ItemBatteryPack.EnumBatteryPack;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.storage.TileEntityBatterySocket;
import com.hbm.util.EnumUtil;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderBatterySocket extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-0.5, 0, 0.5);
		
		TileEntityBatterySocket socket = (TileEntityBatterySocket) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.battery_socket_tex);
		ResourceManager.battery_socket.renderPart("Socket");
		
		if(socket.renderPack >= 0) {
			EnumBatteryPack pack = EnumUtil.grabEnumSafely(EnumBatteryPack.class, socket.renderPack);
			bindTexture(pack.texture);
			ResourceManager.battery_socket.renderPart(pack.isCapacitor() ? "Capacitor" : "Battery");
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_battery_socket);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.battery_socket_tex);
				ResourceManager.battery_socket.renderPart("Socket");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
