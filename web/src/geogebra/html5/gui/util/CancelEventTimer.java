package geogebra.html5.gui.util;

public class CancelEventTimer {

	private long lastTouchEvent = 0;

	/**
	 * amount of time (ms) in which all mouse events are ignored after a touch
	 * event
	 */
	public static final int TIME_BETWEEN_TOUCH_AND_MOUSE = 500;

	/**
	 * called at the end of any touch event
	 */
	public void touchEventOccured() {
		this.lastTouchEvent = System.currentTimeMillis();
	}

	/**
	 * called at the beginning of a mouse event
	 * 
	 * @return whether the actual mouse event should be canceled
	 */
	public boolean cancelMouseEvent() {
		return System.currentTimeMillis() - lastTouchEvent < TIME_BETWEEN_TOUCH_AND_MOUSE;
	}
}
