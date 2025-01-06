package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemColtanCompass extends Item {

	public int lastX = 0;
	public int lastZ = 0;
	public long lease = 0;

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("Points towards the coltan deposit.");
		list.add("The deposit is a large area where coltan ore spawns like standard ore,");
		list.add("it's not one large blob of ore on that exact location, dipshit.");
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean inhand) {
		
		if(world.isRemote) {
			if(stack.hasTagCompound()) {
				lastX = stack.stackTagCompound.getInteger("colX");
				lastZ = stack.stackTagCompound.getInteger("colZ");
				lease = System.currentTimeMillis() + 1000;
				
				Vec3 vec = Vec3.createVectorHelper(entity.posX - lastX, 0, entity.posZ - lastZ);
				MainRegistry.proxy.displayTooltip(((int) vec.lengthVector()) + "m", MainRegistry.proxy.ID_COMPASS);
			}
			
			if(ItemColtanCompass.this.lease < System.currentTimeMillis()) {
				lastX = 0;
				lastZ = 0;
			}
			
		} else {
			if(!stack.hasTagCompound()) {
				stack.stackTagCompound = new NBTTagCompound();

				Random colRand = new Random(world.getSeed() + 5);
				int colX = (int) (colRand.nextGaussian() * 1500);
				int colZ = (int) (colRand.nextGaussian() * 1500);

				stack.stackTagCompound.setInteger("colX", colX);
				stack.stackTagCompound.setInteger("colZ", colZ);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		if(register instanceof TextureMap) {
			TextureMap map = (TextureMap) register;
			TextureColtass cumpiss = new TextureColtass(this.getIconString());
			map.setTextureEntry(this.getIconString(), cumpiss);
			this.itemIcon = cumpiss; //apparently i was quite pissed when i wrote this
		} else {
			this.itemIcon = register.registerIcon(this.getIconString());
		}
	}

	@SideOnly(Side.CLIENT)
	public class TextureColtass extends TextureCompass {

		public TextureColtass(String texture) {
			super(texture);
		}

		public void updateAnimation() {
			Minecraft minecraft = Minecraft.getMinecraft();

			if(minecraft.theWorld != null && minecraft.thePlayer != null && !(ItemColtanCompass.this.lastX == 0 && ItemColtanCompass.this.lastZ == 0)) {
				this.updateCompass(minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, (double) minecraft.thePlayer.rotationYaw, false, false);
			} else {
				this.updateCompass((World) null, 0.0D, 0.0D, 0.0D, true, false);
			}
		}

		public void updateCompass(World world, double x, double z, double yaw, boolean ignoreDestination, boolean instantSnap) {

			if(!this.framesTextureData.isEmpty()) {
				double angle = 0.0D;

				if(world != null && !ignoreDestination && world.provider.isSurfaceWorld() && ItemColtanCompass.this.lease > System.currentTimeMillis()) {

					double d4 = (double) ItemColtanCompass.this.lastX - x;
					double d5 = (double) ItemColtanCompass.this.lastZ - z;
					yaw %= 360.0D;
					angle = -((yaw - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));
					
				} else {
					angle = Math.random() * Math.PI * 2.0D;
				}

				if(instantSnap) {
					this.currentAngle = angle;

				} else {
					double d6;

					for(d6 = angle - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D)) {
						;
					}

					while(d6 >= Math.PI) {
						d6 -= (Math.PI * 2D);
					}

					if(d6 < -1.0D) {
						d6 = -1.0D;
					}

					if(d6 > 1.0D) {
						d6 = 1.0D;
					}

					this.angleDelta += d6 * 0.1D;
					this.angleDelta *= 0.8D;
					this.currentAngle += this.angleDelta;
				}

				int i;

				for(i = (int) ((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double) this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {
					;
				}

				if(i != this.frameCounter) {
					this.frameCounter = i;
					TextureUtil.uploadTextureMipmap((int[][]) this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
				}
			}
		}
	}
}
