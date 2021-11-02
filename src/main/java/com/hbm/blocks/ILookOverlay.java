package com.hbm.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface ILookOverlay {

	@SideOnly(Side.CLIENT)
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z);
}
