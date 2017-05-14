package org.jasonleaster.seckill.controller;

import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import org.jasonleaster.seckill.dto.BusinessDealInfo;
import org.jasonleaster.seckill.dto.SecKillExcution;
import org.jasonleaster.seckill.dto.RestfulResponse;
import org.jasonleaster.seckill.enums.DealStateEnum;
import org.jasonleaster.seckill.exception.RepeatKillException;
import org.jasonleaster.seckill.exception.SeckillCloseException;
import org.jasonleaster.seckill.model.Stock;
import org.jasonleaster.seckill.service.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: jasonleaster
 * File  : SeckillController.java
 * Date  : 2017/02/05.
 */
@Controller
@RequestMapping("/scramble") //url:/模块/资源/{id}/细分   /seckill/list
public class ScrambleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DealService dealService;

    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) PageInfo pageInfo, Model model){

        if (pageInfo == null)
        {
            pageInfo = new PageInfo();
            pageInfo.setStartRow(0);
            pageInfo.setPageSize(5);
        }

        List<Stock> list = dealService.getDealsList(pageInfo, "");
        model.addAttribute("list", list);

        return Views.SEC_KILL_LIST;
    }

    @RequestMapping(value = "/{commodityId}/detail", method = RequestMethod.GET)
    public String Detail(@PathVariable("commodityId") Long commodityId, Model model){

        if (commodityId == null){
            return "redirect:/scramble/list";
        }

        Stock stock = dealService.getById(commodityId);

        if (stock == null){
            return "forward:/scramble/list";
        }

        model.addAttribute("stock", stock);

        return Views.SEC_KILL_DETAIL;
    }

    // ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RestfulResponse<BusinessDealInfo> exposer(@PathVariable("commodityId") Long commodityId){

        RestfulResponse<BusinessDealInfo> result;

        try {
            BusinessDealInfo exposer = dealService.exportDealUrl(commodityId);
            result = new RestfulResponse<BusinessDealInfo>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new RestfulResponse<BusinessDealInfo>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RestfulResponse<SecKillExcution> execute(@PathVariable("seckillId") Long seckillId,
                                                  @PathVariable("md5") String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long phone){
        if (phone==null){
            return new RestfulResponse<SecKillExcution>(false,"未注册");
        }
        RestfulResponse<SecKillExcution> result;
        try {
//            SecKillExcution SecKillExcution = dealService.executeSeckill(seckillId,phone,md5);
            SecKillExcution SecKillExcution = dealService
                .executeSeckillProcedure(seckillId,phone,md5);
            return new RestfulResponse<SecKillExcution>(true,SecKillExcution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage(),e);
            SecKillExcution SecKillExcution = new SecKillExcution(seckillId, DealStateEnum.REPAET_ORDER);
            return new RestfulResponse<SecKillExcution>(true,SecKillExcution);
        }catch (SeckillCloseException e){
            logger.error(e.getMessage(),e);
            SecKillExcution SecKillExcution = new SecKillExcution(seckillId, DealStateEnum.END);
            return new RestfulResponse<SecKillExcution>(true,SecKillExcution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            SecKillExcution SecKillExcution = new SecKillExcution(seckillId, DealStateEnum.INNER_ERROR);
            return new RestfulResponse<SecKillExcution>(true,SecKillExcution);
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<Long> time(){
        Date now = new Date();
        return new RestfulResponse<Long>(true, now.getTime());
    }
}
