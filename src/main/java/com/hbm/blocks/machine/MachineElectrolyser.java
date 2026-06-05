package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.material.Mats;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityElectrolyser;
import com.hbm.blocks.ILookOverlay;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.ArrayList;
import java.util.List;

public class MachineElectrolyser extends BlockDummyable implements ILookOverlay {

	public MachineElectrolyser() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityElectrolyser();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 5, 5, 1, 3};
	}

	@Override
	public int getOffset() {
		return 5;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, -1);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, -1, 5, 5, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -3, 5, 5, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, 4, -4, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, 2, -2, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, 0, 0, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, -2, 2, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, -4, 4, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world,x + dir.offsetX * 4, y + 3, z + dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world,x + dir.offsetX * 2, y + 3, z + dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y + 3, z, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - dir.offsetX * 2, y + 3, z - dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - dir.offsetX * 4, y + 3, z - dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, this, dir);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - dir.offsetX * 5, y, z - dir.offsetZ * 5);
		this.makeExtra(world, x - dir.offsetX * 5 + rot.offsetX, y, z - dir.offsetZ * 5 + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 5 - rot.offsetX, y, z - dir.offsetZ * 5 - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 5, y, z + dir.offsetZ * 5);
		this.makeExtra(world, x + dir.offsetX * 5 + rot.offsetX, y, z + dir.offsetZ * 5 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 5 - rot.offsetX, y, z + dir.offsetZ * 5 - rot.offsetZ);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		if(!MultiblockHandlerXR.checkSpace(world, x, y , z, getDimensions(), x, y, z, dir)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {2, -1, 5, 5, 1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -3, 5, 5, 0, 0}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, 4, -4, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, 2, -2, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, 0, 0, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, -2, 2, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, -4, 4, -3, 3}, x, y, z, dir)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * 4, y + 3, z + dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * 2, y + 3, z + dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y + 3, z, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x - dir.offsetX * 2, y + 3, z - dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x - dir.offsetX * 4, y + 3, z - dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;

		return true;
	}

	private String getSlotText(int packed, String label) {
		if(packed == 0) return null;
		int itemId = packed >> 16;
		int size = packed & 0xFFFF;
		Item item = Item.getItemById(itemId);
		String name = item == null ? "unknown" : item.getItemStackDisplayName(new ItemStack(item));
		return EnumChatFormatting.YELLOW + label + ": " + EnumChatFormatting.RESET + name + " x" + size;
	}

	private String getFluidText(String label, String name, int fill, int max) {
		return EnumChatFormatting.YELLOW + label + ": " + EnumChatFormatting.RESET + name + " " + fill + "/" + max + "mB";
	}

	private String getMetalText(Mats.MaterialStack stack, String label, EnumChatFormatting color) {
		if(stack == null || stack.material == null) {
			return color + label + ": " + EnumChatFormatting.RESET + "empty";
		} else {
			String name = I18nUtil.resolveKey(stack.material.getUnlocalizedName());
			String formatted = Mats.formatAmount(stack.amount, false);
			return color + label + ": " + EnumChatFormatting.RESET + name + " " + formatted;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityElectrolyser))
			return;

		TileEntityElectrolyser elec = (TileEntityElectrolyser) te;

		List<String> lines = new ArrayList<>();

		String powerClr = (elec.power < TileEntityElectrolyser.maxPower / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN).toString();
		lines.add(powerClr + "Power: " + BobMathUtil.getShortNumber(elec.power) + " / " + BobMathUtil.getShortNumber(TileEntityElectrolyser.maxPower) + "HE");

		boolean isFluidMode = elec.progressFluid > 0 || elec.canProcessFluid();
		boolean isMetalMode = elec.progressOre > 0 || elec.canProcessMetal();

		if(isFluidMode || (!isMetalMode && elec.lastSelectedGUI == 0)) {
			lines.add(EnumChatFormatting.AQUA + "Mode: Fluid Electrolysis");
			lines.add(getFluidText("Input", elec.tanks[0].getTankType().getLocalizedName(), elec.tanks[0].getFill(), elec.tanks[0].getMaxFill()));
			lines.add(getFluidText("Out1", elec.tanks[1].getTankType().getLocalizedName(), elec.tanks[1].getFill(), elec.tanks[1].getMaxFill()));
			lines.add(getFluidText("Out2", elec.tanks[2].getTankType().getLocalizedName(), elec.tanks[2].getFill(), elec.tanks[2].getMaxFill()));

			for(int i = 0; i < 3; i++) {
				String by = getSlotText(elec.clientSlotData[i], "By" + (i+1));
				if(by != null) lines.add(EnumChatFormatting.GREEN + by);
			}

			int percent = (elec.progressFluid * 100 / elec.getDurationFluid());
			lines.add(EnumChatFormatting.AQUA + "Progress: " + EnumChatFormatting.RESET + percent + "%");
		} else {
			lines.add(EnumChatFormatting.AQUA + "Mode: Metal Electrolysis");
			lines.add(getFluidText("Acid", elec.tanks[3].getTankType().getLocalizedName(), elec.tanks[3].getFill(), elec.tanks[3].getMaxFill()));

			if(elec.clientSlotData[3] == 0) {
				lines.add(EnumChatFormatting.GRAY + "Crystal: " + EnumChatFormatting.RESET + "empty");
			} else {
				lines.add(getSlotText(elec.clientSlotData[3], "Crystal"));
			}

			lines.add(getMetalText(elec.leftStack, "Red", EnumChatFormatting.RED));
			lines.add(getMetalText(elec.rightStack, "Green", EnumChatFormatting.GREEN));

			for(int i = 0; i < 6; i++) {
				String out = getSlotText(elec.clientSlotData[4 + i], "Out" + (i+1));
				if(out != null) lines.add(EnumChatFormatting.GOLD + out);
			}

			int percent = (elec.progressOre * 100 / elec.getDurationMetal());
			lines.add(EnumChatFormatting.AQUA + "Progress: " + EnumChatFormatting.RESET + percent + "%");
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, lines);
	}
}
