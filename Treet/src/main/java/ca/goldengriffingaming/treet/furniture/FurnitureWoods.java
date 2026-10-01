package ca.goldengriffingaming.treet.furniture;

import ca.goldengriffingaming.treet.GggTreet;
import net.minecraft.resources.Identifier;

import java.util.List;

public final class FurnitureWoods {

    private FurnitureWoods() {
    }

    public static final FurnitureWood CYPRESS =
        treet("cypress", "Leyland Cypress");
    
    public static final FurnitureWood FIR =
        external("biomesoplenty", "fir", "Douglas Fir");

    public static final FurnitureWood MAHOGANY =
        external("biomesoplenty", "mahogany", "Honduran Mahogany");

    public static final FurnitureWood REDWOOD =
        external("biomesoplenty", "redwood", "Coast Redwood");

    public static final FurnitureWood JACARANDA =
        external("biomesoplenty", "jacaranda", "Blue Jacaranda");

    public static final FurnitureWood PINE =
        external("biomesoplenty", "pine", "Eastern White Pine");

    public static final FurnitureWood WILLOW =
        external("biomesoplenty", "willow", "Weeping Willow");

    public static final FurnitureWood MAPLE =
        external("biomesoplenty", "maple", "Red Maple");

    public static final FurnitureWood JURUPA_OAK =
        external("biomesoplenty", "origin_oak", "Jurupa Oak");

    public static final FurnitureWood PALM =
        external("biomesoplenty", "palm", "Royal Palm");

    public static final FurnitureWood HELLBLADE =
        external("biomesoplenty", "hellbark", "Demonspur Hellblade");

    public static final FurnitureWood GREY_ARCANUM =
        external("biomesoplenty", "magic", "Grey Arcanum");


    public static final FurnitureWood OAK =
        vanilla("oak", "Oak");
    
    public static final FurnitureWood SPRUCE =
        vanilla("spruce", "Spruce");

    public static final FurnitureWood BIRCH =
        vanilla("birch", "Birch");

    public static final FurnitureWood JUNGLE =
        vanilla("jungle", "Jungle");

    public static final FurnitureWood ACACIA =
        vanilla("acacia", "Acacia");

    public static final FurnitureWood DARK_OAK =
        vanilla("dark_oak", "Dark Oak");

    public static final FurnitureWood MANGROVE =
        vanilla("mangrove", "Mangrove");

    public static final FurnitureWood CHERRY =
        vanilla("cherry", "Cherry");

    public static final FurnitureWood PALE_OAK =
        vanilla("pale_oak", "Pale Oak");

    public static final FurnitureWood BAMBOO =
        vanilla("bamboo", "Bamboo");

    public static final FurnitureWood CRIMSON =
        vanilla("crimson", "Crimson");

    public static final FurnitureWood WARPED =
        vanilla("warped", "Warped");


private static FurnitureWood vanilla(String id, String displayName) {
    return new FurnitureWood(
            id,
            displayName,
            Identifier.fromNamespaceAndPath(
                    "minecraft",
                    id + "_planks"
            ),
            Identifier.fromNamespaceAndPath(
                    "minecraft",
                    "block/" + id + "_planks"
            )
    );
}
private static FurnitureWood treet(String id, String displayName) {
    return new FurnitureWood(
            id,
            displayName,
            Identifier.fromNamespaceAndPath(
                    GggTreet.MOD_ID,
                    id + "_planks"
            ),
            Identifier.fromNamespaceAndPath(
                    GggTreet.MOD_ID,
                    "block/" + id + "_planks"
            )
    );
}
private static FurnitureWood external(
        String namespace,
        String id,
        String displayName
) {
    return new FurnitureWood(
            id,
            displayName,
            Identifier.fromNamespaceAndPath(namespace, id + "_planks"),
            Identifier.fromNamespaceAndPath(namespace, "block/" + id + "_planks")
    );
}


        public static final List<FurnitureWood> ALL = List.of(

//--- TREET/BOP ---//

        CYPRESS,
        FIR,
        MAHOGANY,
        REDWOOD,
        JACARANDA,
        PINE,
        WILLOW,
        MAPLE,
        JURUPA_OAK,
        PALM,
        HELLBLADE,
        GREY_ARCANUM,

//--- VANILLA ---//

        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK,
        MANGROVE,
        CHERRY,
        PALE_OAK,
        BAMBOO,
        CRIMSON,
        WARPED
);

}