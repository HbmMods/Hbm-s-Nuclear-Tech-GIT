package com.hbm.blocks.machine;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.block.IToolable;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineStrandCaster;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class MachineStrandCaster extends BlockDummyable implements ICrucibleAcceptor, ILookOverlay, IToolable {

	public MachineStrandCaster() {
		super(Material.iron);
	}

	// reminder, if the machine is a solid brick, get dimensions will already
	// handle it without the need to use fillSapce
	// the order is up, down, forward, backward, left, right
	// x is for left(-)/right(+), z is for forward(+)/backward(-), y you already
	// know
	@Override
	public int[] getDimensions() {
		return new int[] { 0, 0, 6, 0, 1, 0 };
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineStrandCaster();
		if(meta >= 6) return new TileEntityProxyCombo(true, false, true).moltenMetal();
		return null;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		// up,down;forward,backward;left,right
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 2, 0, 1, 0, 1, 0 }, this, dir);
		// Fluid ports
		this.makeExtra(world, x + rot.offsetX - dir.offsetX, y, z + rot.offsetZ - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 5, y, z - dir.offsetZ * 5);
		this.makeExtra(world, x + rot.offsetX - dir.offsetX * 5, y, z + rot.offsetZ - dir.offsetZ * 5);
		// Molten slop ports
		this.makeExtra(world, x + rot.offsetX - dir.offsetX, y + 2, z + rot.offsetZ - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX, y + 2, z - dir.offsetZ);
		this.makeExtra(world, x + rot.offsetX, y + 2, z + rot.offsetZ);
		this.makeExtra(world, x, y + 2, z);
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {

		TileEntity poured = world.getTileEntity(x, y, z);
		if(!(poured instanceof TileEntityProxyCombo && ((TileEntityProxyCombo) poured).moltenMetal)) return false;

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return false;
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(tile instanceof TileEntityMachineStrandCaster)) return false;
		TileEntityMachineStrandCaster caster = (TileEntityMachineStrandCaster) tile;

		return caster.canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public Mats.MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {

		TileEntity poured = world.getTileEntity(x, y, z);
		if(!(poured instanceof TileEntityProxyCombo && ((TileEntityProxyCombo) poured).moltenMetal)) return stack;

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return stack;
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(tile instanceof TileEntityMachineStrandCaster)) return stack;
		TileEntityMachineStrandCaster caster = (TileEntityMachineStrandCaster) tile;

		return caster.pour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		return false;
	}

	@Override
	public Mats.MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}

		int[] coords = findCore(world, x, y, z);
		TileEntityMachineStrandCaster cast = (TileEntityMachineStrandCaster) world.getTileEntity(coords[0], coords[1], coords[2]);
		if(cast != null) {
			// insert mold
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.mold && cast.slots[0] == null) {
				cast.slots[0] = player.getHeldItem().copy();
				cast.slots[0].stackSize = 1;
				player.getHeldItem().stackSize--;
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
				cast.markDirty();
				return true;

			}

			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTool && player.getHeldItem().getItem().getToolClasses(player.getHeldItem()).contains("shovel")) {
				if(cast.amount > 0) {
					ItemStack scrap = ItemScraps.create(new Mats.MaterialStack(cast.type, cast.amount));
					if(!player.inventory.addItemStackToInventory(scrap)) {
						EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
						world.spawnEntityInWorld(item);
					} else {
						player.inventoryContainer.detectAndSendChanges();
					}
					cast.amount = 0;
					cast.type = null;
					cast.markDirty();
				}
				return true;
			}
		}
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {

		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityMachineStrandCaster) {
			TileEntityMachineStrandCaster cast = (TileEntityMachineStrandCaster) te;

			if(cast.amount > 0) {
				ItemStack scrap = ItemScraps.create(new Mats.MaterialStack(cast.type, cast.amount));
				EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
				world.spawnEntityInWorld(item);
				cast.amount = 0; // just for safety
			}
		}
		super.breakBlock(world, x, y, z, b, i);
	}

	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
		int[] coords = findCore(world, x, y, z);
		if(coords == null)
			return;

		TileEntityMachineStrandCaster cast = (TileEntityMachineStrandCaster) world.getTileEntity(coords[0], coords[1], coords[2]);

		List<String> text = new ArrayList();
		if(cast != null) {
			if(cast.slots[0] == null) {
				text.add(EnumChatFormatting.RED + I18nUtil.resolveKey("foundry.noCast"));
			} else if(cast.slots[0].getItem() == ModItems.mold) {
				text.add(EnumChatFormatting.BLUE + cast.getInstalledMold().getTitle());
			}
		}
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName() + ".name"), 0xFF4000, 0x401000, text);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, getDimensions(), x, y, z, dir))
			return false;
		return MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 2, 0, 1, 0, 1, 0 }, x, y, z, dir);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER)
			return false;

		int[] coords = findCore(world, x, y, z);
		TileEntityMachineStrandCaster cast = (TileEntityMachineStrandCaster) world.getTileEntity(coords[0], coords[1], coords[2]);

		if(cast.slots[0] == null)
			return false;

		if(!player.inventory.addItemStackToInventory(cast.slots[0].copy())) {
			EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, cast.slots[0].copy());
			world.spawnEntityInWorld(item);
		} else {
			player.inventoryContainer.detectAndSendChanges();
		}

		cast.slots[0] = null;
		cast.markDirty();

		return true;
	}
}
