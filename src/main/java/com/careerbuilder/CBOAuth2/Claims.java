package com.careerbuilder.CBOAuth2;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Claims {
	@SerializedName("iss") private String issuer;
	@SerializedName("sub") private String subject;
	@SerializedName("aud") private String audience;
	@SerializedName("exp") private long expiration;
	
	public void set_issuer(String iss){
		issuer = iss;
	}
	
	public void set_subject(String sub){
		subject = sub;
	}
	
	public void set_audience(String aud){
		audience = aud;
	}
	
	public void set_expiration(Date d){
		expiration = d.getTime();
	}
	
	public String toJSONString(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
