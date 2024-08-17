package dev.aleesk.arsenal.models.menus.view;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.settings.buttons.DecorationItemButton;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class KitGUI extends Menu {

    private final Arsenal plugin;
    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final Category currentCategory;
    
    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(this.configFile.getString("kit_gui.title"));
    }

    public int getSize() {
        return this.configFile.getInt("kit_gui.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        ConfigurationSection panels = plugin.getConfigFile().getConfiguration().getConfigurationSection("kit_gui.decorations.slot-items");
        if (panels != null) {
            for (String key : panels.getKeys(false)) {
                int slot = Integer.parseInt(key);
                if (slot < getSize()) {
                    buttons.put(slot, new DecorationItemButton(slot));
                }
            }
        }

        for (Kit kit : this.plugin.getKitManager().getKits().values()) {
            if (kit.getCategory() != null && kit.getCategory().equals(this.currentCategory)) {
                if (kit.getSlot() != -1 && kit.getSlot() < getSize() && kit.isShow())
                    buttons.put(kit.getSlot(), new KitButton(kit, this.plugin));
            }
        }

        for (Category category : this.plugin.getCategoryManager().getCategories().values()) {
            if (category.getSlot() != -1 && category.getSlot() < getSize())
                buttons.put(category.getSlot(), new CategoryButton(category, plugin));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private static class CategoryButton extends Button {

        private final Category category;
        private final Arsenal plugin;

        @Override
        public ItemStack getButtonItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(this.category.getMaterial());
            itemBuilder.setData(this.category.getData());
            itemBuilder.setName(this.category.getDisplayName());
            itemBuilder.setEnchanted(this.category.isGlow(), this.category.isEnchant());
            itemBuilder.setSkullOwner(this.category.getSkullOwner());
            List<String> lore = this.category.getDescription();
            if (lore != null) {
                itemBuilder.setLore(lore);
            }
            return itemBuilder.build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (player.hasPermission(category.getPermission())) {
                playSuccess(player);
                new KitGUI(plugin, category).openMenu(player);
            } else {
                playFail(player);
                ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("category.no_permission"));
            }
        }
    }

    private static class KitButton extends Button {

        private final Kit kit;
        private final Arsenal plugin;

        public KitButton(Kit kit, Arsenal plugin) {
            this.kit = kit;
            this.plugin = plugin;
        }

        public ItemStack getButtonItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(this.kit.getMaterial());
            itemBuilder.setData(this.kit.getData());
            itemBuilder.setName(this.kit.getDisplayName());
            itemBuilder.setEnchanted(this.kit.isGlow(), this.kit.isEnchant());
            itemBuilder.setSkullOwner(this.kit.getSkullOwner());
            itemBuilder.setLore(plugin.getKitManager().getLoreKit(player, this.kit));
            return itemBuilder.build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (clickType.isRightClick()) {
                (new KitPreviewMenu(this.kit, this.plugin)).openMenu(player);
                return;
            }
            this.kit.give(player, false);
            close(player);
        }
    }
}
