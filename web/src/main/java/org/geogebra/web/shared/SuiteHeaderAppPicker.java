package org.geogebra.web.shared;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.web.full.css.MaterialDesignResources;
import org.geogebra.web.full.gui.dialog.AppDescription;
import org.geogebra.web.full.gui.dialog.AppSwitcherPopup;
import org.geogebra.web.full.main.AppWFull;
import org.geogebra.web.html5.gui.util.AriaHelper;
import org.geogebra.web.html5.gui.util.NoDragImage;
import org.geogebra.web.html5.gui.view.button.StandardButton;
import org.geogebra.web.html5.main.AppW;

import com.google.gwt.aria.client.Roles;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SuiteHeaderAppPicker extends StandardButton {

	private final AppW appW;
	private AppSwitcherPopup suitePopup;

	/**
	 * @param app
	 *            - application
	 */
	public SuiteHeaderAppPicker(AppW app) {
		super(app);
		this.appW = app;
		createAppPickerButton(app);
	}

	private void createAppPickerButton(AppW app) {
		setIconAndLabel(GeoGebraConstants.GRAPHING_APPCODE);
		addStyleName("suiteAppPickerButton");
		suitePopup = new AppSwitcherPopup((AppWFull) app, this);
		addFastClickHandler(event -> suitePopup.showPopup());
	}

	/**
	 * @param appCode
	 *            - subapp code
	 */
	public void setIconAndLabel(String appCode) {
		AppDescription description = AppDescription.get(appCode);
		NoDragImage btnImage = new NoDragImage(description.getIcon(), 24, 24);
		btnImage.getElement().setTabIndex(-1);
		String label = description.getNameKey();
		Label btnLabel = new Label(appW.getLocalization().getMenu(label));
		AriaHelper.setAttribute(btnLabel, "data-trans-key", label);

		this.getElement().removeAllChildren();
		this.getElement().appendChild(btnImage.getElement());
		this.getElement().appendChild(btnLabel.getElement());

		NoDragImage secondImg =
				new NoDragImage(MaterialDesignResources.INSTANCE.arrow_drop_down(), 24);
		secondImg.setStyleName("btnSecondIcon");
		this.getElement().appendChild(secondImg.getElement());
		btnImage.setPresentation();

		Roles.getButtonRole().removeAriaPressedState(getElement());
	}

	/**
	 * sets the button visibility depending on overlapping of divs
	 */
	public void checkButtonVisibility() {
		final RootPanel appPickerPanel = RootPanel.get("suiteAppPicker");
		int buttonRight = appPickerPanel.getAbsoluteLeft() + appPickerPanel.getOffsetWidth();
		int buttonsLeft = GlobalHeader.getButtonElement().getAbsoluteLeft();
		final Style style = appPickerPanel.getElement().getStyle();
		if (buttonsLeft < buttonRight) {
			style.setProperty("visibility", "hidden");
		} else {
			style.setProperty("visibility", "visible");
		}
	}
}
