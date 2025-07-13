package com.hbm.blocks.network;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.TileEntityDroneDock;
import com.hbm.tileentity.network.TileEntityDroneProvider;
import com.hbm.tileentity.network.TileEntityDroneRequester;
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

import java.util.List;
import java.util.Random;

public class DroneDock extends BlockContainer implements ITooltipProvider {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public DroneDock() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(this == ModBlocks.drone_dock) return new TileEntityDroneDock();
		if(this == ModBlocks.drone_crate_provider) return new TileEntityDroneProvider();
		if(this == ModBlocks.drone_crate_requester) return new TileEntityDroneRequester();

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.textureName + "_side");
		this.iconTop = reg.registerIcon(this.textureName + "_top");
		this.iconBottom = reg.registerIcon(this.textureName + "_bottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(this == ModBlocks.drone_dock) this.dropContents(world, x, y, z, block, meta, 0, 9);
		if(this == ModBlocks.drone_crate_provider) this.dropContents(world, x, y, z, block, meta, 0, 9);
		if(this == ModBlocks.drone_crate_requester) this.dropContents(world, x, y, z, block, meta, 9, 18);
		super.breakBlock(world, x, y, z, block, meta);
	}

	private final Random rand = new Random();
	public void dropContents(World world, int x, int y, int z, Block block, int meta, int start, int end) {
		ISidedInventory sidedInventory = (ISidedInventory) world.getTileEntity(x, y, z);

		if(sidedInventory != null) {
			
			for(int i1 = start; i1 < end; ++i1) {
				ItemStack stack = sidedInventory.getStackInSlot(i1);

				if(stack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

					while(stack.stackSize > 0) {
						int j1 = this.rand.nextInt(21) + 10;

						if(j1 > stack.stackSize) {
							j1 = stack.stackSize;
						}

						stack.stackSize -= j1;
						EntityItem entity = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), j1, stack.getItemDamage()));

						if(stack.hasTagCompound()) {
							entity.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entity.motionX = (float) this.rand.nextGaussian() * f3;
						entity.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entity.motionZ = (float) this.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entity);
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}
	}
}
