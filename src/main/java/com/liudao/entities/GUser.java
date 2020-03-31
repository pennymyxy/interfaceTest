package com.liudao.entities;

public class GUser {
	private String login;
	private int id;
	private String avatar_url;
	private String html_url;
	private String name;
	public String getLogin() {
		return login;
	}
	public int getId() {
		return id;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "GUser [login=" + login + ", id=" + id + ", avatar_url=" + avatar_url + ", html_url=" + html_url
				+ ", name=" + name + "]";
	}
}
