package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.gas.BlockGasAir;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ArmorUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;

public class TileEntityAirPump extends TileEntityMachineBase implements IFluidStandardReceiver, INBTPacketReceiver {
	 private World customWorld;
	public static int flucue = 100;
	public int onTicks = 0;
	public static int range = 20;
	public static double offset = 1.75;
	public List<double[]> targets = new ArrayList();
	private boolean needsRevalidate = false;
	AxisAlignedBB sealedRoomAABB; 
	public FluidTank tank;

	public TileEntityAirPump() {
		super(1);
		tank = new FluidTank(Fluids.OXYGEN, 16000);
	}
	
	
	public void spawnParticles() {

			if(worldObj.getTotalWorldTime() % 2 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "tower");
			data.setFloat("lift", 0.1F);
			data.setFloat("base", 0.3F);
			data.setFloat("max", 1F);
			data.setInteger("life", 20 + worldObj.rand.nextInt(20));
			data.setInteger("color",0x98bdf9);

			data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextDouble() - 0.5);
			data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextDouble() -0.5);
			data.setDouble("posY", yCoord + 1);
			
			MainRegistry.proxy.effectNT(data);

		}
	}

	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			if(worldObj.getBlock(xCoord, yCoord+1, zCoord).isAir(worldObj, xCoord, yCoord+1, zCoord)) {
			this.updateConnections();
			if(onTicks > 0) onTicks--;
			this.targets.clear();
						
			if(tank.getFill() > 0) {
				onTicks = 20;


				tank.setFill(tank.getFill() - 10);

				double dx = xCoord + 0.5;
				double dy = yCoord + offset;
				double dz = zCoord + 0.5;

				revalidateRoom();  
				
			}else {
	        	worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
	        	findRoomSections(worldObj, xCoord, yCoord, zCoord);
				}
	        }

			NBTTagCompound data = new NBTTagCompound();
			data.setShort("length", (short)targets.size());
			data.setInteger("onTicks", onTicks);
			tank.writeToNBT(data, "at");
			int i = 0;
			for(double[] d : this.targets) {
				data.setDouble("x" + i, d[0]);
				data.setDouble("y" + i, d[1]);
				data.setDouble("z" + i, d[2]);
				i++;
			}

			this.networkPack(data, 100);
			
		}
		else{
			if(onTicks > 0) {
				this.spawnParticles();
			}
		}
	}
	
		
		
		
	public AxisAlignedBB getSealedRoomAABB() {
	    return sealedRoomAABB;
	}

	public void setNeedsRevalidate(boolean flag) {
	    needsRevalidate = flag;
	    System.out.print("SEVENT RETURNED " + needsRevalidate);
	}

	public void revalidateRoom() {
	    List<AxisAlignedBB> roomSections = findRoomSections(worldObj, xCoord, yCoord, zCoord);
	    AxisAlignedBB newSealedRoomAABB = mergeAABBs(roomSections);
	    // Finalize the sealing process with the new AABB
	    if (newSealedRoomAABB != null) {
	        sealedRoomAABB = newSealedRoomAABB;
	       // processEntitiesWithinAABB(worldObj, sealedRoomAABB);
	    }
	}
	

	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(Fluids.OXYGEN, worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}
	
	public static List<double[]> zap(World worldObj, double x, double y, double z, double radius, Entity source) {

		List<double[]> ret = new ArrayList();
		
		List<EntityLivingBase> targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		
		for(EntityLivingBase e : targets) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - x, e.posY + e.height / 2 - y, e.posZ - z);
			
			if(vec.lengthVector() > range)
				continue;
			
			HbmLivingProps.SsetOxy(e, 20);
			ret.add(new double[] {e.posX, e.posY + e.height / 2 - offset, e.posZ});
		}
		
		return ret;
	}
	
	//TODO: Rewrite this fucking mess of a class
	private List<AxisAlignedBB> findRoomSections(World world, int startX, int startY, int startZ) {
	    Set<BlockPos> visited = new HashSet<>();
	    Set<BlockPos> air = new HashSet<>();
	    Stack<BlockPos> stack = new Stack<>();
	    List<AxisAlignedBB> sectionAABBs = new ArrayList<>();

	    stack.push(new BlockPos(startX, startY, startZ));

	    int minX = startX, minY = startY, minZ = startZ;
	    int maxX = startX, maxY = startY, maxZ = startZ;

	    while (!stack.isEmpty()) {
	        BlockPos current = stack.pop();

	        if (Math.abs(maxX - minX) > MAX_RANGE_X || Math.abs(maxY - minY) > MAX_RANGE_Y || Math.abs(maxZ - minZ) > MAX_RANGE_Z) {
	            // If current stack led to a section that is too big, we discard it and continue
	            continue;
	        }

	        if (!visited.contains(current)) {
	            visited.add(current);

	            // Logic to identify if we've entered a new section could go here

	            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
	                BlockPos neighbor = current.offset(dir);
	                Block block = world.getBlock(neighbor.getX(), neighbor.getY(), neighbor.getZ());

	                if (block.isAir(world, neighbor.getX(), neighbor.getY(), neighbor.getZ()) || block == ModBlocks.vacuum) {
	                	air.add(neighbor);
	                	//world.setBlock(neighbor.getX(), neighbor.getY(), neighbor.getZ(), Blocks.water);
	                    stack.push(neighbor);
	                    minX = Math.min(minX, neighbor.getX());
	                    minY = Math.min(minY, neighbor.getY());
	                    minZ = Math.min(minZ, neighbor.getZ());
	                    maxX = Math.max(maxX, current.getX() + 1); // +1 because block positions are at the corner
	                    maxY = Math.max(maxY, current.getY() + 1);
	                    maxZ = Math.max(maxZ, current.getZ() + 1);
	                    
	                    
	                }
	            }
	        }

	        // After a section has been fully traversed, we add its AABB to the list
	        if (stack.isEmpty() && !visited.isEmpty()) {
	            AxisAlignedBB sectionAABB = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	            sectionAABBs.add(sectionAABB);
	            for(BlockPos pos : air)
	            {
	            	if((pos.getX()<sectionAABB.minX || pos.getX()>sectionAABB.maxX) || (pos.getY()<sectionAABB.minY || pos.getY()>sectionAABB.maxY) || (pos.getZ()<sectionAABB.minZ || pos.getZ()>sectionAABB.maxZ))
	            	{
	            		air.remove(pos);
	            	}
	            	world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.air_block);
	            }
	            // Reset the bounds for the next section
	            minX = maxX = current.getX();
	            minY = maxY = current.getY();
	            minZ = maxZ = current.getZ();
	        }
			if(tank.getFill() <= 0) {
			    for (BlockPos pos : air) {
			        if (worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ()) == ModBlocks.air_block) {
			            worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.air);
			        }
			    }
			    air.clear(); // Clear the set after resetting blocks
			}
	    }

	    return sectionAABBs;
	}
	private AxisAlignedBB mergeAABBs(List<AxisAlignedBB> aabbs) {
	    if (aabbs.isEmpty()) return null;

	    AxisAlignedBB combinedAABB = aabbs.get(0); // Start with the first AABB

	    // Expand the combined AABB to include all other AABBs
	    for (int i = 1; i < aabbs.size(); i++) {
	        combinedAABB = combinedAABB.func_111270_a(aabbs.get(i));
	    }

	    return combinedAABB;
	}
	public AxisAlignedBB mergeRoomSections(World world, int startX, int startY, int startZ) {
	    List<AxisAlignedBB> sectionAABBs = findRoomSections(world, startX, startY, startZ);
	    AxisAlignedBB mergedAABB = mergeAABBs(sectionAABBs);
	    return mergedAABB;
	}

	private void processEntitiesWithinAABB(World world, AxisAlignedBB aabb) {
	    List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);

	    for (EntityLivingBase entity : entities) {
	        HbmLivingProps.SsetOxy(entity, 20);
	    }
	}
	private boolean isWithinBounds(BlockPos pos, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {	    // Check if the position is within a certain bounding box to avoid checking an infinite area.
	    return pos.getX() >= minX && pos.getX() <= maxX && pos.getY() >= minY && pos.getY() <= maxY && pos.getZ() >= minZ && pos.getZ() <= maxZ;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tank.readFromNBT(nbt, "at");
		this.onTicks = nbt.getInteger("onTicks");

	}
	private static final int MAX_RANGE_X = 50;
	private static final int MAX_RANGE_Y = 30; 
	private static final int MAX_RANGE_Z = 50;

	private boolean isWithinBounds(BlockPos pos, int startX, int startY, int startZ) {
	    return pos.getX() >= startX - MAX_RANGE_X && pos.getX() <= startX + MAX_RANGE_X &&
	           pos.getY() >= startY - MAX_RANGE_Y && pos.getY() <= startY + MAX_RANGE_Y &&
	           pos.getZ() >= startZ - MAX_RANGE_Z && pos.getZ() <= startZ + MAX_RANGE_Z;
	}
	
	/*
	private boolean isValidRoomBlock(World world, BlockPos pos) {
	    Block block = world.getBlock(pos.getX(), pos.getY(), pos.getZ());
	    if (!block.isAir(world, pos.getX(), pos.getY(), pos.getZ())) {
	        return false;
	    }

	    int solidNeighbors = 0;
	    for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
	        BlockPos neighbor = pos.offset(dir);
	        Block neighborBlock = world.getBlock(neighbor.getX(), neighbor.getY(), neighbor.getZ());
	        if (!neighborBlock.isAir(world, neighbor.getX(), neighbor.getY(), neighbor.getZ())) {
	            solidNeighbors++;
	        }
	    }

	    return solidNeighbors >= 6;
	}
	*/
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "at");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "at");

	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}




