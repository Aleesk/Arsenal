package dev.aleesk.arsenal.models.menus.editor.kits;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.KitAdminEditorMenu;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.KitCreateButton;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.KitDeleteAllButton;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.KitInfoButton;
import dev.aleesk.arsenal.models.menus.editor.settings.SettingsMenu;
import dev.aleesk.arsenal.models.menus.editor.settings.buttons.KitsStarterButton;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import dev.aleesk.arsenal.utilities.menu.buttons.PageButton;
import dev.aleesk.arsenal.utilities.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class KitsMenu extends PaginatedMenu {
    private final Arsenal plugin;

    private final boolean edit;

    public String getPrePaginatedTitle(Player player) {
        return "Kits";
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
        for (Kit kit : this.plugin.getKitManager().getKits().values())
            if(edit) {
                buttons.put(count.getAndIncrement(), new KitInfoButton(kit, plugin, true));
            } else {
                buttons.put(count.getAndIncrement(), new KitsStarterButton(kit));
            }
        return buttons;
    }

    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new PageButton(-1, this));
        if (edit) {
            buttons.put(2, new BackButton(new KitAdminEditorMenu(plugin)));
            buttons.put(4, new KitCreateButton(plugin));
            buttons.put(6, new KitDeleteAllButton(plugin));
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