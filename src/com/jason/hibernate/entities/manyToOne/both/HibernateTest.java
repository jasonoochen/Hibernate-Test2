package com.jason.hibernate.entities.manyToOne.both;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				                            .buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testDelete(){
		//在不设定级联关系的情况下, �? 1 这一端的对象�? n 的对象在引用, 不能直接删除 1 这一端的对象
		Customer customer = (Customer) session.get(Customer.class, 1);
		session.delete(customer); 
	}
	
	@Test
	public void testUpdate(){
		Order order = (Order) session.get(Order.class, 1);
		order.getCustomer().setCustomerName("AAA");
	}
	
	@Test
	public void testMany2OneGet(){
		//1. 若查询多的一端的�?个对�?, 则默认情况下, 只查询了多的�?端的对象. 而没有查询关联的
		//1 的那�?端的对象!
		Order order = (Order) session.get(Order.class, 1);
		System.out.println(order.getOrderName()); 
		
		System.out.println(order.getCustomer().getClass().getName());
		
		session.close();
		
		//2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句. 
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomerName()); 
		
		//3. 在查�? Customer 对象�?, 由多的一端导航到 1 的一端时, 
		//若此�? session 已被关闭, 则默认情况下
		//会发�? LazyInitializationException 异常
		
		//4. 获取 Order 对象�?, 默认情况�?, 其关联的 Customer 对象是一个代理对�?!
		
	}
	
	@Test
	public void testMany2OneSave(){
		Customer customer = new Customer();
		customer.setCustomerName("BB");
		
		Order order1 = new Order();
		order1.setOrderName("ORDER-3");
		
		Order order2 = new Order();
		order2.setOrderName("ORDER-4");
		
		//设定关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//执行  save 操作: 先插�? Customer, 再插�? Order, 3 �? INSERT
		//先插�? 1 的一�?, 再插�? n 的一�?, 只有 INSERT 语句.
//		session.save(customer);
//		
//		session.save(order1);
//		session.save(order2);
		
		//先插�? Order, 再插�? Customer. 3 �? INSERT, 2 �? UPDATE
		//先插�? n 的一�?, 再插�? 1 的一�?, 会多�? UPDATE 语句!
		//因为在插入多的一端时, 无法确定 1 的一端的外键�?. �?以只能等 1 的一端插入后, 再额外发�? UPDATE 语句.
		//推荐先插�? 1 的一�?, 后插�? n 的一�?
		session.save(order1);
		session.save(order2);
		
		session.save(customer);
	}

}
