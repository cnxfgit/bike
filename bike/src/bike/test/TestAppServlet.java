package bike.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;


@WebServlet("/TestAppServlet")
public class TestAppServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void test(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("接收到客户端的访问：");
		String param = req.getParameter("usernam");
        System.out.println(param);
        String param1 = req.getParameter("password");
        System.out.println(param1);
        PrintWriter writer = resp.getWriter();
		writer.write("字节流success");//这里可以向客户端返回相应的字符串,客户端可用String res=response.body().string();来接收该值
		writer.flush();
		writer.close();//注意刷新和关闭缓存

	}

}
