package org.jasonleaster.spiderz.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jasonleaster.spiderz.model.UserRelationship;
import org.springframework.stereotype.Service;

/**
 * Author: jasonleaster
 * Date  : 2017/6/2
 * Email : jasonleaster@gmail.com
 * Description:
 */
@Service("userRelationshipDAO")
public interface IUserRelationshipDAO {
    /**
     * 批量插入用户的关系列表
     * @param userRelationships 用户关系列表
     * @return 影响数据库的行数
     */
    int insertUsersRelationship(@Param("profiles") List<UserRelationship> userRelationships);
}
