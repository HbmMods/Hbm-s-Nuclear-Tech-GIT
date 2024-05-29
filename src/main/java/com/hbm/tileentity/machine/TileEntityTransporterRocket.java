package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerTransporterRocket;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GUITransporterRocket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityTransporterRocket extends TileEntityTransporterBase {

    public TileEntityTransporterRocket() {
        super(16, 8, 64_000, 0, 2, 64_000);

		tanks[8].setTankType(Fluids.HYDROGEN);
		tanks[9].setTankType(Fluids.OXYGEN);
    }

    @Override
    public Class<? extends TileEntityTransporterBase> getCompatible() {
        // Rocket transporters are only compatible with each other
        return TileEntityTransporterRocket.class;
    }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTransporterRocket(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITransporterRocket(player.inventory, this);
	}
    
}
