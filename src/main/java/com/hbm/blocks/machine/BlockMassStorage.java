package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.machine.storage.TileEntityMassStorage;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockMassStorage extends BlockContainer implements IBlockMulti, ILookOverlay, ITooltipProvider {

	@SideOnly(Side.CLIENT) private IIcon[] iconTop;
	@SideOnly(Side.CLIENT) private IIcon[] iconSide;
	
	public BlockMassStorage() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = new IIcon[4];
		this.iconSide = new IIcon[4];

		this.iconTop[0] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_top_iron");
		this.iconSide[0] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_side_iron");
		this.iconTop[1] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_top_desh");
		this.iconSide[1] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_side_desh");
		this.iconTop[2] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_top");
		this.iconSide[2] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_side");
		this.iconTop[3] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_top_wood");
		this.iconSide[3] = iconRegister.registerIcon(RefStrings.MODID + ":mass_storage_side_wood");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		int meta = this.rectify(metadata);
		return side == 1 ? this.iconTop[meta] : (side == 0 ? this.iconTop[meta] : this.iconSide[meta]);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMassStorage(getCapacity(meta));
	}
	
	public int getCapacity(int meta) {
		return meta == 3 ? 100 : meta == 0 ? 10_000 : meta == 1 ? 100_000 : meta == 2 ? 1_000_000 : 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemLock || player.getHeldItem().getItem() == ModItems.key_kit)) {
			return false;

		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityMassStorage && ((TileEntityMassStorage) entity).canAccess(player)) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean dropInv = true;
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			
			ItemStack drop = new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
			ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			if(inv != null) {
				
				for(int i = 0; i < inv.getSizeInventory(); i++) {
					
					ItemStack stack = inv.getStackInSlot(i);
					if(stack == null)
						continue;
					
					NBTTagCompound slot = new NBTTagCompound();
					stack.writeToNBT(slot);
					nbt.setTag("slot" + i, slot);
				}
			}
			
			if(inv instanceof TileEntityLockableBase) {
				TileEntityLockableBase lockable = (TileEntityLockableBase) inv;
				
				if(lockable.isLocked()) {
					nbt.setInteger("lock", lockable.getPins());
					nbt.setDouble("lockMod", lockable.getMod());
				}
			}
			
			if(inv instanceof TileEntityMassStorage && nbt.func_150296_c().size() > 0) {
				TileEntityMassStorage storage = (TileEntityMassStorage) inv;
				nbt.setInteger("stack", storage.getStockpile());
			}
			
			if(!nbt.hasNoTags()) {
				drop.stackTagCompound = nbt;
			}
			
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop));
		}
		
		dropInv = false;
		boolean flag = world.setBlockToAir(x, y, z);
		dropInv = true;
		
		return flag;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		
		ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);
		
		if(inv != null && stack.hasTagCompound()) {
			
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				inv.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
			
			if(inv instanceof TileEntityMassStorage) {
				TileEntityMassStorage storage = (TileEntityMassStorage) inv;
				
				if(stack.stackTagCompound.hasKey("lock")) {
					storage.setPins(stack.stackTagCompound.getInteger("lock"));
					storage.setMod(stack.stackTagCompound.getDouble("lockMod"));
					storage.lock();
				}
				
				storage.setStockpile(stack.stackTagCompound.getInteger("stack"));
			}
		}

		super.onBlockPlacedBy(world, x, y, z, player, stack);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		if(dropInv) {
			ISidedInventory sided = (ISidedInventory) world.getTileEntity(x, y, z);
			Random rand = world.rand;
	
			if(sided != null) {
				for(int i1 = 0; i1 < sided.getSizeInventory(); ++i1) {
					
					if(i1 == 1) continue; //do NOT drop the filter item
					
					ItemStack itemstack = sided.getStackInSlot(i1);
	
					if(itemstack != null) {
						float f = rand.nextFloat() * 0.8F + 0.1F;
						float f1 = rand.nextFloat() * 0.8F + 0.1F;
						float f2 = rand.nextFloat() * 0.8F + 0.1F;
	
						while(itemstack.stackSize > 0) {
							int j1 = rand.nextInt(21) + 10;
	
							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}
	
							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
	
							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
	
							float f3 = 0.05F;
							entityitem.motionX = (float) rand.nextGaussian() * f3;
							entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) rand.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}
	
				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public int getSubCount() {
		return 4;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityMassStorage))
			return;
		
		TileEntityMassStorage storage = (TileEntityMassStorage) te;
		
		List<String> text = new ArrayList();
		String title = "Empty";
		boolean full = storage.type != null;
				
		if(full) {
			
			title = storage.type.getDisplayName();
			text.add(String.format(Locale.US, "%,d", storage.getStockpile()) + " / " + String.format(Locale.US, "%,d", storage.getCapacity()));
			
			double percent = (double) storage.getStockpile() / (double) storage.getCapacity();
			int charge = (int) Math.floor(percent * 10_000D);
			int color = ((int) (0xFF - 0xFF * percent)) << 16 | ((int)(0xFF * percent) << 8);
			
			text.add("&[" + color + "&]" + (charge / 100D) + "%");
		}
		
		ILookOverlay.printGeneric(event, title, full ? 0xffff00 : 0x00ffff, full ? 0x404000 : 0x004040, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		if(!stack.hasTagCompound()) return;
		
		ItemStack type = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot1"));
		
		if(type != null) {
			list.add(EnumChatFormatting.GOLD + type.getDisplayName());
			list.add(String.format(Locale.US, "%,d", stack.stackTagCompound.getInteger("stack")) + " / " + String.format(Locale.US, "%,d", getCapacity(stack.getItemDamage())));
		}
	}
	
	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return ((TileEntityMassStorage) world.getTileEntity(x, y, z)).redstone;
	}
}
