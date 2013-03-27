package com.belerweb.dnspod.result;



public class ModifyRecordResult extends Result {

  private Record record;

  public Record getRecord() {
    return record;
  }

  public static class Record {

    private int id;
    private String name;
    private String value;
    private String status;

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }

    public String getStatus() {
      return status;
    }
  }

}
