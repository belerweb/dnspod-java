package com.belerweb.dnspod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.belerweb.dnspod.result.GetResultListResult;
import com.belerweb.dnspod.result.ModifyRecordResult;
import com.belerweb.dnspod.result.Result;
import com.belerweb.dnspod.result.UserDetailResult;
import com.belerweb.dnspod.result.VersionResult;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DNSPod API java wrapper implements. See https://www.dnspod.cn/client/user_api_doc.pdf for detail.
 * 
 * @author Jun
 */
public final class DNSPodAPI {

  private static final String API_SERVER = "https://dnsapi.cn/";
  private static final String API_VERSION = "Info.Version";
  private static final String API_USER_DETAIL = "User.Detail";
  private static final String API_USER_MODIFY = "User.Modify";
  private static final String API_USER_PASSWORD_MODIFY = "Userpasswd.Modify";
  private static final String API_USER_EMAIL_MODIFY = "Useremail.Modify";

  private static final String API_RECORD_MODIFY = "Record.Modify";
  private static final String API_RECORD_LIST = "Record.List";

  public static final String CONFIG_KEY_LOGIN_EMAIL = "DNSPod.api.login_email";
  public static final String CONFIG_KEY_LOGIN_PASSWORD = "DNSPod.api.login_password";
  public static final String CONFIG_KEY_FORMAT = "DNSPod.api.format";
  public static final String CONFIG_KEY_LANG = "DNSPod.api.lang";
  public static final String CONFIG_KEY_ERROR_ON_EMPTY = "DNSPod.api.error_on_empty";

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
    this.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd mm:dd:ss"));
  }

  /**
   * Get the version of current API.
   * 
   * @return {@link VersionResult}
   */
  public VersionResult getVersion() {
    return execute(API_VERSION, createCommonParam(), VersionResult.class);
  }

  /**
   * Get the API login user detail.
   * 
   * @return {@link UserDetailResult}
   */
  public UserDetailResult getUserDetail() {
    return execute(API_USER_DETAIL, createCommonParam(), UserDetailResult.class);
  }

  /**
   * @param realName User's real name. If a company account, it represent company name.
   * @param nick Nickname, represent how to call when someone contact you.
   * @param telephone Mobile phone number.
   * @param im Tencent QQ Number
   * @return {@link Result}
   */
  public Result modifyUser(String realName, String nick, String telephone, String im) {
    List<NameValuePair> param = createCommonParam();
    param.add(new BasicNameValuePair("real_name", realName));
    param.add(new BasicNameValuePair("nick", nick));
    param.add(new BasicNameValuePair("telephone", telephone));
    param.add(new BasicNameValuePair("im", im));
    return execute(API_USER_MODIFY, param, Result.class);
  }

  /**
   * Modify current user's password.
   * 
   * @param oldPassword Old password.
   * @param newPassword New password.
   * @return {@link Result}
   */
  public Result modifyUserPassword(String oldPassword, String newPassword) {
    List<NameValuePair> param = createCommonParam();
    param.add(new BasicNameValuePair("old_password", oldPassword));
    param.add(new BasicNameValuePair("new_password", newPassword));
    return execute(API_USER_PASSWORD_MODIFY, param, Result.class);
  }

  /**
   * Modify current user's email.
   * 
   * @param oldEmail Old email.
   * @param newEmail New email.
   * @param password Current password.
   * @return {@link Result}
   */
  public Result modifyUserEmail(String oldEmail, String newEmail, String password) {
    List<NameValuePair> param = createCommonParam();
    param.add(new BasicNameValuePair("old_email", oldEmail));
    param.add(new BasicNameValuePair("new_email", newEmail));
    param.add(new BasicNameValuePair("password", password));
    return execute(API_USER_EMAIL_MODIFY, param, Result.class);
  }

  // TODO Telephoneverify.Code
  // TODO User.Log
  // TODO Domain.Create
  // TODO Domain.List
  // TODO Domain.Remove
  // TODO Domain.Status
  // TODO Domain.Info    return execute(API_VERSION, createCommonParam(), VersionResult.class);

  // TODO Domain.Log
  // TODO Domain.Searchenginepush
  // TODO Domain.Urlincn
  // TODO Domainshare.Create
  // TODO Domainshare.List
  // TODO Domainshare.Modify
  // TODO Domainshare.Remove
  // TODO Domain.Transfer
  // TODO Domain.Lock
  // TODO Domain.Lockstatus
  // TODO Domain.Unlock
  // TODO Domainalias.List
  // TODO Domainalias.Create
  // TODO Domainalias.Remove
  // TODO Domaingroup.List
  // TODO Domaingroup.Create
  // TODO Domaingroup.Modify
  // TODO Domaingroup.Remove
  // TODO Domain.Changegroup
  // TODO Domain.Ismark
  // TODO Domain.Remark
  // TODO Domain.Purview
  // TODO Domain.Acquire
  // TODO Domain.Acquiresend
  // TODO Domain.Acquirevalidate
  // TODO Record.Type
  // TODO Record.Line
  // TODO Record.Create
  // TODO Record.List
  // TODO Record.Modify

  /**
   * Modify domian record
   * 
   * @param domainId Dmain ID
   * @param recordId Record ID
   * @param subDomain Host name, such as "www"
   * @param recordType {@link RecordType}
   * @param recordLine {@link RecordLine}
   * @param value Record value, e.g. IP:8.8.8.8, CNMAE:cname.dnspod.com., MX:mail.dnspod.com.
   * @param mx MX priority, effective when record type is MX. Valid value is 1 to 20.
   * @param tt TTL. Valid value is 1 to 604800.
   * @return
   */
  public ModifyRecordResult modifyRecord(int domainId, int recordId, String subDomain,
      RecordType recordType, RecordLine recordLine, String value, int mx, int tt) {
    List<NameValuePair> param = createCommonParam();
    param.add(new BasicNameValuePair("domain_id", String.valueOf(domainId)));
    param.add(new BasicNameValuePair("record_id", String.valueOf(recordId)));
    param.add(new BasicNameValuePair("sub_domain", subDomain));
    param.add(new BasicNameValuePair("record_type", recordType.getValue()));
    param.add(new BasicNameValuePair("record_line", recordLine.getValue()));
    param.add(new BasicNameValuePair("value", value));
    param.add(new BasicNameValuePair("mx", String.valueOf(mx)));
    param.add(new BasicNameValuePair("tt", String.valueOf(tt)));
    return execute(API_RECORD_MODIFY, param, ModifyRecordResult.class);
  }

  /**
   * Get Record list
   * @param domainId Dmain ID
   * @param offset Offset for record, first record's offset is 0. Could be NULL.
   * @param length A number for limit records if you have too much records. Could be NULL.
   * @param subDomain sub domain. If set, only return the record of this domain. Could be NULL.
   * @return {@link GetResultListResult}
   */
  public GetResultListResult getRecordList(int domainId, Integer offset, Integer length,
      String subDomain) {
    List<NameValuePair> param = createCommonParam();
    param.add(new BasicNameValuePair("domain_id", String.valueOf(domainId)));
    if (null != offset) {
      param.add(new BasicNameValuePair("offset", String.valueOf(offset)));
    }
    if (null != length) {
      param.add(new BasicNameValuePair("length", String.valueOf(length)));
    }
    if (null != subDomain) {
      param.add(new BasicNameValuePair("sub_domain", subDomain));
    }

    return execute(API_RECORD_LIST, param, GetResultListResult.class);

  }

  // TODO Record.Remove
  // TODO Record.Ddns
  // TODO Record.Remark
  // TODO Record.Info
  // TODO Record.Status
  // TODO Monitor.Listsubdomain
  // TODO Monitor.List
  // TODO Monitor.Create
  // TODO Monitor.Modify
  // TODO Monitor.Remove
  // TODO Monitor.Info
  // TODO Monitor.Setstatus
  // TODO Monitor.Gethistory
  // TODO Monitor.Userdesc
  // TODO Monitor.Getdowns
  /**
   * Common parameters which all DNSPod's API need.
   * 
   * @return Parameters
   */
  private List<NameValuePair> createCommonParam() {
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new BasicNameValuePair(PARAM_NAME_LOGIN_EMAIL, email));
    parameters.add(new BasicNameValuePair(PARAM_NAME_LOGIN_PASSWORD, password));
    parameters.add(new BasicNameValuePair(PARAM_NAME_FORMAT, format));
    parameters.add(new BasicNameValuePair(PARAM_NAME_LANG, lang));
    parameters.add(new BasicNameValuePair(PARAM_NAME_ERROR_ON_EMPTY, errorOnEmpty));
    return parameters;
  }

  /**
   * Send request to DNSPod's API, and wrapper json result to bean.
   * 
   * @param api DNSPod's API's path.
   * @param parameters Parameters sent to DNSPod's API.
   * @param resultType Bean result type, see {@link Result}.
   * @return Bean wrapper result.
   */
  private <T> T execute(String api, List<NameValuePair> parameters, Class<T> resultType) {
    HttpPost post = new HttpPost(API_SERVER + api);

    try {
      post.setEntity(new UrlEncodedFormEntity(parameters, Consts.UTF_8));
      HttpResponse respose = http.execute(post);
      return objectMapper.readValue(respose.getEntity().getContent(), resultType);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * DNSPod API factory, use to create a API instance.
   * 
   * @author Jun
   */
  public static final class DNSPodAPIFactory {
    /**
     * Create DNSPodAPI use environment variable or system properties. System properties value will
     * override environment variable value with the same name.
     * 
     * @see {@link DNSPodAPIFactory#create(Properties)} for detail.
     * 
     * @return {@link DNSPodAPI}
     */
    public static DNSPodAPI create() {
      // check config from env
      String loginEmail = System.getenv(CONFIG_KEY_LOGIN_EMAIL);
      String loginPassword = System.getenv(CONFIG_KEY_LOGIN_PASSWORD);
      String format = System.getenv(CONFIG_KEY_FORMAT);
      String lang = System.getenv(CONFIG_KEY_LANG);
      String errorOnEmpty = System.getenv(CONFIG_KEY_ERROR_ON_EMPTY);

      // check config from system properties
      loginEmail = System.getProperty(CONFIG_KEY_LOGIN_EMAIL, loginEmail);
      loginPassword = System.getProperty(CONFIG_KEY_LOGIN_PASSWORD, loginPassword);
      format =
          System.getProperty(CONFIG_KEY_FORMAT, format == null
              ? PARAM_DEFAULT_VALUE_FORMAT
              : format);
      lang = System.getProperty(CONFIG_KEY_LANG, lang == null ? PARAM_DEFAULT_VALUE_LANG : lang);
      errorOnEmpty =
          System.getProperty(CONFIG_KEY_ERROR_ON_EMPTY, errorOnEmpty == null
              ? PARAM_DEFAULT_VALUE_ERROR_ON_EMPTY
              : errorOnEmpty);

      Properties properties = new Properties();
      properties.put(PARAM_NAME_LOGIN_EMAIL, loginEmail);
      properties.put(PARAM_NAME_LOGIN_PASSWORD, loginPassword);
      properties.put(PARAM_NAME_FORMAT, format);
      properties.put(PARAM_NAME_LANG, lang);
      properties.put(PARAM_NAME_ERROR_ON_EMPTY, errorOnEmpty);
      return create(properties);
    }

    /**
     * Create DNSPodAPI use specified properties. Key {@link DNSPodAPI#CONFIG_KEY_LOGIN_EMAIL} hold
     * login email. Key {@link DNSPodAPI#CONFIG_KEY_LOGIN_PASSWORD} hold login password. Key
     * {@link DNSPodAPI#CONFIG_KEY_LANG} hold message language. Key
     * {@link DNSPodAPI#CONFIG_KEY_ERROR_ON_EMPTY} hold if error on empty.
     * 
     * @param properties
     * @return {@link DNSPodAPI}
     */
    public static DNSPodAPI create(Properties properties) {
      return new DNSPodAPI(properties);
    }
  }

}
