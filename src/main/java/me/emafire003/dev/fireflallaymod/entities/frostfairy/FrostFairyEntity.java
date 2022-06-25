package me.emafire003.dev.fireflallaymod.entities.frostfairy;

import me.emafire003.dev.fireflallaymod.entities.goals.ActiveTargetGoalItemConditioned;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;


//Depending on which item you give it, it will grant different effects. Which could be status effects, damage to entities or resitances maybe?
//May try to like attack entities nearby when the player gets damaged. Maybe with snowballs? Like a SnowGolem?
public class FrostFairyEntity extends AllayEntity implements RangedAttackMob {
    public FrostFairyEntity(EntityType<? extends AllayEntity> entityType, World world) {
        super(entityType, world);
    }

    private boolean can_attack = false;
    //obsidian
    private boolean resistance_buff = false;
    //sugar
    private boolean swiftness_buff = false;
    //rabbit foot
    private boolean jump_buff = false;
    //feather
    private boolean slowfall_buff = false;
    //blue ice
    private boolean invisibility_buff = false;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.10000000149011612D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.10000000149011612D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(5, new ProjectileAttackGoal(this, 1.25D, 20, 10.0F/*, Items.SNOWBALL*/));
        this.targetSelector.add(5, new ActiveTargetGoalItemConditioned(this, MobEntity.class, 10, true, false, (entity) -> {
            return entity instanceof Monster;
        }, Items.SNOWBALL));

    }

    public void attack(LivingEntity target, float pullProgress) {
        if(!can_attack){
            return;
        }
        SnowballEntity snowballEntity = new SnowballEntity(this.world, this);
        double d = target.getEyeY() - 1.100000023841858D;
        double e = target.getX() - this.getX();
        double f = d - snowballEntity.getY();
        double g = target.getZ() - this.getZ();
        double h = Math.sqrt(e * e + g * g) * 0.20000000298023224D;
        snowballEntity.setVelocity(e, f + h, g, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(snowballEntity);
    }

    public void disableBuffs(){
        can_attack = false;
        resistance_buff = false;
        invisibility_buff = false;
        swiftness_buff = false;
        slowfall_buff = false;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ItemStack itemStack2 = this.getStackInHand(Hand.MAIN_HAND);
        if (itemStack2.isEmpty() && !itemStack.isEmpty()) {
            ItemStack itemStack3 = itemStack.copy();
            itemStack3.setCount(1);
            this.setStackInHand(Hand.MAIN_HAND, itemStack3);

            //The buffs section and interactions with items etc
            if(itemStack3.getItem().equals(Items.TORCH)){
                World world = this.getEntityWorld();
                AllayEntity entity = new AllayEntity(EntityType.ALLAY, world);
                entity.setPos(this.getX(), this.getY(), this.getZ());
                this.remove(RemovalReason.KILLED);
                world.spawnEntity(entity);
            }else
            if(itemStack3.getItem().equals(Items.SNOWBALL)){
                can_attack = true;
            }else
            if(itemStack3.getItem().equals(Items.OBSIDIAN)||itemStack3.getItem().equals(Items.CRYING_OBSIDIAN)){
                resistance_buff = true;
            }else
            if(itemStack3.getItem().equals(Items.BLUE_ICE)){
                invisibility_buff = true;
                this.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.AIR));
            }else
            if(itemStack3.getItem().equals(Items.SUGAR)){
                swiftness_buff = true;
            }else
            if(itemStack3.getItem().equals(Items.RABBIT_FOOT)){
                jump_buff = true;
            }else
            if(itemStack3.getItem().equals(Items.FEATHER)){
                slowfall_buff = true;
            }


            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            this.world.playSoundFromEntity(player, this, SoundEvents.ENTITY_ALLAY_ITEM_GIVEN, SoundCategory.NEUTRAL, 2.0F, 1.0F);
            this.getBrain().remember(MemoryModuleType.LIKED_PLAYER, player.getUuid());
            return ActionResult.SUCCESS;
        } else if (!itemStack2.isEmpty() && hand == Hand.MAIN_HAND && itemStack.isEmpty()) {
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            disableBuffs();
            this.world.playSoundFromEntity(player, this, SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.NEUTRAL, 2.0F, 1.0F);
            this.swingHand(Hand.MAIN_HAND);
            Iterator var5 = this.getInventory().clearToList().iterator();

            while(var5.hasNext()) {
                ItemStack itemStack4 = (ItemStack)var5.next();
                LookTargetUtil.give(this, itemStack4, this.getPos());
            }

            this.getBrain().forget(MemoryModuleType.LIKED_PLAYER);
            player.giveItemStack(itemStack2);
            return ActionResult.SUCCESS;
        } else {
            return super.interactMob(player, hand);
        }
    }

    public void tickMovement() {
        if (this.world.isClient) {
            if(this.random.nextInt(3) == 1){
                if(can_attack){
                    this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D, this.getParticleZ(0.5D), 0,0,0);
                }
                this.world.addParticle(ParticleTypes.WITCH, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D, this.getParticleZ(0.5D), 0,0,0);
            }
        }else{
            if(resistance_buff){
                applyEffectIfFriendIsNear(StatusEffects.RESISTANCE, 10, 0, 8);
            }else if(swiftness_buff){
                applyEffectIfFriendIsNear(StatusEffects.SPEED, 10, 0, 8);
            }else if(jump_buff){
                applyEffectIfFriendIsNear(StatusEffects.JUMP_BOOST, 10, 0, 8);
            }else if(slowfall_buff){
                applyEffectIfFriendIsNear(StatusEffects.SLOW_FALLING, 10, 0, 8);
            }else if(invisibility_buff){
                applyEffectIfFriendIsNear(StatusEffects.INVISIBILITY, 30, 0, true, true, 8);
                invisibility_buff = false;
            }
        }

        super.tickMovement();
    }

    /**Applies a status effect if the player who "owns" the allay is near
     *
     * @param effect The status effect which is going to be applied
     * @param duration The duration of the effect in seconds
     * @param amplifier The amplifier of the effect
     * @param max_distance The max distance between the player and the allay
     * */
    //TODO make max distance configurable? With a gamerule maybe
    public void applyEffectIfFriendIsNear(StatusEffect effect, int duration, int amplifier, int max_distance){
        Optional<UUID> player_id = this.getBrain().getOptionalMemory(MemoryModuleType.LIKED_PLAYER);
        if(!player_id.isEmpty()){
            PlayerEntity player = this.getServer().getPlayerManager().getPlayer(player_id.get());
            if(player.distanceTo(this)<=max_distance){
            player.addStatusEffect(
                    new StatusEffectInstance(effect, duration*20, amplifier)
            );
            }
        }
    }

    /**Applies a status effect if the player who "owns" the allay is near
     *
     * @param effect The status effect which is going to be applied
     * @param duration The duration of the effect in seconds
     * @param amplifier The amplifier of the effect
     * @param ambient Same as applyStatusEffect
     * @param visible If the effect should display particles or not
     * @param max_distance The max distance between the player and the allay
     * */
    //TODO make max distance configurable? With a gamerule maybe
    public void applyEffectIfFriendIsNear(StatusEffect effect, int duration, int amplifier, boolean ambient, boolean visible, int max_distance){
        Optional<UUID> player_id = this.getBrain().getOptionalMemory(MemoryModuleType.LIKED_PLAYER);
        if(!player_id.isEmpty()){
            PlayerEntity player = this.getServer().getPlayerManager().getPlayer(player_id.get());
            if(player.hasStatusEffect(effect) && player.getStatusEffect(effect).getDuration() > 5){
                return;
            }
            if(player.distanceTo(this)<=max_distance){
                player.addStatusEffect(
                        new StatusEffectInstance(effect, duration*20, amplifier, ambient, visible)
                );
            }
        }
    }


    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }
}
