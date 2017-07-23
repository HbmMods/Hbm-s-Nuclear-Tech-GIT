package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemFuelRod;
import com.hbm.lib.Library;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityReactorMultiblock extends TileEntity implements ISidedInventory, IReactor, ISource {

	public int water;
	public final static int waterMax = 10000000;
	public int cool;
	public final static int coolMax = 10000000;
	public int heat;
	public final static int heatMax = 1000000;
	public int power;
	public final static int maxPower = 1000000;
	private ItemStack slots[];
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	public boolean isLoaded = false;
	
	private String customName;

	public TileEntityReactorMultiblock() {
		slots = new ItemStack[34];
	}
	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.reactorMultiblock";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		water = nbt.getInteger("water");
		cool = nbt.getInteger("cool");
		power = nbt.getInteger("power");
		heat = nbt.getInteger("heat");
		
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("water", water);
		nbt.setInteger("cool", cool);
		nbt.setInteger("power", power);
		nbt.setInteger("heat", heat);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public void updateEntity() {
		if(isStructureValid(worldObj))
		{
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				ffgeuaInit();
			
			//if(!worldObj.isRemote)
			{
				if(slots[30] != null && slots[30].getItem() == Items.water_bucket && this.water + 250000 <= waterMax)
				{
					this.slots[30].stackSize--;
					this.water += 250000;
					if(this.slots[30].stackSize == 0)
					{
						this.slots[30] = this.slots[30].getItem().getContainerItem(this.slots[30]);
					}
				}
				if(slots[30] != null && slots[30].getItem() == ModItems.rod_water && this.water + 250000 <= waterMax)
				{
					this.slots[30].stackSize--;
					this.water += 250000;
					if(this.slots[30].stackSize == 0)
					{
						this.slots[30] = this.slots[30].getItem().getContainerItem(this.slots[30]);
					}
				}
				if(slots[30] != null && slots[30].getItem() == ModItems.rod_dual_water && this.water + 500000 <= waterMax)
				{
					this.slots[30].stackSize--;
					this.water += 500000;
					if(this.slots[30].stackSize == 0)
					{
						this.slots[30] = this.slots[30].getItem().getContainerItem(this.slots[30]);
					}
				}
				if(slots[30] != null && slots[30].getItem() == ModItems.rod_quad_water && this.water + 1000000 <= waterMax)
				{
					this.slots[30].stackSize--;
					this.water += 1000000;
					if(this.slots[30].stackSize == 0)
					{
						this.slots[30] = this.slots[30].getItem().getContainerItem(this.slots[30]);
					}
				}
				if(slots[30] != null && slots[30].getItem() == ModItems.inf_water)
				{
					this.water = waterMax;
				}
				
				if(slots[31] != null && slots[31].getItem() == ModItems.rod_coolant && this.cool + 250000 <= coolMax)
				{
					this.slots[31].stackSize--;
					this.cool += 250000;
					if(this.slots[31].stackSize == 0)
					{
						this.slots[31] = this.slots[31].getItem().getContainerItem(this.slots[31]);
					}
				}
				
				if(slots[31] != null && slots[31].getItem() == ModItems.rod_dual_coolant && this.cool + 500000 <= coolMax)
				{
					this.slots[31].stackSize--;
					this.cool += 500000;
					if(this.slots[31].stackSize == 0)
					{
						this.slots[31] = this.slots[31].getItem().getContainerItem(this.slots[31]);
					}
				}
				
				if(slots[31] != null && slots[31].getItem() == ModItems.rod_quad_coolant && this.cool + 1000000 <= coolMax)
				{
					this.slots[31].stackSize--;
					this.cool += 1000000;
					if(this.slots[31].stackSize == 0)
					{
						this.slots[31] = this.slots[31].getItem().getContainerItem(this.slots[31]);
					}
				}
				
				if(slots[31] != null && slots[31].getItem() == ModItems.inf_coolant)
				{
					this.cool = coolMax;
				}
				
				
				if(hasFuse())
				{
					for(int i = 0; i < 30; i++)
					{
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_uranium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(1);
							attemptPower(250);
					
							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_uranium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_uranium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(1);
							attemptPower(250);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_dual_uranium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_uranium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(1);
							attemptPower(250);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_quad_uranium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_plutonium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(2);
							attemptPower(375);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_plutonium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_plutonium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(2);
							attemptPower(375);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_plutonium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(2);
							attemptPower(375);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_mox_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(1);
							attemptPower(125);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_mox_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_mox_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(1);
							attemptPower(125);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_dual_mox_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_mox_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(1);
							attemptPower(125);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_quad_mox_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_schrabidium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(10);
							attemptPower(62500);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_schrabidium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_schrabidium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(10);
							attemptPower(62500);

							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted);
							}
						}
						if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_schrabidium_fuel)
						{
							int j = slots[i].getItemDamage();
							this.slots[i].setItemDamage(j += 1);
							attemptHeat(10);
							attemptPower(62500);
						
							if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
							{
								this.slots[i] = new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted);
							}
						}
					}
				}
				
				if(this.power > maxPower)
				{
					this.power = maxPower;
				}
				
				if(this.heat > heatMax)
				{
					this.explode();
				}
				
				if(((slots[0] != null && slots[0].getItem() instanceof ItemFuelRod) || slots[0] == null) && 
						((slots[1] != null && !(slots[1].getItem() instanceof ItemFuelRod)) || slots[1] == null) && 
						((slots[2] != null && !(slots[2].getItem() instanceof ItemFuelRod)) || slots[2] == null) && 
						((slots[3] != null && !(slots[3].getItem() instanceof ItemFuelRod)) || slots[3] == null) && 
						((slots[4] != null && !(slots[4].getItem() instanceof ItemFuelRod)) || slots[4] == null) && 
						((slots[5] != null && !(slots[5].getItem() instanceof ItemFuelRod)) || slots[5] == null) && 
						((slots[6] != null && !(slots[6].getItem() instanceof ItemFuelRod)) || slots[6] == null) && 
						((slots[7] != null && !(slots[7].getItem() instanceof ItemFuelRod)) || slots[7] == null) && 
						((slots[8] != null && !(slots[8].getItem() instanceof ItemFuelRod)) || slots[8] == null) && 
						((slots[9] != null && !(slots[9].getItem() instanceof ItemFuelRod)) || slots[9] == null) && 
						((slots[10] != null && !(slots[10].getItem() instanceof ItemFuelRod)) || slots[10] == null) && 
						((slots[11] != null && !(slots[11].getItem() instanceof ItemFuelRod)) || slots[11] == null) && 
						((slots[12] != null && !(slots[12].getItem() instanceof ItemFuelRod)) || slots[12] == null) && 
						((slots[13] != null && !(slots[13].getItem() instanceof ItemFuelRod)) || slots[13] == null) && 
						((slots[14] != null && !(slots[14].getItem() instanceof ItemFuelRod)) || slots[14] == null) && 
						((slots[15] != null && !(slots[15].getItem() instanceof ItemFuelRod)) || slots[15] == null) && 
						((slots[16] != null && !(slots[16].getItem() instanceof ItemFuelRod)) || slots[16] == null) && 
						((slots[17] != null && !(slots[17].getItem() instanceof ItemFuelRod)) || slots[17] == null) && 
						((slots[18] != null && !(slots[18].getItem() instanceof ItemFuelRod)) || slots[18] == null) && 
						((slots[19] != null && !(slots[19].getItem() instanceof ItemFuelRod)) || slots[19] == null) && 
						((slots[20] != null && !(slots[20].getItem() instanceof ItemFuelRod)) || slots[20] == null) && 
						((slots[21] != null && !(slots[21].getItem() instanceof ItemFuelRod)) || slots[21] == null) && 
						((slots[22] != null && !(slots[22].getItem() instanceof ItemFuelRod)) || slots[22] == null) && 
						((slots[23] != null && !(slots[23].getItem() instanceof ItemFuelRod)) || slots[23] == null) && 
						((slots[24] != null && !(slots[24].getItem() instanceof ItemFuelRod)) || slots[24] == null) && 
						((slots[25] != null && !(slots[25].getItem() instanceof ItemFuelRod)) || slots[25] == null) && 
						((slots[26] != null && !(slots[26].getItem() instanceof ItemFuelRod)) || slots[26] == null) && 
						((slots[27] != null && !(slots[27].getItem() instanceof ItemFuelRod)) || slots[27] == null) && 
						((slots[28] != null && !(slots[28].getItem() instanceof ItemFuelRod)) || slots[28] == null) && 
						((slots[29] != null && !(slots[29].getItem() instanceof ItemFuelRod)) || slots[29] == null))
				{
					if(this.heat - 10 >= 0 && this.cool - 10 >= 0)
					{
						this.heat -= 10;
						this.cool -= 2;
					}
					
					if(this.heat < 10 && this.cool != 0)
					{
						this.heat--;
						this.cool--;
					}
					
					if(this.heat != 0 && this.cool == 0)
					{
						this.heat--;
					}
					
					if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
					isLoaded = false;
					
				} else {

					if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
					isLoaded = true;
					
					if(!this.isCoatingValid(worldObj))
					{
						int strength = 20;
						float f = strength;
				        HashSet hashset = new HashSet();
				        int i;
				        int j;
				        int k;
				        double d5;
				        double d6;
				        double d7;
				        double wat = 20;
				        boolean isOccupied = false;
				        
				        i = MathHelper.floor_double(this.xCoord - wat - 1.0D);
				        j = MathHelper.floor_double(this.xCoord + wat + 1.0D);
				        k = MathHelper.floor_double(this.yCoord - wat - 1.0D);
				        int i2 = MathHelper.floor_double(this.yCoord + wat + 1.0D);
				        int l = MathHelper.floor_double(this.zCoord - wat - 1.0D);
				        int j2 = MathHelper.floor_double(this.zCoord + wat + 1.0D);
				        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
				        Vec3 vec3 = Vec3.createVectorHelper(this.xCoord, this.yCoord, this.zCoord);

				        for (int i1 = 0; i1 < list.size(); ++i1)
				        {
				            Entity entity = (Entity)list.get(i1);
				            double d4 = entity.getDistance(this.xCoord, this.yCoord, this.zCoord) / 4;

				            if (d4 <= 20)
				            {
				                d5 = entity.posX - this.xCoord;
				                d6 = entity.posY + entity.getEyeHeight() - this.yCoord;
				                d7 = entity.posZ - this.zCoord;
				                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
				                if (d9 < wat)
				                {
				                	if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
				                	{
				                		/*Library.damageSuit(((EntityPlayer)entity), 0);
				                		Library.damageSuit(((EntityPlayer)entity), 1);
				                		Library.damageSuit(((EntityPlayer)entity), 2);
				                		Library.damageSuit(((EntityPlayer)entity), 3);*/
				                		
				                	} else if(entity instanceof EntityCreeper) {
				                		EntityNuclearCreeper creep = new EntityNuclearCreeper(this.worldObj);
				                		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
				                		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
				                		if(!entity.isDead)
				                			if(!worldObj.isRemote)
				                				worldObj.spawnEntityInWorld(creep);
				                		entity.setDead();
				                	} else if(entity instanceof EntityLivingBase && !(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom))
				                    {
				                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 2 * 60 * 20, 2));
				                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.getId(), 20, 4));
				                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1 * 60 * 20, 1));
				                    }
				                }
				            }
				        }
					}
				}
				
				//Batteries
				
				power = Library.chargeItemsFromTE(slots, 32, power, maxPower);
			}
		}
		
	}

	@Override
	public boolean isStructureValid(World world) {
		if(world.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.reactor_computer &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.reactor_conductor &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.reactor_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord + 1, this.yCoord + 0, this.zCoord) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord - 1, this.yCoord + 0, this.zCoord) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord, this.yCoord + 0, this.zCoord + 1) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord + 1) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord, this.yCoord + 0, this.zCoord - 1) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord - 1) == ModBlocks.reactor_control &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord + 1, this.yCoord + 0, this.zCoord + 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord + 1, this.yCoord + 0, this.zCoord - 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord - 1, this.yCoord + 0, this.zCoord - 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord - 1, this.yCoord + 0, this.zCoord + 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 1) == ModBlocks.reactor_element &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord) == ModBlocks.reactor_hatch &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord) == ModBlocks.reactor_hatch &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 2) == ModBlocks.reactor_hatch &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 2) == ModBlocks.reactor_hatch)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCoatingValid(World world) {
		if(world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord + 2, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord + 2, this.zCoord + 1)== ModBlocks.brick_concrete &&
				
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord - 2, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord - 2, this.zCoord - 1)== ModBlocks.brick_concrete &&
				
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 1)== ModBlocks.brick_concrete &&
				
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 1)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 1)== ModBlocks.brick_concrete &&
				
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 2)== ModBlocks.brick_concrete &&
				
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 2)== ModBlocks.brick_concrete &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 2)== ModBlocks.brick_concrete)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public int getWaterScaled(int i) {
		return (water * i) / waterMax;
	}
	
	@Override
	public int getCoolantScaled(int i) {
		return (cool * i) / coolMax;
	}
	
	@Override
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public int getHeatScaled(int i) {
		return (heat * i) / heatMax;
	}
	
	@Override
	public boolean hasFuse() {
		return slots[33] != null && (slots[33].getItem() == ModItems.fuse || slots[33].getItem() == ModItems.screwdriver);
	}
	
	public void attemptPower(int i) {
		if(this.water - i >= 0)
		{
			this.power += i;
			this.water -= i;
		}
	}
	
	public void attemptHeat(int i) {
		Random rand = new Random();
		
		int j = rand.nextInt(i);
		
		if(this.cool - j >= 0)
		{
			this.cool -= j;
		} else {
			this.heat += i;
		}
	}
	
	public void explode() {
		for(int i = 0; i < slots.length; i++)
		{
			this.slots[i] = null;
		}
        
        EntityNukeExplosionAdvanced explosion = new EntityNukeExplosionAdvanced(this.worldObj);
        explosion.speed = 25;
        explosion.coefficient = 5.0F;
        explosion.destructionRange = 35;
        explosion.posX = this.xCoord;
        explosion.posY = this.yCoord;
        explosion.posZ = this.zCoord;
        this.worldObj.spawnEntityInWorld(explosion);
        ExplosionParticle.spawnMush(this.worldObj, this.xCoord, this.yCoord - 3, this.zCoord);
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 2, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 2, this.zCoord, getTact());
	}
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public int getSPower() {
		return power;
	}

	@Override
	public void setSPower(int i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}
}
