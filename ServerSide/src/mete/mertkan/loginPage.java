package mete.mertkan;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class loginPage
 */
@WebServlet("/loginPage")
public class loginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//User types
	private static final int NORMALUSER = 1;
	private static final int ADMINUSER = 2;
	private static final int GUESSUSER = 3;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public loginPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//Message Bundle base text
		ResourceBundle message=ResourceBundle.getBundle("MessageBundle");

		//validation control integer if its 0-Success 1-failure -1-missing_info
		int validationString;
		boolean allOk=true;

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		//Get the username and password from loginPage.jsp
		String username=request.getParameter("username");  
		String password=request.getParameter("userpass");


		//database object
		MySQLAccess accessControl= new MySQLAccess();

		//Server side validations
		Validations validationControl= new Validations();



		//if user input is not valid warn the user
		validationString=validationControl.verifyString(username);
		//Check for username
		if(validationString!=0) //not valid
			allOk=false;


		validationString=validationControl.verifyString(password);
		//check for password
		if(validationString!=0)//not-valid
			allOk=false;


		if(allOk){

			Connection con=null;
			con=accessControl.connect(con);

			try {
				if(accessControl.authenticate(con, username, password)){
					int[] idRoleid=accessControl.readIdWithRole(con, username);
					//set userId and Roleid
					User user=accessControl.readUsersInfo(con, username);
					user.setId(idRoleid[0]);
					user.setRoleId(idRoleid[1]);

					
					
					//re-direct other page with user object
					if(NORMALUSER==idRoleid[1]){
						String hash_name=accessControl.readHash_word(con, username);
						user.setHash_name(hash_name);
						request.setAttribute("user", user);
						request.getRequestDispatcher("/userPanel").forward(request,response);

					}
					else if(ADMINUSER==idRoleid[1]){
						request.getRequestDispatcher("adminPanel").forward(request,response);  
					}
					else if(GUESSUSER==idRoleid[1]){
						request.setAttribute("user",user);
						RequestDispatcher rd=request.getRequestDispatcher("/guestPanel");
						rd.forward(request,response);  
					}

				}else{
					out.print(message.getString("incorrectUorP"));  
					RequestDispatcher rd=request.getRequestDispatcher("/");  
					rd.include(request,response);  
				}
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			

		}
		else
		{
			out.print(message.getString("notValid"));  
			RequestDispatcher rd=request.getRequestDispatcher("loginPage.jsp");  
			rd.include(request,response);  
		}

	
	}

}