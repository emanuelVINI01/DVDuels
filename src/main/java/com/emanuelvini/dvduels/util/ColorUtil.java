package com.emanuelvini.dvduels.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    public static String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> parseColors(List<String> strings) {
        return strings.stream().map(ColorUtil::parseColors).collect(Collectors.toList());
    }

}
