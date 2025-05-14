package com.hbm.handler.ability;

import net.minecraft.client.resources.I18n;

public interface IBaseAbility {
    String getName();

	default String getExtension(int level) {
        return "";
    }

	default String getFullName(int level) {
        return I18n.format(getName()) + getExtension(level);
    }

    default boolean isAllowed() {
        return true;
    }

    // 1 means no support for levels (i.e. the level is always 0).
    // The UI only supports levels() between 1 and 10 (inclusive).
    // All calls accepting an `int level` parameters must be done
    // with a level between 0 and levels()-1 (inclusive).
    default int levels() {
        return 1;
    }
}
