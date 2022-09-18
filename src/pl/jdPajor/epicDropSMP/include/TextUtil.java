package pl.jdPajor.epicDropSMP.include;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextUtil {
private TextComponent comp;
	
	public TextUtil(String s) {
		this.comp = new TextComponent(Colors.fix(s));
	}
	
	public TextUtil setClickEvent(ClickEvent ev) {
		comp.setClickEvent(ev);
		return this;
	}
	
	public TextUtil setHoverEvent(HoverEvent ev) {
		comp.setHoverEvent(ev);
		return this;
	}
	
	public TextComponent toTextComponent() {
		return this.comp;
	}
	
	public static TextComponent[] toBookPage(TextComponent... c) {
		List<TextComponent> xt = new ArrayList<TextComponent>();
		for (TextComponent t : c) {
			if (xt.size() > 11) break;
			xt.add(t);
		}
		return xt.toArray(new TextComponent[0]);
	}

}
