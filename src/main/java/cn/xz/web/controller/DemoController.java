package cn.xz.web.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("aaa")
public class DemoController {

	private Integer num = 1;

	@RequestMapping("")
	@ResponseBody
	public String aaa() {
		System.out.println(num + Thread.currentThread().getName());
		return LocalDate.now().toString();
	}
}
