package cn.xz.web.跨域;

import javax.servlet.http.HttpServletResponse;

public class 跨域演示 {
	public void 演示(HttpServletResponse response) {
		response .setHeader("Access-Control-Allow-Origin", "*");
	}
}
