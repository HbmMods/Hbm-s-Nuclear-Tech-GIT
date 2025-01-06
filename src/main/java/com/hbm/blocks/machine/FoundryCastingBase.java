package com.hbm.blocks.machine;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.block.IToolable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;
import com.hbm.items.machine.ItemScraps;
import com.hbm.tileentity.machine.TileEntityFoundryCastingBase;
import com.hbm.util.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class FoundryCastingBase extends BlockContainer implements ICrucibleAcceptor, IToolable, ILookOverlay {

	protected FoundryCastingBase() {
		super(Material.rock);
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).pour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).canAcceptPartialFlow(world, x, y, z, side, stack);
	}

	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).flow(world, x, y, z, side, stack);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}

		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(x, y, z);

		//remove casted item
		if(cast.slots[1] != null) {
			if(!player.inventory.addItemStackToInventory(cast.slots[1].copy())) {
				EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, cast.slots[1].copy());
				world.spawnEntityInWorld(item);
			} else {
				player.inventoryContainer.detectAndSendChanges();
			}

			cast.slots[1] = null;
			cast.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}

		//insert mold
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.mold && cast.slots[0] == null) {
			Mold mold = ((ItemMold) player.getHeldItem().getItem()).getMold(player.getHeldItem());

			if(mold.size == cast.getMoldSize()) {
				cast.slots[0] = player.getHeldItem().copy();
				cast.slots[0].stackSize = 1;
				player.getHeldItem().stackSize--;
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
				cast.markDirty();
				world.markBlockForUpdate(x, y, z);
				return true;
			}
		}

		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTool && ((ItemTool) player.getHeldItem().getItem()).getToolClasses(player.getHeldItem()).contains("shovel")) {
			if(cast.amount > 0) {
				ItemStack scrap = ItemScraps.create(new MaterialStack(cast.type, cast.amount));
				if(!player.inventory.addItemStackToInventory(scrap)) {
					EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
					world.spawnEntityInWorld(item);
				} else {
					player.inventoryContainer.detectAndSendChanges();
				}
				cast.amount = 0;
				cast.type = null;
				cast.markDirty();
				world.markBlockForUpdate(x, y, z);
			}
			return true;
		}

		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {

		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(x, y, z);
		if(cast.amount > 0) {
			ItemStack scrap = ItemScraps.create(new MaterialStack(cast.type, cast.amount));
			EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
			world.spawnEntityInWorld(item);
			cast.amount = 0; //just for safety
		}

		for(ItemStack stack : cast.slots) {
			if(stack != null) {
				EntityItem drop = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, stack.copy());
				world.spawnEntityInWorld(drop);
			}
		}

		super.breakBlock(world, x, y, z, b, i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(x, y, z);

		if(cast.amount > 0 && cast.amount >= cast.getCapacity()) {
			world.spawnParticle("smoke", x + 0.25 + rand.nextDouble() * 0.5, y + this.maxY, z + 0.25 + rand.nextDouble() * 0.5, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		if(tool != ToolType.SCREWDRIVER)
			return false;

		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(x, y, z);

		if(cast.slots[0] == null) return false;
		if(cast.amount > 0) return false;

		if(!player.inventory.addItemStackToInventory(cast.slots[0].copy())) {
			EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, cast.slots[0].copy());
			world.spawnEntityInWorld(item);
		} else {
			player.inventoryContainer.detectAndSendChanges();
		}

		cast.markDirty();
		world.markBlockForUpdate(x, y, z);

		cast.slots[0] = null;
		cast.markDirty();

		return true;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(x, y, z);
		List<String> text = new ArrayList();

		if(cast.slots[0] == null) {
			text.add(EnumChatFormatting.RED + I18nUtil.resolveKey("foundry.noCast"));
		} else if(cast.slots[0].getItem() == ModItems.mold){
			Mold mold = ((ItemMold) cast.slots[0].getItem()).getMold(cast.slots[0]);
			text.add(EnumChatFormatting.BLUE + mold.getTitle());
		}

		if(cast.type != null && cast.amount > 0) {
			text.add(EnumChatFormatting.YELLOW + cast.type.names[0] + ": " + cast.amount + " / " + cast.getCapacity());
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName() + ".name"), 0xFF4000, 0x401000, text);
	}
}
