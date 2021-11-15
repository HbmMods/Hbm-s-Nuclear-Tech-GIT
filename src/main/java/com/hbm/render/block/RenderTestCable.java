package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.test.TestConductor;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyConnectorBlock;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderTestCable implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}
		
		GL11.glRotated(180, 0, 1, 0);
		GL11.glScaled(1.25D, 1.25D, 1.25D);
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "Core", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posX", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negX", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posZ", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negZ", iicon, tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		boolean pX = canConnect(world, x + 1, y, z, ForgeDirection.EAST);
		boolean nX = canConnect(world, x - 1, y, z, ForgeDirection.WEST);
		boolean pY = canConnect(world, x, y + 1, z, ForgeDirection.UP);
		boolean nY = canConnect(world, x, y - 1, z, ForgeDirection.DOWN);
		boolean pZ = canConnect(world, x, y, z + 1, ForgeDirection.SOUTH);
		boolean nZ = canConnect(world, x, y, z - 1, ForgeDirection.NORTH);
		
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);

		if(pX && nX && !pY && !nY && !pZ && !nZ)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "CX", iicon, tessellator, 0, true);
		else if(!pX && !nX && pY && nY && !pZ && !nZ)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "CY", iicon, tessellator, 0, true);
		else if(!pX && !nX && !pY && !nY && pZ && nZ)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "CZ", iicon, tessellator, 0, true);
		
		else {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "Core", iicon, tessellator, 0, true);
			if(pX) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posX", iicon, tessellator, 0, true);
			if(nX) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negX", iicon, tessellator, 0, true);
			if(pY) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posY", iicon, tessellator, 0, true);
			if(nY) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negY", iicon, tessellator, 0, true);
			if(nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posZ", iicon, tessellator, 0, true);
			if(pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negZ", iicon, tessellator, 0, true);
		}
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}
	
	private boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		
		if(y > 255 || y < 0)
			return false;
		
		Block b = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(b instanceof IEnergyConnectorBlock) {
			IEnergyConnectorBlock con = (IEnergyConnectorBlock) b;
			
			if(con.canConnect(world, x, y, z, dir))
				return true;
		}
		
		if(te instanceof IEnergyConnectorBlock) {
			IEnergyConnector con = (IEnergyConnector) te;
			
			if(con.canConnect(dir))
				return true;
		}
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return TestConductor.renderID;
	}
}
