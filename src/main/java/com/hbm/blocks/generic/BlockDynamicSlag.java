package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.machine.ItemScraps;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import com.hbm.util.Compat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDynamicSlag extends BlockContainer {
	
	private HashMap<NTMMaterial, IIcon> iconMap = new HashMap();

	public BlockDynamicSlag() {
		super(Material.iron);
		this.useNeighborBrightness = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySlag();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		
		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			
			for(NTMMaterial mat : Mats.orderedList) {
				if(mat.solidColorLight != mat.solidColorDark) {
					String placeholderName = this.getTextureName() + "-" + mat.names[0];
					TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0xFFFFFF, 0x505050, mat.solidColorLight, mat.solidColorDark)).setBlockAtlas();
					map.setTextureEntry(placeholderName, mutableIcon);
					iconMap.put(mat, mutableIcon);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		TileEntitySlag tile = (TileEntitySlag) world.getTileEntity(x, y, z);
		
		if(tile != null && tile.mat != null) {
			IIcon override = iconMap.get(tile.mat);
			if(override != null) {
				return override;
			}
		}
		
		return this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		
		TileEntitySlag tile = (TileEntitySlag) world.getTileEntity(x, y, z);
		
		if(tile != null && tile.mat != null) {
			if(!iconMap.containsKey(tile.mat)) {
				return tile.mat.moltenColor;
			}
		}
		
		return 0xffffff;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntitySlag tile = (TileEntitySlag) world.getTileEntity(x, y, z);
		if(tile != null) {
			this.setBlockBounds(0F, 0F, 0F, 1F, (float) tile.amount / (float) TileEntitySlag.maxAmount, 1F);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		TileEntitySlag tile = (TileEntitySlag) world.getTileEntity(x, y, z);
		if(tile != null) {
			this.setBlockBounds(0F, 0F, 0F, 1F, (float) tile.amount / (float) TileEntitySlag.maxAmount, 1F);
		}
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		TileEntity s = Compat.getTileStandard(world, x, y, z);
		TileEntity b = Compat.getTileStandard(world, x, y - 1, z);
		
		/* Error here, delete the block */
		if(s == null || !(s instanceof TileEntitySlag)) {
			world.setBlockToAir(x, y, z);
			return;
		}
		
		TileEntitySlag self = (TileEntitySlag) s;
		
		/* Flow down */
		if(world.getBlock(x, y - 1, z).isReplaceable(world, x, y - 1, z) && y > 0) {
			world.setBlock(x, y - 1, z, ModBlocks.slag);
			TileEntitySlag tile = (TileEntitySlag) Compat.getTileStandard(world, x, y - 1, z);
			tile.mat = self.mat;
			tile.amount = self.amount;
			world.markBlockForUpdate(x, y - 1, z);
			world.setBlockToAir(x, y, z);
			return;
		} else if(b instanceof TileEntitySlag) {
			
			TileEntitySlag below = (TileEntitySlag) b;
			
			if(below.mat == self.mat && below.amount < TileEntitySlag.maxAmount) {
				int transfer = Math.min(TileEntitySlag.maxAmount - below.amount, self.amount);
				below.amount += transfer;
				self.amount -= transfer;
				
				if(self.amount <= 0){
					world.setBlockToAir(x, y, z);
				} else {
					world.markBlockForUpdate(x, y, z);
				}

				world.markBlockForUpdate(x, y - 1, z);
				world.scheduleBlockUpdate(x, y - 1, z, ModBlocks.slag, 1);
				return;
			}
		}
		
		/* Flow sideways, no neighbors */
		ForgeDirection[] sides = new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
		int count = 0;
		for(ForgeDirection dir : sides) {
			int iX = x + dir.offsetX;
			int iZ = z + dir.offsetZ;
			
			if(world.getBlock(iX, y, iZ).isReplaceable(world, iX, y, iZ)) {
				count++;
			}
		}
		
		if(self.amount >= self.maxAmount / 5 && count > 0) {
			int toSpread = Math.max(self.amount / (count * 2), 1);
			
			for(ForgeDirection dir : sides) {
				int iX = x + dir.offsetX;
				int iZ = z + dir.offsetZ;
				
				if(world.getBlock(iX, y, iZ).isReplaceable(world, iX, y, iZ)) {
					world.setBlock(iX, y, iZ, ModBlocks.slag);
					TileEntitySlag tile = (TileEntitySlag) Compat.getTileStandard(world, iX, y, iZ);
					world.markBlockForUpdate(iX, y, iZ);
					world.scheduleBlockUpdate(iX, y, iZ, ModBlocks.slag, 1);
					tile.mat = self.mat;
					tile.amount = toSpread;
					self.amount -= toSpread;
					world.markBlockForUpdate(x, y, z);
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		
		TileEntitySlag tile = (TileEntitySlag) world.getTileEntity(x, y, z);
		
		if(tile != null && tile.mat != null && tile.amount > 0) {
			ret.add(ItemScraps.create(new MaterialStack(tile.mat, tile.amount)));
		}
		
		return ret;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {

		TileEntitySlag tile = (TileEntitySlag) world.getTileEntity(x, y, z);
		
		if(tile != null) {
			return ItemScraps.create(new MaterialStack(tile.mat, tile.amount));
		}
		
		return super.getPickBlock(target, world, x, y, z, player);
	}

	public static class TileEntitySlag extends TileEntity {
		
		public NTMMaterial mat;
		public int amount;
		public static int maxAmount = MaterialShapes.BLOCK.q(16);

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
			this.mat = Mats.matById.get(nbt.getInteger("mat"));
			this.amount = nbt.getInteger("amount");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(this.mat != null) nbt.setInteger("mat", this.mat.id);
			nbt.setInteger("amount", this.amount);
		}
	}
}
