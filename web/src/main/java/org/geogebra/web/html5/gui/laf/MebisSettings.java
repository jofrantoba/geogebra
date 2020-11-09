package org.geogebra.web.html5.gui.laf;

import java.util.Arrays;
import java.util.List;

import org.geogebra.common.awt.GColor;
import org.geogebra.common.main.AppConfig;
import org.geogebra.common.main.GeoGebraColorConstants;
import org.geogebra.web.html5.gui.zoompanel.FullScreenHandler;
import org.geogebra.web.html5.gui.zoompanel.MebisFullscreenHandler;

import com.google.gwt.user.client.Window.Location;

/**
 * Mebis specific settings
 */
public class MebisSettings implements VendorSettings {

	private static final String MEBIS_LICENSE_PATH = "/static/license.html?";

	@Override
	public String getLicenseURL() {
		if (!Location.getProtocol().startsWith("http")) {
			return "https://tafel.mebis.bayern.de" + MEBIS_LICENSE_PATH;
		}
		return MEBIS_LICENSE_PATH;
	}

	@Override
	public String getAppTitle(AppConfig config) {
		return "Tafel";
	}

	@Override
	public FullScreenHandler getFullscreenHandler() {
		return new MebisFullscreenHandler();
	}

	@Override
	public GColor getPrimaryColor() {
		return GeoGebraColorConstants.MEBIS_ACCENT;
	}

	@Override
	public boolean isMainMenuExternal() {
		return false;
	}

	@Override
	public String getMenuLocalizationKey(String key) {
		return key + ".Mebis";
	}

	@Override
	public String getStyleName(String styleName) {
		return styleName + "Mebis";
	}

	@Override
	public List<FontFamily> getTextToolFonts() {
		return Arrays.asList(FontFamily.ARIAL,
				FontFamily.CALIBRI,
				FontFamily.CENTURY_GOTHIC,
				FontFamily.COMIC_SANS,
				FontFamily.COURIER,
				FontFamily.GEORGIA,
				FontFamily.DYSLEXIC,
				FontFamily.PALATINO,
				FontFamily.QUICKSAND,
				FontFamily.ROBOTO,
				FontFamily.SCHULBUCH_BAYERN,
				FontFamily.SF_MONO,
				FontFamily.SF_PRO,
				FontFamily.SOURCE_SANS_PRO,
				FontFamily.TIMES,
				FontFamily.TITILLIUM,
				FontFamily.TREBUCHET,
				FontFamily.VERDANA);
	}

	@Override
	public boolean canSessionExpire() {
		return true;
	}

	@Override
	public boolean hasBitmapSecurity() {
		return true;
	}
}
