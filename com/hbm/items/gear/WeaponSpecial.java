package com.hbm.items.gear;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

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
        	world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 0.1F);
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
		
		return false;
    }
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f1, float f2, float f3)
    {

		if(this == ModItems.shimmer_sledge) {
			if(world.getBlock(x, y, z) != Blocks.air) {
				
				EntityRubble rubble = new EntityRubble(world);
				rubble.posX = x + 0.5F;
				rubble.posY = y;
				rubble.posZ = z + 0.5F;
				
				rubble.setMetaBasedOnMat(world.getBlock(x, y, z).getMaterial());
				
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
				if(world.getBlock(x, y, z) != Blocks.air) {
					world.func_147480_a(x, y, z, false);
				}
				if(world.getBlock(x, y + 1, z) != Blocks.air) {
					world.func_147480_a(x, y + 1, z, false);
				}
				if(world.getBlock(x, y - 1, z) != Blocks.air) {
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
        return multimap;
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
	}

}
