package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.container.ContainerFurnaceSteel;
import com.hbm.inventory.gui.GUIFurnaceSteel;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.ItemStackUtil;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceSteel extends TileEntityMachineBase implements IGUIProvider {

	public int[] progress = new int[3];
	public int[] bonus = new int[3];
	public static final int processTime = 40_000; // assuming vanilla furnace rules with 200 ticks of coal fire burning at 200HU/t
	
	public int heat;
	public static final int maxHeat = 100_000;
	public static final double diffusion = 0.05D;
	
	private ItemStack[] lastItems = new ItemStack[3];
	
	public boolean wasOn = false;
	
	public TileEntityFurnaceSteel() {
		super(6);
	}

	@Override
	public String getName() {
		return "container.furnaceSteel";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			tryPullHeat();
			
			this.wasOn = false;
			
			int burn = (heat - this.maxHeat / 3) / 10;
			
			for(int i = 0; i < 3; i++) {
				
				if(slots[i] == null || lastItems[i] == null || !slots[i].isItemEqual(lastItems[i])) {
					progress[i] = 0;
					bonus[i] = 0;
				}
				
				if(canSmelt(i)) {
					progress[i] += burn;
					this.heat -= burn;
					this.wasOn = true;
					if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 2);
				}
				
				lastItems[i] = slots[i];
				
				if(progress[i] >= processTime) {
					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[i]);
					
					if(slots[i + 3] == null) {
						slots[i + 3] = result.copy();
					} else {
						slots[i + 3].stackSize += result.stackSize;
					}
					
					this.addBonus(slots[i], i);
					
					while(bonus[i] >= 100) {
						slots[i + 3].stackSize =  Math.min(slots[i + 3].getMaxStackSize(), slots[i + 3].stackSize + result.stackSize);
						bonus[i] -= 100;
					}
					
					this.decrStackSize(i, 1);
					
					progress[i] = 0;
					
				}
			}

			this.networkPackNT(50);

		} else {
			
			if(this.wasOn) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				worldObj.spawnParticle("smoke", xCoord + 0.5 - dir.offsetX * 1.125 - rot.offsetX * 0.75, yCoord + 2.625, zCoord + 0.5 - dir.offsetZ * 1.125 - rot.offsetZ * 0.75, 0.0, 0.05, 0.0);
				
				if(worldObj.rand.nextInt(20) == 0)
					worldObj.spawnParticle("cloud", xCoord + 0.5 + dir.offsetX * 0.75, yCoord + 2, zCoord + 0.5 + dir.offsetZ * 0.75, 0.0, 0.05, 0.0);

				if(worldObj.rand.nextInt(15) == 0)
					worldObj.spawnParticle("lava", xCoord + 0.5 + dir.offsetX * 1.5 + rot.offsetX * (worldObj.rand.nextDouble() - 0.5), yCoord + 0.75, zCoord + 0.5 + dir.offsetZ * 1.5 + rot.offsetZ * (worldObj.rand.nextDouble() - 0.5), dir.offsetX * 0.5D, 0.05, dir.offsetZ * 0.5D);

			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		BufferUtil.writeIntArray(buf, this.progress);
		BufferUtil.writeIntArray(buf, this.bonus);
		buf.writeInt(this.heat);
		buf.writeBoolean(this.wasOn);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.progress = BufferUtil.readIntArray(buf);
		this.bonus = BufferUtil.readIntArray(buf);
		this.heat = buf.readInt();
		this.wasOn = buf.readBoolean();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.progress = nbt.getIntArray("progress");
		this.bonus = nbt.getIntArray("bonus");
		this.heat = nbt.getInteger("heat");
		
		NBTTagList list = nbt.getTagList("lastItems", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("lastItem");
			if(b0 >= 0 && b0 < lastItems.length) {
				lastItems[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setIntArray("progress", progress);
		nbt.setIntArray("bonus", bonus);
		nbt.setInteger("heat", heat);
		
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < lastItems.length; i++) {
			if(lastItems[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("lastItem", (byte) i);
				lastItems[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("lastItems", list);
	}
	
	protected void addBonus(ItemStack stack, int index) {
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.startsWith("ore")) { this.bonus[index] += 25; return; }
			if(name.startsWith("log")) { this.bonus[index] += 50; return; }
			if(name.equals("anyTar")) { this.bonus[index] += 50; return; }
		}
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= this.maxHeat) return;
		
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > this.maxHeat)
					this.heat = this.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	public boolean canSmelt(int index) {
		
		if(this.heat < this.maxHeat / 3) return false;
		if(slots[index] == null) return false;
		
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[index]);
		
		if(result == null) return false;
		if(slots[index + 3] == null) return true;
		
		if(!result.isItemEqual(slots[index + 3])) return false;
		if(result.stackSize + slots[index + 3].stackSize > slots[index + 3].getMaxStackSize()) return false;
		
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1, 2, 3, 4, 5 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i < 3)
			return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i > 2;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceSteel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceSteel(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 3,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
