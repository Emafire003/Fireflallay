package me.emafire003.dev.fireflallaymod;

import me.emafire003.dev.fireflallaymod.entities.fireflallay.FireflallayEntityRenderer;
import me.emafire003.dev.fireflallaymod.entities.fireflallay.FireflallayEntityModel;
import me.emafire003.dev.fireflallaymod.entities.frostfairy.FrostFairyEntityModel;
import me.emafire003.dev.fireflallaymod.entities.frostfairy.FrostFairyEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static me.emafire003.dev.fireflallaymod.FireflallayMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class FireflallayClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_FIREFLALLAY_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "fireflallay"), "main");
    public static final EntityModelLayer MODEL_FROSTFAIRYENTITY_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "frostfairy"), "main");


    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FireflallayMod.FIREFLALLAY, (context) -> {
            return new FireflallayEntityRenderer(context);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_FIREFLALLAY_LAYER, FireflallayEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(FireflallayMod.FROSTFAIRY, (context) -> {
            return new FrostFairyEntityRenderer(context);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_FROSTFAIRYENTITY_LAYER, FrostFairyEntityModel::getTexturedModelData);

    }
}
