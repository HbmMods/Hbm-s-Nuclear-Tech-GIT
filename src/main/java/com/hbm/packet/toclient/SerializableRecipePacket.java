package com.hbm.packet.toclient;

import java.io.*;
import java.nio.file.Files;

import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class SerializableRecipePacket implements IMessage {

	private String filename;
	private byte[] fileBytes;

	private boolean reinit;

	public SerializableRecipePacket() {}

	public SerializableRecipePacket(File recipeFile) {
		try {
			filename = recipeFile.getName();
			fileBytes = Files.readAllBytes(recipeFile.toPath());
		} catch(IOException ex) {}
	}

	public SerializableRecipePacket(boolean reinit) {
		this.reinit = reinit;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		reinit = buf.readBoolean();
		if(reinit) return;

		filename = BufferUtil.readString(buf);
		fileBytes = new byte[buf.readInt()];
		buf.readBytes(fileBytes);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(reinit);
		if(reinit) return;

		BufferUtil.writeString(buf, filename);
		buf.writeInt(fileBytes.length);
		buf.writeBytes(fileBytes);
	}

	public static class Handler implements IMessageHandler<SerializableRecipePacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SerializableRecipePacket m, MessageContext ctx) {
			try {

				// Only reinitialize after receiving all recipes
				if(m.reinit) {
					SerializableRecipe.initialize();
					return null;
				}

				SerializableRecipe.receiveRecipes(m.filename, m.fileBytes);

			} catch (Exception x) { }
			return null;
		}
	}

}
