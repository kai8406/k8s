package com.keruyun.k8s.event;

import com.keruyun.k8s.token.TokenConstant;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Event;
import io.kubernetes.client.models.V1EventList;
import io.kubernetes.client.util.Config;
import org.junit.Test;

public class EventTest {


    /**
     * 查询 event 列表.
     */
    @Test
    public void listNamespacedEventTest() {

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

            V1EventList result = apiInstance.listNamespacedEvent(namespace, pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            //System.out.println(result);
            result.getItems().stream().forEach(s -> System.out.println(s.getMessage()));
            System.err.println(result.getItems().size());

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 查询 event 详情.
     */
    @Test
    public void readNamespacedEventTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String namespace = "test";
        String name = "k8s-demotest-viptest.156414720962fcdd";
        String pretty = "true";
        Boolean exact = true;
        Boolean export = true;

        try {

            V1Event result = apiInstance.readNamespacedEvent(name, namespace, pretty, exact, export);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


}

