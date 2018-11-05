package com.keruyun.k8s.namespace;

import com.keruyun.k8s.token.TokenConstant;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class NamespaceTest {


    /**
     * 查询 namespace 列表.
     */
    @Test
    public void listNamespaceTest() {

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

            V1NamespaceList result = apiInstance.listNamespace(pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);

            result.getItems().stream().forEach(s -> System.out.println(s.getMetadata().getName()));

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 创建 namespace.
     */
    @Test
    public void createNamespaceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("test");

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
    public void readNamespaceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "vipcitest";
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
    public void replaceNamespaceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "test";
        String pretty = "true";

        Map<String, String> map = new HashMap<>();
        map.put("keruyun", "test");

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("test");
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
    public void deleteNamespaceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        CoreV1Api apiInstance = new CoreV1Api(client);

        String name = "test";
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
     * 通过 API 删除 namespace,注意 RestTemplate 要绕开 SSL 认证.
     * <p>
     * https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.10/#delete-483
     */
    @Test
    public void deleteNamespaceByAPITest() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        //绕开 ssl 认证,修改 RestTemplate 配置.
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String name = "test";

        String deleteUrl = TokenConstant.URL + "/api/v1/namespaces/" + name;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization ", "Bearer " + TokenConstant.TOKEN);
        headers.add("Content-Type ", "application/json");

        HttpEntity<?> entity = new HttpEntity(params, headers);

        ResponseEntity<String> result = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);
        System.err.println(result);

    }


}

