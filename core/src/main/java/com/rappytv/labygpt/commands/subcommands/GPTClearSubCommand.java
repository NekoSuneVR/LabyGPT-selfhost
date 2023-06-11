package com.rappytv.labygpt.commands.subcommands;

import com.rappytv.labygpt.GPTAddon;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.util.I18n;

public class GPTClearSubCommand extends SubCommand {

    public GPTClearSubCommand() {
        super("clear");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(GPTAddon.queryHistory.isEmpty()) {
            displayMessage(GPTAddon.prefix + "§c" + I18n.translate("labygpt.messages.alreadyEmptyHistory"));
            return true;
        }
        GPTAddon.queryHistory.clear();
        displayMessage(GPTAddon.prefix + "§a" + I18n.translate("labygpt.messages.historyCleared"));
        return true;
    }
}
