package dao;

import java.util.List;

interface DaoInterface {
   public void insert(Object ob); 
   public List view();
   public void delete(Object ob);
   public Object checkdao(int id);
   public void update(Object ob);

}
