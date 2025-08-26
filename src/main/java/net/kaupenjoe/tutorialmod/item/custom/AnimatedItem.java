package net.kaupenjoe.tutorialmod.item.custom;

import net.kaupenjoe.tutorialmod.item.client.AnimatedItemRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

import java.util.function.Consumer;

public class AnimatedItem extends Item implements GeoItem {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);



    public AnimatedItem(Settings settings) {
        super(settings);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));


    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;



    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
       consumer.accept(new GeoRenderProvider() {


        private final AnimatedItemRenderer renderer = new AnimatedItemRenderer();
          @Override
          public @Nullable BuiltinModelItemRenderer getGeoItemRenderer() {

             return this.renderer;

         }
       });


        GeoItem.super.createGeoRenderer(consumer);
    }



    @Override
    public Object getRenderProvider() {
        return GeoItem.super.getRenderProvider();
    }
}
