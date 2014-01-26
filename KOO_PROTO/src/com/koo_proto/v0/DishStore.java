package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class DishStore {

	private ArrayList<Dish> mDishArray;
	
	private static DishStore sDishStore;
	private Context mAppContext;
	
	private DishStore(Context appContext) {
		mAppContext = appContext;
		mDishArray = new ArrayList<Dish>();
	}
	
	public static DishStore get(Context c) {
		if (sDishStore == null) {
			sDishStore = new DishStore(c.getApplicationContext());
		}
		return sDishStore;
	}
	
	public ArrayList<Dish> getDishes() {
		return mDishArray;
	}
	
	public Dish getDish(UUID id) {
		for (Dish r : mDishArray) {
			if (r.getId().equals(id)) {
				return r;
			}
		}
		return null;
	}
	
	public void addDish(Dish dish) {
		mDishArray.add(dish);
	}
	
	public void addDishes(ArrayList<Dish> dishes) {
		mDishArray.addAll(dishes);
	}
}
