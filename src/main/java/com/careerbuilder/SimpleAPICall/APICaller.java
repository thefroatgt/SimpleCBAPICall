package com.careerbuilder.SimpleAPICall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class APICaller {

	public static String MakeGeographyRequest(String auth) throws Exception {

		URIBuilder builder = getURIBuilder(auth);

		HttpResponse httpresp = ExecuteRequest(builder, auth);

		StringBuffer buf = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(httpresp.getEntity().getContent())));

		String line = "";
		while ((line = br.readLine()) != null) {
			buf.append(line);
		}

		String resp = buf.toString();
		EntityUtils.consume(httpresp.getEntity());
		return resp;

	}

	protected static URIBuilder getURIBuilder(String auth)
			throws URISyntaxException {

		URIBuilder builder = new URIBuilder(
				"https://api.careerbuilder.com/core/geography/validate");

		builder.addParameter("locality", "Atlanta");

		builder.addParameter("adminArea", "GA");

		builder.addParameter("postalCode", "30309");

		return builder;
	}

	public static HttpResponse ExecuteRequest(URIBuilder builder,
			String authToken) throws Exception, Exception {

		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(builder.build());
		request.addHeader("Authorization", "bearer " + authToken);

		HttpResponse response = client.execute(request);
		return response;

	}

}
