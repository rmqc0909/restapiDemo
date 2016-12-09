package service.okhttp;

import okhttp3.*;
import org.json.simple.JSONObject;
import org.junit.Test;
import service.BaseJunit4Test;

import java.io.File;
import java.io.IOException;


/**
 * Created by xiedan11 on 2016/12/1.
 */
public class OkhttpTest extends BaseJunit4Test {
    /**OkHttp官方文档并不建议我们创建多个OkHttpClient，因此全局使用一个。*/
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    /**
     * 同步get
     * @throws IOException
     */
    @Test
    public void testGetPodcast() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:8080/restdemo/podcasts/2")
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println("return>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    @Test
    public void testGetPodcasts() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:8080/restdemo/podcasts/")
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println("return>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * post方式提交：json格式
     * @throws IOException
     */
    @Test
    public void testPostPodcast() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 6);
        jsonObject.put("title", "666");
        jsonObject.put("linkOnPodcastpedia", "linkOnPodcastpedia");
        jsonObject.put("feed", "feed666");
        jsonObject.put("description", "description");
        RequestBody body = RequestBody.create(JSON, jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url("http://localhost:8080/restdemo/podcasts/")
                .post(body)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println("return>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * Post方式提交文件
     * @throws Exception
     */
    @Test
    public void testPostFile() throws Exception {
        File file = new File("README.md");

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        Response response = mOkHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }


}
