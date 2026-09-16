package ca.goldengriffingaming.treet.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;

public class SeatEntity extends Entity {

    public SeatEntity(
            EntityType<? extends SeatEntity> type,
            Level level
    ) {
        super(type, level);

        this.setNoGravity(true);
        this.setInvisible(true);
        this.setSilent(true);
    }

    @Override
    protected void defineSynchedData(
            SynchedEntityData.Builder builder
    ) {
    }

    @Override
    protected void readAdditionalSaveData(
            ValueInput input
    ) {
    }

    @Override
    protected void addAdditionalSaveData(
            ValueOutput output
    ) {
    }

    @Override
    public boolean hurtServer(
            ServerLevel level,
            DamageSource source,
            float damage
    ) {
        return false;
    }

    @Override
    protected boolean canAddPassenger(
            Entity passenger
    ) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public Vec3 getPassengerRidingPosition(
            Entity passenger
    ) {
        return this.position();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()
                && this.tickCount > 5
                && this.getPassengers().isEmpty()) {

            this.discard();
        }
    }
    public static boolean isOccupied(
        Level level,
        Vec3 position
) {
    AABB checkArea = new AABB(
            position.x - 0.2,
            position.y - 0.2,
            position.z - 0.2,
            position.x + 0.2,
            position.y + 0.8,
            position.z + 0.2
    );

    return !level.getEntitiesOfClass(
            SeatEntity.class,
            checkArea,
            seat -> !seat.getPassengers().isEmpty()
    ).isEmpty();
}
}