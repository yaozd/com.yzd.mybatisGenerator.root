
Maven命令：

mvn mybatis-generator:generate


-----------
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
-----------
maven项目使用mybatis-generator自动生成代码
https://www.cnblogs.com/gosky/p/5518748.html
//
generatorConfiguration配置文件及其详细解读
http://blog.csdn.net/weifen234/article/details/49638023
//
mysql查询当前数据库中所有表 
http://blog.itpub.net/14670217/viewspace-660832/
---
select table_name from information_schema.tables where table_schema='当前数据库'

eg:select table_name from information_schema.tables where table_schema='debug.db'
--
-----------

 mybatis-generator 自增长ID 的获取
http://blog.csdn.net/s1146896025/article/details/50434578


用Maven插件生成Mybatis代码
https://my.oschina.net/lilw/blog/168304

使用Mybatis-Generator自动生成Dao、Model、Mapping相关文件(转)

http://www.cnblogs.com/smileberry/p/4145872.html

generator自动生成mybatis配置和类信息

http://jadethao.iteye.com/blog/1726115



	identity=true 代表生成自增长的id
        <table tableName="tb_temp_test" enableCountByExample="false" enableUpdateByExample="false"
               enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
            <!--identity=true 代表生成自增长的id -->
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>
        <table tableName="tb_t1"></table>
        <table tableName="tb_user" enableCountByExample="false" enableUpdateByExample="false"
               enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"></table>
        <!--domainObjectName 代表实体类的名称-不写则用首字母大写去掉中间的下划线 -->
        <table tableName="message" domainObjectName="Messgae" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false"
               selectByExampleQueryId="false"></table>