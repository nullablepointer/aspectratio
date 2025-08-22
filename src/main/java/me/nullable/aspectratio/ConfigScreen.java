package me.nullable.aspectratio;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.ValueFormatter;
import net.minecraft.text.Text;

import java.text.DecimalFormat;

public class ConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        ConfigCategory.Builder main = ConfigCategory.createBuilder()
                .name(Text.literal("Main"));

        main.option(Option.<Float>createBuilder()
                .name(Text.literal("Multiplier"))
                .binding(1.0f, () -> Config.HANDLER.instance().multiplier, val -> Config.HANDLER.instance().multiplier = val)
                .controller(opt -> FloatFieldControllerBuilder.create(opt)
                        .range(0.0f, 2.0f)
                        .formatValue(new FloatFormatter(3)))
                .build()
        );

        return parent -> YetAnotherConfigLib.createBuilder()
                .save(Config.HANDLER::save)
                .title(Text.literal("Aspect Ratio Settings"))
                .category(main.build())
                .build()
                .generateScreen(parent);
    }

    public record FloatFormatter(int decimalPlaces) implements ValueFormatter<Float> {
        // Fancy way to format decimals so there are no trailing 0's
        public Text format(Float value) {
            DecimalFormat format = new DecimalFormat(getDecimalPlaces());
            return Text.literal(format.format(value));
        }

        private String getDecimalPlaces() {
            StringBuilder pattern = new StringBuilder("#");
            if (decimalPlaces > 0) {
                pattern.append(".");
                for (int i = 0; i < decimalPlaces; i++) {
                    pattern.append("#");
                }
            }
            return pattern.toString();
        }
    }
}
