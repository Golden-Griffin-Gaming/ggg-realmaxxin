package ca.goldengriffingaming.strawvest;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static final Item STRAW = register(
            "straw",
            Item::new,
            new Item.Properties()
    );

    public static final Item STRAW_TWINE = register(
            "straw_twine",
            Item::new,
            new Item.Properties()
    );

    public static final Item WOVEN_STRAW = register(
            "woven_straw",
            Item::new,
            new Item.Properties()
    );

    public static <T extends Item> T register(
            String name,
            Function<Item.Properties, T> itemFactory,
            Item.Properties properties
    ) {
        ResourceKey<Item> itemKey = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(
                        GoldenGriffinStrawvest.MOD_ID,
                        name
                )
        );

        T item = itemFactory.apply(
                properties.setId(itemKey)
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                itemKey,
                item
        );

        return item;
    }

        public static void initialize() {
        CreativeModeTabEvents
            .modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
            .register(entries -> {
                entries.accept(STRAW);
                entries.accept(STRAW_TWINE);
                entries.accept(WOVEN_STRAW);
            });
}
}