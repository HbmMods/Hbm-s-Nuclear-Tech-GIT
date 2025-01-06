package com.hbm.blocks.machine.pile;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import api.hbm.block.IInsertable;
import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockGraphiteDrilledBase extends BlockFlammable implements IToolable, IInsertable {

	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconAluminum; //shrouded in aluminum

	public BlockGraphiteDrilledBase() {
		super(ModBlocks.block_graphite.getMaterial(), ((BlockFlammable) ModBlocks.block_graphite).encouragement, ((BlockFlammable) ModBlocks.block_graphite).flammability);
		
		this.setCreativeTab(null);
		this.setStepSound(Block.soundTypeMetal);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.sideIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite");
		this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_drilled_aluminum");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 3;
		int meta = metadata & 4;
		
		if(side == cfg * 2 || side == cfg * 2 + 1) {
			if(meta == 4)
				return this.blockIconAluminum;
			
			return this.blockIcon;
		}
		
		return this.sideIcon;
	}
	
	protected static void ejectItem(World world, int x, int y, int z, ForgeDirection dir, ItemStack stack) {
		
		EntityItem dust = new EntityItem(world, x + 0.5D + dir.offsetX * 0.75D, y + 0.5D + dir.offsetY * 0.75D, z + 0.5D + dir.offsetZ * 0.75D, stack);
		dust.motionX = dir.offsetX * 0.25;
		dust.motionY = dir.offsetY * 0.25;
		dust.motionZ = dir.offsetZ * 0.25;
		world.spawnEntityInWorld(dust);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(!world.isRemote) {

			int meta = world.getBlockMetadata(x, y, z);
			int cfg = meta & 3;
			
			if(side == cfg * 2 || side == cfg * 2 + 1) {
				world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta & 7, 3);
				this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(getInsertedItem(meta)));
			}
		}
		
		return true;
	}
	
	//Thank god getDrops passes meta
	protected Item getInsertedItem(int meta) {
		return getInsertedItem();
	}
	
	protected Item getInsertedItem() {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList();
		drops.add(new ItemStack(ModItems.ingot_graphite, 8));
		if((meta & 4) == 4)
			drops.add(new ItemStack(ModItems.shell, 1, Mats.MAT_ALUMINIUM.id));
		if(getInsertedItem() != null)
			drops.add(new ItemStack(getInsertedItem(meta), 1));
		return drops;
	}
	
	//Checks the relationship between specific items and placement.
	//kinda cringe but anything other than hardcoding would be overengineering this for no reason so
	//all of this is destined to be changed most likely anyway
	protected MetaBlock checkInteractions(ItemStack stack) {
		Item item = stack.getItem(); //temp
		if(item == ModItems.pile_rod_uranium) return new MetaBlock(ModBlocks.block_graphite_fuel);
		if(item == ModItems.pile_rod_pu239) return new MetaBlock(ModBlocks.block_graphite_fuel, 0b1000);
		if(item == ModItems.pile_rod_plutonium) return new MetaBlock(ModBlocks.block_graphite_plutonium);
		if(item == ModItems.pile_rod_source) return new MetaBlock(ModBlocks.block_graphite_source);
		if(item == ModItems.pile_rod_boron) return new MetaBlock(ModBlocks.block_graphite_rod);
		if(item == ModItems.pile_rod_lithium) return new MetaBlock(ModBlocks.block_graphite_lithium);
		if(item == ModItems.cell_tritium) return new MetaBlock(ModBlocks.block_graphite_tritium);
		if(item == ModItems.pile_rod_detector) return new MetaBlock(ModBlocks.block_graphite_detector);
		return null;
	}
	
	@Override
	public boolean insertItem(World world, int x, int y, int z, ForgeDirection dir, ItemStack stack) {
		
		if(stack == null) return false;
		
		MetaBlock baseBlock = checkInteractions(stack);
		if(baseBlock == null) return false;
		
		final int side = dir.ordinal();
		final int pureMeta = world.getBlockMetadata(x, y, z) & 3; //in case it's shrouded in aluminum

		if(side == pureMeta * 2 || side == pureMeta * 2 + 1) {
			//first, make sure we can even push rods out
			for(int i = 0; i <= 3; i++) { //limited to 3 boyos
				int ix = x + dir.offsetX * i;
				int iy = y + dir.offsetY * i;
				int iz = z + dir.offsetZ * i;
				
				Block b = world.getBlock(ix, iy, iz);
				
				if(b instanceof BlockGraphiteDrilledBase) {
					int baseMeta = world.getBlockMetadata(ix, iy, iz);
					if((baseMeta & 3) != pureMeta) //wrong orientation
						return false;
					
					if(((BlockGraphiteDrilledBase)b).getInsertedItem(baseMeta) == null) //if there's nothing to push
						break;
					else if(i >= 3) //if there is stuff to push and we reach our limit
						return false;
				} else {
					if(b.isNormalCube()) //obstructions
						return false;
					else //empty space? no need to search
						break;
				}
			}
			
			//TODO convert old methods to use itemstack for flexibility
			int oldMeta = pureMeta | baseBlock.meta; //metablocks are kinda inconvenient to work with so 
			Block oldBlock = baseBlock.block;
			NBTTagCompound oldTag = new NBTTagCompound(); //In case of TEs
			oldTag.setInteger("x", x); //giving tags prevents issues and resets any lingering tes.
			oldTag.setInteger("y", y);
			oldTag.setInteger("z", z);
			
			//now actually make the change
			for(int i = 0; i <= 3; i++) { //yeah yeah we know it's safe but let's be *extra cautious* of infinite loops
				int ix = x + dir.offsetX * i;
				int iy = y + dir.offsetY * i;
				int iz = z + dir.offsetZ * i;
				
				Block newBlock = world.getBlock(ix, iy, iz);
				
				if(newBlock instanceof BlockGraphiteDrilledBase) {
					int newMeta = world.getBlockMetadata(ix, iy, iz);
					NBTTagCompound newTag = new NBTTagCompound();
					
					if(newBlock instanceof BlockGraphiteDrilledTE) {
						TileEntity te = world.getTileEntity(ix, iy, iz);
						te.writeToNBT(newTag);
						newTag.setInteger("x", te.xCoord + dir.offsetX); //malformed positions is very very bad and prevents the pile TEs from ticking
						newTag.setInteger("y", te.yCoord + dir.offsetY);
						newTag.setInteger("z", te.zCoord + dir.offsetZ);
					}
					
					world.setBlock(ix, iy, iz, oldBlock, (oldMeta & ~0b100) | (newMeta & 0b100), 0);
					
					if(oldBlock instanceof BlockGraphiteDrilledTE && !oldTag.hasNoTags()) { //safety first
						TileEntity te = world.getTileEntity(ix, iy, iz);
						te.readFromNBT(oldTag);
					}
					
					world.markAndNotifyBlock(ix, iy, iz, world.getChunkFromBlockCoords(ix, iz), newBlock, oldBlock, 3); //in case setBlock returns false due to = meta / block
					
					oldMeta = newMeta;
					oldBlock = newBlock;
					oldTag = newTag;
					
					if(oldBlock instanceof BlockGraphiteDrilled) //if there's no need to eject an item
						break;
				} else {
					Item eject = ((BlockGraphiteDrilledBase) oldBlock).getInsertedItem(oldMeta); //TODO old methods to itemstack
					this.ejectItem(world, ix - dir.offsetX, iy - dir.offsetY, iz - dir.offsetZ, dir, new ItemStack(eject));
					world.playSoundEffect(ix + 0.5, iy + 0.5, iz + 0.5, "hbm:item.upgradePlug", 1.25F, 1.0F);
					
					break;
				}
			}
			
			return true;
		}
		
		return false;
	}
}
