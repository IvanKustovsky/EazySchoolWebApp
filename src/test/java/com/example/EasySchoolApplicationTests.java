package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasySchoolApplicationTests {

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

}
