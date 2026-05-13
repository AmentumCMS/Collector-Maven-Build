package com.amentumcms.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestDemoApplication.class)
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
