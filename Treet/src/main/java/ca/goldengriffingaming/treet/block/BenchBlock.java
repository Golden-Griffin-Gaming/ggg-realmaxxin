package ca.goldengriffingaming.treet.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import ca.goldengriffingaming.treet.ModEntities;
import ca.goldengriffingaming.treet.entity.SeatEntity;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.world.phys.Vec3;

public class BenchBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<BenchBlock> CODEC = simpleCodec(BenchBlock::new);

    private static final double[][] NORTH_BOXES = {
        // Legs
        {1, 0, 4, 3, 4, 6},
        {13, 0, 4, 15, 4, 6},
        {1, 0, 10, 3, 4, 12},
        {13, 0, 10, 15, 4, 12},

        // Seat + frame
        {1, 4, 4, 15, 6, 12},

        // Backrest
        {1, 4, 12, 15, 13, 12.5},

        // Armrests
        {0.5, 4, 4, 1, 8, 12.5},
        {15, 4, 4, 15.5, 8, 12.5}
};

private static final Map<Direction, VoxelShape> SHAPES = createShapes();

    public BenchBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }
    private static Map<Direction, VoxelShape> createShapes() {
    Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);

    shapes.put(Direction.NORTH, createShape(Direction.NORTH));
    shapes.put(Direction.EAST, createShape(Direction.EAST));
    shapes.put(Direction.SOUTH, createShape(Direction.SOUTH));
    shapes.put(Direction.WEST, createShape(Direction.WEST));

    return shapes;
}

private static VoxelShape createShape(Direction direction) {
    VoxelShape shape = Shapes.empty();

    for (double[] box : NORTH_BOXES) {
        double minX = box[0];
        double minY = box[1];
        double minZ = box[2];
        double maxX = box[3];
        double maxY = box[4];
        double maxZ = box[5];

        VoxelShape part = switch (direction) {
            case NORTH -> Block.box(
                    minX, minY, minZ,
                    maxX, maxY, maxZ
            );

            case EAST -> Block.box(
                    16 - maxZ, minY, minX,
                    16 - minZ, maxY, maxX
            );

            case SOUTH -> Block.box(
                    16 - maxX, minY, 16 - maxZ,
                    16 - minX, maxY, 16 - minZ
            );

            case WEST -> Block.box(
                    minZ, minY, 16 - maxX,
                    maxZ, maxY, 16 - minX
            );

            default -> Block.box(
                    minX, minY, minZ,
                    maxX, maxY, maxZ
            );
        };

        shape = Shapes.or(shape, part);
    }

    return shape;
}

@Override
protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
) {
    return SHAPES.get(state.getValue(FACING));
}
@Override
protected InteractionResult useWithoutItem(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        BlockHitResult hit
) {
    if (player.isPassenger()) {
        return InteractionResult.PASS;
    }

    Vec3 seatPosition = new Vec3(
            pos.getX() + 0.5,
            pos.getY() + 0.375,
            pos.getZ() + 0.5
    );

    if (!level.isClientSide()) {

        if (SeatEntity.isOccupied(
                level,
                seatPosition
        )) {
            return InteractionResult.SUCCESS;
        }

        SeatEntity seat = new SeatEntity(
                ModEntities.SEAT,
                level
        );

        seat.setPos(
                seatPosition.x,
                seatPosition.y,
                seatPosition.z
        );

        level.addFreshEntity(seat);

        if (!player.startRiding(seat)) {
            seat.discard();
        }
    }

    return InteractionResult.SUCCESS;
}
}