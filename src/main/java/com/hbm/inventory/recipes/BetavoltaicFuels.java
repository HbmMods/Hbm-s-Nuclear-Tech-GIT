package com.hbm.inventory.recipes;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.ICustomWarhead.SaltedFuel.HalfLifeType;
import com.hbm.interfaces.IRadioisotopeFuel;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class BetavoltaicFuels 
{
	public static final HashMap<Item, IRadioisotopeFuel> fuelMap = new HashMap<>();
	
	private static final int thaLife = 48000 * 40;
	private static final byte thaPower = 75;
	private static final int sr90Life = 48000 * 100 * 10;
	private static final byte sr90Power = 50;
	private static final byte sa327Power = 30;
	private static boolean isInitialized = false;
	public static void initialize()
	{
		isInitialized = true;
		fuelMap.put(ModItems.ingot_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.ingot_thorium_fuel);}
		});
		fuelMap.put(ModItems.nugget_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower / 10;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.nugget_thorium_fuel);}
		});
		fuelMap.put(ModItems.billet_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower;}// Intentional, my reasoning is that billets have greater surface area, therefore a larger amount of beta particles being emitted from that area. Either that or I'm going crazy
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.billet_thorium_fuel);}
		});
		fuelMap.put(Item.getItemFromBlock(ModBlocks.block_tha), new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower * 10;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModBlocks.block_thorium_fuel);}
		});
		fuelMap.put(ModItems.rod_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.rod_thorium_fuel);}
		});
		fuelMap.put(ModItems.rod_dual_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower * 2;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.rod_dual_thorium_fuel);}
		});
		fuelMap.put(ModItems.rod_quad_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower * 4;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.rod_quad_thorium_fuel);}
		});
		fuelMap.put(ModItems.ingot_sr90, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sr90Power;}
			@Override public long getMaxLifespan(){return sr90Life;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.powder_zirconium);}
		});
		fuelMap.put(ModItems.powder_sr90, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sr90Power;}
			@Override public long getMaxLifespan(){return sr90Life;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.powder_zirconium);}
		});
		fuelMap.put(ModItems.powder_sr90_tiny, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sr90Power / 10;}
			@Override public long getMaxLifespan(){return sr90Life;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.nugget_zirconium);}
		});
		fuelMap.put(ModItems.cell_tritium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return 1;}
			@Override public long getMaxLifespan(){return 48000 * 100 * 5;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.cell_empty);}
		});
		fuelMap.put(ModItems.ingot_solinium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sa327Power;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}
		});
		fuelMap.put(ModItems.billet_solinium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sa327Power;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}

		});
		fuelMap.put(ModItems.nugget_solinium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sa327Power / 10;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}

		});
		fuelMap.put(ModItems.ingot_technetium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return 10;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}
		});
		fuelMap.put(ModItems.billet_technetium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return 10;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}
		});
		fuelMap.put(ModItems.nugget_technetium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return 1;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}
		});
		fuelMap.put(ModItems.ingot_bk249, new IRadioisotopeFuel() {
			
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return 15;}
			@Override public long getMaxLifespan(){return IRadioisotopeFuel.getLifespan(330, HalfLifeType.SHORT, true) * 2;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.ingot_cf249);}
		});
		fuelMap.put(ModItems.powder_cs137, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sr90Power;}
			@Override public long getMaxLifespan(){return IRadioisotopeFuel.getLifespan(30, HalfLifeType.MEDIUM, true) * 2;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.powder_zirconium);}
		});
		fuelMap.put(ModItems.powder_cs137_tiny, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sr90Power / 10;}
			@Override public long getMaxLifespan(){return IRadioisotopeFuel.getLifespan(30, HalfLifeType.MEDIUM, true) * 2;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.nugget_zirconium);}
		});
	}
	public static boolean isInitialized()
	{
		return isInitialized;
	}
	public static ImmutableList<Item> getFuels()
	{
		return ImmutableList.copyOf(fuelMap.keySet());
	}
}
