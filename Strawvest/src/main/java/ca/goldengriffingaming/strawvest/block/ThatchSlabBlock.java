package ca.goldengriffingaming.strawvest.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ThatchSlabBlock extends SlabBlock {

    public static final EnumProperty<Direction> FACING =
        BlockStateProperties.HORIZONTAL_FACING;

    public ThatchSlabBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockState existingState =
            context.getLevel().getBlockState(context.getClickedPos());

    BlockState placedState = super.getStateForPlacement(context);

    if (placedState == null) {
        return null;
    }

    // When combining two slabs, retain the direction of the first slab.
    if (existingState.is(this)) {
        return placedState.setValue(
                FACING,
                existingState.getValue(FACING)
        );
    }

    // A newly placed slab faces according to the player's direction.
    return placedState.setValue(
            FACING,
            context.getHorizontalDirection().getOpposite()
    );
}

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(
                FACING,
                rotation.rotate(state.getValue(FACING))
        );
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(
                FACING,
                mirror.mirror(state.getValue(FACING))
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}