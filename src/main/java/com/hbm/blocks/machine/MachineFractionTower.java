package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineFractionTower;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFractionTower extends BlockDummyable {

	public MachineFractionTower(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineFractionTower();
		if(meta >= extra)
			return new TileEntityProxyCombo(false, false, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() == null || player.getHeldItem().getItem() == ModItems.fluid_identifier) {
				int[] pos = this.findCore(world, x, y, z);
					
				if(pos == null)
					return false;
				
				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
				
				if(!(te instanceof TileEntityMachineFractionTower))
					return false;
				
				TileEntityMachineFractionTower frac = (TileEntityMachineFractionTower) te;
				
				if(player.getHeldItem() == null) {
					
					player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "=== FRACTIONING TOWER Y:" + pos[1] + " ==="));

					for(int i = 0; i < frac.tanks.length; i++)
						player.addChatComponentMessage(new ChatComponentTranslation("hbmfluid." + frac.tanks[i].getTankType().getName().toLowerCase()).appendSibling(new ChatComponentText(": " + frac.tanks[i].getFill() + "/" + frac.tanks[i].getMaxFill() + "mB")));
				} else {
					
					if(world.getTileEntity(pos[0], pos[1] - 3, pos[2]) instanceof TileEntityMachineFractionTower) {
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "You can only change the type in the bottom segment!"));
					} else {
						FluidType type = Fluids.fromID(player.getHeldItem().getItemDamage());
						frac.tanks[0].setTankType(type);
						frac.markDirty();
						player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation("hbmfluid." + type.getName().toLowerCase())).appendSibling(new ChatComponentText("!")));
					}
				}
				
				return true;
			}
			return false;
			
		} else {
			return true;
		}
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
}