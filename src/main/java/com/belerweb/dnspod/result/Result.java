package com.belerweb.dnspod.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DNSPod API's common result wrapper.
 * 
 * @author Jun
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

  protected Status status;

  /**
   * Check whether or not the result is successful.
   * 
   * @return true if success, or else false.
   */
  public boolean isSuccess() {
    return 1 == status.getCode();
  }

  public Status getStatus() {
    return status;
  }

  /**
   * DNSPod API's common result.
   * 
   * @author Jun
   */
  public static class Status {
    private int code;
    private String message;

    @JsonProperty("created_at")
    private Date timestamp;

    public int getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

    public Date getTimestamp() {
      return timestamp;
    }

  }
}
