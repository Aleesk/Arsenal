package dev.aleesk.arsenal.models.menus.editor.kits;

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
public class KitSlotMenu extends Menu {

    private final Kit kit;

    private final Arsenal plugin;

    public String getTitle(Player player) {
        return this.kit.getName() + " Kit Slot";
    }

    public int getSize() {
        return plugin.getConfigFile().getInt("kit_gui.rows") * 9;
    }

    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < getSize(); i++)
            buttons.put(i, new AvailableSlot(this.kit));

        if (this.kit.getCategory() != null)
            for (Category category : this.plugin.getCategoryManager().getCategories().values())
                if (category.getSlot() != -1 && category.getSlot() < getSize()) {
                    buttons.put(category.getSlot(), new NoAvailableSlotCategory(category));
                }

        for (Kit otherKit : this.plugin.getKitManager().getKits().values())
            if (otherKit.getCategory() != null && otherKit.getCategory().equals(this.kit.getCategory()))
                if (otherKit.getSlot() != -1 && otherKit.getSlot() < getSize()) {
                    buttons.put(otherKit.getSlot(), new NoAvailableSlotKit(otherKit));
                }

        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }

    @RequiredArgsConstructor
    private static class AvailableSlot extends Button {
        private final Kit kit;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial()))
                    .setData(5)
                    .setName("&aAvailable Slot")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            this.kit.setSlot(slot);
            this.kit.save();
            playSuccess(player);
        }
    }

    @RequiredArgsConstructor
    private static class NoAvailableSlotKit extends Button {
        private final Kit kit;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()))
                    .setData(14)
                    .setName(this.kit.getDisplayName() + " &6Kit")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            ChatUtil.sendMessage(player, "&cThis slot is occupied by " + this.kit.getName() + " Kit");
            playFail(player);
        }
    }

    @RequiredArgsConstructor
    private static class NoAvailableSlotCategory extends Button {
        private final Category category;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(this.category.getMaterial()))
                    .setData(this.category.getData())
                    .setEnchanted(this.category.isGlow(), this.category.isEnchant())
                    .setName(this.category.getDisplayName() + " &6Category")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            ChatUtil.sendMessage(player, "&cThis slot is occupied by " + this.category.getName() + " Category");
            playFail(player);
        }
    }
}
