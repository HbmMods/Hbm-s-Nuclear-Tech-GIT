package com.hbm.tileentity;

import java.util.List;

import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;

public interface IUpgradeInfoProvider {

	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo);
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo);
}
