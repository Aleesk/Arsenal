package dev.aleesk.arsenal.models.menus.editor.categories;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class CategorySlotMenu extends Menu{
    private final Category category;

    private final Arsenal plugin;

    public String getTitle(Player player) {
        return this.category.getName() + " Category Slot";
    }

    public int getSize() {
        return plugin.getConfigFile().getInt("kit_gui.rows") * 9;
    }

    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < getSize(); i++)
            buttons.put(i, new AvailableSlot(category));

        for (Category otherCategories: this.plugin.getCategoryManager().getCategories().values())
            if (otherCategories != null && otherCategories.getSlot() != -1 && otherCategories.getSlot() < getSize()) {
                buttons.put(otherCategories.getSlot(), new NoAvailableSlotCategories(otherCategories));
            }

        if (category != null)
            for (Kit kit : this.plugin.getKitManager().getKits().values())
                if (category.equals(kit.getCategory()) && kit.getSlot() != -1 && kit.getSlot() < getSize()) {
                    buttons.put(kit.getSlot(), new NoAvailableSlotKits(kit));
                }

        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }

    @RequiredArgsConstructor
    private static class AvailableSlot extends Button {
        private final Category category;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial()))
                    .setData(5)
                    .setName("&aAvailable Slot")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            category.setSlot(slot);
            category.save();
            playSuccess(player);
        }
    }

    @RequiredArgsConstructor
    private static class NoAvailableSlotCategories extends Button {
        private final Category category;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()))
                    .setData(14)
                    .setName(category.getDisplayName() + " &6Category")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            ChatUtil.sendMessage(player, "&cThis slot is occupied by " + category.getName() + " Category");
            playFail(player);
        }
    }

    @RequiredArgsConstructor
    private static class NoAvailableSlotKits extends Button {
        private final Kit kit;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(kit.getMaterial()))
                    .setData(kit.getData())
                    .setEnchanted(kit.isGlow(), kit.isEnchant())
                    .setName(kit.getDisplayName() + " &6Kit")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            ChatUtil.sendMessage(player, "&cThis slot is occupied by '&f" + kit.getName() + "&c' kit");
            playFail(player);
        }
    }
}
