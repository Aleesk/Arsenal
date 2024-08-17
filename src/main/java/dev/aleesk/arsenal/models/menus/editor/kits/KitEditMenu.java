package dev.aleesk.arsenal.models.menus.editor.kits;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.KitInfoButton;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit.*;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class KitEditMenu extends Menu {

    private final Kit kit;
    private final Arsenal plugin;
    public int selectedPlayerCommand = 0;
    public int selectedConsoleCommand = 0;

    public String getTitle(Player player) {
        return kit.getName() + " Kit Edit";
    }

    public int getSize() {
        return 6 * 9;
    }

    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new BackButton(new KitsMenu(plugin, true)));
        buttons.put(4, new KitInfoButton(kit, plugin, false));
        buttons.put(8, new KitGoToCategoryButton(kit, plugin));
        buttons.put(20, new KitDisplayButton(kit, plugin));
        buttons.put(24, new KitSlotButton(kit, plugin));
        buttons.put(29, new KitCooldownButton(kit, plugin));
        buttons.put(31, new KitCategoryButton(kit, plugin));
        buttons.put(33, new KitLootButton(kit, plugin));
        buttons.put(38, new KitPermissionButton(kit, plugin));
        buttons.put(42, new KitSoundButton(kit, plugin));
        buttons.put(49, new KitShowButton(kit));
        buttons.put(45, new KitCommandsPlayerButton(kit, plugin, this));
        buttons.put(53, new KitCommandsConsoleButton(kit, plugin ,this));
        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }
}
