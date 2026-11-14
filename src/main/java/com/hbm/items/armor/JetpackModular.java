package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.armor.JetpackPart.EnumJetpackPart;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelJetpackModular;
import com.hbm.render.tileentity.IItemRendererProvider;
import com.hbm.util.Vec3NT;

import api.hbm.fluidmk2.IFillableItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class JetpackModular extends ItemArmorMod implements IItemRendererProvider, IFillableItem {
	
	public static final String KEY_FUEL = "fuel";

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
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		EnumJetpackPart flightModel = getInstalledFlightModel(stack);

		if(canOperate(stack) && props.isJetpackActive()) {
			Vec3NT moveVec = new Vec3NT(0, 1, 0); // stabilizer flight model
			
			if(flightModel == null) { // helicopter controls
				moveVec.rotateAroundZDeg(-player.rotationPitch).rotateAroundYDeg(-player.rotationYaw + 90);
			} else if(flightModel == EnumJetpackPart.WINGS) {
				moveVec.rotateAroundZDeg(-player.rotationPitch - 90).rotateAroundYDeg(-player.rotationYaw + 90);
				//player.motionY += 0.06D;
			}
			
			EnumJetpackPart thruster = getInstalledThruster(stack);
			
			double velocity = thruster.velocity;

			player.motionX += moveVec.xCoord * velocity;
			player.motionY += moveVec.yCoord * velocity;
			player.motionZ += moveVec.zCoord * velocity;
		}
		
		if(flightModel == EnumJetpackPart.WINGS && !player.onGround) {
			Vec3NT vel = new Vec3NT(player.motionX, player.motionY, player.motionZ);
			Vec3NT heading = new Vec3NT(0, 1, 0).rotateAroundZDeg(-player.rotationPitch - 90).rotateAroundYDeg(-player.rotationYaw + 90);
			double velocity = vel.lengthVector();
			double glide = MathHelper.clamp_double((velocity - 0.2) / 2D, 0D, 1D);//0.8D;
			player.motionX *= (1D - glide);
			player.motionY *= (1D - glide);
			player.motionZ *= (1D - glide);
			player.motionX += heading.xCoord * velocity * glide;
			player.motionY += heading.yCoord * velocity * glide;
			player.motionZ += heading.zCoord * velocity * glide;
			player.motionX /= 0.95D;
			player.motionY /= 0.95D;
			player.motionZ /= 0.95D;
		}
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
	
	public static void togglePart(ItemStack stack, EnumJetpackPart part, boolean enable) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("p" + part.ordinal(), enable);
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
	
	/** Returns true if there's a thruster and a tank installed and there is some fuel left */
	public static boolean canOperate(ItemStack stack) {
		if(getInstalledTank(stack) == null) return false;
		if(getInstalledThruster(stack) == null) return false;
		return getFuel(stack) > 0;
	}
	
	public static int getFuel(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;
		return stack.stackTagCompound.getInteger(KEY_FUEL);
	}
	
	public static void setFuel(ItemStack stack, int fuel) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger(KEY_FUEL, fuel);
	}
	
	public static int getMaxFuel(ItemStack stack) {
		EnumJetpackPart tank = getInstalledTank(stack);
		if(tank == null) return 0;
		return tank.tankSize + (isPartInstalled(stack, EnumJetpackPart.EXTENSION_TANKS) ? EnumJetpackPart.EXTENSION_TANKS.tankSize : 0);
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return JetpackModular.getInstalledTank(stack) != null;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		if(!acceptsFluid(type, stack)) return amount;
		
		int fill = this.getFuel(stack);
		int req = getMaxFuel(stack) - fill;
		int toFill = Math.min(amount, req);
		this.setFuel(stack, fill + toFill);
		
		return amount - toFill;
	}

	@Override public boolean providesFluid(FluidType type, ItemStack stack) { return false; }
	@Override public int tryEmpty(FluidType type, int amount, ItemStack stack) { return 0; }
	@Override public FluidType getFirstFluidType(ItemStack stack) { return null; }
	@Override public int getFill(ItemStack stack) { return 0; }
}
