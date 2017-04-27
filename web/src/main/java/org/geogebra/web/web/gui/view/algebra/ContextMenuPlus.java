package org.geogebra.web.web.gui.view.algebra;

import org.geogebra.common.awt.GPoint;
import org.geogebra.common.gui.SetLabels;
import org.geogebra.common.main.Localization;
import org.geogebra.common.util.debug.Log;
import org.geogebra.keyboard.web.TabbedKeyboard;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.web.css.GuiResources;
import org.geogebra.web.web.gui.GuiManagerW;
import org.geogebra.web.web.gui.applet.GeoGebraFrameBoth;
import org.geogebra.web.web.gui.menubar.MainMenu;
import org.geogebra.web.web.gui.util.VirtualKeyboardGUI;
import org.geogebra.web.web.javax.swing.GPopupMenuW;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.himamis.retex.editor.share.event.KeyEvent;
import com.himamis.retex.editor.web.MathFieldW;

public class ContextMenuPlus implements SetLabels {
	protected GPopupMenuW wrappedPopup;
	protected Localization loc;
	private AppW app;
	private RadioTreeItem item;
	private MathFieldW mf;
	private TabbedKeyboard kbd;
	/**
	 * Creates new context menu
	 * 
	 * @param item
	 *            application
	 */
	ContextMenuPlus(RadioTreeItem item) {
		app = item.getApplication();
		loc = app.getLocalization();
		this.item = item;
		mf = ((LatexTreeItemController)item.getController()).getRetexListener().getMathField();
		kbd = (TabbedKeyboard)((GuiManagerW)app.getGuiManager()).getOnScreenKeyboard(item, null);
		wrappedPopup = new GPopupMenuW(app);
		wrappedPopup.getPopupPanel().addStyleName("mioMenu");
		buildGUI();
	}

	private void buildGUI() {
		wrappedPopup.clearItems();
		addExpressionItem();
		addTextItem();
		addImageItem();
		addHelpItem();
	}
	
	private void addExpressionItem() {
		MenuItem mi = new MenuItem(loc.getPlain("NewExpression"),
				new Command() {
					
					@Override
					public void execute() {
						item.setText(" ");
						mf.getKeyListener().onKeyPressed(new KeyEvent(KeyEvent.VK_LEFT));
						kbd.selectNumbers();
					}
				});

		// mi.addStyleName("no_image");
		wrappedPopup.addItem(mi);
	}

	private void addTextItem() {
		MenuItem mi = new MenuItem(loc.getPlain("Text"),
				new Command() {
					
					@Override
					public void execute() {
						item.setText("\"\"");
						mf.getKeyListener().onKeyPressed(new KeyEvent(KeyEvent.VK_LEFT));
						kbd.selectAbc();
					}
				});

		// mi.addStyleName("no_image");
		wrappedPopup.addItem(mi);
	}
	
	private void addImageItem() {
		MenuItem mi = new MenuItem(loc.getPlain("Image"),
				new Command() {
					
					@Override
					public void execute() {
						// TODO Auto-generated method stub
						
					}
				});

		// mi.addStyleName("no_image");
		wrappedPopup.addItem(mi);
	}

	private void addHelpItem() {
		String img = GuiResources.INSTANCE.icon_help().getSafeUri()
		.asString();
		MenuItem mi = new MenuItem(MainMenu.getMenuBarHtml(img, loc.getMenu("Help"), true),
				true, new Command() {
					
					@Override
					public void execute() {
						// TODO Auto-generated method stub
						
					}
				});

		// mi.addStyleName("image");
		wrappedPopup.addItem(mi);
	}
	public void show(GPoint p) {
		wrappedPopup.show(p);
	}

	public void show(int x, int y) {
		wrappedPopup.show(new GPoint(x, y));
	}

	@Override
	public void setLabels() {
		buildGUI();
	}
}
