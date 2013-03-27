package com.belerweb.dnspod;

public enum RecordLine {
  DEFAULT("默认"), CHINATELECOM("电信"), CHINAUNICOM("联通"), ENET("教育网"), CHINAMOBILE("移动"), TIETONG(
      "铁通"), INTERNAL("国内"), ABROAD("国外"), SEARCHENGINE("搜索引擎"), BAIDU("百度"), GOOGLE("Google"), YOUDAO(
      "有道"), BING("必应"), SOSO("搜搜"), SOGOU("搜狗"), _360("360搜索");

  private String value;

  private RecordLine(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  };

}
