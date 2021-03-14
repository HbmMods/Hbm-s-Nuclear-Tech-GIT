package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockHadronCoil;
import com.hbm.blocks.machine.BlockHadronPlating;
import com.hbm.interfaces.IConsumer;
import com.hbm.inventory.HadronRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityHadronDiode.DiodeConfig;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadron extends TileEntityMachineBase implements IConsumer {
	
	public long power;
	public static final long maxPower = 10000000;
	
	public boolean isOn = false;
	public boolean analysisOnly = false;
	public boolean hopperMode = false;
	
	private int delay;
	public EnumHadronState state = EnumHadronState.IDLE;
	private static final int delaySuccess = 20;
	private static final int delayNoResult = 60;
	private static final int delayError = 60;
	
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
		return i == 0 || i == 1;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 4, power, maxPower);
			drawPower();
			
			if(delay <= 0 && this.isOn && particles.size() < maxParticles && slots[0] != null && slots[1] != null && power >= maxPower * 0.75) {
				
				if(!hopperMode || (slots[0].stackSize > 1 && slots[1].stackSize > 1)) {
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
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setLong("power", power);
			data.setBoolean("analysis", analysisOnly);
			data.setBoolean("hopperMode", hopperMode);
			data.setByte("state", (byte) state.ordinal());
			this.networkPack(data, 50);
		}
	}
	
	private void process(Particle p) {
		
		ItemStack[] result = HadronRecipes.getOutput(p.item1, p.item2, p.momentum, analysisOnly);
		
		if(result == null) {
			this.state = EnumHadronState.NORESULT;
			this.delay = delayNoResult;
			worldObj.playSoundEffect(p.posX, p.posY, p.posZ, "random.orb", 2, 0.5F);
			return;
		}
		
		if((slots[2] == null || (slots[2].getItem() == result[0].getItem() && slots[2].stackSize < slots[2].getMaxStackSize())) &&
				(slots[3] == null || (slots[3].getItem() == result[1].getItem() && slots[3].stackSize < slots[3].getMaxStackSize()))) {
			
			for(int i = 2; i <= 3; i++ ) {
				
				//System.out.println("yes");
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
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.analysisOnly = data.getBoolean("analysis");
		this.hopperMode = data.getBoolean("hopperMode");
		this.state = EnumHadronState.values()[data.getByte("state")];
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0)
			this.isOn = !this.isOn;
		if(meta == 1)
			this.analysisOnly = !this.analysisOnly;
		if(meta == 2)
			this.hopperMode = !this.hopperMode;
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
			process(p);
		
		p.expired = true;
	}
	
	static final int maxParticles = 1;
	List<Particle> particles = new ArrayList();
	List<Particle> particlesToRemove = new ArrayList();
	
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
		this.hopperMode = nbt.getBoolean("hopperMode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOn", isOn);
		nbt.setLong("power", power);
		nbt.setBoolean("analysis", analysisOnly);
		nbt.setBoolean("hopperMode", hopperMode);
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
		
		boolean expired = false;
		
		public Particle(ItemStack item1, ItemStack item2, ForgeDirection dir, int posX, int posY, int posZ) {
			this.item1 = item1.copy();
			this.item2 = item2.copy();
			this.item1.stackSize = 1;
			this.item2.stackSize = 1;
			this.dir = dir;
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			
			this.charge = 0;
			this.momentum = 0;
		}
		
		public void expire() {
			
			if(expired)
				return;
			
			this.expired = true;
			particlesToRemove.add(this);
			worldObj.newExplosion(null, posX + 0.5, posY + 0.5, posZ + 0.5, 10, false, false);
			//System.out.println("Last dir: " + dir.name());
			//System.out.println("Last pos: " + posX + " " + posY + " " + posZ);
			//Thread.currentThread().dumpStack();

			TileEntityHadron.this.state = EnumHadronState.ERROR;
			TileEntityHadron.this.delay = delayError;
		}
		
		public boolean isExpired() {
			return this.expired;
		}
		
		public void update() {
			
			if(expired) //just in case
				return;

			changeDirection(this);
			makeSteppy(this);
			
			if(!this.isExpired()) //only important for when the current segment is the core
				checkSegment(this);
			
			isCheckExempt = false; //clearing up the exemption we might have held from the previous turn, AFTER stepping
			
			if(charge < 0)
				this.expire();
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
				p.expire();
			else
				this.finishParticle(p);
			
			return;
		}
		
		if(block.getMaterial() != Material.air && block != ModBlocks.hadron_diode)
			p.expire();
		
		if(block == ModBlocks.hadron_diode)
			p.isCheckExempt = true;
		
		if(coilValue(worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) > 0)
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
		
		for(int a = x - dX * 2; a <= x + dX * 2; a++) {
			for(int b = y - dY * 2; b <= y + dY * 2; b++) {
				for(int c = z - dZ * 2; c <= z + dZ * 2;c++) {
					
					Block block = worldObj.getBlock(a, b, c);
					
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
						if(coilVal == 0) {
							p.expire();
						} else {
							p.momentum += coilVal;
							p.charge -= coilVal;
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
							plug.setPower(plug.getPower() - times * bit);
							
							continue;
						}
						
						//Are we exempt from checking the plating? skip all the plating blocks where branches could be
						if(p.isCheckExempt && ix + iy + iz == 2) {
							continue;
						}
						
						//System.out.println("Was exempt: " + p.isCheckExempt);
						//worldObj.setBlock(a, b, c, Blocks.dirt);

						p.expire();
					}
				}
			}
		}
		
		if(analysis) {
			
			p.analysis++;
			
			//if the analysis chamber is too big, destroy
			if(p.analysis > 3)
				p.expire();
			
			if(p.analysis == 2) {
				this.worldObj.playSoundEffect(p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5, "fireworks.blast", 2.0F, 2F);
				this.state = EnumHadronState.ANALYSIS;
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "hadron");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5), new TargetPoint(worldObj.provider.dimensionId, p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5, 25));
			}

			//if operating in line accelerator mode, halt after 2 blocks and staart the reading
			if(this.analysisOnly && p.analysis == 2) {
				this.finishParticle(p);
			}
			
		} else {

			//if the analysis stops despite being short of 3 steps in the analysis chamber, destroy
			if(p.analysis > 0 && p.analysis < 3)
				p.expire();
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
				p.expire();
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
			
			//the direction in which we were going anyway is an output, so we will keep going
			if(diode.getConfig(dir.ordinal()) == DiodeConfig.OUT) {
				return;
				
			//well then, iterate through some random directions and hope a valid output shows up
			} else {
				
				List<ForgeDirection> dirs = getRandomDirs();
				
				for(ForgeDirection d : dirs) {
					
					if(d == dir || d == dir.getOpposite())
						continue;
					
					//looks like we can leave!
					if(diode.getConfig(d.ordinal()) == DiodeConfig.OUT) {
						//set the direction and leave this hellhole
						p.dir = d;
						return;
					}
				}
			}
		}
		
		//next step is air or the core, proceed
		if(next.getMaterial() == Material.air || next == ModBlocks.hadron_core)
			return;
		
		//so, the next block is most certainly a wall. not good. perhaps we could try turning?
		if(coilValue(next) > 0) {
			
			ForgeDirection validDir = ForgeDirection.UNKNOWN;
			
			List<ForgeDirection> dirs = getRandomDirs();
			
			//System.out.println("Starting as " + dir.name());
			
			//let's look at every direction we could go in
			for(ForgeDirection d : dirs) {
				
				if(d == dir || d == dir.getOpposite())
					continue;
				
				//System.out.println("Trying " + d.name());
				
				//there is air! we can pass!
				if(worldObj.getBlock(x + d.offsetX, y + d.offsetY, z + d.offsetZ).getMaterial() == Material.air) {
					
					if(validDir == ForgeDirection.UNKNOWN) {
						validDir = d;
						//System.out.println("yes");
					
					//it seems like there are two or more possible ways, which is not allowed without a diode
					//sorry kid, nothing personal
					} else {
						//System.out.println("what");
						p.expire();
						return;
					}
				}
			}
			
			//set the new direction
			p.dir = validDir;
			p.isCheckExempt = true;
			return;
		}
		
		p.expire();
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
				b == ModBlocks.hadron_access;
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
		SUCCESS(0x00ff00),
		ERROR(0xff0000);
		
		public int color;
		
		private EnumHadronState(int color) {
			this.color = color;
		}
	}
}
