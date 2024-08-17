package dev.aleesk.arsenal.utilities.prompt;

import dev.aleesk.arsenal.utilities.ChatUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

@RequiredArgsConstructor
public class PromptRunnable implements Runnable {

	private final PromptHandler promptHandler;
	
	@Override
	public void run() {
		Iterator<Entry<UUID, PromptData>> iterator = promptHandler.getPromptMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<UUID, PromptData> entry = iterator.next();
			PromptData promptData = entry.getValue();
			long startMillis = promptData.getStartMillis();
			
			if((System.currentTimeMillis() - startMillis) > promptData.getTimeout()) {
				Player player = Bukkit.getPlayer(entry.getKey());
				if(player != null) {
					player.sendMessage(ChatUtil.translate("&cYou have exceded the conversation time for response, you were removed from them."));
				}
				
				iterator.remove();
			}
		}
	}
}
