package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Balefire extends BlockFire {
	
	private IIcon field_149850_M;

    public Balefire()
    {
        super();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    	
    	field_149850_M = p_149651_1_.registerIcon(this.getTextureName());
    }

    @SideOnly(Side.CLIENT)
    public IIcon getFireIcon(int p_149840_1_)
    {
        return field_149850_M;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return field_149850_M;
    }
    public void updateTick(World world, int x, int y, int z, Random p_149674_5_)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            boolean flag = world.getBlock(x, y - 1, z).isFireSource(world, x, y - 1, z, UP);

            if (!this.canPlaceBlockAt(world, x, y, z))
            {
                world.setBlockToAir(x, y, z);
            }

            /*if (!flag && p_149674_1_.isRaining() && (p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ - 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ + 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ - 1) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ + 1)))
            {
                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
            }
            else*/
            {
            	int l = 0;
                /*int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

                if (l < 15)
                {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l + p_149674_5_.nextInt(3) / 2, 4);
                }*/

                world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + p_149674_5_.nextInt(10));

                if (!flag && !this.canNeighborBurn(world, x, y, z))
                {
                    if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)/* || l > 3*/)
                    {
                        world.setBlockToAir(x, y, z);
                    }
                }
                /*else if (!flag && !this.canCatchFire(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, UP) && l == 15 && p_149674_5_.nextInt(4) == 0)
                {
                    //p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
                }*/
                else
                {
                    //boolean flag1 = p_149674_1_.isBlockHighHumidity(p_149674_2_, p_149674_3_, p_149674_4_);
                    byte b0 = 0;

                    /*if (flag1)
                    {
                        b0 = -50;
                    }*/

                    this.tryCatchFire(world, x + 1, y, z, 300 + b0, p_149674_5_, l, WEST );
                    this.tryCatchFire(world, x - 1, y, z, 300 + b0, p_149674_5_, l, EAST );
                    this.tryCatchFire(world, x, y - 1, z, 250 + b0, p_149674_5_, l, UP   );
                    this.tryCatchFire(world, x, y + 1, z, 250 + b0, p_149674_5_, l, DOWN );
                    this.tryCatchFire(world, x, y, z - 1, 300 + b0, p_149674_5_, l, SOUTH);
                    this.tryCatchFire(world, x, y, z + 1, 300 + b0, p_149674_5_, l, NORTH);

                    for (int i1 = x - 1; i1 <= x + 1; ++i1)
                    {
                        for (int j1 = z - 1; j1 <= z + 1; ++j1)
                        {
                            for (int k1 = y - 1; k1 <= y + 4; ++k1)
                            {
                                if (i1 != x || k1 != y || j1 != z)
                                {
                                    int l1 = 100;

                                    if (k1 > y + 1)
                                    {
                                        l1 += (k1 - (y + 1)) * 100;
                                    }

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

                                    if (i2 > 0)
                                    {
                                        int j2 = (i2 + 40 + world.difficultySetting.getDifficultyId() * 7) / (l + 30);

                                        /*if (flag1)
                                        {
                                            j2 /= 2;
                                        }*/

                                        if (j2 > 0 && p_149674_5_.nextInt(l1) <= j2)
                                        {
                                            int k2 = l + p_149674_5_.nextInt(5) / 4;

                                            if (k2 > 15)
                                            {
                                                k2 = 15;
                                            }

                                            world.setBlock(i1, k1, j1, this, k2, 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ChunkRadiationManager.proxy.incrementRad(world, x, y, z, 0.5F);
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

    private void tryCatchFire(World p_149841_1_, int p_149841_2_, int p_149841_3_, int p_149841_4_, int p_149841_5_, Random p_149841_6_, int p_149841_7_, ForgeDirection face)
    {
        int j1 = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_).getFlammability(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, face);

        if (p_149841_6_.nextInt(p_149841_5_) < j1)
        {
            boolean flag = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_) == Blocks.tnt;

            p_149841_1_.setBlock(p_149841_2_, p_149841_3_, p_149841_4_, this, 15, 3);

            if (flag)
            {
                Blocks.tnt.onBlockDestroyedByPlayer(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, 1);
            }
        }
    }

    private boolean canNeighborBurn(World p_149847_1_, int p_149847_2_, int p_149847_3_, int p_149847_4_)
    {
        return this.canCatchFire(p_149847_1_, p_149847_2_ + 1, p_149847_3_, p_149847_4_, WEST ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_ - 1, p_149847_3_, p_149847_4_, EAST ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_ - 1, p_149847_4_, UP   ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_ + 1, p_149847_4_, DOWN ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ - 1, SOUTH) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ + 1, NORTH);
    }

    private int getChanceOfNeighborsEncouragingFire(World p_149845_1_, int p_149845_2_, int p_149845_3_, int p_149845_4_)
    {
        byte b0 = 0;

        if (!p_149845_1_.isAirBlock(p_149845_2_, p_149845_3_, p_149845_4_))
        {
            return 0;
        }
        else
        {
            int l = b0;
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_ + 1, p_149845_3_, p_149845_4_, l, WEST );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_ - 1, p_149845_3_, p_149845_4_, l, EAST );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_ - 1, p_149845_4_, l, UP   );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_ + 1, p_149845_4_, l, DOWN );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ + 1, l, NORTH);
            return l;
        }
    }
    
    public boolean canCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return world.getBlock(x, y, z).isFlammable(world, x, y, z, face);
    }
    
    @Override
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        p_149670_5_.setFire(10);
    	
    	if(p_149670_5_ instanceof EntityLivingBase)
    		((EntityLivingBase)p_149670_5_).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 5 * 20, 9));
    }

}
