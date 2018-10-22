package com.jason.hibernate.entities;

import static org.junit.Assert.*;

import java.util.Date;

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
	/*
	 * 1.session.save()
	 * 1).ʹһ����ʱ�����Ϊ�־û�����
	 * 2).Ϊ�������ID
	 * 3).��flush����ʱ�ᷢ��һ��insert���
	 * 4).��save����֮ǰ��id����Ч��
	 * 5).�־û������ID�ǲ��ܱ��޸ĵ�
	 */
	/*
	 * persist():Ҳ��ִ��insert����
	 * ��save()������
	 * �ڵ���persist����֮ǰ���������Ѿ���ID�ˣ��Ͳ���ִ��insert,�����׳��쳣
	 */
	/*
	 * session.get()
	 * session.load()
	 * 1.ִ��get()���������ض��󣨽������ݿ��ѯ�������ݿ��ȡ���󣩣�����������
	 *   ִ��load�������������øö�����ִ�в�ѯ������������һ����������ӳټ�����
	 *   ���������������ݿ��ѯ��ֱ����Ҫ���˲ż��ض���
	 * 2.�������ݱ���û�еĶ�Ӧ�ļ�¼����sessionҲû�б��رգ�ͬʱ��Ҫʹ�ö���ʱ��
	 *   get: ����null
	 *   load: �������øö�����κ����ԣ�û���⣻����Ҫ��ʼ���ˣ��׳��쳣
	 * 3.load�������ܻ��׳�LazyInitializationException�쳣
	 *   ����Ҫ��ʼ���������֮ǰ�Ѿ��ر���session,�׳��������쳣
	 */
	
	@Test
	public void testLoad(){
		
		News news = (News) session.load(News.class, 10);
		System.out.println(news.getClass().getName()); 
		
//		session.close();
//		System.out.println(news); 
	}
	@Test
	public void testGet(){
		News news = (News) session.get(News.class, 1);
//		session.close();
		System.out.println(news); 
	}
	@Test
	public void testPersist(){
		News news = new News();
		news.setTitle("EE");
		news.setAuthor("ee");
		news.setDate(new Date());
		news.setId(200); 
		
		session.persist(news); 
	}
	
	@Test
	public void testSave(){
		News news = new News();
		news.setTitle("CC");
		news.setAuthor("cc");
		news.setDate(new Date());
		news.setId(100); //��save()֮ǰsetID��Ч
		
		System.out.println(news);		
		session.save(news);
		System.out.println(news);
//		news.setId(101); //�־û������ID�ǲ��ܱ��޸ĵ�
	}
	
	@Test
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
