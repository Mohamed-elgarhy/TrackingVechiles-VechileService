package com.server.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

		return new VechileBean();
	}

	@RequestMapping(value = "/updateVechile", method = RequestMethod.POST)
	public Response updateStatus(@RequestBody VechileBean vechile) {

		Optional<Vechile> objectToUpdate = repository.findById(Long.valueOf(vechile.getVechileId()));

		if (objectToUpdate.isPresent()) {
			Vechile updatedObject = objectToUpdate.get();
			updatedObject.setStatus(vechile.getStatus());
			repository.save(updatedObject);
		} else {
			System.out.println("not found");
			// handle not found
		}

		return Response.ok("success").build();
	}

	@GetMapping("/vechiles")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<VechileBean> retrieveVechileBeanList() {

		List<CustomerDataBean> customerDataList = proxy.retrieveVechileBeanList();

		Map<Long, Object> cutomerMap = customerDataList.stream()
				.collect(Collectors.toMap(CustomerDataBean::getId, item -> item));

		List<Vechile> vechiles = repository.findAll();

		List<VechileBean> result = vechiles.stream()
				.map(item -> new VechileBean(item.getVechileId(),
						((CustomerDataBean) cutomerMap.get(item.getOwnerCustomerId())).getCustomerName(),
						item.getStatus(), item.getRegisterationNumber(),
						((CustomerDataBean) cutomerMap.get(item.getOwnerCustomerId())).getCustomerAddress()))
				.collect(Collectors.toList());

		return result;
	}
}