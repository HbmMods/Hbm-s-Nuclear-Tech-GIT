package com.hbm.blocks.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TestEventTester extends Block {
	
    public float explosionSize = 1000F;
    private Map field_77288_k = new HashMap();
    protected static Random itemRand = new Random();
    public World worldObj;

	public TestEventTester(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x1, int y1, int z1, Block p_149695_5_)
    {
    	this.worldObj = p_149695_1_;
    	//Levers and buttons for some reason set meta and notify, and *then* notify again, meaning they will always fire this twice.
    	//Pressure plates, torches, redstone, etc. do not have this issue.
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x1, y1, z1))
        {
        	/*Component comp = new SiloComponent(this.worldObj.rand, x1, z1);
        	StructureBoundingBox box = comp.getBoundingBox();
        	box.minY = 0;
        	box.maxY = 512;
        	comp.addComponentParts(p_149695_1_, this.worldObj.rand, box);*/
        	
        	//The laser thread is too dangerous to use right now
        	//ThreadLaser laser = new ThreadLaser(p_149695_1_, x, y, z, "north");
        	//laser.start();
			//ExplosionChaos.frag(p_149695_1_, x, y + 2, z, 10, false, null);
        	//EntitySmokeFX smoke = new EntitySmokeFX(p_149695_1_, x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0);
        	//p_149695_1_.spawnEntityInWorld(smoke);
        	//ExplosionParticle.spawnMush(p_149695_1_, x, y, z);
			//mirv(this.worldObj, x, y + 20, z);
        	//killEvent(p_149695_1_, x, y, z);
        	/*EntityNuclearCreeper e = new EntityNuclearCreeper(p_149695_1_);
        	e.posX = x;
        	e.posY = y + 1;
        	e.posZ = z;
        	if(!p_149695_1_.isRemote)
        	{
        		p_149695_1_.spawnEntityInWorld(e);
        	}
			 /*if(p_149695_1_.isRemote)
			 {
				 ExplosionNukeAdvanced.mush(p_149695_1_, x, y, z);
			 }*/
        	/*EntityFalloutRain fallout = new EntityFalloutRain(p_149695_1_, 1000);
        	fallout.posX = x;
        	fallout.posY = y + 3;
        	fallout.posZ = z;
        	fallout.setScale(50);
        	
        	p_149695_1_.spawnEntityInWorld(fallout);*/
        	
        	//worldObj.setBlock(x, y, z, Blocks.air);
        	//ExplosionLarge.explode(worldObj, x + 0.5, y + 0.5, z + 0.5, MainRegistry.x, true, true, true);
        	
        	/*EntityBlackHole bl = new EntityBlackHole(worldObj, MainRegistry.x * 0.1F);
        	bl.posX = x + 0.5F;
        	bl.posY = y + 0.5F;
        	bl.posZ = z + 0.5F;
        	worldObj.spawnEntityInWorld(bl);*/
        	
        	
        	
        	
        	
                /*if(!worldObj.isRemote)
                {
                    try {
                        Chunk oldChunk = worldObj.getChunkFromBlockCoords(x1, z1);

                        if(worldObj instanceof WorldServer)
                        {
                            WorldServer worldServer = (WorldServer) worldObj;
                            ChunkProviderServer chunkProviderServer = worldServer.theChunkProviderServer;
                            IChunkProvider chunkProviderGenerate = chunkProviderServer.currentChunkProvider;

                            Chunk newChunk = chunkProviderGenerate.provideChunk(oldChunk.xPosition, oldChunk.zPosition);

                            for (int x = 0; x < 16; x++)
                            {
                                for (int z = 0; z < 16; z++)
                                {
                                    for (int y = 0; y < worldObj.getHeight(); y++)
                                    {
                                        Block block = newChunk.getBlock(x, y, z);
                                        int metadata = newChunk.getBlockMetadata(x, y, z);

                                        worldServer.setBlock(x + oldChunk.xPosition * 16, y, z + oldChunk.zPosition * 16, block, metadata, 2);

                                        TileEntity tileEntity = newChunk.getTileEntityUnsafe(x, y, z);

                                        if(tileEntity != null)
                                        {
                                            worldServer.setTileEntity(x + oldChunk.xPosition * 16, y, z + oldChunk.zPosition * 16, tileEntity);
                                        }
                                    }
                                }
                            }

                            oldChunk.isTerrainPopulated = false;
                            chunkProviderGenerate.populate(chunkProviderGenerate, oldChunk.xPosition, oldChunk.zPosition);
                        }
                    } catch(Exception e) {
                        System.out.println("Rejuvenation Failed!");
                        e.printStackTrace();
                    }
                }*/
        	
        	
        	/*if(!worldObj.isRemote) {
        		switch(itemRand.nextInt(3)) {
        		case 0:
        			(new Meteorite()).generateLarge(worldObj, itemRand, x1, y1, z1);
        			break;
        		case 1:
        			(new Meteorite()).generateMedium(worldObj, itemRand, x1, y1, z1);
        			break;
        		case 2:
        			(new Meteorite()).generateSmall(worldObj, itemRand, x1, y1, z1);
        			break;
        		}
        	}*/
        	
        	
        	/*if(!worldObj.isRemote) {
        		EntityMeteor met = new EntityMeteor(worldObj);
        		met.posX = x1;
        		met.posY = 300;
        		met.posZ = z1;
        		worldObj.spawnEntityInWorld(met);
        		worldObj.setBlockToAir(x1, y1, z1);
        	}*/
        	
        	
        	/*if(!worldObj.isRemote) {
        		ModEventHandler.meteorShower = 6000;
        	}*/
        	
        	/*EntityCloudFleija rainbow = new EntityCloudFleija(worldObj);
        	rainbow.posX = x1;
        	rainbow.posY = y1;
        	rainbow.posZ = z1;
        	rainbow.maxAge = 100;
        	if(!worldObj.isRemote)
        		worldObj.spawnEntityInWorld(rainbow);*/
        	
        	/*RadiationSavedData data = RadiationSavedData.getData(worldObj);
        	
        	//ALU SETS RAD TO 1000
        	//BER PRINTS RAD LEVEL
        	//COP INITIATES CYCLE
        	//RED FLUSHES ALL RAD
        	//STE PRINTS RAD CHUNKS
        	//URA PRINTS TOTAL RAD
        	//THO SETS RAD TO 1000000
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_aluminium) {

				Chunk chunk = worldObj.getChunkFromBlockCoords(x1, z1);
				
				data.setRadForCoord(chunk.xPosition, chunk.zPosition, 1000);
				
				System.out.println(data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition));
        	}
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_beryllium) {

				Chunk chunk = worldObj.getChunkFromBlockCoords(x1, z1);
				
				System.out.println(data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition));
        	}
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_copper) {
        		data.updateSystem();
        	}
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_red_copper) {

				data.jettisonData();
        	}
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_steel) {
				
				System.out.println(data.contamination.size());
        	}
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_uranium) {
				
        		float r = 0;

            	for(Entry<ChunkCoordIntPair, Float> struct : data.contamination.entrySet()) {
        			r += struct.getValue();
        		}
        		
				System.out.println(r);
        	}
        	
        	if(worldObj.getBlock(x1, y1 - 1, z1) == ModBlocks.block_thorium) {

				Chunk chunk = worldObj.getChunkFromBlockCoords(x1, z1);
				
				data.setRadForCoord(chunk.xPosition, chunk.zPosition, 1000000);
				
				System.out.println(data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition));
        	}
        	
        	if(!worldObj.isRemote) {
        		
        		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, x1, y1 + 5, z1, 100));
        		worldObj.setBlockToAir(x1, y1, z1);
        	}*/
        	
        }
    }
    
	/*public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		this.worldObj = world;
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			//killEvent(world, x, y, z);
			world.spawnParticle("smoke", (double)x, (double)y + 1, (double)z, 0.0D, 1.0D, 0.0D);
			
			return true;
		} else {
			return false;
		}
	}*/
    
    @Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	this.worldObj = par1World;
    	
    		 /*double d = (float)par2 + 0.5F;
    		 double d1 = (float)par3 + 0.7F;
    		 double d2 = (float)par4 + 0.5F;
    		 double d3 = 0.2199999988079071D;
    		 double d4 = 0.27000001072883606D;
    				 par1World.spawnParticle("smoke", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
    				 par1World.spawnParticle("flame", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
    				 //Minecraft.getMinecraft().effectRenderer.addEffect(new NukeSmokeFX(par1World, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 100, 100));
    				 //NukeCloudFX part = new NukeCloudFX(par1World, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 100);
    				 //part.
    				 //Minecraft.getMinecraft().effectRenderer.addEffect(part);
    		 {
    				 return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    			 if(par1World.isRemote)
    			 {
    				 ExplosionNukeAdvanced.mush(par1World, par2, par3, par4);
    			 }
    		 }*/
    	
    	//ExplosionThutmose splosion = new ExplosionThutmose(par1World, null, (double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, 10);
    	//splosion.doExplosion();
    	
    	//par1World.setBlock(par2, par3, par4, Blocks.air);
    	//ExplosionChaos.anvil(par1World, par2, par3 + 2, par4, 1);
    	
    	//return true;
    	//System.out.println(par5EntityPlayer.getCommandSenderName());
    	//System.out.println(par5EntityPlayer.getUniqueID());
    	/*List<ItemStack> list = new ArrayList<ItemStack>();
    	PotionEffect effect = new PotionEffect(PotionEffectTaint.instance.id, 300, 0);
    	effect.setCurativeItems(list);
    	par5EntityPlayer.addPotionEffect(effect);
    	return true;*/
    	
    	

    	/*if(!worldObj.isRemote) {
		    SatelliteSavedData data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    if(data == null) {
		        worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData(worldObj));
		        return true;
		    }
		    
		    for(SatelliteSaveStructure sat : data.satellites) {
		    	par5EntityPlayer.addChatComponentMessage(new ChatComponentText(sat.satelliteID + ": " + sat.satelliteType.name()));
		    }
		    data.markDirty();
    	}*/
    	
    	
    	/*if(!worldObj.isRemote)
    		buildEvent(worldObj, par2, par3, par4, 30);*/
    	
    	/*((EntityLivingBase)par5EntityPlayer).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 100, 0));*/
    	
    	/*worldObj.setBlockToAir(par2, par3, par4);
    	(new Sellafield()).generate(worldObj, par2, par4, 50D, 15D);*/
    	
    	/*switch(itemRand.nextInt(3)) {
    	case 0: 
    		worldObj.playSoundEffect(par2, par3, par4, "hbm:fm.clap", 1.0F, 1.0F); break;
    	case 1: 
    		worldObj.playSoundEffect(par2, par3, par4, "hbm:fm.mug", 1.0F, 1.0F); break;
    	case 2: 
    		worldObj.playSoundEffect(par2, par3, par4, "hbm:fm.sample", 1.0F, 1.0F); break;
    	}*/
    	
    	/*worldObj.setBlockToAir(par2, par3, par4);
    	
    	new Barrel().generate(worldObj, worldObj.rand, par2, par3, par4);*/


    	/*worldObj.setBlock(par2, par3, par4, ModBlocks.crate_steel, 0, 3);

		if(worldObj.getBlock(par2, par3, par4) == ModBlocks.crate_steel)
		{
			WeightedRandomChestContent.generateChestContents(worldObj.rand, HbmChestContents.getLoot(3), (TileEntityCrateSteel)worldObj.getTileEntity(par2, par3, par4), 32);
		}*/
    	
    	/*EntityBurningFOEQ foeq = new EntityBurningFOEQ(worldObj);
    	foeq.posX = par2;
    	foeq.posY = 400;
    	foeq.posZ = par4;
    	foeq.motionX = worldObj.rand.nextGaussian() * 1D;
    	foeq.motionZ = worldObj.rand.nextGaussian() * 1D;
    	foeq.motionY = -4D;
    	
    	if(!worldObj.isRemote)
    		worldObj.spawnEntityInWorld(foeq);*/
    	
    	/*if(!worldObj.isRemote) {
    		worldObj.setBlockToAir(par2, par3, par4);
    		ExplosionLarge.jolt(worldObj, par2 - 0.5, par3 - 0.5, par4 - 0.5, 5, 200, 0.25);
    		ExplosionLarge.explode(worldObj, par2 + 0.5, par3 + 0.5, par4 + 0.5, 5, false, false, false);
    	}*/
    	
    	/*if(!worldObj.isRemote) {
    		EntityMinerRocket rocket = new EntityMinerRocket(worldObj);
    		rocket.posX = par2 + 0.5;
    		rocket.posY = 100;
    		rocket.posZ = par4 + 0.5;
    		
    		worldObj.spawnEntityInWorld(rocket);
    	}*/
    	
    	/*if(!worldObj.isRemote) {
    		EntityEMP emp = new EntityEMP(worldObj);
    		emp.posX = par2 + 0.5;
    		emp.posY = par3 + 0.5;
    		emp.posZ = par4 + 0.5;
    		
    		worldObj.spawnEntityInWorld(emp);
    	}*/
    	
    	/*if(!worldObj.isRemote) {
    		
    		new GeyserLarge().generate(worldObj, itemRand, par2, par3, par4);
    	}*/
    	
    	/*if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(par2, par3 + 2, par4, 2),
					new TargetPoint(worldObj.provider.dimensionId,par2, par3, par4, 50));*/

    	/*if(!worldObj.isRemote) {

            EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldObj, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this, worldObj.getBlockMetadata(par2, par3, par4));
            worldObj.spawnEntityInWorld(entityfallingblock);
    	}*/
    	
    	/*if(!worldObj.isRemote) {
	    	EntityCloudTom tom = new EntityCloudTom(worldObj, 100);
	    	tom.setPosition(par2 + 0.5, par3 + 2, par4 + 0.5);
	    	worldObj.spawnEntityInWorld(tom);
    	}*/
    	
    	/*if(!worldObj.isRemote) {
    		
    		worldObj.setBlockToAir(par2, par3, par4);
    		ExplosionNT ex = new ExplosionNT(worldObj, null, par2 + 0.5, par3 + 2, par4 + 0.5, 5);
    		ex.addAttrib(ExAttrib.ALLDROP);
    		ex.doExplosionA();
    		ex.doExplosionB(false);
    	}*/
    	

		FMLNetworkHandler.openGui(par5EntityPlayer, MainRegistry.instance, -1, par1World, par2, par3, par4);
        
        return true;
    }
    
    public void buildEvent(World world, int x, int y, int z, int r) {
    	
    	Random rand = new Random();
    	
    	for(double h = r; h >= -r; h -= (0.2D + ((r - Math.abs(h)) / r * 0.6D))) {

			double sectionRad = Math.sqrt(Math.pow(r, 2) - Math.pow(h, 2));
			double circumference = 4 * Math.PI * sectionRad + rand.nextGaussian() * 0.5 + 0.25D;
			
			System.out.println(h + " " + circumference);
			
			for(int c = 0; c < circumference; c++) {
				
				Vec3 vec = Vec3.createVectorHelper(sectionRad, h, 0);
				vec = vec.normalize();
				//vec.rotateAroundZ((float) (h / sectionRad) * -2);
				vec.rotateAroundY((float) (360 / circumference * c));

				int dX = (int) Math.round(vec.xCoord * r + x);
				int dY = (int) Math.round(vec.yCoord * r + y);
				int dZ = (int) Math.round(vec.zCoord * r + z);
				
				world.setBlock(dX, dY, dZ, Blocks.gold_block);
			}
    	}
    }
	
	public void killEvent(World world, int x, int y, int z) {
		
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.break", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			float f = this.explosionSize;
	        int i;
	        int j;
	        int k;
	        double d5;
	        double d6;
	        double d7;
	        double wat = 20.0D;
	        

	        this.explosionSize *= 2.0F;
	        i = MathHelper.floor_double(x - wat - 1.0D);
	        j = MathHelper.floor_double(x + wat + 1.0D);
	        k = MathHelper.floor_double(y - wat - 1.0D);
	        int i2 = MathHelper.floor_double(y + wat + 1.0D);
	        int l = MathHelper.floor_double(z - wat - 1.0D);
	        int j2 = MathHelper.floor_double(z + wat + 1.0D);
	        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
	        Vec3 vec4 = Vec3.createVectorHelper(x, y + 1, z);

	        for (int i1 = 0; i1 < list.size(); ++i1)
	        {
	            Entity entity = (Entity)list.get(i1);
	            double d4 = entity.getDistance(x, y, z) / this.explosionSize;

	            if (d4 <= 1.0D)
	            {
	                d5 = entity.posX - x;
	                d6 = entity.posY + entity.getEyeHeight() - y;
	                d7 = entity.posZ - z;
	                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);

	                if (d9 < wat)
	                {
	                    d5 /= d9;
	                    d6 /= d9;
	                    d7 /= d9;
	                    double d10 = world.getBlockDensity(vec4, entity.boundingBox);
	                    double d11 = (1.0D - d4) * d10;
	                    //entity.attackEntityFrom(DamageSource.generic, (float)(100 - d9/wat*100/d10));
	                    
	                    if(!entity.worldObj.isRemote && !entity.isDead)
	                    {
	                    	entity.setFire(50);
	                    	//entity.setDead();
	                    }

	                    //entity.attackEntityFrom(DamageSource.generic, (float)((int)((d11 * d11 + d11) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D)));
	                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
	                    entity.motionX += d5 * d8;
	                    entity.motionY += d6 * d8;
	                    entity.motionZ += d7 * d8;

	                    if (entity instanceof EntityPlayer)
	                    {
	                        this.field_77288_k.put(entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
	                    }
	                }
	            }
	        }

	        this.explosionSize = f;
	}
}
