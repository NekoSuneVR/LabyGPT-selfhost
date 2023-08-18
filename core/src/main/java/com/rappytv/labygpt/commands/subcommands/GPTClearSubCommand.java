package com.rappytv.labygpt.commands.subcommands;

import com.rappytv.labygpt.GPTAddon;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;

public class GPTClearSubCommand extends SubCommand {

    public GPTClearSubCommand() {
        super("clear");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(GPTAddon.queryHistory.isEmpty()) {
            displayMessage(Component.empty().append(GPTAddon.prefix).append(Component.translatable("labygpt.messages.alreadyEmptyHistory", NamedTextColor.RED)));
            return true;
        }
        GPTAddon.queryHistory.clear();
        displayMessage(Component.empty().append(GPTAddon.prefix).append(Component.translatable("labygpt.messages.historyCleared", NamedTextColor.GREEN)));
        return true;
    }
}
