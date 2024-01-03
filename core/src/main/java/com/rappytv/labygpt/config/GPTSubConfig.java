package com.rappytv.labygpt.config;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingListener;
import net.labymod.api.configuration.settings.annotation.SettingListener.EventType;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.Collections;

public class GPTSubConfig extends Config {

    @Exclude
    private final String[] models = new String[]{"GPT-3.5-Turbo", "GPT-3.5-Turbo-16k", "GPT-4", "GPT-4-32k"};

    @DropdownSetting
    @SpriteSlot(size = 32, x = 1)
    private final ConfigProperty<String> model = new ConfigProperty<>(models[0]);
    @TextFieldSetting
    @SpriteSlot(size = 32, x = 1, y = 1)
    private final ConfigProperty<String> behavior = new ConfigProperty<>("You are a helpful assistant.");

    @SuppressWarnings("unchecked")
    @SettingListener(target = "model", type = EventType.INITIALIZE)
    public void initialize(SettingElement setting) {
        DropdownWidget<String> widget = (DropdownWidget<String>) setting.asElement().getWidgets()[0];
        widget.addAll(models);
        setting.setWidgets(Collections.singletonList(widget)
            .toArray(new Widget[0]));
    }

    public ConfigProperty<String> model() {
        return model;
    }
    public ConfigProperty<String> behavior() {
        return behavior;
    }
}
