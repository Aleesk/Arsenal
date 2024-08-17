package dev.aleesk.arsenal.commands.impl.kit.admin.subcommands;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.menus.editor.KitAdminEditorMenu;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;

public class KitEditorCommand extends BaseCommand {

    private final Arsenal plugin;
    public KitEditorCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kitadmin.editor", permission = "arsenal.command.admin.editor", aliases = {"kita.editor"})
    @Override
    public void onCommand(CommandArgs command) {
        new KitAdminEditorMenu(plugin).openMenu(command.getPlayer());
    }
}
