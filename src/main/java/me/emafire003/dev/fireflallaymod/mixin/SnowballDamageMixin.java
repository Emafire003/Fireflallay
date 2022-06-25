package me.emafire003.dev.fireflallaymod.mixin;

import me.emafire003.dev.fireflallaymod.entities.frostfairy.FrostFairyEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public abstract class SnowballDamageMixin extends ThrownItemEntity {

    public SnowballDamageMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), method = "onEntityHit")
    private void onEntityHitInject(EntityHitResult entityHitResult, CallbackInfo ci){
        if(this.getOwner() instanceof FrostFairyEntity){
            entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 3);
        }
    }
}
