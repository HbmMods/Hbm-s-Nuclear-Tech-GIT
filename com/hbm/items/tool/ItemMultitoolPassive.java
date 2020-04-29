package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemMultitoolPassive extends Item {
	
	Random rand = new Random();
	
	public ItemMultitoolPassive() {
		this.setMaxDamage(5000);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(player.isSneaking()) {
			
	        world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
	        
			if (this == ModItems.multitool_ext) {
				return new ItemStack(ModItems.multitool_miner, 1, stack.getItemDamage());
			} else if(this == ModItems.multitool_miner) {
				ItemStack item = new ItemStack(ModItems.multitool_hit, 1, stack.getItemDamage());
				item.addEnchantment(Enchantment.looting, 3);
				item.addEnchantment(Enchantment.knockback, 3);
				return item;
			} else if (this == ModItems.multitool_hit) {
				return new ItemStack(ModItems.multitool_beam, 1, stack.getItemDamage());
			} else if (this == ModItems.multitool_beam) {
				return new ItemStack(ModItems.multitool_sky, 1, stack.getItemDamage());
			} else if (this == ModItems.multitool_sky) {
				ItemStack item = new ItemStack(ModItems.multitool_mega, 1, stack.getItemDamage());
				item.addEnchantment(Enchantment.knockback, 5);
				return item;
			} else if (this == ModItems.multitool_mega) {
				ItemStack item = new ItemStack(ModItems.multitool_joule, 1, stack.getItemDamage());
				item.addEnchantment(Enchantment.knockback, 3);
				return item;
			} else if (this == ModItems.multitool_joule) {
				ItemStack item = new ItemStack(ModItems.multitool_decon, 1, stack.getItemDamage());
				return item;
			} else if (this == ModItems.multitool_decon) {
				ItemStack item = new ItemStack(ModItems.multitool_dig, 1, stack.getItemDamage());
				item.addEnchantment(Enchantment.looting, 3);
				item.addEnchantment(Enchantment.fortune, 3);
				return item;
			}
	        
		} else {
			if(this == ModItems.multitool_ext) {
				return stack;
			} else if (this == ModItems.multitool_miner) {

				EntityMinerBeam plasma = new EntityMinerBeam(world, player, 0.75F);

				world.playSoundAtEntity(player, "hbm:weapon.immolatorIgnite", 1.0F, 1F);
				//world.playSoundAtEntity(player, "hbm:weapon.immolatorShoot", 1.0F, 1F);
				
				if (!world.isRemote)
					world.spawnEntityInWorld(plasma);

				return stack;
			} else if (this == ModItems.multitool_hit) {
				return stack;
			} else if (this == ModItems.multitool_beam) {

				EntityLaserBeam plasma = new EntityLaserBeam(world, player, 1F);

				world.playSoundAtEntity(player, "hbm:weapon.immolatorIgnite", 1.0F, 1F);
				//world.playSoundAtEntity(player, "hbm:weapon.immolatorShoot", 1.0F, 1F);
				
				if (!world.isRemote)
					world.spawnEntityInWorld(plasma);

				return stack;
			} else if (this == ModItems.multitool_sky) {
				for(int i = 0; i < 15; i++) {
					int a = (int)player.posX - 15 + rand.nextInt(31);
					int b = (int)player.posZ - 15 + rand.nextInt(31);
					//if(!world.isRemote) {
						EntityLightningBolt blitz = new EntityLightningBolt(world, a, world.getHeightValue(a, b), b);
						world.spawnEntityInWorld(blitz);
					//}
				}
				return stack;
			} else if (this == ModItems.multitool_mega) {
				return stack;
			} else if (this == ModItems.multitool_joule) {
				return stack;
			} else if (this == ModItems.multitool_decon) {
				return stack;
			}
		}
		
		return stack;
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f1, float f2, float f3)
    {
		if(this == ModItems.multitool_ext) {
			Block b = world.getBlock(x, y, z);
			ItemStack s = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(Item.getItemFromBlock(b), 1, world.getBlockMetadata(x, y, z)));
			if(s != null) {
				ItemStack t = s.copy();
				if(!world.isRemote)
					world.setBlock(x, y, z, Blocks.air, 0, 3);
				
	            if(!player.inventory.addItemStackToInventory(t))
	            	player.dropPlayerItemWithRandomChoice(t, false);
	            player.swingItem();
			}
		} else if (this == ModItems.multitool_miner) {
		} else if (this == ModItems.multitool_hit) {
		} else if (this == ModItems.multitool_beam) {
		} else if (this == ModItems.multitool_sky) {
		} else if (this == ModItems.multitool_mega) {
			
			ExplosionChaos.levelDown(world, x, y, z, 2);
			return true;
			
		} else if (this == ModItems.multitool_joule) {
			
			int l = 25;
			float part = -1F/16F;
			
			Vec3 vec0 = player.getLookVec();
			vec0.rotateAroundY(.25F);
			List<int[]> list = Library.getBlockPosInPath(x, y, z, l, vec0);
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));
			vec0.rotateAroundY(part);
			list.addAll(Library.getBlockPosInPath(x, y, z, l, vec0));

			if(!world.isRemote)
				for(int j = 0; j < list.size(); j++) {
					
					int x1 = list.get(j)[0];
					int y1 = list.get(j)[1];
					int z1 = list.get(j)[2];
					int w1 = list.get(j)[3];
					
					Block b = world.getBlock(x1, y1, z1);
					float k = b.getExplosionResistance(null);
							
					if(k < 6000 && b != Blocks.air) {
						
						EntityRubble rubble = new EntityRubble(world);
						rubble.posX = x1 + 0.5F;
						rubble.posY = y1;
						rubble.posZ = z1 + 0.5F;
						
						rubble.motionY = 0.025F * w1 + 0.15F;
						rubble.setMetaBasedOnBlock(b, world.getBlockMetadata(x1, y1, z1));
						
						world.spawnEntityInWorld(rubble);
						
						world.setBlock(x1, y1, z1, Blocks.air);
					}
				}
			
			return true;
			
		} else if (this == ModItems.multitool_decon) {

			if(!world.isRemote)
				ExplosionChaos.decontaminate(world, x, y, z);
			return true;
			
		}
		
		return false;
    }

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		if(this == ModItems.multitool_ext) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 7, 0));
		} else if (this == ModItems.multitool_miner) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 8, 0));
		} else if (this == ModItems.multitool_hit) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 16, 0));
		} else if (this == ModItems.multitool_beam) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 8, 0));
		} else if (this == ModItems.multitool_sky) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 5, 0));
		} else if (this == ModItems.multitool_mega) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 12, 0));
		} else if (this == ModItems.multitool_joule) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 12, 0));
		} else if (this == ModItems.multitool_decon) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", 5, 0));
		}
		return multimap;
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.multitool_ext) {
			list.add("Right click instantly destroys smeltable blocks");
			list.add("Mined blocks will be smelted and put in the player's inventory");
		}
		if(this == ModItems.multitool_miner) {
			list.add("Shoots lasers which destroy smeltable blocks");
			list.add("These blocks will drop the smelted item");
		}
		if(this == ModItems.multitool_hit) {
			list.add("Very high damage against mobs");
			list.add("Strong knock back");
		}
		if(this == ModItems.multitool_beam) {
			list.add("Shoots lasers which ignite blocks and mobs");
			list.add("Lasers are destroyed by water");
		}
		if(this == ModItems.multitool_sky) {
			list.add("Right click summons a lightning storm around the player");
			list.add("Lightning can also hit the player using the fist");
		}
		if(this == ModItems.multitool_mega) {
			list.add("Right click will level down blocks with a powerful punch");
			list.add("Immense knockback against mobs");
		}
		if(this == ModItems.multitool_joule) {
			list.add("Right click will break blocks in the line of sight");
			list.add("These blocks will be flung up as rubble");
		}
		if(this == ModItems.multitool_decon) {
			list.add("Right click will remove radiation effect from blocks");
			list.add("Blocks like nuclear waste turn into lead");
		}
	}

}
