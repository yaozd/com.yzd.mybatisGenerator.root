package com.hualala.infrastructure.hyperspace.console.dao.mapper;

import com.hualala.infrastructure.hyperspace.console.entity.table.TbResources;

public interface TbResourcesMapper {
    int insertSelective(TbResources record);

    TbResources selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbResources record);
}