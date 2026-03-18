package dev.higurashi.trial_monolith_spellbooks.common.entity;

import dev.higurashi.trial_monolith_spellbooks.registry.TMSEntityRegistry;
import io.github.kosianodangoo.trialmonolith.common.entity.trialmonolith.TrialMonolithEntity;
import io.github.kosianodangoo.trialmonolith.common.entity.trialmonolith.ai.*;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SummonedTrialMonolith extends TrialMonolithEntity implements IMagicSummon {
    public SummonedTrialMonolith(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SummonedTrialMonolith(Level pLevel, LivingEntity summoner) {
        super(TMSEntityRegistry.SUMMONED_TRIAL_MONOLITH.get(), pLevel);
        SummonManager.setOwner(this, summoner);
    }

    @Override
    public Entity getSummoner() {
        return IMagicSummon.super.getSummoner();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(4, new ShootHugeBeamGoal(this));
        this.goalSelector.addGoal(3, new SummonDamageCubeGoal(this));
        this.goalSelector.addGoal(2, new ShootSmallBeamAroundTargetGoal(this));
        this.goalSelector.addGoal(1, new ShootRandomSmallBeamGoal(this));
        this.goalSelector.addGoal(0, new ShootSmallBeamGoal(this));

        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericProtectOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(5, new GenericHurtByTargetGoal(this, entity -> entity == getSummoner()));
    }

    @Override
    public boolean isAlliedTo(@NotNull Entity target) {
        Entity owner = getSummoner();
        return owner == target || this.isAlliedHelper(target);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (shouldIgnoreDamage(source)) return false;
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        this.onDeathHelper();
        super.die(source);
    }

    @Override
    public void onUnSummon() {
        if (!this.level().isClientSide) {
            MagicManager.spawnParticles(this.level(), ParticleTypes.POOF, getX(), getY(), getZ(), 25, 0.4, 0.8, 0.4, 0.03, false);
            this.discard();
        }
    }
}
