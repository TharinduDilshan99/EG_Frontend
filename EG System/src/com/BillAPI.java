package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class BillAPI
 */
@WebServlet("/BillAPI")
@MultipartConfig

public class BillAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Bill bill = new Bill();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		String UserName = request.getParameter("UserName");
		String UserAddress = request.getParameter("UserAddress");
		int UnitCount = Integer.parseInt(request.getParameter("UnitCount"));
		String BillAmount = request.getParameter("BillAmount");
		String DueAmount = request.getParameter("DueAmount");
		String Date = request.getParameter("Date");

		String output = bill.insertBill(UserName, UserAddress, UnitCount, BillAmount, DueAmount, Date);
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int BillID = Integer.parseInt(request.getParameter("hidBillIDSave"));
		String UserName = request.getParameter("UserName");
		String UserAddress = request.getParameter("UserAddress");
		int UnitCount = Integer.parseInt(request.getParameter("UnitCount"));
		String BillAmount = request.getParameter("BillAmount");
		String DueAmount = request.getParameter("DueAmount");
		String Date = request.getParameter("Date");

		String output = bill.updateBill(BillID, UserName, UserAddress, UnitCount, BillAmount, DueAmount, Date);
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		//String output = bill.deleteBill(paras.get("BillID").toString());
		int BillID = Integer.parseInt(paras.get("billID").toString());
		String output = bill.deleteBill(BillID);
		response.getWriter().write(output);
	}
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				 String queryString = scanner.hasNext() ?
				 scanner.useDelimiter("\\A").next() : "";
				 scanner.close();
				 String[] params = queryString.split("&");
				 for (String param : params)
				 { 
					 String[] p = param.split("=");
					 map.put(p[0], p[1]);
				}
			}catch (Exception e){
				
			}
				return map;
		}



}
