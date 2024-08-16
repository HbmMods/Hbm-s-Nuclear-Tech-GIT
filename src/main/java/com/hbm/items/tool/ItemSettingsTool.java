package com.hbm.items.tool;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds;
import com.hbm.interfaces.ICopiable;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ChatBuilder;
import com.hbm.util.Either;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemSettingsTool extends Item {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		NBTTagList displayInfo;
		if(!(entity instanceof EntityPlayerMP)) return;

		if(((EntityPlayer) entity).getHeldItem() == stack && stack.hasTagCompound()) {
			EntityPlayer player = ((EntityPlayer) entity);
			displayInfo = stack.stackTagCompound.getTagList("displayInfo", 10);

			if (HbmPlayerProps.getData(player).getKeyPressed(HbmKeybinds.EnumKeybind.COPY_TOOL)) {
				int index = stack.stackTagCompound.getInteger("copyIndex") + 1;
				if(index > displayInfo.tagCount() - 1) index = 0;
				stack.stackTagCompound.setInteger("copyIndex", index);
			}

			if(world.getTotalWorldTime() % 5 != 0) return;

			if(displayInfo.tagCount() > 0){
				for (int j = 0; j < displayInfo.tagCount(); j++) {
					NBTTagCompound nbt = displayInfo.getCompoundTagAt(j);
					String info = nbt.getString("info");
					EnumChatFormatting format = stack.stackTagCompound.getInteger("copyIndex") == j ? EnumChatFormatting.AQUA : EnumChatFormatting.YELLOW;
					PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(format + info, 897 + j, 4000 ), (EntityPlayerMP) entity);
				}
			}
		}

	}
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
		Either<TileEntity, Block> schrodinger = getCopyInfoSource(world, x, y, z);
		if(schrodinger == null) return false;
		ICopiable copiable = schrodinger.cast();

		if(player.isSneaking()) {
			stack.stackTagCompound = copiable.getSettings(world, x, y, z);
			stack.stackTagCompound.setString("tileName", copiable.getSettingsSourceID(schrodinger));
			stack.stackTagCompound.setInteger("copyIndex", 0);
			String[] info = copiable.infoForDisplay(world, x, y, z);
			if(info != null) {
				NBTTagList displayInfo = new NBTTagList();
				for (String str : info) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("info", str);
					displayInfo.appendTag(nbt);
				}
				stack.stackTagCompound.setTag("displayInfo", displayInfo);
			}

			if(world.isRemote) {
				player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
					.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
					.next("] ").color(EnumChatFormatting.DARK_AQUA)
					.next("Copied settings of " + copiable.getSettingsSourceDisplay(schrodinger)).color(EnumChatFormatting.AQUA).flush());
			}

		} else if(stack.hasTagCompound()) {
			int index = stack.stackTagCompound.getInteger("copyIndex");
			copiable.pasteSettings(stack.stackTagCompound, index, world, x, y, z);
		}

		return !world.isRemote;
	}

	@Nullable
	private Either<TileEntity, Block> getCopyInfoSource(World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof ICopiable) {
			return Either.left(te);
		}

		Block block = world.getBlock(x, y, z);
		if(block instanceof ICopiable) {
			return Either.right(block);
		}

		return null;
	}
}
