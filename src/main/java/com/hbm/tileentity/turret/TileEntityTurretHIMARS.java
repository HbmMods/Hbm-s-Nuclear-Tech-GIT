package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretHIMARS;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityTurretHIMARS extends TileEntityTurretBaseNT implements IGUIProvider {

	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {
		
		if(ammoStacks != null)
			return ammoStacks;
		
		ammoStacks = new ArrayList();

		List list = new ArrayList();
		ModItems.ammo_arty.getSubItems(ModItems.ammo_arty, MainRegistry.weaponTab, list);
		this.ammoStacks.addAll(list);
		
		return ammoStacks;
	}

	@Override
	protected List<Integer> getAmmoList() {
		return new ArrayList();
	}

	@Override
	public String getName() {
		return "container.turretHIMARS";
	}

	@Override
	public long getMaxPower() {
		return 1_000_000;
	}

	@Override
	public void updateFiringTick() {
		
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretHIMARS(player.inventory, this);
	}
}
