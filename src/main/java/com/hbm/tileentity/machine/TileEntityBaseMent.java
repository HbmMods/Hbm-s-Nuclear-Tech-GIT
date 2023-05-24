package com.hbm.tileentity.machine;

import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.mob.EntityFBI;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;

public class TileEntityBaseMent extends TileEntity {

    public static int age = 0;
    public int missileage = 0;
    public int times = 5;
    public int radius = 80;
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            age++;
            missileage++;
                List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
                        AxisAlignedBB.getBoundingBox(this.xCoord - radius, this.yCoord - radius, this.zCoord - radius, this.xCoord + radius,
                                this.yCoord + radius, this.zCoord + radius));
                List<EntityFBI> enermys = this.worldObj.getEntitiesWithinAABB(EntityFBI.class,
                        AxisAlignedBB.getBoundingBox(this.xCoord - radius/2, this.yCoord - radius/2, this.zCoord - radius/2, this.xCoord + radius/2,
                                this.yCoord + radius/2, this.zCoord + radius/2));
                for(EntityPlayer eplayer : players) {
                    double distance = eplayer.getDistance(this.xCoord, this.yCoord, this.zCoord);
                    if (players.size() > 0){
                       if (times > 0 && enermys.size() < 2 && distance < 30 && age > 100) {
                            times--;
                            spawnFBI(this.worldObj, 10, this.xCoord, this.yCoord, this.zCoord);
                            eplayer.addChatMessage(new ChatComponentText("Enemy gain arrives!"));
                            age = 0;
                        }
                        else if (distance > 30 && missileage > 500) {
                            spawnmissile(this.worldObj,this.xCoord,this.yCoord,this.zCoord,(int)eplayer.posX,(int)eplayer.posZ);
                            eplayer.addChatMessage(new ChatComponentText("Watch your head!"));
                            missileage = 0;
                        }
                    }
                }
        }
    }

    public boolean canSpawn(World world, int x,int y,int z) {
        if(world.getBlock(x,y,z) == Blocks.air && world.getBlock(x,y+1,z) == Blocks.air && world.getBlock(x,y-1,z) != Blocks.air) {
            return true;
        }
        return false;
    }
    public boolean canSpawnMissile(World world, int x,int y,int z) {
        for (int i = 0; i <6;i++) {
            if(world.getBlock(x,y+i,z) != Blocks.air ) {
                return false;
            }
        }
        return true;
    }

    public void spawnFBI(World world,int spanwnNumber , double x,double y,double z ){
        if(!world.isRemote){
            for(int i = 0; i<spanwnNumber;) {
                EntityLiving FBI = new EntityFBI(world);
                double spawnx = x + world.rand.nextGaussian()*5;
                double spawny = y + world.rand.nextGaussian()*5;
                double spawnz = z + world.rand.nextGaussian()*5;
                Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(FBI, world, (float) spawnx, (float) spawny, (float) spawnz);
                if (canSpawn == Event.Result.ALLOW || canSpawn == Event.Result.DEFAULT&& canSpawn(world,(int)spawnx,(int)spawny,(int)spawnz)) {
                    i++;
                    FBI.setLocationAndAngles(spawnx, spawny, spawnz, world.rand.nextFloat() * 360.0F, 0.0F);
                    world.spawnEntityInWorld(FBI);
                    ForgeEventFactory.doSpecialSpawn(FBI, world, (float) spawnx, (float) spawny, (float) spawnz);
                    FBI.onSpawnWithEgg(null);
                }
            }
        }
    }
    public void spawnmissile(World world, double x,double y,double z,int targetx,int targetz) {
        boolean launched = false;
        while (!launched) {
            double spawnx = x + world.rand.nextGaussian()*5;
            double spawny = y + world.rand.nextGaussian()*5;
            double spawnz = z + world.rand.nextGaussian()*5;
            if(canSpawnMissile(world, (int)spawnx, (int)spawny, (int)spawnz)) {
                Entity missile = new EntityMissileIncendiary(world, (float) spawnx, (float) spawny, (float) spawnz, targetx+(int)world.rand.nextGaussian()*5, targetz+(int)world.rand.nextGaussian()*5);
                this.worldObj.spawnEntityInWorld(missile);
                launched = true;
            }
        }
    }
}



