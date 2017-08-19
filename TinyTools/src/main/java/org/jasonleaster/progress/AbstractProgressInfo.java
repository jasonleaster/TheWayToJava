package org.jasonleaster.progress;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: jasonleaster
 * Date  : 2017/8/18
 * Email : jasonleaster@gmail.com
 * Description:
 */
@Setter
@Getter
public abstract class AbstractProgressInfo implements Serializable{

    protected String progressId;

    protected double value;

    protected EnumProgressStatus status;

    protected List<AbstractProgressInfo> subProgress;
}
