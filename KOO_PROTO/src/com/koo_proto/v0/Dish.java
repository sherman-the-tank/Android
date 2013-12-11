package com.koo_proto.v0;

import java.util.UUID;

public class Dish {

	public UUID getRestaurantID() {
		return mRestaurantID;
	}

	public void setRestaurantID(UUID restaurantID) {
		mRestaurantID = restaurantID;
	}

	private UUID mId;
	private String mName;
	private String mPrice;
	private UUID mRestaurantID;
	
	public Dish(String name, String price) {
		mId = UUID.randomUUID();
		mName = name;
		mPrice = price;
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getPrice() {
		return mPrice;
	}

	public void setPrice(String price) {
		mPrice = price;
	}

	public UUID getId() {
		return mId;
	}
	
	@Override
	public String toString() {
		return mName + " " + mPrice;
	}
}
