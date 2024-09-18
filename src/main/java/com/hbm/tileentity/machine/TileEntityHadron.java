package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockHadronCoil;
import com.hbm.blocks.machine.BlockHadronPlating;
import com.hbm.inventory.container.ContainerHadron;
import com.hbm.inventory.gui.GUIHadron;
import com.hbm.inventory.recipes.HadronRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityHadronDiode.DiodeConfig;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadron extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider {
	
	public long power;
	public static final long maxPower = 10000000;
	
	public boolean isOn = false;
	public boolean analysisOnly = false;
	public int ioMode = 0;
	public static final int MODE_DEFAULT = 0;
	public static final int MODE_HOPPER = 1;
	public static final int MODE_SINGLE = 2;
	
	private int delay;
	public EnumHadronState state = EnumHadronState.IDLE;
	private static final int delaySuccess = 20;
	private static final int delayNoResult = 60;
	private static final int delayError = 100;
	
	public boolean stat_success = false;
	public EnumHadronState stat_state = EnumHadronState.IDLE;
	public int stat_charge = 0;
	public int stat_x = 0;
	public int stat_y = 0;
	public int stat_z = 0;
	
	public TileEntityHadron() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.hadron";
	}
	
	private static final int[] access = new int[] {0, 1, 2, 3};
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return access;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2 || i == 3;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i != 0 && i != 1) return false;
		
		if(this.ioMode == MODE_SINGLE) {
			return slots[i] == null;
		}
		
		//makes sure that equal items like the antimatter capsules are spread out evenly
		if(slots[0] != null && slots[1] != null && slots[0].getItem() == slots[1].getItem() && slots[0].getItemDamage() == slots[1].getItemDamage()) {
			if(i == 0) return slots[1].stackSize - slots[0].stackSize >= 0;
			if(i == 1) return slots[0].stackSize - slots[1].stackSize >= 0;
		}
		
		return true;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 4, power, maxPower);
			drawPower();

			particles.addAll(particlesToAdd);
			particlesToAdd.clear();
			
			if(delay <= 0 && this.isOn && particles.size() < maxParticles && slots[0] != null && slots[1] != null && power >= maxPower * 0.75) {
				
				if(ioMode != MODE_HOPPER || (slots[0].stackSize > 1 && slots[1].stackSize > 1)) {
					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
					particles.add(new Particle(slots[0], slots[1], dir, xCoord, yCoord, zCoord));
					this.decrStackSize(0, 1);
					this.decrStackSize(1, 1);
					power -= maxPower * 0.75;
					this.state = EnumHadronState.PROGRESS;
				}
			}
			
			if(delay > 0)
				delay--;
			else if(particles.isEmpty()) {
				this.state = EnumHadronState.IDLE;
			}
			
			if(!particles.isEmpty())
				updateParticles();
			
			for(Particle p : particlesToRemove) {
				particles.remove(p);
			}
			
			particlesToRemove.clear();

			//Sort the virtual particles by momentum, and run through them until we have enough momentum to complete the recipe
			//If we succeed, "collapse" the cheapest particle that had enough momentum
			//If we fail to make anything, "collapse" the most expensive particle
			if(particles.isEmpty() && !particlesCompleted.isEmpty()) {
				ItemStack[] result = null;
				Particle particle = null;

				particlesCompleted.sort((p1, p2) -> { return p1.momentum - p2.momentum; });
				for(Particle p : particlesCompleted) {
					particle = p;
					result = HadronRecipes.getOutput(p.item1, p.item2, p.momentum, analysisOnly);
					if(result != null) break;
				}

				process(particle, result);

				particlesCompleted.clear();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setLong("power", power);
			data.setBoolean("analysis", analysisOnly);
			data.setInteger("ioMode", ioMode);
			data.setByte("state", (byte) state.ordinal());
			
			data.setBoolean("stat_success", stat_success);
			data.setByte("stat_state", (byte) stat_state.ordinal());
			data.setInteger("stat_charge", stat_charge);
			data.setInteger("stat_x", stat_x);
			data.setInteger("stat_y", stat_y);
			data.setInteger("stat_z", stat_z);
			this.networkPack(data, 50);
		}
	}
	
	private void process(Particle p, ItemStack[] result) {
		//Collapse this particle to real by consuming power
		p.consumePower();
		
		if(result == null) {
			this.state = HadronRecipes.returnCode;
			this.setStats(this.state, p.momentum, false);
			this.delay = delayNoResult;
			worldObj.playSoundEffect(p.posX, p.posY, p.posZ, "random.orb", 2, 0.5F);
			return;
		}
		
		if((slots[2] == null || (slots[2].getItem() == result[0].getItem() && slots[2].stackSize < slots[2].getMaxStackSize())) &&
				(slots[3] == null || (slots[3].getItem() == result[1].getItem() && slots[3].stackSize < slots[3].getMaxStackSize()))) {
			
			for(int i = 2; i <= 3; i++) {
				if(slots[i] == null)
					slots[i] = result[i - 2].copy();
				else
					slots[i].stackSize++;
			}
			
			if(result[0].getItem() == ModItems.particle_digamma) {
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class,
						AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)
						.expand(128, 50, 128));
				
				for(EntityPlayer player : players)
					player.triggerAchievement(MainRegistry.achOmega12);
			}
		}
		
		worldObj.playSoundEffect(p.posX, p.posY, p.posZ, "random.orb", 2, 1F);
		this.delay = delaySuccess;
		this.state = EnumHadronState.SUCCESS;
		this.setStats(this.state, p.momentum, true);
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.analysisOnly = data.getBoolean("analysis");
		this.ioMode = data.getInteger("ioMode");
		this.state = EnumHadronState.values()[data.getByte("state")];

		this.stat_success = data.getBoolean("stat_success");
		this.stat_state = EnumHadronState.values()[data.getByte("stat_state")];
		this.stat_charge = data.getInteger("stat_charge");
		this.stat_x = data.getInteger("stat_x");
		this.stat_y = data.getInteger("stat_y");
		this.stat_z = data.getInteger("stat_z");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0)
			this.isOn = !this.isOn;
		if(meta == 1)
			this.analysisOnly = !this.analysisOnly;
		if(meta == 2) {
			this.ioMode++;
			if(ioMode > 2) ioMode = 0;
		}
		
		this.markChanged();
	}
	
	private void drawPower() {
		
		for(ForgeDirection dir : getRandomDirs()) {
			
			if(power == maxPower)
				return;

			int x = xCoord + dir.offsetX * 2;
			int y = yCoord + dir.offsetY * 2;
			int z = zCoord + dir.offsetZ * 2;
			
			TileEntity te = worldObj.getTileEntity(x, y, z);
			
			if(te instanceof TileEntityHadronPower) {
				
				TileEntityHadronPower plug = (TileEntityHadronPower)te;
				
				long toDraw = Math.min(maxPower - power, plug.getPower());
				this.setPower(power + toDraw);
				plug.setPower(plug.getPower() - toDraw);
			}
		}
	}
	
	private void finishParticle(Particle p) {
		particlesToRemove.add(p);
		
		if(!p.isExpired())
			particlesCompleted.add(p);
		
		p.expired = true;
	}
	
	static final int maxParticles = 1;
	List<Particle> particles = new ArrayList<Particle>();
	List<Particle> particlesToRemove = new ArrayList<Particle>();
	List<Particle> particlesToAdd = new ArrayList<Particle>();
	List<Particle> particlesCompleted = new ArrayList<Particle>();
	
	private void updateParticles() {
		for(Particle particle : particles) {
			particle.update();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.isOn = nbt.getBoolean("isOn");
		this.power = nbt.getLong("power");
		this.analysisOnly = nbt.getBoolean("analysis");
		this.ioMode = nbt.getInteger("ioMode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOn", isOn);
		nbt.setLong("power", power);
		nbt.setBoolean("analysis", analysisOnly);
		nbt.setInteger("ioMode", ioMode);
	}
	
	public int getPowerScaled(int i) {
		return (int)(power * i / maxPower);
	}

	@Override
	public void setPower(long i) {
		power = i;
		this.markDirty();
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
	public boolean canConnect(ForgeDirection dir) {
		return false;
	}
	
	private void setStats(EnumHadronState state, int count, boolean success) {
		this.stat_state = state;
		this.stat_charge = count;
		this.stat_success = success;
	}
	
	private void setExpireStats(EnumHadronState state, int count, int x, int y, int z) {
		this.stat_state = state;
		this.stat_charge = count;
		this.stat_x = x;
		this.stat_y = y;
		this.stat_z = z;
		this.stat_success = false;
	}
		
	public void expire(Particle particle, EnumHadronState reason) {
		if(particle.expired)
			return;
		
		particle.consumePower();
		for(Particle p : particles) {
			p.expired = true;
			particlesToRemove.add(p);
		}
		worldObj.newExplosion(null, particle.posX + 0.5, particle.posY + 0.5, particle.posZ + 0.5, 10, false, false);

		//If any particles expire, cancel any succeeding particles, since they'll confuse the player
		particlesCompleted.clear();

		state = reason;
		delay = delayError;
		setExpireStats(reason, particle.momentum, particle.posX, particle.posY, particle.posZ);
	}
	
	public class Particle {
		
		//Starting values
		ItemStack item1;
		ItemStack item2;
		ForgeDirection dir;
		int posX;
		int posY;
		int posZ;
		
		//Progressing values
		int momentum;
		int charge;
		int analysis;
		boolean isCheckExempt = false;
		int cl0 = 0;
		int cl1 = 0;
		
		boolean expired = false;
		boolean cloned = false;

		//Quantum mechanical ass particle
		//Virtual particles traverse the accelerator without consuming electrical power
		//The cheapest valid route to the analysis chamber is then turned into a real particle, consuming power
		List<TileEntityHadronPower> plugs = new ArrayList<TileEntityHadronPower>();

		//Quantum particles should only traverse a schottky direction ONCE
		//Keep a list of traversed diodes and directions
		HashMap<TileEntityHadronDiode, List<ForgeDirection>> history = new HashMap<TileEntityHadronDiode, List<ForgeDirection>>();
		
		public Particle(ItemStack item1, ItemStack item2, ForgeDirection dir, int posX, int posY, int posZ) {
			this.item1 = item1.copy();
			this.item2 = item2.copy();
			this.item1.stackSize = 1;
			this.item2.stackSize = 1;
			this.dir = dir;
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			
			this.charge = 750;
			this.momentum = 0;
		}

		//Clones the particle and gives it a new direction
		public Particle clone(ForgeDirection dir) {
			Particle p = new Particle(item1, item2, dir, posX, posY, posZ);
			p.momentum = momentum;
			p.charge = charge;
			p.analysis = analysis;
			p.isCheckExempt = isCheckExempt;
			p.cl0 = cl0;
			p.cl1 = cl1;
			p.expired = expired;
			p.plugs = new ArrayList<TileEntityHadronPower>(plugs);
			p.cloned = true;

			//Deep clone the history
			p.history = new HashMap<TileEntityHadronDiode, List<ForgeDirection>>(history);
			for(TileEntityHadronDiode diode : p.history.keySet()) {
				p.history.put(diode, new ArrayList<ForgeDirection>(p.history.get(diode)));
			}

			return p;
		}
		
		public boolean isExpired() {
			return this.expired;
		}
		
		public void update() {
			
			if(expired) //just in case
				return;

			//Recently cloned particles have already a set direction, this prevents infinite recursion
			if(cloned) {
				cloned = false;
			} else {
				changeDirection(this);
			}

			makeSteppy(this);
			
			if(!this.isExpired()) //only important for when the current segment is the core
				checkSegment(this);
			
			isCheckExempt = false; //clearing up the exemption we might have held from the previous turn, AFTER stepping
			
			if(charge < 0)
				expire(this, EnumHadronState.ERROR_NO_CHARGE);

			if(cl0 > 0) cl0--;
			if(cl1 > 0) cl1--;
		}

		public void incrementCharge(int coilVal) {	
			//not the best code ever made but it works, dammit
			if(cl1 > 0) {
				
				double mult = 2D - (cl1 - 15D) * (cl1 - 15D) / 225D;
				mult = Math.max(mult, 0.1D);
				coilVal *= mult;
				
			} else if(cl0 > 0) {
				if(cl0 > 10) {
					coilVal *= 0.75;
				} else {
					coilVal *= 1.10;
				}
			}
			
			this.momentum += coilVal;
		}

		public void consumePower() {
			for(TileEntityHadronPower plug : plugs) {
				long bit = 10000;
				int times = (int) (plug.getPower() / bit);
				plug.setPower(plug.getPower() - times * bit);
			}
		}
	}
	
	/**
	 * Moves the particle and does all the checks required to do so
	 * Handles diode entering behavior and whatnot
	 * @param p
	 */
	public void makeSteppy(Particle p) {
		
		ForgeDirection dir = p.dir;

		p.posX += dir.offsetX;
		p.posY += dir.offsetY;
		p.posZ += dir.offsetZ;
		
		int x = p.posX;
		int y = p.posY;
		int z = p.posZ;
		
		Block block = worldObj.getBlock(x, y, z);
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityHadron) {

			if(p.analysis != 3)
				expire(p, EnumHadronState.ERROR_NO_ANALYSIS);
			else
				this.finishParticle(p);
			
			return;
		}
		
		if(block.getMaterial() != Material.air && block != ModBlocks.hadron_diode)
			expire(p, EnumHadronState.ERROR_OBSTRUCTED_CHANNEL);
		
		if(block == ModBlocks.hadron_diode)
			p.isCheckExempt = true;
		
		if(isValidCoil(worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)))
			p.isCheckExempt = true;
	}
	
	/**
	 * All the checks done *after* the particle moves one tile
	 * @param p
	 */
	public void checkSegment(Particle p) {
		
		ForgeDirection dir = p.dir;
		int x = p.posX;
		int y = p.posY;
		int z = p.posZ;

		//we make a set of axis where the directional axis is 0 and the normals are 1
		//that allows us to easily iterate through a rectangle that is normal to our moving direction
		int dX = 1 - Math.abs(dir.offsetX);
		int dY = 1 - Math.abs(dir.offsetY);
		int dZ = 1 - Math.abs(dir.offsetZ);
		
		//whether the particle has entered an analysis chamber
		//-> all coils have to be air
		//-> all platings have to be analysis chamber walls
		boolean analysis = true;
		//ensures coolers are useful throughout their initial segment
		int totalValue = 0;
		
		for(int a = x - dX * 2; a <= x + dX * 2; a++) {
			for(int b = y - dY * 2; b <= y + dY * 2; b++) {
				for(int c = z - dZ * 2; c <= z + dZ * 2;c++) {
					
					Block block = worldObj.getBlock(a, b, c);
					int meta = worldObj.getBlockMetadata(a, b, c);
					
					/** ignore the center for now */
					if(a == x && b == y && c == z) {
						
						//we are either in a diode or the core - no analysis for you now
						if(block.getMaterial() != Material.air)
							analysis = false;
						
						continue;
					}

					int ix = Math.abs(x - a);
					int iy = Math.abs(y - b);
					int iz = Math.abs(z - c);
					
					/** check coils, all abs deltas are 1 or less */
					if(ix <= 1 && iy <= 1 && iz <= 1) {
						
						//are we exempt from the coil examination? nice, skip checks only for inner magnets, not the corners!
						if(p.isCheckExempt && ix + iy + iz == 1) {
							continue;
						}
						
						//coil is air, analysis can remain true
						if(block.getMaterial() == Material.air && analysis) {
							continue;
						}
						
						//not air -> not an analysis chamber
						analysis = false;
						
						int coilVal = coilValue(block);
						
						//not a valid coil: kablam!
						if(!isValidCoil(block)) {
							expire(p, EnumHadronState.ERROR_EXPECTED_COIL);
						} else {
							p.charge -= coilVal;
							totalValue += coilVal;
							
							if(block == ModBlocks.hadron_cooler) {
								if(meta == 0) p.cl0 += 10;
								if(meta == 1) p.cl1 += 5;
							}
						}

						continue;
					}
					
					/** now we check the plating, sum of all local positions being 3 or less gives us the outer plating without corners */
					if(ix + iy + iz <= 3) {
						
						//if the plating is for the analysis chamber, continue no matter what
						if(isAnalysis(block))
							continue;

						//no analysis chamber -> turn off analysis and proceed
						analysis = false;
						
						//a plating? good, continue
						if(isPlating(block))
							continue;
						
						TileEntity te = worldObj.getTileEntity(a, b, c);
						
						//power plugs are also ok, might as well succ some energy when passing
						if(te instanceof TileEntityHadronPower) {
							
							TileEntityHadronPower plug = (TileEntityHadronPower)te;
							
							long bit = 10000;							//how much HE one "charge point" is
							int times = (int) (plug.getPower() / bit);	//how many charges the plug has to offer
							
							p.charge += times;
							p.plugs.add(plug);
							
							continue;
						}
						
						//Are we exempt from checking the plating? skip all the plating blocks where branches could be
						if(p.isCheckExempt && ix + iy + iz == 2) {
							continue;
						}

						expire(p, EnumHadronState.ERROR_MALFORMED_SEGMENT);
					}
				}
			}
		}
		//all errors prior to this point come from bad construction, where exact momentum is irrelevant
		p.incrementCharge(totalValue);
		
		if(analysis) {
			
			p.analysis++;
			
			//if the analysis chamber is too big, destroy
			if(p.analysis > 3)
				expire(p, EnumHadronState.ERROR_ANALYSIS_TOO_LONG);
			
			if(p.analysis == 2) {
				//Only pop for the first particle
				if(this.state != EnumHadronState.ANALYSIS) {
					this.worldObj.playSoundEffect(p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5, "fireworks.blast", 2.0F, 2F);
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "hadron");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5), new TargetPoint(worldObj.provider.dimensionId, p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5, 25));
				}
				this.state = EnumHadronState.ANALYSIS;
			}

			//if operating in line accelerator mode, halt after 2 blocks and staart the reading
			if(this.analysisOnly && p.analysis == 2) {
				this.finishParticle(p);
			}
			
		} else {

			//if the analysis stops despite being short of 3 steps in the analysis chamber, destroy
			if(p.analysis > 0 && p.analysis < 3)
				expire(p, EnumHadronState.ERROR_ANALYSIS_TOO_SHORT);
		}
	}
	
	/**
	 * Checks whether we can go forward or if we might want to do a turn
	 * Handles the better part of the diode behavior
	 * @param p
	 */
	public void changeDirection(Particle p) {
		
		ForgeDirection dir = p.dir;
		
		int x = p.posX;
		int y = p.posY;
		int z = p.posZ;

		int nx = x + dir.offsetX;
		int ny = y + dir.offsetY;
		int nz = z + dir.offsetZ;
		
		Block next = worldObj.getBlock(nx, ny, nz);
		
		TileEntity te = worldObj.getTileEntity(nx, ny, nz);
		
		//the next block appears to be a diode, let's see if we can enter
		if(te instanceof TileEntityHadronDiode) {
			TileEntityHadronDiode diode = (TileEntityHadronDiode)te;
			
			if(diode.getConfig(p.dir.getOpposite().ordinal()) != DiodeConfig.IN) {
				//it appears as if we have slammed into the side of a diode, ouch
				expire(p, EnumHadronState.ERROR_DIODE_COLLISION);
			}
			
			//there's a diode ahead, turn off checks so we can make the curve
			p.isCheckExempt = true;
			
			//the *next* block is a diode, we are not in it yet, which means no turning and no check exemption. too bad kiddo.
			return;
		}
		
		//instead of the next TE, we are looking at the current one - the diode (maybe)
		te = worldObj.getTileEntity(x, y, z);
		
		//if we are currently in a diode, we might want to consider changing dirs
		if(te instanceof TileEntityHadronDiode) {
			
			//since we are *in* a diode, we might want to call the warrant officer for
			//an exemption for the coil check, because curves NEED holes to turn into, and
			//checking for coils in spaces where there cannot be coils is quite not-good
			p.isCheckExempt = true;
			
			TileEntityHadronDiode diode = (TileEntityHadronDiode)te;

			boolean hasTurnedCurrent = false;

			if(!p.history.containsKey(diode))
				p.history.put(diode, new ArrayList<ForgeDirection>());

			List<ForgeDirection> usedDirections = p.history.get(diode);

			//Instance a new particle for each required fork
			for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
				if(!usedDirections.contains(d) && diode.getConfig(d.ordinal()) == DiodeConfig.OUT) {
					if(!hasTurnedCurrent) {
						p.dir = d;
						hasTurnedCurrent = true;
					} else {
						Particle clone = p.clone(d);
						clone.history.get(diode).add(d);
						particlesToAdd.add(clone);
					}
				}
			}

			//Add the used direction to the main particle AFTER cloning, so the clones don't get incorrect travel history
			usedDirections.add(p.dir);

			//If we failed to exit, raise DIODE_COLLISION
			if(!hasTurnedCurrent)
				expire(p, EnumHadronState.ERROR_DIODE_COLLISION);

			return;
		}
		
		//next step is air or the core, proceed
		if(next.getMaterial() == Material.air || next == ModBlocks.hadron_core)
			return;
		
		//so, the next block is most certainly a wall. not good. perhaps we could try turning?
		if(isValidCoil(next)) {
			
			ForgeDirection validDir = ForgeDirection.UNKNOWN;
			
			List<ForgeDirection> dirs = getRandomDirs();
			
			//let's look at every direction we could go in
			for(ForgeDirection d : dirs) {
				
				if(d == dir || d == dir.getOpposite())
					continue;
				
				//there is air! we can pass!
				if(worldObj.getBlock(x + d.offsetX, y + d.offsetY, z + d.offsetZ).getMaterial() == Material.air) {
					
					if(validDir == ForgeDirection.UNKNOWN) {
						validDir = d;
					
					//it seems like there are two or more possible ways, which is not allowed without a diode
					//sorry kid, nothing personal
					} else {
						expire(p, EnumHadronState.ERROR_BRANCHING_TURN);
						return;
					}
				}
			}
			
			//set the new direction
			p.dir = validDir;
			p.isCheckExempt = true;
			return;
		}

		expire(p, EnumHadronState.ERROR_OBSTRUCTED_CHANNEL);
	}
	
	/**
	 * Dear god please grant me the gift of death and end my eternal torment
	 * @return
	 */
	private List<ForgeDirection> getRandomDirs() {
		
		List<Integer> rands = Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5} );
		Collections.shuffle(rands);
		List<ForgeDirection> dirs = new ArrayList();
		for(Integer i : rands) {
			dirs.add(ForgeDirection.getOrientation(i));
		}
		return dirs;
	}
	
	public boolean isValidCoil(Block b) {
		if(coilValue(b) > 0) return true;
		
		if(b == ModBlocks.hadron_cooler) return true;
		
		return false;
	}
	
	public int coilValue(Block b) {
		
		if(b instanceof BlockHadronCoil)
			return ((BlockHadronCoil)b).factor;
		
		return 0;
	}
	
	public boolean isPlating(Block b) {
		
		return b instanceof BlockHadronPlating ||
				b instanceof BlockHadronCoil ||
				b == ModBlocks.hadron_plating_glass ||
				b == ModBlocks.hadron_analysis_glass ||
				b == ModBlocks.hadron_access ||
				b == ModBlocks.hadron_cooler;
	}
	
	public boolean isAnalysis(Block b) {
		
		return b == ModBlocks.hadron_analysis ||
				b == ModBlocks.hadron_analysis_glass;
	}
	
	public static enum EnumHadronState {
		IDLE(0x8080ff),
		PROGRESS(0xffff00),
		ANALYSIS(0xffff00),
		NORESULT(0xff8000),
		NORESULT_TOO_SLOW(0xff8000),
		NORESULT_WRONG_INGREDIENT(0xff8000),
		NORESULT_WRONG_MODE(0xff8000),
		SUCCESS(0x00ff00),
		ERROR_NO_CHARGE(0xff0000, true),
		ERROR_NO_ANALYSIS(0xff0000, true),
		ERROR_OBSTRUCTED_CHANNEL(0xff0000, true),
		ERROR_EXPECTED_COIL(0xff0000, true),
		ERROR_MALFORMED_SEGMENT(0xff0000, true),
		ERROR_ANALYSIS_TOO_LONG(0xff0000, true),
		ERROR_ANALYSIS_TOO_SHORT(0xff0000, true),
		ERROR_DIODE_COLLISION(0xff0000, true),
		ERROR_BRANCHING_TURN(0xff0000, true),
		ERROR_GENERIC(0xff0000, true);
		
		public int color;
		public boolean showCoord;
		
		private EnumHadronState(int color) {
			this(color, false);
		}
		
		private EnumHadronState(int color, boolean showCoord) {
			this.color = color;
			this.showCoord = showCoord;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerHadron(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIHadron(player.inventory, this);
	}
}
