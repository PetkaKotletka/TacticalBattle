package main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
	public Point mousePosition;
	public Point lastClickPosition;
	public boolean isClicked;
	
	public MouseHandler() {
		this.mousePosition = new Point(0, 0);
		this.lastClickPosition = new Point(0, 0);
		this.isClicked = false;
	}
	
	public void mouseClicked(MouseEvent e) {
		this.lastClickPosition = e.getPoint();
		this.isClicked = true;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		this.mousePosition = e.getPoint();
	}

}
