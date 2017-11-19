package dao;

import java.util.List;

import model.RecipeIngredients;
import model.Unit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RecipeIngredientsDao implements DaoInterface {
			SessionFactory sessionfactory = HibernateUtil.getSessionFactory();

	RecipeIngredients ri = new RecipeIngredients();

	public List showRecipeIngredients(int id)
	{

		Session session;
		session = sessionfactory.openSession();
		session.beginTransaction();
        Query query = session.createQuery("FROM RecipeIngredients R WHERE R.recipe.id = :id"); 
        query.setInteger("id", id);
        List<RecipeIngredients> recipe = query.list();
        session.getTransaction().commit();
        session.close();
        
        System.out.println(recipe.getClass());
        return recipe;

	}
	
	
	@Override
	public void insert(Object ob) {

	}

	@Override
	public List view() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object ob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object checkdao(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object ob) {
		// TODO Auto-generated method stub
		
	}

}
