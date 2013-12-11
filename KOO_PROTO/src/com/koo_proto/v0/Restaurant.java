package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

public class Restaurant {


	private UUID mId;
	private String mName;
	private String mAddress;
	private ArrayList<Dish> mDishes;
	private RestaurantOrder mOrder;
	
	public String getAddress() {
		return mAddress;
	}

	public Restaurant(String name, String address) {
		mId = UUID.randomUUID();
		mName = name;
		mAddress = address;
		mDishes = new ArrayList<Dish>();
		mOrder = new RestaurantOrder(mId);
	}
	
	public RestaurantOrder getOrder() {
		return mOrder;
	}
	
	public void addDishToOrder(UUID dishID) {
		mOrder.addDish(dishID);
	}
	
	public boolean removeDishFromOrder(UUID dishID) {
		return mOrder.removeDish(dishID);
	}
	
	public boolean orderContainsDish(UUID dishID) {
		return mOrder.contains(dishID);
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
		for (Dish dish : dishes) {
			dish.setRestaurantID(mId);
		}
	}

	@Override
	public String toString() {
		return mName;
	}
}
