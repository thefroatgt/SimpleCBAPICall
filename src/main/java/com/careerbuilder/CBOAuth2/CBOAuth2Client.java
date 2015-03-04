package com.careerbuilder.CBOAuth2;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.nimbusds.jose.JOSEException;

public class CBOAuth2Client {
	private String signature;
	private String clientId;

	public static CBOAuth2Token getAccessToken(String clientId, String signature)
			throws JOSEException {

		CBOAuth2Client client = new CBOAuth2Client(clientId, signature);
		CBOAuth2Token tok = client.getToken();

		return tok;

	}

	public CBOAuth2Client(String clientId, String signature) {
		this.signature = signature;
		this.clientId = clientId;
	}

	CBOAuth2Token getToken() throws JOSEException {

		String tokenResponse = getTokenResponse();

		return parseResponse(tokenResponse);
	}

	private String getTokenResponse() throws JOSEException {
		String tokenResponse = "";
		Response response = null;

		try {
			// write parameters
			String data = "grant_type=client_credentials&client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
			data += "&client_assertion=" + getJWT() + "&client_id="
					+ this.clientId;

			SSLContext context = null;
			try {
				context = SSLContext.getInstance("TLSv1.2");
				context.init(null, null, null);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}

			Client client = ClientBuilder.newBuilder().sslContext(context)
					.build();

			WebTarget webTarget = client.property(
					ClientProperties.CHUNKED_ENCODING_SIZE,
					Integer.toString(data.getBytes().length)).target(
					"https://www.careerbuilder.com/share/oauth2/token.aspx");

			response = webTarget
					.request()
					.header("content-length",
							Integer.toString(data.getBytes().length))
					.post(Entity.entity(data,
							MediaType.APPLICATION_FORM_URLENCODED));

			tokenResponse = response.readEntity(String.class);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return tokenResponse;
	}

	private CBOAuth2Token parseResponse(String response) {
		CBOAuth2Token token = new CBOAuth2Token(response);
		return token;
	}

	private String getJWT() throws JOSEException {
		return JWTManager.createClientAssertionJwt(this.clientId,
				this.signature);
	}
}
