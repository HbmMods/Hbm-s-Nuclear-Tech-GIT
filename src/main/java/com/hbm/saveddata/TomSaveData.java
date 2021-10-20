package com.hbm.saveddata;

import java.util.Iterator;

import com.hbm.lib.RefStrings;
import com.hbm.main.ModEventHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class TomSaveData extends WorldSavedData {

   final static String key = "impactData";
	public static float dust;
	public static float fire;
	public static boolean impact;
   
   public static TomSaveData forWorld(World world) {
      TomSaveData result = (TomSaveData)world.perWorldStorage.loadData(TomSaveData.class, "impactData");
      if (result == null) {         
    	 world.perWorldStorage.setData(key, new TomSaveData(key));
         result = (TomSaveData)world.perWorldStorage.loadData(TomSaveData.class, "impactData");    	  
      }
      return result;
   }
   
   private NBTTagCompound data = new NBTTagCompound();

   public TomSaveData(String tagName) {
       super(tagName);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
  	 data = compound.getCompoundTag(key);
  	 this.dust = compound.getFloat("dust");
  	 this.fire = compound.getFloat("fire");
  	 this.impact = compound.getBoolean("impact");
  	 ModEventHandler.dust = this.dust;
  	 ModEventHandler.fire = this.fire;
  	 ModEventHandler.impact = this.impact;
   }

   @Override
   public void writeToNBT(NBTTagCompound compound) {
       compound.setTag(key, data);
   }

   public NBTTagCompound getData() {
       return data;
   }
}