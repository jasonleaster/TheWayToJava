package com.jasonleaster.designpattern.factory;

import com.jasonleaster.designpattern.factory.model.Button;

/**
 * Author: Administrator
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public interface ButtonFactory {
    abstract Button createButton();
}
