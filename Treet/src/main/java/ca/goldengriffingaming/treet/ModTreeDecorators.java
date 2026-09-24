
package ca.goldengriffingaming.treet;

import ca.goldengriffingaming.treet.worldgen.RoyalPalmCrownDecorator;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ModTreeDecorators {

    public static final TreeDecoratorType<RoyalPalmCrownDecorator>
            ROYAL_PALM = Registry.register(
                    BuiltInRegistries.TREE_DECORATOR_TYPE,
                    GggTreet.id("royal_palm_crown_decorator"),
                    new TreeDecoratorType<>(
                            RoyalPalmCrownDecorator.CODEC
                    )
            );

    public static void initialize() {
    }
}