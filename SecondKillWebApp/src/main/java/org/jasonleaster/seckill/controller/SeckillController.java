package org.jasonleaster.seckill.controller;

import java.util.Date;
import java.util.List;
import org.jasonleaster.seckill.dto.Exposer;
import org.jasonleaster.seckill.dto.SecKillExcution;
import org.jasonleaster.seckill.dto.SeckillResult;
import org.jasonleaster.seckill.enums.SeckillStateEnum;
import org.jasonleaster.seckill.exception.RepeatKillException;
import org.jasonleaster.seckill.exception.SeckillCloseException;
import org.jasonleaster.seckill.model.Seckill;
import org.jasonleaster.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: jasonleaster
 * File  : SeckillController.java
 * Date  : 2017/02/05.
 */
@Controller
@RequestMapping("/seckill") //url:/模块/资源/{id}/细分   /seckill/list
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public String list(Model model){

        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);

        return Views.SEC_KILL_LIST;
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String Detail(@PathVariable("seckillId") Long seckillId, Model model){

        if (seckillId == null){
            return "redirect:/seckill/list";
        }

        Seckill seckill = seckillService.getById(seckillId);

        if (seckill == null){
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill", seckill);

        return Views.SEC_KILL_DETAIL;
    }

    // ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){

        SeckillResult<Exposer> result;

        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SecKillExcution> execute(@PathVariable("seckillId") Long seckillId,
                                                  @PathVariable("md5") String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long phone){
        if (phone==null){
            return new SeckillResult<SecKillExcution>(false,"未注册");
        }
        SeckillResult<SecKillExcution> result;
        try {
//            SecKillExcution SecKillExcution = seckillService.executeSeckill(seckillId,phone,md5);
            SecKillExcution SecKillExcution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            return new SeckillResult<SecKillExcution>(true,SecKillExcution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage(),e);
            SecKillExcution SecKillExcution = new SecKillExcution(seckillId, SeckillStateEnum.REPAET_KILL);
            return new SeckillResult<SecKillExcution>(true,SecKillExcution);
        }catch (SeckillCloseException e){
            logger.error(e.getMessage(),e);
            SecKillExcution SecKillExcution = new SecKillExcution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SecKillExcution>(true,SecKillExcution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            SecKillExcution SecKillExcution = new SecKillExcution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SecKillExcution>(true,SecKillExcution);
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }
}
