package ru.roma.vk.wall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.roma.vk.post.Attachment;

/**
 * Created by Ilan on 02.01.2018.
 */

public class WallPost {

    private  static int count;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("from_id")
    @Expose
    private Integer fromId;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("text")
    @Expose
    private String text;

    private transient List<CopyHistory> copyHistory = null;

    @SerializedName("attachments")
    @Expose
    private  List<NewAttachment> attachments = null;
//    @SerializedName("post_source")
//    @Expose
//    private PostSource postSource;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("reposts")
    @Expose
    private Reposts reposts;


    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        WallPost.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CopyHistory> getCopyHistory() {
        return copyHistory;
    }

    public void setCopyHistory(List<CopyHistory> copyHistory) {
        this.copyHistory = copyHistory;
    }

    public List<NewAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<NewAttachment> attachments) {
        this.attachments = attachments;
    }
//        public PostSource getPostSource() {
//        return postSource;
//    }
//
//    public void setPostSource(PostSource postSource) {
//        this.postSource = postSource;
//    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Reposts getReposts() {
        return reposts;
    }

    public void setReposts(Reposts reposts) {
        this.reposts = reposts;
    }

    @Override
    public String toString() {
        return "WallPost{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", ownerId=" + ownerId +
                ", date=" + date +
                ", postType='" + postType + '\'' +
                ", text='" + text + '\'' +
                ", copyHistory=" + copyHistory +
                ", attachments=" + attachments +
                ", comments=" + comments +
                ", likes=" + likes +
                ", reposts=" + reposts +
                '}';
    }
}
