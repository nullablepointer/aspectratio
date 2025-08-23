package me.nullable.aspectratio;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("aspectratio", "aspectratio_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("aspectratio_config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public float ratio = 1.0f;

    @SerialEntry
    public Aspectratio.Modes mode = Aspectratio.Modes.MULTIPLIER;
}