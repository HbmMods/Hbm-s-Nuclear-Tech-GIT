package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.ArmorModHandler;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelJetpackModular;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class JetpackModular extends ItemArmorMod implements IItemRendererProvider {

	public JetpackModular() {
		super(ArmorModHandler.plate_only, false, true, false, false);
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!(entity instanceof EntityPlayer)) return;
		
		ItemStack jetpack = ArmorModHandler.pryMods(armor)[ArmorModHandler.plate_only];
		if(jetpack == null) return;
				
		onArmorTick(entity.worldObj, (EntityPlayer)entity, jetpack);
		ArmorModHandler.applyMod(armor, jetpack);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.SetArmorModel event, ItemStack armor) {

		ModelBiped modelJetpack = getArmorModel(event.entityLiving, null, 1);
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;

		modelJetpack.isSneak = model.isSneak;
		modelJetpack.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}

	@Override public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) { return armorType == 1; }

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return ModelJetpackModular.INSTANCE;
	}

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { GL11.glTranslated(5, -12, 0); }
			public void renderNonInv() { GL11.glScaled(0.25, 0.25, 0.25); GL11.glTranslated(0, -12, 5); }
			public void renderCommonWithStack(ItemStack stack) {
				ModelJetpackModular.INSTANCE.renderFromItem(stack, true, 1F);
			}};
	}
}
