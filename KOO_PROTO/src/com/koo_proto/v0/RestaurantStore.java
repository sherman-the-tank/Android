package com.koo_proto.v0;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class RestaurantStore {

	private ArrayList<Restaurant> mRestaurantArray;
	
	private static RestaurantStore sRestaurantStore;
	private Context mAppContext;
	
	private RestaurantStore(Context appContext) {
		mAppContext = appContext;
		mRestaurantArray = new ArrayList<Restaurant>();
		Restaurant r = new Restaurant("Panera Bread", "118 East El Camino Real, Sunnyvale");
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		dishes.add(new Dish(appContext, "Rigatoni San Marzano", 5.95));
		dishes.add(new Dish(appContext, "Chilled Shrimp Soba Noodle Salad", 8.95));
		dishes.add(new Dish(appContext, "All Natural Bistro French Onion Soup", 6.95));
		r.setDishes(dishes);
		mRestaurantArray.add(r);
		
		r = new Restaurant("DishDash", "190 S Murphy Ave, Sunnyvale");
		dishes = new ArrayList<Dish>();
		dishes.add(new Dish(appContext, "Musaka'a", 6.50));
		dishes.add(new Dish(appContext, "Shakshuka", 20.95));
		dishes.add(new Dish(appContext, "Coppa Yogurt & Berry", 8.00));
		r.setDishes(dishes);
		mRestaurantArray.add(r);
		
		r = new Restaurant("Gochi Japanese", "19980 E Homestread Rd, Cupertino");
		dishes = new ArrayList<Dish>();
		dishes.add(new Dish(appContext, "Hiyayakko", 3.50));
		dishes.add(new Dish(appContext, "Yasai Sticks", 7.50));
		dishes.add(new Dish(appContext, "Tori Soboro Natto", 7.50));
		r.setDishes(dishes);
		mRestaurantArray.add(r);
		
		r = new Restaurant("Falafel STOP", "1325 Sunnyvale Saratoga Rd, Sunnyvale");
		dishes = new ArrayList<Dish>();
		dishes.add(new Dish(appContext, "Sabich", 5.55));
		dishes.add(new Dish(appContext, "Falafel Plate", 6.75));
		dishes.add(new Dish(appContext, "Greek Salad", 7.25));
		r.setDishes(dishes);
		mRestaurantArray.add(r);
		
		r = new Restaurant("Orenchi Ramen", "3540 Homestead Rd, Santa Clara");
		dishes = new ArrayList<Dish>();
		dishes.add(new Dish(appContext, "Orenchi Ramen", 9.00));
		dishes.add(new Dish(appContext, "Shio Ramen", 8.80));
		dishes.add(new Dish(appContext, "Shoyu Ramen", 8.80));
		r.setDishes(dishes);
		mRestaurantArray.add(r);
	}
	
	public static RestaurantStore get(Context c) {
		if (sRestaurantStore == null) {
			sRestaurantStore = new RestaurantStore(c.getApplicationContext());
		}
		return sRestaurantStore;
	}
	
	public ArrayList<Restaurant> getRestaurants() {
		return mRestaurantArray;
	}
	
	public Restaurant getRestaurant(UUID id) {
		for (Restaurant r : mRestaurantArray) {
			if (r.getId().equals(id)) {
				return r;
			}
		}
		return null;
	}
}
