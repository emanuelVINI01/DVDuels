package com.emanuelvini.dvduels.registry.registries;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.models.Kit;
import com.emanuelvini.dvduels.registry.Registry;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class KitRegistry extends Registry {
    public KitRegistry(DvDuels plugin) {
        super(plugin);
    }

    @Override
    @SneakyThrows
    public void register() {
        val kitsDirectory = new File(plugin.getDataFolder(), "kits");
        if (!kitsDirectory.exists()) {
            kitsDirectory.mkdir();
            plugin.saveResource("kits/example.yml", false);
        }
        val kitManager = plugin.getKitManager();
        Arrays.asList(kitsDirectory.listFiles()).forEach(file -> {
            try {
                val yaml = YamlConfiguration.loadConfiguration(file);
                val kit = loadKit(yaml);
                kitManager.addKit(kit);
                plugin.log(String.format("§aSuccesfully loaded kit §f%s§a.", kit.getName()));
            } catch (Exception e) {
                plugin.log(String.format("§cCan't load the file §f%s§c because a error:", file.getName()));
                e.printStackTrace();
            }
        });
    }

    private ItemStack loadItem(ConfigurationSection section) {
        val material = Material.matchMaterial(section.getString("item"));
        val amount = section.getInt("amount");
        val data = section.getInt("data");

        val item = new ItemStack(material, amount, (short) data);

        val meta = item.getItemMeta();

        section.getStringList("enchantments").forEach(enchant -> {
            val properties = enchant.split(":");
            val type = Enchantment.getByName(properties[0]);
            val level = Integer.parseInt(properties[1]);
            meta.addEnchant(type, level, true);
        });

        item.setItemMeta(meta);

        return item;
    }
    private Kit loadKit(ConfigurationSection section) {
        val armors = new ArrayList<ItemStack>();
        val armorsSection = section.getConfigurationSection("armors");
        if (armorsSection.contains("helmet")) {
            armors.add(loadItem(armorsSection.getConfigurationSection("helmet")));
        } else {
            armors.add(new ItemStack(Material.AIR));
        }
        if (armorsSection.contains("chestplate")) {
            armors.add(loadItem(armorsSection.getConfigurationSection("chestplate")));
        } else {
            armors.add(new ItemStack(Material.AIR));
        }
        if (armorsSection.contains("leggings")) {
            armors.add(loadItem(armorsSection.getConfigurationSection("leggings")));
        } else {
            armors.add(new ItemStack(Material.AIR));
        }
        if (armorsSection.contains("boots")) {
            armors.add(loadItem(armorsSection.getConfigurationSection("boots")));
        } else {
            armors.add(new ItemStack(Material.AIR));
        }

        val inventorySection = section.getConfigurationSection("items");

        val items = new ArrayList<ItemStack>();
        inventorySection.getKeys(false).forEach(key -> {
            items.add(loadItem(inventorySection.getConfigurationSection(key)));
        });

        return Kit
                .create()
                .name(section.getString("name"))
                .armorContents(armors)
                .inventoryContents(items)
                .build();
    }

    @Override
    public void unregister() {

    }
}
