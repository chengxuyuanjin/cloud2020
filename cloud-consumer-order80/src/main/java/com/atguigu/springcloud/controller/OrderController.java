package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: zhanghuijin
 * @date: 2023/10/20/10:54
 * @Description:
 */
@RestController
@Slf4j
public class OrderController
{

//    通过在eureka上注册过的微服务名称调用
    public static final String PaymentSrv_URL = "http://CLOUD-PAYMENT-SERVICE";
    //通过实际IP端口调用
//    public static final String PaymentSrv_URL = "http://localhost:8001";
    @Resource
    private RestTemplate restTemplate;
    @GetMapping("/consumer/payment/create")//客户端用浏览器是get请求，但是底层实质发送post调用服务端8001
    public CommonResult create(Payment payment)
    {
        log.info("插入的数据为："+payment);
        return restTemplate.postForObject(PaymentSrv_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPayment(@PathVariable Long id)
    {
        log.info("调用的地址："+PaymentSrv_URL);
        return restTemplate.getForObject(PaymentSrv_URL+"/payment/get/"+id,CommonResult.class,id);
    }
    @GetMapping("/consumer/serviceDiscovery")
    public CommonResult getServiceInfos()
    {
        log.info("开始调用服务发现,"+"调用的地址："+PaymentSrv_URL);
//        restTemplate.getForObject(PaymentSrv_URL+"/payment/discovery",Object.class);
        return new CommonResult(200,"服务发现调用成功",restTemplate.getForObject(PaymentSrv_URL+"/payment/discovery",Object.class));
    }
}
