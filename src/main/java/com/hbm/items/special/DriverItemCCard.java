package com.hbm.items.special;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.item.Container;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.item.ItemStack;

public class DriverItemCCard {
    public String slot(ItemStack stack) {
        return Slot.Card;
    }

    public ManagedEnvironment createEnvironment(ItemStack stack, Container container) {
        return new Environment(container);
    }

    public class Environment extends li.cil.oc.api.prefab.ManagedEnvironment {
        protected final Container container;

        public Environment(Container container) {
            this.container = container;
            Node node = Network.newNode(this, Visibility.Neighbors).
                    withComponent("particle").
                    create();
        }
    }
    @Callback(direct = true, limit = 16)
    public Object[] Greet(Context context, Arguments args) {

        return new Object[] {null};
    }

}
