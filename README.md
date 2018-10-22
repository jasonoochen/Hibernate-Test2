# Hibernate-Test2
  
  	/*
	 * connection.isolation
	 * 事务隔离级别 
	 */  
    
	/*
	 * session.clear()
	 * 清理缓存
	 */
      
	/*
	 * session.refresh()
	 * refresh():会强制发送select语句，使session缓存中对象的状态和数据表中的状态一样。
	 */
	
	/*
	 * session.flush()
	 * flush:使数据表中的记录和Session缓存中的对象的状态保持一致，为了保持一致，可能会发送对应的SQL语句。
	 * 1.在Transaction的commit()方法中：先调用session的flush方法，再提交事务
	 * 2.flush()方法可能会发送SQL语句，但不会提交事务
	 * 3.注意：在未提交事务或显式的调用session.flush()方法之前，也有可能会进行flush()操作。
	 * 1).执行HQL或QBC查询，会先进行flush()操作，以得到数据表的最新的记录。
	 * 2).若记录的ID是由底层数据库使用自增的方式生成的，则在调用save()方法后，就会立即发送insert语句
	 * 因为save方法后，必须保证对象的ID是存在的。
	 */
	/*
	 * 1.session.save()
	 * 1).使一个临时对象变为持久化对象
	 * 2).为对象分配ID
	 * 3).在flush缓存时会发送一条insert语句
	 * 4).在save方法之前的id是无效的
	 * 5).持久化对象的ID是不能被修改的
	 */
	/*
	 * persist():也会执行insert操作
	 * 和save()的区别：
	 * 在调用persist方法之前，若对象已经有ID了，就不会执行insert,而是抛出异常
	 */
	/*
	 * session.get()
	 * session.load()
	 * 1.执行get()会立即加载对象（进行数据库查询，从数据库获取对象）（立即检索）
	 *   执行load方法，若不适用该对象，则不执行查询操作，而返回一个代理对象（延迟检索）
	 *   （不立即进行数据库查询，直到需要用了才加载对象）
	 * 2.若对数据表中没有的对应的记录，且session也没有被关闭，同时需要使用对象时：
	 *   get: 返回null
	 *   load: 若不适用该对象的任何属性，没问题；若需要初始化了，抛出异常
	 * 3.load方法可能会抛出LazyInitializationException异常
	 *   在需要初始化代理对象之前已经关闭了session,抛出懒加载异常
	 */
