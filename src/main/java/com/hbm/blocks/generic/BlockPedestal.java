package com.hbm.blocks.generic;

import com.hbm.inventory.recipes.PedestalRecipes;
import com.hbm.inventory.recipes.PedestalRecipes.PedestalRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.particle.helper.ExplosionSmallCreator;
import com.hbm.util.Compat;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPedestal extends BlockContainer {

	protected IIcon iconSide;

	public BlockPedestal() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPedestal();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":pedestal_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? this.blockIcon : this.iconSide;
	}

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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;
		
		TileEntityPedestal pedestal = (TileEntityPedestal) world.getTileEntity(x, y, z);
		
		if(pedestal.item == null && player.getHeldItem() != null) {
			pedestal.item = player.getHeldItem().copy();
			player.inventory.mainInventory[player.inventory.currentItem] = null;
			pedestal.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		} else if(pedestal.item != null && player.getHeldItem() == null) {
			player.inventory.mainInventory[player.inventory.currentItem] = pedestal.item.copy();
			pedestal.item = null;
			pedestal.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		if(!world.isRemote) {
			TileEntityPedestal entity = (TileEntityPedestal) world.getTileEntity(x, y, z);
			if(entity != null && entity.item != null) {
				EntityItem item = new EntityItem(world, x + 0.5, y, z + 0.5, entity.item.copy());
				world.spawnEntityInWorld(item);
			}
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		if(!world.isRemote) {
			if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
				
				TileEntityPedestal nw = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.NORTH.offsetX * 2 + ForgeDirection.WEST.offsetX * 2, y, z + ForgeDirection.NORTH.offsetZ * 2 + ForgeDirection.WEST.offsetZ * 2));
				TileEntityPedestal n = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.NORTH.offsetX * 3, y, z + ForgeDirection.NORTH.offsetZ * 3));
				TileEntityPedestal ne = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.NORTH.offsetX * 2 + ForgeDirection.EAST.offsetX * 2, y, z + ForgeDirection.NORTH.offsetZ * 2 + ForgeDirection.EAST.offsetZ * 2));
				TileEntityPedestal w = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.WEST.offsetX * 3, y, z + ForgeDirection.WEST.offsetZ * 3));
				TileEntityPedestal center = (TileEntityPedestal) world.getTileEntity(x, y, z);
				TileEntityPedestal e = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.EAST.offsetX * 3, y, z + ForgeDirection.EAST.offsetZ * 3));
				TileEntityPedestal sw = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.SOUTH.offsetX * 2 + ForgeDirection.WEST.offsetX * 2, y, z + ForgeDirection.SOUTH.offsetZ * 2 + ForgeDirection.WEST.offsetZ * 2));
				TileEntityPedestal s = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.SOUTH.offsetX * 3, y, z + ForgeDirection.SOUTH.offsetZ * 3));
				TileEntityPedestal se = castOrNull(Compat.getTileStandard(world, x + ForgeDirection.SOUTH.offsetX * 2 + ForgeDirection.EAST.offsetX * 2, y, z + ForgeDirection.SOUTH.offsetZ * 2 + ForgeDirection.EAST.offsetZ * 2));
				
				TileEntityPedestal[] tileArray = new TileEntityPedestal[] {nw, n, ne, w, center, e, sw, s, se};
				
				outer: for(PedestalRecipe recipe : PedestalRecipes.recipes) {
					
					if(recipe.extra == recipe.extra.FULL_MOON) {
						if(world.getCelestialAngle(0) < 0.35 || world.getCelestialAngle(0) > 0.65) continue;
						if(world.provider.getMoonPhase(world.getWorldInfo().getWorldTime()) != 0) continue;
					}
					
					if(recipe.extra == recipe.extra.NEW_MOON) {
						if(world.getCelestialAngle(0) < 0.35 || world.getCelestialAngle(0) > 0.65) continue;
						if(world.provider.getMoonPhase(world.getWorldInfo().getWorldTime()) != 4) continue;
					}
					
					if(recipe.extra == recipe.extra.SUN) {
						if(world.getCelestialAngle(0) > 0.15 && world.getCelestialAngle(0) < 0.85) continue;
					}
					
					for(int i = 0; i < 9; i++) {
						ItemStack pedestal = tileArray[i] != null ? tileArray[i].item : null;
						if(pedestal == null && recipe.input[i] != null) continue outer;
						if(pedestal != null && recipe.input[i] == null) continue outer;
						if(pedestal == null && recipe.input[i] == null) continue;
						
						if(!recipe.input[i].matchesRecipe(pedestal, true) || recipe.input[i].stacksize != pedestal.stackSize) continue outer;
					}
					
					for(int i = 0; i < 9; i++) {
						if(i == 4) continue;
						ItemStack pedestal = tileArray[i] != null ? tileArray[i].item : null;
						if(pedestal == null && recipe.input[i] == null) continue;
						tileArray[i].item = null;
						tileArray[i].markDirty();
						world.markBlockForUpdate(tileArray[i].xCoord, tileArray[i].yCoord, tileArray[i].zCoord);
					}
					
					center.item = recipe.output.copy();
					center.markDirty();
					world.markBlockForUpdate(x, y, z);
					ExplosionSmallCreator.composeEffect(world, x + 0.5, y + 1.5, z + 0.5, 10, 2.5F, 1F);
					
					return;
				}
			}
		}
	}
	
	public static TileEntityPedestal castOrNull(TileEntity tile) {
		if(tile instanceof TileEntityPedestal) return (TileEntityPedestal) tile;
		return null;
	}

	public static class TileEntityPedestal extends TileEntity {

		public ItemStack item;
		
		@Override
		public boolean canUpdate() {
			return false;
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item"));
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(this.item != null) {
				NBTTagCompound stack = new NBTTagCompound();
				this.item.writeToNBT(stack);
				nbt.setTag("item", stack);
			}
		}
	}
}
