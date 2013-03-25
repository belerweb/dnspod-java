package com.belerweb.dnspod.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailResult extends Result {

	@JsonProperty("info")
	private Info info;

	public User getUser() {
		return info.user;
	}

	public Agent getAgent() {
		return info.agent;
	}

	public static class Info {
		private User user;
		private Agent agent;

		public User getUser() {
			return user;
		}

		public Agent getAgent() {
			return agent;
		}
	}

	public static class User {

		private int id;
		private String email;
		private String status;
		private String telephone;
		private String im;
		private String nick;
		private int balance;
		private int smsbalance;

		@JsonProperty("email_verified")
		private String emailVerified;

		@JsonProperty("telephone_verified")
		private String telephoneVerified;

		@JsonProperty("agent_pending")
		private String agentPending;

		@JsonProperty("real_name")
		private String realName;

		@JsonProperty("user_type")
		private String userType;

		public int getId() {
			return id;
		}

		public String getEmail() {
			return email;
		}

		public String getStatus() {
			return status;
		}

		public String getTelephone() {
			return telephone;
		}

		public String getIm() {
			return im;
		}

		public String getNick() {
			return nick;
		}

		public int getBalance() {
			return balance;
		}

		public int getSmsbalance() {
			return smsbalance;
		}

		public String getEmailVerified() {
			return emailVerified;
		}

		public String getTelephoneVerified() {
			return telephoneVerified;
		}

		public String getAgentPending() {
			return agentPending;
		}

		public String getRealName() {
			return realName;
		}

		public String getUserType() {
			return userType;
		}
	}

	public static class Agent {

		@JsonProperty("user_id")
		private int userId;

		private int discount;
		private int points;
		private int users;

		public int getUserId() {
			return userId;
		}

		public int getDiscount() {
			return discount;
		}

		public int getPoints() {
			return points;
		}

		public int getUsers() {
			return users;
		}

	}

}
