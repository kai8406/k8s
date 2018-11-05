package com.keruyun.k8s.deploy;

import com.keruyun.k8s.token.TokenConstant;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1beta1Api;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeploymentTest {


    /**
     * 查询 deployment 列表.
     */
    @Test
    public void listNamespacedDeploymentTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);

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

            AppsV1beta1DeploymentList result = apiInstance.listNamespacedDeployment(namespace, pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);

            result.getItems().stream().forEach(s -> System.out.println(s.getMetadata().getName()));

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * 创建 deployment.
     */
    @Test
    public void createNamespacedDeploymentTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);

        String namespace = "test";
        String pretty = "true";


        AppsV1beta1Deployment body = new AppsV1beta1Deployment();


        body.setSpec(wrapperDeployment_Spec(namespace));
        body.setMetadata(wrapperDeployment_Metadata(namespace));


        try {

            AppsV1beta1Deployment result = apiInstance.createNamespacedDeployment(namespace, body, pretty);

            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 查询 deployment 详情.
     */
    @Test
    public void readNamespacedDeploymentTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);

        String namespace = "test";
        String name = "k8s-demotest-viptest";
        String pretty = "true";
        Boolean exact = true;
        Boolean export = true;

        try {

            AppsV1beta1Deployment result = apiInstance.readNamespacedDeployment(name, namespace, pretty, exact, export);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 查询 deployment scale 详情.
     */
    @Test
    public void readNamespacedDeploymentScaleTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);

        String namespace = "test";
        String name = "k8s-demotest-viptest";
        String pretty = "true";

        try {

            AppsV1beta1Scale result = apiInstance.readNamespacedDeploymentScale(name, namespace, pretty);
            System.out.println(result);
            System.out.println(result.getSpec().getReplicas());

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }


    /**
     * replace deployment scale .
     */
    @Test
    public void replaceNamespacedDeploymentScaleTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);
        String namespace = "test";
        String name = "k8s-demotest-viptest";
        String pretty = "true";

        AppsV1beta1Scale body = new AppsV1beta1Scale();


        AppsV1beta1ScaleSpec spec = new AppsV1beta1ScaleSpec();
        spec.setReplicas(1);

        V1ObjectMeta metadata = new V1ObjectMeta();
        Map<String, String> map = new HashMap<>();
        map.put("k8s-app", name);

        metadata.setName(name);
        metadata.setLabels(map);
        metadata.setNamespace(namespace);

        body.setMetadata(metadata);
        body.setSpec(spec);

        try {
            AppsV1beta1Scale result = apiInstance.replaceNamespacedDeploymentScale(name, namespace, body, pretty);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }
    }


    /**
     * replace deployment 内容.
     */
    @Test
    public void replaceNamespacedDeploymentTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);


        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);

        String namespace = "test";
        String name = "k8s-demotest-viptest";
        String pretty = "true";
        AppsV1beta1Deployment body = new AppsV1beta1Deployment();


        body.setSpec(wrapperDeployment_Spec(namespace));
        body.setMetadata(wrapperDeployment_Metadata(namespace));

        try {

            AppsV1beta1Deployment result = apiInstance.replaceNamespacedDeployment(name, namespace, body, pretty);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }

    }

    /**
     * 删除 deployment.
     * <p>
     * ps: 执行会抛异常,deployment 会删除,但是 Pod 没有删除,SDK 本身的 Bug.
     * <p>
     * 更多详情查看: https://github.com/kubernetes-client/java/issues/86
     */
    @Test
    public void deleteNamespaceTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);

        String namespace = "test";
        String name = "k8s-demotest-viptest";
        String pretty = "true";
        V1DeleteOptions body = new V1DeleteOptions();
        Integer gracePeriodSeconds = 10;
        Boolean orphanDependents = true;
        String propagationPolicy = "Foreground";

        try {

            V1Status result = apiInstance.deleteNamespacedDeployment(name, namespace, body, pretty, gracePeriodSeconds, orphanDependents, propagationPolicy);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }
    }


    /**
     * 通过 API 删除 namespace,注意 RestTemplate 要绕开 SSL 认证..
     * <p>
     * ps: 执行会抛异常,deployment 会删除,但是 Pod 没有删除,SDK 本身的 Bug.
     * <p>
     * 更多详情查看: https://github.com/kubernetes-client/java/issues/86
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


        String namespace = "test";
        String name = "k8s-demotest-viptest";


        String deleteUrl = TokenConstant.URL + "/apis/apps/v1/namespaces/" + namespace + "/deployments/" + name;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization ", "Bearer " + TokenConstant.TOKEN);
        headers.add("Content-Type ", "application/json");

        HttpEntity<?> entity = new HttpEntity(params, headers);

        ResponseEntity<String> result = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);
        System.err.println(result);

    }


    /**
     * 创建一个 deployment 的 rollback.
     */
    @Test
    public void createNamespacedDeploymentRollbackTest() {

        ApiClient client = Config.fromToken(TokenConstant.URL, TokenConstant.TOKEN, false);

        AppsV1beta1Api apiInstance = new AppsV1beta1Api(client);


        String name = "k8s-demotest-viptest";
        String namespace = "test";
        String pretty = "true";

        AppsV1beta1DeploymentRollback body = new AppsV1beta1DeploymentRollback();

        AppsV1beta1RollbackConfig rollbackConfig = new AppsV1beta1RollbackConfig();
        rollbackConfig.setRevision(0L); // roll back 到指定的修订版本，如果为 0，则回滚到上一个修订版本
        body.setRollbackTo(rollbackConfig);
        body.setName(name);

        try {

            AppsV1beta1DeploymentRollback result = apiInstance.createNamespacedDeploymentRollback(name, namespace, body, pretty);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        }


    }

    /**
     * 封装 deployment.metadata 对象.
     *
     * @param namespace
     * @return
     */
    private V1ObjectMeta wrapperDeployment_Metadata(String namespace) {

        V1ObjectMeta meta = new V1ObjectMeta();

        Map<String, String> metaLabelsMap = new HashMap<>();
        metaLabelsMap.put("k8s-app", "k8s-demotest-viptest");

        meta.setName("k8s-demotest-viptest");
        meta.setNamespace(namespace);
        meta.setLabels(metaLabelsMap);

        return meta;
    }

    /**
     * 封装 deployment.spec 对象.
     */
    private AppsV1beta1DeploymentSpec wrapperDeployment_Spec(String namespace) {

        AppsV1beta1DeploymentSpec spec = new AppsV1beta1DeploymentSpec();

        Map<String, String> metaLabelsMap = new HashMap<>();
        metaLabelsMap.put("k8s-app", "k8s-demotest-viptest");

        Integer replicas = 3;

        // selector
        V1LabelSelector selector = new V1LabelSelector();
        Map<String, String> map = new HashMap<>();
        map.put("k8s-app", "k8s-demotest-viptest");
        selector.setMatchLabels(map);

        spec.setReplicas(replicas);
        spec.selector(selector);
        spec.setTemplate(wrapperDeployment_Spec_Template(namespace));

        return spec;
    }

    /**
     * 封装 deployment.spec.template 对象.
     */
    private V1PodTemplateSpec wrapperDeployment_Spec_Template(String namespace) {

        V1PodTemplateSpec templateSpec = new V1PodTemplateSpec();

        V1ObjectMeta metadata = new V1ObjectMeta();
        Map<String, String> map = new HashMap<>();
        map.put("k8s-app", "k8s-demotest-viptest");

        metadata.setLabels(map);

        templateSpec.setMetadata(metadata);
        templateSpec.setSpec(wrapperDeployment_Spec_Template_PodSpec(namespace));

        return templateSpec;
    }

    /**
     * 封装 deployment.spec.template.spec 对象.
     */
    private V1PodSpec wrapperDeployment_Spec_Template_PodSpec(String namespace) {

        V1PodSpec podSpec = new V1PodSpec();

        // volumes
        List<V1Volume> volumes = new ArrayList<>();

        V1Volume volume = new V1Volume();

        volume.setName("localtime");

        V1HostPathVolumeSource hostPath = new V1HostPathVolumeSource();
        hostPath.setPath("/etc/localtime");

        volume.setHostPath(hostPath);

        volumes.add(volume);

        podSpec.setVolumes(volumes);


        // imagePullSecrets
        List<V1LocalObjectReference> imagePullSecrets = new ArrayList<>();
        V1LocalObjectReference reference = new V1LocalObjectReference();
        reference.setName("regsecret");

        podSpec.setImagePullSecrets(imagePullSecrets);


        //containers
        podSpec.setContainers(wrapperDeployment_Spec_Template_PodSpec_Container(namespace));


        return podSpec;
    }


    /**
     * 封装 deployment.spec.template.spec.container 对象.
     */
    private List<V1Container> wrapperDeployment_Spec_Template_PodSpec_Container(String namespace) {


        // containers

        List<V1Container> containers = new ArrayList<>();

        V1Container container = new V1Container();

        container.setName("k8s-demotest-viptest");
        container.setImage("registry-vpc.cn-hangzhou.aliyuncs.com/shishike/oom:1.0");
        container.setImagePullPolicy("Always");


        // containers / env
        List<V1EnvVar> envs = new ArrayList<>();

        V1EnvVar envVar1 = new V1EnvVar();
        envVar1.setName("CURRENT_ENV");
        envVar1.setValue(namespace);
        envs.add(envVar1);

        V1EnvVar envVar2 = new V1EnvVar();
        envVar2.setName("CONTAINER_NAME");
        envVar2.setValue("k8s-demotest-viptest");
        envs.add(envVar2);

        V1EnvVar envVar3 = new V1EnvVar();
        envVar3.setName("JAVA_OPTS");
        envVar3.setValue("-Duser.timezone=GMT+08");
        envs.add(envVar3);

        container.setEnv(envs);

        // containers / ports

        List<V1ContainerPort> containerPorts = new ArrayList<>();
        V1ContainerPort containerPort = new V1ContainerPort();
        containerPort.setContainerPort(8080);
        containerPort.setProtocol("TCP");
        containerPorts.add(containerPort);

        container.setPorts(containerPorts);

        // containers / volumeMounts

        List<V1VolumeMount> volumeMounts = new ArrayList<>();
        V1VolumeMount volumeMount = new V1VolumeMount();
        volumeMount.setMountPath("/etc/localtime");
        volumeMount.setName("localtime");
        volumeMounts.add(volumeMount);

        container.setVolumeMounts(volumeMounts);


        containers.add(container);

        return containers;
    }


}

