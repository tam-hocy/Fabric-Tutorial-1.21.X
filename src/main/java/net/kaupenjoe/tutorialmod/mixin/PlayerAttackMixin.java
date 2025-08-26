package net.kaupenjoe.tutorialmod.mixin;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.kaupenjoe.tutorialmod.TutorialModClient; // Import class client của bạn
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class PlayerAttackMixin {

    // Không cần biến animationLayer ở đây nữa

    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        // Chỉ chạy animation cho người chơi hiện tại
        if (client.player != null && player == client.player) {
            // Lấy animation từ Registry. API đã thay đổi, giờ nó trả về IPlayable.
            var playableAnimation = PlayerAnimationRegistry.getAnimation(Identifier.of("tutorialmod", "chao"));

            // Kiểm tra xem animation có tồn tại và có phải là dạng KeyframeAnimation không
            if (playableAnimation instanceof KeyframeAnimation keyframeAnimation) {
                // TutorialModClient.ATTACK_ANIMATION_LAYER là layer tĩnh ta đã tạo
                // Tạo một KeyframeAnimationPlayer mới để animation có thể chạy lại từ đầu mỗi lần tấn công
                TutorialModClient.ATTACK_ANIMATION_LAYER.setAnimation(new KeyframeAnimationPlayer(keyframeAnimation));
            }
        }
    }

    // Xóa phương thức loadAnimation() đi, không cần nữa
}
