package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;

public final class RealmaxxinMoisture {

    // Soil:
    // Wet -> Normal happens relatively quickly.
    public static final int SOIL_WET_TO_NORMAL = 8;

    // Normal -> Dry represents prolonged drying / drought.
    public static final int SOIL_NORMAL_TO_DRY = 320;

    // Grass surface appearance changes much faster than the soil itself.
    public static final int GRASS_WET_TO_NORMAL = 8;
    public static final int GRASS_NORMAL_TO_DRY = 12;

    // Paths are compacted and react somewhat differently.
    public static final int PATH_WET_TO_NORMAL = 12;
    public static final int PATH_NORMAL_TO_DRY = 40;

    // Cultivated farmland.
    public static final int FARMLAND_WET_TO_NORMAL = 10;
    public static final int FARMLAND_NORMAL_TO_DRY = 160;

    // Gravel is a bit more porous than soil, so it dries out faster.
    public static final int GRAVEL_WET_TO_NORMAL = 6;
    public static final int GRAVEL_NORMAL_TO_DRY = 24;

    public static final int GRAVEL_PATH_WET_TO_NORMAL = 8;
    public static final int GRAVEL_PATH_NORMAL_TO_DRY = 30;

    public static final int MUD_WET_TO_NORMAL = 6;
    public static final int MUD_NORMAL_TO_DRY = 60;

    private RealmaxxinMoisture() {
    }

    public static boolean hasNearbyWater(
            ServerLevel level,
            BlockPos pos,
            int radius
    ) {
        for (BlockPos checkPos : BlockPos.betweenClosed(
                pos.offset(-radius, 0, -radius),
                pos.offset(radius, 1, radius)
        )) {
            if (level.getFluidState(checkPos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }
}