package dev.aleesk.arsenal.commands.impl.kit.admin.subcommands;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.menus.editor.settings.SettingsMenu;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;

public class KitSettingsCommand extends BaseCommand {
    private final Arsenal plugin;
    public KitSettingsCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kitadmin.settings", permission = "arsenal.command.admin.settings", aliases = {"kita.settings"})
    @Override
    public void onCommand(CommandArgs command) {
        new SettingsMenu(plugin).openMenu(command.getPlayer());
    }
}
