package me.emafire003.dev.fireflallaymod;

import me.emafire003.dev.fireflallaymod.entities.FireflallayEntity;
import me.emafire003.dev.fireflallaymod.items.AllaySkin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.sensor.FrogAttackablesSensor;
import net.minecraft.entity.ai.brain.task.FrogEatEntityTask;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireflallayMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "fireflallaymod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<FireflallayEntity> FIREFLALLAY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "fireflallay"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FireflallayEntity::new).dimensions(EntityDimensions.fixed(0.37f, 0.6f)).build()
    );

    public static final Item FIREFLALLAY_SPAWN_EGG = new SpawnEggItem(FIREFLALLAY, 15239978, 15255082, new Item.Settings().group(ItemGroup.MISC));
    public static final Item ALLAY_SKIN = registerItem("allay_skin",
            new AllaySkin(new FabricItemSettings().rarity(Rarity.RARE)
                    .food(new FoodComponent.Builder().alwaysEdible().hunger(2).build()).maxCount(16).group(ItemGroup.FOOD)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        FabricDefaultAttributeRegistry.register(FIREFLALLAY, FireflallayEntity.createAttributes());
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fireflallay_spawn_egg"), FIREFLALLAY_SPAWN_EGG);
    }
}