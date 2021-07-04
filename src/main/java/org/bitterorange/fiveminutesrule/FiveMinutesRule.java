package org.bitterorange.fiveminutesrule;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class FiveMinutesRule extends JavaPlugin
{
    public static final File PluginRoot = new File("./plugins/FiveMinutesRule");
    public static JavaPlugin ThisPlugin;
    public static Logger ThisLogger;
    public static BukkitScheduler MainScheduler;
    public static final FiveMinutesRuleListener MainListener = new FiveMinutesRuleListener();

    public static Map<String, Boolean> IsActive = new HashMap<>();
    private static boolean IsRunning = false;

    @Override
    public void onEnable()
    {
        ThisPlugin = this;
        ThisLogger = getLogger();
        MainScheduler = Bukkit.getScheduler();
        getServer().getPluginManager().registerEvents(MainListener, this);
        PluginCommand pluginCommand = getCommand("fmr");
        pluginCommand.setExecutor(new FiveMinutesRuleCommand());
        pluginCommand.setTabCompleter(new FiveMinutesRuleTabCompleter());

        IsRunning = true;
        new Thread(() ->
        {
            while (IsRunning)
            {
                IsActive.forEach((s, b) ->
                {
                    Player player = Bukkit.getPlayer(s);
                    if (b)
                    {
                        MainScheduler.runTask(ThisPlugin, () ->
                        {
                            List<Entity> entities = player.getNearbyEntities(3.0, 3.0, 3.0);
                            entities.forEach(entity ->
                            {
                                if (entity instanceof LivingEntity)
                                {
                                    new PotionEffect(PotionEffectType.LEVITATION, 10, 1).apply((LivingEntity) entity);
                                }
                            });
                        });
                    }
                });
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDisable()
    {
        //Fired when the server stops and disables all plugins
        IsRunning = false;
    }

    public static String readAllText(File file) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(s);
            stringBuilder.append('\n');
        }
        bufferedReader.close();
        if (stringBuilder.length() > 0)
        {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}