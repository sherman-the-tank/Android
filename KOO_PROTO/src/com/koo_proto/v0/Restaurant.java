package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

public class Restaurant {


	private UUID mId;
	private String mName;
	private String mAddress;
	private ArrayList<Dish> mDishes;
	
	public String getAddress() {
		return mAddress;
	}

	public Restaurant(String name, String address) {
		mId = UUID.randomUUID();
		mName = name;
		mAddress = address;
		mDishes = new ArrayList<Dish>();
	}
	
	public void setAddress(String address) {
		mAddress = address;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public UUID getId() {
		return mId;
	}
	
	public ArrayList<Dish> getDishes() {
		return mDishes;
	}

	public void setDishes(ArrayList<Dish> dishes) {
		mDishes = dishes;
	}

	@Override
	public String toString() {
		return mName;
	}
}
