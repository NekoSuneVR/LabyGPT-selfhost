package com.rappytv.labygpt.core.command;

import com.rappytv.labygpt.core.GPTAddon;
import com.rappytv.labygpt.api.GPTMessage;
import com.rappytv.labygpt.api.GPTMessage.GPTRole;
import com.rappytv.labygpt.api.GPTRequest;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.I18n;
import java.util.Objects;

public class GPTCommand extends Command {

    private final GPTAddon addon;

    public GPTCommand(GPTAddon addon) {
        super("gpt");

        this.addon = addon;
        withSubCommand(new ClearSubCommand());
        withSubCommand(new HistorySubCommand());
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if (addon.configuration().openAI().bearer().isEmpty()) {
            displayMessage(Component.empty().append(GPTAddon.prefix)
                .append(Component.translatable("labygpt.messages.noKey", NamedTextColor.RED)));
            return true;
        }
        if (arguments.length < 1) {
            displayMessage(Component.empty().append(GPTAddon.prefix)
                .append(Component.translatable("labygpt.messages.noQuery", NamedTextColor.RED)));
            return true;
        }

        if (!addon.configuration().saveHistory()) {
            GPTRequest.queryHistory.clear();
        }
        GPTRequest.sendRequestAsync(
            String.join(" ", arguments),
            addon.configuration().openAI().apiurl(),
            addon.configuration().openAI().bearer(),
            addon.configuration().openAI().shareUsername() ? labyAPI.getName() : "",
            addon.configuration().gpt().model().get().toLowerCase(),
            addon.configuration().gpt().behavior().get(),
            (response) -> {
                if (!response.successful() || response.output() == null) {
                    GPTRequest.queryHistory.removeLast();
                    displayMessage(
                        Component.empty()
                            .append(GPTAddon.prefix)
                            .append(Component.text(
                                Objects.requireNonNullElse(
                                    response.error(),
                                    I18n.translate("labygpt.messages.requestError")
                                ),
                                NamedTextColor.RED
                            ))
                    );
                } else {
                    displayMessage(Component.empty().append(GPTAddon.prefix)
                        .append(Component.text(response.output(), NamedTextColor.WHITE)));
                }
            }
        );

        return true;
    }

    public static class ClearSubCommand extends SubCommand {

        public ClearSubCommand() {
            super("clear");
        }

        @Override
        public boolean execute(String prefix, String[] arguments) {
            if(GPTRequest.queryHistory.isEmpty()) {
                displayMessage(Component.empty().append(GPTAddon.prefix).append(Component.translatable("labygpt.messages.alreadyEmptyHistory", NamedTextColor.RED)));
                return true;
            }
            GPTRequest.queryHistory.clear();
            displayMessage(Component.empty().append(GPTAddon.prefix).append(Component.translatable("labygpt.messages.historyCleared", NamedTextColor.GREEN)));
            return true;
        }
    }

    public static class HistorySubCommand extends SubCommand {

        public HistorySubCommand() {
            super("history");
        }

        @Override
        public boolean execute(String prefix, String[] arguments) {
            if(GPTRequest.queryHistory.size() < 2) {
                displayMessage(Component.empty().append(GPTAddon.prefix).append(Component.translatable("labygpt.messages.emptyHistory", NamedTextColor.RED)));
                return true;
            }

            Component component = Component.empty();
            for(int i = 0; i < GPTRequest.queryHistory.size(); i++) {
                GPTMessage message = GPTRequest.queryHistory.get(i);
                String name = message.name.isEmpty() ? labyAPI.getName() : message.name;
                if(message.role != GPTRole.System)
                    component
                        .append(Component.text(i == 0 ? "" : "\n"))
                        .append(Component.text("[", NamedTextColor.DARK_GRAY))
                        .append(Component.text(name))
                        .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                        .append(Component.text(message.content.replace("\n\n", ""), NamedTextColor.WHITE));
            }

            displayMessage(component);
            return true;
        }
    }
}
