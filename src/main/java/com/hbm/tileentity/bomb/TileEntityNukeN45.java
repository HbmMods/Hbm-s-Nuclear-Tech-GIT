package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.inventory.container.ContainerNukeN45;
import com.hbm.inventory.gui.GUINukeN45;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityNukeN45 extends TileEntity implements ISidedInventory, IGUIProvider {

	public ItemStack slots[];
	private String customName;
	
	public boolean primed = false;
	
	public TileEntityNukeN45() {
		slots = new ItemStack[2];
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
		return this.hasCustomInventoryName() ? this.customName : "container.nukeN45";
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
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		primed = nbt.getBoolean("primed");
		
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
		NBTTagList list = new NBTTagList();
		
		nbt.setBoolean("primed", primed);
		
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
	
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, primed ? 1 : 0, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			
			if(primed) {
				
				if(getType() == 0) {
					return;
				}

				int rad = 0;
				
				if(slots[1] != null) {
					
					if(slots[1].getItem() == ModItems.upgrade_effect_1)
						rad = 5;
					if(slots[1].getItem() == ModItems.upgrade_effect_2)
						rad = 10;
					if(slots[1].getItem() == ModItems.upgrade_effect_3)
						rad = 15;
				}
				
				if(rad == 0) {
					primed = false;
					return;
				}
				
				List<Object> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5 - rad, yCoord + 0.5 - rad, zCoord + 0.5 - rad, xCoord + 0.5 + rad, yCoord + 0.5 + rad, zCoord + 0.5 + rad));
				
				for(Object o : list) {
					
					Entity e  = (Entity)o;
					
					if(e instanceof EntityLivingBase && e.width * e.width * e.height >= 0.5 && !((EntityLivingBase)e).isPotionActive(Potion.invisibility.id)) {
						int t = getType();
						this.clearSlots();
						explode(worldObj, xCoord, yCoord, zCoord, t);
						break;
					}
				}
			}
		}
		
	}
	
	public static void explode(World world, int x, int y, int z, int type) {
		
		if(!world.isRemote) {
			world.setBlockToAir(x, y, z);
			
			//System.out.println(type);
			
			switch(type) {
			case 1:
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
				break;
			case 2:
		        world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 4.0F, true);
				break;
			case 3:
				ExplosionLarge.explode(world, x, y, z, 15, true, false, false);
				break;
			case 4:
				world.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(world, (int)(BombConfig.missileRadius * 0.75F), x + 0.5, y + 0.5, z + 0.5));
				EntityNukeTorex.statFac(world, x + 0.5, y + 0.5, z + 0.5, BombConfig.missileRadius * 0.75F);
				break;
			}
		}
	}
	
	public int getType() {
		
		if(!primed && slots[1] != null) {
			
			if(slots[1].getItem() == ModItems.upgrade_effect_1 ||
					slots[1].getItem() == ModItems.upgrade_effect_2 ||
					slots[1].getItem() == ModItems.upgrade_effect_3)
				return 100;
		}
		
		if(slots[0] != null) {

			if(slots[0].getItem() == Item.getItemFromBlock(ModBlocks.det_cord))
				return 1;
			if(slots[0].getItem() == Item.getItemFromBlock(Blocks.tnt))
				return 2;
			if(slots[0].getItem() == Item.getItemFromBlock(ModBlocks.det_charge))
				return 3;
			if(slots[0].getItem() == Item.getItemFromBlock(ModBlocks.det_nuke))
				return 4;
		}
		
		return 0;
	}
	
	public void clearSlots() {
		for(int i = 0; i < slots.length; i++)
		{
			slots[i] = null;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerNukeN45(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeN45(player.inventory, this);
	}

}
