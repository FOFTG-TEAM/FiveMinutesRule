package org.bitterorange.fiveminutesrule;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;


public class FiveMinutesRule extends JavaPlugin
{
    public static final File PluginRoot = new File("./plugins/FiveMinutesRule");
    public static JavaPlugin ThisPlugin;
    public static Logger ThisLogger;
    public static BukkitScheduler MainScheduler;
    public static final FiveMinutesRuleListener MainListener = new FiveMinutesRuleListener();

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
    }

    @Override
    public void onDisable()
    {
        //Fired when the server stops and disables all plugins
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