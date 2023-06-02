package com.hbm.blocks.siege;

import api.hbm.block.IToolable;
import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.items.ModItems;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;


public class Base extends BlockContainer implements ITooltipProvider,IBlockMulti {
    public Base() {
        super(Material.iron);
        this.setResistance(10.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBaseMent();
    }
    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        spawnFBI(world, 20, x, y ,z);
        super.onBlockDestroyedByPlayer(world, x, y, z, meta);
    }
    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        spawnFBI(world, 20, x, y ,z);
        super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(player.getCurrentEquippedItem() != null && IToolable.ToolType.getType(player.getCurrentEquippedItem()) == IToolable.ToolType.SCREWDRIVER){
            EntityItem entityitem = new EntityItem(world,  x,  y,  z,new ItemStack(ModItems.circuit_copper,10));
            if(!world.isRemote)
            world.spawnEntityInWorld(entityitem);
            world.setBlock(x,  y,  z,Blocks.air);
            return true;
           }
        return false;
    }
        @Override
    public int getSubCount() {
        return 3;
    }
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0; i < 3; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
        int meta = stack.getItemDamage();
        list.add(EnumChatFormatting.RED + "FBI");
        if(meta == canSpawnMissle) {
            list.add(EnumChatFormatting.GOLD + "missle basement");
        }
        else if(meta == canSpawnShell) {
            list.add(EnumChatFormatting.GOLD + "shell basement");
        }
    }


    public static final int canSpawnMissle = 1;
    public static final int canSpawnShell = 2;



    public static class TileEntityBaseMent extends TileEntity {

        public static int age = 0;
        public int missileage = 0;
        public int play = 70;

        public int times = 5;
        public int radius = 80;
        //public int type = 2;
        //public void setType(int type) {
            //this.type = type;
        //}
        public void updateEntity() {
            if (!this.worldObj.isRemote) {
                age++;
                missileage++;
                play--;
                List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
                        AxisAlignedBB.getBoundingBox(this.xCoord - radius, this.yCoord - radius, this.zCoord - radius, this.xCoord + radius,
                                this.yCoord + radius, this.zCoord + radius));
                List<EntityFBI> enermys = this.worldObj.getEntitiesWithinAABB(EntityFBI.class,
                        AxisAlignedBB.getBoundingBox(this.xCoord - radius/2, this.yCoord - radius/2, this.zCoord - radius/2, this.xCoord + radius/2,
                                this.yCoord + radius/2, this.zCoord + radius/2));
                for(EntityPlayer eplayer : players) {
                    double distance = eplayer.getDistance(this.xCoord, this.yCoord, this.zCoord);
                    if (players.size() > 0){
                        if(distance < 30) {
                            if(play<10){
                                this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:alarm.klaxon" , 4.0F, 1.0F);
                            }
                            if(play<=0){
                                play=70;
                            }
                        }
                        if (times > 0 && enermys.size() < 2 && distance < 30 && age > 100) {
                            times--;
                            spawnFBI(this.worldObj, 10, this.xCoord, this.yCoord, this.zCoord);
                            eplayer.addChatMessage(new ChatComponentText("Enemy gain arrives!"));
                            age = 0;
                        }
                        else if (distance > 30 && missileage > 300 && isMissile()) {
                            spawnmissile(this.worldObj,this.xCoord,this.yCoord,this.zCoord,(int)eplayer.posX,(int)eplayer.posZ);
                            eplayer.addChatMessage(new ChatComponentText("Watch your head!"));
                            missileage = 0;
                        }
                        else if (distance > 30 && missileage > 300 && isShell()) {
                            spawnshell(this.worldObj,this.xCoord,this.yCoord,this.zCoord,(int)eplayer.posX,(int)eplayer.posZ);
                            eplayer.addChatMessage(new ChatComponentText("Watch your head!"));
                            missileage = 0;
                        }
                    }
                }
            }
        }
        private boolean isMissile() {
            int meta = this.getBlockMetadata();
            return meta == 1;
        }
        private boolean isShell() {
            int meta = this.getBlockMetadata();
            return meta == 2;
        }
    }
    public static void spawnFBI(World world, int spanwnNumber, double x, double y, double z){
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
    public static void spawnmissile(World world, double x, double y, double z, int targetx, int targetz) {
        boolean launched = false;
        while (!launched) {
            double spawnx = x + world.rand.nextGaussian()*5;
            double spawny = y + world.rand.nextGaussian()*5;
            double spawnz = z + world.rand.nextGaussian()*5;
            if(canSpawnMissile(world, (int)spawnx, (int)spawny, (int)spawnz)) {
                Entity missile = new EntityMissileIncendiary(world, (float) spawnx, (float) spawny, (float) spawnz, targetx+(int)world.rand.nextGaussian()*5, targetz+(int)world.rand.nextGaussian()*5);
                world.spawnEntityInWorld(missile);
                launched = true;
            }
        }
    }
    public static void spawnshell(World world, double x, double y, double z, int targetx, int targetz) {
        boolean launched = false;
        while (!launched) {
            double spawnx = x + world.rand.nextGaussian()*5;
            double spawny = y + world.rand.nextGaussian()*5;
            double spawnz = z + world.rand.nextGaussian()*5;
            if(canSpawnMissile(world, (int)spawnx, (int)spawny, (int)spawnz)) {
                Entity shell = new EntityMissileCluster(world, (float) spawnx, (float) spawny, (float) spawnz, targetx+(int)world.rand.nextGaussian()*5, targetz+(int)world.rand.nextGaussian()*5);
                world.spawnEntityInWorld(shell);
                launched = true;
            }
        }
    }
    public static boolean canSpawn(World world, int x, int y, int z) {
        if(world.getBlock(x,y,z) == Blocks.air && world.getBlock(x,y+1,z) == Blocks.air && world.getBlock(x,y-1,z) != Blocks.air) {
            return true;
        }
        return false;
    }
    public static boolean canSpawnMissile(World world, int x, int y, int z) {
        for (int i = 0; i <6;i++) {
            if(world.getBlock(x,y+i,z) != Blocks.air) {
                return false;
            }
        }
        return true;
    }
}


