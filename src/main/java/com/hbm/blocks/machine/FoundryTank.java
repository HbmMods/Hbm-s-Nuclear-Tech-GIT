package com.hbm.blocks.machine;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.machine.ItemScraps;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFoundryTank;

import api.hbm.block.ICrucibleAcceptor;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FoundryTank extends BlockContainer implements ICrucibleAcceptor {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconSideOutlet;
	@SideOnly(Side.CLIENT) public IIcon iconSideUpper;
	@SideOnly(Side.CLIENT) public IIcon iconSideUpperOutlet;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInner;
	@SideOnly(Side.CLIENT) public IIcon iconLava;

	public FoundryTank() {
		super(Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_side");
		this.iconSideOutlet = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_side_outlet");
		this.iconSideUpper = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_upper");
		this.iconSideUpperOutlet = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_upper_outlet");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_bottom");
		this.iconInner = iconRegister.registerIcon(RefStrings.MODID + ":foundry_tank_inner");
		this.iconLava = iconRegister.registerIcon(RefStrings.MODID + ":lava_gray");
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundryTank();
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).pour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return stack; }

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}
		
		TileEntityFoundryTank cast = (TileEntityFoundryTank) world.getTileEntity(x, y, z);
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTool && ((ItemTool) player.getHeldItem().getItem()).getToolClasses(player.getHeldItem()).contains("shovel")) {
			if(cast.amount > 0) {
				ItemStack scrap = ItemScraps.create(new MaterialStack(cast.type, cast.amount));
				if(!player.inventory.addItemStackToInventory(scrap)) {
					EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
					world.spawnEntityInWorld(item);
				} else {
					player.inventoryContainer.detectAndSendChanges();
				}
				cast.amount = 0;
				cast.type = null;
				cast.markDirty();
				world.markBlockForUpdate(x, y, z);
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {
		
		TileEntityFoundryTank tank = (TileEntityFoundryTank) world.getTileEntity(x, y, z);
		if(tank.amount > 0) {
			ItemStack scrap = ItemScraps.create(new MaterialStack(tank.type, tank.amount));
			EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
			world.spawnEntityInWorld(item);
			tank.amount = 0;
		}
		
		super.breakBlock(world, x, y, z, b, i);
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.UP;
	}
}
