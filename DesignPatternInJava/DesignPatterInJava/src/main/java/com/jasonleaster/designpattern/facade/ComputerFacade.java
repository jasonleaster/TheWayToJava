package com.jasonleaster.designpattern.facade;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 *
 * This pattern hides the complexities of the larger system
 * and provides a simpler interface to the client. It typically
 * involves a single wrapper class that contains a set of members
 * required by client. These members access the system on behalf
 * of the facade client and hide the implementation details.
 */
/* Facade */
public class ComputerFacade {

    private static final long BOOT_ADDRESS = 0x100;
    private static final long BOOT_SECTOR = 0x1;
    private static final int SECTOR_SIZE = 0x30;

    private CPU processor;
    private Memory ram;
    private HardDrive hd;

    public ComputerFacade() {
        this.processor = new CPU();
        this.ram = new Memory();
        this.hd = new HardDrive();
    }

    public void start() {
        processor.freeze();
        ram.load(BOOT_ADDRESS, hd.read(BOOT_SECTOR, SECTOR_SIZE));
        processor.jump(BOOT_ADDRESS);
        processor.execute();
    }
}