package ru.roma.vk.wall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilan on 02.01.2018.
 */

public class ModelResponseWall {


    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ModelResponseWall{" +
                "response=" + response +
                '}';
    }

    public class Response {

        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("items")
        @Expose
        private List<WallPost> items = null;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<WallPost> getItems() {
            return items;
        }

        public void setItems(List<WallPost> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "count=" + count +
                    ", items=" + items +
                    '}';
        }
    }
}
