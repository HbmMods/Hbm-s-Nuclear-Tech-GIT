package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerITER;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
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
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityITER extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IGUIProvider, IInfoProviderEC, SimpleComponent, CompatHandler.OCComponent {
	
	public long power;
	public static final long maxPower = 10000000;
	public static final int powerReq = 100000;
	public FluidTank[] tanks;
	public FluidTank plasma;
	
	public int progress;
	public static final int duration = 100;
	public long totalRuntime;
	
	@SideOnly(Side.CLIENT)
	public int blanket;
	
	public float rotor;
	public float lastRotor;
	public boolean isOn;

	private float rotorSpeed = 0F;

	private AudioWrapper audio;

	public TileEntityITER() {
		super(5);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WATER, 1280000);
		tanks[1] = new FluidTank(Fluids.ULTRAHOTSTEAM, 128000);
		plasma = new FluidTank(Fluids.PLASMA_DT, 16000);
	}

	@Override
	public String getName() {
		return "container.machineITER";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			/// START Processing part ///
			
			if(!isOn) {
				plasma.setFill(0);	//jettison plasma if the thing is turned off
			}
			
			//explode either if there's plasma that is too hot or if the reactor is turned on but the magnets have no power
			if(plasma.getFill() > 0 && (this.plasma.getTankType().temperature >= this.getShield() || (this.isOn && this.power < this.powerReq))) {
				this.explode();
			}
			
			if(isOn && power >= powerReq) {
				power -= powerReq;
				
				if(plasma.getFill() > 0) {
					this.totalRuntime++;
					int delay = FusionRecipes.getByproductDelay(plasma.getTankType());
					if(delay > 0 && totalRuntime % delay == 0) produceByproduct();
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
			
			for(DirPos pos : getConPos()) {
				if(tanks[1].getFill() > 0) {
					this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setLong("power", power);
			data.setInteger("progress", progress);
			tanks[0].writeToNBT(data, "t0");
			tanks[1].writeToNBT(data, "t1");
			plasma.writeToNBT(data, "t2");
			
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
			this.rotor += this.rotorSpeed;
				
			if(this.rotor >= 360) {
				this.rotor -= 360;
				this.lastRotor -= 360;
			}
			
			if(this.isOn && this.power >= powerReq) {
				this.rotorSpeed = Math.max(0F, Math.min(15F, this.rotorSpeed + 0.05F));

				if(audio == null) {
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.fusionReactorRunning", xCoord, yCoord, zCoord, 1.0F, 30F, 1.0F);
					audio.startSound();
				}

				float rotorSpeed = this.rotorSpeed / 15F;
				audio.updateVolume(getVolume(0.5f * rotorSpeed));
				audio.updatePitch(0.25F + 0.75F * rotorSpeed);
			} else {
				this.rotorSpeed = Math.max(0F, Math.min(15F, this.rotorSpeed - 0.1F));
				
				if(audio != null) {
					if(this.rotorSpeed > 0) {
						float rotorSpeed = this.rotorSpeed / 15F;
						audio.updateVolume(getVolume(0.5f * rotorSpeed));
						audio.updatePitch(0.25F + 0.75F * rotorSpeed);
					} else {
						audio.stopSound();
						audio = null;
					}
				}
			}
		}
	}
	
	protected List<DirPos> connections;
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
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
		super.networkUnpack(data);
		
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.blanket = data.getInteger("blanket");
		this.progress = data.getInteger("progress"); //
		tanks[0].readFromNBT(data, "t0");
		tanks[1].readFromNBT(data, "t1");
		plasma.readFromNBT(data, "t2");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		if(meta == 0) this.isOn = !this.isOn;
	}

	public long getPowerScaled(long i) { return (power * i) / maxPower; }
	public long getProgressScaled(long i) { return (progress * i) / duration; }
	@Override public void setPower(long i) { this.power = i; }
	@Override public long getPower() { return power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.totalRuntime = nbt.getLong("totalRuntime");

		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		plasma.readFromNBT(nbt, "plasma");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", isOn);
		nbt.setLong("totalRuntime", this.totalRuntime);

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
					case 1: worldObj.setBlock(xCoord - width + x, yCoord + y - 2, zCoord - width + z, ModBlocks.fusion_conductor, 1, 3); break;
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
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
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
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.isOn && plasma.getFill() > 0);
		int output = FusionRecipes.getSteamProduction(plasma.getTankType());
		data.setDouble("consumption", output * 10);
		data.setDouble("outputmb", output);
	}


	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_fusion";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {isOn};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		isOn = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluid(Context context, Arguments args) {
		return new Object[] {
				tanks[0].getFill(), tanks[0].getMaxFill(),
				tanks[1].getFill(), tanks[1].getMaxFill(),
				plasma.getFill(), plasma.getMaxFill(), plasma.getTankType().getUnlocalizedName()
		};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPlasmaTemp(Context context, Arguments args) {
		return new Object[] {plasma.getTankType().temperature};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxTemp(Context context, Arguments args) {
		if (slots[3] != null && (slots[3].getItem() instanceof ItemFusionShield))
			return new Object[] {((ItemFusionShield) slots[3].getItem()).maxTemp};
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getBlanketDamage(Context context, Arguments args) {
		if (slots[3] != null && (slots[3].getItem() instanceof ItemFusionShield))
			return new Object[]{ItemFusionShield.getShieldDamage(slots[3]), ((ItemFusionShield)slots[3].getItem()).maxDamage};
		return new Object[] {"N/A", "N/A"};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getEnergyInfo",
				"isActive",
				"setActive",
				"getFluid",
				"getPlasmaTemp",
				"getMaxTemp",
				"getBlanketDamage"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch (method) {
			case ("getEnergyInfo"):
				return getEnergyInfo(context, args);
			case ("isActive"):
				return isActive(context, args);
			case ("setActive"):
				return setActive(context, args);
			case ("getFluid"):
				return getFluid(context, args);
			case ("getPlasmaTemp"):
				return getPlasmaTemp(context, args);
			case ("getMaxTemp"):
				return getMaxTemp(context, args);
			case ("getBlanketDamage"):
				return getBlanketDamage(context, args);
		}
		throw new NoSuchMethodException();
	}
}
