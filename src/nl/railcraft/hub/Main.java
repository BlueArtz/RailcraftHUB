package nl.railcraft.hub;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import nl.railcraft.hub.Events.BPAC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.spigotmc.Metrics;

public class Main
  extends JavaPlugin
  implements Listener
{
  private ArrayList<Player> jumpers = new ArrayList();
  
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    getServer().getPluginManager().registerEvents(new BPAC(), this);

    try
    {
      Metrics metrics = new Metrics();
      metrics.start();
    }
    catch (IOException e)
    {
      System.out.println("failed metrics");
    }
    Bukkit.getLogger().info(ChatColor.GRAY + "RailCraft Hub " + ChatColor.RED + "Enabled!");
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    File config = new File(getDataFolder(), "config.yml");
    if (!config.exists())
    {
      getConfig().options().copyDefaults(true);
      saveConfig();
    }
  }
  
  public String lobbytext = new String(ChatColor.GRAY + "�bRailCraftHub" + ChatColor.GRAY + " �8�m->�a" + ChatColor.GREEN);
  
  public void onDisable() {}
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("rlhreload"))
    {
      Player player = (Player)sender;
      saveConfig();
      reloadConfig();
      player.sendMessage(ChatColor.GRAY + "�bRailCraftHub" + ChatColor.GRAY + " �8�m->�a" + ChatColor.GREEN + "Plugin Reloaded!");
    }
    if ((cmd.getName().equalsIgnoreCase("sethub")) && 
      ((sender instanceof Player))) {
      if (sender.hasPermission("rch.admin"))
      {
        Player p = (Player)sender;
        getConfig().set("x", Double.valueOf(p.getLocation().getX()));
        getConfig().set("y", Double.valueOf(p.getLocation().getY()));
        getConfig().set("z", Double.valueOf(p.getLocation().getZ()));
        getConfig().set("pitch", Float.valueOf(p.getLocation().getPitch()));
        getConfig().set("yaw", Float.valueOf(p.getLocation().getYaw()));
        getConfig().set("world", p.getLocation().getWorld().getName());
        saveConfig();
        p.sendMessage(this.lobbytext + " Hub Set!");
      }
    }
    if (cmd.getName().equalsIgnoreCase("hub")) {
      if (sender.hasPermission("rch.use"))
      {
        Player p = (Player)sender;
        World w = Bukkit.getServer().getWorld(getConfig().getString("world"));
        double x = getConfig().getDouble("x");
        double y = getConfig().getDouble("y");
        double z = getConfig().getDouble("z");
        Location lobby = new Location(w, x, y, z);
        lobby.setPitch((float)getConfig().getDouble("pitch"));
        lobby.setYaw((float)getConfig().getDouble("yaw"));
        p.teleport(lobby);
      }
    }
    return false;
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e)
  {
    if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK)
    {
      e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1));
      e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.7D, e.getPlayer().getVelocity().getZ()));
      this.jumpers.add(e.getPlayer());
    }
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    e.setJoinMessage(ChatColor.GOLD + " ");
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    e.setQuitMessage(ChatColor.GOLD + " ");
  }
  
  @EventHandler
  public void alEntrar(PlayerJoinEvent e)
  {
    World w = Bukkit.getServer().getWorld(getConfig().getString("world"));
    double x = getConfig().getDouble("x");
    double y = getConfig().getDouble("y");
    double z = getConfig().getDouble("z");
    Location lobby = new Location(w, x, y, z);
    lobby.setPitch((float)getConfig().getDouble("pitch"));
    lobby.setYaw((float)getConfig().getDouble("yaw"));
    e.getPlayer().teleport(lobby);
  }
}

