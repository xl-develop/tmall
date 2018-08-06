package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Order;
import tmall.util.Page;

public class OrderServlet extends BaseBackServlet{
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}
	public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Order o = orderDAO.get(id);
		o.setDeliveryDate(new Date());
		o.setStatus(orderDAO.waitConfirm);
		orderDAO.update(o);
		return "@admin_order_list";	
	}
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Order> os = orderDAO.list(page.getStart(), page.getCount());
		orderItemDAO.fill(os);
		int total = orderDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("os", os);
		request.setAttribute("page", page);
		
		return "admin/listOrder.jsp";
	}
}
