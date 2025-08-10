package com.hbm.blocks.generic;

import api.hbm.block.IToolable;
import com.hbm.blocks.IBlockSideRotation;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.StructureConfig;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.ICopiable;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.world.gen.nbt.INBTTileEntityTransformable;
import com.hbm.world.gen.util.LogicBlockActions;
import com.hbm.world.gen.util.LogicBlockConditions;
import com.hbm.world.gen.util.LogicBlockInteractions;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockWandLogic extends BlockContainer implements ILookOverlay, IToolable, ITooltipProvider, IBlockSideRotation, IBomb {

	@SideOnly(Side.CLIENT) protected IIcon iconTop;

	public BlockWandLogic() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":wand_logic");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":wand_logic_top");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return (side <= 1) ? iconTop : blockIcon;
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		if(side == 0) return IBlockSideRotation.topToBottom(world.getBlockMetadata(x, y, z));
		if(side == 1) return world.getBlockMetadata(x, y, z);
		return 0;
	}

	@Override
	public int getRenderType() {
		return IBlockSideRotation.getRenderType();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if (i == 1) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if (i == 2) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		if (i == 3) world.setBlockMetadataWithNotify(x, y, z, 1, 2);

		ForgeDirection dir = ForgeDirection.UNKNOWN;
		switch(i){
			case 0: dir = ForgeDirection.SOUTH;break;
			case 1: dir = ForgeDirection.WEST; break;
			case 2: dir = ForgeDirection.NORTH;break;
			case 3: dir = ForgeDirection.EAST; break;
		}
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityWandLogic)
			((TileEntityWandLogic)te).placedRotation = dir.ordinal();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {

		ItemStack stack = player.getHeldItem();

		if (stack != null && stack.getItem() instanceof ItemBlock && !player.isSneaking()) {
			ItemBlock ib = (ItemBlock) stack.getItem();
			Block block = ib.field_150939_a;

			if (block.renderAsNormalBlock() && block != this) {

				TileEntity tile = world.getTileEntity(x, y, z);

				if(tile instanceof TileEntityWandLogic){
					TileEntityWandLogic logic = (TileEntityWandLogic) tile;
					logic.disguise = block;
					logic.disguiseMeta = stack.getItemDamage() & 15;
					return true;
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, fX, fY, fZ);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandLogic)) return false;

		TileEntityWandLogic logic = (TileEntityWandLogic) te;

		switch(tool) {
			case SCREWDRIVER:
				List<String> actionNames = LogicBlockActions.getActionNames();
				int indexA = actionNames.indexOf(logic.actionID);

				indexA += player.isSneaking() ? -1 : 1;
				indexA = MathHelper.clamp_int(indexA, 0, actionNames.size() - 1);

				logic.actionID = actionNames.get(indexA);
				return true;
			case DEFUSER:
				List<String> conditionNames = LogicBlockConditions.getConditionNames();
				int indexC = conditionNames.indexOf(logic.conditionID);

				indexC += player.isSneaking() ? -1 : 1;
				indexC = MathHelper.clamp_int(indexC, 0, conditionNames.size() - 1);

				logic.conditionID = conditionNames.get(indexC);

				return true;
			case HAND_DRILL:
				List<String> interactionNames = LogicBlockInteractions.getInteractionNames();
				int indexI = interactionNames.indexOf(logic.interactionID);

				indexI += player.isSneaking() ? -1 : 1;
				indexI = MathHelper.clamp_int(indexI, 0, interactionNames.size() - 1);

				logic.interactionID = interactionNames.get(indexI);

				return true;

			default: return false;
		}
	}

	@Override
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandLogic)) return;

		TileEntityWandLogic logic = (TileEntityWandLogic) te;

		List<String> text = new ArrayList<>();
		text.add("Action: " + logic.actionID);
		text.add("Condition: " + logic.conditionID);
		text.add("Interaction: " + (logic.interactionID != null ? logic.interactionID : "None"));

		String block;

		if(logic.disguise != null && logic.disguise != Blocks.air)
			block = I18nUtil.resolveKey(logic.disguise.getUnlocalizedName() + ".name");
		else
			block = "None";

		text.add("Disguise Block: " + block);

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Use screwdriver to cycle forwards through the action list, shift click to go back");
		list.add(EnumChatFormatting.GOLD + "Use defuser to cycle forwards through the condition list, shift click to go back");
		list.add(EnumChatFormatting.GOLD + "Use hand drill to cycle forwards through the interaction list, shift click to go back");
		list.add(EnumChatFormatting.YELLOW + "Use a detonator to transform");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWandLogic();
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandLogic)) return null;

		((TileEntityWandLogic) te).triggerReplace = true;

		return BombReturnCode.TRIGGERED;
	}

	public static class TileEntityWandLogic extends TileEntityLoadedBase implements INBTTileEntityTransformable, ICopiable {
		private boolean triggerReplace;

		public int placedRotation;

		Block disguise;
		int disguiseMeta = -1;

		public String actionID = "FODDER_WAVE";
		public String conditionID = "PLAYER_CUBE_5";
		public String interactionID;

		@Override
		public void updateEntity() {
			if(!worldObj.isRemote) {
				if(triggerReplace) {
					// On the first tick of this TE, replace with intended block and fill with loot
					replace();
				} else {
					networkPackNT(15);
				}
			}
		}

		private void replace() {
			if (!(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockWandLogic)) {
				MainRegistry.logger.warn("Somehow the block at: " + xCoord + ", " + yCoord + ", " + zCoord + " isn't a logic block but we're doing a TE update as if it is, cancelling!");
				return;
			}
			worldObj.setBlock(xCoord,yCoord,zCoord, ModBlocks.logic_block);

			TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);

			if(te == null || te instanceof BlockWandLoot.TileEntityWandLoot) {
				MainRegistry.logger.warn("TE for logic block set incorrectly at: " + xCoord + ", " + yCoord + ", " + zCoord + ". If you're using some sort of world generation mod, report it to the author!");
				te = ModBlocks.wand_logic.createTileEntity(worldObj, 0);
				worldObj.setTileEntity(xCoord, yCoord, zCoord, te);
			}

			if(te instanceof LogicBlock.TileEntityLogicBlock){
				LogicBlock.TileEntityLogicBlock logic = (LogicBlock.TileEntityLogicBlock)	te;
				logic.actionID = actionID;
				logic.conditionID = conditionID;
				logic.interactionID = interactionID;
				logic.direction = ForgeDirection.getOrientation(placedRotation);
				logic.disguise = disguise;
				logic.disguiseMeta = disguiseMeta;
			}

		}

		@Override
		public void transformTE(World world, int coordBaseMode) {
			triggerReplace = !StructureConfig.debugStructures;
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setString("actionID", actionID);
			nbt.setString("conditionID", conditionID);
			if(interactionID != null)
				nbt.setString("interactionID", interactionID);
			nbt.setInteger("rotation", placedRotation);
			if(disguise != null){
				nbt.setString("disguise", GameRegistry.findUniqueIdentifierFor(disguise).toString());
				nbt.setInteger("disguiseMeta", disguiseMeta);
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			actionID = nbt.getString("actionID");
			conditionID = nbt.getString("conditionID");
			if(nbt.hasKey("interactionID"))
				interactionID = nbt.getString("interactionID");
			placedRotation = nbt.getInteger("rotation");
			if(nbt.hasKey("disguise")){
				disguise = Block.getBlockFromName(nbt.getString("disguise"));
				disguiseMeta = nbt.getInteger("disguiseMeta");
			}
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeInt(placedRotation);
			BufferUtil.writeString(buf, actionID);
			BufferUtil.writeString(buf, conditionID);
			BufferUtil.writeString(buf, interactionID);
			buf.writeInt(Block.getIdFromBlock(disguise));
			buf.writeInt(disguiseMeta);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			placedRotation = buf.readInt();
			actionID = BufferUtil.readString(buf);
			conditionID = BufferUtil.readString(buf);
			interactionID = BufferUtil.readString(buf);
			disguise = Block.getBlockById(buf.readInt());
			disguiseMeta = buf.readInt();
		}

		@Override
		public NBTTagCompound getSettings(World world, int x, int y, int z) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("actionID", actionID);
			nbt.setString("conditionID", conditionID);
			if(interactionID != null)
				nbt.setString("interactionID", interactionID);
			if(disguise != null){
				nbt.setString("disguise", GameRegistry.findUniqueIdentifierFor(disguise).toString());
				nbt.setInteger("disguiseMeta", disguiseMeta);
			}

			return nbt;
		}

		@Override
		public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
			actionID = nbt.getString("actionID");
			conditionID = nbt.getString("conditionID");
			interactionID = nbt.getString("interactionID");
			if(nbt.hasKey("disguise")){
				disguise = Block.getBlockFromName(nbt.getString("disguise"));
				disguiseMeta = nbt.getInteger("disguiseMeta");
			}
		}
	}
}
