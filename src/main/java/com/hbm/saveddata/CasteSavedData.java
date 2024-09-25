package com.hbm.saveddata;

import com.hbm.saveddata.caste.Caste;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


public class CasteSavedData extends WorldSavedData {

	public final List<Caste> castes = new ArrayList<>();

	public CasteSavedData(String name) {
		super(name);
	}

	public CasteSavedData() {
		super("castes");
		this.markDirty();
	}

	public Caste createCaste(String name) {
		Caste newCaste = new Caste(name);
		this.castes.add(newCaste);
		this.markDirty();
		return newCaste;
	}

	public void deleteCaste(UUID casteId) {
		this.castes.removeIf(caste -> caste.getId().equals(casteId));
		this.markDirty();
	}

	public List<Caste> getCastes() {
		return this.castes;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int casteCount = nbt.getInteger("casteCount");

		for(int i = 0; i < casteCount; i++) {
			Caste caste = new Caste();
			caste.readFromNBT((NBTTagCompound) nbt.getTag("caste_data_" + i));

			castes.add(caste);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("casteCount", castes.size());

		int i = 0;

		for(Caste caste : castes) {
			NBTTagCompound data = new NBTTagCompound();
			caste.writeToNBT(data);

			nbt.setTag("caste_data_" + i, data);
			i++;
		}
	}

	public static CasteSavedData getData(World worldObj) {
		CasteSavedData data = (CasteSavedData)worldObj.perWorldStorage.loadData(CasteSavedData.class, "castes");
		if(data == null) {
			worldObj.perWorldStorage.setData("castes", new CasteSavedData());

			data = (CasteSavedData)worldObj.perWorldStorage.loadData(CasteSavedData.class, "castes");
		}

		return data;
	}
}
