package org.armitage.inc.AAInfo.extra;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PushMessageDto {
	private String token;
	private String user;
	private String message;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isValid() {
		if (token == null || user == null || message == null) {
			return false;
		}
		return true;
	}

	public List<NameValuePair> generatePostData() {
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		if (!isValid())
			return null;
		result.add(new BasicNameValuePair("token", getToken()));
		result.add(new BasicNameValuePair("user", getUser()));
		result.add(new BasicNameValuePair("message", getMessage()));

		return result;
	}

	@Override
	public String toString() {
		return "PushMessageDto [token=" + token + ", user=" + user + ", message=" + message + "]";
	}
}
