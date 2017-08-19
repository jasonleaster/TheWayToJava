package org.jasonleaster.progress;

/**
 * Author: jasonleaster
 * Date  : 2017/8/17
 * Email : jasonleaster@gmail.com
 * Description:
 */
public interface IProgressHandler {

    void start();

    void update(double progressInPercentage);

    void cancel();

    void end();

    String getProgressId();

    AbstractProgressInfo getProgressInfo();
}
