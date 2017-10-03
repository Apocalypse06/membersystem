package controler;

import java.io.IOException;
import java.sql.SQLException;
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

import Email.JavaMailMain;
import model.MemberBean;
import model.MemberService;


@WebServlet("/controler/ForgetPswdServlet")
public class ForgetPswdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> errorMessage = new HashMap<>();
		HttpSession session = request.getSession();
		request.setAttribute("ErrorMsg", errorMessage);
		request.setCharacterEncoding("UTF-8");
		MemberService ms = new MemberService();
		MemberBean mb=null;
		
		String mEmail = request.getParameter("mEmail");
		if (mEmail == null || mEmail.trim().length() == 0) {
			errorMessage.put("mEmail","Email欄必須輸入");
		}
		
		if (!errorMessage.isEmpty()) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/ForgetPswd/ForgetPswd.jsp");
			rd.forward(request, response);

			return;
		}
		
		
		try {	
			mb = ms.select_by_email(mEmail);
			if(mb!=null) {
				//將會員資料放入request(識別字串為EmailOK)
				session.setAttribute("EmailOK", mb);
				String member_Id = mb.getMember_Id();
				String mAccount = mb.getAccount();
				//發送驗證信
				JavaMailMain mail=new JavaMailMain();
				mail.sendmail_ChangePswd(member_Id,mAccount);
			}else{
				errorMessage.put("mEmail","此Email不存在");
			}
			
		}catch(SQLException e ){
			errorMessage.put("mEmail","連線有問題");
			System.out.println("連線有問題");
		}
			
			
		if (errorMessage.isEmpty()) {
			// 如果 errorMsgMap 是空的，表示帳密正確，發送請求道回到首頁(要是登入狀態)
				response.sendRedirect(
			    response.encodeRedirectURL(request.getContextPath() + "/ForgetPswd/ForgetPswd.jsp" ));
			return;
		} else {
			// 如果 errorMsgMap 不是空的，表示有錯誤，交棒給/ch06_01/login.jsp
			RequestDispatcher rd = request
					.getRequestDispatcher("/ForgetPswd/ForgetPswd.jsp");
			rd.forward(request, response);
			return;
		}
			
			
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
