package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class TextLabel {
	
	private final int SHADOW_OFFSET = 2;
	private final int LINE_GAP = 40;
	private final Color DEFAULT_COLOR = Color.WHITE;
	private final Color DEFAULT_SHADOW_COLOR = Color.GRAY;
	
	private String _text;
	private Point _centerPosition;
	private Font _font;
	private Color _color, _shadowColor;
	private Rectangle2D _bounds;
	private int _xOffset, _yOffset;
	private boolean _hasShadow;
	private boolean _isVisible;
	
	public TextLabel(String text, Point centerPosition, Font font) {
		this._text = text;
		this._centerPosition = centerPosition;
		this._font = font;
		this._color = DEFAULT_COLOR;
		this._shadowColor = DEFAULT_SHADOW_COLOR;
		this._hasShadow = true;
		this._isVisible = true;
		this._bounds = null;
	}
	
	public void setText(String text) {
		this._text = text;
	}
	
	public void setShadow(boolean hasShadow) {
		this._hasShadow = hasShadow;
	}
	
	public void setVisible(boolean isVisible) {
		this._isVisible = isVisible;
	}
	
	public void setColor(Color color) {
		this._color = color;
	}
	
	public void setShadowColor(Color color) {
		this._shadowColor = color;
	}
	
	public void setPosition(Point centerPosition) {
		this._centerPosition = centerPosition;
	}
	
	public String getText() {
		return this._text;
	}
	
	public boolean isVisible() {
		return this._isVisible;
	}
	
	public boolean isMouseOnLabel(Point mouse) {
		if (this._bounds == null) {
			return false;
		}
		if ((mouse.x > this._centerPosition.x - this._xOffset && mouse.x < this._centerPosition.x + this._xOffset) && 
			(mouse.y > this._centerPosition.y - this._yOffset && mouse.y < this._centerPosition.y + this._yOffset)) {
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g2, boolean asButton) {
		if (!this._isVisible) {
			return;
		}
		g2.setFont(this._font);
		this._bounds = g2.getFontMetrics().getStringBounds(this._text, g2);
		this._xOffset = (int)this._bounds.getWidth() / 2;
		this._yOffset = (int)this._bounds.getHeight() / 2;
		int x = this._centerPosition.x - this._xOffset;
		int y = this._centerPosition.y + this._yOffset - g2.getFontMetrics().getDescent();
		if (this._hasShadow) {
			g2.setColor(this._shadowColor);
			g2.drawString(this._text, x + SHADOW_OFFSET, y + SHADOW_OFFSET);
			g2.setColor(this._color);
			g2.drawString(this._text, x, y);
		} else {
			g2.setColor(this._color);
			if (asButton) {
				g2.drawString(this._text, x + SHADOW_OFFSET, y + SHADOW_OFFSET);
			} else {
				g2.drawString(this._text, x, y);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		this.draw(g2, false);
	}
	
	public void drawMulti(Graphics2D g2) {
		g2.setFont(this._font);
		g2.setColor(this._color);
		int x = this._centerPosition.x;
		int y = this._centerPosition.y;
		for (String line : this._text.split("\n")) {
			g2.drawString(line, x, y);
			y += LINE_GAP;
		}
	}
}
