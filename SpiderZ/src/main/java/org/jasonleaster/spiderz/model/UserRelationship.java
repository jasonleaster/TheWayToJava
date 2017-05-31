package org.jasonleaster.spiderz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: jasonleaster
 * Date  : 2017/5/27
 * Email : jasonleaster@gmail.com
 * Description:
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRelationship {

    @JsonProperty("is_followed")
    private boolean isFollowed;

    @JsonProperty("avatar_url_template")
    private String avatarUrlTemplate;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("answer_count")
    private Integer answerCount;

    @JsonProperty("is_following")
    private boolean isFollowing;

    @JsonProperty("type")
    private String type;

    @JsonProperty("url_token")
    private String urlToken;

    @JsonProperty("id")
    private String id;

    @JsonProperty("articles_count")
    private Integer articlesCount;

    @JsonProperty("name")
    private String name;

    @JsonProperty("is_advertiser")
    private String isAdvertiser;

    @JsonProperty("headline")
    private String headline;

    @JsonProperty("gender")
    private Integer gender;

    @JsonProperty("url")
    private String url;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("is_org")
    private boolean isOrg;

    @JsonProperty("follower_count")
    private Integer followerCount;

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public String getAvatarUrlTemplate() {
        return avatarUrlTemplate;
    }

    public void setAvatarUrlTemplate(String avatarUrlTemplate) {
        this.avatarUrlTemplate = avatarUrlTemplate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlToken() {
        return urlToken;
    }

    public void setUrlToken(String urlToken) {
        this.urlToken = urlToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(Integer articlesCount) {
        this.articlesCount = articlesCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsAdvertiser() {
        return isAdvertiser;
    }

    public void setIsAdvertiser(String isAdvertiser) {
        this.isAdvertiser = isAdvertiser;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isOrg() {
        return isOrg;
    }

    public void setOrg(boolean org) {
        isOrg = org;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }
}
