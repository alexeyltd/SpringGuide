package com.ps.integration;

import com.ps.base.UserType;
import com.ps.ents.Pet;
import com.ps.ents.User;
import com.ps.repo.stub.StubPetRepo;
import com.ps.services.PetService;
import com.ps.services.impl.SimplePetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Set;

import static com.ps.util.TestObjectsBuilder.buildUser;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SpringPetServiceTest3 {

	public static final Long PET_ID = 1L;
	public static final User owner = buildUser("test@gmail.com", "test", UserType.OWNER);


	@Configuration
	static class TestCtxConfig {

		@Bean
		StubPetRepo petRepo() {
			return new StubPetRepo();
		}

		@Bean
		PetService simplePetService() {
			SimplePetService petService = new SimplePetService();
			petService.setRepo(petRepo());
			return petService;
		}

	}

	@Autowired
	PetService simplePetService;

	@Test
	public void findByOwnerPositive() {
		Set<Pet> result = simplePetService.findAllByOwner(owner);
		assertEquals(result.size(), 2);
	}
}
