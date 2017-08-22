package org.jasonleaster.progress.container;

import org.apache.log4j.Logger;
import org.jasonleaster.progress.IProgressHandler;
import org.jasonleaster.utils.cache.JedisPoolUtils;
import org.jasonleaster.utils.json.JsonUtils;

/**
 * Author: jasonleaster
 * Date  : 2017/8/18
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ProgressContainerWithRedis implements IProgressInstanceContainer {

    private final static Logger logger = Logger.getLogger(JsonUtils.class);

    private static final int DB_INDEX_FOR_PROGRESS = 3;

    @Override
    public IProgressHandler getProgress(String progressId) {
        String serializedObj = JedisPoolUtils.getInstance().getString(DB_INDEX_FOR_PROGRESS, progressId);
        return JsonUtils.toObject(serializedObj, IProgressHandler.class);
    }

    @Override
    public void putProgress(IProgressHandler progress) {
        if (progress != null){
            String serialized = JsonUtils.toJson(progress);
            JedisPoolUtils.getInstance().setString(DB_INDEX_FOR_PROGRESS, progress.getProgressId(), serialized);
        } else {
            logger.error("ProgressContainerWithRedis## " + "parameter is null!");
        }
    }
}
