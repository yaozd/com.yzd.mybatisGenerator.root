




Maven命令：

mvn mybatis-generator:generate

特别注意：进行插入记录时：禁止使用insert 方法，只能使用 insertSelective ，因为insert 方法会使用数据库字段的值设置为NULL而不走默认的值

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
