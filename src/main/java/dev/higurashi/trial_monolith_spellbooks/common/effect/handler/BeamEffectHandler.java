package dev.higurashi.trial_monolith_spellbooks.common.effect.handler;

import dev.higurashi.trial_monolith_spellbooks.registry.TMSEffectRegistry;
import io.github.kosianodangoo.trialmonolith.common.entity.AbstractDelayedTraceableEntity;
import io.github.kosianodangoo.trialmonolith.common.entity.SmallBeamEntity;
import io.github.kosianodangoo.trialmonolith.common.helper.EntityHelper;
import io.github.kosianodangoo.trialmonolith.common.init.TrialMonolithEntities;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BeamEffectHandler {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (level.isClientSide) return;

        if (entity.hasEffect(TMSEffectRegistry.BEAM.get())) {
            int interval = 2;

            if (entity.tickCount % interval == 0) {
                shootAutoBeam(level, entity);
            }
        }
    }

    private static void shootAutoBeam(@NotNull Level level, @NotNull LivingEntity living) {
        RandomSource r = level.getRandom();
        int count = 1 + r.nextInt(2);

        for (int i = 0; i < count; i++) {
            Vec3 eyePos = living.getEyePosition();
            Vec3 look = living.getViewVector(0).normalize();

            Vec3 back = look.scale(-1);

            Vec3 circleCenter = living.position().add(back.scale(2.0));

            double radius = 5.0;
            double angle = r.nextDouble() * Math.PI * 2;
            double dist = Math.sqrt(r.nextDouble()) * radius;

            double offsetX = Math.cos(angle) * dist;
            double offsetZ = Math.sin(angle) * dist;

            Vec3 spawnPos = circleCenter.add(offsetX, living.getBbHeight() * 0.5, offsetZ);
            Vec3 dirToSpawn = spawnPos.subtract(living.position()).normalize();

            if (dirToSpawn.dot(look) > 0) spawnPos = living.position().add(dirToSpawn.scale(-dist)).add(0, living.getBbHeight() * 0.5, 0);

            Vec3 to = eyePos.add(look.scale(128));
            HitResult hit = level.clip(new ClipContext(eyePos, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, living));

            Vec3 target = hit.getLocation();

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(level, living, eyePos, to, new AABB(eyePos, to), (entity -> !(entity instanceof AbstractDelayedTraceableEntity traceable && living == traceable.getOwner()) && !(entity instanceof Projectile projectile && living == projectile.getOwner()) && !(entity instanceof ItemEntity)));

            if (entityHit != null) target = entityHit.getEntity().getBoundingBox().getCenter();

            SmallBeamEntity beam = new SmallBeamEntity(TrialMonolithEntities.SMALL_BEAM.get(), level);
            beam.setOwner(living);

            if (EntityHelper.isSoulProtected(living)) EntityHelper.setSoulProtected(beam, true);

            beam.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

            beam.lookAt(EntityAnchorArgument.Anchor.EYES, target);

            level.addFreshEntity(beam);
        }
    }

}
