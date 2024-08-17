package dev.aleesk.arsenal.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class OffHandUtil {

    public static ItemStack getOffHandItem(Player player) {
        try {
            Method getInventoryMethod = Player.class.getMethod("getInventory");
            Object inventory = getInventoryMethod.invoke(player);

            Method getItemInOffHandMethod = inventory.getClass().getMethod("getItemInOffHand");
            return (ItemStack) getItemInOffHandMethod.invoke(inventory);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setOffHandItem(Player player, ItemStack item) {
        try {
            Method getInventoryMethod = Player.class.getMethod("getInventory");
            Object inventory = getInventoryMethod.invoke(player);

            Method setItemInOffHandMethod = inventory.getClass().getMethod("setItemInOffHand", ItemStack.class);
            setItemInOffHandMethod.invoke(inventory, item);
        } catch (Exception e){
            //nothing
        }
    }
}
