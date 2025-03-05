package com.hbm.inventory.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;

import com.hbm.config.GeneralConfig;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.*;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.EnumSymbol;
import com.hbm.uninos.INetworkProvider;
import com.hbm.util.I18nUtil;

import api.hbm.fluidmk2.FluidNetMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
	//Unlocalized string ID of the fluid
	private String unlocalized;
	//localization override for custom fluids
	private String localizedOverride;
	private int guiTint = 0xffffff;
	
	public int poison;
	public int flammability;
	public int reactivity;
	public EnumSymbol symbol;
	public boolean customFluid = false;
	
	public static final int ROOM_TEMPERATURE = 20;
	
	// v v v this entire system is a pain in the ass to work with. i'd much rather define state transitions and heat values manually.
	/** How hot this fluid is. Simple enough. */
	public int temperature = ROOM_TEMPERATURE;
	
	public HashMap<Class, Object> containers = new HashMap();
	public HashMap<Class<? extends FluidTrait>, FluidTrait> traits = new HashMap();
	//public List<EnumFluidTrait> enumTraits = new ArrayList();
	
	private ResourceLocation texture;
	
	public FluidType(String name, int color, int p, int f, int r, EnumSymbol symbol) {
		this.stringId = name;
		this.color = color;
		this.unlocalized = "hbmfluid." + name.toLowerCase(Locale.US);
		this.poison = p;
		this.flammability = f;
		this.reactivity = r;
		this.symbol = symbol;
		this.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/fluids/" + name.toLowerCase(Locale.US) + ".png");
		
		this.id = Fluids.registerSelf(this);
	}
	
	public FluidType(String name, int color, int p, int f, int r, EnumSymbol symbol, String texName, int tint, int id, String displayName) {
		this.stringId = name;
		this.color = color;
		this.unlocalized = "hbmfluid." + name.toLowerCase(Locale.US);
		this.poison = p;
		this.flammability = f;
		this.reactivity = r;
		this.symbol = symbol;
		this.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/fluids/" + texName + ".png");
		this.guiTint = tint;
		this.localizedOverride = displayName;
		this.customFluid = true;

		this.id = id;
		Fluids.register(this, id);
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
	
	public FluidType addContainers(Object... containers) {
		for(Object container : containers) this.containers.put(container.getClass(), container);
		return this;
	}
	
	public <T> T getContainer(Class<? extends T> container) {
		return (T) this.containers.get(container);
	}
	
	public FluidType addTraits(FluidTrait... traits) {
		for(FluidTrait trait : traits) this.traits.put(trait.getClass(), trait);
		return this;
	}
	
	public boolean hasTrait(Class<? extends FluidTrait> trait) {
		return this.traits.containsKey(trait);
	}
	
	public <T extends FluidTrait> T getTrait(Class<? extends T> trait) { //generics, yeah!
		return (T) this.traits.get(trait);
	}
	
	public int getID() {
		return this.id;
	}
	/** The unique mapping name for this fluid, usually matches the unlocalied name, minus the prefix */
	public String getName() {
		return this.stringId;
	}

	public int getColor() {
		return this.color;
	}

	public int getTint() {
		return this.guiTint;
	}

	public ResourceLocation getTexture() {
		return this.texture;
	}
	public String getUnlocalizedName() {
		return this.unlocalized;
	}
	/** Returns the localized override name if present, or otherwise the I18n converted name */
	@SideOnly(Side.CLIENT) public String getLocalizedName() {
		return this.localizedOverride != null ? this.localizedOverride : I18nUtil.resolveKey(this.unlocalized);
	}
	/** Returns the localized override name if present, or otherwise the raw unlocalized name. Used for server-side code that needs ChatComponentTranslation. */
	public String getConditionalName() {
		return this.localizedOverride != null ? this.localizedOverride : this.unlocalized;
	}
	public String getDict(int quantity) {
		String prefix = GeneralConfig.enableFluidContainerCompat ? "container" : "ntmcontainer";
		return prefix + quantity + this.stringId.replace("_", "").toLowerCase(Locale.US);
	}
	
	public boolean isHot() {
		return this.temperature >= 100;
	}
	public boolean isCorrosive() {
		return this.traits.containsKey(FT_Corrosive.class);
	}
	public boolean isAntimatter() {
		return this.traits.containsKey(FT_Amat.class);
	}
	public boolean hasNoContainer() {
		return this.traits.containsKey(FT_NoContainer.class);
	}
	public boolean hasNoID() {
		return this.traits.containsKey(FT_NoID.class);
	}
	public boolean needsLeadContainer() {
		return this.traits.containsKey(FT_LeadContainer.class);
	}
	public boolean isDispersable() {
		return !(this.traits.containsKey(FT_Amat.class) || this.traits.containsKey(FT_NoContainer.class) || this.traits.containsKey(FT_Viscous.class));
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
	
	@SideOnly(Side.CLIENT)
	public void addInfo(List<String> info) {

		if(temperature != ROOM_TEMPERATURE) {
			if(temperature < 0) info.add(EnumChatFormatting.BLUE + "" + temperature + "°C");
			if(temperature > 0) info.add(EnumChatFormatting.RED + "" + temperature + "°C");
		}
		
		boolean shiftHeld = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		
		List<String> hidden = new ArrayList();
		
		for(Class<? extends FluidTrait> clazz : FluidTrait.traitList) {
			FluidTrait trait = this.getTrait(clazz);
			if(trait != null) {
				trait.addInfo(info);
				if(shiftHeld) trait.addInfoHidden(info);
				trait.addInfoHidden(hidden);
			}
		}
		
		if(!hidden.isEmpty() && !shiftHeld) {
			info.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
					EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
					EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display more info");
		}
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
	
	public INetworkProvider<FluidNetMK2> getNetworkProvider() {
		return null; //TBI
	}
}
