package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.config.VersatileConfig;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityStorageDrum extends TileEntityMachineBase {
	
	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };

	public TileEntityStorageDrum() {
		super(24);
	}

	@Override
	public String getName() {
		return "container.storageDrum";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			float rad = 0;
			
			for(int i = 0; i < 24; i++) {
				
				if(slots[i] != null) {
					
					Item item = slots[i].getItem();
					
					if(item instanceof IItemHazard && worldObj.getTotalWorldTime() % 20 == 0) {
						rad += ((IItemHazard)item).getModule().radiation;
					}
					
					if(item == ModItems.nuclear_waste_long && worldObj.rand.nextInt(VersatileConfig.getLongDecayChance()) == 0) {
						slots[i] = new ItemStack(ModItems.nuclear_waste_long_depleted, 1, slots[i].getItemDamage());
					}
					
					if(item == ModItems.nuclear_waste_long_tiny && worldObj.rand.nextInt(VersatileConfig.getLongDecayChance() / 10) == 0) {
						slots[i] = new ItemStack(ModItems.nuclear_waste_long_depleted_tiny, 1, slots[i].getItemDamage());
					}
					
					if(item == ModItems.nuclear_waste_short && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance()) == 0) {
						slots[i] = new ItemStack(ModItems.nuclear_waste_short_depleted, 1, slots[i].getItemDamage());
					}
					
					if(item == ModItems.nuclear_waste_short_tiny && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						slots[i] = new ItemStack(ModItems.nuclear_waste_short_depleted_tiny, 1, slots[i].getItemDamage());
					}
					
					if(item == ModItems.nugget_au198 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 100) == 0) {
						slots[i] = new ItemStack(ModItems.nugget_mercury, 1, slots[i].getItemDamage());
					}
				}
			}
			
			if(rad > 0) {
				radiate(worldObj, xCoord, yCoord, zCoord, rad);
			}
		}
	}
	
	private void radiate(World world, int x, int y, int z, float rads) {
		
		double range = 32D;
		
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5).expand(range, range, range));
		
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - (x + 0.5), (e.posY + e.getEyeHeight()) - (y + 0.5), e.posZ - (z + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(x + 0.5 + vec.xCoord * i);
				int iy = (int)Math.floor(y + 0.5 + vec.yCoord * i);
				int iz = (int)Math.floor(z + 0.5 + vec.zCoord * i);
				
				res += world.getBlock(ix, iy, iz).getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		Item item = itemStack.getItem();
		
		if(item == ModItems.nuclear_waste_long || 
				item == ModItems.nuclear_waste_long_tiny || 
				item == ModItems.nuclear_waste_short || 
				item == ModItems.nuclear_waste_short_tiny || 
				item == ModItems.nugget_au198)
			return true;
		
		return false;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {

		Item item = itemStack.getItem();
		
		if(item == ModItems.nuclear_waste_long_depleted || 
				item == ModItems.nuclear_waste_long_depleted_tiny || 
				item == ModItems.nuclear_waste_short_depleted || 
				item == ModItems.nuclear_waste_short_depleted_tiny || 
				item == ModItems.nugget_mercury)
			return true;
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_arr;
	}
}
