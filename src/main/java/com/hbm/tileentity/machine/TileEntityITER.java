package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerITER;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.gui.GUIITER;
import com.hbm.inventory.recipes.BreederRecipes;
import com.hbm.inventory.recipes.BreederRecipes.BreederRecipe;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemFusionShield;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
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
import net.minecraft.item.Item;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityITER extends TileEntityMachineBase implements IEnergyUser, IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, IGUIProvider /* TODO: finish fluid API impl */ {
	
	public long power;
	public static final long maxPower = 10000000;
	public static final int powerReq = 100000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank[] tanks;
	public FluidTank plasma;
	public static final int CoolReq = 1;
	
	public int progress;
	public static final int duration = 100;
	EntityPlayer player;
	@SideOnly(Side.CLIENT)
	public int blanket;
	
	public float rotor;
	public float lastRotor;
	public boolean isOn;

	public TileEntityITER() {
		super(6);
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(Fluids.WATER, 1280000, 0);
		tanks[1] = new FluidTank(Fluids.ULTRAHOTSTEAM, 128000, 1);
		tanks[2] = new FluidTank(Fluids.COOLANT, 16_000, 0);
		tanks[3] = new FluidTank(Fluids.COOLANT_HOT, 16_000, 1);
		plasma = new FluidTank(Fluids.PLASMA_DT, 16000, 2);
		
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
			tanks[2].setType(5, slots);
			tanks[2].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			tanks[3].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			if (age == 9 || age == 19)
				this.tanks[1].getTankType();
				this.tanks[2].getTankType();
			this.updateConnections();
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			if(tanks[2].getTankType().hasTrait(FT_Heatable.class)) {
				FT_Heatable trait = tanks[2].getTankType().getTrait(FT_Heatable.class);
				HeatingStep step = trait.getFirstStep();
				tanks[3].setTankType(step.typeProduced);
			}
			else {
				tanks[2].setTankType(Fluids.NONE);
				tanks[3].setTankType(Fluids.NONE);
			}
			/// START Processing part ///
			
			if(!isOn) {
				plasma.setFill(0);	//jettison plasma if the thing is turned off
			}
			
			//explode either if there's plasma that is too hot or if the reactor is turned on but the magnets have no power
			if(plasma.getFill() > 0 && (this.plasma.getTankType().temperature >= this.getShield() || (this.isOn && this.power < this.powerReq || tanks[2].getFill() == 0 || tanks[3].getFill() == tanks[3].getMaxFill()))) {
				this.explode();
			}
			
			if(isOn && power >= powerReq) {
				power -= powerReq;
				
				if(plasma.getFill() > 0) {
					
					int chance = FusionRecipes.getByproductChance(plasma.getTankType());
					
					if(chance > 0 && worldObj.rand.nextInt(chance) == 0)
						produceByproduct();
				}
				
				if(plasma.getFill() > 0 && this.getShield() != 0) {
					
					ItemFusionShield.setShieldDamage(slots[3], ItemFusionShield.getShieldDamage(slots[3]) + 1);
					
					if(ItemFusionShield.getShieldDamage(slots[3]) > ((ItemFusionShield)slots[3].getItem()).maxDamage) {
						slots[3] = null;
						worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.shutdown", 5F, 1F);
						this.isOn = false;
						this.markDirty();
					}
				}
				
				int prod = FusionRecipes.getSteamProduction(plasma.getTankType());
				int lod = FusionRecipes.getCoolant(plasma.getTankType());
				double coolantTemperatureRate = FusionRecipes.coolprod.get(plasma.getTankType());
				FT_Heatable trait = tanks[2].getTankType().getTrait(FT_Heatable.class);
				//int temp = tanks[2].getTankType().temperature;
				//coolantTemperatureRate = trait.getEfficiency(HeatingType.HEATEXCHANGER);
				for(int i = 0; i < 20; i++) {
					
					if(plasma.getFill() > 0) {
						
						if(tanks[0].getFill() >= prod * 10) {
							tanks[0].setFill(tanks[0].getFill() - prod * 10);
							tanks[1].setFill(tanks[1].getFill() + prod);

						}
						if(tanks[1].getFill() > tanks[1].getMaxFill()) {
							tanks[1].setFill(tanks[1].getMaxFill());
							tanks[0].setFill(tanks[0].getMaxFill()); //this should stop it from eating fluids when buffers are full
						}
							
						if(tanks[2].getFill() >= lod) {
							int coolantToDrain = (int) (Math.min(tanks[3].getMaxFill(), tanks[2].getFill()));
							coolantToDrain = Math.min(lod, tanks[1].getMaxFill() - tanks[1].getFill());
							tanks[2].setFill(tanks[2].getFill() - coolantToDrain);
							tanks[3].setFill(tanks[3].getFill() + coolantToDrain);
								
						}
						if(tanks[3].getFill() > tanks[3].getMaxFill()) {
							tanks[3].setFill(tanks[3].getMaxFill());
							tanks[2].setFill(tanks[2].getMaxFill());
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
			
			for(DirPos pos : getConPos()) {
				if(tanks[1].getFill() > 0) {
					this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
				if(tanks[3].getFill() > 0) {
					this.sendFluid(tanks[3], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setShort("type", (short)tanks[2].getTankType().getID());
			data.setShort("hottype", (short)tanks[3].getTankType().getID());
			tanks[0].writeToNBT(data, "water");
			tanks[1].writeToNBT(data, "steam");
			tanks[2].writeToNBT(data, "coolant");
			tanks[3].writeToNBT(data, "hotlant");
			plasma.writeToNBT(data, "plasma");
			
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
	

	protected List<DirPos> connections;
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[2].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected List<DirPos> getConPos() {
		if(connections != null && !connections.isEmpty())
			return connections;
		
		connections = new ArrayList();

		connections.add(new DirPos(xCoord, yCoord + 3, zCoord, ForgeDirection.UP));
		connections.add(new DirPos(xCoord, yCoord - 3, zCoord, ForgeDirection.DOWN));
		
		Vec3 vec = Vec3.createVectorHelper(5.75, 0, 0);
		
		for(int i = 0; i < 16; i++) {
			vec.rotateAroundY((float) (Math.PI / 8));
			connections.add(new DirPos(xCoord + (int)vec.xCoord, yCoord + 3, zCoord + (int)vec.zCoord, ForgeDirection.UP));
			connections.add(new DirPos(xCoord + (int)vec.xCoord, yCoord - 3, zCoord + (int)vec.zCoord, ForgeDirection.DOWN));
		}
		
		return connections;
	}
	
	private void explode() {
		this.disassemble();
		
		if(this.plasma.getTankType() == Fluids.PLASMA_BF) {
			
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
			ExplosionLarge.spawnShrapnels(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 50);
			
			ExplosionNT exp = new ExplosionNT(worldObj, null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 20F)
					.addAttrib(ExAttrib.BALEFIRE)
					.addAttrib(ExAttrib.NOPARTICLE)
					.addAttrib(ExAttrib.NOSOUND)
					.addAttrib(ExAttrib.NODROP)
					.overrideResolution(64);
			exp.doExplosionA();
			exp.doExplosionB(false);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			data.setBoolean("balefire", true);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			
		} else {
			Vec3 vec = Vec3.createVectorHelper(5.5, 0, 0);
			vec.rotateAroundY(worldObj.rand.nextFloat() * (float)Math.PI * 2F);
			
			worldObj.newExplosion(null, xCoord + 0.5 + vec.xCoord, yCoord + 0.5 + worldObj.rand.nextGaussian() * 1.5D, zCoord + 0.5 + vec.zCoord, 2.5F, true, true);
		}
		
	}

	private void doBreederStuff() {
		
		if(plasma.getFill() == 0) {
			this.progress = 0;
			return;
		}
		
		BreederRecipe out = BreederRecipes.getOutput(slots[1]);
		
		if(slots[1] != null && slots[1].getItem() == ModItems.meteorite_sword_irradiated)
			out = new BreederRecipe(ModItems.meteorite_sword_fused, 1000);
		
		if(slots[1] != null && slots[1].getItem() == ModItems.meteorite_sword_fused)
			out = new BreederRecipe(ModItems.meteorite_sword_baleful, 4000);
	
		if(slots[1] != null && slots[1].getItem() == Item.getItemFromBlock(ModBlocks.lattice_log))
			out = new BreederRecipe(ModItems.woodemium_briquette, 4000);
		
		if(out == null) {
			this.progress = 0;
			return;
		}
		
		if(slots[2] != null && slots[2].stackSize >= slots[2].getMaxStackSize()) {
			this.progress = 0;
			return;
		}
		
		int level = FusionRecipes.getBreedingLevel(plasma.getTankType());
		
		if(out.flux > level) {
			this.progress = 0;
			return;
		}
		
		progress++;
		
		if(progress > this.duration) {
			
			this.progress = 0;
			
			if(slots[2] != null) {
				slots[2].stackSize++;
			} else {
				slots[2] = out.output.copy();
			}
			
			slots[1].stackSize--;
			
			if(slots[1].stackSize <= 0)
				slots[1] = null;
			
			this.markDirty();
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 1, 2, 4 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i == 1 && BreederRecipes.getOutput(itemStack) != null)
			return true;
		
		return false;
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
			return 0;
		
		return ((ItemFusionShield)slots[3].getItem()).maxTemp;
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.blanket = data.getInteger("blanket");
		this.progress = data.getInteger("progress"); //
		tanks[0].readFromNBT(data, "water");
		tanks[1].readFromNBT(data, "steam");
		tanks[2].readFromNBT(data, "coolant");
		tanks[3].readFromNBT(data, "hotlant");
		plasma.readFromNBT(data, "plasma");
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

	public void setFillForSync(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
		
		if(index == 2)
			plasma.setFill(fill);
		tanks[0].setFill(fill);
		tanks[1].setFill(fill);
		tanks[2].setFill(fill);
		tanks[3].setFill(fill);
	}
	/*
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if (type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
		else if (type.name().equals(tanks[3].getTankType().name()))
			tanks[3].setFill(i);
		else if (type.name().equals(plasma.getTankType().name()))
			plasma.setFill(i);
			
	}
	*/
	

	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
		
		if(index == 2)
			plasma.setTankType(type);
		tanks[0].setTankType(type);
		tanks[1].setTankType(type);
		tanks[2].setTankType(type);
		tanks[3].setTankType(type);
	}
	/*
	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if (type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else if (type.name().equals(tanks[3].getTankType().name()))
			return tanks[3].getFill();
		else if (type.name().equals(plasma.getTankType().name()))
			return plasma.getFill();
		else
			return 0;
	}
	*/
/*
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
		else if (type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getMaxFill();
		else if (type.name().equals(tanks[3].getTankType().name()))
			return tanks[3].getMaxFill();
		else if (type.name().equals(plasma.getTankType().name()))
			return plasma.getMaxFill();
		else
			return 0;
	}
	*/
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");

		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		tanks[2].readFromNBT(nbt, "coolant");
		tanks[3].readFromNBT(nbt, "hotlant");
		plasma.readFromNBT(nbt, "plasma");
	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", isOn);

		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
		tanks[2].writeToNBT(nbt, "coolant");
		tanks[3].writeToNBT(nbt, "hotlant");
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
		
		MachineITER.drop = false;
		
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
		
		MachineITER.drop = true;
		
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class,
				AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(50, 10, 50));
		
		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.achMeltdown);
		}
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1], tanks[3]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0], tanks[2]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerITER(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIITER(player.inventory, this);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFluidFill(FluidType type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearFluidList(FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		// TODO Auto-generated method stub
		return 0;
	}
}
