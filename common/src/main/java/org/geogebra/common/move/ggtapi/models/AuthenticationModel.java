package org.geogebra.common.move.ggtapi.models;

import org.geogebra.common.move.ggtapi.events.LoginEvent;
import org.geogebra.common.move.ggtapi.operations.BackendAPI;
import org.geogebra.common.util.GTimer;
import org.geogebra.common.util.GTimerListener;

import java.util.ArrayList;

/**
 * @author gabor Base class for login logout operations
 *
 */
public abstract class AuthenticationModel implements GTimerListener {
	private GeoGebraTubeUser loggedInUser = null;
	private GTimer sessionExpireTimer;

	/**
	 * token name for user logged in got back from GGT
	 */
	protected static final String GGB_TOKEN_KEY_NAME = "token";
	private boolean stayLoggedOut;
	private boolean loginStarted;

	/**
	 * @param loginEvent login event
	 */
	public void onLogin(LoginEvent loginEvent) {
		this.loginStarted = false;
		if (loginEvent.isSuccessful()) {
			onLoginSuccess(loginEvent.getUser(), loginEvent.getJSON());
		} else {
			onLoginError(loginEvent.getUser());
		}
	}

	/**
	 * Update after logout
	 */
	public void onLogout() {
		clearLoginToken();
		loggedInUser = null;
	}

	/**
	 * Keep track of started passive login.
	 */
	public void setLoginStarted() {
		this.loginStarted = true;
	}

	/**
	 * @param token
	 *            The token to store Stores the token in localStorage or with
	 *            any other client side method.
	 */
	public abstract void storeLoginToken(String token);

	/**
	 * @return The stored Token or null if not token stored
	 */
	public abstract String getLoginToken();

	/**
	 * Clears the login token from localStorage, or from other storage place
	 * used
	 */
	public abstract void clearLoginToken();

	/**
	 * @param user
	 *            ggb tube user
	 * @param json
	 *            from GGT Parses the response, and sets model dependent things
	 *            (localStorage, etc).
	 */
	private void onLoginSuccess(GeoGebraTubeUser user, String json) {
		this.stayLoggedOut = false;
		// Remember the logged in user
		this.loggedInUser = user;
		storeLastUser(json);
		// Store the token in the storage
		if (!user.getLoginToken().equals(this.getLoginToken())) {
			storeLoginToken(user.getLoginToken());
		}
		getSessionExpireTimer().start();
	}

	protected abstract void storeLastUser(String s);

	/**
	 * @param user ggb tube user
	 *            from GGT error happened, cleanup, etc
	 */
	private void onLoginError(GeoGebraTubeUser user) {
		this.stayLoggedOut = false;
		if (getLoginToken() != null || user.isShibbolethAuth()) {
			clearLoginToken();
		}
	}

	/**
	 * @return the Username of the currently logged in user or null if no user
	 *         is logged in
	 */
	public String getUserName() {
		if (loggedInUser != null) {
			return loggedInUser.getUserName();
		}
		return null;
	}

	/**
	 * @return list of group IDs for current user
	 */
	public ArrayList<String> getUserGroups() {
		if (loggedInUser != null) {
			return loggedInUser.getGroups();
		}
		return null;
	}

	/**
	 * @return the Username of the currently logged in user or null if no user
	 *         is logged in
	 */
	public int getUserId() {
		if (loggedInUser != null) {
			return loggedInUser.getUserId();
		}
		return -1;
	}

	/**
	 * @return the language of the currently logged in user or empty string if no
	 *         user is logged in
	 */
	public String getUserLanguage() {
		if (loggedInUser != null) {
			return loggedInUser.getLanguage();
		}
		return "";
	}

	/**
	 * @return The currently logged in user or null if no user is logged in
	 */
	public GeoGebraTubeUser getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * @return true, if a user is currently logged in or false otherwise.
	 */
	public boolean isLoggedIn() {
		return loggedInUser != null;
	}

	/**
	 * Initialize for offline use (assume last user logged in).
	 * 
	 * @param api
	 *            tube API
	 */
	public void startOffline(BackendAPI api) {
		if (this.loadLastUser() != null) {
			GeoGebraTubeUser offline = new GeoGebraTubeUser(null);
			if (api.parseUserDataFromResponse(offline, this.loadLastUser())) {
				this.loggedInUser = offline;
			}
		}
	}

	/**
	 * @return last user from local storage
	 */
	public abstract String loadLastUser();

	/**
	 * User closed login explicitly, save a flag not to ask again.
	 */
	public void stayLoggedOut() {
		this.stayLoggedOut = true;
		this.loginStarted = false;
	}

	/**
	 * @return false if user closed login explicitly
	 */
	public boolean mayLogIn() {
		return !stayLoggedOut;
	}

	/**
	 * @return whether login was initiated but not finished
	 */
	public boolean isLoginStarted() {
		return loginStarted;
	}

	public String getEncoded() {
		return null;
	}

	/**
	 * timer for session life
	 * @return session timer
	 */
	public GTimer getSessionExpireTimer() {
		return sessionExpireTimer;
	}

	public void setSessionExpireTimer(GTimer timer) {
		sessionExpireTimer = timer;
	}
}
