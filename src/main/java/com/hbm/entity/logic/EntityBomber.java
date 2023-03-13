package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.LoopedEntitySoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.ParticleUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

public class EntityBomber extends Entity implements IChunkLoader {
	
	int timer = 200;
	int civtimer = 600;
	int bombStart = 75;
	int bombStop = 125;
	int bombRate = 3;
	int type = 0;

	public int health = 50;
	
    public EntityBomber(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
    	this.setSize(8.0F, 4.0F);
	}
	
    public boolean canBeCollidedWith()
    {
        return this.health > 0;
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
    	if(p_70097_1_ == ModDamageSource.nuclearBlast)
    		return false;
    	
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isRemote && this.health > 0)
            {
            	health -= p_70097_2_;
            	
                if (this.health <= 0)
                {
                    this.killBomber();
                }
                /*
                if (this.health <= 0 && type == 9)
                {
                    this.killBomber(); //not really needed wtf
                }
                */
            }

            return true;
        }
    }
    
    private void killBomber() {
        ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
    	if(type == 8) {
    		worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:entity.warCrimeShotDown", 25.0F, 1.0F);
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(200, 200, 200));

			for(EntityPlayer player : players) {
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.coin_airliner));
			}
			}else {
    	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:entity.planeShotDown", 25.0F, 1.0F);
    	
    	}
    }
	
	@Override
	public void onUpdate() {
		
		//super.onUpdate();

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;

		this.setPosition(posX + motionX, posY + motionY, posZ + motionZ);
		
		if(!worldObj.isRemote) {
			
			this.dataWatcher.updateObject(17, health);
			
			if(health > 0)
				PacketDispatcher.wrapper.sendToAllAround(new LoopedEntitySoundPacket(this.getEntityId()), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 250));
		} else {
			health = this.dataWatcher.getWatchableObjectInt(17);
		}
		
		this.rotation();
		
		if(this.health <= 0) {
			motionY -= 0.025;
			
			for(int i = 0; i < 10; i++)
				ParticleUtil.spawnGasFlame(this.worldObj, this.posX + rand.nextGaussian() * 0.5 - motionX * 2, this.posY + rand.nextGaussian() * 0.5 - motionY * 2, this.posZ + rand.nextGaussian() * 0.5 - motionZ * 2, 0.0, 0.1, 0.0);
			
			if(worldObj.getBlock((int)posX, (int)posY, (int)posZ).isNormalCube() && !worldObj.isRemote) {
				this.setDead();
				
				/*worldObj.setBlock((int)posX, (int)posY, (int)posZ, ModBlocks.bomber);
				TileEntityBomber te = (TileEntityBomber)worldObj.getTileEntity((int)posX, (int)posY, (int)posZ);

				if(te != null) {
					te.yaw = (int)(this.rotationYaw);
					te.pitch = (int)(this.rotationPitch);
					
					te.type = this.getDataWatcher().getWatchableObjectByte(16);
				}*/
				
				ExplosionLarge.explodeFire(worldObj, posX, posY, posZ, 25, true, false, true);
		    	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:entity.planeCrash", 10.0F, 1.0F);
				
				return;
			}
		}
		
		if(this.ticksExisted > timer)
			this.setDead();
		else if(type == 8 && ticksExisted > civtimer){
			this.setDead();
		}
		
		if(!worldObj.isRemote && this.health > 0 && this.ticksExisted > bombStart && this.ticksExisted < bombStop && this.ticksExisted % bombRate == 0) {
			
			if(type == 3) {

	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
				ExplosionChaos.spawnChlorine(worldObj, this.posX, this.posY - 1F, this.posZ, 10, 0.5, 3);
				
			} else if(type == 5) {
				
	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:weapon.missileTakeOff", 10.0F, 0.9F + rand.nextFloat() * 0.2F);
	        	EntityRocketHoming rocket = new EntityRocketHoming(worldObj);
	        	rocket.setIsCritical(true);
	        	//rocket.motionX = motionX;
	        	//rocket.motionZ = motionZ;
	        	rocket.motionY = -1;
	        	rocket.shootingEntity = this;
	        	rocket.homingRadius = 50;
	        	rocket.homingMod = 5;
				
	        	rocket.posX = posX + rand.nextDouble() - 0.5;
	        	rocket.posY = posY - rand.nextDouble();
	        	rocket.posZ = posZ + rand.nextDouble() - 0.5;
	        	
				worldObj.spawnEntityInWorld(rocket);
	        	
			} else if(type == 6) {
				
	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:weapon.missileTakeOff", 10.0F, 0.9F + rand.nextFloat() * 0.2F);
	        	EntityBoxcar rocket = new EntityBoxcar(worldObj);
				
	        	rocket.posX = posX + rand.nextDouble() - 0.5;
	        	rocket.posY = posY - rand.nextDouble();
	        	rocket.posZ = posZ + rand.nextDouble() - 0.5;
	        	
				worldObj.spawnEntityInWorld(rocket);
	        	
			} else if(type == 7) {

	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
				ExplosionChaos.spawnChlorine(worldObj, this.posX, worldObj.getHeightValue((int)this.posX, (int)this.posZ) + 2, this.posZ, 10, 1, 2);
				
			} else {
				
	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:entity.bombWhistle", 10.0F, 0.9F + rand.nextFloat() * 0.2F);
	        	
				EntityBombletZeta zeta = new EntityBombletZeta(worldObj);
				/*zeta.prevRotationYaw = zeta.rotationYaw = this.rotationYaw;
				zeta.prevRotationPitch = zeta.rotationPitch = this.rotationPitch;*/
				
				zeta.rotation();
				
				zeta.type = type;
				
				zeta.posX = posX + rand.nextDouble() - 0.5;
				zeta.posY = posY - rand.nextDouble();
				zeta.posZ = posZ + rand.nextDouble() - 0.5;
				
				if(type == 0) {
					zeta.motionX = motionX + rand.nextGaussian() * 0.15;
					zeta.motionZ = motionZ + rand.nextGaussian() * 0.15;
				} else {
					zeta.motionX = motionX;
					zeta.motionZ = motionZ;
				}
				
				worldObj.spawnEntityInWorld(zeta);
			}
		}

        if(!worldObj.isRemote)
        	loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
		
	}
    
    public void fac(World world, double x, double y, double z) {
    	
    	Vec3 vector = Vec3.createVectorHelper(world.rand.nextDouble() - 0.5, 0, world.rand.nextDouble() - 0.5);
    	vector = vector.normalize();
    	vector.xCoord *= GeneralConfig.enableBomberShortMode ? 1 : 2;
    	vector.zCoord *= GeneralConfig.enableBomberShortMode ? 1 : 2;
    	
    	this.setLocationAndAngles(x - vector.xCoord * 100, y + 50, z - vector.zCoord * 100, 0.0F, 0.0F);
    	this.loadNeighboringChunks((int)(x / 16), (int)(z / 16));
    	
    	this.motionX = vector.xCoord;
    	this.motionZ = vector.zCoord;
    	this.motionY = 0.0D;
    	
    	this.rotation();
    	
    	int i = 1;
    	
    	int rand = world.rand.nextInt(7);
    	
    	switch(rand) {
    	case 0:
    	case 1: i = 1; break;
    	case 2:
    	case 3: i = 2; break;
    	case 4: i = 5; break;
    	case 5: i = 6; break;
    	case 6: i = 7; break;
    	}
    	
    	if(world.rand.nextInt(100) == 0) {
        	rand = world.rand.nextInt(4);

        	switch(rand) {
        	case 0: i = 0; break;
        	case 1: i = 3; break;
        	case 2: i = 4; break;
        	case 3: i = 8; break;
        	}
    	}
    	
    	this.getDataWatcher().updateObject(16, (byte)i);
    	this.setSize(8.0F, 4.0F);
    }
    
    public static EntityBomber statFacCarpet(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 100;
    	bomber.bombRate = 2;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 0;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacNapalm(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 100;
    	bomber.bombRate = 5;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 1;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacChlorine(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 100;
    	bomber.bombRate = 4;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 2;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacOrange(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 75;
    	bomber.bombStop = 125;
    	bomber.bombRate = 1;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 3;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacABomb(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 60;
    	bomber.bombStop = 70;
    	bomber.bombRate = 65;

    	bomber.fac(world, x, y, z);
    	
    	int i = 1;
    	
    	int rand = world.rand.nextInt(3);
    	
    	switch(rand) {
    	case 0: i = 5; break;
    	case 1: i = 6; break;
    	case 2: i = 7; break;
    	}
    	
    	if(world.rand.nextInt(100) == 0) {
        	i = 8;
    	}
    	
    	bomber.getDataWatcher().updateObject(16, (byte)i);
    	
    	bomber.type = 4;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacStinger(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 150;
    	bomber.bombRate = 10;

    	bomber.fac(world, x, y, z);
    	
    	bomber.getDataWatcher().updateObject(16, (byte)4);
    	
    	bomber.type = 5;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacBoxcar(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 150;
    	bomber.bombRate = 10;

    	bomber.fac(world, x, y, z);
    	
    	bomber.getDataWatcher().updateObject(16, (byte)6);
    	
    	bomber.type = 6;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacPC(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 75;
    	bomber.bombStop = 125;
    	bomber.bombRate = 1;

    	bomber.fac(world, x, y, z);
    	
    	bomber.getDataWatcher().updateObject(16, (byte)6);
    	
    	bomber.type = 7;
    	
    	return bomber;
    }
    public static EntityBomber statFacCV(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 75;
    	bomber.bombStop = 125;
    	bomber.bombRate = 90000;

    	bomber.fac(world, x, y+16, z); //adding the y value breaks things, sorry bluehat
    	
    	bomber.getDataWatcher().updateObject(16, (byte)9);
    	
    	bomber.type = 8;
    	
    	
    	return bomber;
    }


    @Override
	public void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(17, Integer.valueOf((int)50));
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		ticksExisted = nbt.getInteger("ticksExisted");
		bombStart = nbt.getInteger("bombStart");
		bombStop = nbt.getInteger("bombStop");
		bombRate = nbt.getInteger("bombRate");
		type = nbt.getInteger("type");

    	this.getDataWatcher().updateObject(16, nbt.getByte("style"));
    	this.getDataWatcher().updateObject(17, nbt.getInteger("health"));
    	this.setSize(8.0F, 4.0F);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setInteger("bombStart", bombStart);
		nbt.setInteger("bombStop", bombStop);
		nbt.setInteger("bombRate", bombRate);
		nbt.setInteger("type", type);
		nbt.setByte("style", this.getDataWatcher().getWatchableObjectByte(16));
		nbt.setInteger("health", this.getDataWatcher().getWatchableObjectInt(17));
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
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
	
    private Ticket loaderTicket;
    
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
