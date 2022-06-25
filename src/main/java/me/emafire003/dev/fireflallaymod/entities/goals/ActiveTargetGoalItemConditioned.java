package me.emafire003.dev.fireflallaymod.entities.goals;

import me.emafire003.dev.fireflallaymod.FireflallayMod;
import me.emafire003.dev.fireflallaymod.entities.frostfairy.FrostFairyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class ActiveTargetGoalItemConditioned<T extends LivingEntity> extends TrackTargetGoal {
    private static final int DEFAULT_RECIPROCAL_CHANCE = 10;
    protected final Class<T> targetClass;
    /**
     * The reciprocal of chance to actually search for a target on every tick
     * when this goal is not started. This is also the average number of ticks
     * between each search (as in a poisson distribution).
     */
    protected final int reciprocalChance;
    @Nullable
    protected LivingEntity targetEntity;
    protected TargetPredicate targetPredicate;
    protected Item attack_item;

    public ActiveTargetGoalItemConditioned(FrostFairyEntity mob, Class<T> targetClass, boolean checkVisibility, Item attack_item) {
        this(mob, targetClass, 10, checkVisibility, false, (Predicate)null, attack_item);
    }

    public ActiveTargetGoalItemConditioned(FrostFairyEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate, Item attack_item) {
        this(mob, targetClass, 10, checkVisibility, false, targetPredicate, attack_item);
    }

    public ActiveTargetGoalItemConditioned(FrostFairyEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate, Item attack_item) {
        this(mob, targetClass, 10, checkVisibility, checkCanNavigate, (Predicate)null, attack_item);
    }

    public ActiveTargetGoalItemConditioned(FrostFairyEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate, Item attack_item) {
        super(mob, checkVisibility, checkCanNavigate);
        this.targetClass = targetClass;
        this.reciprocalChance = toGoalTicks(reciprocalChance);
        this.setControls(EnumSet.of(Goal.Control.TARGET));
        this.targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(targetPredicate);
        this.attack_item = attack_item;
    }

    public boolean canStart() {
        if(!mob.getStackInHand(Hand.MAIN_HAND).getItem().equals(attack_item)) {
            return false;
        }
        if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
            return false;
        } else {
            this.findClosestTarget();
            return this.targetEntity != null;
        }
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0, distance);
    }

    protected void findClosestTarget() {
        if(!mob.getStackInHand(Hand.MAIN_HAND).getItem().equals(attack_item)) {
            return;
        }
        if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
            this.targetEntity = this.mob.world.getClosestEntity(this.mob.world.getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()), (livingEntity) -> {
                return true;
            }), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        } else {
            this.targetEntity = this.mob.world.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

    }

    public void start() {
        this.mob.setTarget(this.targetEntity);
        super.start();
    }

    public void setTargetEntity(@Nullable LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }
}
