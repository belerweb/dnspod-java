package com.belerweb.dnspod.result;

import java.util.Date;
import java.util.List;

import com.belerweb.dnspod.result.Result.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * result wrapper for https://dnsapi.cn/Record.List 
 * @author XF
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResultListResult {

  protected Status status;
  protected Domain domain;
  protected Info info;
  protected List<Record> records;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Domain {
    private Integer id;
    private String name;
    private String punycode;
    private String grade;
    private String owner;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPunycode() {
      return punycode;
    }

    public void setPunycode(String punycode) {
      this.punycode = punycode;
    }

    public String getGrade() {
      return grade;
    }

    public void setGrade(String grade) {
      this.grade = grade;
    }

    public String getOwner() {
      return owner;
    }

    public void setOwner(String owner) {
      this.owner = owner;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Info {
    @JsonProperty("sub_domains")
    private Integer subDomains;

    @JsonProperty("record_total")
    private Integer recordTotal;

    public Integer getSubDomains() {
      return subDomains;
    }

    public void setSubDomains(Integer subDomains) {
      this.subDomains = subDomains;
    }

    public Integer getRecordTotal() {
      return recordTotal;
    }

    public void setRecordTotal(Integer recordTotal) {
      this.recordTotal = recordTotal;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Record {
    private Integer id;
    private String name;
    private String line;
    private String type;
    private Integer ttl;
    private String value;
    private Integer mx;
    private String enabled;
    private String status;
    private String remark;
    private String hold;

    @JsonProperty("use_aqb")
    private String useAqb;
    @JsonProperty("monitor_status")
    private String monitorStatus;
    @JsonProperty("updated_on")
    private Date updateTime;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getLine() {
      return line;
    }

    public void setLine(String line) {
      this.line = line;
    }

    public Integer getTtl() {
      return ttl;
    }

    public void setTtl(Integer ttl) {
      this.ttl = ttl;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public Integer getMx() {
      return mx;
    }

    public void setMx(Integer mx) {
      this.mx = mx;
    }

    public String getEnabled() {
      return enabled;
    }

    public void setEnabled(String enabled) {
      this.enabled = enabled;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getRemark() {
      return remark;
    }

    public void setRemark(String remark) {
      this.remark = remark;
    }

    public String getMonitorStatus() {
      return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
      this.monitorStatus = monitorStatus;
    }

    public Date getUpdateTime() {
      return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getUseAqb() {
      return useAqb;
    }

    public void setUseAqb(String useAqb) {
      this.useAqb = useAqb;
    }

    public String getHold() {
      return hold;
    }

    public void setHold(String hold) {
      this.hold = hold;
    }

  }

  public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public List<Record> getRecords() {
    return records;
  }

  public void setRecords(List<Record> records) {
    this.records = records;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
