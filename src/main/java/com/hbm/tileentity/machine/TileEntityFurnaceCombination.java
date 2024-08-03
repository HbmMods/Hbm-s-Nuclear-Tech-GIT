package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerFurnaceCombo;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFurnaceCombo;
import com.hbm.inventory.recipes.CombinationRecipes;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.Tuple.Pair;

import api.hbm.fluid.IFluidStandardSender;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceCombination extends TileEntityMachinePolluting implements IFluidStandardSender, IGUIProvider {

	public boolean wasOn;
	public int progress;
	public static int processTime = 20_000;
	
	public int heat;
	public static int maxHeat = 100_000;
	public static double diffusion = 0.25D;
	
	public FluidTank tank;

	public TileEntityFurnaceCombination() {
		super(4, 50);
		this.tank = new FluidTank(Fluids.NONE, 24_000);
	}

	@Override
	public String getName() {
		return "container.furnaceCombination";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.tryPullHeat();
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(int i = 2; i < 6; i++) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
					
					for(int y = yCoord; y <= yCoord + 1; y++) {
						for(int j = -1; j <= 1; j++) {
							if(tank.getFill() > 0) this.sendFluid(tank, worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * j, y, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
							this.sendSmoke(xCoord + dir.offsetX * 2 + rot.offsetX * j, y, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
						}
					}
				}
	
				for(int x = xCoord - 1; x <= xCoord + 1; x++) {
					for(int z = zCoord - 1; z <= zCoord + 1; z++) {
						if(tank.getFill() > 0) this.sendFluid(tank, worldObj, x, yCoord + 2, z, ForgeDirection.UP);
						this.sendSmoke(x, yCoord + 2, z, ForgeDirection.UP);
					}
				}
			}
			
			this.wasOn = false;
			
			tank.unloadTank(2, 3, slots);
			
			if(canSmelt()) {
				int burn = heat / 100;
				
				if(burn > 0) {
					this.wasOn = true;
					this.progress += burn;
					this.heat -= burn;
					
					if(progress >= processTime) {
						this.markChanged();
						progress -= this.processTime;
						
						Pair<ItemStack, FluidStack> pair = CombinationRecipes.getOutput(slots[0]);
						ItemStack out = pair.getKey();
						FluidStack fluid = pair.getValue();
						
						if(out != null)  {
							if(slots[1] == null) {
								slots[1] = out.copy();
							} else {
								slots[1].stackSize += out.stackSize;
							}
						}
						
						if(fluid != null) {
							if(tank.getTankType() != fluid.type) {
								tank.setTankType(fluid.type);
							}
							
							tank.setFill(tank.getFill() + fluid.fill);
						}
						
						this.decrStackSize(0, CombinationRecipes.getAmountRequired(slots[0]));
					}
					
					List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord + 2, zCoord - 0.5, xCoord + 1.5, yCoord + 4, zCoord + 1.5));
					
					for(Entity e : entities) e.setFire(5);
					
					if(worldObj.getTotalWorldTime() % 10 == 0) this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 1, this.zCoord, "hbm:weapon.flamethrowerShoot", 0.25F, 0.5F);
					if(worldObj.getTotalWorldTime() % 20 == 0) this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 3);
				}
			} else {
				this.progress = 0;
			}
			
			this.networkPackNT(50);
		} else {
			
			if(this.wasOn && worldObj.rand.nextInt(15) == 0) {
				worldObj.spawnParticle("lava", xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5, yCoord + 2, zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5, 0, 0, 0);
			}
		}
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(wasOn);
		buf.writeInt(heat);
		buf.writeInt(progress);
		tank.serialize(buf);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		wasOn = buf.readBoolean();
		heat = buf.readInt();
		progress = buf.readInt();
		tank.deserialize(buf);
	}
	
	public boolean canSmelt() {
		if(slots[0] == null) return false;
		Pair<ItemStack, FluidStack> pair = CombinationRecipes.getOutput(slots[0]);
		
		if(pair == null) return false;
		
		ItemStack out = pair.getKey();
		FluidStack fluid = pair.getValue();
		
		if(out != null) {
			if(slots[1] != null) {
				if(!out.isItemEqual(slots[1])) return false;
				if(out.stackSize + slots[1].stackSize > slots[1].getMaxStackSize()) return false;
			}
		}
		
		if(fluid != null) {
			if(tank.getTankType() != fluid.type && tank.getFill() > 0) return false;
			if(tank.getTankType() == fluid.type && tank.getFill()  + fluid.fill > tank.getMaxFill()) return false;
		}
		
		return true;
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
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && CombinationRecipes.getOutput(itemStack) != null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
		this.progress = nbt.getInteger("prog");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setInteger("prog", progress);
		nbt.setInteger("heat", heat);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceCombo(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceCombo(player.inventory, this);
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
					yCoord + 2.125,
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

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tank, smoke, smoke_leaded, smoke_poison};
	}
}
