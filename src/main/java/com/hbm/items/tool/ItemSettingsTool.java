package com.hbm.items.tool;

import com.hbm.interfaces.ICopiable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.IDroneLinkable;
import com.hbm.util.ChatBuilder;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemSettingsTool extends Item {


	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Can copy the settings (filters, fluid ID, etc) of machines");
		list.add("Shift right-click to copy, right click to paste");
		if(stack.stackTagCompound != null) {
			NBTTagCompound nbt = stack.stackTagCompound;
			list.add(EnumChatFormatting.YELLOW + "Current machine:"
					+ (nbt.hasKey("tileName") ? EnumChatFormatting.BLUE + " " + nbt.getString("tileName") : EnumChatFormatting.RED + " None "));
		}

	}
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);

			if (tile instanceof ICopiable) {
				ICopiable te = ((ICopiable) tile);

					if (player.isSneaking()) {
						stack.stackTagCompound = ((ICopiable) tile).getSettings();
						stack.stackTagCompound.setString("tileName", tile.getBlockType().getLocalizedName());

						player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
								.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
								.next("] ").color(EnumChatFormatting.DARK_AQUA)
								.next("Copied settings of " + tile.getBlockType().getLocalizedName()).color(EnumChatFormatting.AQUA).flush());

					} else if (stack.hasTagCompound()) {
						te.pasteSettings(stack.stackTagCompound);
					}


				return true;
			}
		}
		return false;
	}
}
