package com.swu.erp.util.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.auth.menu.vo.MenuModel;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.invoice.order.vo.OrderModel;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailModel;
import com.swu.erp.invoice.supplier.vo.SupplierModel;

public class GeneratorUtil {
	private Class clazz;
	private String big;
	private String first;
	private String small;
	private String pkg;
	private String dir;

	public static void main(String[] args) throws Exception {
		//EmpModel,ReloModel,ResModel,MenuModel
		//SupplierModel,GoodsModel,OrderModel,OrderDetailModel
		new GeneratorUtil(OrderDetailModel.class);
		System.out.println("struts.xml未进行映射");
		System.out.println("hbm.xml未添加关联信息");
		System.out.println("QueryModel未添加自定义范围查询条件");
		System.out.println("DaoImpl中未对自定义查询条件进行设置");
	}

	// 根据Model类在构造方法中生成对应模块的各层所有代码
	public GeneratorUtil(Class clazz) throws Exception {
		this.clazz = clazz;
		// 1，数据初始化
		dataInit();
		// 2，创建目录
		generatorDirectory();

		// 3，生成QueryModel
		generatorQueryModel();

		// 4,生成hbm.xml配置文件
		generatorHbmXml();

		// 5，生成DaoR
		generatorDao();

		// 6，生成DaoImpl
		generatorDaoImpl();

		// 7，生成Ebi
		gerneratorEbi();

		// 8，生成Ebo
		generatorEbo();

		// 9，生成Actioin
		generatorAction();

		// 10，生成ApplicationContextXML
		generatorApplicationContextXML();
	}

	// 1，数据初始化
	private void dataInit() {
		// 获取Model的基名
		getBaseNameOfModel();
		// 根据报名获取对应的文件路径
		getDirByPkg();
	}

	// 2，创建目录
	private void generatorDirectory() {
		// 生成目录 src/com/erp/auth/emp/dao/dao
		File f = new File("src/" + dir + "/dao/dao");
		if (f.exists()) { return; }
		f.mkdirs();

		// 生成目录 src/com/erp/auth/emp/dao/impl
		f = new File("src/" + dir + "/dao/impl");
		if (f.exists()) { return; }
		f.mkdirs();

		// 生成目录 src/com/erp/auth/emp/business/ebiR
		f = new File("src/" + dir + "/business/ebi");
		if (f.exists()) { return; }
		f.mkdirs();

		// 生成目录 src/com/erp/auth/emp/business/ebo
		f = new File("src/" + dir + "/business/ebo");
		if (f.exists()) { return; }
		f.mkdirs();

		// 生成目录 src/com/erp/auth/emp/web
		f = new File("src/" + dir + "/web");
		if (f.exists()) { return; }
		f.mkdirs();
	}

	// 3，生成QueryModel
	private void generatorQueryModel() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/vo/" + big + "QueryModel.java");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		
		if (f.exists()) { return; }
		 

		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("package " + pkg + ".vo;");
		bw.newLine();

		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseQueryModel;");
		bw.newLine();

		bw.newLine();

		bw.write("public class " + big + "QueryModel extends " + big
				+ "Model implements BaseQueryModel{ ");
		bw.newLine();

		bw.write("	//TODO 添加自定义查询条件");
		bw.newLine();

		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();
	}

	// 4, 生成hbm.xml配置文件
	private void generatorHbmXml() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/vo/" + big + "Model.hbm.xml");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }
		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();

		bw.write("<!DOCTYPE hibernate-mapping PUBLIC ");
		bw.newLine();

		bw.write("    '-//Hibernate/Hibernate Mapping DTD 3.0//EN'");
		bw.newLine();

		bw.write("    'http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd'>");
		bw.newLine();

		bw.newLine();

		bw.write("<hibernate-mapping>");
		bw.newLine();

		bw.write("	<class name=\"" + pkg + ".vo." + big
				+ "Model\" table=\"tbl_" + small + "\">");
		bw.newLine();

		bw.write("		<id name=\"uuid\" column=\"uuid\">");
		bw.newLine();

		bw.write("			<generator class=\"native\"/>");
		bw.newLine();

		bw.write("		</id>");
		bw.newLine();

		// 反射获取模型中的所有字段，用于配置Hibernate映射文件
		Field[] fds = clazz.getDeclaredFields();
		for (Field fd : fds) {
			// 如果字段的修饰符是private则生成
			if (fd.getModifiers() == Modifier.PRIVATE
					&& !fd.getName().equals("uuid")) {
				// 如果是关联关系字段则不生成，否则生成
				if (fd.getType().equals(String.class)
						|| fd.getType().equals(Long.class)
						|| fd.getType().equals(Integer.class)
						|| fd.getType().equals(Double.class)) {
					if(!fd.getName().endsWith("View")) {
						bw.write("		<property name=\"" + fd.getName() + "\"/>");
						bw.newLine();
					}
				}
			}
		}

		bw.write("	</class>");
		bw.newLine();

		bw.write("</hibernate-mapping>");
		bw.newLine();

		bw.flush();
		bw.close();

	}

	// 5，生成Dao
	private void generatorDao() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/dao/dao/" + big + "Dao.java");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }
		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("package " + pkg + ".dao.dao;");
		bw.newLine();

		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "Model;");
		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseDao;");
		bw.newLine();

		bw.newLine();

		bw.write("public interface " + big + "Dao extends BaseDao<" + big
				+ "Model>{");
		bw.newLine();

		bw.newLine();

		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();
	}

	// 6，生成DaoImpl
	private void generatorDaoImpl() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/dao/impl/" + big + "DaoImpl.java");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }
		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("package " + pkg + ".dao.impl;");
		bw.newLine();

		bw.newLine();

		bw.write("import org.hibernate.criterion.DetachedCriteria;");
		bw.newLine();

		bw.write("import org.hibernate.criterion.Restrictions;");
		bw.newLine();

		bw.write("import " + pkg + ".dao.dao." + big + "Dao;");
		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "Model;");
		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "QueryModel;");
		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseDaoImpl;");
		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseQueryModel;");
		bw.newLine();

		bw.newLine();

		bw.write("public class " + big + "DaoImpl extends BaseDaoImpl<" + big
				+ "Model> implements " + big + "Dao {");
		bw.newLine();

		bw.write("	public void doQBC(DetachedCriteria dc, BaseQueryModel bqm) {");
		bw.newLine();

		bw.write("		" + big + "QueryModel "+first+"qm = (" + big + "QueryModel) bqm;");
		bw.newLine();

		bw.write("		//TODO 添加自定义查询条件");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();
	}

	// 7，生成Ebi
	private void gerneratorEbi() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/business/ebi/" + big + "Ebi.java");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }
		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("package " + pkg + ".business.ebi;");
		bw.newLine();

		bw.newLine();

		bw.write("import org.springframework.transaction.annotation.Transactional;");
		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "Model;");
		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseEbi;");
		bw.newLine();

		bw.newLine();

		bw.write("@Transactional");
		bw.newLine();

		bw.write("public interface " + big + "Ebi extends BaseEbi<" + big
				+ "Model>{");
		bw.newLine();

		bw.newLine();

		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();
	}

	// 8，生成Ebo
	private void generatorEbo() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/business/ebo/" + big + "Ebo.java");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }
		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("package " + pkg + ".business.ebo;");
		bw.newLine();

		bw.newLine();

		bw.write("import java.io.Serializable;");
		bw.newLine();

		bw.write("import java.util.List;");
		bw.newLine();

		bw.write("import " + pkg + ".business.ebi." + big + "Ebi;");
		bw.newLine();

		bw.write("import " + pkg + ".dao.dao." + big + "Dao;");
		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "Model;");
		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseQueryModel;");
		bw.newLine();

		bw.newLine();

		bw.write("public class " + big + "Ebo implements " + big + "Ebi {");
		bw.newLine();

		bw.write("	private " + big + "Dao " + small + "Dao;");
		bw.newLine();

		bw.write("	public void set" + big + "Dao(" + big + "Dao " + small
				+ "Dao) {");
		bw.newLine();

		bw.write("		this." + small + "Dao = " + small + "Dao;");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public void save(" + big + "Model "+first+"m) {");
		bw.newLine();

		bw.write("		" + small + "Dao.save("+first+"m);");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public void delete(" + big + "Model "+first+"m) {");
		bw.newLine();

		bw.write("		" + small + "Dao.delete("+first+"m);");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.write("	public void update(" + big + "Model "+first+"m) {");
		bw.newLine();

		bw.write("		" + small + "Dao.update("+first+"m);");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public " + big + "Model get(Serializable uuid) {");
		bw.newLine();

		bw.write("		return " + small + "Dao.get(uuid);");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public List<" + big + "Model> getAll() {");
		bw.newLine();

		bw.write("		return " + small + "Dao.getAll();");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public List<" + big
				+ "Model> getAll(BaseQueryModel bqm, Integer curPageNum,");
		bw.newLine();

		bw.write("			Integer pageCount) {");
		bw.newLine();

		bw.write("		return " + small + "Dao.getAll(bqm,curPageNum,pageCount);");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public Integer getCount(BaseQueryModel bqm) {");
		bw.newLine();

		bw.write("		return " + small + "Dao.getCount(bqm);");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();

	}

	// 9，生成Actioin
	private void generatorAction() throws Exception {
		// 创建文件
		File f = new File("src/" + dir + "/web/" + big + "Action.java");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }
		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("package " + pkg + ".web;");
		bw.newLine();

		bw.newLine();

		bw.write("import java.util.List;");
		bw.newLine();

		bw.write("import " + pkg + ".business.ebi." + big + "Ebi;");
		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "Model;");
		bw.newLine();

		bw.write("import " + pkg + ".vo." + big + "QueryModel;");
		bw.newLine();

		bw.write("import com.swu.erp.util.base.BaseAction;");
		bw.newLine();

		bw.newLine();

		bw.write("public class " + big + "Action extends BaseAction{ ");
		bw.newLine();

		bw.write("	public " + big + "Model "+first+"m = new " + big + "Model();");
		bw.newLine();

		bw.write("	public " + big + "QueryModel "+first+"qm = new " + big
				+ "QueryModel();");
		bw.newLine();

		bw.newLine();

		bw.write("	private " + big + "Ebi " + small + "Ebi;");
		bw.newLine();

		bw.write("	public void set" + big + "Ebi(" + big + "Ebi " + small
				+ "Ebi) {");
		bw.newLine();

		bw.write("		this." + small + "Ebi = " + small + "Ebi;");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public String input() {");
		bw.newLine();

		bw.write("		if("+first+"m.getUuid() != null) {");
		bw.newLine();

		bw.write("			"+first+"m = " + small + "Ebi.get("+first+"m.getUuid());");
		bw.newLine();

		bw.write("		}");
		bw.newLine();

		bw.write("		return BaseAction.INPUT;");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public String save() {");
		bw.newLine();

		bw.write("		if("+first+"m.getUuid() == null) {");
		bw.newLine();

		bw.write("			" + small + "Ebi.save("+first+"m);");
		bw.newLine();

		bw.write("		} else {");
		bw.newLine();

		bw.write("			" + small + "Ebi.update("+first+"m);");
		bw.newLine();

		bw.write("		}");
		bw.newLine();

		bw.newLine();

		bw.write("		return BaseAction.TO_LIST;");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public String delete() {");
		bw.newLine();

		bw.write("		" + small + "Ebi.delete("+first+"m);");
		bw.newLine();

		bw.write("		return BaseAction.TO_LIST;");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.newLine();

		bw.write("	public String list() {");
		bw.newLine();

		bw.write("		this.initPageDate(" + small + "Ebi.getCount("+first+"qm));");
		bw.newLine();

		bw.write("		List<" + big + "Model> " + small + "List = " + small
				+ "Ebi.getAll("+first+"qm,curPageNum,pageCount);");
		bw.newLine();

		bw.write("		this.put(\"" + small + "List\", " + small + "List);");
		bw.newLine();

		bw.write(" 		return BaseAction.LIST;");
		bw.newLine();

		bw.write("	}");
		bw.newLine();

		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();
	}
	
	//10, 生成ApplicationContext.xml
	private void generatorApplicationContextXML() throws Exception {
		// 创建文件
		File f = new File("resources/applicationContext-" + small + ".xml");

		// 判断，如果文件存在，终止操作(防止生成的代码覆盖原有代码)
		if (f.exists()) { return; }

		f.createNewFile();

		// 通过IO向文件中写入内容
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();

		bw.write("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
		bw.newLine();

		bw.write("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		bw.newLine();

		bw.write("	xsi:schemaLocation=\"");
		bw.newLine();

		bw.write("		http://www.springframework.org/schema/beans ");
		bw.newLine();

		bw.write("		http://www.springframework.org/schema/beans/spring-beans.xsd");
		bw.newLine();

		bw.write("		\">");
		bw.newLine();

		bw.write("		<!-- Action -->");
		bw.newLine();

		bw.write("		<bean id=\"" + small + "Action\" class=\"" + pkg + ".web."
				+ big + "Action\" scope=\"prototype\">");
		bw.newLine();

		bw.write("			<property name=\"" + small + "Ebi\" ref=\"" + small
				+ "Ebi\"/>");
		bw.newLine();

		bw.write("		</bean>");
		bw.newLine();

		bw.newLine();

		bw.write("		<!-- Ebi -->");
		bw.newLine();

		bw.write("		<bean id=\"" + small + "Ebi\" class=\"" + pkg
				+ ".business.ebo." + big + "Ebo\">");
		bw.newLine();

		bw.write("			<property name=\"" + small + "Dao\" ref=\"" + small
				+ "Dao\"/>");
		bw.newLine();

		bw.write("		</bean>");
		bw.newLine();

		bw.newLine();

		bw.write("		<!-- Dao -->");
		bw.newLine();

		bw.write("		<bean id=\"" + small + "Dao\" class=\"" + pkg
				+ ".dao.impl." + big + "DaoImpl\">");
		bw.newLine();

		bw.write("			<property name=\"sessionFactory\" ref=\"sessionFactory\"/>");
		bw.newLine();

		bw.write("		</bean>");
		bw.newLine();

		bw.write("</beans>");
		bw.newLine();

		bw.flush();
		bw.close();

	}

	// 根据报名获取对应的文件路径
	private void getDirByPkg() {
		String pkgName = clazz.getPackage().getName();
		pkg = pkgName.substring(0, pkgName.length() - 3);
		dir = pkg.replace(".", "/");
	}

	// 获取Model的基名
	private void getBaseNameOfModel() {
		String className = clazz.getSimpleName();
		big = className.substring(0, className.length() - 5);
		first = big.substring(0, 1).toLowerCase();
		small = first + big.substring(1);
		
	}

}
