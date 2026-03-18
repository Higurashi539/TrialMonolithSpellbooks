package dev.higurashi.trial_monolith_spellbooks.common.spell.eldritch;

import dev.higurashi.trial_monolith_spellbooks.TrialMonolithSpellbooks;
import dev.higurashi.trial_monolith_spellbooks.common.entity.SummonedTrialMonolith;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class SummonTrialMonolithSpell extends AbstractSpell {
    private final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(TrialMonolithSpellbooks.MOD_ID, "summon_trial_monolith");
    private final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setAllowCrafting(false)
            .setCooldownSeconds(120)
            .setMaxLevel(3).build();

    public SummonTrialMonolithSpell() {
        this.castTime = 30;
        this.baseManaCost = 1000;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 40;
        this.spellPowerPerLevel = 3;
    }

    @Override public ResourceLocation getSpellResource() { return spellResource; }
    @Override public DefaultConfig getDefaultConfig() { return spellConfig; }

    // === SPELL SETTINGS ===
    @Override public CastType getCastType() { return CastType.LONG; }
    @Override public int getRecastCount(int spellLevel, LivingEntity caster) { return 2; }
    @Override public ICastDataSerializable getEmptyCastData() { return new SummonedEntitiesCastData(); }
    @Override public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
        );
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (SummonManager.recastFinishedHelper(serverPlayer, recastInstance, recastResult, castDataSerializable)) {
            super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        var recasts = magicData.getPlayerRecasts();
        if (!recasts.hasRecastForSpell(this)) {
            SummonedEntitiesCastData castData = new SummonedEntitiesCastData();
            int summonTime = 20 * 60 * 10;

            SummonedTrialMonolith monolith = new SummonedTrialMonolith(level, caster);
            monolith.setPos(caster.position());

            level.addFreshEntity(monolith);

            RecastInstance recastInstance = new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, caster), summonTime, source, castData);
            recasts.addRecast(recastInstance, magicData);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }
}
