package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CategoriesDefaultButton extends Button {

    private final Category category;

    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final String path = "kit_gui.default_category";

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(category.getMaterial())
                .setName(category.getName().equals(configFile.getString(path)) ? "&aCurrent: &r" + category.getDisplayName() : "&c" + category.getDisplayName())
                .setLore("&7Click to define this default category,",
                        "&7which will be displayed when opening",
                        "&7the kits GUI.",
                        "",
                        "&eClick to set!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        configFile.getConfiguration().set(path, category.getName());
        configFile.save();
    }
}
