package me.emafire003.dev.fireflallaymod.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


//Fairy of the snow/north: it can be made out of a Totem of Undying, Fireflally Skin, Allay skin, Cold allay skin
//spawns only in cold biomes and it proviedes a resistance boost (small) to players nearby

public class FireflallaySkin extends Item {
    public FireflallaySkin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (this.isFood()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        } else {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(user instanceof ServerPlayerEntity){
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 8*20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 8*20, 0));
        }
        return this.isFood() ? user.eatFood(world, stack) : stack;
    }

}
