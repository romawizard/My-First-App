package ru.roma.vk.wall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.roma.vk.post.Attachment;

/**
 * Created by Ilan on 02.01.2018.
 */

public class CopyHistory {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("from_id")
    @Expose
    private Integer fromId;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("text")
    @Expose
    private String text;
//    @SerializedName("attachments")
//    @Expose
//    private List<Attachment> attachments = null;
//    @SerializedName("post_source")
//    @Expose
//    private PostSource postSource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
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

//    public List<Attachment> getAttachments() {
//        return attachments;
//    }
//
//    public void setAttachments(List<Attachment> attachments) {
//        this.attachments = attachments;
//    }

//    public PostSource getPostSource() {
//        return postSource;
//    }
//
//    public void setPostSource(PostSource postSource) {
//        this.postSource = postSource;
//    }

    @Override
    public String toString() {
        return "CopyHistory{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", fromId=" + fromId +
                ", date=" + date +
                ", postType='" + postType + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
