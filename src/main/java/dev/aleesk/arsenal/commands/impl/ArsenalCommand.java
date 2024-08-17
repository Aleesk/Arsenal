package dev.aleesk.arsenal.commands.impl;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class ArsenalCommand extends BaseCommand {

    private final Arsenal plugin;

    public ArsenalCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "arsenal", permission = "arsenal.command.arsenal", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /arsenal reload");
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.onReload();
            ChatUtil.sendMessage(sender, "&aArsenal has been reloaded.");
        }
    }
}
