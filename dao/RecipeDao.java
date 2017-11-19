package dao;

import java.util.List;

import model.Recipe;
import model.Unit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RecipeDao implements DaoInterface {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Recipe recipe=new Recipe();
	@Override
	public void insert(Object ob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List view() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Recipe ORDER BY Recipe.name");   
        List<Recipe> recipe= query.list();
        session.getTransaction().commit();
        session.close();
        
        return recipe;
	}

	@Override
	public void delete(Object ob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object checkdao(int id) {

		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		recipe = (Recipe) session.get(Recipe.class,id);
		session.getTransaction().commit();
		//session.close();
		return recipe;
	}

	@Override
	public void update(Object ob) {
		// TODO Auto-generated method stub
		
	}
        
        public List showRecipeById(int id)
	{

		Session session;
		session = sessionFactory.openSession();
		session.beginTransaction();
                Query query = session.createQuery("FROM Recipe R WHERE R.category.id = :id ORDER BY R.name"); 
                query.setInteger("id", id);
                List<Recipe> recipe = query.list();
                session.getTransaction().commit();
                session.close();

                //System.out.println(recipe.getClass());
                return recipe;

	}
	

}
