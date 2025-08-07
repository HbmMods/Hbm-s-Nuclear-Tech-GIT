package com.hbm.main;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockAshes;
import com.hbm.blocks.generic.BlockRebar;
import com.hbm.config.ClientConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.train.EntityRailCarRidable;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.HTTPHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.hazard.HazardSystem;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.gui.GUIArmorTable;
import com.hbm.inventory.gui.GUIScreenPreview;
import com.hbm.inventory.gui.GUIScreenWikiRender;
import com.hbm.inventory.gui.LoadingScreenRendererNT;
import com.hbm.items.ItemCustomLore;
import com.hbm.items.ModItems;
import com.hbm.items.armor.*;
import com.hbm.items.machine.ItemDepletedFuel;
import com.hbm.items.machine.ItemFluidDuct;
import com.hbm.items.machine.ItemRBMKPellet;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.qmaw.GuiQMAW;
import com.hbm.qmaw.QMAWLoader;
import com.hbm.qmaw.QuickManualAndWiki;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.item.weapon.sedna.ItemRenderWeaponBase;
import com.hbm.render.util.RenderAccessoryUtility;
import com.hbm.render.util.RenderOverhead;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.render.world.RenderNTMSkyboxChainloader;
import com.hbm.render.world.RenderNTMSkyboxImpact;
import com.hbm.sound.MovingSoundChopper;
import com.hbm.sound.MovingSoundChopperMine;
import com.hbm.sound.MovingSoundCrashing;
import com.hbm.sound.MovingSoundPlayerLoop;
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.CustomNukeEntry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.EnumEntryType;
import com.hbm.util.*;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.GuiWorldInAJar;
import com.hbm.wiaj.cannery.CanneryBase;
import com.hbm.wiaj.cannery.Jars;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Method;
import java.util.*;

public class ModEventHandlerClient {

	public static final int flashDuration = 5_000;
	public static long flashTimestamp;
	public static final int shakeDuration = 1_500;
	public static long shakeTimestamp;

	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		/// NUKE FLASH ///
		if(event.type == ElementType.CROSSHAIRS && (flashTimestamp + flashDuration - Clock.get_ms()) > 0 && ClientConfig.NUKE_HUD_FLASH.get()) {
			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();
			Tessellator tess = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
			GL11.glDepthMask(false);
			tess.startDrawingQuads();
			float brightness = (flashTimestamp + flashDuration - Clock.get_ms()) / (float) flashDuration;
			tess.setColorRGBA_F(1F, 1F, 1F, brightness * 1F);
			tess.addVertex(width, 0, 0);
			tess.addVertex(0, 0, 0);
			tess.addVertex(0, height, 0);
			tess.addVertex(width, height, 0);
			tess.draw();
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDepthMask(true);
			return;
		}

		/*if(event.type == ElementType.CROSSHAIRS && player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.gun_aberrator) {
			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();
			Tessellator tess = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
			GL11.glDepthMask(false);
			tess.startDrawingQuads();
			float intensity = 0.2F;
			tess.setColorRGBA_F(intensity, intensity, intensity, 1F);
			tess.addVertex(width, 0, 0);
			tess.addVertex(0, 0, 0);
			tess.addVertex(0, height, 0);
			tess.addVertex(width, height, 0);
			tess.draw();
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDepthMask(true);
		}*/

		/// HANDLE GUN OVERLAYS ///
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemHUD) {
			((IItemHUD)player.getHeldItem().getItem()).renderHUD(event, event.type, player, player.getHeldItem());
		}

		/// HANDLE GEIGER COUNTER HUD ///
		if(event.type == ElementType.HOTBAR) {

			if(!(ArmorFSB.hasFSBArmor(player) && ((ArmorFSB)player.inventory.armorInventory[2].getItem()).customGeiger)) {

				if(player.inventory.hasItem(ModItems.geiger_counter)) {

					float rads = HbmLivingProps.getRadiation(player);

					RenderScreenOverlay.renderRadCounter(event.resolution, rads, Minecraft.getMinecraft().ingameGUI);
				}
			}
		}

		/// DODD DIAG HOOK FOR RBMK
		if(event.type == ElementType.CROSSHAIRS && ClientConfig.DODD_RBMK_DIAGNOSTIC.get()) {
			Minecraft mc = Minecraft.getMinecraft();
			World world = mc.theWorld;
			MovingObjectPosition mop = mc.objectMouseOver;

			if(mop != null) {

				if(mop.typeOfHit == mop.typeOfHit.BLOCK) {

					if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ILookOverlay) {
						((ILookOverlay) player.getHeldItem().getItem()).printHook(event, world, mop.blockX, mop.blockY, mop.blockZ);

					} else if(world.getBlock(mop.blockX, mop.blockY, mop.blockZ) instanceof ILookOverlay) {
						((ILookOverlay) world.getBlock(mop.blockX, mop.blockY, mop.blockZ)).printHook(event, world, mop.blockX, mop.blockY, mop.blockZ);
					}

					/*List<String> text = new ArrayList();
					text.add("Meta: " + world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
					ILookOverlay.printGeneric(event, "DEBUG", 0xffff00, 0x4040000, text);*/

				} else if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
					Entity entity = mop.entityHit;

					if(entity instanceof ILookOverlay) {
						((ILookOverlay) entity).printHook(event, world, 0, 0, 0);
					}
				}
				
				GL11.glColor4f(1F, 1F, 1F, 1F);
			}

			/*List<String> text = new ArrayList();
			text.add("IMPACT: " + ImpactWorldHandler.getImpactForClient(world));
			text.add("DUST: " + ImpactWorldHandler.getDustForClient(world));
			text.add("FIRE: " + ImpactWorldHandler.getFireForClient(world));
			ILookOverlay.printGeneric(event, "DEBUG", 0xffff00, 0x4040000, text);*/

			/*if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
				ScaledResolution resolution = event.resolution;
				GL11.glPushMatrix();
				int pX = resolution.getScaledWidth() / 2 + 8;
				int pZ = resolution.getScaledHeight() / 2;
				mc.fontRenderer.drawString("META: " + world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ), pX, pZ - 3, 0xffff00);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glPopMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
			}*/

			/*List<String> text = new ArrayList();
			MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);

			for(int i = 0; i < 2; i++) if(pos != null && pos.typeOfHit == pos.typeOfHit.BLOCK) {

				float yaw = player.rotationYaw;

				Vec3 next = Vec3.createVectorHelper(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord);
				int it = 0;

				BlockPos anchor = new BlockPos(pos.blockX, pos.blockY, pos.blockZ);

				double distanceToCover = 4D * (i == 0 ? 1 : -1);

				if(distanceToCover < 0) {
					distanceToCover *= -1;
					yaw += 180;
				}

				do {

					it++;

					if(it > 30) {
						world.createExplosion(player, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 5F, false);
						break;
					}

					int x = anchor.getX();
					int y = anchor.getY();
					int z = anchor.getZ();
					Block block = world.getBlock(x, y, z);

					Vec3 rot = Vec3.createVectorHelper(0, 0, 1);
					rot.rotateAroundY((float) (-yaw * Math.PI / 180D));

					if(block instanceof IRailNTM) {
						IRailNTM rail = (IRailNTM) block;
						RailContext info = new RailContext();

						boolean flip = distanceToCover < 0;

						if(it == 1) {
							Vec3 snap = next = rail.getTravelLocation(world, x, y, z, next.xCoord, next.yCoord, next.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, 0, info, new MoveContext(RailCheckType.CORE, 0));
							if(i == 0) world.spawnParticle("reddust", snap.xCoord, snap.yCoord + 0.25, snap.zCoord, 0.1, 1, 0.1);
						}

						Vec3 prev = next;
						next = rail.getTravelLocation(world, x, y, z, prev.xCoord, prev.yCoord, prev.zCoord, rot.xCoord, rot.yCoord, rot.zCoord, distanceToCover, info, new MoveContext(i == 0 ? RailCheckType.FRONT : RailCheckType.BACK, 0));
						distanceToCover = info.overshoot;
						anchor = info.pos;
						if(i == 0) world.spawnParticle("reddust", next.xCoord, next.yCoord + 0.25, next.zCoord, 0, distanceToCover != 0 ? 0.5 : 0, 0);
						else world.spawnParticle("reddust", next.xCoord, next.yCoord + 0.25, next.zCoord, 0, distanceToCover != 0 ? 0.5 : 0, 1);

						double deltaX = next.xCoord - prev.xCoord;
						double deltaZ = next.zCoord - prev.zCoord;
						double radians = -Math.atan2(deltaX, deltaZ);
						yaw = (float) MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI + (flip ? 180 : 0));

						text.add(it + ": " + yaw);

					} else {
						break;
					}

				} while(distanceToCover != 0);

				ILookOverlay.printGeneric(event, "DEBUG", 0xffff00, 0x4040000, text);
			}*/
		}

		/// HANLDE ANIMATION BUSES ///

		for(int i = 0; i < HbmAnimations.hotbar.length; i++) {
			for(int j = 0; j < HbmAnimations.hotbar[i].length; j++) {

				Animation animation = HbmAnimations.hotbar[i][j];

				if(animation == null)
					continue;

				if(animation.holdLastFrame)
					continue;

				long time = Clock.get_ms() - animation.startMillis;

				if(time > animation.animation.getDuration())
					HbmAnimations.hotbar[i][j] = null;
			}
		}

		if(!ducked && Keyboard.isKeyDown(Keyboard.KEY_O) && Minecraft.getMinecraft().currentScreen == null) {
			ducked = true;
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(0, 0, 0, 999, 0));
		}

		/// HANDLE SCOPE OVERLAY ///
		ItemStack held = player.getHeldItem();

		if(held != null && held.getItem() instanceof ItemGunBaseNT && ItemGunBaseNT.aimingProgress == ItemGunBaseNT.prevAimingProgress && ItemGunBaseNT.aimingProgress == 1F && event.type == event.type.HOTBAR)  {
			ItemGunBaseNT gun = (ItemGunBaseNT) held.getItem();
			GunConfig cfg = gun.getConfig(held, 0);
			if(cfg.getScopeTexture(held) != null) {
				ScaledResolution resolution = event.resolution;
				RenderScreenOverlay.renderScope(resolution, cfg.getScopeTexture(held));
			}
		}

		//prevents NBT changes (read: every fucking tick) on guns from bringing up the item's name over the hotbar
		if(held != null && held.getItem() instanceof ItemGunBaseNT && Minecraft.getMinecraft().ingameGUI.highlightingItemStack != null && Minecraft.getMinecraft().ingameGUI.highlightingItemStack.getItem() == held.getItem()) {
			Minecraft.getMinecraft().ingameGUI.highlightingItemStack = held;
		}

		/// HANDLE FSB HUD ///
		ItemStack helmet = player.inventory.armorInventory[3];

		if(helmet != null && helmet.getItem() instanceof ArmorFSB) {
			((ArmorFSB)helmet.getItem()).handleOverlay(event, player);
		}
		if(!event.isCanceled() && event.type == event.type.HOTBAR) {

			HbmPlayerProps props = HbmPlayerProps.getData(player);
			if(props.getDashCount() > 0) {
				RenderScreenOverlay.renderDashBar(event.resolution, Minecraft.getMinecraft().ingameGUI, props);

			}
		}
	}

	@SubscribeEvent(receiveCanceled = true)
	public void onHUDRenderShield(RenderGameOverlayEvent.Pre event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if(event.type == event.type.ARMOR) {

			HbmPlayerProps props = HbmPlayerProps.getData(player);
			if(props.getEffectiveMaxShield() > 0) {
				RenderScreenOverlay.renderShieldBar(event.resolution, Minecraft.getMinecraft().ingameGUI);
			}
		}
	}

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
	public void onHUDRenderBar(RenderGameOverlayEvent.Post event) {

		/// HANDLE ELECTRIC FSB HUD ///

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		Tessellator tess = Tessellator.instance;

		if(event.type == event.type.ARMOR) {

			if(ForgeHooks.getTotalArmorValue(player) == 0) {
				GuiIngameForge.left_height -= 10;
			}

			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();
			int left = width / 2 - 91;

			if(ArmorFSB.hasFSBArmorIgnoreCharge(player)) {
				ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory[2].getItem();
				boolean noHelmet = chestplate.noHelmet;

				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tess.startDrawingQuads();

				for(int i = 0; i < (noHelmet ? 3 : 4); i++) {

					int top = height - GuiIngameForge.left_height + 7;

					ItemStack stack = player.inventory.armorInventory[i];

					if(!(stack != null && stack.getItem() instanceof ArmorFSBPowered))
						break;

					float tot = 1F - (float) ((ArmorFSBPowered) stack.getItem()).getDurabilityForDisplay(stack);

					tess.setColorOpaque_F(0.25F, 0.25F, 0.25F);
					tess.addVertex(left - 0.5, top - 0.5, 0);
					tess.addVertex(left - 0.5, top + 1.5, 0);
					tess.addVertex(left + 81.5, top + 1.5, 0);
					tess.addVertex(left + 81.5, top - 0.5, 0);

					tess.setColorOpaque_F(1F - tot, tot, 0F);
					tess.addVertex(left, top, 0);
					tess.addVertex(left, top + 1, 0);
					tess.addVertex(left + 81 * tot, top + 1, 0);
					tess.addVertex(left + 81 * tot, top, 0);

					GuiIngameForge.left_height += 3;
				}

				tess.draw();

				GL11.glEnable(GL11.GL_TEXTURE_2D);

			} else if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof JetpackFueledBase) {

				ItemStack stack = player.inventory.armorInventory[2];

				float tot = (float) ((JetpackFueledBase) stack.getItem()).getFuel(stack) / (float) ((JetpackFueledBase) stack.getItem()).getMaxFill(stack);

				int top = height - GuiIngameForge.left_height + 3;

				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tess.startDrawingQuads();
				tess.setColorOpaque_F(0.25F, 0.25F, 0.25F);
				tess.addVertex(left - 0.5, top - 0.5, 0);
				tess.addVertex(left - 0.5, top + 4.5, 0);
				tess.addVertex(left + 81.5, top + 4.5, 0);
				tess.addVertex(left + 81.5, top - 0.5, 0);

				tess.setColorOpaque_F(1F - tot, tot, 0F);
				tess.addVertex(left, top, 0);
				tess.addVertex(left, top + 4, 0);
				tess.addVertex(left + 81 * tot, top + 4, 0);
				tess.addVertex(left + 81 * tot, top, 0);
				tess.draw();

				GL11.glEnable(GL11.GL_TEXTURE_2D);

			}
		}
	}

	@SubscribeEvent
	public void setupFOV(FOVUpdateEvent event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack held = player.getHeldItem();

		if(held == null) return;

		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(held, IItemRenderer.ItemRenderType.EQUIPPED);
		if(!(customRenderer instanceof ItemRenderWeaponBase)) return;
		ItemRenderWeaponBase renderGun = (ItemRenderWeaponBase) customRenderer;
		event.newfov = renderGun.getViewFOV(held, event.fov);
	}

	public static boolean ducked = false;

	@SubscribeEvent
	public void preRenderEvent(RenderPlayerEvent.Pre event) {

		RenderPlayer renderer = event.renderer;
		AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;

		PotionEffect invis = player.getActivePotionEffect(Potion.invisibility);

		if(invis != null && invis.getAmplifier() > 0)
			event.setCanceled(true);

		if(player.getDisplayName().toLowerCase(Locale.US).equals("martmn")) {

			event.setCanceled(true);

			float pX = (float) (player.prevPosX + (player.posX - player.prevPosX) * (double)event.partialRenderTick);
			float pY = (float) (player.prevPosY + (player.posY - player.prevPosY) * (double)event.partialRenderTick);
			float pZ = (float) (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)event.partialRenderTick);
			EntityPlayer me = Minecraft.getMinecraft().thePlayer;
			float mX = (float) (me.prevPosX + (me.posX - me.prevPosX) * (double)event.partialRenderTick);
			float mY = (float) (me.prevPosY + (me.posY - me.prevPosY) * (double)event.partialRenderTick);
			float mZ = (float) (me.prevPosZ + (me.posZ - me.prevPosZ) * (double)event.partialRenderTick);

			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/particle/fart.png"));
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glTranslatef(pX - mX, pY - mY + 0.75F - (float)player.getYOffset(), pZ - mZ);
			GL11.glRotatef(-me.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(me.rotationPitch, 1.0F, 0.0F, 0.0F);
			Tessellator t = Tessellator.instance;
			t.startDrawingQuads();
			t.setBrightness(240);
			t.addVertexWithUV(-1, 1, 0, 0, 0);
			t.addVertexWithUV(1, 1, 0, 1, 0);
			t.addVertexWithUV(1, -1, 0, 1, 1);
			t.addVertexWithUV(-1, -1, 0, 0, 1);
			t.draw();

			GL11.glEnable(GL11.GL_LIGHTING);

			GL11.glPopMatrix();
		}

		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);

		if(cloak != null)
			player.func_152121_a(Type.CAPE, cloak);

		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IHoldableWeapon) {
			renderer.modelBipedMain.aimedBow = true;
			renderer.modelArmor.aimedBow = true;
			renderer.modelArmorChestplate.aimedBow = true;
		}
	}

	@SubscribeEvent
	public void onRenderArmorEvent(RenderPlayerEvent.SetArmorModel event) {

		EntityPlayer player = event.entityPlayer;

		for(int i = 0; i < 4; i++) {

			ItemStack armor = player.getCurrentArmor(i);

			if(armor != null && ArmorModHandler.hasMods(armor)) {

				for(ItemStack mod : ArmorModHandler.pryMods(armor)) {

					if(mod != null && mod.getItem() instanceof ItemArmorMod) {
						((ItemArmorMod)mod.getItem()).modRender(event, armor);
					}
				}
			}

			//because armor that isn't ItemArmor doesn't render at all
			if(armor != null && armor.getItem() instanceof JetpackBase) {
				((ItemArmorMod)armor.getItem()).modRender(event, armor);
			}
		}

		if(player.getCurrentArmor(2) == null && !player.isPotionActive(Potion.invisibility)) {
			if(player.getUniqueID().toString().equals(ShadyUtil.HbMinecraft) ||		player.getDisplayName().equals("HbMinecraft"))		RenderAccessoryUtility.renderWings(event, 2);
			if(player.getUniqueID().toString().equals(ShadyUtil.the_NCR) ||			player.getDisplayName().equals("the_NCR"))			RenderAccessoryUtility.renderWings(event, 3);
			if(player.getUniqueID().toString().equals(ShadyUtil.Barnaby99_x) ||		player.getDisplayName().equals("pheo7"))			RenderAccessoryUtility.renderAxePack(event);
			if(player.getUniqueID().toString().equals(ShadyUtil.LePeeperSauvage) ||	player.getDisplayName().equals("LePeeperSauvage"))	RenderAccessoryUtility.renderFaggot(event);
		}
	}

	@Spaghetti("please get this shit out of my face")
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 e) {

		EntityPlayer player = MainRegistry.proxy.me();
		Minecraft mc = Minecraft.getMinecraft();

		if(player != null && mc.theWorld != null) {
			int i = MathHelper.floor_double(player.posX);
			int j = MathHelper.floor_double(player.posY);
			int k = MathHelper.floor_double(player.posZ);
			Block block = mc.theWorld.getBlock(i, j, k);

			if(block == ModBlocks.vacuum) {
				e.result = null;
				return;
			}
		}

		ResourceLocation r = e.sound.getPositionedSoundLocation();

		WorldClient wc = mc.theWorld;

		//Alright, alright, I give the fuck up, you've wasted my time enough with this bullshit. You win.
		//A winner is you.
		//Conglaturations.
		//Fuck you.

		if(r.toString().equals("hbm:misc.nullChopper") && Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopper(new ResourceLocation("hbm:entity.chopperFlyingLoop"), ent, EnumHbmSound.soundChopperLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop).setVolume(10.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullCrashing") && Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundCrashing(new ResourceLocation("hbm:entity.chopperCrashingLoop"), ent, EnumHbmSound.soundCrashingLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop).setVolume(10.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullMine") && Library.getClosestMineForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityChopperMine ent = Library.getClosestMineForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopperMine(new ResourceLocation("hbm:entity.chopperMineLoop"), ent, EnumHbmSound.soundMineLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop).setVolume(10.0F);
			}
		}

		for(MovingSoundPlayerLoop sounds : MovingSoundPlayerLoop.globalSoundList)
		{
			if(!sounds.init || sounds.isDonePlaying()) {
				sounds.init = true;
				sounds.setDone(false);
				mc.getSoundHandler().playSound(sounds);
			}
		}
	}

	@SubscribeEvent
	public void drawTooltip(ItemTooltipEvent event) {

		ItemStack stack = event.itemStack;
		List<String> list = event.toolTip;

		/// DAMAGE RESISTANCE ///
		DamageResistanceHandler.addInfo(stack, list);

		/// HAZMAT INFO ///
		List<HazardClass> hazInfo = ArmorRegistry.hazardClasses.get(stack.getItem());

		if(hazInfo != null) {

			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("hazard.prot"));
				for(HazardClass clazz : hazInfo) {
					list.add(EnumChatFormatting.YELLOW + "  " + I18nUtil.resolveKey(clazz.lang));
				}
			} else {

				list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
						EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
						EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display protection info");
			}
		}

		/// CLADDING (LEGACY) ///
		double rad = HazmatRegistry.getResistance(stack);
		rad = ((int)(rad * 1000)) / 1000D;
		if(rad > 0) list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.radResistance", rad));

		/// ARMOR MODS ///
		if(stack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(stack)) {

			if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !(Minecraft.getMinecraft().currentScreen instanceof GUIArmorTable)) {

				list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
						EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
						EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display installed armor mods");

			} else {

				list.add(EnumChatFormatting.YELLOW + "Mods:");

				ItemStack[] mods = ArmorModHandler.pryMods(stack);

				for(int i = 0; i < 8; i++) {

					if(mods[i] != null && mods[i].getItem() instanceof ItemArmorMod) {

						((ItemArmorMod)mods[i].getItem()).addDesc(list, mods[i], stack);
					}
				}
			}
		}

		/// HAZARDS ///
		HazardSystem.addFullTooltip(stack, event.entityPlayer, list);

		if(event.showAdvancedItemTooltips && ClientConfig.ITEM_TOOLTIP_SHOW_OREDICT.get()) {
			List<String> names = ItemStackUtil.getOreDictNames(stack);

			if(names.size() > 0) {
				list.add(EnumChatFormatting.BLUE + "Ore Dict:");
				for(String s : names) {
					list.add(EnumChatFormatting.AQUA + " -" + s);
				}
			}
		}

		/// CUSTOM NUKE ///
		ComparableStack comp = new ComparableStack(stack).makeSingular();

		if(ClientConfig.ITEM_TOOLTIP_SHOW_CUSTOM_NUKE.get()) {
			CustomNukeEntry entry = TileEntityNukeCustom.entries.get(comp);

			if(entry != null) {

				if(!list.isEmpty())
					list.add("");

				if(entry.entry == EnumEntryType.ADD)
					list.add(EnumChatFormatting.GOLD + "Adds " + entry.value + " to the custom nuke stage " + entry.type);

				if(entry.entry == EnumEntryType.MULT)
					list.add(EnumChatFormatting.GOLD + "Adds multiplier " + entry.value + " to the custom nuke stage " + entry.type);
			}
		}

		try {
			CanneryBase cannery = Jars.canneries.get(comp);
			if(cannery != null) {
				list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("cannery.f1"));
				lastCannery = comp;
				canneryTimestamp = Clock.get_ms();
			}
		} catch(Exception ex) {
			list.add(EnumChatFormatting.RED + "Error loading cannery: " + ex.getLocalizedMessage());
		}

		try {
			QuickManualAndWiki qmaw = QMAWLoader.triggers.get(comp);
			if(qmaw != null) {
				list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("qmaw.tab"));
				lastQMAW = qmaw;
				qmawTimestamp = Clock.get_ms();
			}
		} catch(Exception ex) {
			list.add(EnumChatFormatting.RED + "Error loading cannery: " + ex.getLocalizedMessage());
		}

		/*ItemStack copy = stack.copy();
		List<MaterialStack> materials = Mats.getMaterialsFromItem(copy);

		if(!materials.isEmpty()) {
			for(MaterialStack mat : materials) {
				list.add(EnumChatFormatting.DARK_PURPLE + mat.material.names[0] + ": " + Mats.formatAmount(mat.amount * stack.stackSize));
			}
		}*/
	}

	private static long canneryTimestamp;
	private static ComparableStack lastCannery = null;
	private static long qmawTimestamp;
	private static QuickManualAndWiki lastQMAW = null;

	private ResourceLocation ashes = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_ash.png");

	@SideOnly(Side.CLIENT)
	//@SubscribeEvent
	public void onRenderStorm(RenderHandEvent event) {

		if(BlockAshes.ashes == 0)
			return;

		GL11.glPushMatrix();

		Minecraft mc = Minecraft.getMinecraft();

		GL11.glRotatef((float)-mc.thePlayer.rotationYaw, 0, 1, 0);
		GL11.glRotatef((float)(mc.thePlayer.rotationPitch), 1, 0, 0);

		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		int w = resolution.getScaledWidth();
		int h = resolution.getScaledHeight();
		double off = Clock.get_ms() / -10000D % 10000D;
		double aw = 25;

		Tessellator tessellator = Tessellator.instance;

		//int d = mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posY), MathHelper.floor_double(mc.thePlayer.posZ), 0);
		int cX = currentBrightness % 65536;
		int cY = currentBrightness / 65536;
		int lX = lastBrightness % 65536;
		int lY = lastBrightness / 65536;
		float interp = (mc.theWorld.getTotalWorldTime() % 20) * 0.05F;

		if(mc.theWorld.getTotalWorldTime() == 1)
			lastBrightness = currentBrightness;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(lX + (cX - lX) * interp) / 1.0F, (float)(lY + (cY - lY) * interp) / 1.0F);

		mc.entityRenderer.enableLightmap((double)event.partialTicks);

		mc.getTextureManager().bindTexture(ashes);

		for(int i = 1; i < 3; i++) {

			GL11.glRotatef(-15, 0, 0, 1);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, BlockAshes.ashes / 256F * 0.98F / i);

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-w * 0.25, 	h * 0.25, 	aw, 0.0D + off * i, 1.0D);
			tessellator.addVertexWithUV(w * 0.25, 	h * 0.25, 	aw, 1.0D + off * i, 1.0D);
			tessellator.addVertexWithUV(w * 0.25, 	-h * 0.25, 	aw, 1.0D + off * i, 0.0D);
			tessellator.addVertexWithUV(-w * 0.25, 	-h * 0.25, 	aw, 0.0D + off * i, 0.0D);
			tessellator.draw();
		}

		mc.entityRenderer.disableLightmap((double)event.partialTicks);

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPopMatrix();
	}

	public static int currentBrightness = 0;
	public static int lastBrightness = 0;

	static boolean isRenderingItems = false;

	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {

		Minecraft mc = Minecraft.getMinecraft();
		ArmorNo9.updateWorldHook(mc.theWorld);

		boolean supportsHighRenderDistance = FMLClientHandler.instance().hasOptifine() || Loader.isModLoaded("angelica");

		if(mc.gameSettings.renderDistanceChunks > 16 && GeneralConfig.enableRenderDistCheck && !supportsHighRenderDistance) {
			mc.gameSettings.renderDistanceChunks = 16;
			LoggingUtil.errorWithHighlight("========================== WARNING ==========================");
			LoggingUtil.errorWithHighlight("Dangerous render distance detected: Values over 16 only work on 1.8+ or with Optifine/Angelica installed!!");
			LoggingUtil.errorWithHighlight("Set '1.25_enableRenderDistCheck' in hbm.cfg to 'false' to disable this check.");
			LoggingUtil.errorWithHighlight("========================== WARNING ==========================");
			LoggingUtil.errorWithHighlight("If you got this error after removing Optifine/Angelica: Consider deleting your option files after removing mods.");
			LoggingUtil.errorWithHighlight("If you got this error after downgrading your Minecraft version: Consider using a launcher that doesn't reuse the same folders for every game instance. MultiMC for example, it's really good and it comes with a dedicated cat button. You like cats, right? Are you using the Microsoft launcher? The one launcher that turns every version switch into a tightrope act because all the old config and options files are still here because different instances all use the same folder structure instead of different folders like a competent launcher would, because some MO-RON thought that this was an acceptable way of doing things? Really? The launcher that circumcises every crashlog into indecipherable garbage, tricking oblivious people into posting that as a \"crash report\", effectively wasting everyone's time? The launcher made by the company that thought it would be HI-LA-RI-OUS to force everyone to use Microsoft accounts, effectively breaking every other launcher until they implement their terrible auth system?");
			LoggingUtil.errorWithHighlight("========================== WARNING ==========================");
		}

		if(mc.theWorld == null || mc.thePlayer == null)
			return;

		if(event.phase == Phase.START && event.side == Side.CLIENT) {

			if(BlockAshes.ashes > 256) BlockAshes.ashes = 256;
			if(BlockAshes.ashes > 0) BlockAshes.ashes -= 2;
			if(BlockAshes.ashes < 0) BlockAshes.ashes = 0;

			if(mc.theWorld.getTotalWorldTime() % 20 == 0) {
				this.lastBrightness = this.currentBrightness;
				currentBrightness = mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posY), MathHelper.floor_double(mc.thePlayer.posZ), 0);
			}

			if(ArmorUtil.isWearingEmptyMask(mc.thePlayer)) {
				MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "Your mask has no filter!", MainRegistry.proxy.ID_FILTER);
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_F1) && Minecraft.getMinecraft().currentScreen != null) {

			ComparableStack comp = canneryTimestamp > Clock.get_ms() - 100 ? lastCannery : null;

			if(comp == null) {
				ItemStack stack = getMouseOverStack();
				if(stack != null) comp = new ComparableStack(stack).makeSingular();
			}

			if(comp != null) {
				CanneryBase cannery = Jars.canneries.get(comp);
				if(cannery != null) {
					Minecraft.getMinecraft().thePlayer.closeScreen();
					FMLCommonHandler.instance().showGuiScreen(new GuiWorldInAJar(cannery.createScript(), cannery.getName(), cannery.getIcon(), cannery.seeAlso()));
				}
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_TAB) && Minecraft.getMinecraft().currentScreen != null) {

			QuickManualAndWiki qmaw = qmawTimestamp > Clock.get_ms() - 100 ? lastQMAW : null;

			if(qmaw != null) {
				Minecraft.getMinecraft().thePlayer.closeScreen();
				FMLCommonHandler.instance().showGuiScreen(new GuiQMAW(qmaw));
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {

			ItemStack stack = getMouseOverStack();
			if(stack != null) {
				stack = stack.copy();
				stack.stackSize = 1;
				Minecraft.getMinecraft().thePlayer.closeScreen();
				FMLCommonHandler.instance().showGuiScreen(new GUIScreenPreview(stack));
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_0) && Keyboard.isKeyDown(Keyboard.KEY_1)) {
			if (!isRenderingItems) {
				isRenderingItems = true;

				MainRegistry.logger.info("Taking a screenshot of ALL items, if you did this by mistake: fucking lmao get rekt nerd");

				List<Item> ignoredItems = Arrays.asList(
					ModItems.assembly_template,
					ModItems.crucible_template,
					ModItems.chemistry_template,
					ModItems.chemistry_icon,
					ModItems.achievement_icon,
					Items.spawn_egg,
					Item.getItemFromBlock(Blocks.mob_spawner)
				);

				List<Class<? extends Item>> collapsedClasses = Arrays.asList(
					ItemRBMKPellet.class,
					ItemDepletedFuel.class,
					ItemFluidDuct.class
				);
				
				String prefix = "Gun ";
				//int gunScale = 16;
				//int defaultScale = 1;
				int slotScale = 16;
				boolean ignoreNonNTM = true;
				boolean onlyGuns = true;

				List<ItemStack> stacks = new ArrayList<ItemStack>();
				for (Object reg : Item.itemRegistry) {
					Item item = (Item) reg;
					if(ignoreNonNTM && !Item.itemRegistry.getNameForObject(item).startsWith("hbm:")) continue;
					if(ignoredItems.contains(item)) continue;
					if(onlyGuns && !(item instanceof ItemGunBaseNT)) continue;
					if(collapsedClasses.contains(item.getClass())) {
						stacks.add(new ItemStack(item));
					} else {
						item.getSubItems(item, null, stacks);
					}
				}

				Minecraft.getMinecraft().thePlayer.closeScreen();
				FMLCommonHandler.instance().showGuiScreen(new GUIScreenWikiRender(stacks.toArray(new ItemStack[0]), prefix, "wiki-block-renders-256", slotScale));
			}
		} else {
			isRenderingItems = false;
		}

		EntityPlayer player = mc.thePlayer;

		if(event.phase == Phase.START) {

			float discriminator = 0.003F;
			float defaultStepSize = 0.5F;
			int newStepSize = 0;

			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof ArmorFSB) {
				ArmorFSB plate = (ArmorFSB) player.inventory.armorInventory[2].getItem();
				if(plate.hasFSBArmor(player)) newStepSize = plate.stepSize;
			}

			if(newStepSize > 0) {
				player.stepHeight = newStepSize + discriminator;
			} else {
				for(int i = 1; i < 4; i++) if(player.stepHeight == i + discriminator) player.stepHeight = defaultStepSize;
			}
		}

		if(event.phase == Phase.END) {

			if(ClientConfig.GUN_VISUAL_RECOIL.get()) {
				ItemGunBaseNT.offsetVertical += ItemGunBaseNT.recoilVertical;
				ItemGunBaseNT.offsetHorizontal += ItemGunBaseNT.recoilHorizontal;
				player.rotationPitch -= ItemGunBaseNT.recoilVertical;
				player.rotationYaw -= ItemGunBaseNT.recoilHorizontal;

				ItemGunBaseNT.recoilVertical *= ItemGunBaseNT.recoilDecay;
				ItemGunBaseNT.recoilHorizontal *= ItemGunBaseNT.recoilDecay;
				float dV = ItemGunBaseNT.offsetVertical * ItemGunBaseNT.recoilRebound;
				float dH = ItemGunBaseNT.offsetHorizontal * ItemGunBaseNT.recoilRebound;

				ItemGunBaseNT.offsetVertical -= dV;
				ItemGunBaseNT.offsetHorizontal -= dH;
				player.rotationPitch += dV;
				player.rotationYaw += dH;
			} else {
				ItemGunBaseNT.offsetVertical = 0;
				ItemGunBaseNT.offsetHorizontal = 0;
				ItemGunBaseNT.recoilVertical = 0;
				ItemGunBaseNT.recoilHorizontal = 0;
			}
		}
	}

	public static ItemStack getMouseOverStack() {

		Minecraft mc = Minecraft.getMinecraft();
		if(mc.currentScreen instanceof GuiContainer) {

			ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			int width = scaledresolution.getScaledWidth();
			int height = scaledresolution.getScaledHeight();
			int mouseX = Mouse.getX() * width / mc.displayWidth;
			int mouseY = height - Mouse.getY() * height / mc.displayHeight - 1;

			GuiContainer container = (GuiContainer) mc.currentScreen;

			for(Object o : container.inventorySlots.inventorySlots) {
				Slot slot = (Slot) o;

				if(slot.getHasStack()) {
					try {
						Method isMouseOverSlot = ReflectionHelper.findMethod(GuiContainer.class, container, new String[] {"func_146981_a", "isMouseOverSlot"}, Slot.class, int.class, int.class);

						if((boolean) isMouseOverSlot.invoke(container, slot, mouseX, mouseY)) {
							return slot.getStack();
						}

					} catch(Exception ex) { }
				}
			}
		}

		return null;
	}

	public static boolean renderLodeStar = false;
	public static long lastStarCheck = 0L;
	public static long lastLoadScreenReplacement = 0L;
	public static int loadingScreenReplacementRetry = 0;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onClientTickLast(ClientTickEvent event) {
		
		Minecraft mc = Minecraft.getMinecraft();
		long millis = Clock.get_ms();
		if(millis == 0) millis = System.currentTimeMillis();
		
		if(GeneralConfig.enableLoadScreenReplacement && loadingScreenReplacementRetry < 25 && !(mc.loadingScreen instanceof LoadingScreenRendererNT) && millis > lastLoadScreenReplacement + 5_000) {
			mc.loadingScreen = new LoadingScreenRendererNT(mc);
			lastLoadScreenReplacement = millis;
			loadingScreenReplacementRetry++; // this might not do anything, but at least it should prevent a metric fuckton of framebuffers from being created
		}

		if(event.phase == Phase.START && GeneralConfig.enableSkyboxes) {

			World world = mc.theWorld;
			if(world == null) return;

			IRenderHandler sky = world.provider.getSkyRenderer();

			if(world.provider instanceof WorldProviderSurface) {

				if(ImpactWorldHandler.getDustForClient(world) > 0 || ImpactWorldHandler.getFireForClient(world) > 0) {

					//using a chainloader isn't necessary since none of the sky effects should render anyway
					if(!(sky instanceof RenderNTMSkyboxImpact)) {
						world.provider.setSkyRenderer(new RenderNTMSkyboxImpact());
						return;
					}
				}
			}

			if(world.provider.dimensionId == 0) {

				if(!(sky instanceof RenderNTMSkyboxChainloader)) {
					world.provider.setSkyRenderer(new RenderNTMSkyboxChainloader(sky));
				}
			}

			EntityPlayer player = mc.thePlayer;

			if(lastStarCheck + 200 < millis) {
				renderLodeStar = false;
				lastStarCheck = millis;

				if(player != null) {
					Vec3NT pos = new Vec3NT(player.posX, player.posY, player.posZ);
					Vec3NT lodestarHeading = new Vec3NT(0, 0, -1D).rotateAroundXDeg(-15).multiply(25);
					Vec3NT nextPos = new Vec3NT(pos).add(lodestarHeading.xCoord,lodestarHeading.yCoord, lodestarHeading.zCoord);
					MovingObjectPosition mop = world.func_147447_a(pos, nextPos, false, true, false);
					if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == ModBlocks.glass_polarized) {
						renderLodeStar = true;
					}
				}
			}
		}

		// ???
		/*if(event.phase == Phase.START) {

			Minecraft mc = Minecraft.getMinecraft();

			if(mc.currentScreen != null && mc.currentScreen.allowUserInput) {
				HbmPlayerProps props = HbmPlayerProps.getData(MainRegistry.proxy.me());

				for(EnumKeybind key : EnumKeybind.values()) {
					boolean last = props.getKeyPressed(key);

					if(last) {
						PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, !last));
						props.setKeyPressed(key, !last);
					}
				}
			}
		}*/
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;

		int x = MathHelper.floor_double(player.posX);
		int y = MathHelper.floor_double(player.posY);
		int z = MathHelper.floor_double(player.posZ);
		Block b = player.worldObj.getBlock(x, y, z);

		// Support climbing freestanding vines and chains using spacebar
		if (
			b.isLadder(player.worldObj, x, y, z, player) &&
			b.getCollisionBoundingBoxFromPool(player.worldObj, x, y, z) == null &&
			!player.capabilities.isFlying &&
			GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) &&
			player.motionY < 0.15
		) {
			player.motionY = 0.15;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderWorldLastEvent(RenderWorldLastEvent event) {

		Clock.update();
		
		BlockRebar.renderRebar(Minecraft.getMinecraft().theWorld.loadedTileEntityList, event.partialTicks);

		GL11.glPushMatrix();

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		double dx = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
		double dy = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
		double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;

		int dist = 300;
		int x = 0;
		int y = 500;
		int z = 0;

		Vec3 vec = Vec3.createVectorHelper(x - dx, y - dy, z - dz);

		if(player.worldObj.provider.dimensionId == 0 && vec.lengthVector() < dist && !HTTPHandler.capsule.isEmpty()) {

			GL11.glTranslated(vec.xCoord, vec.yCoord, vec.zCoord);

			GL11.glPushMatrix();

			RenderHelper.enableStandardItemLighting();

			GL11.glRotated(80, 0, 0, 1);
			GL11.glRotated(30, 0, 1, 0);

			double sine = Math.sin(Clock.get_ms() * 0.0005) * 5;
			double sin3 = Math.sin(Clock.get_ms() * 0.0005 + Math.PI * 0.5) * 5;
			GL11.glRotated(sine, 0, 0, 1);
			GL11.glRotated(sin3, 1, 0, 0);

			GL11.glTranslated(0, -3, 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 6500F, 30F);
			SoyuzPronter.prontCapsule();

			GL11.glRotated(Clock.get_ms() * 0.025 % 360, 0, -1, 0);

			int rand = new Random(MainRegistry.startupTime).nextInt(HTTPHandler.capsule.size());
			String msg = HTTPHandler.capsule.get(rand);

			GL11.glTranslated(0, 3.75, 0);
			GL11.glRotated(180, 1, 0, 0);

			float rot = 0F;

			// looks dumb but we'll use this technology for the cyclotron
			for(char c : msg.toCharArray()) {

				GL11.glPushMatrix();

				GL11.glRotatef(rot, 0, 1, 0);

				float width = Minecraft.getMinecraft().fontRenderer.getStringWidth(msg);
				float scale = 5 / width;

				rot -= Minecraft.getMinecraft().fontRenderer.getCharWidth(c) * scale * 50;

				GL11.glTranslated(2, 0, 0);

				GL11.glRotatef(-90, 0, 1, 0);
				GL11.glScalef(scale, scale, scale);
				GL11.glDisable(GL11.GL_CULL_FACE);
				Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(c), 0, 0, 0xff00ff);
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glPopMatrix();
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			RenderHelper.disableStandardItemLighting();

			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();

		boolean hudOn = HbmPlayerProps.getData(player).enableHUD;

		if(hudOn) {
			RenderOverhead.renderMarkers(event.partialTicks);
			boolean thermalSights = false;

			if(ArmorFSB.hasFSBArmor(player)) {
				ItemStack plate = player.inventory.armorInventory[2];
				ArmorFSB chestplate = (ArmorFSB) plate.getItem();

				if(chestplate.thermal) thermalSights = true;
			}
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGunBaseNT && ItemGunBaseNT.aimingProgress == 1) {
				ItemGunBaseNT gun = (ItemGunBaseNT) player.getHeldItem().getItem();
				for(int i = 0; i < gun.getConfigCount(); i++) if(gun.getConfig(player.getHeldItem(), i).hasThermalSights(player.getHeldItem())) thermalSights = true;
			}

			if(thermalSights) RenderOverhead.renderThermalSight(event.partialTicks);
		}

		RenderOverhead.renderActionPreview(event.partialTicks);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void preRenderEventFirst(RenderLivingEvent.Pre event) {

		if(MainRegistry.proxy.isVanished(event.entity))
			event.setCanceled(true);
	}

	@SubscribeEvent
	public void preRenderEvent(RenderLivingEvent.Pre event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if(ArmorFSB.hasFSBArmor(player) && HbmPlayerProps.getData(player).enableHUD) {
			ItemStack plate = player.inventory.armorInventory[2];
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();

			if(chestplate.vats) {

				int count = (int)Math.min(event.entity.getMaxHealth(), 100);

				int bars = (int)Math.ceil(event.entity.getHealth() * count / event.entity.getMaxHealth());

				String bar = EnumChatFormatting.RED + "";

				for(int i = 0; i < count; i++) {

					if(i == bars)
						bar += EnumChatFormatting.RESET + "";

						bar += "|";
				}
				RenderOverhead.renderTag(event.entity, event.x, event.y, event.z, event.renderer, bar, chestplate.thermal);
			}
		}
	}

	public static IIcon particleBase;
	public static IIcon particleLeaf;
	public static IIcon particleSplash;
	public static IIcon particleAshes;

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre event) {

		if(event.map.getTextureType() == 0) {
			particleBase = event.map.registerIcon(RefStrings.MODID + ":particle/particle_base");
			particleLeaf = event.map.registerIcon(RefStrings.MODID + ":particle/dead_leaf");
			particleSplash = event.map.registerIcon(RefStrings.MODID + ":particle/particle_splash");
			particleAshes = event.map.registerIcon(RefStrings.MODID + ":particle/particle_ashes");
		}
	}

	@SubscribeEvent
	public void postTextureStitch(TextureStitchEvent.Post event) {
		CTStitchReceiver.receivers.forEach(x -> x.postStitch());
	}

	private static final ResourceLocation poster = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/poster.png");
	private static final ResourceLocation poster_cat = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/poster_cat.png");

	@SubscribeEvent
	public void renderFrame(RenderItemInFrameEvent event) {

		if(event.item != null && event.item.getItem() == ModItems.flame_pony) {
			event.setCanceled(true);

			double p = 0.0625D;
			double o = p * 2.75D;

			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().renderEngine.bindTexture(poster);
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.addVertexWithUV(0.5, 0.5 + o, p * 0.5, 1, 0);
			tess.addVertexWithUV(-0.5, 0.5 + o, p * 0.5, 0, 0);
			tess.addVertexWithUV(-0.5, -0.5 + o, p * 0.5, 0, 1);
			tess.addVertexWithUV(0.5, -0.5 + o, p * 0.5, 1, 1);
			tess.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
		}

		if(event.item != null && event.item.getItem() == Items.paper) {
			event.setCanceled(true);

			double p = 0.0625D;
			double o = p * 2.75D;

			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().renderEngine.bindTexture(poster_cat);
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.addVertexWithUV(0.5, 0.5 + o, p * 0.5, 1, 0);
			tess.addVertexWithUV(-0.5, 0.5 + o, p * 0.5, 0, 0);
			tess.addVertexWithUV(-0.5, -0.5 + o, p * 0.5, 0, 1);
			tess.addVertexWithUV(0.5, -0.5 + o, p * 0.5, 1, 1);
			tess.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}

	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if(player != null && player.ridingEntity instanceof EntityRailCarRidable && player instanceof EntityClientPlayerMP) {
			EntityRailCarRidable train = (EntityRailCarRidable) player.ridingEntity;
			EntityClientPlayerMP client = (EntityClientPlayerMP) player;

			//mojank compensation, because apparently the "this makes the render work" method also determines the fucking input
			if(!train.shouldRiderSit()) {
				client.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(client.rotationYaw, client.rotationPitch, client.onGround));
				client.sendQueue.addToSendQueue(new C0CPacketInput(client.moveStrafing, client.moveForward, client.movementInput.jump, client.movementInput.sneak));
			}
		}

		if(event.phase == event.phase.END) {
			ItemCustomLore.updateSystem();
		}
	}

	@SubscribeEvent
	public void onOpenGUI(GuiOpenEvent event) {

		if(event.gui instanceof GuiMainMenu && ClientConfig.MAIN_MENU_WACKY_SPLASHES.get()) {
			GuiMainMenu main = (GuiMainMenu) event.gui;
			int rand = (int)(Math.random() * 150);

			switch(rand) {
			case 0: main.splashText = "Floppenheimer!"; break;
			case 1: main.splashText = "i should dip my balls in sulfuric acid"; break;
			case 2: main.splashText = "All answers are popbob!"; break;
			case 3: main.splashText = "None may enter The Orb!"; break;
			case 4: main.splashText = "Wacarb was here"; break;
			case 5: main.splashText = "SpongeBoy me Bob I am overdosing on keramine agagagagaga"; break;
			case 6: main.splashText = EnumChatFormatting.RED + "I know where you live, " + System.getProperty("user.name"); break;
			case 7: main.splashText = "Nice toes, now hand them over."; break;
			case 8: main.splashText = "I smell burnt toast!"; break;
			case 9: main.splashText = "There are bugs under your skin!"; break;
			case 10: main.splashText = "Fentanyl!"; break;
			case 11: main.splashText = "Do drugs!"; break;
			case 12: main.splashText = "Imagine being scared by splash texts!"; break;
			}

			double d = Math.random();
			if(d < 0.1) main.splashText = "Redditors aren't people!";
			else if(d < 0.2) main.splashText = "Can someone tell me what corrosive fumes the people on Reddit are huffing so I can avoid those more effectively?";
		}
	}
}
