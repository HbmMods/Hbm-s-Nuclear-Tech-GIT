package com.hbm.blocks.machine;

import com.hbm.blocks.BlockEnums.LightType;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class SpotlightModular extends Spotlight {
    
    public SpotlightModular(Material mat, int beamLength, LightType type, boolean isOn) {
		super(mat, beamLength, type, isOn);
	}

    @Override
    public String getPartName(int connectionCount) {
        if (connectionCount == 0) return "FluoroSingle";
        if (connectionCount == 1) return "FluoroCap";
        return "FluoroMid";
    }

    public boolean canConnectTo(IBlockAccess world, int x, int y, int z) {
        return world.getBlock(x, y, z) == this;
    }

}
