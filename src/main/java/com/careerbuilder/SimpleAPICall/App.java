package com.careerbuilder.SimpleAPICall;

import com.careerbuilder.CBOAuth2.CBOAuth2Client;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		try {
			String token = GetAccessToken();
			System.out.println(token);
			String result = CallAPI(token);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Exception!");
		}
	}

	private static String CallAPI(String token) throws Exception {

		return APICaller.MakeGeographyRequest(token);
	}

	public static String GetAccessToken() {
		try {
			String id = "";
			String signature = "";
			String accessToken = CBOAuth2Client.getAccessToken(id, signature).access_token;

			return accessToken;

		} catch (Exception e) {
			return "EXCEPTION";
		}

	}

}
