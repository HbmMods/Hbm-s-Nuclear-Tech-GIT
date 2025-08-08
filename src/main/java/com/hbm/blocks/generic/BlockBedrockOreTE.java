package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrillbit.EnumDrillType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.fluidmk2.IFillableItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockBedrockOreTE extends BlockContainer implements ILookOverlay, IBlockMultiPass {

	public BlockBedrockOreTE() {
		super(Material.rock);
		this.setBlockTextureName("bedrock");
		this.setBlockUnbreakable();
		this.setResistance(1_000_000);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBedrockOre();
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return null;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz) {
		
		ItemStack stack = player.getHeldItem();
		if(stack == null) return false;
		if(!player.capabilities.isCreativeMode) return false;
		if(world.isRemote) return true;

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityBedrockOre) {
			TileEntityBedrockOre ore = (TileEntityBedrockOre) te;
			
			if(stack.getItem() == ModItems.drillbit) {
				EnumDrillType type = EnumUtil.grabEnumSafely(EnumDrillType.class, stack.getItemDamage());
				ore.tier = type.tier;
			} else if(FluidContainerRegistry.getFluidType(stack) != Fluids.NONE) {
				FluidType type = FluidContainerRegistry.getFluidType(stack);
				int amount = FluidContainerRegistry.getFluidContent(stack, type);
				ore.acidRequirement = new FluidStack(type, amount);
			} else if(stack.getItem() instanceof IFillableItem) {
				IFillableItem item = (IFillableItem) stack.getItem();
				FluidType type = item.getFirstFluidType(stack);
				if(type != null) {
					ore.acidRequirement = new FluidStack(type, item.getFill(stack));
				}
			} else {
				ore.resource = stack.copy();
				ore.shape = world.rand.nextInt(10);
			}
			
			ore.markDirty();
		}
		
		world.markBlockForUpdate(x, y, z);
		
		return true;
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
		
		this.blockIcon = reg.registerIcon("bedrock");
		for(int i = 0; i < overlays.length; i++) {
			overlays[i] = reg.registerIcon(RefStrings.MODID + ":ore_random_" + (i + 1));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return Blocks.bedrock.getIcon(0, 0);

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityBedrockOre) {
			TileEntityBedrockOre ore = (TileEntityBedrockOre) te;
			int index = ore.shape % overlays.length;
			return overlays[index];
		}

		return Blocks.bedrock.getIcon(0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		if(RenderBlockMultipass.currentPass == 0)
			return Blocks.bedrock.getIcon(0, 0);
		
		int index = meta % overlays.length;
		return overlays[index];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return 0xffffff;

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityBedrockOre) {
			TileEntityBedrockOre ore = (TileEntityBedrockOre) te;
			return ore.color;
		}
		
		return super.colorMultiplier(world, x, y, z);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityBedrockOre))
			return;
		
		TileEntityBedrockOre ore = (TileEntityBedrockOre) te;

		List<String> text = new ArrayList();
		
		if(ore.resource != null) {
			text.add(ore.resource.getDisplayName());
		}
		
		text.add("Tier: " + ore.tier);
		
		if(ore.acidRequirement != null) {
			text.add("Requires: " + ore.acidRequirement.fill + "mB " + ore.acidRequirement.type.getLocalizedName());
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	public static class TileEntityBedrockOre extends TileEntity {
		
		public ItemStack resource;
		public FluidStack acidRequirement;
		public int tier;
		public int color;
		public int shape;
		
		public TileEntityBedrockOre setStyle(int color, int shape) {
			this.color = color;
			this.shape = shape;
			return this;
		}

		@Override
		public boolean canUpdate() {
			return false;
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			
			this.resource = new ItemStack(Item.getItemById(nbt.getInteger("0id")), nbt.getByte("size"), nbt.getShort("meta"));
			
			if(this.resource.getItem() == null) this.resource = new ItemStack(ModItems.powder_iron);
			
			FluidType type = Fluids.fromID(nbt.getInteger("fluid"));
			
			if(type != Fluids.NONE) {
				this.acidRequirement = new FluidStack(type, nbt.getInteger("amount"));
			}

			this.tier = nbt.getInteger("tier");
			this.color = nbt.getInteger("color");
			this.shape = nbt.getInteger("shape");
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			
			if(this.resource != null) {
				nbt.setInteger("0id", Item.getIdFromItem(this.resource.getItem()));
				nbt.setByte("size", (byte) this.resource.stackSize);
				nbt.setShort("meta", (short) this.resource.getItemDamage());
			}
			
			if(this.acidRequirement != null) {
				nbt.setInteger("fluid", this.acidRequirement.type.getID());
				nbt.setInteger("amount", this.acidRequirement.fill);
			}

			nbt.setInteger("tier", this.tier);
			nbt.setInteger("color", this.color);
			nbt.setInteger("shape", this.shape);
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
			
			if(color == 0) {
				this.color = MainRegistry.proxy.getStackColor(resource, true);
			}

			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
}
