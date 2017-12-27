




Maven命令：

mvn mybatis-generator:generate

特别注意：
1,进行插入记录时：禁止使用insert 方法，只能使用 insertSelective ，因为insert 方法会使用数据库字段的值设置为NULL而不走默认的值
2,进行更新记录时：禁止使用updateByPrimaryKey 方法，只能使用 updateByPrimaryKeySelective
==解释原因：
这两个update都是使用generator生成的mapper.xml文件中,对dao层的更新操作
updateByPrimaryKey对你注入的字段全部更新（不判断是否为Null）
updateByPrimaryKeySelective会对字段进行判断再更新(如果为Null就忽略更新)
区别了这两点就很容易根据业务来选择服务层的调用了！
详细可以查看generator生成的源代码！
insert和insertSelective和上面类似，加入是insert就把所有值插入,但是要注意加入数据库字段有default,default是不会起作用的
insertSelective不会忽略default

/////////////////////////////////////////////////////////////////////
Mybatis-V1-普通版：
<!--identity=true 代表生成自增长的id -->
<generatedKey column="order_id" sqlStatement="MySql" identity="true"/>
生成结果：
<insert id="insert" parameterType="com.keith.db.order.entity.TbTest">
    <selectKey keyProperty="orderId" order="AFTER" resultType="java.lang.Long">
      SELECT LAST_INSERT_ID()
    </selectKey>
    insert into tb_test ……
</insert>
/////////////////////////////////////////////////////////////////////
Mybatis-V2-ShardingJDBC版：
<!--identity=true 代表生成自增长的id -->
<generatedKey column="order_id" sqlStatement="JDBC" identity="true"/>
生成结果：
<insert id="insert" keyProperty="orderId" parameterType="com.keith.db.order.entity.TbTest" useGeneratedKeys="true">
    insert into tb_test ……
</insert>
/////////////////////////////////////////////////////////////////////
