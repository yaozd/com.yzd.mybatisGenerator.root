




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
<!-- 注释【generatedKey column="order_id" sqlStatement="JDBC" identity="true"】就不自动生成主键-->
 <table tableName="tb_order" enableDeleteByPrimaryKey="false" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
     <!--Mybatis-V2-ShardingJDBC版 -->
     <!--identity=true 代表生成自增长的id -->
     <!--<generatedKey column="order_id" sqlStatement="JDBC" identity="true"/>-->
 </table>
/////////////////////////////////////////////////////////////////////
