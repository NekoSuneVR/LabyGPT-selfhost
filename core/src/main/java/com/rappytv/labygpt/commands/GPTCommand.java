package com.rappytv.labygpt.commands;

import com.rappytv.labygpt.GPTAddon;
import com.rappytv.labygpt.api.GPTRequest;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.util.I18n;
import java.util.Objects;

public class GPTCommand extends Command {

    private final GPTAddon addon;

    public GPTCommand(GPTAddon addon) {
        super("gpt");

        this.addon = addon;
        withSubCommand(new GPTClearSubCommand());
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(addon.configuration().bearer().isEmpty()) {
            displayMessage(GPTAddon.prefix + "§c" + I18n.translate("labygpt.messages.noKey"));
            return true;
        }
        if(arguments.length < 1) {
            displayMessage(GPTAddon.prefix + "§c" + I18n.translate("labygpt.messages.noQuery"));
            return true;
        }

        if(!addon.configuration().saveHistory())
            GPTAddon.queryHistory.clear();
        GPTRequest request = new GPTRequest(
            String.join(" ", arguments),
            addon.configuration().bearer(),
            this.addon.configuration().shareUsername() ? labyAPI.getName() : ""
        );

        if(!request.isSuccessful() || request.getOutput() == null) {
            displayMessage(GPTAddon.prefix + "§c" + Objects.requireNonNullElseGet(request.getError(), () -> I18n.translate("labygpt.messages.requestError")));
            return true;
        }

        displayMessage(GPTAddon.prefix + "§f" + request.getOutput());
        return true;
    }
}
