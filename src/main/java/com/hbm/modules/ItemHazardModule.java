package com.hbm.modules;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import com.google.common.annotations.Beta;
import com.hbm.config.RadiationConfig;
import com.hbm.inventory.BreederRecipes;
import com.hbm.lib.HbmCollection;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemHazardModule {
	
	static final DecimalFormat basicFormatter = new DecimalFormat("0.###");
	static final DecimalFormat sciNotFormatter = new DecimalFormat("0.###E0");
	static
	{
		basicFormatter.setGroupingUsed(true);
		basicFormatter.setGroupingSize(3);
	}
	
	/**
	 * Dependency injection: It's fun for boys and girls!
	 * All this interface-pattern-wish-wash only exists for three reasons:
	 * -it lets me add item hazards with ease by using self-returning setters
	 * -it's agnositc and also works with ItemBlocks or whatever implementation I want it to work
	 * -it makes the system truly centralized and I don't have to add new cases to 5 different classes when adding a new hazard
	 */

	float radiation;
	float digamma;
	int fire;
	boolean blinding;
	boolean asbestos;
	boolean hydro;
	float explosive;
	
	// Custom toxic damage
	boolean hasCustomTox = false;
	float toxMod = 0;
//	CustomToxicity tox = null;
	Optional<CustomToxicity> tox = Optional.empty();
	
	public void addRadiation(float radiation) {
		this.radiation = radiation;
	}
	
	public void addDigamma(float digamma) {
		this.digamma = digamma;
	}
	
	public void addFire(int fire) {
		this.fire = fire;
	}
	
	public void addAsbestos() {
		this.asbestos = true;
	}
	
	public void addBlinding() {
		this.blinding = true;
	}
	
	public void addHydroReactivity() {
		this.hydro = true;
	}
	
	public void addExplosive(float bang) {
		this.explosive = bang;
	}
	/**
	 * Experimental custom toxicity handler
	 * @param toxIn Pun not intended
	 */
	@Beta
	public void addCustomToxicity(CustomToxicity toxIn, float mod)
	{
		hasCustomTox = true;
		tox = Optional.of(toxIn);
		toxMod = mod;
	}

	public void applyEffects(EntityLivingBase entity, float mod, int slot, boolean currentItem) {
			
		if(this.radiation > (RadiationConfig.realisticRads ? 0.0001F : 0))
			ContaminationUtil.applyRadData(entity, this.radiation * mod / 20F);

		if(this.digamma > 0)
			ContaminationUtil.applyDigammaData(entity, this.digamma * mod / 20F);

		if(this.fire > 0)
			entity.setFire(this.fire);

		if(this.asbestos)
			ContaminationUtil.applyAsbestos(entity, (int) (1 * mod));
		
		if (tox.isPresent())
			ContaminationUtil.applyCustom(entity, (int) (mod * toxMod), tox.get());

		if(this.hydro && currentItem) {

			if(!entity.worldObj.isRemote && entity.isInWater() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem();
				
				player.inventory.mainInventory[player.inventory.currentItem] = held.getItem().getContainerItem(held);
				player.inventoryContainer.detectAndSendChanges();
				player.worldObj.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, 2F, true, true);
			}
		}

		if(this.explosive > 0 && currentItem) {

			if(!entity.worldObj.isRemote && entity.isBurning() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem();
				
				player.inventory.mainInventory[player.inventory.currentItem] = held.getItem().getContainerItem(held);
				player.inventoryContainer.detectAndSendChanges();
				player.worldObj.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, this.explosive, true, true);
			}
		}

		if(this.blinding && !(entity instanceof EntityPlayer && ArmorUtil.checkForGoggles((EntityPlayer) entity))) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
		}
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if (this.radiation > 0)
		{
			list.add(EnumChatFormatting.GREEN + "[" + I18nUtil.resolveKey(HbmCollection.radioactive) + "]");
			double rad = (Math.floor(radiation * 1000) / 1000);
			double totalrad = radiation * stack.stackSize;
//			System.out.println(radiation);
//			System.out.println(radiation * stack.stackSize);
			String radFormatted = rad >= 0.0001F ? basicFormatter.format(rad) : sciNotFormatter.format(radiation);
			String totalRadFormatted = rad >= 0.0001F ? basicFormatter.format(totalrad) : sciNotFormatter.format(totalrad);
			list.add(EnumChatFormatting.YELLOW + (radFormatted + "RAD/s"));
			if (stack.stackSize > 1)
				list.add(String.format("%s%sRAD/s total", EnumChatFormatting.YELLOW, totalRadFormatted));
		}
		
		if(this.fire > 0) {
			list.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey(HbmCollection.hot) + "]");
		}
		
		if(this.blinding) {
			list.add(EnumChatFormatting.DARK_AQUA + "[" + I18nUtil.resolveKey(HbmCollection.blinding) + "]");
		}
		
		if(this.asbestos) {
			list.add(EnumChatFormatting.WHITE + "[" + I18nUtil.resolveKey(HbmCollection.asbestos) + "]");
		}
		
		if (tox.isPresent())
			list.add(String.format("%s[%s]", tox.get().format, I18nUtil.resolveKey("trait." + tox.get().name)));
		
		if(this.hydro) {
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey(HbmCollection.hydro) + "]");
		}
		
		if(this.explosive > 0) {
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey(HbmCollection.explosive) + "]");
		}
		
		if (this.digamma > 0)
		{
			float d = ((int) (digamma * 10000F)) / 10F;
			float totalDrx = d * stack.stackSize;
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey(HbmCollection.drx) + "]");
			list.add(EnumChatFormatting.DARK_RED + "" + d + "mDRX/s");
			// FIXME
			if (stack.stackSize > 1)
				list.add(String.format("%s%smDRX/s total", EnumChatFormatting.DARK_RED, totalDrx));
		}
		
		int[] breeder = BreederRecipes.getFuelValue(stack);
		
		if(breeder != null) {
			list.add(BreederRecipes.getHEATString("[" + I18nUtil.resolveKey("trait.heat", breeder[0]) + "]", breeder[0]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.breeding", breeder[1]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.furnace", (breeder[0] * breeder[1] * 5)));
		}
	}
	// May switch to an enum instead
	public static class CustomToxicity
	{
		public String name;
		public float damage;
		public float cap;
		public EnumChatFormatting format = EnumChatFormatting.WHITE;
		public CustomToxicity(String name, float damage, float cap)
		{
			this.name = name;
			this.damage = damage;
			this.cap = cap;
		}
		public CustomToxicity(String name, float damage, float cap, EnumChatFormatting format)
		{
			this(name, damage, cap);
			this.format = format;
		}
		
		@Override
		public String toString()
		{
			return String.format("Toxicity name: %s; damage at max: %s, cap: %s; formatting code: %s; default string: %s", name, damage, cap, format.getFriendlyName(), super.toString());
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof CustomToxicity))
				return false;
			CustomToxicity test = (CustomToxicity) obj;
			return test.name.equals(name);
		}
	}
}
