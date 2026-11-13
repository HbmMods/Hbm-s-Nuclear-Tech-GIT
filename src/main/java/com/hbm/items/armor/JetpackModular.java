package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.JetpackPart.EnumJetpackPart;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelJetpackModular;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class JetpackModular extends ItemArmorMod implements IItemRendererProvider {

	public JetpackModular() {
		super(ArmorModHandler.plate_only, false, true, false, false);
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!(entity instanceof EntityPlayer)) return;
		
		ItemStack jetpack = ArmorModHandler.pryMods(armor)[ArmorModHandler.plate_only];
		if(jetpack == null) return;
				
		onArmorTick(entity.worldObj, (EntityPlayer)entity, jetpack);
		ArmorModHandler.applyMod(armor, jetpack);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
	}
	
	public static boolean[] getInstalledParts(ItemStack stack) {
		boolean[] parts = new boolean[EnumJetpackPart.values().length];
		
		if(stack.hasTagCompound()) {
			for(int i = 0; i < parts.length; i++) {
				parts[i] = stack.stackTagCompound.getBoolean("p" + i);
			}
		}
		
		return parts;
	}
	
	public static boolean isPartInstalled(ItemStack stack, EnumJetpackPart part) {
		if(!stack.hasTagCompound()) return false;
		return stack.stackTagCompound.getBoolean("p" + part.ordinal());
	}
	
	/// all of these are gonna look like ass because inventing an entirely new system for mutex would be overkill ///
	
	public static EnumJetpackPart getInstalledTank(ItemStack stack) {
		if(isPartInstalled(stack, EnumJetpackPart.TANK_JETFUEL)) return EnumJetpackPart.TANK_JETFUEL;
		if(isPartInstalled(stack, EnumJetpackPart.TANK_KEROSENE)) return EnumJetpackPart.TANK_KEROSENE;
		if(isPartInstalled(stack, EnumJetpackPart.TANK_COALGAS)) return EnumJetpackPart.TANK_COALGAS;
		return null;
	}
	
	public static EnumJetpackPart getInstalledFlightModel(ItemStack stack) {
		if(isPartInstalled(stack, EnumJetpackPart.STABILIZER)) return EnumJetpackPart.STABILIZER;
		if(isPartInstalled(stack, EnumJetpackPart.WINGS)) return EnumJetpackPart.WINGS;
		return null;
	}
	
	public static EnumJetpackPart getInstalledThruster(ItemStack stack) {
		if(isPartInstalled(stack, EnumJetpackPart.THRUSTER_LARGE)) return EnumJetpackPart.THRUSTER_LARGE;
		if(isPartInstalled(stack, EnumJetpackPart.THRUSTERS)) return EnumJetpackPart.THRUSTERS;
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.SetArmorModel event, ItemStack armor) {
		ModelJetpackModular.INSTANCE.renderFromEvent(event, armor);
	}

	@Override public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) { return armorType == 1; }

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return ModelJetpackModular.INSTANCE;
	}

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { GL11.glTranslated(5, -12, 0); }
			public void renderNonInv() { GL11.glScaled(0.25, 0.25, 0.25); GL11.glTranslated(0, -12, 5); }
			public void renderCommonWithStack(ItemStack stack) {
				ModelJetpackModular.INSTANCE.renderFromItem(stack, true, 1F);
			}};
	}
}
