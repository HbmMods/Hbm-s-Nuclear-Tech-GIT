package com.hbm.handler.ability;

import com.hbm.util.i18n.I18nUtil;

public interface IBaseAbility extends Comparable<IBaseAbility> {
	public String getName();

	public default String getExtension(int level) {
		return "";
	}

	// Note: only usable client-side. Server-side, use ChatComponentTranslation manually instead
	public default String getFullName(int level) {
		return I18nUtil.format(getName()) + getExtension(level);
	}

	public default boolean isAllowed() {
		return true;
	}

	// 1 means no support for levels (i.e. the level is always 0).
	// The UI only supports levels() between 1 and 10 (inclusive).
	// All calls accepting an `int level` parameters must be done
	// with a level between 0 and levels()-1 (inclusive).
	default int levels() {
		return 1;
	}

	default int sortOrder() {
		return hashCode();
	}

	@Override
	default int compareTo(IBaseAbility o) {
		return sortOrder() - o.sortOrder();
	}
}
