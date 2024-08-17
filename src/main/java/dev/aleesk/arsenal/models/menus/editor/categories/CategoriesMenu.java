package dev.aleesk.arsenal.models.menus.editor.categories;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.menus.editor.KitAdminEditorMenu;
import dev.aleesk.arsenal.models.menus.editor.categories.buttons.CategoryCreateButton;
import dev.aleesk.arsenal.models.menus.editor.categories.buttons.CategoryInfoButton;
import dev.aleesk.arsenal.models.menus.editor.categories.buttons.CategoryDeleteAllButton;
import dev.aleesk.arsenal.models.menus.editor.settings.SettingsMenu;
import dev.aleesk.arsenal.models.menus.editor.settings.buttons.CategoriesDefaultButton;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import dev.aleesk.arsenal.utilities.menu.buttons.PageButton;
import dev.aleesk.arsenal.utilities.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class CategoriesMenu extends PaginatedMenu {
    private final Arsenal plugin;

    private final boolean edit;

    public String getPrePaginatedTitle(Player player) {
        return "Categories";
    }

    public int getSize() {
        return 6 * 9;
    }

    public int getMaxItemsPerPage(Player player) {
        return 5 * 9;
    }

    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        AtomicInteger count = new AtomicInteger();
        for (Category category : this.plugin.getCategoryManager().getCategories().values())
            if (edit) {
                buttons.put(count.getAndIncrement(), new CategoryInfoButton(category, this.plugin, true));
            } else {
                buttons.put(count.getAndIncrement(), new CategoriesDefaultButton(category));
            }
        return buttons;
    }

    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new PageButton(-1, this));
        if (edit) {
            buttons.put(2, new BackButton(new KitAdminEditorMenu(plugin)));
            buttons.put(4, new CategoryCreateButton(plugin));
            buttons.put(6, new CategoryDeleteAllButton(plugin));
        } else {
            buttons.put(4, new BackButton(new SettingsMenu(plugin)));
        }
        buttons.put(8, new PageButton(1, this));
        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }
}
