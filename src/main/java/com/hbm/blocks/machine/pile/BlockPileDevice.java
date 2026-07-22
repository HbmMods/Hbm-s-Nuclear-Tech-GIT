package com.hbm.blocks.machine.pile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.main.NTMSounds;
import com.hbm.tileentity.machine.pile.TileEntityPileControl;
import com.hbm.tileentity.machine.pile.TileEntityPileLoader;
import com.hbm.tileentity.machine.pile.TileEntityPileVent;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPileDevice extends BlockContainer implements IBlockMulti, ILookOverlay, ITooltipProvider {

	public static final int ITEM_META_LOADER = 0;
	public static final int ITEM_META_VENT = 1;
	public static final int ITEM_META_CONTROL = 2;

	public static final int BLOCK_META_LOADER = 0;
	public static final int BLOCK_META_VENT = 4;
	public static final int BLOCK_META_CONTROL = 8;

	public BlockPileDevice() {
		super(Material.iron);
	}

	@Override
	public int getSubCount() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		meta -= meta % 4;
		if(meta == BLOCK_META_LOADER) return new TileEntityPileLoader();
		if(meta == BLOCK_META_VENT) return new TileEntityPileVent();
		if(meta == BLOCK_META_CONTROL) return new TileEntityPileControl();
		return null;
	}
	
	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fx, float fy, float fz, int meta) {
		int metaOffset = itemMetaToBlockMeta(meta);
		// side is reduced by 2 because UP and DOWN (0 and 1) aren't relevant
		// therefore, all item metas (device subtypes) are neatly packed into 4 metas each
		side = MathHelper.clamp_int(side - 2, 0, 3);
		return metaOffset + side;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		int meta = world.getBlockMetadata(x, y, z);
		meta -= meta % 4;
		
		if(meta == BLOCK_META_LOADER) {
			if(world.isRemote) return true;
			
			TileEntityPileLoader tile = (TileEntityPileLoader) world.getTileEntity(x, y, z);
			
			if(tile.level <= 0 && !tile.loading) {
				
				if(player.getHeldItem() != null && tile.stack == null && tile.isItemLoadable(player.getHeldItem())) {
					tile.stack = player.getHeldItem().copy();
					tile.stack.stackSize = 1;
					player.getHeldItem().stackSize--;
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, NTMSounds.UPGRADE_PLUG, 1F, 1F);
					return true;
				}
				
				tile.loading = true;
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		if(world.getBlockMetadata(x, y, z) != BLOCK_META_CONTROL) return;
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, BLOCK_META_CONTROL + 0, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, BLOCK_META_CONTROL + 3, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, BLOCK_META_CONTROL + 1, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, BLOCK_META_CONTROL + 2, 2);
	}
	
	public static int itemMetaToBlockMeta(int meta) {
		if(meta >= ITEM_META_CONTROL) return BLOCK_META_CONTROL;
		if(meta == ITEM_META_VENT) return BLOCK_META_VENT;
		return BLOCK_META_LOADER;
	}

	@Override
	public int damageDropped(int meta) {
		if(meta >= BLOCK_META_CONTROL) return ITEM_META_CONTROL;
		if(meta >= BLOCK_META_VENT) return ITEM_META_VENT;
		return ITEM_META_LOADER;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= BLOCK_META_CONTROL) return side.ordinal() == meta % 4 + 2;
		if(meta >= BLOCK_META_VENT) return false;
		if(meta >= BLOCK_META_LOADER) return side.ordinal() == meta % 4 + 2;
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, this.getUnlocalizedName(stack) + ".desc", player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		List<String> text = new ArrayList();
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityPileLoader) {
			TileEntityPileLoader device = (TileEntityPileLoader) tile;
			text.add("Index: " + device.chanNum);
			if(device.syncStack != null) text.add("Loading: " + device.syncStack.getDisplayName());
		}
		
		if(tile instanceof TileEntityPileVent) {
			TileEntityPileVent device = (TileEntityPileVent) tile;
			text.add("Index: " + device.chanNum);
		}
		
		if(tile instanceof TileEntityPileControl) {
			TileEntityPileControl device = (TileEntityPileControl) tile;
			text.add("Index: " + device.chanNum);
			text.add("Extraction level: " + (int) + (device.level * 100) + "%");
		}
		
		if(!text.isEmpty())
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedNameFromItemMeta(this.damageDropped(meta)) + ".name"), 0xffff00, 0x404000, text);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedNameFromItemMeta(stack.getItemDamage());
	}
	
	public String getUnlocalizedNameFromItemMeta(int meta) {
		meta = Math.abs(meta % 3); // recitfy
		if(meta == ITEM_META_LOADER)	return this.getUnlocalizedName() + ".loader";
		if(meta == ITEM_META_VENT)		return this.getUnlocalizedName() + ".vent";
		if(meta == ITEM_META_CONTROL)	return this.getUnlocalizedName() + ".control";
		return this.getUnlocalizedName();
	}
}
