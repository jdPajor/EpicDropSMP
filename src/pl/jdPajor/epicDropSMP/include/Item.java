package pl.jdPajor.epicDropSMP.include;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Item {
    private ItemStack is;
    
    public Item( Material m) {
        this(m, 1);
    }
    
    public Item( Material m,  int ilosc,  short durability) {
        this.is = new ItemStack(m, ilosc, durability);
    }
    
    public Item( Material m,  int ilosc) {
        this.is = new ItemStack(m, ilosc);
    }
    
    public Item setAmount( int i) {
        this.is.setAmount(i);
        return this;
    }
    
    public Item setDurability( short durability) {
        this.is.setDurability(durability);
        return this;
    }
    
    public Item setName( String name) {
    	ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name);
        this.is.setItemMeta(im);
        return this;
    }
    
    public Item setSkullOwner( String owner) {
        try {
            SkullMeta im = (SkullMeta)this.is.getItemMeta();
            im.setOwner(owner);
            this.is.setItemMeta((ItemMeta)im);
        }
        catch (Exception ex) {}
        return this;
    }
    
    public Item( ItemStack is) {
        this.is = is;
    }
    
    public Item clone() {
        return new Item(this.is);
    }
    
    public Item setLore( String lore) {
         ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        return this;
    }
    
    public Item addLore( String lore) {
         ItemMeta im = this.is.getItemMeta();
        List<String> line = new ArrayList<String>();
        if (im.hasLore()) {
            line = new ArrayList<String>(im.getLore());
        }
        line.add(lore);
        im.setLore(line);
        this.is.setItemMeta(im);
        return this;
    }
    
    public Item addEnchant( Enchantment ench,  int lvl) {
        this.is.addUnsafeEnchantment(ench, lvl);
        return this;
    }
    
    public Item setColor(Color c) {
    	LeatherArmorMeta m = (LeatherArmorMeta) this.is.getItemMeta();
    	m.setColor(c);
    	this.is.setItemMeta(m);
    	return this;
    }
    
    public Item removeEnchant( Enchantment ench) {
        if (this.is.containsEnchantment(ench)) {
            this.is.removeEnchantment(ench);
        }
        return this;
    }
    
    public Item setAttribute(Attribute at, String s, double value, EquipmentSlot slot) {
    	ItemMeta im = this.is.getItemMeta();
    	im.removeAttributeModifier(at);
    	im.addAttributeModifier(at, new AttributeModifier(UUID.randomUUID(), s, value, AttributeModifier.Operation.ADD_NUMBER, slot));
    	this.is.setItemMeta(im);
    	return this;
    }
    
    public Item setCustomModelData(int i) {
    	ItemMeta im = this.is.getItemMeta();
    	im.setCustomModelData(i);
    	this.is.setItemMeta(im);
    	return this;
    }
    
    public ItemStack toIs() {
        return this.is;
    }
}
