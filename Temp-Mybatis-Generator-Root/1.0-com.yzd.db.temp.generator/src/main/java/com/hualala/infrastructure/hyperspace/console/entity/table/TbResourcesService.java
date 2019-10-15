package com.hualala.infrastructure.hyperspace.console.entity.table;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.hualala.infrastructure.hyperspace.console.entity.table.TbResources;
import com.hualala.infrastructure.hyperspace.console.entity.table.TbResourcesDao;

@Service
public class TbResourcesService {

    @Resource
    private TbResourcesDao tbResourcesDao;

    public int insert(TbResources pojo){
        return tbResourcesDao.insert(pojo);
    }

    public int insertList(List< TbResources> pojos){
        return tbResourcesDao.insertList(pojos);
    }

    public List<TbResources> select(TbResources pojo){
        return tbResourcesDao.select(pojo);
    }

    public int update(TbResources pojo){
        return tbResourcesDao.update(pojo);
    }

}
