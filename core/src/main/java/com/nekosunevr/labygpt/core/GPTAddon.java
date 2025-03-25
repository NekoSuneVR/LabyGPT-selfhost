package com.nekosunevr.labygpt.core;

import com.nekosunevr.labygpt.core.command.GPTCommand;
import com.nekosunevr.labygpt.core.config.GPTAddonConfig;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GPTAddon extends LabyAddon<GPTAddonConfig> {

    public static final Component prefix = Component.text("[", NamedTextColor.DARK_GRAY)
        .append(Component.text("LabyGPT Selfhost", NamedTextColor.BLUE))
        .append(Component.text("] ", NamedTextColor.DARK_GRAY));
    public static final String[] models = new String[]{"mistral:7b", "deepseek-llm:7b"};

    @Override
    protected void enable() {
        registerSettingCategory();

        registerCommand(new GPTCommand(this));
    }

    @Override
    protected Class<? extends GPTAddonConfig> configurationClass() {
        return GPTAddonConfig.class;
    }
}
