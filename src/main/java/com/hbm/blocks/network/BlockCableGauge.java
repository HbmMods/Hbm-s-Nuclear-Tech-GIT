package com.hbm.blocks.network;

import api.hbm.energymk2.PowerNetMK2;
import api.hbm.redstoneoverradio.IRORValueProvider;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.CompatHandler;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.network.TileEntityCableBaseNT;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import java.util.ArrayList;
import java.util.List;

public class BlockCableGauge extends BlockContainer implements IBlockMultiPass, ILookOverlay, ITooltipProvider {

	@SideOnly(Side.CLIENT) protected IIcon overlayGauge;

	public BlockCableGauge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCableGauge();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":deco_red_copper");
		this.overlayGauge = reg.registerIcon(RefStrings.MODID + ":cable_gauge");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

		if(RenderBlockMultipass.currentPass == 0) {
			return blockIcon;
		}

		return side == world.getBlockMetadata(x, y, z) ? this.overlayGauge : this.blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public int getPasses() {
		return 2;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityCableGauge))
			return;

		TileEntityCableGauge duct = (TileEntityCableGauge) te;

		List<String> text = new ArrayList();
		text.add(BobMathUtil.getShortNumber(duct.deltaTick) + "HE/t");
		text.add(BobMathUtil.getShortNumber(duct.deltaLastSecond) + "HE/s");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
	public static class TileEntityCableGauge extends TileEntityCableBaseNT implements SimpleComponent, CompatHandler.OCComponent, IRORValueProvider {

		private long deltaTick = 0;
		private long deltaSecond = 0;
		private long deltaLastSecond = 0;

		@Override
		public void updateEntity() {
			super.updateEntity();

			if(!worldObj.isRemote) {

				if(this.node != null && this.node.net != null) {

					PowerNetMK2 net = this.node.net;

					this.deltaTick = net.energyTracker;
					if(worldObj.getTotalWorldTime() % 20 == 0) {
						this.deltaLastSecond = this.deltaSecond;
						this.deltaSecond = 0;
					}
					this.deltaSecond += deltaTick;
				}

				networkPackNT(25);
			}
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeLong(deltaTick);
			buf.writeLong(deltaLastSecond);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			this.deltaTick = Math.max(buf.readLong(), 0);
			this.deltaLastSecond = Math.max(buf.readLong(), 0);
		}

		@Override
		@Optional.Method(modid = "OpenComputers")
		public String getComponentName() {
			return "ntm_power_gauge";
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getTransfer(Context context, Arguments args) {
			return new Object[] {deltaTick, deltaLastSecond};
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getInfo(Context context, Arguments args) {
			return new Object[] {deltaTick, deltaLastSecond, xCoord, yCoord, zCoord};
		}

		@Override
		public String[] getFunctionInfo() {
			return new String[] {
					PREFIX_VALUE + "deltatick",
					PREFIX_VALUE + "deltasecond",
			};
		}

		@Override
		public String provideRORValue(String name) {
			if((PREFIX_VALUE + "deltatick").equals(name))	return "" + deltaTick;
			if((PREFIX_VALUE + "deltasecond").equals(name))	return "" + deltaLastSecond;
			return null;
		}
	}
}
