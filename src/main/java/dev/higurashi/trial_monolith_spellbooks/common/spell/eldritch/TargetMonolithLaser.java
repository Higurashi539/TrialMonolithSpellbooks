package dev.higurashi.trial_monolith_spellbooks.common.spell.eldritch;

import dev.higurashi.trial_monolith_spellbooks.TrialMonolithSpellbooks;
import io.github.kosianodangoo.trialmonolith.common.entity.SmallBeamEntity;
import io.github.kosianodangoo.trialmonolith.common.helper.EntityHelper;
import io.github.kosianodangoo.trialmonolith.common.init.TrialMonolithEntities;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class TargetMonolithLaser extends AbstractSpell {
    private final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(TrialMonolithSpellbooks.MOD_ID, "target_monolith_laser");
    private final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setAllowCrafting(false)
            .setCooldownSeconds(40)
            .setMaxLevel(3).build();

    public TargetMonolithLaser() {
        this.castTime = 45;
        this.baseManaCost = 1000;
        this.baseSpellPower = 20;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 5;
    }

    @Override public ResourceLocation getSpellResource() { return spellResource; }
    @Override public DefaultConfig getDefaultConfig() { return spellConfig; }

    // === SPELL SETTINGS ===
    @Override public boolean stopSoundOnCancel() { return true; }
    @Override public CastType getCastType() { return CastType.LONG; }
    @Override public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity caster) { return getCastTime(spellLevel); }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        Utils.preCastTargetHelper(level, entity, playerMagicData, this, 42, 1.0f);
        return true;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        Vec3 targetPos;

        if (magicData.getAdditionalCastData() instanceof TargetEntityCastData castData) {
            targetPos = castData.getTargetPosition((ServerLevel) level);


            RandomSource r = level.getRandom();
            int beamCount = 20;

            for (int i = 0; i < beamCount; i++) {

                double theta = r.nextDouble() * Math.PI * 2;
                double phi = Math.acos(2 * r.nextDouble() - 1);

                double radius = 5.0;

                double x = radius * Math.sin(phi) * Math.cos(theta);
                double y = radius * Math.cos(phi);
                double z = radius * Math.sin(phi) * Math.sin(theta);

                Vec3 spawnPos = targetPos.add(x, y, z);

                SmallBeamEntity beam = new SmallBeamEntity(TrialMonolithEntities.SMALL_BEAM.get(), level);
                beam.setOwner(caster);

                if (EntityHelper.isSoulProtected(caster)) {
                    EntityHelper.setSoulProtected(beam, true);
                }

                beam.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

                beam.lookAt(EntityAnchorArgument.Anchor.EYES, targetPos);

                level.addFreshEntity(beam);
            }
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }
}
