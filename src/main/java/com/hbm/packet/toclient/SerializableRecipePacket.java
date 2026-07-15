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
import io.netty.handler.codec.DecoderException;

public class SerializableRecipePacket implements IMessage {

	private static final int MAX_RECIPE_BYTES = 8 * 1024 * 1024;

	private String filename;
	private byte[] fileBytes;

	private boolean reinit;

	public SerializableRecipePacket() {}

	public SerializableRecipePacket(File recipeFile) {
		try {
			filename = recipeFile.getName();
			fileBytes = Files.readAllBytes(recipeFile.toPath());
		} catch(IOException ex) {
			throw new IllegalStateException("Failed to read recipe file " + recipeFile, ex);
		}
	}

	public SerializableRecipePacket(boolean reinit) {
		this.reinit = reinit;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		reinit = buf.readBoolean();
		if(reinit) return;

		filename = BufferUtil.readString(buf, 255);
		if(filename == null || !filename.matches("[A-Za-z0-9._-]+")) throw new DecoderException("Invalid recipe filename");
		if(buf.readableBytes() < 4) throw new DecoderException("Missing recipe length");
		int length = buf.readInt();
		if(length < 0 || length > MAX_RECIPE_BYTES || length > buf.readableBytes()) throw new DecoderException("Invalid recipe length " + length);
		fileBytes = new byte[length];
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
