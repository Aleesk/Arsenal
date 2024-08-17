package dev.aleesk.arsenal.utilities.menu.utility;

import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MenuUtility {

    public static ItemStack[] convert(Map<Integer, Button> buttons) {
        ItemStack[] menuItems = new ItemStack[buttons.size()];

        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            int slot = entry.getKey();
            Button button = entry.getValue();
            menuItems[slot] = button.getButtonItem(null);
        }

        return menuItems;
    }
}
