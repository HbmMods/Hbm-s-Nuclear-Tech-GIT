package com.hbm.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;

public class CommandDebugChunkLoad extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmloadchunk";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmloadchunk <x> <z>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length != 2) {
			return;
		}

		int x = this.parseInt(sender, args[0]);
		int z = this.parseInt(sender, args[1]);
		
		IChunkProvider prov = sender.getEntityWorld().getChunkProvider();
		if(prov instanceof ChunkProviderServer) {
			ChunkProviderServer serv = (ChunkProviderServer) prov;
			IChunkLoader loader = serv.currentChunkLoader;
			
			if(loader instanceof AnvilChunkLoader) {
				AnvilChunkLoader anvil = (AnvilChunkLoader) loader;
				
				try {
					int cX = x >> 4;
					int cZ = z >> 4;
					
					if(prov.chunkExists(cX, cZ)) {
						Chunk chunk = sender.getEntityWorld().getChunkFromChunkCoords(cX, cZ);
						if(chunk.isChunkLoaded) {
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Chunk currently loaded."));
							return;
						}
					}
					
					Object[] data = anvil.loadChunk__Async(sender.getEntityWorld(), cX, cZ);
					//Chunk chunk = (Chunk) data[0];
					NBTTagCompound nbt = (NBTTagCompound) data[1];
					NBTTagCompound level = nbt.getCompoundTag("Level");
					NBTTagList tagList = level.getTagList("TileEntities", 10);

					if(tagList != null) {
						
						if(tagList.tagCount() <= 0) {
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Tag list empty"));
						}
						
						for(int i1 = 0; i1 < tagList.tagCount(); ++i1) {
							NBTTagCompound tileCompound = tagList.getCompoundTagAt(i1);
							int tX = tileCompound.getInteger("x");
							int tY = tileCompound.getInteger("y");
							int tZ = tileCompound.getInteger("z");
							String name = tileCompound.getString("id");
							
							int i = tX - cX * 16;
							int j = tY;
							int k = tZ - cZ * 16;
							
							EnumChatFormatting color = EnumChatFormatting.GREEN;
							
							if(i < 0 || i > 15 || j < 0 || j > 255 || k < 0 || k > 15) {
								color = EnumChatFormatting.RED;
							}
							
							sender.addChatMessage(new ChatComponentText(color + name + " " + i + " " + j + " " + k));
							
							if(i < 0 || i > 15 || j < 0 || j > 255 || k < 0 || k > 15) {
								tileCompound.setString("id", "INVALID_POS_" + name);
								
								NBTTagCompound nbttagcompound = new NBTTagCompound();
								NBTTagCompound nbttagcompound1 = new NBTTagCompound();
								nbttagcompound.setTag("Level", nbttagcompound1);
								// anvil.writeChunkToNBT(chunk, sender.getEntityWorld(), nbttagcompound1);
								// anvil.addChunkToPending(chunk.getChunkCoordIntPair(), nbttagcompound);
							}
						}
					} else {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Tag list null"));
					}
					
				} catch(Exception e) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + e.getLocalizedMessage()));
				}
			} else {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Not AnvilChunkLoader"));
			}
		} else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Not ChunkProviderServer"));
		}
	}
}
