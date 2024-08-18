package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.menus.editor.categories.CategoriesMenu;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class DefaultCategoryButton extends Button {

    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final Arsenal plugin;

    @Override
    public ItemStack getButtonItem(Player player) {
        String categoryName = configFile.getString("kit_gui.default_category");
        String name = null;

        Map<String, Category> categories = plugin.getCategoryManager().getCategories();
        if (categories.containsKey(categoryName)) {
            Category category = categories.get(categoryName);
            name = category.getName();
        }

        return new ItemBuilder(name != null ? XMaterial.CHEST_MINECART.parseMaterial() : XMaterial.MINECART.parseMaterial())
                .setName("&aDefault Category")
                .setLore("&7Define which default category you want",
                        "&7to display when opening the kits GUI.",
                        "",
                        "&7Current: &r" + (name == null ? "&cNone" : name),
                        "",
                        "&eClick to change!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        new CategoriesMenu(plugin, false).openMenu(player);
    }
}
