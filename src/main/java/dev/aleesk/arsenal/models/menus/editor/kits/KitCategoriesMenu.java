package dev.aleesk.arsenal.models.menus.editor.kits;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.KitSetCategoryButton;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import dev.aleesk.arsenal.utilities.menu.buttons.PageButton;
import dev.aleesk.arsenal.utilities.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class KitCategoriesMenu extends PaginatedMenu {

    private final Kit kit;

    private final Arsenal plugin;

    public String getPrePaginatedTitle(Player player) {
        return ChatUtil.translate("Edit " + this.kit.getName() + " Category");
    }

    public int getSize() {
        return 6 * 9;
    }

    public int getMaxItemsPerPage(Player player) {
        return 5 * 9;
    }

    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        AtomicInteger slot = new AtomicInteger();
        for (Category category : this.plugin.getCategoryManager().getCategories().values())
            buttons.put(slot.getAndIncrement(), new KitSetCategoryButton(this.kit, category));
        return buttons;
    }


    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new PageButton(-1, this));
        buttons.put(4, new BackButton(new KitEditMenu(kit, plugin)));
        buttons.put(8, new PageButton(1, this));
        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return false;
    }
}
