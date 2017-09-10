package com.ps.integration;

import com.ps.base.PetType;
import com.ps.base.UserType;
import com.ps.ents.Pet;
import com.ps.ents.User;
import com.ps.repo.stub.StubPetRepo;
import com.ps.repos.NotFoundException;
import com.ps.services.PetService;
import com.ps.services.impl.SimplePetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static com.ps.util.TestObjectsBuilder.buildPet;
import static com.ps.util.TestObjectsBuilder.buildUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by iuliana.cosmina on 4/17/16.
 */
public class SpringPetServiceTest {

    public static final Long PET_ID = 1L;
    public static final User owner = buildUser("test@gmail.com", "a!2#tre", UserType.OWNER);
	public static final long NEGATIVE_ID = 99L;

	private StubPetRepo stubPetRepo = new StubPetRepo();

	PetService simplePetService;

	@Before
	public void setUp() throws Exception {
		stubPetRepo.save(buildPet(owner, PetType.CAT, "John", 3, "0122345645"));
		stubPetRepo.save(buildPet(owner, PetType.DOG, "Max", 5, "0222335645"));

		simplePetService = new SimplePetService();
		simplePetService.setRepo(stubPetRepo);
	}



    //positive test, we know that a Pet with ID=1 exists
    @Test
    public void findByIdPositive() {
        Pet pet = simplePetService.findById(PET_ID);
        assertNotNull(pet);
    }

    //positive test, we know that pets for this owner exist and how many
    @Test
    public void findByOwnerPositive() {
        Set<Pet> result = simplePetService.findAllByOwner(owner);
        assertEquals(result.size(), 2);
    }

    @Test
    public void findByOwnerNegative() {
		User newOwner = buildUser("gigi@gmail.com", "test", UserType.OWNER);
		Set<Pet> result = simplePetService.findAllByOwner(newOwner);
		assertEquals(result.size(), 0);
	}

	@Test
	public void deleteByIdPositive() {
		simplePetService.deleteById(PET_ID);
		Pet pet = simplePetService.findById(PET_ID);
		assertNull(pet);
	}

	@Test(expected = NotFoundException.class)
	public void deleteByIdNegative() {
		simplePetService.deleteById(NEGATIVE_ID);
	}


}
