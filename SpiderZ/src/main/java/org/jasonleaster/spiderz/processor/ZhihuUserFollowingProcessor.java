package org.jasonleaster.spiderz.processor;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jasonleaster.spiderz.constants.RedisConstants;
import org.jasonleaster.spiderz.model.PageInfo;
import org.jasonleaster.spiderz.model.RelationShipPaginationModel;
import org.jasonleaster.spiderz.model.UserRelationship;
import org.jasonleaster.spiderz.service.UserRelationshipService;
import org.jasonleaster.spiderz.url.UrlFactory;
import org.jasonleaster.spiderz.utils.JedisPoolUtils;
import org.jasonleaster.spiderz.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

/**
 * Author: jasonleaster
 * Date  : 2017/5/27
 * Email : jasonleaster@gmail.com
 * Description:
 */
@Repository("zhihuUserFollowingProcessor")
public class ZhihuUserFollowingProcessor implements PageProcessor {

    private static final Logger log = Logger.getLogger(ZhihuUserFollowingProcessor.class);

    /**
     * 限制队列的长度
     */
    private static final int limitedQueueLen = 1000;

    /**
     * 批量插入数据的规模
     */
    private static final int BATCH_SIZE = 5;

    /**
     * 缓存待插入DAO的用户关系
     */
    private List<UserRelationship> userRelationships = new ArrayList<>(BATCH_SIZE);

    @Autowired
    private UserRelationshipService userRelationshipService;

    private Site site = Site.me()
        .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36\n")
        .addHeader("accept","application/json, text/plain, */*")
        .addHeader("Accept-charset", "UTF-8")
        .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
        .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
        .addHeader("Cache-Control", "no-cache")
        .addHeader("authorization","oauth c3cef7c66a1843f8b3a9e6a1e3160e20")
        .addHeader("Host","www.zhihu.com")
        .addHeader("Pragma", "no-cache")
        .addHeader("x-udid", "AIBCvvXSzguPTuq9ctFwIhT0Wy9XppYISL8=");

    public ZhihuUserFollowingProcessor() {
    }

    @Override
    public void process(Page page) {

        String url = page.getUrl().get();
        String fromUrlToken = UrlFactory.getTokenFromUrl(url);

        Json json = page.getJson();

        RelationShipPaginationModel paginationModel = (RelationShipPaginationModel)
                JsonUtil.toObject(json.get(), RelationShipPaginationModel.class);

        if (paginationModel == null){
            log.info("paginationModel is NULL!");
            return;
        }

        PageInfo pageInfo   = paginationModel.getPaging();
        if (pageInfo.getIsStart()){
            addAllRestPaginationUrlsIntoQueue(fromUrlToken, pageInfo);
        }

        List<UserRelationship> relationships = paginationModel.getData();

        // 过滤不必要的数据, 并对现有数据序列化
        List<UserRelationship> toBeRemoved = new ArrayList<>();
        List<String> serializedObjects      = new ArrayList<>();
        for (UserRelationship relationship : relationships){
            if (relationship.getToUrlToken().isEmpty()){
                toBeRemoved.add(relationship);
            }else{

                /*
                 * 由于分页查询返回的结果模型中没有来源用户，
                 * 因此需要设置该关系的来源(隶属于哪个用户)
                 */
                relationship.setFromUrlToken(fromUrlToken);

                serializedObjects.add(relationship.getToUrlToken());
            }
        }

        relationships.removeAll(toBeRemoved);

        if (relationships.isEmpty()){
            return;
        }

        storeUserProfileInfo(relationships);

        JedisPoolUtils.getInstance().addMessagesIntoQueue(RedisConstants.dbIndexOfUrlTokens, RedisConstants.messageQueueName,  limitedQueueLen, serializedObjects);
    }

    @Override
    public Site getSite() {
        return site;
    }

    private synchronized void storeUserProfileInfo(List<UserRelationship> userRelationship){
        if (userRelationships.size() >= BATCH_SIZE) {
            userRelationshipService.saveUserRelationships(userRelationships);
            userRelationships.clear();
        }

        userRelationships.addAll(userRelationship);
    }

    /**
     * 把用户被关注列表的分页查询的Url加入到分页查询队列
     * @param pageInfo 某一页的分页信息
     */
    private synchronized void addAllRestPaginationUrlsIntoQueue(String fromUrlToken, PageInfo pageInfo){

        List<String> urls = new ArrayList<>();

        int totalFollowings = pageInfo.getTotals();
        int limit = 20;// 每页最多展示20个数据项
        for (int offset = 20; offset < totalFollowings; offset += limit){
            String paginationUrl = UrlFactory.getRestfulFollowerAPI(fromUrlToken, offset, limit);
            urls.add(paginationUrl);
        }

        JedisPoolUtils.getInstance().addMessagesIntoQueue(
            RedisConstants.dbIndexOfPaginationUrls,
            RedisConstants.messageQueueNameForPaginationUrls,
            limitedQueueLen, urls);

    }
}
