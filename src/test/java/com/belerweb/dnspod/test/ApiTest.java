package com.belerweb.dnspod.test;

import org.junit.Assert;
import org.junit.Test;

import com.belerweb.dnspod.DNSPodAPI;
import com.belerweb.dnspod.DNSPodAPI.DNSPodAPIFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiTest {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void test() {
		try {
			DNSPodAPI api = DNSPodAPIFactory.create();

			debug(api.getVersion());
			debug(api.getUserDetail());
			debug(api.modifyUser("xxx", "xxx", "12345678910", "00000000"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	private void debug(Object obj) {
		try {
			System.out.println(objectMapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
		}
	}

}
