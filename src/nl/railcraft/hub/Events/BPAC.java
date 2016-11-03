package nl.railcraft.hub.Events;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BPAC implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		event.setCancelled(true);
		if (player.hasPermission("rch.admin")) {
			event.setCancelled(false);
		}
	}

	@EventHandler
	public void onLluvia(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void Crafting(CraftItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void Craft(PrepareItemCraftEvent e) {
	}

	@EventHandler
	public void onHungerDeplete(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		e.setFoodLevel(20);
	}

	@EventHandler
	public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
		if ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)
				|| (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		event.setCancelled(true);
		event.getItemDrop().getItemStack().getType();
	}

	@EventHandler
	public void onRide(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if ((p.hasPermission("rch.ride")) && ((e.getRightClicked() instanceof Player))) {
			e.getRightClicked().setPassenger(p);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ItemStack itemspeed = new ItemStack(Material.SULPHUR);
		ItemMeta itemspeedmeta = itemspeed.getItemMeta();
		itemspeedmeta.setDisplayName("§9Speed -> §cOff");
		itemspeed.setItemMeta(itemspeedmeta);

		ItemStack itemspeed4 = new ItemStack(Material.COMPASS);
		ItemMeta itemspeedmeta4 = itemspeed.getItemMeta();
		itemspeedmeta4.setDisplayName("§9Server §rSelector");
		itemspeed4.setItemMeta(itemspeedmeta4);

		ItemStack itemspeed5 = new ItemStack(Material.PAPER);
		ItemMeta itemspeedmeta5 = itemspeed.getItemMeta();
		itemspeedmeta4.setDisplayName("§9Network information");
		itemspeed4.setItemMeta(itemspeedmeta4);

		e.getPlayer().getInventory().setItem(5, itemspeed);
		e.getPlayer().getInventory().setItem(1, itemspeed4);
		e.getPlayer().getInventory().setItem(7, itemspeed5);

	}

	@EventHandler
	  public void onclickeven1t(PlayerInteractEvent e)
	  {
	    Player p = e.getPlayer();
	    if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
	      (p.getItemInHand().getType().equals(Material.PAPER))) {
	    	p.sendMessage(" ");
	    	p.sendMessage("§9§lWEBSITE §rrailcraft.nl");
	    	p.sendMessage("§9§lDONATE §rstore.railcraft.nl");
	    	p.sendMessage("§9§lYOUTUBE §rstore.railcraft.nl");
	    	p.sendMessage(" ");
	    	p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
	    } }

	@EventHandler
	public void onclickevent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (p.getItemInHand().getType().equals(Material.SULPHUR))) {
			if (e.getPlayer().getItemInHand().getType().equals(Material.SULPHUR)) {
				ItemStack itemspeed1 = new ItemStack(Material.SUGAR);
				ItemMeta itemspeed1meta = itemspeed1.getItemMeta();
				itemspeed1meta.setDisplayName("§9Speed §r-> §aOn");
				itemspeed1.setItemMeta(itemspeed1meta);
				e.getPlayer().getInventory().setItem(5, itemspeed1);
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

				e.getPlayer().getInventory().remove(Material.SULPHUR);
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 9));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3));
			}
		} else if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (p.getItemInHand().getType().equals(Material.SUGAR))) {
			e.getPlayer().getInventory().remove(Material.SUGAR);
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			ItemStack itemspeed2 = new ItemStack(Material.SULPHUR);
			ItemMeta itemspeed2meta = itemspeed2.getItemMeta();
			itemspeed2meta.setDisplayName("§9Speed §r-> §cOff");
			itemspeed2.setItemMeta(itemspeed2meta);
			e.getPlayer().getInventory().setItem(5, itemspeed2);
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
		}
	}
}
