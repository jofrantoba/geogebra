package org.geogebra.web.shared.view;

import org.geogebra.common.euclidian.event.PointerEventType;
import org.geogebra.common.gui.view.ActionView;
import org.geogebra.common.main.App;
import org.geogebra.web.html5.gui.util.AriaHelper;
import org.geogebra.web.html5.gui.util.ClickStartHandler;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * A view element that can perform an action.
 */
public class Actionable implements ActionView {

	private App app;
	private RootPanel view;

	/**
	 * @param app The app.
	 * @param view The wrapped view.
	 */
	public Actionable(App app, RootPanel view) {
		this.app = app;
		this.view = view;
	}

	@Override
	public void setAction(final Runnable action) {
		if (action != null) {
			ClickStartHandler.init(
					view,
					new ClickStartHandler(true, true) {

				@Override
				public void onClickStart(int x, int y, PointerEventType type) {
					action.run();
				}
			});
		}
	}

	@Override
	public void setEnabled(boolean enabled) {

	}

	/**
	 * Sets the title for the view with the localization key.
	 * @param titleLocalizationKey The localization key for the title.
	 */
	public void setTitle(String titleLocalizationKey) {
		AriaHelper.setTitle(view, app.getLocalization().getMenu(titleLocalizationKey), app);
	}
}
