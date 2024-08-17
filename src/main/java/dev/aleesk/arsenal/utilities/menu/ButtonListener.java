package dev.aleesk.arsenal.utilities.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ButtonListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onButtonPress(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = Menu.getMenu(player);

        if (menu != null) {
            event.setCancelled(menu.isCancelPlayerInventory());

            if (event.getSlot() != event.getRawSlot()) {
                if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                    event.setCancelled(menu.isCancelPlayerInventory());
                }
                return;
            }

            if (menu.getButtons().containsKey(event.getSlot())) {
                Button button = menu.getButtons().get(event.getSlot());
                boolean shouldCancel = button.shouldCancel(player, event.getSlot(), event.getClick());
                boolean shouldShift = button.shouldShift(player, event.getSlot(), event.getClick());

                if (shouldCancel && shouldShift) {
                    event.setCancelled(true);
                } else {
                    event.setCancelled(shouldCancel);
                }

                button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());

                if (Menu.getMenus().containsKey(player.getUniqueId())) {
                    Menu newMenu = Menu.getMenu(player);

                    if (newMenu == menu) {
                        if (menu.isUpdateAfterClick()) {
                            menu.setClosedByMenu(true);
                            newMenu.openMenu(player);
                        }
                    }
                } else if (button.shouldUpdate(player, event.getSlot(), event.getClick())) {
                    menu.setClosedByMenu(true);
                    menu.openMenu(player);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu openMenu = Menu.getMenu(player);

        if (openMenu != null) {
            Menu.getMenus().remove(player.getUniqueId());
        }
    }
}