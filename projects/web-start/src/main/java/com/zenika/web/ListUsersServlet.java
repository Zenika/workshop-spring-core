/**
 * 
 */
package com.zenika.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zenika.business.UserService;
import com.zenika.domain.User;

/**
 * @author acogoluegnes
 *
 */
public class ListUsersServlet extends HttpServlet {
	
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		// TODO 02 récupérer le contexte Spring, puis le bean UserService pour l'assigner à la propriété userService
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		userService = ctx.getBean(UserService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 03 analyser le code de la servlet
		// il liste les Users dans un tablea HTML
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head><title>Spring Workshop</title></head>");
		builder.append("<table><tr><th>ID</th><th>Login</th><th>Password</th></tr>");
		for(User user : userService.list()) {
			builder.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>",user.getId(),user.getLogin(),user.getPassword()));
		}
		builder.append("</table>");
		resp.getWriter().append(builder.toString());
		resp.getWriter().flush();
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
