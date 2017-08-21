package org.jasonleaster.progress;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: jasonleaster
 * Date  : 2017/8/17
 * Email : jasonleaster@gmail.com
 * Description:
 */
class DefaultProgressHandler implements IProgressHandler {

    private BasicProgressInfo progressInfo;

    private List<IProgressHandler> subProgressHandlers;

    public DefaultProgressHandler(String progressId) {
        this.progressInfo = new BasicProgressInfo(progressId);
    }

    public DefaultProgressHandler(String progressId, String progressName) {
        this.progressInfo = new BasicProgressInfo(progressId, progressName);
    }

    public DefaultProgressHandler(String progressId, String progressName,
        List<IProgressHandler> subProgress) {
        this.progressInfo = new BasicProgressInfo(progressId, progressName);
        this.subProgressHandlers = subProgress;
    }

    @Override
    public void start() {
        progressInfo.setStartTime(LocalDateTime.now());
        progressInfo.setStatus(EnumProgressStatus.START);
        progressInfo.setValue(0.0);
    }

    @Override
    public void update(double progressInPercentage) {
        progressInfo.setStatus(EnumProgressStatus.RUNNING);
        progressInfo.setValue(progressInPercentage);
    }

    @Override
    public void cancel() {

        if (subProgressHandlers != null) {
            for (IProgressHandler subProgress : subProgressHandlers) {
                subProgress.cancel();
            }
        }

        progressInfo.setEndTime(LocalDateTime.now());
        progressInfo.setStatus(EnumProgressStatus.CANCELED);
    }

    @Override
    public void end() {
        if (subProgressHandlers != null) {
            for (IProgressHandler subProgress : subProgressHandlers) {
                if (subProgress != null
                    && subProgress.getProgressInfo().getStatus() != EnumProgressStatus.FINISHED) {
                    System.out.println("ERROR! There have sub-progress un-finished!");
                    return;
                }
            }
        }

        progressInfo.setEndTime(LocalDateTime.now());
        progressInfo.setStatus(EnumProgressStatus.FINISHED);
    }

    @Override
    public String getProgressId() {
        return progressInfo.getProgressId();
    }

    @Override
    public AbstractProgressInfo getProgressInfo() {
        if (subProgressHandlers == null || subProgressHandlers.size() == 0) {
            return new BasicProgressInfo(progressInfo);
        }

        int finishedCounts = 0;
        int totalCounts = subProgressHandlers.size();
        List<AbstractProgressInfo> subProgressInfo = new ArrayList<>();
        for (IProgressHandler handler : subProgressHandlers) {

            AbstractProgressInfo progressInfo = handler.getProgressInfo();
            subProgressInfo.add(progressInfo);

            if (progressInfo != null
                && progressInfo.getStatus() == EnumProgressStatus.FINISHED) {
                finishedCounts++;
            }
        }

        if (finishedCounts == totalCounts) {
            this.end();
        } else{
            this.update(finishedCounts * 100. / totalCounts);
        }

        AbstractProgressInfo snapshot = new BasicProgressInfo(progressInfo);
        snapshot.setSubProgress(subProgressInfo);

        // return a value based copy(Shallow Copy) of this object
        return snapshot;
    }
}
