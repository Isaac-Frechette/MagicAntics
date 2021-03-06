package net.undergroundantics.magicantics.spells;

import net.undergroundantics.magicantics.plugin.Spell;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Inferno implements Spell {

    private static final String NAME = "Inferno";
    private static final String DISPLAY_NAME = ChatColor.DARK_RED + NAME;
    private static final long COOLDOWN = 30;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public long getCooldown() {
        return COOLDOWN;
    }

    @Override
    public boolean isLearnable() {
        return true;
    }

    @Override
    public boolean cast(Player p) {
        boolean castSuccess = false;
        List<Entity> localMobsInferno = new LinkedList();
        for (Entity ent : p.getNearbyEntities(6, 2, 6)) {
            if (ent instanceof LivingEntity) {
                localMobsInferno.add((LivingEntity) ent);
            }
        }
        p.getWorld().spawnParticle(Particle.FLAME, p.getEyeLocation(), 30, 4, 1, 4, 0.2);
        p.getWorld().spawnParticle(Particle.LAVA, p.getEyeLocation(), 8, 2, 0.5, 2, 0.1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 0.5f, 0.0f);
        if (localMobsInferno.isEmpty()) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SILVERFISH_DEATH, SoundCategory.PLAYERS, 0.5f, 2.0f);
        }
        else{
            castSuccess = true;
        }
        for (Entity mob : localMobsInferno) {
            if (mob instanceof LivingEntity && p.hasLineOfSight(mob)) {
                mob.setFireTicks(160);
            }
        }
        return castSuccess;
    }

}
