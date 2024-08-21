package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class RenderTurretBase extends TileEntitySpecialRenderer {
	
	protected void renderConnectors(TileEntityTurretBaseNT turret, boolean power, boolean fluid, FluidType type) {

		bindTexture(ResourceManager.turret_connector_tex);
		Vec3 pos = turret.getHorizontalOffset();
		int x = (int)(turret.xCoord + pos.xCoord);
		int y = turret.yCoord;
		int z = (int)(turret.zCoord + pos.zCoord);

		checkPlug(turret.getWorldObj(), x - 2, y, z, power, fluid, type, 0, 0, 0, Library.NEG_X);
		checkPlug(turret.getWorldObj(), x - 2, y, z - 1, power, fluid, type, 0, -1, 0, Library.NEG_X);
		
		checkPlug(turret.getWorldObj(), x - 1, y, z + 1, power, fluid, type, 0, -1, 90, Library.POS_Z);
		checkPlug(turret.getWorldObj(), x, y, z + 1, power, fluid, type, 0, 0, 90, Library.POS_Z);

		checkPlug(turret.getWorldObj(), x + 1, y, z, power, fluid, type, 0, -1, 180, Library.POS_X);
		checkPlug(turret.getWorldObj(), x + 1, y, z - 1, power, fluid, type, 0, 0, 180, Library.POS_X);

		checkPlug(turret.getWorldObj(), x, y, z - 2, power, fluid, type, 0, -1, 270, Library.NEG_Z);
		checkPlug(turret.getWorldObj(), x - 1, y, z - 2, power, fluid, type, 0, 0, 270, Library.NEG_Z);
	}
	
	private void checkPlug(World world, int x, int y, int z, boolean power, boolean fluid, FluidType type, int ox, int oz, int rot, ForgeDirection dir) {
		
		if((power && Library.canConnect(world, x, y, z, dir)) || (fluid && Library.canConnectFluid(world, x, y, z, dir, type))) {
			
			GL11.glPushMatrix();
			GL11.glRotated(rot, 0, 1, 0);
			GL11.glTranslated(ox, 0, oz);
			ResourceManager.turret_chekhov.renderPart("Connectors");
			GL11.glPopMatrix();
		}
	}

}
