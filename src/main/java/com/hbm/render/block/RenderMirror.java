package com.hbm.render.block;

import com.hbm.blocks.machine.SolarMirror;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.tileentity.machine.TileEntitySolarMirror;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderMirror implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, world.getBlockMetadata(x, y, z));

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntitySolarMirror))
			return false;
		
		TileEntitySolarMirror mirror = (TileEntitySolarMirror) te;

		int dx = mirror.tX - mirror.xCoord;
		int dy = mirror.tY - mirror.yCoord;
		int dz = mirror.tZ - mirror.zCoord;

		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.solar_mirror, "Base", iicon, tessellator, 0, true);
		
		if(mirror.tY <= mirror.yCoord)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.solar_mirror, "Mirror", iicon, tessellator, 0, true);
		else
			printMirror(iicon, dx, dy, dz);
		
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

		return true;
	}
	
	private void printMirror(IIcon icon, int dx, int dy, int dz) {

		GroupObject go = null;

		for(GroupObject obj : ((WavefrontObject)ResourceManager.solar_mirror).groupObjects) {
			if(obj.name.equals("Mirror"))
				go = obj;
		}

		if(go == null)
			return;
		
		Tessellator tes = Tessellator.instance;

		double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
		double pitch = -Math.asin((dy + 0.5) / dist) + Math.PI / 2D;
		double yaw = -Math.atan2(dz, dx) - Math.PI / 2D;

		for(Face f : go.faces) {

			Vertex n = f.faceNormal;
			
			tes.setNormal(n.x, n.y, n.z);
			float brightness = (n.y + 1) * 0.65F;

			if(brightness < 0.45F)
				brightness = 0.45F;
			
			tes.setColorOpaque_F(brightness, brightness, brightness);

			for(int i = 0; i < f.vertices.length; i++) {

				Vertex v = f.vertices[i];

				Vec3 vec = Vec3.createVectorHelper(v.x, v.y - 1, v.z);
				vec.rotateAroundX((float) pitch);
				vec.rotateAroundY((float) yaw);

				float x = (float) vec.xCoord;
				float y = (float) vec.yCoord + 1;
				float z = (float) vec.zCoord;

				TextureCoordinate t = f.textureCoordinates[i];
				tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16), icon.getInterpolatedV(t.v * 16));
				
				if(i % 3 == 2)
					tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16), icon.getInterpolatedV(t.v * 16));
			}
		}
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return SolarMirror.renderID;
	}
}
