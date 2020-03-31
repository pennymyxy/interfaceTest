package com.liudao.gsontest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liudao.entities.Cat;

public class GsonDemo {
	public static void main(String[] args) {
		Gson gson = new GsonBuilder().create();
		Cat cat = new Cat();
		cat.setName("Tom");
		cat.setAge(6);
		String jsonStr = gson.toJson(cat);
		System.out.println(jsonStr);
	}
}
