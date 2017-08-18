package org.jasonleaster.progress;

/**
 * Author: jasonleaster
 * Date  : 2017/8/17
 * Email : jasonleaster@gmail.com
 * Description:
 */
public final class ProgressHandlerFactory {

    public static IProgressHandler createProgressHandler(String progressId){
        return new DefaultProgressHandler(progressId);
    }

    public static IProgressHandler createProgressHandler(String progressId, String progressName){
        return new DefaultProgressHandler(progressId, progressName);
    }
}
