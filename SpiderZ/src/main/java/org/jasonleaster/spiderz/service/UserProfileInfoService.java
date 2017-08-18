package org.jasonleaster.spiderz.service;

import java.util.List;
import org.jasonleaster.spiderz.dao.IUserProfileDAO;
import org.jasonleaster.spiderz.model.UserProfileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: jasonleaster
 * Date  : 2017/5/27
 * Email : jasonleaster@gmail.com
 * Description:
 */
@Service("userProfileInfoService")
public class UserProfileInfoService {

    @Autowired
    private IUserProfileDAO userProfileDAO;

    public int saveUsersProfileInfo(List<UserProfileInfo> usersProfileInfo) {

        if (usersProfileInfo != null && !usersProfileInfo.isEmpty()) {
            userProfileDAO.insertUsersProfileInfo(usersProfileInfo);
        }else {

        }

        return 0;
    }
}
