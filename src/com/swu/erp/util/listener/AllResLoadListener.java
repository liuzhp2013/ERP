package com.swu.erp.util.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.swu.erp.auth.res.business.ebi.ResEbi;
import com.swu.erp.auth.res.vo.ResModel;

public class AllResLoadListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		//容器初始化时查询所有资源信息，并将结果保存在servletContext中，
		//下次需要获取资源列表时，可直接从servletContext中取，
		//由于资源列表中的信息比较固定，且使用频繁，故在容器初始化时将其取出并保存到servletContext域中，
		//这种做法能有效的避免频繁地向数据库中取数据，从而大大提升系统性能
		ServletContext sc = event.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		ResEbi resEbi = (ResEbi) ctx.getBean("resEbi");
		
		List<ResModel> allRes = resEbi.getAll();
		StringBuilder sbd = new StringBuilder();
		for (ResModel res : allRes) {
			sbd.append(res.getUrl());
			sbd.append(",");
		}
		
		//放入servletContext中
		sc.setAttribute("allRes", sbd.toString());
	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}
}
