package com.hbm.saveddata.satellites;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.particle.helper.ASATCreator;
import com.hbm.saveddata.SatelliteSavedData;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SatelliteASAT extends SatelliteBase {
	
	public static final String CMD_GETMISSILECOUNT = "getmissilecount";
	public static final String CMD_FIRE = "fire";
	
	public int missilesLoaded = 0;

	@Override public String getType() { return "ORBITAL_MISSILE_PLATFORM"; }

	@Override
	public IChatComponent[] getInfo(World world) {
		
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.ASAT.ordinal())) + ".name"),
				new ChatComponentTranslation("satellite.missilesLoaded", missilesLoaded),
		};
	}

	@Override
	public void onPartDelivered(World world, ItemStack part) {
		
		if(part.getItem() == ModItems.satellite) {
			if(part.getItemDamage() == EnumSatType.ASAT_AMMO.ordinal()) this.missilesLoaded = MathHelper.clamp_int(this.missilesLoaded + 2, 0, 4);
			this.markDirty();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("missilesLoaded", missilesLoaded);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.missilesLoaded = nbt.getInteger("missilesLoaded");
	}

	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_GETMISSILECOUNT)) {
			this.tx = "" + this.missilesLoaded;
			return;
		}
		
		if(cmd[0].equals(CMD_FIRE) && cmd.length == 2) {
			
			if(this.missilesLoaded <= 0) {
				this.tx = "NO AMMO";
				return;
			}
			
			String target = cmd[1];
			SatelliteSavedData data = SatelliteSavedData.getData(world);
			int freq = XSatelliteRegistry.getFreqFromTargetId(world, target);
			
			if(data.isFreqTaken(freq) && rand.nextBoolean()) {
				data.sats.remove(freq);
				data.markDirty();
				ASATCreator.composeEffect(world);
				this.tx = "TARGET DESTROYED";
			} else {
				this.tx = "TARGET MISSED";
			}
			
			this.missilesLoaded--;
			this.markDirty();
			return;
		}
	}
}
