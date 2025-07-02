package com.hbm.blocks.generic;

import java.util.function.Consumer;
import java.util.function.Function;

import com.hbm.blocks.generic.BlockSkeletonHolder.TileEntitySkeletonHolder;
import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.ModItems;
import com.hbm.util.BufferUtil;
import com.hbm.world.gen.util.DungeonSpawnerActions;
import com.hbm.world.gen.util.DungeonSpawnerConditions;
import com.hbm.util.Vec3NT;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DungeonSpawner extends BlockContainer {

	public DungeonSpawner() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDungeonSpawner();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityDungeonSpawner){
			TileEntityDungeonSpawner spawner = (TileEntityDungeonSpawner) tile;
			if(spawner.disguise != null){
				return spawner.disguise.getIcon(side, spawner.disguiseMeta);
			}
		}

		return super.getIcon(world, x, y, z, side);
	}



	public static class TileEntityDungeonSpawner extends TileEntity {

		public int phase = 0;
		public int timer = 0;

		public Block disguise;
		public int disguiseMeta;

		public String conditionID = "ABERRATOR";
		//actions always get called before conditions, use the phase timer in order to control behavior via condition
		public String actionID = "ABERRATOR";

		public Function<TileEntityDungeonSpawner, Boolean> condition;
		public Consumer<TileEntityDungeonSpawner> action;

		public ForgeDirection direction = ForgeDirection.UNKNOWN;
		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {
				if(action == null){
					action = DungeonSpawnerActions.actions.get(actionID);
				}
				if(condition == null){
					condition = DungeonSpawnerConditions.conditions.get(conditionID);
				}
				if(action == null || condition == null){
					worldObj.setBlock(xCoord,yCoord,zCoord, Blocks.air);
					return;
				}
				action.accept(this);
				if(condition.apply(this)) {
					phase++;
					timer = 0;
				} else {
					timer++;
				}
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("phase", phase);
			nbt.setString("conditionID", conditionID);
			nbt.setString("actionID", actionID);
			nbt.setInteger("direction", direction.ordinal());
			if(disguise != null){
				nbt.setInteger("disguiseMeta", disguiseMeta);
				nbt.setString("disguise", GameRegistry.findUniqueIdentifierFor(disguise).toString());
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.phase = nbt.getInteger("phase");
			this.conditionID = nbt.getString("conditionID");
			this.direction = ForgeDirection.getOrientation(nbt.getInteger("direction"));
			if(nbt.hasKey("disguise")){
				disguiseMeta = nbt.getInteger("disguiseMeta");
				disguise = Block.getBlockFromName(nbt.getString("disguise"));
			}
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}

		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}
	}

}
