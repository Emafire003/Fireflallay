package me.emafire003.dev.fireflallaymod.mixin;

import me.emafire003.dev.fireflallaymod.entities.fireflallay.FireflallayEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FrogEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrogEntity.class)
public abstract class ValidFrogFoodMixin {

    @Inject(at = @At(value = "RETURN"), method = "isValidFrogFood", cancellable = true)
    private static void isValidFrogFoodYep(LivingEntity entity, CallbackInfoReturnable<Boolean> cir){
        if(entity instanceof FireflallayEntity){
            cir.setReturnValue(true);
        }
    }
}
