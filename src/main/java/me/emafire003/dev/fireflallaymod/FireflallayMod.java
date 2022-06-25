package me.emafire003.dev.fireflallaymod;

import me.emafire003.dev.fireflallaymod.entities.fireflallay.FireflallayEntity;
import me.emafire003.dev.fireflallaymod.entities.frostfairy.FrostFairyEntity;
import me.emafire003.dev.fireflallaymod.items.AllaySkin;
import me.emafire003.dev.fireflallaymod.items.ColdAllaySkin;
import me.emafire003.dev.fireflallaymod.items.FireflallaySkin;
import me.emafire003.dev.fireflallaymod.items.FrostfairySpawnTotem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
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

    public static final EntityType<FrostFairyEntity> FROSTFAIRY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "frostfairy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FrostFairyEntity::new).dimensions(EntityDimensions.fixed(0.37f, 0.6f)).build()
    );

    public static final Item FIREFLALLAY_SPAWN_EGG = new SpawnEggItem(FIREFLALLAY, 15239976, 15255082, new Item.Settings().group(ItemGroup.MISC));

    public static final Item FROSTFAIRY_SPAWN_TOTEM = new FrostfairySpawnTotem(FROSTFAIRY, 12895428, 11382189, new Item.Settings().rarity(Rarity.RARE).group(ItemGroup.MISC));
    public static final Item ALLAY_RESPAWN_TOTEM = new SpawnEggItem(EntityType.ALLAY, 12895428, 11382189, new Item.Settings().group(ItemGroup.MISC));
    public static final Item FIREFLALLAY_RESPAWN_TOTEM  = new SpawnEggItem(FIREFLALLAY, 12895428, 11382189, new Item.Settings().group(ItemGroup.MISC));

    public static final Item ALLAY_SKIN = registerItem("allay_skin",
            new AllaySkin(new FabricItemSettings().rarity(Rarity.RARE)
                    .food(new FoodComponent.Builder().alwaysEdible().hunger(2).build()).maxCount(16).group(ItemGroup.FOOD)));

    public static final Item FIREFLALLAY_SKIN = registerItem("fireflallay_skin",
            new FireflallaySkin(new FabricItemSettings().rarity(Rarity.RARE)
                    .food(new FoodComponent.Builder().alwaysEdible().hunger(2).build()).maxCount(16).group(ItemGroup.FOOD)));

    public static final Item COLD_ALLAY_SKIN = registerItem("cold_allay_skin",
            new ColdAllaySkin(new FabricItemSettings().rarity(Rarity.RARE)
                    .food(new FoodComponent.Builder().alwaysEdible().hunger(2).build()).maxCount(16).group(ItemGroup.FOOD)));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Registering entities and items for " + MOD_ID);
        FabricDefaultAttributeRegistry.register(FIREFLALLAY, FireflallayEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(FROSTFAIRY, FrostFairyEntity.createAttributes());
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fireflallay_spawn_egg"), FIREFLALLAY_SPAWN_EGG);

        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "frostfairy_spawn_totem"), FROSTFAIRY_SPAWN_TOTEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "allay_respawn_totem"), ALLAY_RESPAWN_TOTEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fireflallay_respawn_totem"), FIREFLALLAY_RESPAWN_TOTEM);

        LOGGER.info("Done!");

    }
}