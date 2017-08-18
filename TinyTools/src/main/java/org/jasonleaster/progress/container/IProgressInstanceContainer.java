package org.jasonleaster.progress.container;


import org.jasonleaster.progress.IProgressHandler;

/**
 * Author: jasonleaster
 * Date  : 2017/8/18
 * Email : jasonleaster@gmail.com
 * Description:
 */
public interface IProgressInstanceContainer {
    IProgressHandler getProgress(String progressId);

    void putProgress(IProgressHandler progress);
}
