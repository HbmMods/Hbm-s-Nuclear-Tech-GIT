package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityMissileCustom extends Entity implements IChunkLoader {
	
	int startX;
	int startZ;
	int targetX;
	int targetZ;
	double velocity;
	double decelY;
	double accelXZ;
    private Ticket loaderTicket;
    public int health = 50;
    MissileMultipart template;

	public EntityMissileCustom(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
	}
	
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isRemote)
            {
            	health -= p_70097_2_;
            	
                if (this.health <= 0)
                {
                    this.setDead();
                    this.killMissile();
                }
            }

            return true;
        }
    }
    
    private void killMissile() {
        ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
        ExplosionLarge.spawnShrapnelShower(worldObj, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
    }

	public EntityMissileCustom(World world, float x, float y, float z, int a, int b, MissileMultipart template) {
		super(world);
		this.ignoreFrustumCheck = true;
		/*this.posX = x;
		this.posY = y;
		this.posZ = z;*/
		this.setLocationAndAngles(x, y, z, 0, 0);
		startX = (int) x;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 2;
		
		this.template = template;
		
		this.dataWatcher.updateObject(9, Item.getIdFromItem(template.warhead.part));
		this.dataWatcher.updateObject(10, Item.getIdFromItem(template.fuselage.part));
		if(template.fins != null)
			this.dataWatcher.updateObject(11, Item.getIdFromItem(template.fins.part));
        else
        	this.dataWatcher.addObject(11, Integer.valueOf(0));
		this.dataWatcher.updateObject(12, Item.getIdFromItem(template.thruster.part));
		
        Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1/vector.lengthVector();
		decelY *= 2;
		
		velocity = 0.0;

        this.setSize(1.5F, 1.5F);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
        this.dataWatcher.addObject(8, Integer.valueOf(this.health));

        if(template != null) {
	        System.out.println("yeah");
	        this.dataWatcher.addObject(9, Integer.valueOf(Item.getIdFromItem(template.warhead.part)));
	        this.dataWatcher.addObject(10, Integer.valueOf(Item.getIdFromItem(template.fuselage.part)));
	        
	        if(template.fins != null)
	        	this.dataWatcher.addObject(11, Integer.valueOf(Item.getIdFromItem(template.fins.part)));
	        else
	        	this.dataWatcher.addObject(11, Integer.valueOf(0));
	        
	        this.dataWatcher.addObject(12, Integer.valueOf(Item.getIdFromItem(template.thruster.part)));
        } else {
	        this.dataWatcher.addObject(9, Integer.valueOf(0));
	        this.dataWatcher.addObject(10, Integer.valueOf(0));
	        this.dataWatcher.addObject(11, Integer.valueOf(0));
	        this.dataWatcher.addObject(12, Integer.valueOf(0));
        }
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		motionX = nbt.getDouble("moX");
		motionY = nbt.getDouble("moY");
		motionZ = nbt.getDouble("moZ");
		posX = nbt.getDouble("poX");
		posY = nbt.getDouble("poY");
		posZ = nbt.getDouble("poZ");
		decelY = nbt.getDouble("decel");
		accelXZ = nbt.getDouble("accel");
		targetX = nbt.getInteger("tX");
		targetZ = nbt.getInteger("tZ");
		startX = nbt.getInteger("sX");
		startZ = nbt.getInteger("sZ");
		velocity = nbt.getInteger("veloc");
		this.dataWatcher.updateObject(9, nbt.getInteger("warhead"));
		this.dataWatcher.updateObject(10, nbt.getInteger("fuselage"));
		this.dataWatcher.updateObject(11, nbt.getInteger("fins"));
		this.dataWatcher.updateObject(12, nbt.getInteger("thruster"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("moX", motionX);
		nbt.setDouble("moY", motionY);
		nbt.setDouble("moZ", motionZ);
		nbt.setDouble("poX", posX);
		nbt.setDouble("poY", posY);
		nbt.setDouble("poZ", posZ);
		nbt.setDouble("decel", decelY);
		nbt.setDouble("accel", accelXZ);
		nbt.setInteger("tX", targetX);
		nbt.setInteger("tZ", targetZ);
		nbt.setInteger("sX", startX);
		nbt.setInteger("sZ", startZ);
		nbt.setDouble("veloc", velocity);
		nbt.setInteger("warhead", this.dataWatcher.getWatchableObjectInt(9));
		nbt.setInteger("fuselage", this.dataWatcher.getWatchableObjectInt(10));
		nbt.setInteger("fins", this.dataWatcher.getWatchableObjectInt(11));
		nbt.setInteger("thruster", this.dataWatcher.getWatchableObjectInt(12));
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
    public void onUpdate()
    {
        this.dataWatcher.updateObject(8, Integer.valueOf(this.health));
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
		this.setLocationAndAngles(posX + this.motionX * velocity, posY + this.motionY * velocity, posZ + this.motionZ * velocity, 0, 0);

		this.rotation();

		this.motionY -= decelY * velocity;

		Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
		vector = vector.normalize();
		vector.xCoord *= accelXZ * velocity;
		vector.zCoord *= accelXZ * velocity;

		if (motionY > 0) {
			motionX += vector.xCoord;
			motionZ += vector.zCoord;
		}

		if (motionY < 0) {
			motionX -= vector.xCoord;
			motionZ -= vector.zCoord;
		}

		if (!this.worldObj.isRemote)
			// this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj,
			// this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(posX, posY, posZ, 2),
					new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 300));

		if (this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air
				&& this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.water
				&& this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.flowing_water) {

			if (!this.worldObj.isRemote) {
				onImpact();
			}
			this.setDead();
			return;
		}

		loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
		
		if(velocity < 5)
			velocity += 0.01;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 2500000;
    }

	public void onImpact() {
		
		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(9));

		WarheadType type = (WarheadType)part.attributes[0];
		float strength = (Float)part.attributes[1];
		
		switch(type) {
		case HE:
			ExplosionLarge.explode(worldObj, posX, posY, posZ, strength, true, true, true);
			break;
		case INC:
			break;
		case CLUSTER:
			break;
		case BUSTER:
			break;
		case NUCLEAR:
			break;
		case TX:
			break;
		case BALEFIRE:
			break;
		case N2:
			break;
		default:
			break;
		
		}
	}
	
	public void init(Ticket ticket) {
		if(!worldObj.isRemote) {
			
            if(ticket != null) {
            	
                if(loaderTicket == null) {
                	
                	loaderTicket = ticket;
                	loaderTicket.bindEntity(this);
                	loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
            }
        }
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

    public void loadNeighboringChunks(int newChunkX, int newChunkZ)
    {
        if(!worldObj.isRemote && loaderTicket != null)
        {
            for(ChunkCoordIntPair chunk : loadedChunks)
            {
                ForgeChunkManager.unforceChunk(loaderTicket, chunk);
            }

            loadedChunks.clear();
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ - 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ - 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ - 1));

            for(ChunkCoordIntPair chunk : loadedChunks)
            {
                ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
    }
}
