package h2o.common.util.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtil {

	private CookieUtil() {
	}

	public static void addCookie(HttpServletResponse response, String path, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	public static String getCookie(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
