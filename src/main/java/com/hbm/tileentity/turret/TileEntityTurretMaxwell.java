package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.gui.GUITurretMaxwell;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretMaxwell extends TileEntityTurretBaseNT implements IUpgradeInfoProvider {

	@Override
	public String getName() {
		return "container.turretMaxwell";
	}

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {

		if(ammoStacks != null)
			return ammoStacks;

		ammoStacks = new ArrayList();

		ammoStacks.add(new ItemStack(ModItems.upgrade_speed_1));
		ammoStacks.add(new ItemStack(ModItems.upgrade_speed_2));
		ammoStacks.add(new ItemStack(ModItems.upgrade_speed_3));
		ammoStacks.add(new ItemStack(ModItems.upgrade_effect_1));
		ammoStacks.add(new ItemStack(ModItems.upgrade_effect_2));
		ammoStacks.add(new ItemStack(ModItems.upgrade_effect_3));
		ammoStacks.add(new ItemStack(ModItems.upgrade_power_1));
		ammoStacks.add(new ItemStack(ModItems.upgrade_power_2));
		ammoStacks.add(new ItemStack(ModItems.upgrade_power_3));
		ammoStacks.add(new ItemStack(ModItems.upgrade_afterburn_1));
		ammoStacks.add(new ItemStack(ModItems.upgrade_afterburn_2));
		ammoStacks.add(new ItemStack(ModItems.upgrade_afterburn_3));
		ammoStacks.add(new ItemStack(ModItems.upgrade_overdrive_1));
		ammoStacks.add(new ItemStack(ModItems.upgrade_overdrive_2));
		ammoStacks.add(new ItemStack(ModItems.upgrade_overdrive_3));
		ammoStacks.add(new ItemStack(ModItems.upgrade_5g));
		ammoStacks.add(new ItemStack(ModItems.upgrade_screm));

		return ammoStacks;
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.EFFECT || type == UpgradeType.POWER || type == UpgradeType.AFTERBURN || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.turret_maxwell));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + "Damage +0." + (level * 25) + "/t");
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 3) + "%"));
		}
		if(type == UpgradeType.EFFECT) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_RANGE, "+" + (level * 3) + "m"));
		}
		if(type == UpgradeType.AFTERBURN) {
			info.add(EnumChatFormatting.GREEN + "Afterburn +3s");
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 27);
		upgrades.put(UpgradeType.POWER, 27);
		upgrades.put(UpgradeType.EFFECT, 27);
		upgrades.put(UpgradeType.AFTERBURN, 27);
		upgrades.put(UpgradeType.OVERDRIVE, 27);
		return upgrades;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 2;
	}

	@Override
	public double getDecetorGrace() {
		return 5D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 9D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 6D;
	}

	@Override
	public double getTurretElevation() {
		return 40D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public double getDecetorRange() {
		return 64D + this.greenLevel * 3;
	}

	@Override
	public long getMaxPower() {
		return 10000000;
	}

	@Override
	public long getConsumption() {
		return _5g ? 10 : 10000 - this.blueLevel * 300;
	}

	@Override
	public double getBarrelLength() {
		return 2.125D;
	}

	@Override
	public double getHeightOffset() {
		return 2D;
	}

	public int beam;
	public double lastDist;

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {

			if(this.tPos != null) {
				Vec3 pos = this.getTurretPos();
				double length = Vec3.createVectorHelper(tPos.xCoord - pos.xCoord, tPos.yCoord - pos.yCoord, tPos.zCoord - pos.zCoord).lengthVector();
				this.lastDist = length;
			}

			if(beam > 0)
				beam--;
		} else {

			if(checkDelay <= 0) {
				checkDelay = 20;

				this.redLevel = 0;
				this.greenLevel = 0;
				this.blueLevel = 0;
				this.blackLevel = 0;
				this.pinkLevel = 0;
				this._5g = false;
				this.screm = false;

				for(int i = 1; i < 10; i++) {
					if(slots[i] != null) {
						Item item = slots[i].getItem();

						if(item == ModItems.upgrade_speed_1) redLevel += 1;
						if(item == ModItems.upgrade_speed_2) redLevel += 2;
						if(item == ModItems.upgrade_speed_3) redLevel += 3;
						if(item == ModItems.upgrade_effect_1) greenLevel += 1;
						if(item == ModItems.upgrade_effect_2) greenLevel += 2;
						if(item == ModItems.upgrade_effect_3) greenLevel += 3;
						if(item == ModItems.upgrade_power_1) blueLevel += 1;
						if(item == ModItems.upgrade_power_2) blueLevel += 2;
						if(item == ModItems.upgrade_power_3) blueLevel += 3;
						if(item == ModItems.upgrade_afterburn_1) pinkLevel += 1;
						if(item == ModItems.upgrade_afterburn_2) pinkLevel += 2;
						if(item == ModItems.upgrade_afterburn_3) pinkLevel += 3;
						if(item == ModItems.upgrade_overdrive_1) blackLevel += 1;
						if(item == ModItems.upgrade_overdrive_2) blackLevel += 2;
						if(item == ModItems.upgrade_overdrive_3) blackLevel += 3;
						if(item == ModItems.upgrade_5g) _5g = true;
						if(item == ModItems.upgrade_screm) screm = true;
					}
				}
			}

			checkDelay--;
		}

		super.updateEntity();
	}

	int redLevel;
	int greenLevel;
	int blueLevel;
	int blackLevel;
	int pinkLevel;
	boolean _5g;
	boolean screm;

	int checkDelay;

	@Override
	public void updateFiringTick() {

		long demand = this.getConsumption() * 10;

		if(this.target != null && this.getPower() >= demand) {

			if(_5g && target instanceof EntityPlayer) {
				EntityPlayer living = (EntityPlayer) target;
				living.addPotionEffect(new PotionEffect(HbmPotion.death.id, 30 * 60 * 20, 0, true));
			} else {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.microwave, (this.blackLevel * 10 + this.redLevel + 1F) * 0.25F);
			}

			if(pinkLevel > 0)
				this.target.setFire(this.pinkLevel * 3);

			if(!this.target.isEntityAlive() && this.target instanceof EntityLivingBase) {
				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "giblets");
				vdat.setInteger("ent", this.target.getEntityId());
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(vdat, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ), new TargetPoint(this.target.dimension, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ, 150));

				if(this.screm)
					worldObj.playSoundEffect(this.target.posX, this.target.posY, this.target.posZ, "hbm:block.screm", 20.0F, 1.0F);
				else
					worldObj.playSoundEffect(this.target.posX, this.target.posY, this.target.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
			}

			this.power -= demand;

			this.shot = true;
			this.networkPackNT(250);
			this.shot = false;
		}
	}

	private boolean shot = false;

	@Override
	public void serialize(ByteBuf buf) {
		if (this.shot)
			buf.writeBoolean(true);
		else {
			buf.writeBoolean(false);
			super.serialize(buf);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		boolean shot = buf.readBoolean();
		if(shot)
			this.beam = 5;
		else
			super.deserialize(buf);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretMaxwell(player.inventory, this);
	}
}
