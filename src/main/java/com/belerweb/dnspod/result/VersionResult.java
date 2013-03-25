package com.belerweb.dnspod.result;

public class VersionResult extends Result {

  public String getVersion() {
    return isSuccess() ? status.getMessage() : null;
  }

}
