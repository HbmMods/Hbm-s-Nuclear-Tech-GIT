package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.MachineCapacitor;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderCapacitor implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);
		
		MachineCapacitor capacitor = (MachineCapacitor) block;
		
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "Top", capacitor.iconTop, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "Side", capacitor.iconSide, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "Bottom", capacitor.iconBottom, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "InnerTop", capacitor.iconInnerTop, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "InnerSide", capacitor.iconInnerSide, tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		MachineCapacitor capacitor = (MachineCapacitor) block;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		float flip = 0;
		float rotation = 0;
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 0)
			flip = (float)Math.PI;

		if(meta == 2)
			rotation = 90F / 180F * (float) Math.PI;

		if(meta == 3)
			rotation = 270F / 180F * (float) Math.PI;

		if(meta == 4)
			rotation = 180F / 180F * (float)Math.PI;
		
		if(rotation != 0F || meta == 5)
			flip = (float)Math.PI * 0.5F;

		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);

		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "Top", capacitor.iconTop, tessellator, rotation, flip, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "Side", capacitor.iconSide, tessellator, rotation, flip, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "Bottom", capacitor.iconBottom, tessellator, rotation, flip, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "InnerTop", capacitor.iconInnerTop, tessellator, rotation, flip, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.capacitor, "InnerSide", capacitor.iconInnerSide, tessellator, rotation, flip, true);
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return MachineCapacitor.renderID;
	}
}
