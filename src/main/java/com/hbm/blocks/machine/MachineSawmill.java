package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntitySawmill;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSawmill extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineSawmill() {
		super(Material.iron);

		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 0D, -1.5D, 1.5D, 1D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.25D, 1D, -0.5D, -0.625D, 1.875D, 0.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.625D, 1D, -1D, 1.375D, 2D, 1D));
		this.maxY = 0.999D; //item bounce prevention
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntitySawmill();
		
		if(meta >= extra)
			return new TileEntityProxyCombo().inventory();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		this.makeExtra(world, x + 1, y, z);
		this.makeExtra(world, x - 1, y, z);
		this.makeExtra(world, x, y, z + 1);
		this.makeExtra(world, x, y, z - 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			TileEntitySawmill sawmill = (TileEntitySawmill)world.getTileEntity(pos[0], pos[1], pos[2]);
			
			if(!sawmill.hasBlade && player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.sawblade) {
				player.getHeldItem().stackSize--;
				sawmill.hasBlade = true;
				sawmill.markDirty();
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:item.upgradePlug", 1.5F, 0.75F);
				return true;
			}
			
			if(sawmill.slots[1] != null || sawmill.slots[2] != null) {
				for(int i = 1; i < 3; i++) {
					if(sawmill.slots[i] != null) {
						if(!player.inventory.addItemStackToInventory(sawmill.slots[i].copy())) {
							player.dropPlayerItemWithRandomChoice(sawmill.slots[i].copy(), false);
						}
						sawmill.slots[i] = null;
					}
				}
				player.inventoryContainer.detectAndSendChanges();
				sawmill.markDirty();
				return true;
				
			} else {
				if(sawmill.slots[0] == null && player.getHeldItem() != null && sawmill.getOutput(player.getHeldItem()) != null) {
					sawmill.slots[0] = player.getHeldItem().copy();
					sawmill.slots[0].stackSize = 1;
					player.getHeldItem().stackSize--;
					sawmill.markDirty();
					player.inventoryContainer.detectAndSendChanges();
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
		
		if(itemStack.getItemDamage() == 1) {

			int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			int o = -getOffset();
			
			ForgeDirection dir = ForgeDirection.NORTH;
			if(i == 0) dir = ForgeDirection.getOrientation(2);
			if(i == 1) dir = ForgeDirection.getOrientation(5);
			if(i == 2) dir = ForgeDirection.getOrientation(3);
			if(i == 3) dir = ForgeDirection.getOrientation(4);
			
			dir = getDirModified(dir);

			TileEntity te = world.getTileEntity(x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o);
			
			if(te instanceof TileEntitySawmill) {
				((TileEntitySawmill) te).hasBlade = false;
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		int dmg = 0;

		int[] pos = this.findCore(world, x, y, z);
		
		if(pos != null) {
			TileEntitySawmill stirling = (TileEntitySawmill)world.getTileEntity(pos[0], pos[1], pos[2]);
			if(!stirling.hasBlade) {
				dmg = 1;
			}
		}
		
		for(int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if(item != null) {
				ret.add(new ItemStack(item, 1, dmg));
			}
		}
		return ret;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntitySawmill))
			return;
		
		TileEntitySawmill stirling = (TileEntitySawmill) te;

		List<String> text = new ArrayList();
		text.add(stirling.heat + "TU/t");

		double percent = (double) stirling.heat / (double) 300;
		int color = ((int) (0xFF - 0xFF * percent)) << 16 | ((int)(0xFF * percent) << 8);
		
		if(percent > 1D)
			color = 0xff0000;
		
		text.add("&[" + color + "&]" + ((stirling.heat * 1000 / 300) / 10D) + "%");
		
		int limiter = stirling.progress * 26 / stirling.processingTime;
		String bar = EnumChatFormatting.GREEN + "[ ";
		for(int i = 0; i < 25; i++) {
			if(i == limiter) {
				bar += EnumChatFormatting.RESET;
			}
			
			bar += "▏";
		}
		
		bar += EnumChatFormatting.GREEN + " ]";
		
		text.add(bar);
		
		for(int i = 0; i < 3; i++) {
			if(stirling.slots[i] != null) {
				text.add((i == 0 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + stirling.slots[i].getDisplayName() + (stirling.slots[i].stackSize > 1 ? " x" + stirling.slots[i].stackSize : ""));
			}
		}
		
		if(stirling.heat > 300) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! OVERSPEED ! ! !");
		}
		
		if(!stirling.hasBlade) {
			text.add("&[" + 0xff0000 + "&]Blade missing!");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
