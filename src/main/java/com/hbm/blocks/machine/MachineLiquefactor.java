package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.UpgradeManager;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineLiquefactor;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineLiquefactor extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineLiquefactor() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineLiquefactor();
		
		if(meta >= extra)
			return new TileEntityProxyCombo(true, true, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 1, 1, 1, 1};
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
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

		this.makeExtra(world, x, y + 3, z);
		
		this.makeExtra(world, x + 1, y + 1, z);
		this.makeExtra(world, x - 1, y + 1, z);
		this.makeExtra(world, x, y + 1, z + 1);
		this.makeExtra(world, x, y + 1, z - 1);
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
		
		if(!(te instanceof TileEntityMachineLiquefactor))
			return;
		
			TileEntityMachineLiquefactor heater = (TileEntityMachineLiquefactor) te;
        int gain=100*(heater.processTimeBase-heater.processTime)/heater.processTimeBase;
		if(gain<0){
			gain=0;
		}
		List<String> text = new ArrayList();
		text.add(heater.heat + "TU");
		text.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("info.heating_gain") + String.format("%,d", gain) +"%"  );
		text.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("info.heating_pgain") + String.format("%.2f", heater.pincrease)  );
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
