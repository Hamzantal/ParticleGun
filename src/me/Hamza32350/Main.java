package me.Hamza32350;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void StickEffect(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!p.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.STICK))) {
            return;
        }

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location l = p.getEyeLocation();

            for (double i = 0; i < 70; i += .2){
                makeCircle(p, l, i, 2);
            }

//            new BukkitRunnable() {
//                double i = 1;
//                double radius = 10.0;
//                boolean boo;
//                public void run() {
//                    i += .2;
//                    boo = makeCircle(p, l, i, radius);
//                    if(boo){
//                        this.cancel();
//                        return;
//                    }
//                    radius = 10.0 / i;
//                    i += .2;
//                    boo = makeCircle(p, l, i, radius);
//                    if (boo) {
//                        this.cancel();
//                        return;
//                    }
//                    radius = 10.0 / i;
//                    i += .2;
//                    boo = makeCircle(p, l, i, radius);
//                    if (boo) {
//                        this.cancel();
//                        return;
//                    }
//                    radius = 10.0 / i;
//                    i += .2;
//                    boo = makeCircle(p, l, i, radius);
//                    if (boo) {
//                        this.cancel();
//                        return;
//                    }
//                    radius = 10.0 / i;
//                    if(i > 300){
//                        this.cancel();
//                        return;
//                    }
//                }
//            }.runTaskTimer(this, 0, 1);

        }
    }



     private boolean makeCircle(Player p, Location l, double i, double radius) {
        double c2 = .5;
        p.sendMessage("BOOM!");
        Location lol = l.clone().add(l.getDirection().multiply(i));
        Vector vector = l.getDirection().multiply(i).multiply(c2);
        Vector aVector = new Vector(1, 1, -(vector.getX() + vector.getY()) / vector.getZ()).normalize();
        Vector bVector = vector.crossProduct(aVector).normalize();

//            Location loc = l.clone().add(vector); // center of the circle location
        Location l1 = lol.clone().add( // making the circle around the centre
                aVector.clone().multiply(Math.cos(c2 * i) * radius)
                        .add(bVector.clone().multiply(Math.sin(c2 * i) * radius)
                        ));
        Location l2 = lol.clone().add( // making the circle around the centre
                aVector.multiply(Math.cos(c2 * i + Math.PI) * radius)
                        .add(bVector.multiply(Math.sin(c2 * i + Math.PI) * radius)
                        ));


        PacketPlayOutWorldParticles pp = new PacketPlayOutWorldParticles(
                EnumParticle.FLAME,
                true,
                (float) l1.getX(),
                (float) l1.getY(),
                (float) l1.getZ(),
                0,
                0,
                0,
                1,
                0);

        PacketPlayOutWorldParticles p1 = new PacketPlayOutWorldParticles(
                EnumParticle.PORTAL,
                true,
                (float) lol.getX(),
                (float) lol.getY(),
                (float) lol.getZ(),
                0,
                0,
                0,
                1,
                0);
        PacketPlayOutWorldParticles poop = new PacketPlayOutWorldParticles(
                EnumParticle.FLAME,
                true,
                (float) l2.getX(),
                (float) l2.getY(),
                (float) l2.getZ(),
                0,
                0,
                0,
                1,
                0);

        for(Player pl: Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(pp);
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(p1);
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(poop);
        }

        if(lol.getBlock().getType().isSolid()){
            lol.getWorld().createExplosion(lol, 5.0f);
            return true;
        }
        return false;
    }

    public void spawnParticle(){
    }


}



