package com.careerbuilder.CBOAuth2;

import java.util.Date;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;

public class JWTManager
{
	public static String createClientAssertionJwt(String client_id, String signature) throws JOSEException
	{
		// Compose the JWT claims set
		Claims claims = new Claims();
		claims.set_issuer(client_id);
		claims.set_subject(client_id);
		claims.set_audience("http://www.careerbuilder.com/share/oauth2/token.aspx");
		claims.set_expiration(new Date(System.currentTimeMillis() + 1000*60*30));
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
		header.setContentType("text/plain");
		
		JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONString()));

		String sharedKey = signature;
		                
		JWSSigner signer = new MACSigner(sharedKey.getBytes());
		jwsObject.sign(signer);
		                        
		return jwsObject.serialize();
	}
}