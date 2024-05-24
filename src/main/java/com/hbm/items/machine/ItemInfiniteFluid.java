package com.hbm.items.machine;

import java.util.List;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemInfiniteFluid extends Item {

	private FluidType type;
	private int amount;
	private int chance;
	private double minimumAtmosphere;
	
	public ItemInfiniteFluid(FluidType type, int amount) {
		this(type, amount, 1, 0);
	}

	public ItemInfiniteFluid(FluidType type, int amount, int chance) {
		this(type, amount, chance, 0);
	}

	public ItemInfiniteFluid(FluidType type, int amount, double requiresAtmosphere) {
		this(type, amount, 1, requiresAtmosphere);
	}
	
	public ItemInfiniteFluid(FluidType type, int amount, int chance, double requiresAtmosphere) {
		this.type = type;
		this.amount = amount;
		this.chance = chance;
		this.minimumAtmosphere = requiresAtmosphere;
	}

	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean inHand) {
		if(minimumAtmosphere <= 0) return;

		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		
		// Check that the world we're currently in has a strong enough atmosphere to enable this barrel to function
		// Below the limit, the fluid immediately boils away, forcing the player to find alternative sources
		CBT_Atmosphere atmosphere = CelestialBody.getTrait(world, CBT_Atmosphere.class);

		if(atmosphere == null) {
			stack.stackTagCompound.setBoolean("noAtmo", true);
			return;
		}

		stack.stackTagCompound.setBoolean("noAtmo", atmosphere.getPressure() < minimumAtmosphere);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(canOperate(stack))
			return;

		list.add("Atmosphere too thin to operate.");
		list.add("Requires: " + minimumAtmosphere + "atm");
	}

	public FluidType getType() { return this.type; }
	public int getAmount() { return this.amount; }
	public int getChance() { return this.chance; }
	public boolean allowPressure(int pressure) { return this == ModItems.fluid_barrel_infinite || pressure == 0; }
	public boolean canOperate(ItemStack stack) { return stack.stackTagCompound == null || !stack.stackTagCompound.getBoolean("noAtmo"); }
}
