package ca.goldengriffingaming.treet;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class ModItems {

    public static final Item MAHOGANY_SEEDS = register(
            "mahogany_seeds",
            Item::new,
            new Item.Properties()
    );

    public static final Item MAHOGANY_SEED_POD = register(
            "mahogany_seed_pod",
            Item::new,
            new Item.Properties()
    );

        public static final Item FIR_SEEDS = register(
            "fir_seeds",
            Item::new,
            new Item.Properties()
    );

    public static final Item FIR_CONE = register(
            "fir_cone",
            Item::new,
            new Item.Properties()
    );
    public static final Item CYPRESS_BOAT = register(
        "cypress_boat",
        properties -> new BoatItem(ModEntities.CYPRESS_BOAT, properties),
        new Item.Properties().stacksTo(1)
    );

    public static final Item CYPRESS_CHEST_BOAT = register(
        "cypress_chest_boat",
        properties -> new BoatItem(ModEntities.CYPRESS_CHEST_BOAT, properties),
        new Item.Properties().stacksTo(1)
    );
    public static final Item CYPRESS_SEED_POD = register(
        "cypress_seed_pod",
        Item::new,
        new Item.Properties()
    );

    public static final Item CYPRESS_SEEDS = register(
        "cypress_seeds",
        Item::new,
        new Item.Properties()
    );

    public static final Item CYPRESS_SEEDLING = register(
        "cypress_seedling",
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
                        GggTreet.MOD_ID,
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
                entries.accept(MAHOGANY_SEEDS);
                entries.accept(MAHOGANY_SEED_POD);
                entries.accept(CYPRESS_SEED_POD);
                entries.accept(CYPRESS_SEEDS);
                entries.accept(CYPRESS_SEEDLING);
            });
        CreativeModeTabEvents
            .modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register(entries -> {
            entries.insertAfter(
                    Items.PALE_OAK_CHEST_BOAT,
                    CYPRESS_BOAT,
                    CYPRESS_CHEST_BOAT
            );
        });
}
}