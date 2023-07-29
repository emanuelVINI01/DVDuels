package com.emanuelvini.dvduels.models;

import lombok.Builder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Builder(builderMethodName = "create")
public class Kit {

    private List<ItemStack> armorContents;

    private List<ItemStack> inventoryContents;

}
