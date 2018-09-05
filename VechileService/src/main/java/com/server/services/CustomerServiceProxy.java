package com.server.services;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="customer-service")
@RibbonClient(name="customer-service")
public interface CustomerServiceProxy {
  @GetMapping("/customers")
  public List<CustomerDataBean> retrieveVechileBeanList();
  @GetMapping("/customer")
	public CustomerDataBean retrieveVechileBean();
}