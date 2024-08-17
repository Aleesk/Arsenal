package dev.aleesk.arsenal.utilities.prompt.defaults;

import dev.aleesk.arsenal.utilities.JavaUtil;
import dev.aleesk.arsenal.utilities.prompt.Prompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class IntegerPrompt extends Prompt<Integer> {

	@Override
	public void handleFailed(Player player, String input) {
		player.sendMessage(ChatColor.RED + "Integer value '" + ChatColor.WHITE + input + ChatColor.RED + "' is invalid.");
	}
	
	@Override
	public Integer handleInput(Player player, String input) {
		Integer integer = JavaUtil.tryParseInt(input);
		if (integer == null) {
			return null;
		}

		return integer;
	}
}
