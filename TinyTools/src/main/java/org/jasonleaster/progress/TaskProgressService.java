package org.jasonleaster.progress;

import java.util.Arrays;
import org.jasonleaster.progress.container.IProgressInstanceContainer;
import org.jasonleaster.progress.container.InMemoryProgressContainer;

/**
 * Author: jasonleaster
 * Date  : 2017/8/17
 * Email : jasonleaster@gmail.com
 * Description:
 */
public final class TaskProgressService implements ITaskProgressService{

    private static final TaskProgressService instance = new TaskProgressService();

    private static final IProgressInstanceContainer container = new InMemoryProgressContainer();

    public static ITaskProgressService getInstance(){
        return instance;
    }

    @Override
    public IProgressHandler createProgressHandler(String progressId) {
        synchronized (container){
            IProgressHandler progressHandler = container.getProgress(progressId);
            if (progressHandler == null){
                progressHandler = new DefaultProgressHandler(progressId);
                container.putProgress(progressHandler);
            }
            return progressHandler;
        }
    }

    @Override
    public IProgressHandler createProgressHandler(String progressId, String progressName) {
        synchronized (container){
            IProgressHandler progressHandler = container.getProgress(progressId);
            if (progressHandler == null){
                progressHandler = new DefaultProgressHandler(progressId, progressName);
                container.putProgress(progressHandler);
            }
            return progressHandler;
        }
    }

    @Override
    public IProgressHandler createProgressHandler(String mainProgressId, String mainProgressName,
        IProgressHandler[] subHandlers) {
        synchronized (container){
            IProgressHandler progressHandler = container.getProgress(mainProgressId);
            if (progressHandler == null){

                progressHandler = new DefaultProgressHandler(mainProgressId, mainProgressName,
                    Arrays.asList(subHandlers));

                container.putProgress(progressHandler);
            }
            return progressHandler;
        }
    }

    @Override
    public AbstractProgressInfo getProgressInfo(String progressId) {

        IProgressHandler handler = container.getProgress(progressId);

        if (handler == null){
            return null;
        }else {
            return handler.getProgressInfo();
        }

    }
}
