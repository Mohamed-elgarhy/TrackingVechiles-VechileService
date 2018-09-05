package com.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class VechileController {

	@Autowired
	private VechileRepository repository;

	@Autowired
	private CustomerServiceProxy proxy;

	@GetMapping("/vechile/{vechileId}")
	public Vechile retrieveVechileById(@PathVariable Long vechileId) {

		Optional<Vechile> exchangeValue = repository.findById(vechileId);

		return exchangeValue.orElse(new Vechile(1001L, "default", "REG_num", 1L, "Disconnected"));
	}

	@GetMapping("/vechile/bean/{vechileId}")
	public VechileBean retrieveVechileBeanById(@PathVariable Long vechileId) {

		// Optional<Vechile> exchangeValue = repository.findById(vechileId);

		return new VechileBean(123L, "cust_name");
	}

	@GetMapping("/vechiles/secured")
	public Response  retrieveVechileBeanListSecured() {

		/*
		 * ResponseEntity<CustomerDataBean> responseEntity = new
		 * RestTemplate().getForEntity( "http://localhost:8100/customer",
		 * CustomerDataBean.class, new HashMap<>());
		 */

		List<CustomerDataBean> customerDataList = proxy.retrieveVechileBeanList();

		//customerDataList.forEach(System.out::println);

		Map<Long, String> cutomerMap = customerDataList.stream()
				.collect(Collectors.toMap(CustomerDataBean::getId, CustomerDataBean::getCustomerName));

		Set<Long> set = cutomerMap.keySet();
		System.out.println("--before map");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Long long1 = (Long) iterator.next();
			System.out.println(cutomerMap.get(long1));
		}
		System.out.println("--after map");
		// CustomerDataBean response = responseEntity.getBody();

		// System.out.println("-------------------------->response"+response);

		List<Vechile> vechiles = repository.findAll();

		//vechiles.forEach(System.out::println);

		/*
		 * HashMap<Long, String> hashMap = new HashMap<>(); hashMap.put(12L,
		 * "Kristopher"); hashMap.put(13L, "Sam");
		 */

		System.out.println("printing " + vechiles.size());

		List<VechileBean> result = vechiles.stream()
				.map(item -> new VechileBean(item.getId(), cutomerMap.get(item.getOwnerCustomerId())))
				.collect(Collectors.toList());

		return Response.ok(result).header("Access-Control-Allow-Origin", "http://localhost:9080").build();
	}
	
/*	@OPTIONS
	  @Path("/vechiles")
	  public Response getOptions() {
	    return Response.ok()
	      .header("Access-Control-Allow-Origin", "http://localhost:9080")
	      .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
	      .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	  }*/
	
	@GetMapping("/vechiles")
	//@CrossOrigin(origins="http://localhost:7711")
	@CrossOrigin(origins="http://localhost:4200")
	public List<VechileBean> retrieveVechileBeanList() {

		/*
		 * ResponseEntity<CustomerDataBean> responseEntity = new
		 * RestTemplate().getForEntity( "http://localhost:8100/customer",
		 * CustomerDataBean.class, new HashMap<>());
		 */

		List<CustomerDataBean> customerDataList = proxy.retrieveVechileBeanList();

		//customerDataList.forEach(System.out::println);

		Map<Long, String> cutomerMap = customerDataList.stream()
				.collect(Collectors.toMap(CustomerDataBean::getId, CustomerDataBean::getCustomerName));

		Set<Long> set = cutomerMap.keySet();
		System.out.println("--before map");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Long long1 = (Long) iterator.next();
			System.out.println(cutomerMap.get(long1));
		}
		System.out.println("--after map");
		// CustomerDataBean response = responseEntity.getBody();

		// System.out.println("-------------------------->response"+response);

		List<Vechile> vechiles = repository.findAll();

		//vechiles.forEach(System.out::println);

		/*
		 * HashMap<Long, String> hashMap = new HashMap<>(); hashMap.put(12L,
		 * "Kristopher"); hashMap.put(13L, "Sam");
		 */

		System.out.println("printing " + vechiles.size());

		List<VechileBean> result = vechiles.stream()
				.map(item -> new VechileBean(item.getId(), cutomerMap.get(item.getOwnerCustomerId())))
				.collect(Collectors.toList());

		return result;
	}
}