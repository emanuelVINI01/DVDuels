package com.emanuelvini.dvduels.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Builder(builderMethodName = "create")
public class Kit {

    private String name;

    private List<ItemStack> armorContents;

    private List<ItemStack> inventoryContents;

}
