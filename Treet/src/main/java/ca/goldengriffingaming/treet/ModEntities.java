package ca.goldengriffingaming.treet;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;

public class ModEntities {

    public static final EntityType<Boat> CYPRESS_BOAT = register(
            "cypress_boat",
            EntityType.Builder.<Boat>of(
                    (type, level) -> new Boat(
                            type,
                            level,
                            () -> ModItems.CYPRESS_BOAT
                    ),
                    MobCategory.MISC
            )
            .noLootTable()
            .sized(1.375F, 0.5625F)
            .eyeHeight(0.5625F)
            .clientTrackingRange(10)
    );

    public static final EntityType<ChestBoat> CYPRESS_CHEST_BOAT = register(
            "cypress_chest_boat",
            EntityType.Builder.<ChestBoat>of(
                    (type, level) -> new ChestBoat(
                            type,
                            level,
                            () -> ModItems.CYPRESS_CHEST_BOAT
                    ),
                    MobCategory.MISC
            )
            .noLootTable()
            .sized(1.375F, 0.5625F)
            .eyeHeight(0.5625F)
            .clientTrackingRange(10)
    );

    private static <T extends Entity> EntityType<T> register(
            String name,
            EntityType.Builder<T> builder
    ) {
        ResourceKey<EntityType<?>> entityKey = ResourceKey.create(
                Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(GggTreet.MOD_ID, name)
        );

        EntityType<T> entityType = builder.build(entityKey);

        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                entityKey,
                entityType
        );
    }

    public static void initialize() {
    }
}