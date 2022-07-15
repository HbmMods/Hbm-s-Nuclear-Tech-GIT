package com.hbm.handler;

import static com.hbm.main.MainRegistry.logger;

import java.lang.reflect.InvocationTargetException;

import com.hbm.calc.EasyLocation;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final EasyLocation location = new EasyLocation(x, y, z).setWorld(world);
		final TileEntity entity = location.getTE();
		final EnumGUI gui = EnumUtil.grabEnumSafely(EnumGUI.class, ID);
		if (gui.hasStandardBehavior() && gui.getContainer().isPresent())
		{
			try
			{
				if (entity == null)
				{
					logger.error("Unable to open Container for device: " + gui + " because tile entity at location is null.");
					return null;
				}
				else if (gui.getTE().isAssignableFrom(entity.getClass()))
					return gui.getContainer().get().getConstructor(InventoryPlayer.class, gui.getTE()).newInstance(player.inventory, gui.getTE().cast(entity));
				else
				{
					logger.error("Unable to open Container for device: " + gui + " because tile entity class (" + entity.getClass().getCanonicalName() + ") doesn't match GUI.");
					return null;
				}
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
				logger.error("Unable to open Container for device: " + gui + "! This should not be possible!");
				logger.catching(e);
			}
		}
		else if (gui.getCustomBehavior().isPresent() && !gui.hasStandardBehavior())
			return gui.getCustomBehavior().get().getContainer(entity, player, location);

		logger.warn("getServerGuiElement() executed without opening a container for the device: " + gui.toString() + "; is this ok?");
		return null;
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final EasyLocation location = new EasyLocation(x, y, z).setWorld(world);
		final TileEntity entity = location.getTE();
		final EnumGUI gui = EnumUtil.grabEnumSafely(EnumGUI.class, ID);
		if (gui.hasStandardBehavior())
		{
			try
			{
				if (entity == null)
				{
					logger.error("Unable to open GUI for device: " + gui + " because tile entity at location is null.");
					return null;
				}
				else if (gui.getTE().isAssignableFrom(entity.getClass()))
					return gui.getGui().getConstructor(InventoryPlayer.class, gui.getTE()).newInstance(player.inventory, gui.getTE().cast(entity));
				else
				{
					logger.error("Unable to open GUI for device: " + gui + " because tile entity class (" + entity.getClass().getCanonicalName() + ") doesn't match GUI.");
					return null;
				}
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
				logger.error("Unable to open GUI for device: " + gui + "! This should not be possible!");
				logger.catching(e);
			}
		}
		else if (gui.getCustomBehavior().isPresent())
			return gui.getCustomBehavior().get().getGUI(entity, player, location);
		
		logger.warn("getClientGuiElement() executed without opening a GUI for the device: " + gui + "; is this ok?");
		return null;
	}
}
