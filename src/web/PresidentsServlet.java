package web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.President;
import data.PresidentsDaoImpl;


public class PresidentsServlet extends HttpServlet {
	PresidentsDaoImpl president;
	List<President> pres = new ArrayList();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("Welcome.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		boolean filtered = new Boolean(req.getParameter("filter"));
	   
		if(req.getParameter("result") != null){
			if(req.getParameter("result").equals("")){
				president.setTracker(44);
			} else{
			president.setTracker(president.getTracker() + Integer.parseInt(req.getParameter("result")));
			}
		}
		
		if(pres.size() <=0){
			pres= president.loadPresidentsFromFile(this.getServletContext());
		} 
		
		if(filtered){
			//pres= president.loadPresidentsFromFile(this.getServletContext());
		} else{
			pres.clear();
			pres = president.loadPresidentsFromFile(this.getServletContext());
		}
		
		if(req.getParameter("filterNum") != null && !req.getParameter("filterNum").equals("")){
			president.setTracker(Integer.parseInt(req.getParameter("filterNum"))-1);
		}
		
		System.out.println(pres.size());
		req.setAttribute("presidents", pres);
		req.setAttribute("president", president);
		req.setAttribute("tracker", president.getTracker());
		req.setAttribute("pres", pres.get(president.getTracker()) );
		req.getRequestDispatcher("Presidents.jsp").forward(req, resp);
	}

	@Override
	public void init() throws ServletException {
		
		president = new PresidentsDaoImpl(this.getServletContext());
		
	}
	
	
	
}
