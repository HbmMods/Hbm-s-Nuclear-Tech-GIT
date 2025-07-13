package com.hbm.blocks.network;

import api.hbm.block.IToolable;
import com.hbm.blocks.IBlockSideRotation;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.items.tool.ItemConveyorWand;
import com.hbm.items.tool.ItemTooling;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.TileEntityCraneBase;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public abstract class BlockCraneBase extends BlockContainer implements IBlockSideRotation, IToolable, ITooltipProvider {

	@SideOnly(Side.CLIENT) protected IIcon iconSide;
	@SideOnly(Side.CLIENT) protected IIcon iconIn;
	@SideOnly(Side.CLIENT) protected IIcon iconSideIn;
	@SideOnly(Side.CLIENT) protected IIcon iconOut;
	@SideOnly(Side.CLIENT) protected IIcon iconSideOut;

	@SideOnly(Side.CLIENT) protected IIcon iconDirectional;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalUp;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalDown;

	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalTurnLeft;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalTurnRight;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideLeftTurnUp;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideRightTurnUp;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideLeftTurnDown;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideRightTurnDown;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideUpTurnLeft;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideUpTurnRight;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideDownTurnLeft;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalSideDownTurnRight;

	public BlockCraneBase(Material mat) {
		super(mat);
	}

	@Override
	public abstract TileEntityCraneBase createNewTileEntity(World p_149915_1_, int p_149915_2_);

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crane_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":crane_side");
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_in");
		this.iconSideIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_in");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_out");
		this.iconSideOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_out");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTooling) {
			return false;
		} else if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemConveyorWand) {
			return false;
		} else if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if (tool != ToolType.SCREWDRIVER) return false;

		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof TileEntityCraneBase)) return false;

		TileEntityCraneBase craneTileEntity = (TileEntityCraneBase) te;

		ForgeDirection newDirection = ForgeDirection.getOrientation(side);

		if (player.isSneaking()) {
			craneTileEntity.setOutputOverride(newDirection);
		} else {
			craneTileEntity.setInput(newDirection);
		}

		return true;
	}

	public ForgeDirection getInputSide(IBlockAccess world, int x, int y, int z) {
		return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
	}

	/**
	 * Returns the player-overridden output direction, or {@link ForgeDirection#UNKNOWN} if unset.
	 * A return value of {@link ForgeDirection#UNKNOWN} suggests use of default meta behavior.
	 * <i>Should</i> never return the current input direction.
	 */
	protected final ForgeDirection getOutputSideOverride(IBlockAccess world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof TileEntityCraneBase)) return ForgeDirection.UNKNOWN;
		TileEntityCraneBase craneTileEntity = (TileEntityCraneBase) te;

		return craneTileEntity.getOutputOverride();
	}

	public ForgeDirection getOutputSide(IBlockAccess world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof TileEntityCraneBase)) return ForgeDirection.UNKNOWN;
		TileEntityCraneBase craneTileEntity = (TileEntityCraneBase) te;

		return craneTileEntity.getOutputSide();
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		ForgeDirection inputSide = getInputSide(world, x, y, z);
		ForgeDirection outputOverride = getOutputSideOverride(world, x, y, z);
		boolean outputSideOverridden = outputOverride != ForgeDirection.UNKNOWN && outputOverride.getOpposite() != inputSide;
		ForgeDirection outputSide = outputSideOverridden ? outputOverride : inputSide.getOpposite();

		// take your left hand, make your thumb the input side and the index finger the output side
		// angle your middle finger to make your hand look like coordinate axes
		// the direction your middle finger is pointing towards will be the direction returned from this function
		ForgeDirection leftHandRotation = outputSide.getRotation(inputSide);

		if(side == 0 || side == 1) {
			if(side == outputSide.ordinal()) {
				return this.iconOut;
			}
			if(side == inputSide.ordinal()) {
				return this.iconIn;
			}

			if (side == 1) {
				if (outputSideOverridden) {
					if (leftHandRotation == ForgeDirection.UP) {
						return this.iconDirectionalTurnLeft;
					}
					if (leftHandRotation == ForgeDirection.DOWN) {
						return this.iconDirectionalTurnRight;
					}
				} else return iconDirectional;
			}

			return this.blockIcon;
		}

		if(side == outputSide.ordinal()) {
			return this.iconSideOut;
		}
		if(side == inputSide.ordinal()) {
			return this.iconSideIn;
		}

		if (outputSideOverridden) {
			if (leftHandRotation.ordinal() == side) {
				if (outputSide == ForgeDirection.UP)
					return this.iconDirectionalSideLeftTurnUp;
				if (outputSide == ForgeDirection.DOWN)
					return this.iconDirectionalSideRightTurnDown;
				if (inputSide == ForgeDirection.UP)
					return this.iconDirectionalSideUpTurnRight;
				if (inputSide == ForgeDirection.DOWN)
					return this.iconDirectionalSideDownTurnLeft;
			}
			if (leftHandRotation.getOpposite().ordinal() == side) {
				if (outputSide == ForgeDirection.UP)
					return this.iconDirectionalSideRightTurnUp;
				if (outputSide == ForgeDirection.DOWN)
					return this.iconDirectionalSideLeftTurnDown;
				if (inputSide == ForgeDirection.UP)
					return this.iconDirectionalSideUpTurnLeft;
				if (inputSide == ForgeDirection.DOWN)
					return this.iconDirectionalSideDownTurnRight;
			}
		} else {
			if(outputSide == ForgeDirection.UP) {
				return this.iconDirectionalUp;
			}
			if(outputSide == ForgeDirection.DOWN) {
				return this.iconDirectionalDown;
			}
		}

		return this.iconSide;
	}

	// kept for inventory rendering
	@Override
	public IIcon getIcon(int side, int metadata) {

		if(side == 0 || side == 1) {
			if(side == metadata) {
				return this.iconOut;
			}
			if(side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal()) {
				return this.iconIn;
			}

			return side == 1 ? this.iconDirectional : this.blockIcon;
		}

		if(side == metadata) {
			return this.iconSideOut;
		}
		if(side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal()) {
			return this.iconSideIn;
		}

		if(metadata == 0) {
			return this.iconDirectionalUp;
		}
		if(metadata == 1) {
			return this.iconDirectionalDown;
		}

		return this.iconSide;
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);

		if(meta > 1 && side == 1) {
			if(meta == 2) return 3;
			if(meta == 3) return 0;
			if(meta == 4) return 1;
			if(meta == 5) return 2;
		}

		return 0;
	}

	public static int renderIDClassic = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return IBlockSideRotation.getRenderType();
	}

	private final Random rand = new Random();
	public void dropContents(World world, int x, int y, int z, Block block, int meta, int start, int end) {
		ISidedInventory tileentityfurnace = (ISidedInventory) world.getTileEntity(x, y, z);

		if(tileentityfurnace != null) {

			for(int i1 = start; i1 < end; ++i1) {
				ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

				if(itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

					while(itemstack.stackSize > 0) {
						int j1 = this.rand.nextInt(21) + 10;

						if(j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if(itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
