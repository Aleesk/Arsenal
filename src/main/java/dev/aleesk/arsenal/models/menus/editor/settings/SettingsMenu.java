package dev.aleesk.arsenal.models.menus.editor.settings;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.menus.editor.KitAdminEditorMenu;
import dev.aleesk.arsenal.models.menus.editor.settings.buttons.*;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class SettingsMenu extends Menu {
    private final Arsenal plugin;

    @Override
    public String getTitle(Player player) {
        return "Kit Settings";
    }

    public int getSize() {
        return 4 * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new BackButton(new KitAdminEditorMenu(plugin)));
        buttons.put(10, new TitleButton(plugin));
        buttons.put(11, new DecorationsButton(plugin));
        buttons.put(12, new GlowDecorationsButton());
        buttons.put(21, new EnchantDecorationsButton());
        buttons.put(13, new DefaultCategoryButton(plugin));
        buttons.put(14, new RowsButton());
        buttons.put(15, new KitStarterButton(plugin));
        buttons.put(16, new KitClearInventoryButton());
        return buttons;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }
}
