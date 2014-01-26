package com.koo_proto.v0;

import java.util.UUID;
import android.content.Context;

public class Dish {

	public UUID getRestaurantID() {
		return mRestaurantID;
	}

	public void setRestaurantID(UUID restaurantID) {
		mRestaurantID = restaurantID;
	}

	private UUID mId;
	private String mName;
	private double mPrice;
	private UUID mRestaurantID;
	
	public Dish(Context context, String name, double price) {
		mId = UUID.randomUUID();
		mName = name;
		mPrice = price;
		DishStore dishStore = DishStore.get(context);
		dishStore.addDish(this);
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getPriceString() {
		return String.format("$%.2f", mPrice);
	}
	
	public double getPrice() {
		return mPrice;
	}

	public void setPrice(float price) {
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
