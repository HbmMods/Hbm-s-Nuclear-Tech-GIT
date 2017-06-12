package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPylonRedWire extends TileEntity implements IConductor {
	
	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();
	public List<TileEntityPylonRedWire> connected = new ArrayList<TileEntityPylonRedWire>();
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		int[] conX = nbt.getIntArray("conX");
		int[] conY = nbt.getIntArray("conY");
		int[] conZ = nbt.getIntArray("conZ");
		if(conX.length == conY.length && conY.length == conZ.length)
		{
			int[][] con = new int[conX.length][3];
			
			for(int i = 0; i < conX.length; i++) {
				con[i][0] = conX[i];
				con[i][1] = conY[i];
				con[i][2] = conZ[i];
			}
			
			this.connected = this.convertArrayToList(con);
		} else {
            throw new RuntimeException(this.getClass() + " failed to read connected electricity poles from NBT!");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		int[] conX = new int[this.connected.size()];
		int[] conY = new int[this.connected.size()];
		int[] conZ = new int[this.connected.size()];
		
		for(int i = 0; i < this.connected.size(); i++) {
			conX[i] = this.connected.get(i).xCoord;
			conY[i] = this.connected.get(i).yCoord;
			conZ[i] = this.connected.get(i).zCoord;
		}
		
		nbt.setIntArray("conX", conX);
		nbt.setIntArray("conY", conY);
		nbt.setIntArray("conZ", conZ);
	}
	
	public List<TileEntityPylonRedWire> convertArrayToList(int[][] array) {
		
		List<TileEntityPylonRedWire> list = new ArrayList<TileEntityPylonRedWire>();
		
		for(int i = 0; i < array.length; i++) {
			TileEntity te = worldObj.getTileEntity(array[i][0], array[i][1], array[i][2]);
			if(te != null && te instanceof TileEntityPylonRedWire)
				list.add((TileEntityPylonRedWire)te);
		}
		
		return list;
	}
	
	public int[][] convertListToArray(List<TileEntityPylonRedWire> list) {
		
		int[][] array = new int[list.size()][3];
		
		for(int i = 0; i < list.size(); i++) {
			TileEntity te = list.get(i);
			array[i][0] = te.xCoord;
			array[i][1] = te.yCoord;
			array[i][2] = te.zCoord;
		}
		
		return array;
	}
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}*/
	
	/*Princess cards she sends me with her regards,
	Oh, bar-room eyes shine vacancy
	To see her you gotta look hard
	Wounded deep in battle, I stand stuffed like some soldier undaunted
	To her cheshire smile I'll stand on file
	She's all I ever wanted
	You let your blue walls stand in the way of these facts, honey
	Get your carpet baggers off my back
	Girl give me time to cover my tracks
	You said, "Here's your mirror and your ball and jacks"
	But they're not what I came for
	Oh I came for so much more
	And I know you that too
	And I know you know that's true
	
	I came for you
	I came for you
	I came for you
	For you
	I came for you
	
	Crawl into my ambulance
	Your pulse is getting weak
	Reveal yourself all to me now
	While you've got the strength to speak
	'Cause they're waiting for you at Bellevue
	With their oxygen masks
	But I could give it all to you now
	If only you could ask
	Don't call for your surgeon
	Even he says it's late
	It's not your lungs this time
	But your heart holds your fate
	Don't give me my money back
	Don't want it anymore
	It's not that nursery mouth I came back for
	It's not the way you're stretched out on the floor
	I've broken all your windows
	And I've rammed through all your doors
	Who am I to ask you to fight my wars
	And you should know that's true
	You should know that too
	
	I came for you
	I came for you
	I came for you
	For you
	I came for you
	
	Don't call for your surgeon
	Even he says it's late
	It's not your lungs this time
	But your heart holds your fate
	Don't give me my money back
	Don't want it anymore
	It's not that nursery mouth I came back for
	It's not the way you're stretched out on the floor
	I've broken all your windows
	And I've rammed through all your doors
	Who am I to ask you to fight my wars
	You should know that's true
	You should know that too
	
	I came for you
	I came for you
	I came for you
	For you
	I came for you*/

}
