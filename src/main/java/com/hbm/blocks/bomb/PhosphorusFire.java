package com.hbm.blocks.bomb;

import java.util.Random;

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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PhosphorusFire extends BlockFire
{
	private IIcon icon;
	public PhosphorusFire()
	{
		super();
	}
	
	@Override
	public void updateTick(World worldIn, int x, int y, int z, Random rand)
	{
		if (worldIn.getGameRules().getGameRuleBooleanValue("doFireTick"))
		{
			boolean flag = worldIn.getBlock(x, y - 1, z).isFireSource(worldIn, x, y, z, ForgeDirection.UP);
			
			if (!canPlaceBlockAt(worldIn, x, y, z))
				worldIn.setBlockToAir(x, y, z);
			
			int l = 0;
			
			worldIn.scheduleBlockUpdate(x, y, z, this, this.tickRate(worldIn) + rand.nextInt(10));
			
			if (!flag && !canNeighborBurn(worldIn, x, y, z))
				if (!World.doesBlockHaveSolidTopSurface(worldIn, x, y, z - 1))
					worldIn.setBlockToAir(x, y, z);
			else
			{
				byte b1 = 0;
				
				tryCatchFire(worldIn, x + 1, y, z, 300 + b1, rand, ForgeDirection.WEST);
				tryCatchFire(worldIn, x - 1, y, z, 300 + b1, rand, ForgeDirection.EAST);
				tryCatchFire(worldIn, x, y - 1, z, 250 + b1, rand, ForgeDirection.UP);
				tryCatchFire(worldIn, x, y + 1, z, 250 + b1, rand, ForgeDirection.DOWN);
				tryCatchFire(worldIn, x, y, z + 1, 300 + b1, rand, ForgeDirection.NORTH);
				tryCatchFire(worldIn, x, y, z - 1, 300 + b1, rand, ForgeDirection.SOUTH);
				
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
                                    l1 += (k1 - (y + 1)) * 100;

                                int i2 = getChanceOfNeighborsEncouragingFire(worldIn, i1, k1, j1);

                                if (i2 > 0)
                                {
                                    int j2 = (i2 + 40 + worldIn.difficultySetting.getDifficultyId() * 7) / (l + 30);

                                    if (j2 > 0 && rand.nextInt(l1) <= j2)
                                    {
                                        int k2 = l + rand.nextInt(5) / 4;

                                        if (k2 > 15)
                                        {
                                            k2 = 15;
                                        }

                                        worldIn.setBlock(i1, k1, j1, this, k2, 3);
                                    }
                                }
                            }
                        }
                    }
                }
			}
		}
	}
	
	private boolean canNeighborBurn(World worldIn, int x, int y, int z)
	{
		return canCatchFire(worldIn, x + 1, y, z, ForgeDirection.WEST) ||
				canCatchFire(worldIn, x - 1, y, z, ForgeDirection.EAST) ||
				canCatchFire(worldIn, x, y - 1, z, ForgeDirection.UP) ||
				canCatchFire(worldIn, x, y + 1, z, ForgeDirection.DOWN) ||
				canCatchFire(worldIn, x, y, z + 1, ForgeDirection.NORTH) ||
				canCatchFire(worldIn, x, y, z - 1, ForgeDirection.SOUTH);
	}
	
	private void tryCatchFire(World worldIn, int x, int y, int z, int randBound, Random rand, ForgeDirection face)
	{
		int flammability = worldIn.getBlock(x, y, z).getFlammability(worldIn, x, y, z, face);
		
		if (rand.nextInt(randBound) < flammability)
		{
			boolean flag = worldIn.getBlock(x, y, z) == Blocks.tnt;
			worldIn.setBlock(x, y, z, this, 15, 3);
			if (flag)
				Blocks.tnt.onBlockDestroyedByPlayer(worldIn, x, y, z, 1);
		}
	}
	
	private int getChanceOfNeighborsEncouragingFire(World worldIn, int x, int y, int z)
	{
		byte b1 = 0;
		if (!worldIn.isAirBlock(x, y, z))
			return 0;
		else
		{
			int chance = b1;
			chance = getChanceToEncourageFire(worldIn, x + 1, y, z, chance, ForgeDirection.WEST);
			chance = getChanceToEncourageFire(worldIn, x - 1, y, z, chance, ForgeDirection.EAST);
			chance = getChanceToEncourageFire(worldIn, x, y - 1, z, chance, ForgeDirection.UP);
			chance = getChanceToEncourageFire(worldIn, x, y + 1, z, chance, ForgeDirection.DOWN);
			chance = getChanceToEncourageFire(worldIn, x, y, z - 1, chance, ForgeDirection.SOUTH);
			chance = getChanceToEncourageFire(worldIn, x, y, z + 1, chance, ForgeDirection.NORTH);
			return chance;
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z,
			Entity entity)
	{
		entity.setFire(15);
		if (entity instanceof EntityLivingBase)
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(HbmPotion.phosphorus.id, 15 * 20, 2));
	}
	
	private boolean isFlammable(World worldIn, int x, int y, int z)
	{
		return worldIn.getBlock(x, y, z).getMaterial().getCanBurn();
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icon = reg.registerIcon(getTextureName());
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getFireIcon(int p_149840_1_)
	{
		return icon;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return icon;
	}
}
