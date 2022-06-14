package me.emafire003.dev.fireflallaymod;

import me.emafire003.dev.fireflallaymod.entities.FireflallayEntityRenderer;
import me.emafire003.dev.fireflallaymod.entities.FireflallayEntityModel;
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
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FireflallayMod.FIREFLALLAY, (context) -> {
            return new FireflallayEntityRenderer(context);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_FIREFLALLAY_LAYER, FireflallayEntityModel::getTexturedModelData);
    }
}
