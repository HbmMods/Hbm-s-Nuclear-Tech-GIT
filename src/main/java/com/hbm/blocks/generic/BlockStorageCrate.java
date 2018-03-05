package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCrateIron;
import com.hbm.tileentity.machine.TileEntityCrateSteel;
import com.hbm.tileentity.machine.TileEntityMachineOilWell;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockStorageCrate extends BlockContainer {

    private final Random field_149933_a = new Random();
	private Random rand;
	private static boolean keepInventory;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockStorageCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		if(this == ModBlocks.crate_iron)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_side");
		}
		if(this == ModBlocks.crate_steel)
		{
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.crate_iron)
			return new TileEntityCrateIron();
		if(this == ModBlocks.crate_steel)
			return new TileEntityCrateSteel();
		return null;
	}
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	ISidedInventory tileentityfurnace = (ISidedInventory)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.field_149933_a.nextInt(21) + 10;

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
                            entityitem.motionX = (float)this.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)this.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)this.field_149933_a.nextGaussian() * f3;
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
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityCrateIron)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_crate_iron, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateSteel)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_crate_steel, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

}
