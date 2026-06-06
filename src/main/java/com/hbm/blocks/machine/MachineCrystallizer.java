package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;
import com.hbm.blocks.ILookOverlay;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.config.ClientConfig;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Arrays;

public class MachineCrystallizer extends BlockDummyable implements ILookOverlay {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public MachineCrystallizer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineCrystallizer();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			TileEntityMachineCrystallizer entity = (TileEntityMachineCrystallizer) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 5, 0, 1, 1, 1, 1 };
	}

	@Override
	public int getOffset() {
		return 1;
	}

	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o - 1);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o - 1);
	}

	private String getSlotText(int packed, String label) {
		if(packed == 0) {
			return EnumChatFormatting.GRAY + label + ": " + EnumChatFormatting.RESET + "empty";
		} else {
			int itemId = packed >> 16;
			int size = packed & 0xFFFF;
			Item item = Item.getItemById(itemId);
			String name = item == null ? "unknown" : item.getItemStackDisplayName(new ItemStack(item));
			return EnumChatFormatting.YELLOW + label + ": " + EnumChatFormatting.RESET + name + " x" + size;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		if(!ClientConfig.MACHINE_OVERLAY_ENABLED.get()) return;
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityMachineCrystallizer)) return;

		TileEntityMachineCrystallizer crys = (TileEntityMachineCrystallizer) te;

		String powerClr = (crys.power < TileEntityMachineCrystallizer.maxPower / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN).toString();
		String powerTxt = powerClr + "Power: " + BobMathUtil.getShortNumber(crys.power) + " / " + BobMathUtil.getShortNumber(TileEntityMachineCrystallizer.maxPower) + "HE";

		String fluidTxt = EnumChatFormatting.AQUA + "Fluid: " + EnumChatFormatting.RESET + crys.tank.getTankType().getLocalizedName() + " " + crys.tank.getFill() + "/" + crys.tank.getMaxFill() + "mB";

		String inputTxt = getSlotText(crys.clientSlotData[0], "In");
		String outputTxt = getSlotText(crys.clientSlotData[1], "Out");

		int percent = (crys.progress * 100 / crys.duration);
		String progTxt = EnumChatFormatting.AQUA + "Progress: " + EnumChatFormatting.RESET + percent + "%";

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, Arrays.asList(powerTxt, fluidTxt, inputTxt, outputTxt, progTxt));
	}
}
