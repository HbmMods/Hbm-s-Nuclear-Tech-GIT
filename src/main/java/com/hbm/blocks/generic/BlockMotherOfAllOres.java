package com.hbm.blocks.generic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.google.common.collect.HashBiMap;
import com.hbm.blocks.IBlockMultiPass;
import com.hbm.config.WorldConfig;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.util.ColorUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockMotherOfAllOres extends BlockContainer implements IBlockMultiPass {
	
	public static int override = -1;
	
	public static void shuffleOverride(Random rand) {
		override = rand.nextInt(uniqueItems.size());
	}
	
	public static void resetOverride() {
		override = -1;
	}

	public BlockMotherOfAllOres() {
		super(Material.rock);
		this.setBlockTextureName("stone");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRandomOre();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < uniqueItems.size(); i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRandomOre) {
			TileEntityRandomOre ore = (TileEntityRandomOre) te;
			return new ItemStack(this, 1, ore.getStackId());
		}
		
		return new ItemStack(ModItems.nothing);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		
		if(fortune == 0xFECE00) {
			TileEntity te = world.getTileEntity(x, y, z);
			
			if(te instanceof TileEntityRandomOre) {
				TileEntityRandomOre ore = (TileEntityRandomOre) te;
				ComparableStack item = ore.getCompStack();
				ret.add(item.toStack());
			}
		}
		
		return ret;
	}
	
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropBlockAsItemWithChance(world, x, y, z, meta, 1, 0xFECE00);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		((TileEntityRandomOre)world.getTileEntity(x, y, z)).setItem(stack.getItemDamage());
		world.markBlockForUpdate(x, y, z);
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Override
	public int getPasses() {
		return 2;
	}

	private IIcon[] overlays = new IIcon[10];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		this.blockIcon = reg.registerIcon("stone");
		for(int i = 0; i < overlays.length; i++) {
			overlays[i] = reg.registerIcon(RefStrings.MODID + ":ore_random_" + (i + 1));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return Blocks.stone.getIcon(0, 0);

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRandomOre) {
			TileEntityRandomOre ore = (TileEntityRandomOre) te;
			int index = ore.getStackId() % overlays.length;
			return overlays[index];
		}

		return Blocks.stone.getIcon(0, 0);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		if(RenderBlockMultipass.currentPass == 0)
			return Blocks.stone.getIcon(0, 0);
		
		int index = meta % overlays.length;
		return overlays[index];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return 0xffffff;

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRandomOre) {
			TileEntityRandomOre ore = (TileEntityRandomOre) te;
			ItemStack stack = ore.getStack();
			int color = ColorUtil.getAverageColorFromStack(stack);
			color = ColorUtil.amplifyColor(color);
			
			Color col = new Color(color);
			int r = col.getRed();
			int g = col.getGreen();
			int b = col.getBlue();
			
			float[] hsb = new Color(color).RGBtoHSB(r, g, b, new float[3]);
			
			if(hsb[1] > 0F && hsb[1] < 0.75F)
				hsb[1] = 0.75F;
			
			color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
			
			return color;
		}
		
		return super.colorMultiplier(world, x, y, z);
	}
	
	public static class TileEntityRandomOre extends TileEntity {
		
		private ComparableStack stack;
		
		public TileEntityRandomOre() {
			if(override != -1) {
				setItem(override);
			}
		}
		
		public void setItem(int id) {
			ComparableStack comp = itemMap.get(id);
			this.stack = comp != null ? ((ComparableStack) comp.copy()) : null;
			
			if(this.worldObj != null)
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		}
		
		public int getStackId() {
			return itemMap.inverse().get(getCompStack());
		}
		
		public ItemStack getStack() {
			return getCompStack().toStack();
		}
		
		public ComparableStack getCompStack() {
			
			if(stack == null) {
				int rand = worldObj.rand.nextInt(uniqueItems.size());
				stack = (ComparableStack) itemMap.get(rand).copy();
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			}
			
			return stack != null ? stack : new ComparableStack(ModItems.nothing);
		}

		@Override
		public boolean canUpdate() {
			return false;
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			int key = nbt.getInteger("item");
			this.setItem(key);
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			
			try {
				Integer key = itemMap.inverse().get(getCompStack());
				nbt.setInteger("item", key != null ? key : 0);
			} catch(Exception ex) { }
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
	}
	
	public static class ItemRandomOreBlock extends ItemBlock {

		public ItemRandomOreBlock(Block block) {
			super(block);
			this.setHasSubtypes(true);
			this.setMaxDamage(0);
		}
		
		@Override
		public String getItemStackDisplayName(ItemStack stack) {
			ComparableStack comp = itemMap.get(stack.getItemDamage());
			ItemStack name = comp != null ? comp.toStack() : new ItemStack(ModItems.nothing);
			if(name.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
				name.setItemDamage(0);
			}
			//return I18nUtil.resolveKey(this.getUnlocalizedName() + ".name", name.getItem().getItemStackDisplayName(name));
			return StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".name", name.getItem().getItemStackDisplayName(name));
		}
	}
	
	public static HashSet<ComparableStack> uniqueItems = new HashSet();
	public static HashBiMap<Integer, ComparableStack> itemMap = HashBiMap.create();
	
	public static void init() {
		
		if(WorldConfig.enableRandom) {
			for(Object b : Block.blockRegistry.getKeys()) {
				Block block = Block.getBlockFromName((String) b);
				if(block != null && Item.getItemFromBlock(block) != null)
					uniqueItems.add(new ComparableStack(block));
			}
			
			for(Object i : Item.itemRegistry.getKeys()) {
				Item item = (Item) Item.itemRegistry.getObject((String) i);
				uniqueItems.add(new ComparableStack(item));
			}
			
			for(String i : OreDictionary.getOreNames()) {
				for(ItemStack stack : OreDictionary.getOres(i)) {
					uniqueItems.add(new ComparableStack(stack));
				}
			}
		} else {
			uniqueItems.add(new ComparableStack(ModItems.nothing));
		}
		
		int i = 0;
		for(ComparableStack stack : uniqueItems) {
			itemMap.put(i++, stack);
		}
	}
}
