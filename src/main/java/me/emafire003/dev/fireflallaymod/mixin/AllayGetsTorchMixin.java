package me.emafire003.dev.fireflallaymod.mixin;

import me.emafire003.dev.fireflallaymod.FireflallayMod;
import me.emafire003.dev.fireflallaymod.entities.FireflallayEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntity.class)
public abstract class AllayGetsTorchMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AllayEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER), method = "interactMob")
    private void onAllayGetsTorch(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(player.getStackInHand(hand).getItem().equals(Items.TORCH)){
            AllayEntity allay = ((AllayEntity)(Object)this);
            World world = allay.getEntityWorld();
            FireflallayEntity entity = new FireflallayEntity(FireflallayMod.FIREFLALLAY, world);
            entity.setPos(allay.getX(), allay.getY(), allay.getZ());
            allay.remove(Entity.RemovalReason.KILLED);
            world.spawnEntity(entity);
        }
    }
}
