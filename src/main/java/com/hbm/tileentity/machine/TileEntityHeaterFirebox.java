package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.container.ContainerFirebox;
import com.hbm.inventory.gui.GUIFirebox;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeaterFirebox extends TileEntityMachineBase implements IGUIProvider, IHeatSource {

	public int maxBurnTime;
	public int burnTime;
	public int burnHeat;
	public boolean wasOn = false;
	private int playersUsing = 0;
	
	public float doorAngle = 0;
	public float prevDoorAngle = 0;

	public int heatEnergy;
	public static final int maxHeatEnergy = 100_000;
	
	public ModuleBurnTime burnModule;

	public TileEntityHeaterFirebox() {
		super(2);
		
		burnModule = new ModuleBurnTime()
				.setLigniteTimeMod(1.25)
				.setCoalTimeMod(1.25)
				.setCokeTimeMod(1.25)
				.setSolidTimeMod(1.5)
				.setRocketTimeMod(1.5)
				.setBalefireTimeMod(0.5)

				.setLigniteHeatMod(2)
				.setCoalHeatMod(2)
				.setCokeHeatMod(2)
				.setSolidHeatMod(3)
				.setRocketHeatMod(5)
				.setBalefireHeatMod(15);
	}

	@Override
	public String getName() {
		return "container.heaterFirebox";
	}
	
	@Override
	public void openInventory() {
		if(!worldObj.isRemote) this.playersUsing++;
	}
	
	@Override
	public void closeInventory() {
		if(!worldObj.isRemote) this.playersUsing--;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			wasOn = false;
			
			if(burnTime <= 0) {
				
				for(int i = 0; i < 2; i++) {
					if(slots[i] != null) {
						
						int fuel = burnModule.getBurnTime(slots[i]);
						
						if(fuel > 0) {
							this.maxBurnTime = this.burnTime = fuel;
							this.burnHeat = burnModule.getBurnHeat(100, slots[i]);
							slots[i].stackSize--;

							if(slots[i].stackSize == 0) {
								slots[i] = slots[i].getItem().getContainerItem(slots[i]);
							}

							this.wasOn = true;
							break;
						}
					}
				} 
			} else {
				
				if(this.heatEnergy < this.maxHeatEnergy) {
					burnTime--;
				}
				this.wasOn = true;
				
				if(worldObj.rand.nextInt(15) == 0) {
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fire.fire", 1.0F, 0.5F + worldObj.rand.nextFloat() * 0.5F);
				}
			}
			
			if(wasOn) {
				this.heatEnergy = Math.min(this.heatEnergy + this.burnHeat, maxHeatEnergy);
			} else {
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
				this.burnHeat = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setInteger("burnTime", this.burnTime);
			data.setInteger("burnHeat", this.burnHeat);
			data.setInteger("heatEnergy", this.heatEnergy);
			data.setInteger("playersUsing", this.playersUsing);
			data.setBoolean("wasOn", this.wasOn);
			this.networkPack(data, 50);
		} else {
			this.prevDoorAngle = this.doorAngle;
			float swingSpeed = (doorAngle / 10F) + 3;
			
			if(this.playersUsing > 0) {
				this.doorAngle += swingSpeed;
			} else {
				this.doorAngle -= swingSpeed;
			}
			
			this.doorAngle = MathHelper.clamp_float(this.doorAngle, 0F, 135F);
			
			if(wasOn && worldObj.getTotalWorldTime() % 5 == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				double x = xCoord + 0.5 + dir.offsetX;
				double y = yCoord + 0.25;
				double z = zCoord + 0.5 + dir.offsetZ;
				worldObj.spawnParticle("flame", x + worldObj.rand.nextDouble() * 0.5 - 0.25, y + worldObj.rand.nextDouble() * 0.25, z + worldObj.rand.nextDouble() * 0.5 - 0.25, 0, 0, 0);
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return burnModule.getBurnTime(itemStack) > 0;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.burnHeat = nbt.getInteger("burnHeat");
		this.heatEnergy = nbt.getInteger("heatEnergy");
		this.playersUsing = nbt.getInteger("playersUsing");
		this.wasOn = nbt.getBoolean("wasOn");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.burnHeat = nbt.getInteger("burnHeat");
		this.heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("maxBurnTime", maxBurnTime);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnHeat", burnHeat);
		nbt.setInteger("heatEnergy", heatEnergy);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFirebox(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFirebox(player.inventory, this);
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
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
					yCoord + 1,
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
