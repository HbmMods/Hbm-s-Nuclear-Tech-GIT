package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.BreederRecipes.BreederRecipe;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.FusionRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemFusionShield;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class TileEntityITER extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, IFluidSource {
	
	public long power;
	public static final long maxPower = 100000000;
	public static final int powerReq = 1000000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank[] tanks;
	public FluidTank plasma;
	
	public int progress;
	public static final int duration = 100;
	
	@SideOnly(Side.CLIENT)
	public int blanket;
	
	public float rotor;
	public float lastRotor;
	public boolean isOn;

	public TileEntityITER() {
		super(5);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.WATER, 128000, 0);
		tanks[1] = new FluidTank(FluidType.ULTRAHOTSTEAM, 128000, 1);
		plasma = new FluidTank(FluidType.PLASMA_DT, 16000, 2);
	}

	@Override
	public String getName() {
		return "container.machineITER";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				fillFluidInit(tanks[1].getTankType());
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			/// START Processing part ///
			
			if(!isOn) {
				plasma.setFill(0);	//jettison plasma if the thing is turned off
			}
			
			//explode either if there's plasma that is too hot or if the reactor is turned on but the magnets have no power
			if(plasma.getFill() > 0 && (this.plasma.getTankType().temperature >= this.getShield() || (this.isOn && this.power < this.powerReq))) {
				this.disassemble();
				Vec3 vec = Vec3.createVectorHelper(5.5, 0, 0);
				vec.rotateAroundY(worldObj.rand.nextFloat() * (float)Math.PI * 2F);
				
				worldObj.newExplosion(null, xCoord + 0.5 + vec.xCoord, yCoord + 0.5 + worldObj.rand.nextGaussian() * 1.5D, zCoord + 0.5 + vec.zCoord, 2.5F, true, true);
			}
			
			if(isOn && power >= powerReq) {
				power -= powerReq;
				
				if(plasma.getFill() > 0) {
					
					int chance = FusionRecipes.getByproductChance(plasma.getTankType());
					
					if(chance > 0 && worldObj.rand.nextInt() == 0)
						produceByproduct();
				}
				
				if(plasma.getFill() > 0 && this.getShield() != 0) {
					
					ItemFusionShield.setShieldDamage(slots[3], ItemFusionShield.getShieldDamage(slots[3]) + 1);
					
					if(ItemFusionShield.getShieldDamage(slots[3]) > ((ItemFusionShield)slots[3].getItem()).maxDamage)
						slots[3] = null;
				}
				
				int prod = FusionRecipes.getSteamProduction(plasma.getTankType());
				
				for(int i = 0; i < 20; i++) {
					
					if(plasma.getFill() > 0) {
						
						if(tanks[0].getFill() >= prod * 10) {
							tanks[0].setFill(tanks[0].getFill() - prod * 10);
							tanks[1].setFill(tanks[1].getFill() + prod);
							
							if(tanks[1].getFill() > tanks[1].getMaxFill())
								tanks[1].setFill(tanks[1].getMaxFill());
						}
						
						plasma.setFill(plasma.getFill() - 1);
					}
				}
			}
			
			doBreederStuff();
			
			/// END Processing part ///

			/// START Notif packets ///
			for(int i = 0; i < tanks.length; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			plasma.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setLong("power", power);
			data.setInteger("progress", progress);
			
			if(slots[3] == null) {
				data.setInteger("blanket", 0);
			} else if(slots[3].getItem() == ModItems.fusion_shield_tungsten) {
				data.setInteger("blanket", 1);
			} else if(slots[3].getItem() == ModItems.fusion_shield_desh) {
				data.setInteger("blanket", 2);
			} else if(slots[3].getItem() == ModItems.fusion_shield_chlorophyte) {
				data.setInteger("blanket", 3);
			} else if(slots[3].getItem() == ModItems.fusion_shield_vaporwave) {
				data.setInteger("blanket", 4);
			}
			
			this.networkPack(data, 250);
			/// END Notif packets ///
			
		} else {
			
			this.lastRotor = this.rotor;
			
			if(this.isOn && this.power >= this.powerReq) {
				
				this.rotor += 15F;
				
				if(this.rotor >= 360) {
					this.rotor -= 360;
					this.lastRotor -= 360;
				}
			}
		}
	}
	
	private void doBreederStuff() {
		
		if(plasma.getFill() == 0) {
			this.progress = 0;
			return;
		}
		
		BreederRecipe out = BreederRecipes.getOutput(slots[1]);
		
		if(out == null) {
			this.progress = 0;
			return;
		}
		
		if(slots[2] != null && slots[2].stackSize >= slots[2].getMaxStackSize()) {
			this.progress = 0;
			return;
		}
		
		int level = FusionRecipes.getBreedingLevel(plasma.getTankType());
		
		if(out.heat > level) {
			this.progress = 0;
			return;
		}
		
		progress++;
		
		if(progress > this.duration) {
			
			if(slots[2] != null) {
				slots[2].stackSize++;
			} else {
				slots[2] = out.output.copy();
			}
			
			slots[1].stackSize--;
			
			if(slots[1].stackSize <=0)
				slots[1] = null;
			
			this.markDirty();
		}
	}
	
	private void produceByproduct() {
		
		ItemStack by = FusionRecipes.getByproduct(plasma.getTankType());
		
		if(by == null)
			return;
		
		if(slots[4] == null) {
			slots[4] = by;
			return;
		}
		
		if(slots[4].getItem() == by.getItem() && slots[4].getItemDamage() == by.getItemDamage() && slots[4].stackSize < slots[4].getMaxStackSize()) {
			slots[4].stackSize++;
		}
	}
	
	public int getShield() {
		
		if(slots[3] == null || !(slots[3].getItem() instanceof ItemFusionShield))
			return 273;
		
		return ((ItemFusionShield)slots[3].getItem()).maxTemp;
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.blanket = data.getInteger("blanket");
		this.progress = data.getInteger("progress"); //
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0) {
			this.isOn = !this.isOn;
		}
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public long getProgressScaled(long i) {
		return (progress * i) / duration;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
		
		if(index == 2)
			plasma.setFill(fill);
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if (type.name().equals(plasma.getTankType().name()))
			plasma.setFill(i);
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
		
		if(index == 2)
			plasma.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		list.add(plasma);
		
		return list;
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if (type.name().equals(plasma.getTankType().name()))
			return plasma.getFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(xCoord, yCoord - 3, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord + 3, zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else if (type.name().equals(plasma.getTankType().name()))
			return plasma.getMaxFill();
		else
			return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");

		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		plasma.readFromNBT(nbt, "plasma");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", isOn);

		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
		plasma.writeToNBT(nbt, "plasma");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 8,
					yCoord + 0.5 - 3,
					zCoord + 0.5 - 8,
					xCoord + 0.5 + 8,
					yCoord + 0.5 + 3,
					zCoord + 0.5 + 8
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public void disassemble() {
		
		int[][][] layout = TileEntityITERStruct.layout;
		
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < layout[0].length; x++) {
				for(int z = 0; z < layout[0][0].length; z++) {
					
					int ly = y > 2 ? 4 - y : y;
					
					int width = 7;
					
					if(x == width && y == 0 && z == width)
						continue;
					
					int b = layout[ly][x][z];
					
					switch(b) {
					case 1: worldObj.setBlock(xCoord - width + x, yCoord + y - 2, zCoord - width + z, ModBlocks.fusion_conductor); break;
					case 2: worldObj.setBlock(xCoord - width + x, yCoord + y - 2, zCoord - width + z, ModBlocks.fusion_center); break;
					case 3: worldObj.setBlock(xCoord - width + x, yCoord + y - 2, zCoord - width + z, ModBlocks.fusion_motor); break;
					case 4: worldObj.setBlock(xCoord - width + x, yCoord + y - 2, zCoord - width + z, ModBlocks.reinforced_glass); break;
					}
				}
			}
		}
		
		worldObj.setBlock(xCoord, yCoord - 2, zCoord, ModBlocks.struct_iter_core);
	}
}
