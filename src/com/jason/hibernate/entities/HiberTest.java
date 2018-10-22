package com.jason.hibernate.entities;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HiberTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
											.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
	}
	
	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	public void testClear() {
		News news1 = (News) session.get(News.class, 1);
		session.clear();
		News news2 = (News) session.get(News.class, 1);
	}
	
	/*
	 * connection.isolation
	 * ������뼶�� - 
	 */
	/*
	 * session.clear()
	 * ������
	 */
	/*
	 * session.refresh()
	 * refresh():��ǿ�Ʒ���select��䣬ʹsession�����ж����״̬�����ݱ��е�״̬һ����
	 */
	
	/*
	 * session.flush()
	 * flush:ʹ���ݱ��еļ�¼��Session�����еĶ����״̬����һ�£�Ϊ�˱���һ�£����ܻᷢ�Ͷ�Ӧ��SQL��䡣
	 * 1.��Transaction��commit()�����У��ȵ���session��flush���������ύ����
	 * 2.flush()�������ܻᷢ��SQL��䣬�������ύ����
	 * 3.ע�⣺��δ�ύ�������ʽ�ĵ���session.flush()����֮ǰ��Ҳ�п��ܻ����flush()������
	 * 1).ִ��HQL��QBC��ѯ�����Ƚ���flush()�������Եõ����ݱ�����µļ�¼��
	 * 2).����¼��ID���ɵײ����ݿ�ʹ�������ķ�ʽ���ɵģ����ڵ���save()�����󣬾ͻ���������insert���
	 * ��Ϊsave�����󣬱��뱣֤�����ID�Ǵ��ڵġ�
	 */
	
	@Test
	public void testSessionFlush() {
		News news = (News) session.get(News.class, 1);
		news.setAuthor("Oracle");
	}
	
	@Test
	public void test() {
		News news = (News) session.get(News.class, 1);
	}

}
