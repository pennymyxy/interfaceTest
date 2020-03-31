package com.liudao.github;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liudao.entities.CommitItem;
import com.liudao.entities.GithubCommitsResult;
import com.liudao.entities.GithubRepo;

public class GithubSpider {
	public static void main(String[] args) {
		Map<String, Integer> repo_count = new HashMap<>(); // repo_count集合用于存放仓库全名——提交次数
		Map<String, String> repo_lang = new HashMap<>(); // repo_lang集合用于存放仓库全名——语言
		for(int i = 1; i <= 10; i++) {
			String url = ("https://api.github.com/search/commits"
					+ "?q=committer-date:2017-11-27..2017-12-01&page="+i+"&per_page=50"); // 10页记录，每页50条
			String ret = getGithubApiReply(url);
			Gson gson = new GsonBuilder().create();
			GithubCommitsResult gcr = gson.fromJson(ret, GithubCommitsResult.class);
			CommitItem[] items = gcr.getItems();
			for(CommitItem item : items) {
				String repoFullName = item.getRepository().getFull_name();
				if(repo_count.containsKey(repoFullName)) { 
					repo_count.put(repoFullName, repo_count.get(repoFullName)+1); // 如果该仓库已经出现过，则在原来的提交数量上+1
				}else {
					repo_count.put(repoFullName, 1); // 如果该仓库没出现过，则定义为第一次提交
				}
			}
		}
		for(String repoFullName : repo_count.keySet()) {
			String ret = getGithubApiReply("https://api.github.com/repos/"+repoFullName); // 查询仓库所用的语言
			Gson gson = new GsonBuilder().create();
			GithubRepo gp = gson.fromJson(ret, GithubRepo.class);
			repo_lang.put(repoFullName, gp.getLanguage());
		}
		System.out.println(repo_count);
		System.out.println(repo_lang);
	}
	
	public static String getGithubApiReply(String api_url) {
		String retstr = null;
		try {
			URL url = new URL(api_url);
			HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
			https.addRequestProperty("Accept", "application/vnd.github.cloak-preview");
			String up = MyPassport.username+":"+MyPassport.password;
			up = Base64.getEncoder().encodeToString(up.getBytes());
			https.addRequestProperty("Authorization","Basic "+up);
			https.setDoInput(true);
			InputStream is = https.getInputStream();
			Reader reader = new InputStreamReader(is, "utf-8");
			StringBuffer sb = new StringBuffer();
			int c = -1;
			while((c=reader.read())!=-1) {
				sb.append((char)c);
			}
			retstr = new String(sb);
			is.close();
			https.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retstr;
	}
}
