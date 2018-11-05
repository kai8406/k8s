package com.keruyun.k8s.pod;

import com.keruyun.k8s.token.TokenConstant;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.junit.Test;

public class PodTest {


    /**
     * 查询所有 pod 列表.
     */
    @Test
    public void listPodForAllNamespacesTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

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

            V1PodList result = apiInstance.listPodForAllNamespaces(_continue, fieldSelector, includeUninitialized, labelSelector, limit, pretty, resourceVersion, timeoutSeconds, watch);

            result.getItems().stream().forEach(s -> System.out.println(s.getMetadata().getName()));
            System.err.println(result.getItems().size());

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 查询 pod 列表.
     */
    @Test
    public void listNamespacedPodTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String namespace = "vipcitest";
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

            V1PodList result = apiInstance.listNamespacedPod(namespace, pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);

            result.getItems().stream().forEach(s -> System.out.println(s.getMetadata().getName()));
            System.err.println(result.getItems().size());

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 查询 pod 详情.
     */
    @Test
    public void readNamespacedPodTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String namespace = "vipcitest";
        String name = "k8s-on-store-store-openapi-vipcitest-6497dcdc85-8k972";
        String pretty = "true";
        Boolean exact = true;
        Boolean export = true;

        try {

            V1Pod result = apiInstance.readNamespacedPod(name, namespace, pretty, exact, export);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


}

