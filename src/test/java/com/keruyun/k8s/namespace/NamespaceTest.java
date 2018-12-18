package com.keruyun.k8s.namespace;

import com.google.gson.reflect.TypeToken;
import com.keruyun.k8s.token.TokenConstant;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NamespaceTest {


    /**
     * 查询 namespace 列表.
     */
    @Test
    public void listNamespaceTest() throws IOException {


        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String pretty = "true";
        String _continue = null;
        String fieldSelector = null;
        Boolean includeUninitialized = true;
        String labelSelector = null;
        Integer limit = null; //返回数量
        String resourceVersion = null;
        Integer timeoutSeconds = 0;
        Boolean watch = false;

        try {

            V1NamespaceList result = apiInstance.listNamespace(pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);

            result.getItems().forEach(s -> System.out.println(s.getMetadata().getName()));

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 创建 namespace.
     */
    @Test
    public void createNamespaceTest() throws IOException {

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);

        CoreV1Api apiInstance = new CoreV1Api(client);

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("liukai");

        V1Namespace body = new V1Namespace();
        body.setMetadata(meta);

        String pretty = "true";

        try {

            V1Namespace result = apiInstance.createNamespace(body, pretty);

            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 查询 namespace 详情.
     */
    @Test
    public void readNamespaceTest() throws IOException {

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "liukai";
        String pretty = "true";
        Boolean exact = true;
        Boolean export = true;

        try {

            V1Namespace result = apiInstance.readNamespace(name, pretty, exact, export);

            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * replace namespace 内容.
     */
    @Test
    public void replaceNamespaceTest() throws IOException {

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "liukai";
        String pretty = "true";

        Map<String, String> map = new HashMap<>();
        map.put("keruyun", "test");

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("liukai");
        meta.setAnnotations(map);

        V1Namespace body = new V1Namespace();
        body.setMetadata(meta);

        try {
            V1Namespace result = apiInstance.replaceNamespace(name, body, pretty);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 删除 namespace.
     * <p>
     * ps: 执行会抛异常,但是 namespace 会删除,SDK 本身的 Bug.
     * <p>
     * 更多详情查看: https://github.com/kubernetes-client/java/issues/86
     */
    @Test
    @Ignore
    public void deleteNamespaceTest() throws IOException {

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);


        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "liukai";
        String pretty = "true";
        V1DeleteOptions body = new V1DeleteOptions();
        Integer gracePeriodSeconds = 0;
        Boolean orphanDependents = null;
        String propagationPolicy = "Orphan";

        try {

            V1Status result = apiInstance.deleteNamespace(name, body, pretty, gracePeriodSeconds, orphanDependents, propagationPolicy);

            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }
    }


    /**
     * 通过 API 删除 namespace,根据 ApiClient 复用 OkHttpClient.
     * <p>
     * https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.10/#delete-483
     */
    @Test
    public void deleteNamespaceByAPITest() throws IOException {

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);

        OkHttpClient okHttpClient = client.getHttpClient();

        String name = "liukai";

        String deleteUrl = TokenConstant.URL + "/api/v1/namespaces/" + name;

        Request request = new Request.Builder()
                .url(deleteUrl)
                .delete(null)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();


        System.out.println(response.body().string());

    }

    @Test
    public void watchNamespaceTest() throws IOException, ApiException {


        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("kubeconfig-ci");

        ApiClient client = Config.fromConfig(stream);
        client.getHttpClient().setReadTimeout(60, TimeUnit.SECONDS);
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        Watch<V1Namespace> watch =
                Watch.createWatch(
                        client,
                        api.listNamespaceCall(
                                null, null, null, null, null, null, null, null, Boolean.TRUE, null, null),
                        new TypeToken<Watch.Response<V1Namespace>>() {
                        }.getType());

        try {

            for (Watch.Response<V1Namespace> item : watch) {

                System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
            }

        } finally {
            watch.close();
        }

    }


}

