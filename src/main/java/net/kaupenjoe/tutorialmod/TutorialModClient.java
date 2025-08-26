package net.kaupenjoe.tutorialmod;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.kaupenjoe.tutorialmod.block.ModBlocks;
import net.kaupenjoe.tutorialmod.block.entity.ModBlockEntities;
import net.kaupenjoe.tutorialmod.block.entity.renderer.PedestalBlockEntityRenderer;
import net.kaupenjoe.tutorialmod.entity.ModEntities;
import net.kaupenjoe.tutorialmod.entity.client.*;
import net.kaupenjoe.tutorialmod.particle.ModParticles;
import net.kaupenjoe.tutorialmod.particle.PinkGarnetParticle;
import net.kaupenjoe.tutorialmod.screen.ModScreenHandlers;
import net.kaupenjoe.tutorialmod.screen.custom.GrowthChamberScreen;
import net.kaupenjoe.tutorialmod.screen.custom.PedestalScreen;
import net.kaupenjoe.tutorialmod.util.ModModelPredicates;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class TutorialModClient implements ClientModInitializer {

    public static final ModifierLayer<IAnimation> ATTACK_ANIMATION_LAYER = new ModifierLayer<>();

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_GARNET_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_GARNET_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CAULIFLOWER_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HONEY_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIFTWOOD_SAPLING, RenderLayer.getCutout());
        ModModelPredicates.registerModelPredicates();
        EntityModelLayerRegistry.registerModelLayer(MantisModel.MANTIS, MantisModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MANTIS, MantisRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TomahawkProjectileModel.TOMAHAWK, TomahawkProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TOMAHAWK, TomahawkProjectileRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHAIR, ChairRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PINK_GARNET_PARTICLE, PinkGarnetParticle.Factory::new);
        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BE, PedestalBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);
        HandledScreens.register(ModScreenHandlers.GROWTH_CHAMBER_SCREEN_HANDLER, GrowthChamberScreen::new);
        // --- Kết thúc code cũ ---






        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null) {
                AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(client.player);

                animationStack.addAnimLayer(1000, ATTACK_ANIMATION_LAYER);
            }
        });


    }
}
