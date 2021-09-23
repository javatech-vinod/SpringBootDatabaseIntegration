package com.atradius.cibt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

	@FeignClient(url="${user.service}",name="SERVICE-CLIENT")
	public interface UserDetailsAPIService {

		@GetMapping("/port")
		public String getMicro2Instance();


}
