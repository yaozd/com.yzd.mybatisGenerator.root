## 生成Temp数据库对应的Mybatis文件

```
如果当前项目有多个数据库可以创建多个模块即可。
```
### selectAll替换Mapper模板
```
List<TbTagUser> selectAll(@Param("pojo") TbTagUser pojo);
```
### selectAll替换XML模板
```
  <!--auto generated Code-->
  <select id="selectAll" resultMap="BaseResultMap">
    SELECT <include refid="Base_Column_List"/>
    FROM tb_tag
    <where>
      <if test="pojo.id != null"> AND id = #{pojo.id} </if>
      <if test="pojo.name != null"> AND name = #{pojo.name} </if>
      <if test="pojo.description != null"> AND description = #{pojo.description} </if>
      <if test="pojo.tagCode != null"> AND tag_code = #{pojo.tagCode} </if>
      <if test="pojo.gmtModified != null"> AND gmt_modified = #{pojo.gmtModified} </if>
      <if test="pojo.gmtCreate != null"> AND gmt_create = #{pojo.gmtCreate} </if>
      <if test="pojo.gmtIsDeleted != null"> AND gmt_is_deleted = #{pojo.gmtIsDeleted} </if>
    </where>
    LIMIT 1000
  </select>
```