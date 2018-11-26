package com.keruyun.k8s.ingress;

import com.keruyun.k8s.token.TokenConstant;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngressTest {


    /**
     * 查询 ingress 列表.
     */
    @Test
    public void listNamespacedIngressTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api(client);

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

            V1beta1IngressList result = apiInstance.listNamespacedIngress(namespace, pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);

            result.getItems().stream().forEach(s -> System.out.println(s.getMetadata().getName()));

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 创建 service.
     */
    @Test
    public void createNamespacedIngressTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api(client);

        String namespace = "vipcitest";
        String pretty = "true";

        V1beta1Ingress body = new V1beta1Ingress();


        // metadata 节点

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("test-ingress");
        body.setMetadata(meta);

        // spec 节点
        V1beta1IngressSpec spec = new V1beta1IngressSpec();


        // rules
        List<V1beta1IngressRule> rules = new ArrayList<>();


        V1beta1IngressRule rule  = new V1beta1IngressRule();

        rule.setHost("vipcitest.test.shishike.com");


        V1beta1HTTPIngressRuleValue http = new V1beta1HTTPIngressRuleValue();

        List<V1beta1HTTPIngressPath> paths = new ArrayList<>();

        V1beta1HTTPIngressPath v1beta1HTTPIngressPath = new V1beta1HTTPIngressPath();
        v1beta1HTTPIngressPath.setPath("/");
        V1beta1IngressBackend backend = new V1beta1IngressBackend();
        backend.setServiceName("k8s-op-test");
        backend.setServicePort(new IntOrString(8080));
        v1beta1HTTPIngressPath.setBackend(backend);

        paths.add(v1beta1HTTPIngressPath);

        http.setPaths(paths);

        rule.setHttp(http);

        rules.add(rule);
        spec.setRules(rules);


        body.setSpec(spec);


        try {
            V1beta1Ingress result = apiInstance.createNamespacedIngress(namespace, body, pretty);


            System.out.println(result);
        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 查询 service 详情.
     */
    @Test
    public void readNamespacedIngressTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api(client);

        String name = "test-ingress";
        String namespace = "vipcitest";
        String pretty = "true";
        Boolean exact = true;
        Boolean export = true;

        try {

            V1beta1Ingress result = apiInstance.readNamespacedIngress(name, namespace, pretty, exact, export);
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

        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api(client);

        String name = "test-ingress";
        String namespace = "vipcitest";
        String pretty = "true";

        // metadata 节点

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName(name);

        //为了保证对资源对象操作的原子性，resourceVersion是用于标识一个资源对象的内部版本的一个字符串，客户端可以通过它来判断该对象是否被更新过。
        //更多可查看 https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.10/#service-v1-core
        //设置为 0，只返回在缓存中的内容。
        meta.setResourceVersion("0");


        // spec 节点
        V1beta1IngressSpec spec = new V1beta1IngressSpec();


        // rules
        List<V1beta1IngressRule> rules = new ArrayList<>();


        V1beta1IngressRule rule  = new V1beta1IngressRule();

        rule.setHost("vipcitest.test.shishike.com");


        V1beta1HTTPIngressRuleValue http = new V1beta1HTTPIngressRuleValue();

        List<V1beta1HTTPIngressPath> paths = new ArrayList<>();

        V1beta1HTTPIngressPath v1beta1HTTPIngressPath = new V1beta1HTTPIngressPath();
        v1beta1HTTPIngressPath.setPath("/");
        V1beta1IngressBackend backend = new V1beta1IngressBackend();
        backend.setServiceName("k8s-op-test");
        backend.setServicePort(new IntOrString(8080));
        v1beta1HTTPIngressPath.setBackend(backend);

        paths.add(v1beta1HTTPIngressPath);

        http.setPaths(paths);

        rule.setHttp(http);

        rules.add(rule);
        spec.setRules(rules);



        try {

            V1beta1Ingress body = new V1beta1Ingress();
            body.setMetadata(meta);
            body.setSpec(spec);

            V1beta1Ingress result = apiInstance.replaceNamespacedIngress(name, namespace, body, pretty);
            System.out.println(result);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 删除 service.
     */
    @Test
    public void deleteNamespacedIngressTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api(client);

        String name = "test-ingress";
        String namespace = "vipcitest";
        String pretty = "true";
        Integer gracePeriodSeconds = 0;
        Boolean orphanDependents = true;
        String propagationPolicy = "Orphan";

        V1DeleteOptions body = new V1DeleteOptions();

        try {

            V1Status result = apiInstance.deleteNamespacedIngress(name, namespace, body, pretty, gracePeriodSeconds, orphanDependents, propagationPolicy);

            System.out.println(result);
            System.out.println(result.getStatus());

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }
    }


}

