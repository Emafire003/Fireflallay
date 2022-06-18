package me.emafire003.dev.fireflallaymod.mixin;

import me.emafire003.dev.fireflallaymod.entities.FireflallayEntity;
import net.minecraft.entity.ai.brain.task.FrogEatEntityTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrogEatEntityTask.class)
public abstract class FrogEatsFireflallayMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;remove(Lnet/minecraft/entity/Entity$RemovalReason;)V"), method = "eat")
    private void eatFireflallay(ServerWorld world, FrogEntity frog, CallbackInfo ci) {
        if(frog.getFrogTarget().get() instanceof FireflallayEntity){
            frog.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5*20, 3));
           }
    }
}
