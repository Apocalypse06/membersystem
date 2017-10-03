package controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Apocalypse.member.model.dao.MemberDAO;
import com.google.gson.Gson;

import model.MemberService;
import model.MemberBean;

@WebServlet("/change/ChangeMemberServlet")
public class ChangeMemberServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 定義存放錯誤訊息的 Collection物件
		Map<String, String> errorMessage = new HashMap<>();
		request.setAttribute("ErrorMsg", errorMessage);
		HttpSession session = request.getSession(false);
		// 設定輸入資料的編碼
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
				
		
		String mEmail = request.getParameter("mEmail");
		if (mEmail == null || mEmail.trim().length() == 0) {
			errorMessage.put("mEmail","Email欄必須輸入");
		}
		
		String mNick_Name = request.getParameter("mNick_Name");
		if (mNick_Name == null || mNick_Name.trim().length() == 0) {
			errorMessage.put("mNick_Name","暱稱欄必須輸入");
		}
		
		String mPswd = request.getParameter("mPswd");
		if (mPswd == null || mPswd.trim().length() == 0) {
			errorMessage.put("mPswd","密碼欄必須輸入");
		}
		
		String mBirthday = request.getParameter("mBirthday");
		if (mBirthday == null || mBirthday.trim().length() == 0) {
			errorMessage.put("mBirthday","生日欄必須輸入");
		}
		java.sql.Date mdate = null;
		if (mBirthday != null && mBirthday.trim().length() > 0) {
			try {
				mdate = java.sql.Date.valueOf(mBirthday);
			} catch (IllegalArgumentException e) {
				errorMessage.put("mBday","生日欄格式錯誤");
			}
		}
	
		String mGender = request.getParameter("mGender");
		if (mGender == null || mGender.trim().length() == 0) {
			errorMessage.put("mGender","性別欄必須輸入");
		}
		String mCellphone = request.getParameter("mCellphone");
		if (mCellphone == null || mCellphone.trim().length() == 0) {
			errorMessage.put("mCellphone","手機欄必須輸入");
		}
	
		// 如果輸入資料有錯誤，送回前端，請使用者修正
				if (!errorMessage.isEmpty()) {
					out.println(gson.toJson(errorMessage));
					out.close();
					return;
				}

		
		MemberBean obj = (MemberBean) session.getAttribute("LoginOK");
		String member_Id=obj.getMember_Id();
		String account=obj.getAccount();
		Date reg_date=obj.getReg_date();
		MemberService ms = new MemberService();
		MemberBean mb=null;
		

			MemberBean Mb = new MemberBean(member_Id,account,mPswd,mNick_Name,mdate,mCellphone,mEmail,mGender,reg_date);


		  try {
			  mb=ms.changeMember(Mb);
		  }catch(SQLException e) {
			  errorMessage.put("MySQL","連線有問題");
			  System.out.println("執行checkAccount()時,連線有問題");
		   }
		  
		  if (errorMessage.isEmpty()) {
				session.setAttribute("LoginOK", mb);
			    errorMessage.put("Success", "修改成功");

			} 
		  	out.println(gson.toJson(errorMessage));
			out.close();
			return;

			
	 }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
	
}
