package com.hbm.packet.toserver;

import com.hbm.inventory.container.ContainerAnvil;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.util.AchievementHandler;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class AnvilCraftPacket implements IMessage {

	int recipeIndex;
	int mode;

	public AnvilCraftPacket() { }

	public AnvilCraftPacket(AnvilConstructionRecipe recipe, int mode) {
		this.recipeIndex = AnvilRecipes.getConstruction().indexOf(recipe);
		this.mode = mode;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.recipeIndex = buf.readInt();
		this.mode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.recipeIndex);
		buf.writeInt(this.mode);
	}

	public static class Handler implements IMessageHandler<AnvilCraftPacket, IMessage> {
		
		@Override
		public IMessage onMessage(AnvilCraftPacket m, MessageContext ctx) {
			
			if(m.recipeIndex < 0 || m.recipeIndex >= AnvilRecipes.getConstruction().size()) //recipe is out of range -> bad
				return null;
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			if(!(p.openContainer instanceof ContainerAnvil)) //player isn't even using an anvil -> bad
				return null;
			
			ContainerAnvil anvil = (ContainerAnvil) p.openContainer;
			AnvilConstructionRecipe recipe = AnvilRecipes.getConstruction().get(m.recipeIndex);
			
			if(!recipe.isTierValid(anvil.tier)) //player is using the wrong type of anvil -> bad
				return null;
			
			int count = m.mode == 1 ? (recipe.output.size() > 1 ? 64 : (recipe.output.get(0).stack.getMaxStackSize() / recipe.output.get(0).stack.stackSize)) : 1;
			
			for(int i = 0; i < count; i++) {
				
				if(InventoryUtil.doesPlayerHaveAStacks(p, recipe.input, true)) {
					InventoryUtil.giveChanceStacksToPlayer(p, recipe.output);
					AchievementHandler.fire(p, recipe.output.get(0).stack);
					
				} else {
					break;
				}
			}
			
			p.inventoryContainer.detectAndSendChanges();
			
			return null;
		}
	}
}
