package org.jasonleaster.spiderz.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.jasonleaster.spiderz.dao.IUserRelationshipDAO;
import org.jasonleaster.spiderz.model.UserRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: jasonleaster
 * Date  : 2017/6/2
 * Email : jasonleaster@gmail.com
 * Description:
 */
@Service("userRelationshipService")
public class UserRelationshipService {

    private static final Logger log = Logger.getLogger(UserRelationshipService.class);

    @Autowired
    private IUserRelationshipDAO userRelationshipDAO;

    public UserRelationshipService() {
    }

    public int saveUserRelationships(List<UserRelationship> relationships) {

        if (relationships != null && !relationships.isEmpty()) {
            return userRelationshipDAO.insertUsersRelationship(relationships);
        }else {
            log.info("empty list");
            return -1;
        }
    }
}
