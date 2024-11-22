package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityFurnaceBrick;
import com.hbm.util.ItemStackUtil;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class MachineBrickFurnace extends BlockContainer {

	private final Random rand = new Random();
	private final boolean isActive;
	private static boolean keepInventory;

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;
	@SideOnly(Side.CLIENT) private IIcon iconFront;

	public MachineBrickFurnace(boolean blockState) {
		super(Material.iron);
		isActive = blockState;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_furnace_brick_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":machine_furnace_brick_bottom");
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":machine_furnace_brick_front_on" : ":machine_furnace_brick_front_off"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_furnace_brick_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : (side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon)));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFurnaceBrick();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ModBlocks.machine_furnace_brick_off);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote) {
			Block nZ = world.getBlock(x, y, z - 1);
			Block pZ = world.getBlock(x, y, z + 1);
			Block nX = world.getBlock(x - 1, y, z);
			Block pX = world.getBlock(x + 1, y, z);

			byte meta = 3;

			if(nZ.func_149730_j() && !pZ.func_149730_j()) meta = 3;
			if(pZ.func_149730_j() && !nZ.func_149730_j()) meta = 2;
			if(nX.func_149730_j() && !pX.func_149730_j()) meta = 5;
			if(pX.func_149730_j() && !nX.func_149730_j()) meta = 4;

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);

		if(itemStack.hasDisplayName()) ((TileEntityFurnaceBrick)world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityFurnaceBrick entity = (TileEntityFurnaceBrick) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	public static void updateBlockState(boolean isProcessing, World world, int x, int y, int z) {
		int i = world.getBlockMetadata(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);
		keepInventory = true;

		if(isProcessing) {
			world.setBlock(x, y, z, ModBlocks.machine_furnace_brick_on);
		} else {
			world.setBlock(x, y, z, ModBlocks.machine_furnace_brick_off);
		}

		keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, i, 2);

		if(entity != null) {
			entity.validate();
			world.setTileEntity(x, y, z, entity);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(!keepInventory) ItemStackUtil.spillItems(world, x, y, z, block, rand);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if(isActive) {
			int meta = world.getBlockMetadata(x, y, z);
			float cX = x + 0.5F;
			float cY = y + rand.nextFloat() * 0.375F;
			float cZ = z + 0.5F;
			float off = 0.52F;
			float var = rand.nextFloat() * 0.6F - 0.3F;
			rand.nextFloat();
			rand.nextFloat();

			if(meta == 4) {
				world.spawnParticle("smoke", cX - off, cY, cZ + var, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", cX - off, cY, cZ + var, 0.0D, 0.0D, 0.0D);
			} else if(meta == 5) {
				world.spawnParticle("smoke", cX + off, cY, cZ + var, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", cX + off, cY, cZ + var, 0.0D, 0.0D, 0.0D);
			} else if(meta == 2) {
				world.spawnParticle("smoke", cX + var, cY, cZ - off, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", cX + var, cY, cZ - off, 0.0D, 0.0D, 0.0D);
			} else if(meta == 3) {
				world.spawnParticle("smoke", cX + var, cY, cZ + off, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", cX + var, cY, cZ + off, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
