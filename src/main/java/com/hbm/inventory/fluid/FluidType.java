package com.hbm.inventory.fluid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.inventory.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.EnumSymbol;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FluidType {

	//The numeric ID of the fluid
	private int id;
	//The internal name
	private String stringId;
	//Approximate HEX Color of the fluid, used for pipe rendering
	private int color;
	//The color for containers, not the liquid itself. Used for canisters.
	private int containerColor = 0xffffff;
	//Unlocalized string ID of the fluid
	private String unlocalized;
	
	public int poison;
	public int flammability;
	public int reactivity;
	public EnumSymbol symbol;
	
	public static final int ROOM_TEMPERATURE = 20;
	public static final double DEFAULT_HEATCAP = 0.01D;
	public static final double DEFAULT_COMPRESSION = 1D;
	
	/** How hot this fluid is. Simple enough. */
	public int temperature = ROOM_TEMPERATURE;
	/** How much heat energy each mB requires to be heated by 1°C. Total heat energy = heatCap * delta-T. */
	public double heatCap = DEFAULT_HEATCAP;
	/** How much "stuff" there is in one mB. 1mB of water turns into 100mB of steam, therefore steam has a compression of 0.01. Compression is only used for translating fluids into other fluids, heat calculations should ignore this. */
	public double compression = DEFAULT_COMPRESSION;
	
	public Set<ExtContainer> containers = new HashSet();
	public List<FluidTrait> traits = new ArrayList();
	
	private ResourceLocation texture;
	
	public FluidType(String name, int color, int p, int f, int r, EnumSymbol symbol) {
		this.stringId = name;
		this.color = color;
		this.unlocalized = "hbmfluid." + name.toLowerCase();
		this.poison = p;
		this.flammability = f;
		this.reactivity = r;
		this.symbol = symbol;
		this.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/fluids/" + name.toLowerCase() + ".png");
		
		this.id = Fluids.registerSelf(this);
	}
	
	public FluidType(int forcedId, String name, int color, int p, int f, int r, EnumSymbol symbol) {
		this(name, color, p, f, r, symbol);
		
		if(this.id != forcedId) {
			throw new IllegalStateException("Howdy! I am a safeguard put into place by Bob to protect you, the player, from Bob's dementia. For whatever reason, Bob decided to either add or remove a fluid in a way that shifts the IDs, despite the entire system being built to prevent just that. Instead of people's fluids getting jumbled for the 500th time, I am here to prevent the game from starting entirely. The expected ID was " + forcedId + ", but turned out to be " + this.id + ".");
		}
	}
	
	public FluidType setTemp(int temperature) {
		this.temperature = temperature;
		return this;
	}
	
	public FluidType setHeatCap(double heatCap) {
		this.heatCap = heatCap;
		return this;
	}
	
	public FluidType setCompression(double compression) {
		this.compression = compression;
		return this;
	}
	
	public FluidType addContainers(int color, ExtContainer... containers) {
		this.containerColor = color;
		Collections.addAll(this.containers, containers);
		return this;
	}
	
	public FluidType addTraits(FluidTrait... traits) {
		Collections.addAll(this.traits, traits);
		return this;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.stringId;
	}

	public int getColor() {
		return this.color;
	}

	public int getContainerColor() {
		return this.containerColor;
	}
	public ResourceLocation getTexture() {
		return this.texture;
	}
	public String getUnlocalizedName() {
		return this.unlocalized;
	}
	public String getDict(int quantity) {
		return "container" + quantity + this.stringId.replace("_", "").toLowerCase();
	}
	
	public boolean isHot() {
		return this.temperature >= 100;
	}
	public boolean isCorrosive() {
		return this.traits.contains(FluidTrait.CORROSIVE) || this.traits.contains(FluidTrait.CORROSIVE_2);
	}
	public boolean isAntimatter() {
		return this.traits.contains(FluidTrait.AMAT);
	}
	public boolean hasNoContainer() {
		return this.traits.contains(FluidTrait.NO_CONTAINER);
	}
	public boolean hasNoID() {
		return this.traits.contains(FluidTrait.NO_ID);
	}
	public boolean needsLeadContainer() {
		return this.traits.contains(FluidTrait.LEAD_CONTAINER);
	}

	/**
	 * Called when the tile entity is broken, effectively voiding the fluids.
	 * @param te
	 * @param tank
	 */
	public void onTankBroken(TileEntity te, FluidTank tank) { }
	/**
	 * Called by the tile entity's update loop. Also has an arg for the fluid tank for possible tanks using child-classes that are shielded or treated differently.
	 * @param te
	 * @param tank
	 */
	public void onTankUpdate(TileEntity te, FluidTank tank) { }
	/**
	 * For when the tile entity is releasing this fluid into the world, either by an overflow or (by proxy) when broken.
	 * @param te
	 * @param tank
	 * @param overflowAmount
	 */
	public void onFluidRelease(TileEntity te, FluidTank tank, int overflowAmount) {
		this.onFluidRelease(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, tank, overflowAmount);
	}
	
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) { }
	//public void onFluidTransmit(FluidNetwork net) { }
	
	public void addInfo(List<String> info) {

		if(temperature != ROOM_TEMPERATURE) {
			if(temperature < 0) info.add(EnumChatFormatting.BLUE + "" + temperature + "°C");
			if(temperature > 0) info.add(EnumChatFormatting.RED + "" + temperature + "°C");
		}
		if(isAntimatter()) info.add(EnumChatFormatting.DARK_RED + "Antimatter");

		if(traits.contains(FluidTrait.CORROSIVE_2)) info.add(EnumChatFormatting.GOLD + "Strongly Corrosive");
		else if(traits.contains(FluidTrait.CORROSIVE)) info.add(EnumChatFormatting.YELLOW + "Corrosive");
		
		if(traits.contains(FluidTrait.NO_CONTAINER)) info.add(EnumChatFormatting.RED + "Cannot be stored in any universal tank");
		if(traits.contains(FluidTrait.LEAD_CONTAINER)) info.add(EnumChatFormatting.YELLOW + "Requires hazardous material tank to hold");

		info.add("");
		info.add(EnumChatFormatting.RED + "[DEBUG]");
		
		for(FluidTrait trait : traits) {
			info.add(EnumChatFormatting.RED + "-" + trait.name());
		}
	}
	
	/**
	 * Metadata for describing how the fluid acts, like being corrosive, not having fluid IDs or being only stored in certain containers.
	 */
	public static enum FluidTrait {
		LIQUID,
		GASEOUS,
		PETROCHEMICAL,
		AMAT,
		CORROSIVE,
		CORROSIVE_2,
		NO_CONTAINER,
		LEAD_CONTAINER,
		NO_ID;
	}
	
	public static enum ExtContainer {
		CANISTER
	}
	
	//shitty wrapper delegates, go!
	//only used for compatibility purposes, these will be removed soon
	//don't use these, dumbfuck
	@Deprecated //reason: not an enum, asshole, use the registry
	public static FluidType getEnum(int i) {
		return Fluids.fromID(i);
	}
	@Deprecated //reason: the more time you waste reading this the less time is there for you to use that fucking registry already
	public static FluidType getEnumFromName(String s) {
		return Fluids.fromName(s);
	}
	@Deprecated //reason: not an enum, again, fuck you
	public int ordinal() {
		return this.getID();
	}
	@Deprecated
	public int getMSAColor() {
		return this.color;
	}
	@Deprecated
	public String name() {
		return this.stringId;
	}
}
