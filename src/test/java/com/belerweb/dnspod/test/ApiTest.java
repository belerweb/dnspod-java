package com.belerweb.dnspod.test;

import org.junit.Assert;
import org.junit.Test;

import com.belerweb.dnspod.DNSPodAPI;
import com.belerweb.dnspod.DNSPodAPI.DNSPodAPIFactory;
import com.belerweb.dnspod.RecordLine;
import com.belerweb.dnspod.RecordType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Due I do not have enough test account, I can do simple test only.
   */
  @Test
  public void test() {
    try {
      DNSPodAPI api = DNSPodAPIFactory.create();

      debug(api.getVersion());
      debug(api.getUserDetail());
      debug(api.modifyUser("xxx", "xxx", "12345678910", "00000000"));
      debug(api.modifyUserPassword("old", "new"));
      debug(api.modifyUserEmail("test@test.com", "test@test.com", "123456"));

      debug(api.modifyRecord(2049921, 21366982, "ddd", RecordType.A, RecordLine.DEFAULT,
          "192.168.0.105", 1, 600));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }

  @Test
  public void testGetRecordList() {
    DNSPodAPI api = DNSPodAPIFactory.create();
    debug(api.getRecordList(Integer.valueOf(System.getenv("DNSPod.api.domainId")), null, null,
        "www"));
  }

  private void debug(Object obj) {
    try {
      System.out.println(objectMapper.writeValueAsString(obj));
    } catch (JsonProcessingException e) {}
  }

}
