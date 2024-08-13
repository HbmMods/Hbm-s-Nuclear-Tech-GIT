package com.hbm.items.armor;

import java.util.List;

import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.atmosphere.ChunkAtmosphereManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;

import api.hbm.fluid.IFillableItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemModOxy extends ItemArmorMod implements IFillableItem {

    // Quite similar to JetpackBase, but with a few crucial differences meaning we can't subclass

    private FluidType fuel;
    private int maxFuel;
    private int rate;
    private int consumption;

    private AudioWrapper audioBreathing;

    public ItemModOxy(int maxFuel, int rate, int consumption) {
        super(ArmorModHandler.plate_only, true, false, false, false);
        this.maxFuel = maxFuel;
        this.rate = rate;
        this.consumption = consumption;
        fuel = Fluids.OXYGEN;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.LIGHT_PURPLE + fuel.getLocalizedName() + ": " + getFuel(itemstack) + "mB / " + this.maxFuel + "mB");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
		list.add(EnumChatFormatting.GOLD + I18n.format("armor.mustSeal"));
	}
	
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.RED + "  " + stack.getDisplayName() + " (" + fuel.getLocalizedName() + ": " + getFuel(stack) + "mB / " + this.maxFuel + "mB");
	}
	
    @Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
        if(entity.worldObj.isRemote) {
            ItemStack stack = ArmorModHandler.pryMods(armor)[ArmorModHandler.plate_only];
            if(getInUse(stack)) {
                if(audioBreathing == null || !audioBreathing.isPlaying()) {
                    audioBreathing = MainRegistry.proxy.getLoopedSound("hbm:player.plss_breathing", (float)entity.posX, (float)entity.posY, (float)entity.posZ, 0.1F, 5.0F, 1.0F, 5);
                    audioBreathing.startSound();
                }

                audioBreathing.updatePosition((float)entity.posX, (float)entity.posY, (float)entity.posZ);
                audioBreathing.keepAlive();
            } else {
                if(audioBreathing != null) {
                    audioBreathing.stopSound();
                    audioBreathing = null;
                }
            }
        }
    }

    // returns true if the entity can breathe, either via the contained air, or via the atmosphere itself
    // if contained air is used, it'll be decremented here, this saves on multiple atmosphere checks
    public boolean attemptBreathing(EntityLivingBase entity, ItemStack stack, CBT_Atmosphere atmosphere) {
        if(ChunkAtmosphereManager.proxy.canBreathe(atmosphere)) {
            setInUse(stack, false);
            return true;
        }

        if(entity.ticksExisted % rate == 0)
            setFuel(stack, Math.max(getFuel(stack) - consumption, 0));

        boolean hasFuel = getFuel(stack) > 0;

        setInUse(stack, hasFuel);

        return hasFuel;
    }

    @Override
    public boolean acceptsFluid(FluidType type, ItemStack stack) {
        return type == fuel;
    }

    @Override
    public int tryFill(FluidType type, int amount, ItemStack stack) {
        if(!acceptsFluid(type, stack))
            return amount;

        int fill = getFuel(stack);
        int toFill = Math.min(amount, maxFuel - fill);

        setFuel(stack, fill + toFill);

        return amount - toFill;
    }

    @Override
    public boolean providesFluid(FluidType type, ItemStack stack) {
        return false;
    }

    @Override
    public int tryEmpty(FluidType type, int amount, ItemStack stack) {
        return 0;
    }

    @Override
    public FluidType getFirstFluidType(ItemStack stack) {
        return null;
    }
	
    public static boolean getInUse(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return false;
		}
		
		return stack.stackTagCompound.getInteger("ticks") > 20;
	}
	
	public static void setInUse(ItemStack stack, boolean inUse) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
        if(inUse) {
            stack.stackTagCompound.setInteger("ticks", stack.stackTagCompound.getInteger("ticks") + 1);
        } else {
            stack.stackTagCompound.setInteger("ticks", 0);
        }
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

    public int getMaxFuel() {
        return maxFuel;
    }

    @Override
    public int getFill(ItemStack stack) {
        return 0;
    }
    
}
