package com.careerbuilder.CBOAuth2;

import com.google.gson.Gson;

public class CBOAuth2Token
{
	public final String access_token;
	public final String token_type;
	public final String expires_in;
	public final long expirationTimeMs;
	
	public CBOAuth2Token(String json)
	{
		Gson gson = new Gson();
		CBOAuth2Token tmp = gson.fromJson(json, CBOAuth2Token.class);
		access_token = tmp.access_token;
		token_type = tmp.token_type;
		expires_in = tmp.expires_in;
		
		int tokenDurationInSeconds;
		try
		{
			tokenDurationInSeconds = Integer.parseInt(expires_in);
		}
		catch (NumberFormatException nfe)
		{
			tokenDurationInSeconds = 1800;
		}
		
		expirationTimeMs = System.currentTimeMillis() + (tokenDurationInSeconds * 1000);
	}
	
}