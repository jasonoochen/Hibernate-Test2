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
	/*
	 * session.update()
	 * 1.若更新一个持久化对象，不需要显示的调用update方法，因为在调用transaction
	 *   的commit()方法时，会先执行session的flush方法。
	 * 2.更新一个游离对象，需要显式的调用session的update方法。
	 *   可以把一个游离对象变为持久化对象。(重新放入session中)
	 * 
	 * 注意：
	 * 1.无论要更新的游离对象和数据表的记录是否一致，都会发送update语句。
	 *   如何让update方法不再盲目的发出update语句？
	 *   在.hbm.xml 文件的class节点设置 select-before-update = "true"(默认为false)。
	 *   通常不需要设置该属性(会多发送select语句，降低效率（一般情况下）)
	 * 2.若数据表中没有对应的记录，但还是调用了update方法，会抛出异常
	 * 3.当update()方法关联一个游离对象时，如果在session的缓存中已经存在相同OID的持久化对象，会抛出异常
	 *   在session缓存中，不能有两个OID相同的对象。
	 */
	 /*
	 * session.saveOrUpdate();
	 * 按OID来判断，若对象的OID为null，进行save操作，若对象的OID有值，进行update操作
	 * 1.若OID不为null,但数据表中还没有和其对应的记录，会抛出异常
	 * 2.了解：OID值等于ID的unsaved-value属性值的对象，也会被认为是一个游离对象
	 */
	 /*
	 * session.delete()
	 * 执行删除操作，只要OID和数据表中一条记录对应，就会执行delete操作
	 * 若OID在数据表中没有对应的记录，抛出异常
	 * 
	 * 可以通过设置hibernate配置文件hibernate.use_identifier_rollback为true
	 * 使删除对象后，把其OID设为null
	 */
	 /*
	 * session.evict() 从session缓存中把指定的持久化对象移除
	 */
2. 映射 Java 的时间, 日期类型

1). 两个基础知识:

I. 在 Java 中, 代表时间和日期的类型包括: java.util.Date 和 java.util.Calendar. 
此外, 在 JDBC API 中还提供了 3 个扩展了 java.util.Date 类的子类: java.sql.Date, java.sql.Time 
和 java.sql.Timestamp, 这三个类分别和标准 SQL 类型中的 DATE, TIME 和 TIMESTAMP 类型对应

II. 在标准 SQL 中, DATE 类型表示日期, TIME 类型表示时间, TIMESTAMP 类型表示时间戳, 同时包含日期和时间信息. 

2). 如何进行映射 ?

I. 因为 java.util.Date 是 java.sql.Date, java.sql.Time 和 java.sql.Timestamp 的父类, 所以 java.util.Date
可以对应标准 SQL 类型中的 DATE, TIME 和 TIMESTAMP

II. 基于 I, 所以在设置持久化类的 Date 类型是, 设置为 java.util.Date. 

III. 如何把 java.util.Date 映射为 DATE, TIME 和 TIMESTAMP ?

可以通过 property 的 type 属性来进行映射: 

例如:

\<property name="date" type="timestamp">  
    \<column name="DATE" />  
\</property>  
  
\<property name="date" type="data">  
    \<column name="DATE" />  
\</property>  
  
\<property name="date" type="time">  
    \<column name="DATE" />  
\</property>  
  
其中 timestamp, date, time 既不是 Java 类型, 也不是标准 SQL 类型, 而是 hibernate 映射类型. 

3. class 的 local variable's type is another class


4. many to one mapping  
/* 
映射多对一的关联关系。 使用 many-to-one 来映射多对一的关联关系   
name: 多这一端关联的一那一端的属性的名字  
class: 一那一端的属性对应的类名  
column: 一那一端在多的一端对应的数据表中的外键的名字  
\<many-to-one name="customer" class="Customer" column="CUSTOMER_ID">\</many-to-one>  
*/  
save():  
执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT  
先插入 1 的一端, 再插入 n 的一端, 只有 INSERT 语句.  
先插入 Order, 再插入 Customer. 3 条 INSERT, 2 条 UPDATE  
先插入 n 的一端, 再插入 1 的一端, 会多出 UPDATE 语句!  
因为在插入多的一端时, 无法确定 1 的一端的外键值. 所以只能等 1 的一端插入后, 再额外发送 UPDATE 语句.  
推荐先插入 1 的一端, 后插入 n 的一端  
  
get():  
//1. 若查询多的一端的一个对象, 则默认情况下, 只查询了多的一端的对象. 而没有查询关联的  
//1 的那一端的对象!  
//2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句.   
//3. 在查询 Customer 对象时, 由多的一端导航到 1 的一端时,   
//若此时 session 已被关闭, 则默认情况下  
//会发生 LazyInitializationException 异常  
//4. 获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!  
  
delete():  
在不设定级联关系的情况下, 且 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象  

