package dev.aleesk.arsenal.commands.impl.kit.admin.subcommands;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCooldownCommand extends BaseCommand {
    private final Arsenal plugin;

    public KitCooldownCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kitadmin.cooldown", permission = "arsenal.command.admin.cooldown", aliases = {"kita.cooldown"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String label = command.getLabel().replace(".cooldown", "");
        String[] args = command.getArgs();
        if (args.length < 3) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " cooldown <set|remove> <kit> <player>");
            return;
        }
        if (args[0].equalsIgnoreCase("set")) {
            String kitName = args[1];
            Kit kit = this.plugin.getKitManager().getByName(kitName);
            if (kit == null) {
                ChatUtil.sendMessage(sender, "&c'&f" + kitName + "&c' kit not found.");
                return;
            }
            Player player = Bukkit.getPlayer(args[2]);
            if (player == null) {
                ChatUtil.sendMessage(sender, "&cPlayer '&f" + args[1] + "&c' not found.");
                return;
            }
            Arsenal.get().getUserManager().getUser(player.getUniqueId()).addKitCooldown(kit.getName(), kit.getCooldown());
            ChatUtil.sendMessage(sender, "&7Set cooldown of " + kit.getName() + " kit to player &a" + player.getName());
        } else if (args[0].equalsIgnoreCase("remove")) {
            String kitName = args[1];
            Kit kit = this.plugin.getKitManager().getByName(kitName);
            if (kit == null) {
                ChatUtil.sendMessage(sender, "&c'&f" + kitName + "&c' kit not found.");
                return;
            }
            Player player = Bukkit.getPlayer(args[2]);
            if (player == null) {
                ChatUtil.sendMessage(sender, "&cPlayer '&f" + args[1] + "&c' not found.");
                return;
            }
            Arsenal.get().getUserManager().getUser(player.getUniqueId()).removeKitCooldown(kitName);
            ChatUtil.sendMessage(sender, "&aRemove cooldown of '&f" + kit.getName() + "&a' kit to player '&f" + player.getName() + "&a'.");
        } else {
            ChatUtil.sendMessage(sender, "&cCooldown subcommand '&f" + args[0] + "&c' not found.");
        }
    }
}
