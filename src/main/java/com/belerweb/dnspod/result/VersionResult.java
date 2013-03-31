package com.belerweb.dnspod.result;

/**
 * API result of get API's version.
 * 
 * @author jun
 *
 */
public class VersionResult extends Result {

  public String getVersion() {
    return isSuccess() ? status.getMessage() : null;
  }

}
