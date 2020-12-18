package com.hanul.coffeelike.caramelweb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;

/**
 * 외부 서버로의 HTTP 연결을 쉽게 핸들링하는 유틸리티 클래스.
 *
 */
public class HttpConnector implements AutoCloseable {
	public static HttpConnector create(String url) throws IOException {
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			return new HttpConnector(c);
		} catch (MalformedURLException | ClassCastException ex) {
			throw new IllegalArgumentException("'" + url + "' is not a valid HTTP URL", ex);
		}
	}

	private HttpURLConnection connection;

	private HttpConnector(HttpURLConnection connection) {
		this.connection = connection;
	}

	public HttpConnector setRequestMethod(String method) throws ProtocolException {
		connection.setRequestMethod(method);
		return this;
	}

	public HttpConnector setRequestProperty(String key, String value) {
		connection.setRequestProperty(key, value);
		return this;
	}

	public Response<String> readAsString() throws IOException {
		try {
			connection.connect();
			int responseCode = connection.getResponseCode();
			return new Response<>(responseCode,
					collectString(
						responseCode>=200 && responseCode<=300 ?
								connection.getInputStream() 
								: connection.getErrorStream()));
		} finally {
			close();
		}
	}

	public Response<JsonObject> readAsJsonObject() throws IOException {
		try {
			connection.connect();
			int responseCode = connection.getResponseCode();
			return new Response<>(responseCode,
					collectJsonObject(
						responseCode>=200 && responseCode<300 ?
								connection.getInputStream() 
								: connection.getErrorStream()));
		} finally {
			close();
		}
	}

	private static String collectString(InputStream inputStream) throws IOException {
		try (InputStream is = inputStream;
			BufferedReader r = new BufferedReader(
					new InputStreamReader(is, StandardCharsets.UTF_8))
		) {
			StringBuilder stb = new StringBuilder();
			boolean first = true;
			while (true) {
				String s = r.readLine();
				if (s == null)
					break;

				if (first)
					first = false;
				else
					stb.append("\n");

				stb.append(s);
			}

			return stb.toString();
		}
	}

	private static JsonObject collectJsonObject(InputStream inputStream) throws IOException {
		try (InputStream is = inputStream;
			BufferedReader r = new BufferedReader(
					new InputStreamReader(is, StandardCharsets.UTF_8))
		) {
			return JsonHelper.GSON.fromJson(r, JsonObject.class);
		}
	}

	@Override
	public void close() {
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
	}

	public static final class Response<T> {
		private final int responseCode;
		private final T response;

		public Response(int responseCode, T response) {
			this.responseCode = responseCode;
			this.response = response;
		}

		public int getResponseCode() {
			return responseCode;
		}

		public T getResponse() {
			return response;
		}
		
		public boolean isSuccess() {
			return responseCode>=200&&responseCode<300;
		}

		@Override public String toString(){
			return "Response{"+
					"responseCode="+responseCode+
					", response="+response+
					'}';
		}
	}
}
