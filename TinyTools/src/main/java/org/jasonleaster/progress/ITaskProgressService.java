package org.jasonleaster.progress;

/**
 * Author: jasonleaster
 * Date  : 2017/8/18
 * Email : jasonleaster@gmail.com
 * Description:
 */
public interface ITaskProgressService {

    /**
     * Create a progress with a id or name.
     *
     * @param progressId Id of progress
     * @return  {@link IProgressHandler}
     */
    IProgressHandler createProgressHandler(String progressId);
    IProgressHandler createProgressHandler(String progressId, String progressName);

    /**
     * This method will create a progress which will represent for the process of all sub-progress.
     * @param mainProgressId    The ID of the parent progress
     * @param mainProgressName  The name of the parent progress
     * @param subHandlers       The handlers for sub-progress
     * @return {@link IProgressHandler}. The total progress handler for sub-progress handlers
     */
    IProgressHandler createProgressHandler(String mainProgressId, String mainProgressName, IProgressHandler[] subHandlers);

    /**
     * Get the basic information of the progress with the specified Id of that progress
     * @param progressId Id of progress
     * @return {@link AbstractProgressInfo}
     */
    AbstractProgressInfo getProgressInfo(String progressId);
}
