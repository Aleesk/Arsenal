package dev.aleesk.arsenal.models.menus.editor.categories;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.menus.editor.categories.buttons.CategoryInfoButton;
import dev.aleesk.arsenal.models.menus.editor.categories.buttons.edit.*;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import org.bukkit.entity.Player;

import java.util.Map;

public class CategoryEditMenu extends Menu {
    private final Category category;
    private final Arsenal plugin;
    public int selectedDescriptionLine = 0;

    public CategoryEditMenu(Category category, Arsenal plugin) {
        this.category = category;
        this.plugin = plugin;
    }

    public String getTitle(Player player) {
        return this.category.getName() + " Category Edit";
    }

    public int getSize() {
        return 5 * 9;
    }

    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new BackButton(new CategoriesMenu(plugin, true)));
        buttons.put(4, new CategoryInfoButton(category, plugin,  false));
        buttons.put(20, new CategoryDisplayNameButton(category, plugin));
        buttons.put(21, new CategoryEnchantButton(category));
        buttons.put(22, new CategoryIconButton(category));
        buttons.put(23, new CategoryGlowButton(category));
        buttons.put(24, new CategoryDescriptionButton(category, plugin, this));
        buttons.put(30, new CategorySlotButton(category, plugin));
        buttons.put(32, new CategoryPermissionButton(category, plugin));
        return buttons;
    }

    public boolean isCancelPlayerInventory() {
        return false;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }
}
