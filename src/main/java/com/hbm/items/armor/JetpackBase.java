package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IPartiallyFillable;
import com.hbm.render.model.ModelJetPack;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public abstract class JetpackBase extends ItemArmorMod implements IPartiallyFillable {

	private ModelJetPack model;
	public FluidType fuel;
	public int maxFuel;

	public JetpackBase(FluidType fuel, int maxFuel) {
		super(ArmorModHandler.plate_only, false, true, false, false);
		this.fuel = fuel;
		this.maxFuel = maxFuel;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey(fuel.getUnlocalizedName()) + ": " + this.getFuel(itemstack) + "mB / " + this.maxFuel + "mB");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
		list.add(EnumChatFormatting.GOLD + "Can be worn on its own!");
	}
	
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		ItemStack jetpack = ArmorModHandler.pryMods(armor)[ArmorModHandler.plate_only];
		
		if(jetpack == null)
			return;
		
		list.add(EnumChatFormatting.RED + "  " + stack.getDisplayName() + " (" + I18nUtil.resolveKey(fuel.getUnlocalizedName()) + ": " + this.getFuel(jetpack) + "mB / " + this.maxFuel + "mB");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(!(entity instanceof EntityPlayer))
			return;
		
		ItemStack jetpack = ArmorModHandler.pryMods(armor)[ArmorModHandler.plate_only];
		
		if(jetpack == null)
			return;
				
		onArmorTick(entity.worldObj, (EntityPlayer)entity, jetpack);
		ArmorUtil.resetFlightTime((EntityPlayer)entity);
		
		ArmorModHandler.applyMod(armor, jetpack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.SetArmorModel event, ItemStack armor) {

		ModelBiped modelJetpack = getArmorModel(event.entityLiving, null, 1);
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		modelJetpack.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(this.getArmorTexture(armor, event.entity, 1, null)));
		modelJetpack.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (armorSlot == 1) {
			if (model == null) {
				this.model = new ModelJetPack();
			}
			return this.model;
		}
		
		return null;
	}
	
	protected void useUpFuel(EntityPlayer player, ItemStack stack, int rate) {

		if(player.ticksExisted % rate == 0)
			this.setFuel(stack, this.getFuel(stack) - 1);
	}
	
    public static int getFuel(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("fuel");
		
	}
	
	public static void setFuel(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("fuel", i);
		
	}

	@Override
	public FluidType getType(ItemStack stack) {
		return fuel;
	}

	@Override
	public int getFill(ItemStack stack) {
		return this.getFuel(stack);
	}

	@Override
	public void setFill(ItemStack stack, int fill) {
		this.setFuel(stack, fill);
	}

	@Override
	public int getMaxFill(ItemStack stack) {
		return this.maxFuel;
	}

	@Override
	public int getLoadSpeed(ItemStack stack) {
		return 10;
	}

	@Override
	public int getUnloadSpeed(ItemStack stack) {
		return 0;
	}
}
