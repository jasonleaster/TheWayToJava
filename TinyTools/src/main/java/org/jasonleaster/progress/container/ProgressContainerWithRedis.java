package org.jasonleaster.progress.container;

import org.jasonleaster.progress.IProgressHandler;

/**
 * Author: jasonleaster
 * Date  : 2017/8/18
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ProgressContainerWithRedis implements IProgressInstanceContainer {

    @Override
    public IProgressHandler getProgress(String progressId) {
        return null;
    }

    @Override
    public void putProgress(IProgressHandler progress) {

    }
}
