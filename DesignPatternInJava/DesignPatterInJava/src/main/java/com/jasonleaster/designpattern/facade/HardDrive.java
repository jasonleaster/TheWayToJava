package com.jasonleaster.designpattern.facade;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class HardDrive {

    public byte[] read(long lba, int size) {
        return new byte[1];
    }
}
