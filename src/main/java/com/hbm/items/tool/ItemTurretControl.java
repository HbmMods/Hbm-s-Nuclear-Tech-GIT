package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.turret.TurretBase;
import com.hbm.lib.Library;
import com.hbm.tileentity.turret.TileEntityTurretBase;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;
import com.hbm.tileentity.turret.TileEntityTurretCheapo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemTurretControl extends Item {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		
		if(entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) entity;

			if(player.getHeldItem() != null && stack != null && player.getHeldItem().equals(stack)) {
				
				if(stack.hasTagCompound()) {
					int x = stack.getTagCompound().getInteger("xCoord");
					int y = stack.getTagCompound().getInteger("yCoord");
					int z = stack.getTagCompound().getInteger("zCoord");

					TileEntity te = world.getTileEntity(x, y, z);

					if(te != null && te instanceof TileEntityTurretBase) {
						TileEntityTurretBase turret = (TileEntityTurretBase) te;

						if(!turret.isAI) {
							turret.rotationYaw = player.rotationYaw;
							turret.rotationPitch = player.rotationPitch;

							if(turret.rotationPitch < -60)
								turret.rotationPitch = -60;
							if(turret.rotationPitch > 30)
								turret.rotationPitch = 30;

							if(turret instanceof TileEntityTurretCheapo) {
								if(turret.rotationPitch < -30)
									turret.rotationPitch = -30;
								if(turret.rotationPitch > 15)
									turret.rotationPitch = 15;
							}
						}
					}

					if(te != null && te instanceof TileEntityTurretBaseNT) {
						TileEntityTurretBaseNT turret = (TileEntityTurretBaseNT) te;
						
						MovingObjectPosition pos = Library.rayTrace(player, 200, 1, true, true, false);
						
						if(pos == null)
							pos = Library.rayTrace(player, 200, 1);
						
						if(pos != null) { 
							
							Vec3 vecOrigin = Vec3.createVectorHelper(player.posX, player.posY + player.eyeHeight - player.getYOffset(), player.posZ);
							Vec3 vecDestination = Vec3.createVectorHelper(pos.blockX, pos.blockY, pos.blockZ);
							
							//List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(pos.blockX, pos.blockY, pos.blockZ).expand(1.0, 1.0, 1.0));
							List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(200, 200, 200));
							
							for(Entity e : list) {

								if (e.canBeCollidedWith() && e != player) {
									
									MovingObjectPosition mop = e.boundingBox.expand(0.2, 0.2, 0.2).calculateIntercept(vecOrigin, vecDestination);
									
									if(mop != null) {
										
										pos = mop;
										pos.typeOfHit = MovingObjectType.ENTITY;
										pos.entityHit = e;
									}
								}
							}
							
							if(pos.typeOfHit == MovingObjectType.ENTITY) {
								turret.target = pos.entityHit;
								turret.turnTowards(turret.getEntityPos(pos.entityHit));
								
							} else {
								turret.turnTowards(Vec3.createVectorHelper(pos.blockX + 0.5, pos.blockY + 0.5, pos.blockZ + 0.5));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(itemstack.stackTagCompound != null) {
			list.add("Linked to:");
			list.add("X: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord")));
			list.add("Y: " + String.valueOf(itemstack.stackTagCompound.getInteger("yCoord")));
			list.add("Z: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord")));
		} else {
			list.add("Please select a turret.");
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int fx, float fy, float fz, float f0) {
		
		if(world.getBlock(x, y, z) instanceof TurretBase) {
			if(stack.stackTagCompound == null) {
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			stack.stackTagCompound.setInteger("xCoord", x);
			stack.stackTagCompound.setInteger("yCoord", y);
			stack.stackTagCompound.setInteger("zCoord", z);
			
			if(!world.isRemote) {
				player.addChatMessage(new ChatComponentText("Turret Linked!"));
			}

			world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
			return true;
		}
		
		if(world.getBlock(x, y, z) instanceof BlockDummyable) {
			
			int[] pos = ((BlockDummyable)world.getBlock(x, y, z)).findCore(world, x, y, z);
			
			if(pos == null)
				return false;

			int ix = pos[0];
			int iy = pos[1];
			int iz = pos[2];
			
			TileEntity te = world.getTileEntity(ix, iy, iz);
			
			if(te instanceof TileEntityTurretBaseNT) {
				
				if(stack.stackTagCompound == null) {
					stack.stackTagCompound = new NBTTagCompound();
				}
				
				stack.stackTagCompound.setInteger("xCoord", ix);
				stack.stackTagCompound.setInteger("yCoord", iy);
				stack.stackTagCompound.setInteger("zCoord", iz);
				
				if(!world.isRemote) {
					player.addChatMessage(new ChatComponentText("Turret Linked!"));
				}

				world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
				return true;
			}
		}

		return false;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i) {

		int j = this.getMaxItemUseDuration(stack) - i;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
		MinecraftForge.EVENT_BUS.post(event);

		j = event.charge;

		if(stack.hasTagCompound()) {
			int x = stack.getTagCompound().getInteger("xCoord");
			int y = stack.getTagCompound().getInteger("yCoord");
			int z = stack.getTagCompound().getInteger("zCoord");

			if(world.getBlock(x, y, z) instanceof TurretBase) {

				TileEntity te = world.getTileEntity(x, y, z);

				if(te != null && te instanceof TileEntityTurretBase) {
					TileEntityTurretBase turret = (TileEntityTurretBase) te;

					if(!turret.isAI) {
						((TurretBase) world.getBlock(x, y, z)).executeReleaseAction(world, j, player.rotationYaw, player.rotationPitch, x, y, z);
					}
				}
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		World world = player.worldObj;

		if(stack.hasTagCompound()) {
			
			int x = stack.getTagCompound().getInteger("xCoord");
			int y = stack.getTagCompound().getInteger("yCoord");
			int z = stack.getTagCompound().getInteger("zCoord");

			if(world.getBlock(x, y, z) instanceof TurretBase) {

				TileEntity te = world.getTileEntity(x, y, z);

				if(te != null && te instanceof TileEntityTurretBase) {
					TileEntityTurretBase turret = (TileEntityTurretBase) te;

					if(!turret.isAI && turret.ammo > 0) {
						if(((TurretBase) world.getBlock(x, y, z)).executeHoldAction(world, stack.getMaxItemUseDuration() - count, player.rotationYaw, player.rotationPitch, x, y, z))
							turret.ammo--;
					}
				}
			}

			if(world.getTileEntity(x, y, z) instanceof TileEntityTurretBaseNT) {
				
				TileEntityTurretBaseNT turret = (TileEntityTurretBaseNT) world.getTileEntity(x, y, z);
				
				turret.manualSetup();
				if(!world.isRemote)
					turret.updateFiringTick();
			}
		}
	}
}
