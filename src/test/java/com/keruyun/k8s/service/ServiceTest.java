package com.keruyun.k8s.service;

import com.keruyun.k8s.token.TokenConstant;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ServiceTest {


    /**
     * 查询 service 列表.
     */
    @Test
    public void listNamespacedServiceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String namespace = "test";
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

            V1ServiceList result = apiInstance.listNamespacedService(namespace, pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);

            result.getItems().stream().forEach(s -> System.out.println(s.getMetadata().getName()));

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 创建 service.
     */
    @Test
    public void createNamespacedServiceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String namespace = "test";
        String pretty = "true";

        // metadata 节点

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("eve-service");

        // spec 节点
        V1ServiceSpec spec = new V1ServiceSpec();

        V1ServicePort port = new V1ServicePort();
        port.setTargetPort(new IntOrString(8080));
        port.setPort(8080);

        spec.addPortsItem(port);

        // spec 下的 selector ，对应 deployment name
        Map<String, String> map = new HashMap<>();
        map.put("k8s-app", "k8s-infra-infra-rbac-test");

        spec.selector(map);

        V1Service body = new V1Service();
        body.setMetadata(meta);
        body.setSpec(spec);


        try {
            V1Service result = apiInstance.createNamespacedService(namespace, body, pretty);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 查询 service 详情.
     */
    @Test
    public void readNamespacedServiceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "eve-service";
        String namespace = "test";
        String pretty = "true";
        Boolean exact = true;
        Boolean export = true;

        try {

            V1Service result = apiInstance.readNamespacedService(name, namespace, pretty, exact, export);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * replace service 内容.
     */
    @Test
    public void replaceNamespacedServiceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "eve-service";
        String namespace = "test";
        String pretty = "true";

        // metadata 节点

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName(name);

        //为了保证对资源对象操作的原子性，resourceVersion是用于标识一个资源对象的内部版本的一个字符串，客户端可以通过它来判断该对象是否被更新过。
        //更多可查看 https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.10/#service-v1-core
        //设置为 0，只返回在缓存中的内容。
        meta.setResourceVersion("0");

        // spec 节点
        V1ServiceSpec spec = new V1ServiceSpec();

        V1ServicePort port = new V1ServicePort();
        port.setTargetPort(new IntOrString(8082));
        port.setPort(8082);

        spec.addPortsItem(port);
        spec.setClusterIP("172.21.9.111");

        // spec 下的 selector ，对应 deployment name
        Map<String, String> map = new HashMap<>();
        map.put("k8s-app", "k8s-infra-infra-rbac-test");

        spec.selector(map);

        try {

            V1Service body = new V1Service();
            body.setMetadata(meta);
            body.setSpec(spec);

            V1Service result = apiInstance.replaceNamespacedService(name, namespace, body, pretty);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 删除 service.
     */
    @Test
    public void deleteNamespacedServiceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "eve-service";
        String namespace = "test";
        String pretty = "true";
        Integer gracePeriodSeconds = 0;
        Boolean orphanDependents = true;
        String propagationPolicy = "Orphan";

        V1DeleteOptions body = new V1DeleteOptions();

        try {

            V1Status result = apiInstance.deleteNamespacedService(name, namespace, body, pretty, gracePeriodSeconds, orphanDependents, propagationPolicy);

            System.out.println(result);
            System.out.println(result.getStatus());

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }
    }


}

