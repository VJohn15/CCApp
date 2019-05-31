package com.example.tesapp2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;



public class NutritionDiet {
    float calories;
    float breakfastCalories;
    float lunchCalories;
    float dinnerCalories;
    HashMap<String, FoodInfo> usersFoodAndCalories = getFoodCalories();

    HashMap<String,HashMap<String,Integer>> portions;

    public HashMap<String, FoodInfo> getUsersFoodAndCalories() {
        return usersFoodAndCalories;
    }

    ArrayList<String> food ;

    static HashMap<String, Integer>  foodCount(ArrayList<String> strings)
    {
        // Creating a HashMap containing char
        // as a key and occurrences as  a value
        HashMap<String, Integer> foodCounter
                = new HashMap<String, Integer>();

        // Converting given string to char array

        // checking each char of strArray
        for (String c : strings) {
            if (foodCounter.containsKey(c)) {

                // If char is present in charCountMap,
                // incrementing it's count by 1
                foodCounter.put(c, foodCounter.get(c) + 1);
            }
            else {

                // If char is not present in charCountMap,
                // putting this char to charCountMap with 1 as it's value
                foodCounter.put(c, 1);
            }
        }

        // Printing the charCountMap
        //for (Map.Entry entry : foodCounter.entrySet()) {
        //System.out.println(entry.getKey() + " " + entry.getValue());
        //}
        return foodCounter;
    }
    private String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private FoodInfo getNutrionData(String food, int index){
        try{
            JSONArray fieldsArr = new JSONArray();
            fieldsArr.put("item_name");
            fieldsArr.put("nf_calories");
            fieldsArr.put("nf_serving_size_unit");

            JSONArray sortArr = new JSONArray();
            sortArr.put("_score");
            sortArr.put("desc");

            JSONObject json = new JSONObject();
            json.put("appId","19056c2a");
            json.put("appKey","13a11feb3ce02c97d4bed2b5a85e1461");
            json.put("query",food);
            json.put("fields",fieldsArr);
            json.put("sort",sortArr);

            JSONObject response = new JSONObject(executePost("https://api.nutritionix.com/v1_1/search", json.toString()));
            JSONObject result = new JSONObject(((JSONObject)response.getJSONArray("hits").get(index)).get("fields").toString());
            //System.out.println(result);

            return new FoodInfo(Float.valueOf(result.get("nf_calories").toString()), result.get("item_name").toString(),result.get("nf_serving_size_unit").toString());
        }catch (Exception ex){
            System.out.println("Catched exception : " + ex);
            return null;
        }

    }
    private HashMap<String, FoodInfo> getFoodCalories(){
        HashMap<String,FoodInfo> userFoodAndCalories = new HashMap<>();
        for(String f : food){
            userFoodAndCalories.put(f,getNutrionData(f,0));
        }
        return userFoodAndCalories;
    }
    private  ArrayList<String> createDiet(HashMap<String, FoodInfo> eatingProducts, float calories){
        ArrayList<String> leftFood = new ArrayList<>(food);
        Random rand = new Random();
        ArrayList<String> diet = new ArrayList<>();
        for (int i = 0; calories > 50; i++){
            if( leftFood.size() == 0){
                leftFood = new ArrayList<>(food);

            }
            int r = rand.nextInt(leftFood.size()) ;
            calories -= eatingProducts.get(leftFood.get(r)).nrCalories;

            String selectedFood = leftFood.remove(r);
            diet.add(eatingProducts.get(selectedFood).itemName);
        }
        return diet;
    }
    public  float getCalories (int age, float weight, float height, char gender ){
        if( gender == 'M'){
            return (66.47f + (13.75f * weight) +  (5 * height) - (6.75f * age));
        }
        else {
            return (665.09f + (9.56f * weight) +  (1.84f * height) - (4.67f * age));
        }
    }
    NutritionDiet(int age, float weight, float height, char gender , ArrayList<String> food){
        this.food = food;
        this.calories = getCalories(age, weight , height, gender);

        breakfastCalories = this.calories * 0.23f;
        lunchCalories = this.calories * 0.38f;
        dinnerCalories = this.calories * 0.38f;

        System.out.println(calories);
        usersFoodAndCalories = getFoodCalories();

        portions = new HashMap<>();


        portions.put("breakfast",foodCount(createDiet(usersFoodAndCalories, this.breakfastCalories)));
        portions.put("lunch",foodCount(createDiet(usersFoodAndCalories, this.lunchCalories)));
        portions.put("dinner",foodCount(createDiet(usersFoodAndCalories, this.dinnerCalories)));


        System.out.println(portions);
        portions.get("breakfast").keySet()

    }

    HashMap<String,HashMap<String,Integer>> getPortions(){
        return (HashMap<String, HashMap<String, Integer>>) portions.clone();
    }

    class FoodInfo{
        public float nrCalories;
        public String itemName;
        public String portion;

        FoodInfo(float nrCalories, String itemName,String portion){
            this.nrCalories = nrCalories;
            this.itemName = itemName;
            this.portion = portion;
        }

    }
}

