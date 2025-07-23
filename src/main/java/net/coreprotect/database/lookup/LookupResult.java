package net.coreprotect.database.lookup;

import it.unimi.dsi.fastutil.Pair;
import net.coreprotect.utility.Chat;
import org.bukkit.command.CommandSender;

import java.util.List;

public record LookupResult(List<Pair<String, List<Object>>> strings) {

    public void add(String string, Object... args) {
        if (string == null) return;
        strings.add(Pair.of(string, List.of(args)));
    }

    public void send(CommandSender sender) {
        for (Pair<String, List<Object>> entry : strings) {
            Chat.sendComponent(sender, entry.key(), null, entry.value().toArray());
        }
    }
}
