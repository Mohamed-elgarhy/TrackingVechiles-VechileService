package com.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.constants.ValidatorConstants;
import com.server.services.validators.BeansValidator;

@RestController
public class VechileController {

	@Autowired
	private VechileRepository repository;

	@Autowired
	private CustomerServiceProxy proxy;

	/**
	 * @param vechileId - Identifier for the vechile to be fetched and returned
	 * @return Response Entity of type Vechile
	 */
	@GetMapping("/vechile")
	public ResponseEntity<Vechile> retrieveVechileById(@RequestParam Long vechileId) {

		Optional<Vechile> vechile = repository.findById(vechileId);

		if (vechile.isPresent()) {
			return ResponseEntity.ok().body(vechile.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	/**
	 * @param vechile - object of type VechileBean should at least contaian vechile id and status as JSON
	 * @return Response 
	 */
	@RequestMapping(value = "/vechile/update/status", method = RequestMethod.POST)
	public Response updateStatus(@RequestBody VechileBean vechile) {

		// validate the bean
		Short result = BeansValidator.validateVechileBeanForStatusUpdate(vechile);
		
		if (result == ValidatorConstants.EMPTY_STATUS_NOT_ALLOWED)
		{
			return Response.notModified().status(Status.BAD_REQUEST).entity("status can not be empty").build();
		}
		if (result == ValidatorConstants.EMPTY_VECHILE_ID_NOT_ALLOWED)
		{
			return Response.notModified().status(Status.BAD_REQUEST).entity("vechile id can not be empty").build();
		}
		
		
		Optional<Vechile> objectToUpdate = repository.findById(Long.valueOf(vechile.getVechileId()));

		if (objectToUpdate.isPresent()) {
			Vechile updatedObject = objectToUpdate.get();
			updatedObject.setStatus(vechile.getStatus());
			repository.save(updatedObject);
		} else {
			return Response.status(Status.NOT_FOUND).entity("Object not found").build();
			// handle not found
		}

		return Response.ok("success").build();
	}

	@RequestMapping(value = "/updateVechileEntity", method = RequestMethod.POST)
	public ResponseEntity<Vechile> updateVechileStatus(@RequestBody VechileBean vechile) {

		Optional<Vechile> objectToUpdate = repository.findById(Long.valueOf(vechile.getVechileId()));
		Vechile updatedObject;
		if (objectToUpdate.isPresent()) {
			updatedObject = objectToUpdate.get();
			updatedObject.setStatus(vechile.getStatus());
			repository.save(updatedObject);
		} else {
			return ResponseEntity.notFound().build();
			// handle not found
		}

		return ResponseEntity.ok().body(updatedObject);
	}

	/**
	 * This function retrieves customer list (through customer service) and vechile list 
	 * then find intersection between the two lists to return.
	 * 
	 * @return List<VechileBean> 
	 */
	@GetMapping("/vechiles")
	@CrossOrigin(origins = "http://localhost:4200") // this is to allow connections from Angular on 4200 port
	public List<VechileBean> retrieveVechileBeanList() {

		List<CustomerDataBean> customerDataList = proxy.retrieveVechileBeanList();

		Map<Long, Object> cutomerMap;
		if (!customerDataList.isEmpty()) {
			cutomerMap = customerDataList.stream().collect(Collectors.toMap(CustomerDataBean::getId, item -> item));
		} else {
			return new ArrayList<>();
		}

		List<Vechile> vechiles = repository.findAll();

		List<VechileBean> result = new ArrayList<>();
		if (!vechiles.isEmpty()) {
			result = vechiles.stream().filter(item ->

			cutomerMap.get(item.getOwnerCustomerId()) != null)
					.map(item -> new VechileBean(item.getVechileId(),
							((CustomerDataBean) cutomerMap.get(item.getOwnerCustomerId())).getCustomerName(),
							item.getStatus(), item.getRegisterationNumber(),
							((CustomerDataBean) cutomerMap.get(item.getOwnerCustomerId())).getCustomerAddress()))
					.collect(Collectors.toList());

		}

		return result;
	}
}