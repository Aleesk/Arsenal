package dev.aleesk.arsenal.models.menus.editor.kits;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.KitInfoButton;
import dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit.display.*;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class KitDisplayMenu extends Menu {

    private final Kit kit;
    private final Arsenal plugin;
    public int selectedDescriptionLine = 0;

    @Override
    public String getTitle(Player player) {
        return kit.getName() + " Kit Edit";
    }

    public int getSize() {
        return 4 * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new BackButton(new KitEditMenu(kit, plugin)));
        buttons.put(4, new KitInfoButton(kit, plugin, false));
        buttons.put(20, new KitDisplayNameButton(kit, plugin));
        buttons.put(21, new KitEnchantButton(kit));
        buttons.put(22, new KitIconButton(kit));
        buttons.put(23, new KitGlowButton(kit));
        buttons.put(24, new KitDescriptionButton(kit, plugin, this));
        return buttons;
    }

    public boolean isCancelPlayerInventory() {
        return false;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }
}
