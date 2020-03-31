package com.liudao.github;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liudao.entities.GUser;

public class GithubUser {
	public static void main(String[] args) {
		URL url = null;
		HttpsURLConnection https = null;
		try {
			url = new URL("https://api.github.com/users/jimmyseraph"); // 定义要访问的api的url地址
			https = (HttpsURLConnection) url.openConnection(); // 根据url地址打开连接，返回https对象
			https.setDoInput(true); // 打开输入流
			InputStream is = https.getInputStream(); // 获得当前https连接的输入字节流
			Gson gson = new GsonBuilder().create();
			/*
			 *  new InputStreamReader(is)表示将is字节流转换为reader字符流，用于作为fromJson的第一个参数
			 *  fromJson的第二个参数，则是要将json字符串转换成的对应的实体类（bean）
			 */
			GUser user = gson.fromJson(new InputStreamReader(is), GUser.class);
			System.out.println(user.getLogin());
			System.out.println(user.getId());
			System.out.println(user);
//			int c = -1;
//			while((c=is.read())!=-1) {  // 通过循环遍历从服务端发来的所有内容，当返回位-1，则表示结束
//				System.out.print((char)c); // 逐个字节打印
//			}
			is.close();
			https.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
