package me.nullable.aspectratio.mixin;

import me.nullable.aspectratio.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Final
    @Shadow
    MinecraftClient client;
    @Shadow
    private float zoom;
    @Shadow
    private float zoomY;
    @Shadow
    private float zoomX;

    @Shadow
    public abstract float getFarPlaneDistance();

    /*
     * By hooking onto only a single
     * call of getBasicProjectionMatrix,
     * we effectively only modify the
     * world view instead of affecting
     * player view model.
     *
     * Hacky but it works
     */
    @Redirect(method = "renderWorld",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;getBasicProjectionMatrix(D)Lorg/joml/Matrix4f;",
                    ordinal = 0)
    )
    private Matrix4f getFirstBasicProjMatrix(GameRenderer instance, double fov) {
        Matrix4f matrix4f = new Matrix4f();
        if (this.zoom != 1.0F) {
            matrix4f.translate(this.zoomX, -this.zoomY, 0.0F);
            matrix4f.scale(this.zoom, this.zoom, 1.0F);
        }
        float height = client.getWindow().getFramebufferHeight();
        float width = client.getWindow().getFramebufferWidth();
        float aspect = (width / height) * Config.HANDLER.instance().multiplier;
        return matrix4f.perspective((float) Math.toRadians(fov), aspect, 0.05F, this.getFarPlaneDistance());
    }
}
