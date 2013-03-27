package com.belerweb.dnspod;

public enum RecordType {

  A("A"), CNAME("CNAME"), MX("MX"), TXT("TXT"), NS("NS"), AAAA("AAAA"), SRV("SRV");

  private String value;

  private RecordType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  };

}
