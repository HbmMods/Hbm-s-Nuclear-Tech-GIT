package com.hbm.items.special;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;

import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSpawner extends Item
{
	Class<? extends Entity> entity = null;
	
	public ItemSpawner()
	{
		setMaxStackSize(1);
	}
	
	public ItemSpawner(@Nonnull Class<? extends Entity> entityIn)
	{
		this();
		assert entityIn != null : "Class cannot be null!";
		assert Entity.class.isAssignableFrom(entityIn) : "Class must extend Entity!";
		entity = entityIn;
	}
	
    @Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World worldIn, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
            return true;
        else
        {
            Block block = worldIn.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double d0 = 0.0D;

            if (side == 1 && block.getRenderType() == 11)
                d0 = 0.5D;

            Entity entity = spawnCreature(worldIn, stack.getItemDamage(), x + 0.5D, y + d0, z + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                    ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());

                if (!player.capabilities.isCreativeMode)
                    --stack.stackSize;
            }

            return true;
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (worldIn.isRemote)
            return stack;
        else
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, player, true);

            if (movingobjectposition == null)
                return stack;
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!worldIn.canMineBlock(player, i, j, k))
                        return stack;

                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack))
                        return stack;

                    if (worldIn.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                        Entity entity = spawnCreature(worldIn, stack.getItemDamage(), i, j, k);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                                ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());

                            if (!player.capabilities.isCreativeMode)
                                --stack.stackSize;
                        }
                    }
                }

                return stack;
            }
        }
    }

    /**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    public Entity spawnCreature(World worldIn, int p_77840_1_, double x, double y, double z)
    {
    	Entity entityToSpawn = null;
    	try
    	{
    		entityToSpawn = entity.getConstructor(World.class).newInstance(worldIn);
    	}
    	catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException
    			| SecurityException | InvocationTargetException e)
    	{
    		MainRegistry.logger.catching(Level.ERROR, e);
    	}

        if (entityToSpawn != null && entityToSpawn instanceof EntityLivingBase)
        {
            EntityLiving entityliving = (EntityLiving)entityToSpawn;
            entityToSpawn.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onSpawnWithEgg((IEntityLivingData)null);
            worldIn.spawnEntityInWorld(entityToSpawn);
        }
        

        return entityToSpawn;
    }

}
