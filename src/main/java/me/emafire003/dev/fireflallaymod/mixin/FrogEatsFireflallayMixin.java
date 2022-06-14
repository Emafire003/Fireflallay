package me.emafire003.dev.fireflallaymod.mixin;

import me.emafire003.dev.fireflallaymod.FireflallayMod;
import me.emafire003.dev.fireflallaymod.entities.FireflallayEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.task.FrogEatEntityTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FrogEatEntityTask.class)
public abstract class FrogEatsFireflallayMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;remove(Lnet/minecraft/entity/Entity$RemovalReason;)V"), method = "eat")
    private void eatFireflallay(ServerWorld world, FrogEntity frog, CallbackInfo ci) {
        if(frog.getFrogTarget().get() instanceof FireflallayEntity){
            frog.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5*20, 3));
            world.spawnEntity(new ItemEntity(world, frog.getX(), frog.getY(), frog.getZ(), new ItemStack(FireflallayMod.ALLAY_SKIN)));
        }
    }
}
