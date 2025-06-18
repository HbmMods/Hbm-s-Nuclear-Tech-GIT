package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class WeaponSpecial extends ItemSword {
	
	Random rand = new Random();

	public WeaponSpecial(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
    
    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
		if(this == ModItems.schrabidium_hammer) {
			return EnumRarity.rare;
		}
		if(this == ModItems.ullapool_caber) {
			return EnumRarity.uncommon;
		}
		if(this == ModItems.shimmer_sledge || this == ModItems.shimmer_axe) {
			return EnumRarity.epic;
		}
		
		return EnumRarity.common;
    }
    
    @Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer)
    {
    	World world = entity.worldObj;

		if(this == ModItems.schrabidium_hammer) {
			if (!world.isRemote)
        	{
        		entity.setHealth(0.0F);
        	}
        	world.playSoundAtEntity(entity, "hbm:weapon.bonk", 3.0F, 1.0F);
		}

		if(this == ModItems.bottle_opener) {
			if (!world.isRemote)
        	{
				int i = rand.nextInt(7);
				if(i == 0)
					entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 5 * 60 * 20, 0));
				if(i == 1)
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 60 * 20, 2));
				if(i == 2)
					entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5 * 60 * 20, 2));
				if(i == 3)
					entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 1 * 60 * 20, 0));
        	}
        	world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 1.F);
		}
    	
		if(this == ModItems.ullapool_caber) {
			if (!world.isRemote)
        	{
				world.createExplosion(null, entity.posX, entity.posY, entity.posZ, 7.5F, true);
        	}
			
			stack.damageItem(505, entityPlayer);
		}
    	
		if(this == ModItems.shimmer_sledge) {
			Vec3 vec = entityPlayer.getLookVec();
			double dX = vec.xCoord * 5;
			double dY = vec.yCoord * 5;
			double dZ = vec.zCoord * 5;

			entity.motionX += dX;
			entity.motionY += dY;
			entity.motionZ += dZ;
        	world.playSoundAtEntity(entity, "hbm:weapon.bang", 3.0F, 1.F);
		}
    	
		if(this == ModItems.shimmer_axe) {
			entity.setHealth(entity.getHealth() / 2);
			
        	world.playSoundAtEntity(entity, "hbm:weapon.slice", 3.0F, 1.F);
		}
    	
		if(this == ModItems.wood_gavel) {
        	world.playSoundAtEntity(entity, "hbm:weapon.whack", 3.0F, 1.F);
		}
    	
		if(this == ModItems.lead_gavel) {
        	world.playSoundAtEntity(entity, "hbm:weapon.whack", 3.0F, 1.F);
        	
			entity.addPotionEffect(new PotionEffect(HbmPotion.lead.id, 15 * 20, 4));
		}
		
		if(this == ModItems.diamond_gavel) {
			
			float ded = entity.getMaxHealth() / 3;
			entity.setHealth(entity.getHealth() - ded);
			
        	world.playSoundAtEntity(entity, "hbm:weapon.whack", 3.0F, 1.F);
		}
    	
		if(this == ModItems.wrench) {

			Vec3 vec = entityPlayer.getLookVec();
			
			double dX = vec.xCoord * 0.5;
			double dY = vec.yCoord * 0.5;
			double dZ = vec.zCoord * 0.5;

			entity.motionX += dX;
			entity.motionY += dY;
			entity.motionZ += dZ;
        	world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 0.75F);
		}
    	
		if(this == ModItems.memespoon) {

			if(entityPlayer.fallDistance >= 2) {
				world.playSoundAtEntity(entity, "hbm:weapon.bang", 3.0F, 0.75F);
				entity.setHealth(0);
			}
			
			if(!(entityPlayer instanceof EntityPlayer))
				return false;
			
			if(entityPlayer.fallDistance >= 20 && !((EntityPlayer)entityPlayer).capabilities.isCreativeMode) {
				if(!world.isRemote) {
					world.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(world, 100, entity.posX, entity.posY, entity.posZ));
					EntityNukeTorex.statFacStandard(world, entity.posX, entity.posY, entity.posZ, 100);
				}
			}
		}

		if(this == ModItems.stopsign || this == ModItems.sopsign)
        	world.playSoundAtEntity(entity, "hbm:weapon.stop", 1.0F, 1.0F);
		
		return false;
    }
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f1, float f2, float f3)
    {

		if(this == ModItems.shimmer_sledge) {
			if(world.getBlock(x, y, z) != Blocks.air && world.getBlock(x, y, z).getExplosionResistance(null) < 6000) {
				
				EntityRubble rubble = new EntityRubble(world);
				rubble.posX = x + 0.5F;
				rubble.posY = y;
				rubble.posZ = z + 0.5F;
				
				rubble.setMetaBasedOnBlock(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
				
				Vec3 vec = player.getLookVec();
				double dX = vec.xCoord * 5;
				double dY = vec.yCoord * 5;
				double dZ = vec.zCoord * 5;

				rubble.motionX += dX;
				rubble.motionY += dY;
				rubble.motionZ += dZ;
	        	world.playSoundAtEntity(rubble, "hbm:weapon.bang", 3.0F, 1.0F);
				
	        	if(!world.isRemote) {
	        		
	        		world.spawnEntityInWorld(rubble);
					world.func_147480_a(x, y, z, false);
	        	}
			}
			return true;
		}
		
		if(this == ModItems.shimmer_axe) {

        	world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:weapon.kapeng", 3.0F, 1.0F);

        	if(!world.isRemote) {
				if(world.getBlock(x, y, z) != Blocks.air && world.getBlock(x, y, z).getExplosionResistance(null) < 6000) {
					world.func_147480_a(x, y, z, false);
				}
				if(world.getBlock(x, y + 1, z) != Blocks.air && world.getBlock(x, y + 1, z).getExplosionResistance(null) < 6000) {
					world.func_147480_a(x, y + 1, z, false);
				}
				if(world.getBlock(x, y - 1, z) != Blocks.air && world.getBlock(x, y - 1, z).getExplosionResistance(null) < 6000) {
					world.func_147480_a(x, y - 1, z, false);
				}
        	}
			return true;
		}
		
		return false;
    }
    
    @Override
	public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
		if(this == ModItems.schrabidium_hammer) {
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", -0.5, 1));
		}
		if(this == ModItems.shimmer_sledge || this == ModItems.shimmer_axe) {
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", -0.2, 1));
		}
		if(this == ModItems.wrench || this == ModItems.wrench_flipped) {
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", -0.1, 1));
		}
        return multimap;
    }
	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {

    	if(entity instanceof EntityPlayer) {
    		if(ArmorUtil.checkForFiend((EntityPlayer) entity)) {
    			((EntityPlayer) entity).triggerAchievement(MainRegistry.achFiend);
    		} else  if(ArmorUtil.checkForFiend2((EntityPlayer) entity)) {
        		((EntityPlayer) entity).triggerAchievement(MainRegistry.achFiend2);
        	}
    	}
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.schrabidium_hammer) {
			list.add("Even though it says \"+1000000000");
			list.add("damage\", it's actually \"onehit anything\"");
		}
		if(this == ModItems.ullapool_caber) {
			list.add("High-yield Scottish face removal.");
			list.add("A sober person would throw it...");
		}
		if(this == ModItems.bottle_opener) {
			list.add("My very own bottle opener.");
			list.add("Use with caution!");
		}
		if(this == ModItems.shimmer_sledge) {
			if(MainRegistry.polaroidID == 11) {
				list.add("shimmer no");
				list.add("drop that hammer");
				list.add("you're going to hurt somebody");
				list.add("shimmer no");
				list.add("shimmer pls");
			} else {
				list.add("Breaks everything, even portals.");
			}
		}
		if(this == ModItems.shimmer_axe) {
			if(MainRegistry.polaroidID == 11) {
				list.add("shim's toolbox does an e-x-p-a-n-d");
			} else {
				list.add("Timber!");
			}
		}
		if(this == ModItems.wrench) {
			list.add("Mechanic Richard");
		}
		if(this == ModItems.wrench_flipped) {
			list.add("Wrench 2: The Wrenchening");
		}
		if(this == ModItems.memespoon) {
			list.add(EnumChatFormatting.DARK_GRAY + "Level 10 Shovel");
			list.add(EnumChatFormatting.AQUA + "Deals crits while the wielder is rocket jumping");
			list.add(EnumChatFormatting.RED + "20% slower firing speed");
			list.add(EnumChatFormatting.RED + "No random critical hits");
		}

		if(this == ModItems.wood_gavel) {
			list.add("Thunk!");
		}
		if(this == ModItems.lead_gavel) {
			list.add("You are hereby sentenced to lead poisoning.");
		}
		if(this == ModItems.diamond_gavel) {
			list.add("The joke! It makes sense now!!");
			list.add("");
			list.add(EnumChatFormatting.BLUE + "Deals as much damage as it needs to.");
		}
	}

}
