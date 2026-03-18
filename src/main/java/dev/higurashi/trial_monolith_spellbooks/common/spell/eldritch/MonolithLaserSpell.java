package dev.higurashi.trial_monolith_spellbooks.common.spell.blood;

import dev.higurashi.trial_monolith_spellbooks.TrialMonolithSpellbooks;
import io.github.kosianodangoo.trialmonolith.common.entity.HugeBeamEntity;
import io.github.kosianodangoo.trialmonolith.common.init.TrialMonolithEntities;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MonolithLaserSpell extends AbstractSpell {
    private final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(TrialMonolithSpellbooks.MOD_ID, "monolith_laser");
    private final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setAllowCrafting(false)
            .setCooldownSeconds(40)
            .setMaxLevel(3).build();

    public MonolithLaserSpell() {
        this.castTime = 45;
        this.baseManaCost = 300;
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
    @Override public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(

        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        HugeBeamEntity beam = new HugeBeamEntity(TrialMonolithEntities.HUGE_BEAM.get(), level);
        beam.setOwner(caster);
        beam.setPos(caster.getEyePosition().add(0, -0.15, 0).add(caster.getLookAngle().scale(1)));
        beam.setXRot(caster.getXRot());
        beam.setYRot(caster.getYRot());

        level.addFreshEntity(beam);

        super.onCast(level, spellLevel, caster, source, magicData);
    }
}
