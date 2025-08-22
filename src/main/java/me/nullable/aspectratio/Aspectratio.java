package me.nullable.aspectratio;

import net.fabricmc.api.ClientModInitializer;

public class Aspectratio implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
    }
}
