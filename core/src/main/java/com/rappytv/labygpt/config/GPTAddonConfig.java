package com.rappytv.labygpt.config;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class GPTAddonConfig extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @TextFieldSetting
    private final ConfigProperty<String> bearer = new ConfigProperty<>("");
    @SwitchSetting
    private final ConfigProperty<Boolean> shareUsername = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public String bearer() {
        return bearer.get();
    }
    public Boolean shareUsername() {
        return shareUsername.get();
    }
}
