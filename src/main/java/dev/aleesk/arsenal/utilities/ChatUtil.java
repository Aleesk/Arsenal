package dev.aleesk.arsenal.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class ChatUtil {

    public String LONG_LINE = "&7&m---------------------------------------------------";
    public String NORMAL_LINE = "&7&m-----------------------------";

    public String translate(String text) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String hexCode = text.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();

            for (char c : ch) {
                builder.append("&").append(c);
            }

            text = text.replace(hexCode, builder.toString());
            matcher = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> translate(List<String> list) {
        return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public String[] translate(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = translate(array[i]);
        }
        return array;
    }

    public void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(translate(text));
    }

    public void sendMessage(CommandSender sender, String[] array) {
        sender.sendMessage(translate(array));
    }

    public void logger(String text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public void logger(String[] text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public void broadcast(String text) {
        Bukkit.broadcastMessage(translate(text));
    }

    public String format(String string, String name) {
        return string.replace("%player%", name);
    }

    public static String toReadable(String name) {
        if (name != null) return WordUtils.capitalize(name.replace("_", " ").toLowerCase());
        return null;
    }

    public static String strip(String text) {
        return ChatColor.stripColor(text);
    }

    public static List<String> replacePlaceholder(List<String> lore, String placeholder, String replacement) {
        List<String> updatedLore = new ArrayList<>();
        for (String line : lore) {
            updatedLore.add(line.replace(placeholder, replacement));
        }
        return updatedLore;
    }
}
