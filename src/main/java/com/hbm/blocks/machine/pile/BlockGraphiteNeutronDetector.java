package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.pile.TileEntityPileNeutronDetector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteNeutronDetector extends BlockGraphiteDrilledTE {

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityPileNeutronDetector();
	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon outIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon outIconAluminum;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_detector_aluminum");
		this.outIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_detector_out_aluminum");
		this.outIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_detector_out");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 3;
		
		if(side == cfg * 2 || side == cfg * 2 + 1) {
			if((metadata & 4) == 4)
				return ((metadata & 8) > 0) ? this.outIconAluminum : this.blockIconAluminum;
			return ((metadata & 8) > 0) ? this.outIcon : this.blockIcon;
		}
		
		return this.sideIcon;
	}
	
	public void triggerRods(World world, int x, int y, int z) {
		int oldMeta = world.getBlockMetadata(x, y, z);
		int newMeta = oldMeta ^ 8; //toggle bit #4
		int pureMeta = oldMeta & 3;
		
		world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
		
		world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "hbm:item.techBleep", 0.02F, 1.0F);
		
		ForgeDirection dir = ForgeDirection.getOrientation(pureMeta * 2);
		
		if(dir == ForgeDirection.UNKNOWN)
			return;
		
		for(int i = -1; i <= 1; i += 1) {
			
			int ix = x + dir.offsetX * i;
			int iy = y + dir.offsetY * i;
			int iz = z + dir.offsetZ * i;
			
			while(world.getBlock(ix, iy, iz) == ModBlocks.block_graphite_rod && world.getBlockMetadata(ix, iy, iz) == oldMeta) {
				
				world.setBlockMetadataWithNotify(ix, iy, iz, newMeta, 3);
				
				ix += dir.offsetX * i;
				iy += dir.offsetY * i;
				iz += dir.offsetZ * i;
			}
		}
	}
	
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(!world.isRemote) {
			
			if(tool == ToolType.SCREWDRIVER) {
	
				int meta = world.getBlockMetadata(x, y, z);
				int cfg = meta & 3;
				
				if(!player.isSneaking()) {
					if(side == cfg * 2 || side == cfg * 2 + 1) {
						world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta & 7, 3);
						this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(getInsertedItem()));
					}
				} else {
					TileEntityPileNeutronDetector pile = (TileEntityPileNeutronDetector) world.getTileEntity(x, y, z);
					
					player.addChatComponentMessage(new ChatComponentText("CP1 FUEL ASSEMBLY " + x + " " + y + " " + z).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
					player.addChatComponentMessage(new ChatComponentText("FLUX: " + pile.lastNeutrons + "/" + pile.maxNeutrons).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
				}
			}
			
			if(tool == ToolType.DEFUSER) {
				TileEntityPileNeutronDetector pile = (TileEntityPileNeutronDetector) world.getTileEntity(x, y, z);
				
				if(player.isSneaking()) {
					if(pile.maxNeutrons > 1)
						pile.maxNeutrons--;
				} else {
					pile.maxNeutrons++;
				}
			}
		}
		
		return true;
	}
	
	@Override
	protected Item getInsertedItem() {
		return ModItems.pile_rod_detector;
	}
}
