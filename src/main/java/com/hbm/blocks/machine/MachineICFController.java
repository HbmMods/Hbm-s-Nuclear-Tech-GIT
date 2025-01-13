package com.hbm.blocks.machine;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockICF.TileEntityBlockICF;
import com.hbm.blocks.machine.BlockICFLaserComponent.EnumICFPart;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.lib.RefStrings;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.machine.TileEntityICFController;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
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
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class MachineICFController extends BlockContainer implements ILookOverlay {

	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public MachineICFController() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityICFController();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":icf_controller");
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

			TileEntityICFController controller = (TileEntityICFController) world.getTileEntity(x, y, z);

			if(!controller.assembled) {
				assemble(world, x, y, z, player);
			}

			return true;
		} else {
			return false;
		}
	}

	private static HashMap<BlockPos, Integer> assembly = new HashMap();
	private static HashSet<BlockPos> casings = new HashSet();
	private static HashSet<BlockPos> ports = new HashSet();
	private static HashSet<BlockPos> cells = new HashSet();
	private static HashSet<BlockPos> emitters = new HashSet();
	private static HashSet<BlockPos> capacitors = new HashSet();
	private static HashSet<BlockPos> turbochargers = new HashSet();
	private static boolean errored;
	private static final int maxSize = 1024;

	public void assemble(World world, int x, int y, int z, EntityPlayer player) {
		assembly.clear();
		casings.clear();
		ports.clear();
		cells.clear();
		emitters.clear();
		capacitors.clear();
		turbochargers.clear();
		assembly.put(new BlockPos(x, y, z), 0);

		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();

		errored = false;
		floodFill(world, x + dir.offsetX, y, z + dir.offsetZ, player);
		assembly.remove(new BlockPos(x, y, z));

		TileEntityICFController controller = (TileEntityICFController) world.getTileEntity(x, y, z);

		if(!errored) {

			for(Entry<BlockPos, Integer> entry : assembly.entrySet()) {

				BlockPos pos = entry.getKey();

				if(ports.contains(pos)) {
					world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.icf_block, 1, 3);
				} else {
					world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.icf_block, 0, 3);
				}

				TileEntityBlockICF icf = (TileEntityBlockICF) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
				icf.block = ModBlocks.icf_laser_component;
				icf.meta = entry.getValue();
				icf.coreX = x;
				icf.coreY = y;
				icf.coreZ = z;
				icf.markDirty();
			}

			controller.setup(ports, cells, emitters, capacitors, turbochargers);
			controller.markDirty();
		}
		controller.assembled = !errored;

		assembly.clear();
		casings.clear();
		ports.clear();
		cells.clear();
		emitters.clear();
		capacitors.clear();
		turbochargers.clear();
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
		int meta = world.getBlockMetadata(x, y, z);

		boolean validCasing = false;
		boolean validCore = false;

		if(block == ModBlocks.icf_laser_component) {
			if(meta == EnumICFPart.CASING.ordinal()) { casings.add(pos); validCasing = true; }
			if(meta == EnumICFPart.PORT.ordinal()) { ports.add(pos); validCasing = true; }
			if(meta == EnumICFPart.CELL.ordinal()) { cells.add(pos); validCore = true; }
			if(meta == EnumICFPart.EMITTER.ordinal()) { emitters.add(pos); validCore = true; }
			if(meta == EnumICFPart.CAPACITOR.ordinal()) { capacitors.add(pos); validCore = true; }
			if(meta == EnumICFPart.TURBO.ordinal()) { turbochargers.add(pos); validCore = true; }
		}

		if(validCasing) {
			assembly.put(pos, meta);
			return;
		}

		if(validCore) {
			assembly.put(pos, meta);
			floodFill(world, x + 1, y, z, player);
			floodFill(world, x - 1, y, z, player);
			floodFill(world, x, y + 1, z, player);
			floodFill(world, x, y - 1, z, player);
			floodFill(world, x, y, z + 1, player);
			floodFill(world, x, y, z - 1, player);
			return;
		}

		sendError(world, x, y, z, "Non-laser block", player);
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

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof TileEntityICFController)) return;
		TileEntityICFController icf = (TileEntityICFController) te;
		List<String> text = new ArrayList();
		text.add(BobMathUtil.getShortNumber(icf.getPower()) + "/" + BobMathUtil.getShortNumber(icf.getMaxPower()) + "HE");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
