package dev.aleesk.arsenal.utilities.menu.menus;

import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import dev.aleesk.arsenal.utilities.menu.button.BooleanButton;
import dev.aleesk.arsenal.utilities.menu.callback.Callback;
import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Map;

public class ConfirmMenu extends Menu {

    private final String title;
    private final Callback<Boolean> response;

    public int getSize() {
        return 3 * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(11, new BooleanButton(true, this.response));
        buttons.put(15, new BooleanButton(false, this.response));
        return buttons;
    }

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(this.title);
    }

    @ConstructorProperties(value={"title", "response"})
    public ConfirmMenu(String title, Callback<Boolean> response) {
        this.title = title;
        this.response = response;
    }
}

