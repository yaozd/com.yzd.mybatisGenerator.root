




Maven命令：

mvn mybatis-generator:generate

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
