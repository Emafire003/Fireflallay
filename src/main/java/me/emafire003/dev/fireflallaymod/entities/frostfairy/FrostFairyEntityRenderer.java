package me.emafire003.dev.fireflallaymod.entities.frostfairy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static me.emafire003.dev.fireflallaymod.FireflallayMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class FrostFairyEntityRenderer extends MobEntityRenderer<FrostFairyEntity, FrostFairyEntityModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/frostfairy/frostfairy.png");

    public FrostFairyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrostFairyEntityModel(context.getPart(EntityModelLayers.ALLAY)), 0.4F);
        this.addFeature(new HeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    public Identifier getTexture(FrostFairyEntity allayEntity) {
        return TEXTURE;
    }

    protected int getBlockLight(FrostFairyEntity allayEntity, BlockPos blockPos) {
        return 15;
    }
}
