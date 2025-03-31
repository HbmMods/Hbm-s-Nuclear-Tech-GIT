package com.hbm.blocks.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockVVER.TileEntityBlockVVER;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.machine.TileEntityVVERController;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineVVERController extends BlockContainer implements ITooltipProvider {

	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public MachineVVERController(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityVVERController();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":vver_controller");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : this.blockIcon);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {

			TileEntityVVERController controller = (TileEntityVVERController) world.getTileEntity(x, y, z);

			if(!controller.assembled) {
				assemble(world, x, y, z, player);
			} else {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}

			return true;
		} else {
			return false;
		}
	}

	private static HashMap<BlockPos, Block> assembly = new HashMap();
	private static HashMap<BlockPos, Block> fuelRods = new HashMap();
	private static HashMap<BlockPos, Block> sources = new HashMap();
	private static boolean errored;
	private static final int maxSize = 4096;

	public void assemble(World world, int x, int y, int z, EntityPlayer player) {
		assembly.clear();
		fuelRods.clear();
		sources.clear();
		assembly.put(new BlockPos(x, y, z), this);

		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();

		errored = false;
		floodFill(world, x + dir.offsetX, y, z + dir.offsetZ, player);

		if(fuelRods.size() == 0){
			sendError(world, x, y, z, "Fuel rods required", player);
			errored = true;
		}

		if(sources.size() == 0) {
			sendError(world, x, y, z, "Neutron sources required", player);
			errored = true;
		}

		TileEntityVVERController controller = (TileEntityVVERController) world.getTileEntity(x, y, z);

		if(!errored) {
			for(Entry<BlockPos, Block> entry : assembly.entrySet()) {

				BlockPos pos = entry.getKey();
				Block block = entry.getValue();

				if(block != ModBlocks.vver_controller) {

					if(block == ModBlocks.vver_port) {
						world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.vver_block, 1, 3);
					} else {
						world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.vver_block, 0, 3);
					}

					TileEntityBlockVVER vver = (TileEntityBlockVVER) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
					vver.block = block;
					vver.coreX = x;
					vver.coreY = y;
					vver.coreZ = z;
					vver.markDirty();
				}
			}

			controller.setup(assembly, fuelRods);
		}
		controller.assembled = !errored;

		assembly.clear();
		fuelRods.clear();
		sources.clear();
	}

	private void floodFill(World world, int x, int y, int z, EntityPlayer player) {

		BlockPos pos = new BlockPos(x, y, z);

		if(assembly.containsKey(pos)) return;
		if(assembly.size() >= maxSize) {
			errored = true;
			sendError(world, x, y, z, "Max size exceeded", player);
			return;
		}

		Block block = world.getBlock(x, y, z);

		if(isValidCasing(block)) {
			assembly.put(pos, block);
			return;
		}

		if(isValidCore(block)) {
			assembly.put(pos, block);
			if(block == ModBlocks.vver_fuel) fuelRods.put(pos, block);
			if(block == ModBlocks.vver_neutron_source) sources.put(pos, block);
			floodFill(world, x + 1, y, z, player);
			floodFill(world, x - 1, y, z, player);
			floodFill(world, x, y + 1, z, player);
			floodFill(world, x, y - 1, z, player);
			floodFill(world, x, y, z + 1, player);
			floodFill(world, x, y, z - 1, player);
			return;
		}

		sendError(world, x, y, z, "Non-reactor block", player);
		errored = true;
	}

	private void sendError(World world, int x, int y, int z, String message, EntityPlayer player) {

		if(player instanceof EntityPlayerMP) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "marker");
			data.setInteger("color", 0xff0000);
			data.setInteger("expires", 5_000);
			data.setDouble("dist", 128D);
			if(message != null) data.setString("label", message);
			PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(data, x, y, z), (EntityPlayerMP) player);
		}
	}

	private boolean isValidCore(Block block) {
		if(block == ModBlocks.vver_fuel ||
				block == ModBlocks.vver_control ||
				block == ModBlocks.vver_channel ||
				block == ModBlocks.vver_heatex ||
				block == ModBlocks.vver_heatsink ||
				block == ModBlocks.vver_neutron_source)
			return true;
		return false;
	}

	private boolean isValidCasing(Block block) {
		if(block == ModBlocks.vver_casing || block == ModBlocks.vver_reflector || block == ModBlocks.vver_port) return true;
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
