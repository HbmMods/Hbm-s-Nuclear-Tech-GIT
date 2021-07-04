package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class RenderTurretBase extends TileEntitySpecialRenderer {
	
	protected void renderConnectors(TileEntityTurretBaseNT turret, boolean power, boolean fluid, FluidType type) {

		bindTexture(ResourceManager.turret_connector_tex);
		Vec3 pos = turret.getHorizontalOffset();
		int x = (int)(turret.xCoord + pos.xCoord);
		int y = turret.yCoord;
		int z = (int)(turret.zCoord + pos.zCoord);

		checkPlug(turret.getWorldObj(), x - 2, y, z, power, fluid, type, 0, 0, 0);
		checkPlug(turret.getWorldObj(), x - 2, y, z - 1, power, fluid, type, 0, -1, 0);
		
		checkPlug(turret.getWorldObj(), x - 1, y, z + 1, power, fluid, type, 0, -1, 90);
		checkPlug(turret.getWorldObj(), x, y, z + 1, power, fluid, type, 0, 0, 90);

		checkPlug(turret.getWorldObj(), x + 1, y, z, power, fluid, type, 0, -1, 180);
		checkPlug(turret.getWorldObj(), x + 1, y, z - 1, power, fluid, type, 0, 0, 180);

		checkPlug(turret.getWorldObj(), x, y, z - 2, power, fluid, type, 0, -1, 270);
		checkPlug(turret.getWorldObj(), x - 1, y, z - 2, power, fluid, type, 0, 0, 270);
	}
	
	private void checkPlug(World world, int x, int y, int z, boolean power, boolean fluid, FluidType type, int ox, int oz, int rot) {
		
		if( (power && Library.checkCableConnectables(world, x, y, z)) ||
			(fluid && Library.checkFluidConnectables(world, x, y, z, type)) ) {
			
			GL11.glPushMatrix();
			GL11.glRotated(rot, 0, 1, 0);
			GL11.glTranslated(ox, 0, oz);
			ResourceManager.turret_chekhov.renderPart("Connectors");
			GL11.glPopMatrix();
		}
	}

}
