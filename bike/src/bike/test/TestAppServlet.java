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
		System.out.println("���յ��ͻ��˵ķ��ʣ�");
		String param = req.getParameter("usernam");
        System.out.println(param);
        String param1 = req.getParameter("password");
        System.out.println(param1);
        PrintWriter writer = resp.getWriter();
		writer.write("�ֽ���success");//���������ͻ��˷�����Ӧ���ַ���,�ͻ��˿���String res=response.body().string();�����ո�ֵ
		writer.flush();
		writer.close();//ע��ˢ�º͹رջ���

	}

}
