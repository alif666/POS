package dao;

import model.Recipe;

public class NewClass {
    
    public static void main(String args[]){

         
            Recipe recipe = new Recipe();
            RecipeDao recipeDao = new RecipeDao();
            recipeDao.insert(recipe);
    }
}
