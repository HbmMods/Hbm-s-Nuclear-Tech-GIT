package com.hbm.blocks.bomb;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.logic.EntityMissileTest;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LaunchPad extends BlockContainer implements IBomb {

	public TileEntityLaunchPad tetn = new TileEntityLaunchPad();
	public static boolean keepInventory = false;
    private final static Random field_149933_a = new Random();

	public LaunchPad(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLaunchPad();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.launch_pad);
    }
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	TileEntityLaunchPad tileentityfurnace = (TileEntityLaunchPad)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = LaunchPad.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = LaunchPad.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = LaunchPad.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = LaunchPad.field_149933_a.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float)LaunchPad.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)LaunchPad.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)LaunchPad.field_149933_a.nextGaussian() * f3;
                            p_149749_1_.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityLaunchPad entity = (TileEntityLaunchPad) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_launch_pad, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z) && !p_149695_1_.isRemote)
        {
        	this.explode(p_149695_1_, x, y, z);
        }
    }
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}
	
	/*@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8*f, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}*/

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(ModBlocks.launch_pad);
    }
    
    @Override
	public void explode(World p_149695_1_, int x, int y, int z)
    {
		TileEntityLaunchPad entity = (TileEntityLaunchPad) p_149695_1_.getTileEntity(x, y, z);
        //
        {
        	if(entity.slots[1] != null && (entity.slots[1].getItem() == ModItems.designator || entity.slots[1].getItem() == ModItems.designator_range || entity.slots[1].getItem() == ModItems.designator_manual) && entity.slots[1].stackTagCompound != null)
        	{
        		int xCoord = entity.slots[1].stackTagCompound.getInteger("xCoord");
        		int zCoord = entity.slots[1].stackTagCompound.getInteger("zCoord");
        		
        		if(xCoord == entity.xCoord && zCoord == entity.zCoord)
        		{
        			xCoord += 1;
        		}

	    		if(MainRegistry.enableExtendedLogging)
	    			MainRegistry.logger.log(Level.INFO, "[MISSILE] Tried to launch missile at " + x + " / " + y + " / " + z + " to " + xCoord + " / " + zCoord + "!");
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_generic && entity.power >= 75000)
        		{
            		//EntityMissileGeneric missile = new EntityMissileGeneric(p_149695_1_, xCoord, zCoord, x + 0.5F, y + 2F, z + 0.5F);
            		EntityMissileGeneric missile = new EntityMissileGeneric(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_incendiary && entity.power >= 75000)
        		{
            		EntityMissileIncendiary missile = new EntityMissileIncendiary(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_cluster && entity.power >= 75000)
        		{
            		EntityMissileCluster missile = new EntityMissileCluster(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_buster && entity.power >= 75000)
        		{
            		EntityMissileBunkerBuster missile = new EntityMissileBunkerBuster(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_strong && entity.power >= 75000)
        		{
            		EntityMissileStrong missile = new EntityMissileStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_incendiary_strong && entity.power >= 75000)
        		{
            		EntityMissileIncendiaryStrong missile = new EntityMissileIncendiaryStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_cluster_strong && entity.power >= 75000)
        		{
            		EntityMissileClusterStrong missile = new EntityMissileClusterStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_buster_strong && entity.power >= 75000)
        		{
            		EntityMissileBusterStrong missile = new EntityMissileBusterStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_burst && entity.power >= 75000)
        		{
            		EntityMissileBurst missile = new EntityMissileBurst(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_inferno && entity.power >= 75000)
        		{
            		EntityMissileInferno missile = new EntityMissileInferno(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_rain && entity.power >= 75000)
        		{
            		EntityMissileRain missile = new EntityMissileRain(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_drill && entity.power >= 75000)
        		{
            		EntityMissileDrill missile = new EntityMissileDrill(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_nuclear && entity.power >= 75000)
        		{
            		EntityMissileNuclear missile = new EntityMissileNuclear(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_endo && entity.power >= 75000)
        		{
            		EntityMissileEndo missile = new EntityMissileEndo(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_exo && entity.power >= 75000)
        		{
            		EntityMissileExo missile = new EntityMissileExo(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_nuclear_cluster && entity.power >= 75000)
        		{
            		EntityMissileMirv missile = new EntityMissileMirv(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_doomsday && entity.power >= 75000)
        		{
            		EntityMissileDoomsday missile = new EntityMissileDoomsday(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_taint && entity.power >= 75000)
        		{
            		EntityMissileTaint missile = new EntityMissileTaint(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_micro && entity.power >= 75000)
        		{
            		EntityMissileMicro missile = new EntityMissileMicro(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_bhole && entity.power >= 75000)
        		{
            		EntityMissileBHole missile = new EntityMissileBHole(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_schrabidium && entity.power >= 75000)
        		{
            		EntityMissileSchrabidium missile = new EntityMissileSchrabidium(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_emp && entity.power >= 75000)
        		{
            		EntityMissileEMP missile = new EntityMissileEMP(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_emp_strong && entity.power >= 75000)
        		{
            		EntityMissileEMPStrong missile = new EntityMissileEMPStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.power -= 75000;
            		
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        	}

    		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_carrier && entity.power >= 75000)
    		{
    			EntityCarrier missile = new EntityCarrier(p_149695_1_);
    			missile.posX = x + 0.5F;
    			missile.posY = y + 1F;
    			missile.posZ = z + 0.5F;
    			
    			if(entity.slots[1] != null)
    				missile.setPayload(entity.slots[1]);
    			
    			entity.slots[1] = null;
    			
        		if (!p_149695_1_.isRemote)
        			p_149695_1_.spawnEntityInWorld(missile);
        		entity.power -= 75000;
        		
        		entity.slots[0] = null;
        		p_149695_1_.playSoundEffect(x, y, z, "hbm:entity.rocketTakeoff", 100.0F, 1.0F);
    		}

    		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_anti_ballistic && entity.power >= 75000)
    		{
    			EntityMissileAntiBallistic missile = new EntityMissileAntiBallistic(p_149695_1_);
    			missile.posX = x + 0.5F;
    			missile.posY = y + 0.5F;
    			missile.posZ = z + 0.5F;
    			
        		if (!p_149695_1_.isRemote)
        			p_149695_1_.spawnEntityInWorld(missile);
        		
        		entity.power -= 75000;
        		
        		entity.slots[0] = null;
        		p_149695_1_.playSoundEffect(x, y, z, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
    		}
        }
    }

}
