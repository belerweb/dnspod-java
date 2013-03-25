package com.belerweb.dnspod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.belerweb.dnspod.result.Result;
import com.belerweb.dnspod.result.UserDetailResult;
import com.belerweb.dnspod.result.VersionResult;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jun
 * 
 */
public final class DNSPodAPI {

	private static final String API_SERVER = "https://dnsapi.cn/";
	private static final String API_VERSION = "Info.Version";
	private static final String API_USER_DETAIL = "User.Detail";
	private static final String API_USER_MODIFY = "User.Modify";

	public static final String PARAM_NAME_LOGIN_EMAIL = "login_email";
	public static final String PARAM_NAME_LOGIN_PASSWORD = "login_password";
	public static final String PARAM_NAME_FORMAT = "format";
	public static final String PARAM_NAME_LANG = "lang";
	public static final String PARAM_NAME_ERROR_ON_EMPTY = "error_on_empty";

	public static final String PARAM_VALUE_FORMAT_JSON = "json";
	public static final String PARAM_VALUE_FORMAT_XML = "xml";
	public static final String PARAM_DEFAULT_VALUE_FORMAT = PARAM_VALUE_FORMAT_JSON;
	public static final String PARAM_VALUE_LANG_EN = "en";
	public static final String PARAM_VALUE_LANG_CN = "cn";
	public static final String PARAM_DEFAULT_VALUE_LANG = PARAM_VALUE_LANG_EN;
	public static final String PARAM_VALUE_ERROR_ON_EMPTY_YES = "yes";
	public static final String PARAM_VALUE_ERROR_ON_EMPTY_NO = "no";
	public static final String PARAM_DEFAULT_VALUE_ERROR_ON_EMPTY = PARAM_VALUE_ERROR_ON_EMPTY_NO;

	private String email;
	private String password;
	private String format;
	private String lang;
	private String errorOnEmpty;
	private HttpClient http;
	private ObjectMapper objectMapper;

	private DNSPodAPI(Properties properties) {
		assert properties != null : "properties is need.";
		assert properties.get(PARAM_NAME_LOGIN_EMAIL) != null : "login_email is need.";
		assert properties.get(PARAM_NAME_LOGIN_PASSWORD) != null : "login_password is need.";

		this.email = (String) properties.get(PARAM_NAME_LOGIN_EMAIL);
		this.password = (String) properties.get(PARAM_NAME_LOGIN_PASSWORD);
		// TODO get format from properties
		// this.format = (String) properties.get(PARAM_NAME_FORMAT);
		this.format = PARAM_DEFAULT_VALUE_FORMAT;
		this.lang = (String) properties.get(PARAM_NAME_LANG);
		this.errorOnEmpty = (String) properties.get(PARAM_NAME_ERROR_ON_EMPTY);

		this.http = new DefaultHttpClient();
		this.objectMapper = new ObjectMapper();
		this.objectMapper.setDateFormat(new SimpleDateFormat(
				"yyyy-MM-dd mm:dd:ss"));
	}

	public VersionResult getVersion() {
		return execute(API_VERSION, createCommonParam(), VersionResult.class);
	}

	public UserDetailResult getUserDetail() {
		return execute(API_USER_DETAIL, createCommonParam(),
				UserDetailResult.class);
	}

	public Result modifyUser(String realName, String nick, String telephone,
			String im) {
		List<NameValuePair> param = createCommonParam();
		param.add(new BasicNameValuePair("real_name", realName));
		param.add(new BasicNameValuePair("nick", nick));
		param.add(new BasicNameValuePair("telephone", telephone));
		param.add(new BasicNameValuePair("im", im));
		return execute(API_USER_MODIFY, param, Result.class);
	}

	private List<NameValuePair> createCommonParam() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair(PARAM_NAME_LOGIN_EMAIL, email));
		parameters.add(new BasicNameValuePair(PARAM_NAME_LOGIN_PASSWORD,
				password));
		parameters.add(new BasicNameValuePair(PARAM_NAME_FORMAT, format));
		parameters.add(new BasicNameValuePair(PARAM_NAME_LANG, lang));
		parameters.add(new BasicNameValuePair(PARAM_NAME_ERROR_ON_EMPTY,
				errorOnEmpty));
		return parameters;
	}

	private <T extends Result> T execute(String api,
			List<NameValuePair> parameters, Class<T> resultType) {
		HttpPost post = new HttpPost(API_SERVER + api);

		try {
			post.setEntity(new UrlEncodedFormEntity(parameters));
			HttpResponse respose = http.execute(post);
			return objectMapper.readValue(respose.getEntity().getContent(),
					resultType);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static final class DNSPodAPIFactory {
		public static DNSPodAPI create() {
			// check config from env
			String loginEmail = System.getenv("DNSPod.api.login_email");
			String loginPassword = System.getenv("DNSPod.api.login_password");
			String format = System.getenv("DNSPod.api.format");
			String lang = System.getenv("DNSPod.api.lang");
			String errorOnEmpty = System.getenv("DNSPod.api.error_on_empty");

			// check config from system properties
			loginEmail = System.getProperty("DNSPod.api.login_email",
					loginEmail);
			loginPassword = System.getProperty("DNSPod.api.login_password",
					loginPassword);
			format = System.getProperty("DNSPod.api.format",
					format == null ? PARAM_DEFAULT_VALUE_FORMAT : format);
			lang = System.getProperty("DNSPod.api.lang",
					lang == null ? PARAM_DEFAULT_VALUE_LANG : lang);
			errorOnEmpty = System.getProperty("DNSPod.api.error_on_empty",
					errorOnEmpty == null ? PARAM_DEFAULT_VALUE_ERROR_ON_EMPTY
							: errorOnEmpty);

			Properties properties = new Properties();
			properties.put(PARAM_NAME_LOGIN_EMAIL, loginEmail);
			properties.put(PARAM_NAME_LOGIN_PASSWORD, loginPassword);
			properties.put(PARAM_NAME_FORMAT, format);
			properties.put(PARAM_NAME_LANG, lang);
			properties.put(PARAM_NAME_ERROR_ON_EMPTY, errorOnEmpty);
			return create(properties);
		}

		public static DNSPodAPI create(Properties properties) {
			return new DNSPodAPI(properties);
		}
	}

}
