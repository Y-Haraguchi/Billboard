package billboard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import billboard.beans.User;
import billboard.service.UserService;

@WebFilter(urlPatterns = {"/editUser", "/signup", "/usersManager"})
public class UsersRoleCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest)request).getSession();
		List<String> errorMessages = new ArrayList<String>();

		if(isValid(request, errorMessages) == true) {
			chain.doFilter(request, response);
		} else {
			session.setAttribute("accessErrorMessages", errorMessages);
			((HttpServletResponse)response).sendRedirect("home");
		}
	}

	public boolean isValid(ServletRequest request,
			List<String> errorMessages) {
		HttpSession session = ((HttpServletRequest)request).getSession();
		//現在ログインしているユーザー
		User loginUser = (User)session.getAttribute("loginUser");
		User latestUser = new User();

		if(loginUser != null) {
			//ログイン済のユーザーの最新情報
			latestUser = new UserService().getUser(loginUser.getId());
		}

		if(loginUser.getBranchId() != 1 || loginUser.getAssignTypeId() != 1) {
			errorMessages.add("アクセス権限がありません");
		} else if(latestUser.getBranchId() != 1 || latestUser.getAssignTypeId() != 1) {
			errorMessages.add("アクセス権限がありません");
		}
		if(errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {

	}

}
