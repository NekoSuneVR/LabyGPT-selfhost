package com.rappytv.labygpt.commands.subcommands;

import com.rappytv.labygpt.GPTAddon;
import com.rappytv.labygpt.api.GPTMessage;
import com.rappytv.labygpt.api.GPTRole;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.util.I18n;

public class GPTHistorySubCommand extends SubCommand {

    public GPTHistorySubCommand() {
        super("history");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(GPTAddon.queryHistory.size() < 2) {
            displayMessage(GPTAddon.prefix + "§c" + I18n.translate("labygpt.messages.emptyHistory"));
            return true;
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < GPTAddon.queryHistory.size(); i++) {
            GPTMessage message = GPTAddon.queryHistory.get(i);
            String name = message.name.isEmpty() ? labyAPI.getName() : message.name;
            if(message.role != GPTRole.System)
                builder.append("§8[§9").append(name).append("§8] §f").append(message.content.replace("\n\n", "")).append("\n");
        }
        String history = builder.toString();

        displayMessage(history.substring(0, history.length() - 1));
        return true;
    }
}
