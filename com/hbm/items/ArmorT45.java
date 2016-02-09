package com.hbm.items;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.render.ModelT45Boots;
import com.hbm.render.ModelT45Chest;
import com.hbm.render.ModelT45Helmet;
import com.hbm.render.ModelT45Legs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.ISpecialArmor;

public class ArmorT45 extends ItemArmor implements ISpecialArmor {
	  @SideOnly(Side.CLIENT)
	  private ModelT45Helmet helmet;
	  private ModelT45Chest plate;
	  private ModelT45Legs legs;
	  private ModelT45Boots boots;
	  
	  public ArmorT45(ArmorMaterial armorMaterial, int renderIndex, int armorType)
	  {
			super(armorMaterial, renderIndex, armorType);
	  }
	  
	  @Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	  {
		  if(stack.getItem() == ModItems.t45_helmet)
			  return armorType == 0;
		  if(stack.getItem() == ModItems.t45_plate)
			  return armorType == 1;
		  if(stack.getItem() == ModItems.t45_legs)
			  return armorType == 2;
		  if(stack.getItem() == ModItems.t45_boots)
			  return armorType == 3;
		  return false;
	  }
	  
	  @Override
	@SideOnly(Side.CLIENT)
	  public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	  {
		  if(this == ModItems.t45_helmet)
		  {
			  if (armorSlot == 0)
			  {
				  if (this.helmet == null) {
					  this.helmet = new ModelT45Helmet();
				  }
				  return this.helmet;
			  }
		  }
		  if(this == ModItems.t45_plate)
		  {
			  if (armorSlot == 1)
			  {
				  if (this.plate == null) {
					  this.plate = new ModelT45Chest();
				  }
				  return this.plate;
			  }
		  }
		  if(this == ModItems.t45_legs)
		  {
			  if (armorSlot == 2)
			  {
				  if (this.legs == null) {
					  this.legs = new ModelT45Legs();
				  }
				  return this.legs;
			  }
		  }
		  if(this == ModItems.t45_boots)
		  {
			  if (armorSlot == 3)
			  {
				  if (this.boots == null) {
					  this.boots = new ModelT45Boots();
				  }
				  return this.boots;
			  }
		  }
		  return null;
	  }
	  
	  @Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	  {
		  if(stack.getItem() == ModItems.t45_helmet)
		  {
			  return "hbm:textures/models/T45Helmet.png";
		  }
		  if(stack.getItem() == ModItems.t45_plate)
		  {
			  return "hbm:textures/models/T45Chest.png";
		  }
		  if(stack.getItem() == ModItems.t45_legs)
		  {
			  return "hbm:textures/models/T45Legs.png";
		  }
		  if(stack.getItem() == ModItems.t45_boots)
		  {
			  return "hbm:textures/models/T45Boots.png";
		  }
		  return null;
	  }

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		//return null;
		if(player instanceof EntityPlayer && Library.checkArmor((EntityPlayer)player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots))
		{
			if(source == DamageSource.inFire || source == DamageSource.onFire || source == DamageSource.fall || source == DamageSource.drown || source == DamageSource.cactus || source == DamageSource.magic || source.isProjectile())
				return new ArmorProperties(1, 1, MathHelper.floor_double(999999999));
			if(source == DamageSource.fallingBlock || source == DamageSource.anvil)
				return new ArmorProperties(1, 1, MathHelper.floor_double(10));
			if(source == DamageSource.lava)
				return new ArmorProperties(1, 1, MathHelper.floor_double(5));
			if(source.isExplosion())
				return new ArmorProperties(1, 1, MathHelper.floor_double(10));
		}
		return new ArmorProperties(1, 1, MathHelper.floor_double(3));
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if(slot == 0)
		{
			return 3;
		}
		if(slot == 1)
		{
			return 8;
		}
		if(slot == 2)
		{
			return 6;
		}
		if(slot == 3)
		{
			return 3;
		}
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if(source != DamageSource.cactus && source != DamageSource.drown && source != DamageSource.fall)
		stack.damageItem(damage * 1, entity);
		
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if(armor.getItem() == ModItems.t45_plate)
		{
			if(armor.stackTagCompound == null)
			{
				armor.stackTagCompound = new NBTTagCompound();
				armor.stackTagCompound.setInteger("charge", 0);
			}
			if(armor.stackTagCompound.getInteger("charge") <= 0){
				for(int i = 0; i < player.inventory.mainInventory.length; i++)
				{
					ItemStack stack = player.inventory.getStackInSlot(i);
					if(stack != null && stack.getItem() == ModItems.fusion_core && stack.getItemDamage() != stack.getMaxDamage())
					{
						if(armor.stackTagCompound.getInteger("charge") == 0)
						{
							if(world.isRemote && armor.stackTagCompound.getInteger("charge") == 0)
							{
							}
							if(!player.worldObj.isRemote)
							{
								int j = stack.getItemDamage();
								armor.stackTagCompound.setInteger("charge", stack.getMaxDamage() - j);
								player.inventory.mainInventory[i] = null;
								player.addChatMessage(new ChatComponentText("[Power Armor recharged]"));
								break;
							}
						}
					}
				}
			}
			
			if(armor.stackTagCompound.getInteger("charge") > 0 && Library.checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots))
			{
				armor.stackTagCompound.setInteger("charge", armor.stackTagCompound.getInteger("charge") - 1);
			}
		}
		
		if(Library.checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots))
		{
			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == ModItems.t45_plate && player.inventory.armorInventory[2].stackTagCompound != null && player.inventory.armorInventory[2].stackTagCompound.getInteger("charge") > 0)
			{
				 player.addPotionEffect(new PotionEffect(Potion.jump.id, 5, 0, true));
				 player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, 1, true));
				 player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 5, 2, true));
				 player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 5, 0, true));
			} else {
				 player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5, 1, true));
				 player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5, 0, true));
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.getItem() == ModItems.t45_plate)
		{
			if(itemstack.stackTagCompound != null)
			{
				if(itemstack.stackTagCompound.getInteger("charge") != 0)
					list.add("Charge: " + (itemstack.stackTagCompound.getInteger("charge") / 50 + 1) + "%");
				else
					list.add("Charge: " + (itemstack.stackTagCompound.getInteger("charge") / 50) + "%");
			}
		}
	}
}
