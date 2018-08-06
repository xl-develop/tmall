package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDAO {
	public int getTotal() {
		int total = 0;
		/*连接到数据库（Connection）、建立操作指令（Statement）、执行查询指令（executeQuery）、获得查询结果（ResultSet）等。
		
		声名类（statement）（依次继承而来）
		（1）Statement对象用于执行不带参数的简单的SQL语句；Statement接口提供了执行语句和获取结果的基本方法。

		（2）PerparedStatement对象用于执行带或不带IN参数的预编译SQL语句；PeraredStatement接口添加处理IN参数的方法；

		（3）CallableStatement对象用于执行对数据库已存储过程的调用；CallableStatement添加处理OUT参数的方法
		
		Statement提供了许多方法，最常用的方法如下：
		（1）execute()方法：运行语句，返回是否有结果集。

		（2）executeQuery()方法：运行查询语句，返回ReaultSet对象。

		（3）executeUpdata()方法：运行更新操作，返回更新的行数。

		（4）addBatch()方法：增加批处理语句。

		（5）executeBatch()方法：执行批处理语句。

		（6）clearBatch()方法：清除批处理语句。*/
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			
			String sql = "select count(*) from Category";
			
			ResultSet rs = s.executeQuery(sql);//rs：数据集
			while (rs.next()) {                //获取下一列
				total = rs.getInt(1);          //通过索引和列名来获取某一列的值
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Category bean) {
		
		String sql = "insert into category values(null,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());         //第一个参数具体指哪个“？”，第二个参数替换的值
			ps.execute();
			ResultSet re = ps.getGeneratedKeys();    //获取主键
			
			if (re.next()) {
				int id = re.getInt(1);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void update(Category bean) {
		
		String sql = "update category set name= ? where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());                           //设置第几个参数
			
			ps.execute();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id) {
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			
			String sql = "delete form Category where id = " + id;
			s.execute(sql);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public Category get(int id) {
		Category bean = null;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select * from category where id = " + id;
			
			ResultSet rs = s.executeQuery(sql);
			
			if(rs.next()) {
				bean = new Category();
				String name = rs.getString(2);//获取第二列的数据
				bean.setName(name);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bean;
		
	}
	
	public List<Category> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<Category> list(int start, int count) {
		// TODO Auto-generated method stub
		List<Category> beans = new  ArrayList<Category>();
		
		String sql = "select * from category order by id desc limit ?,?";
		
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Category bean = new Category();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				bean.setId(id);
				bean.setName(name);
				beans.add(bean);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return beans;
	}

}
