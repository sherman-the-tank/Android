package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

public class RestaurantOrder {

	private UUID mRestaurantID;
	private ArrayList<UUID> mOrderedDishIDs;
	
	public RestaurantOrder(UUID restaurantID) {
		mRestaurantID = restaurantID;
		mOrderedDishIDs = new ArrayList<UUID>();
	}
	
	public UUID getRestaurantID() {
		return mRestaurantID;
	}
	
	public void addDish(UUID dishID) {
		mOrderedDishIDs.add(dishID);
	}
	
	public boolean removeDish(UUID dishID) {
		if (mOrderedDishIDs.contains(dishID)) {
			mOrderedDishIDs.remove(dishID);
			return true;
		}
		return false;
	}
	
	public boolean contains(UUID dishID) {
		return mOrderedDishIDs.contains(dishID);
	}
	
	public ArrayList<UUID> getOrderedDishIDs() {
		return mOrderedDishIDs;
	}
}
