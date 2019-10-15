package com.hualala.infrastructure.hyperspace.console.entity.table;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.hualala.infrastructure.hyperspace.console.entity.table.TbResources;

public interface TbResourcesDao {

    int insert(@Param("pojo") TbResources pojo);

    int insertList(@Param("pojos") List< TbResources> pojo);

    List<TbResources> select(@Param("pojo") TbResources pojo);

    int update(@Param("pojo") TbResources pojo);

}
