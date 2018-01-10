/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.netflix.hystrix;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.test.TestAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author Dave Syer
 * @author Spencer Gibb
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, value = {
		"spring.application.name=hystrixstreamtest" })
@DirtiesContext
public class HystrixWebfluxControllerTests {
	private static final Log log = LogFactory.getLog(HystrixWebfluxControllerTests.class);

	@LocalServerPort
	private int port = 0;

	public static void main(String[] args) {
		new SpringApplicationBuilder(Config.class)
				.web(WebApplicationType.REACTIVE)
				.run(/*args*/ "--debug");
	}

	@RestController
	@EnableCircuitBreaker
	@EnableAutoConfiguration(exclude = TestAutoConfiguration.class,
			excludeName = {"org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration",
			"org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration"})
	@SpringBootConfiguration
	protected static class Config {

		@HystrixCommand
		@RequestMapping("/")
		public String hi() {
			return "hi";
		}
	}
}
