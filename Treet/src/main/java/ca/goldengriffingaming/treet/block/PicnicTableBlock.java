package ca.goldengriffingaming.treet.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import ca.goldengriffingaming.treet.ModEntities;
import ca.goldengriffingaming.treet.entity.SeatEntity;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class PicnicTableBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<PicnicTableBlock> CODEC =
            simpleCodec(PicnicTableBlock::new);

    /*
     * 0 = anchor / visible model
     * 1 = across the short side
     * 2 = along the long side
     * 3 = diagonal corner
     */
    public static final IntegerProperty PART =
            IntegerProperty.create("part", 0, 3);

    /*
     * Solid collision strips.
     *
     * We intentionally use a solid 12-pixel-high footprint rather than
     * modelling every bench/leg individually. This prevents the player
     * from staircase-climbing over the benches and tabletop.
     */

    private static final VoxelShape WEST_INNER =
            Block.box(0, 0, 0, 10, 12, 16);

    private static final VoxelShape WEST_OUTER =
            Block.box(6, 0, 0, 16, 12, 16);

    private static final VoxelShape EAST_INNER =
            Block.box(6, 0, 0, 16, 12, 16);

    private static final VoxelShape EAST_OUTER =
            Block.box(0, 0, 0, 10, 12, 16);

    private static final VoxelShape NORTH_INNER =
            Block.box(0, 0, 0, 16, 12, 10);

    private static final VoxelShape NORTH_OUTER =
            Block.box(0, 0, 6, 16, 12, 16);

    private static final VoxelShape SOUTH_INNER =
            Block.box(0, 0, 6, 16, 12, 16);

    private static final VoxelShape SOUTH_OUTER =
            Block.box(0, 0, 0, 16, 12, 10);

    public PicnicTableBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(PART, 0)
        );
    }
    private static final double[][] SEAT_POSITIONS = {
        // Left bench
        {-0.53125, 0.4375, 0.5},
        {-0.53125, 0.4375, 1.5},

        // Right bench
        {0.53125, 0.4375, 0.5},
        {0.53125, 0.4375, 1.5}
};
    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction facing =
                context.getHorizontalDirection().getOpposite();

        Direction longDirection =
                facing.getCounterClockWise();

        BlockPos anchor =
                context.getClickedPos();

        BlockPos side =
                anchor.relative(facing);

        BlockPos end =
                anchor.relative(longDirection);

        BlockPos corner =
                side.relative(longDirection);

        /*
         * Don't allow placement unless the whole 2x2 footprint
         * is available.
         */
        if (!context.getLevel()
                        .getBlockState(side)
                        .canBeReplaced(context)
                || !context.getLevel()
                        .getBlockState(end)
                        .canBeReplaced(context)
                || !context.getLevel()
                        .getBlockState(corner)
                        .canBeReplaced(context)) {

            return null;
        }

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(PART, 0);
    }

    @Override
    public void setPlacedBy(
            Level level,
            BlockPos pos,
            BlockState state,
            LivingEntity placer,
            ItemStack stack
    ) {
        super.setPlacedBy(
                level,
                pos,
                state,
                placer,
                stack
        );

        Direction facing =
                state.getValue(FACING);

        Direction longDirection =
                facing.getCounterClockWise();

        BlockPos side =
                pos.relative(facing);

        BlockPos end =
                pos.relative(longDirection);

        BlockPos corner =
                side.relative(longDirection);

        level.setBlock(
                side,
                state.setValue(PART, 1),
                3
        );

        level.setBlock(
                end,
                state.setValue(PART, 2),
                3
        );

        level.setBlock(
                corner,
                state.setValue(PART, 3),
                3
        );
    }

    @Override
    public BlockState playerWillDestroy(
            Level level,
            BlockPos pos,
            BlockState state,
            Player player
    ) {
        if (!level.isClientSide()) {
            removeOtherParts(
                    level,
                    pos,
                    state
            );
        }

        return super.playerWillDestroy(
                level,
                pos,
                state,
                player
        );
    }

    private void removeOtherParts(
            Level level,
            BlockPos brokenPos,
            BlockState brokenState
    ) {
        BlockPos anchor =
                getAnchorPos(
                        brokenPos,
                        brokenState
                );

        Direction facing =
                brokenState.getValue(FACING);

        Direction longDirection =
                facing.getCounterClockWise();

        BlockPos[] positions = {
                anchor,
                anchor.relative(facing),
                anchor.relative(longDirection),
                anchor.relative(facing)
                        .relative(longDirection)
        };

        for (BlockPos partPos : positions) {

            if (partPos.equals(brokenPos)) {
                continue;
            }

            BlockState partState =
                    level.getBlockState(partPos);

            if (partState.is(this)
                    && partState.getValue(FACING) == facing) {

                level.removeBlock(
                        partPos,
                        false
                );
            }
        }
    }

    private BlockPos getAnchorPos(
            BlockPos pos,
            BlockState state
    ) {
        Direction facing =
                state.getValue(FACING);

        Direction longDirection =
                facing.getCounterClockWise();

        return switch (state.getValue(PART)) {

            case 0 ->
                    pos;

            case 1 ->
                    pos.relative(
                            facing.getOpposite()
                    );

            case 2 ->
                    pos.relative(
                            longDirection.getOpposite()
                    );

            case 3 ->
                    pos.relative(
                                    facing.getOpposite()
                            )
                            .relative(
                                    longDirection.getOpposite()
                            );

            default ->
                    throw new IllegalStateException(
                            "Invalid picnic table part"
                    );
        };
    }

    @Override
    public RenderShape getRenderShape(
            BlockState state
    ) {
        /*
         * Only part 0 renders the actual picnic-table model.
         * The other three blocks are invisible occupancy/collision helpers.
         */
        return state.getValue(PART) == 0
                ? RenderShape.MODEL
                : RenderShape.INVISIBLE;
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        Direction facing =
                state.getValue(FACING);

        int part =
                state.getValue(PART);

        boolean inner =
                part == 0 || part == 2;

        return switch (facing) {

            case WEST ->
                    inner
                            ? WEST_INNER
                            : WEST_OUTER;

            case EAST ->
                    inner
                            ? EAST_INNER
                            : EAST_OUTER;

            case NORTH ->
                    inner
                            ? NORTH_INNER
                            : NORTH_OUTER;

            case SOUTH ->
                    inner
                            ? SOUTH_INNER
                            : SOUTH_OUTER;

            default ->
                    throw new IllegalStateException(
                            "Picnic table must face horizontally"
                    );
        };
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(
                FACING,
                PART
        );
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

    BlockPos anchor = getAnchorPos(
            pos,
            state
    );

    Direction facing =
            state.getValue(FACING);

    Vec3 clicked =
            hit.getLocation();

    Vec3 closestSeat = null;
    double closestDistance =
            Double.MAX_VALUE;

    for (double[] seat : SEAT_POSITIONS) {

        Vec3 seatPosition =
                getSeatPosition(
                        anchor,
                        facing,
                        seat[0],
                        seat[1],
                        seat[2]
                );
                if (SeatEntity.isOccupied(
        level,
        seatPosition
)) {
    continue;
}
        double distance =
                clicked.distanceToSqr(
                        seatPosition
                );

        if (distance < closestDistance) {
            closestDistance = distance;
            closestSeat = seatPosition;
        }
    }

    if (!level.isClientSide()
            && closestSeat != null) {

        SeatEntity seat =
                new SeatEntity(
                        ModEntities.SEAT,
                        level
                );

        seat.setPos(
                closestSeat.x,
                closestSeat.y,
                closestSeat.z
        );

        level.addFreshEntity(seat);

        if (!player.startRiding(seat)) {
            seat.discard();
        }
    }

    return InteractionResult.SUCCESS;
}
private static Vec3 getSeatPosition(
        BlockPos anchor,
        Direction facing,
        double x,
        double y,
        double z
) {
    double rotatedX;
    double rotatedZ;

    switch (facing) {

        // Picnic-table model's unrotated orientation
        case WEST -> {
            rotatedX = x;
            rotatedZ = z;
        }

        case NORTH -> {
            rotatedX = 1.0 - z;
            rotatedZ = x;
        }

        case EAST -> {
            rotatedX = 1.0 - x;
            rotatedZ = 1.0 - z;
        }

        case SOUTH -> {
            rotatedX = z;
            rotatedZ = 1.0 - x;
        }

        default -> {
            rotatedX = x;
            rotatedZ = z;
        }
    }

    return new Vec3(
            anchor.getX() + rotatedX,
            anchor.getY() + y,
            anchor.getZ() + rotatedZ
    );
}
}