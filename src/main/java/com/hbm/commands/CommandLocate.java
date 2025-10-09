package com.hbm.commands;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.hbm.lib.HbmWorld;
import com.hbm.world.gen.nbt.NBTStructure;
import com.hbm.world.gen.nbt.SpawnCondition;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class CommandLocate extends CommandBase {

	private static final int MAX_DISTANCE = 256;

	@Override
	public String getCommandName() {
		return "ntmlocate";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format(Locale.US,
			"%s/%s structure <name> %s- Locates the nearest structure with a given name.",
			EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE
		);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer))
			throw new PlayerNotFoundException();

		if(args.length == 0)
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);

		if(args[0].equals("structure")) {
			EntityPlayer player = (EntityPlayer) sender;

			SpawnCondition structure = NBTStructure.getStructure(args[1]);

			if(structure == null) {
				ChatComponentTranslation message = new ChatComponentTranslation("commands.locate.no_match");
				message.getChatStyle().setColor(EnumChatFormatting.RED);
				sender.addChatMessage(message);
				return;
			}

			int chunkX = MathHelper.floor_double(player.posX) / 16;
			int chunkZ = MathHelper.floor_double(player.posZ) / 16;

			ChunkCoordIntPair pos = getNearestLocationTo(structure, player.worldObj, chunkX, chunkZ);

			if(pos == null) {
				ChatComponentTranslation message = new ChatComponentTranslation("commands.locate.none_found");
				message.getChatStyle().setColor(EnumChatFormatting.RED);
				sender.addChatMessage(message);
				return;
			}

			ChatComponentTranslation message = new ChatComponentTranslation("commands.locate.success.coordinates", structure.name, pos.chunkXPos * 16, pos.chunkZPos * 16);
			message.getChatStyle().setColor(EnumChatFormatting.GREEN);
			sender.addChatMessage(message);
		} else {
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}
	}

	private ChunkCoordIntPair getNearestLocationTo(SpawnCondition spawn, World world, int chunkX, int chunkZ) {
		if(HbmWorld.worldGenerator.getStructureAt(world, chunkX, chunkZ) == spawn)
			return new ChunkCoordIntPair(chunkX, chunkZ);

		for(int radius = 1; radius < MAX_DISTANCE; radius++) {
			for(int x = chunkX - radius; x <= chunkX + radius; x++) {
				if(HbmWorld.worldGenerator.getStructureAt(world, x, chunkZ - radius) == spawn)
					return new ChunkCoordIntPair(x, chunkZ - radius);
				if(HbmWorld.worldGenerator.getStructureAt(world, x, chunkZ + radius) == spawn)
					return new ChunkCoordIntPair(x, chunkZ + radius);
			}
			for(int z = chunkZ - radius; z <= chunkZ + radius; z++) {
				if(HbmWorld.worldGenerator.getStructureAt(world, chunkX - radius, z) == spawn)
					return new ChunkCoordIntPair(chunkX - radius, z);
				if(HbmWorld.worldGenerator.getStructureAt(world, chunkX + radius, z) == spawn)
					return new ChunkCoordIntPair(chunkX + radius, z);
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(args.length < 1)
			return Collections.emptyList();

		if(args.length == 1)
			return getListOfStringsMatchingLastWord(args, "structure");

		if(args.length == 2) {
			List<String> structures = NBTStructure.listStructures();
			return getListOfStringsMatchingLastWord(args, structures.toArray(new String[structures.size()]));
		}

		return Collections.emptyList();
	}

}
