package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBatteryPack.EnumBatteryPack;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.HorsePronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.storage.TileEntityBatterySocket;
import com.hbm.util.EnumUtil;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderBatterySocket extends TileEntitySpecialRenderer implements IItemRendererProvider {

	private static ResourceLocation blorbo = new ResourceLocation(RefStrings.MODID, "textures/models/horse/sunburst.png");

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
		
		ItemStack render = socket.syncStack;
		if(render != null) {
			
			if(render.getItem() == ModItems.battery_pack) {
				EnumBatteryPack pack = EnumUtil.grabEnumSafely(EnumBatteryPack.class, render.getItemDamage());
				bindTexture(pack.texture);
				ResourceManager.battery_socket.renderPart(pack.isCapacitor() ? "Capacitor" : "Battery");
			} else if(render.getItem() == ModItems.battery_sc) {
				bindTexture(ResourceManager.battery_sc_tex);
				ResourceManager.battery_socket.renderPart("Battery");
			} else if(render.getItem() == ModItems.battery_creative) {
				GL11.glPushMatrix();
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glRotated((socket.getWorldObj().getTotalWorldTime() % 360 + interp) * 25D, 0, -1, 0);
				this.bindTexture(blorbo);
				HorsePronter.reset();
				HorsePronter.enableHorn();
				HorsePronter.pront();
				GL11.glPopMatrix();
				
				Random rand = new Random(tile.getWorldObj().getTotalWorldTime() / 5);
				rand.nextBoolean();

				for(int i = -1; i <= 1; i += 2) for(int j = -1; j <= 1; j += 2) if(rand.nextInt(4) == 0) {
					GL11.glPushMatrix();
					GL11.glTranslated(0, 0.75, 0);
					BeamPronter.prontBeam(Vec3.createVectorHelper(0.4375 * i, 1.1875, 0.4375 * j), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.0625F, 3, 0.025F);
					BeamPronter.prontBeam(Vec3.createVectorHelper(0.4375 * i, 1.1875, 0.4375 * j), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 1, 0, 3, 0.025F);
					GL11.glPopMatrix();
				}
			}
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
