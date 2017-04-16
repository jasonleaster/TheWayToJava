package com.jasonleaster.designpattern.factory;

import com.jasonleaster.designpattern.factory.model.Button;
import com.jasonleaster.designpattern.factory.model.MacButton;

/**
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
class MacButtonFactory implements ButtonFactory{
    public Button createButton(){
        return new MacButton();
    }
}
