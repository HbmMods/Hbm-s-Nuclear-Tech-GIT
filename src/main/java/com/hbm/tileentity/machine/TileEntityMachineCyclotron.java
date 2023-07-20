package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.inventory.recipes.CyclotronRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineCyclotron extends TileEntityMachineBase implements IFluidSource, IFluidAcceptor, IEnergyUser, IFluidStandardTransceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 100000000;
	public int consumption = 1000000;
	
	public boolean isOn;
	
	private int age;
	private int countdown;
	
	private byte plugs; 
	
	public int progress;
	public static final int duration = 690;
	
	public FluidTank coolant;
	public FluidTank amat;
	
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityMachineCyclotron() {
		super(16);

		coolant = new FluidTank(Fluids.COOLANT, 32000, 0);
		amat = new FluidTank(Fluids.AMAT, 8000, 1);
	}

	@Override
	public String getName() {
		return "container.cyclotron";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();

			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				fillFluidInit(amat.getTankType());

			this.power = Library.chargeTEFromItems(slots, 13, power, maxPower);
			this.coolant.loadTank(11, 12, slots);
			this.amat.unloadTank(9, 10, slots);
			
			if(isOn) {
				
				int defConsumption = consumption - 100000 * getConsumption();
				
				if(canProcess() && power >= defConsumption) {
					
					progress += this.getSpeed();
					power -= defConsumption;
					
					if(progress >= duration) {
						process();
						progress = 0;
						this.markDirty();
					}
					
					if(coolant.getFill() > 0) {

			    		countdown = 0;
						
						if(worldObj.rand.nextInt(3) == 0)
							coolant.setFill(coolant.getFill() - 1);
						
					} else if(worldObj.rand.nextInt(this.getSafety()) == 0) {
						
						countdown++;
						
						int chance = 7 - Math.min((int) Math.ceil(countdown / 200D), 6);
						
						if(worldObj.rand.nextInt(chance) == 0)
							ExplosionLarge.spawnTracers(worldObj, xCoord + 0.5, yCoord + 3.25, zCoord + 0.5, 1);
						
						if(countdown > 1000) {
							ExplosionThermo.setEntitiesOnFire(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 25);
							ExplosionThermo.scorchLight(worldObj, xCoord, yCoord, zCoord, 7);
							
							if(countdown % 4 == 0)
								ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord + 3.25, zCoord + 0.5, 18, 1);
							
						} else if(countdown > 600) {
							ExplosionThermo.setEntitiesOnFire(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 10);
						}
						
						if(countdown == 1140)
							worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "hbm:block.shutdown", 10.0F, 1.0F);
						
						if(countdown > 1200)
							explode();
					}
					
				} else {
					progress = 0;
				}
				
			} else {
				progress = 0;
			}
			
			this.sendFluid();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isOn", isOn);
			data.setByte("plugs", plugs);
			this.networkPack(data, 25);
			
			coolant.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			amat.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		}
	}
	
	private void updateConnections()  {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(coolant.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void sendFluid() {
		for(DirPos pos : getConPos()) {
			this.sendFluid(amat, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 3, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 3, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 3, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 3, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 3, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 3, Library.NEG_Z)
		};
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.plugs = data.getByte("plugs");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		this.isOn = !this.isOn;
	}
	
	private void explode() {

		ExplosionLarge.explodeFire(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 25, true, false, true);
		
		int rand = worldObj.rand.nextInt(10);

		if(rand < 2) {
			
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, (int)(BombConfig.fatmanRadius * 1.5), xCoord + 0.5, yCoord + 1.5, zCoord + 0.5).mute());
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 250));
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
			
		} else if(rand < 4) {
			
			EntityBalefire bf = new EntityBalefire(worldObj).mute();
			bf.posX = xCoord + 0.5;
			bf.posY = yCoord + 1.5;
			bf.posZ = zCoord + 0.5;
			bf.destructionRange = (int)(BombConfig.fatmanRadius * 1.5);
			worldObj.spawnEntityInWorld(bf);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			data.setBoolean("balefire", true);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 250));
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
			
		} else if(rand < 5) {
			
			EntityBlackHole bl = new EntityBlackHole(worldObj, 1.5F + worldObj.rand.nextFloat());
			bl.posX = xCoord + 0.5F;
			bl.posY = yCoord + 1.5F;
			bl.posZ = zCoord + 0.5F;
			worldObj.spawnEntityInWorld(bl);
		}
	}
	
	public boolean canProcess() {
		
		for(int i = 0; i < 3; i++) {
			
			Object[] res = CyclotronRecipes.getOutput(slots[i + 3], slots[i]);
			
			if(res == null)
				continue;
			
			ItemStack out = (ItemStack)res[0];
			
			if(out == null)
				continue;
			
			if(slots[i + 6] == null)
				return true;
			
			if(slots[i + 6].getItem() == out.getItem() && slots[i + 6].getItemDamage() == out.getItemDamage() && slots[i + 6].stackSize < out.getMaxStackSize())
				return true;
		}
		
		return false;
	}
	
	public void process() {
		
		for(int i = 0; i < 3; i++) {
			
			Object[] res = CyclotronRecipes.getOutput(slots[i + 3], slots[i]);
			
			if(res == null)
				continue;
			
			ItemStack out = (ItemStack)res[0];
			
			if(out == null)
				continue;
			
			if(slots[i + 6] == null) {
				
				this.decrStackSize(i, 1);
				this.decrStackSize(i + 3, 1);
				slots[i + 6] = out;
				
				this.amat.setFill(this.amat.getFill() + (Integer)res[1]);
				
				continue;
			}
			
			if(slots[i + 6].getItem() == out.getItem() && slots[i + 6].getItemDamage() == out.getItemDamage() && slots[i + 6].stackSize < out.getMaxStackSize()) {
				
				this.decrStackSize(i, 1);
				this.decrStackSize(i + 3, 1);
				slots[i + 6].stackSize++;
				
				this.amat.setFill(this.amat.getFill() + (Integer)res[1]);
			}
		}
		
		if(this.amat.getFill() > this.amat.getMaxFill())
			this.amat.setFill(this.amat.getMaxFill());
	}
	
	public int getSpeed() {
		
		int speed = 1;
		
		for(int i = 14; i < 16; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_speed_1)
					speed += 1;
				else if(slots[i].getItem() == ModItems.upgrade_speed_2)
					speed += 2;
				else if(slots[i].getItem() == ModItems.upgrade_speed_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 4);
	}
	
	public int getConsumption() {
		
		int speed = 0;
		
		for(int i = 14; i < 16; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_power_1)
					speed += 1;
				else if(slots[i].getItem() == ModItems.upgrade_power_2)
					speed += 2;
				else if(slots[i].getItem() == ModItems.upgrade_power_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 3);
	}
	
	public int getSafety() {
		
		int speed = 1;
		
		for(int i = 14; i < 16; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_effect_1)
					speed += 1;
				else if(slots[i].getItem() == ModItems.upgrade_effect_2)
					speed += 2;
				else if(slots[i].getItem() == ModItems.upgrade_effect_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 4);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / duration;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		
		if(index == 0)
			coolant.setFill(fill);
		else if(index == 1)
			amat.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == Fluids.COOLANT)
			coolant.setFill(fill);
		else if(type == Fluids.AMAT)
			amat.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index == 0)
			coolant.setTankType(type);
		else if(index == 1)
			amat.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == Fluids.COOLANT)
			return coolant.getFill();
		else if(type == Fluids.AMAT)
			return amat.getFill();
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(xCoord + 3, yCoord, zCoord + 1, getTact(), type);
		fillFluid(xCoord + 3, yCoord, zCoord - 1, getTact(), type);
		fillFluid(xCoord - 3, yCoord, zCoord + 1, getTact(), type);
		fillFluid(xCoord - 3, yCoord, zCoord - 1, getTact(), type);

		fillFluid(xCoord + 1, yCoord, zCoord + 3, getTact(), type);
		fillFluid(xCoord - 1, yCoord, zCoord + 3, getTact(), type);
		fillFluid(xCoord + 1, yCoord, zCoord - 3, getTact(), type);
		fillFluid(xCoord - 1, yCoord, zCoord - 3, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return age >= 0 && age < 10;
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
		
		if(type == Fluids.COOLANT)
			return coolant.getMaxFill();
		
		return 0;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 4, zCoord + 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		coolant.readFromNBT(nbt, "coolant");
		amat.readFromNBT(nbt, "amat");
		
		this.isOn = nbt.getBoolean("isOn");
		this.countdown = nbt.getInteger("countdown");
		this.progress = nbt.getInteger("progress");
		this.power = nbt.getLong("power");
		this.plugs = nbt.getByte("plugs");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		coolant.writeToNBT(nbt, "coolant");
		amat.writeToNBT(nbt, "amat");
		
		nbt.setBoolean("isOn", isOn);
		nbt.setInteger("countdown", countdown);
		nbt.setInteger("progress", progress);
		nbt.setLong("power", power);
		nbt.setByte("plugs", plugs);
	}
	
	public void setPlug(int index) {
		this.plugs |= (1 << index);
		this.markDirty();
	}
	
	public boolean getPlug(int index) {
		return (this.plugs & (1 << index)) > 0;
	}
	
	public static Item getItemForPlug(int i) {
		
		switch(i) {
		case 0: return ModItems.powder_balefire;
		case 1: return ModItems.book_of_;
		case 2: return ModItems.diamond_gavel;
		case 3: return ModItems.coin_maskman;
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 14 && i <= 15 && stack.getItem() instanceof ItemMachineUpgrade)
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.5F, 1.0F);
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { amat };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { coolant };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { amat, coolant };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCyclotron(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCyclotron(player.inventory, this);
	}
}
