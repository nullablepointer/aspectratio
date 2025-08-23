package me.nullable.aspectratio;

import dev.isxander.yacl3.api.NameableEnum;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.text.Text;

public class Aspectratio implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
    }

    public enum Modes implements NameableEnum {
        MULTIPLIER,
        FIXED;

        @Override
        public Text getDisplayName() {
            return Text.translatable("aspectratio.modes." + name().toLowerCase());
        }
    }
}
