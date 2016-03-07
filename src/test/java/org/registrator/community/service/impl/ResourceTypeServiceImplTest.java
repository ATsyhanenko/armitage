package org.registrator.community.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.TypeParameterDTO;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ResourceTypeServiceImplTest {
	ResourceTypeRepository resTypeRep = mock(ResourceTypeRepository.class);
	LinearParameterRepository linParRep = mock(LinearParameterRepository.class);
	DiscreteParameterRepository discParRep = mock(DiscreteParameterRepository.class);
	ResourceService resServ = mock(ResourceService.class);
	Logger logger = LoggerFactory.getLogger(ResourceTypeServiceImplTest.class);
	ResourceTypeService resTServ = new ResourceTypeServiceImpl();
	int testRuns = 10;
	int resId = 0;

	@BeforeClass
	public void reflectOperations()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		logger.info("Performing reflect operations to replace the inner bonds of the class with other classes");

		Class<?> cls = resTServ.getClass();

		Field field = cls.getDeclaredField("resourceTypeRepository");
		field.setAccessible(true);
		field.set(resTServ, resTypeRep);

		field = cls.getDeclaredField("linearParameterRepository");
		field.setAccessible(true);
		field.set(resTServ, linParRep);

		field = cls.getDeclaredField("discreteParameterRepository");
		field.setAccessible(true);
		field.set(resTServ, discParRep);

		field = cls.getDeclaredField("resourceService");
		field.setAccessible(true);
		field.set(resTServ, resServ);

		field = cls.getDeclaredField("logger");
		field.setAccessible(true);
		field.set(resTServ, logger);
	}

	@BeforeClass
	public void prepareMockForResourceTypeRep() {
		/*
		 * ResourceType resourceTypeRepository.saveAndFlush(resourceType); +
		 * ResourceType resourceTypeRepository.findOne(id); + ResourceType
		 * resourceTypeRepository.findByName(name); + List<ResourceType>
		 * resourceTypeRepository.findAll(); + void
		 * resourceTypeRepository.delete(resourceType); + int
		 * resourceTypeRepository.count()
		 * 
		 * Object: resTypeRep
		 */
		logger.info("Preparing the Mock object to suite the needed calls");

		List<ResourceType> mockList = new ArrayList<ResourceType>();

		when(resTypeRep.saveAndFlush(any(ResourceType.class))).then(new Answer<ResourceType>() {
			public ResourceType answer(InvocationOnMock invo) {
				ResourceType arg = invo.getArgumentAt(0, ResourceType.class);
				mockList.add(arg);
				return arg;
			}
		});

		when(resTypeRep.findOne(anyInt())).then(new Answer<ResourceType>() {
			public ResourceType answer(InvocationOnMock invo) {
				Integer arg = invo.getArgumentAt(0, Integer.class);
				for (ResourceType res : mockList) {
					int resId = (res.getTypeId() != null) ? res.getTypeId() : -1;// TODO
																					// watch
																					// for
																					// NullExceptions
					if (resId == arg) {
						return res;
					}
				}
				return null;
			}
		});

		when(resTypeRep.findByName(anyString())).then(new Answer<ResourceType>() {
			public ResourceType answer(InvocationOnMock invo) {
				String arg = invo.getArgumentAt(0, String.class);
				for (ResourceType res : mockList) {
					String resName = res.getTypeName();
					if (resName.equals(arg)) {
						return res;
					}
				}
				return null;
			}
		});

		when(resTypeRep.findAll()).then(new Answer<List<ResourceType>>() {
			public List<ResourceType> answer(InvocationOnMock invo) {
				return mockList;
			}
		});

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invo) {
				ResourceType arg = invo.getArgumentAt(0, ResourceType.class);
				mockList.remove(arg);
				return null;
			}
		}).when(resTypeRep).delete(any(ResourceType.class));

		when(resTypeRep.count()).then(new Answer<Long>() {
			public Long answer(InvocationOnMock invo) {
				Long size = new Long(mockList.size());
				return size;
			}
		});
	}

	@BeforeClass
	public void prepareMockForLinearParamRep() {
		/*
		 * linearParameterRepository.save(lp); ???
		 */
		logger.info("Preparing the Mock object to suite the needed calls");
	}

	@BeforeClass
	public void prepareMockForDiscreteParamRep() {
		/*
		 * discreteParameterRepository.save(dp); ???
		 */
		logger.info("Preparing the Mock object to suite the needed calls");
	}

	@BeforeClass
	public void prepareMockForResourceServ() {
		/*
		 * List<Resource> resourceService.findByType(resourceType); ResourceDTO
		 * addNewResource(ResourceDTO resourceDTO, String ownerLogin, User
		 * registrator);
		 * 
		 * Object: resServ
		 */
		logger.info("Preparing the Mock object to suite the needed calls");

		List<Resource> mockList = new ArrayList<Resource>();

		when(resServ.findByType(any(ResourceType.class))).then(new Answer<List<Resource>>() {
			public List<Resource> answer(InvocationOnMock invo) {
				ResourceType arg = invo.getArgumentAt(0, ResourceType.class);
				String argTypeName = arg.getTypeName();

				List<Resource> result = new ArrayList<Resource>();
				for (Resource res : mockList) {

					String resType = res.getType().getTypeName(); // TODO Watch
																	// for Null

					if (resType.equals(argTypeName)) {
						result.add(res);
					}
				}
				return result;
			}
		});

		when(resServ.addNewResource(any(ResourceDTO.class), any(String.class), any(User.class)))
				.then(new Answer<ResourceDTO>() {
					public ResourceDTO answer(InvocationOnMock invo) {
						ResourceDTO arg = invo.getArgumentAt(0, ResourceDTO.class);

						ResourceType resType = resTypeRep.findByName(arg.getResourceType());
						String resId = arg.getIdentifier();
						String resDesc = arg.getDescription();
						User resRegistrator = new User();
						Date resDate = arg.getDate();
						String resStatus = arg.getStatus().toString();
						Tome resTome = new Tome();
						String resReason = arg.getReasonInclusion();

						Resource res = new Resource(resType, resId, resDesc, resRegistrator, resDate, resStatus,
								resTome, resReason);
						mockList.add(res);

						return arg;
					}
				});
	}

	// DataProviders

	@DataProvider
	public Object[][] formResourceTypes() {
		Object[][] result = new Object[testRuns][];
		// new ResourceType(String name) + uniqResourceId

		String resMask = "ResourceType#%03d";

		for (int i = 0; i < result.length; i++) {
			ResourceType res = new ResourceType(String.format(resMask, resId));
			res.setTypeId(resId++);
			result[i] = new Object[] { res };
		}
		return result;
	}

	@DataProvider
	public Object[][] formAndFlushResourceTypes() {
		Object[][] result = new Object[testRuns][];
		// new ResourceType(String name) + uniqResourceId

		String resMask = "ResourceType#%03d";
		User user = new User();
		Date date = new Date();

		for (int i = 0; i < result.length; i++) {
			ResourceType res = new ResourceType(String.format(resMask, resId));
			res.setTypeId(resId++);
			result[i] = new Object[] { res };
			resTypeRep.saveAndFlush(res);

			if (i % 2 == 0) {
				ResourceDTO resDTO = new ResourceDTO();
				resDTO.setResourceType(res.getTypeName());
				resDTO.setIdentifier(String.valueOf(res.getTypeId()));
				resDTO.setDate(date);
				resDTO.setStatus(ResourceStatus.ACTIVE);
				resDTO.setReasonInclusion("Reason");

				resServ.addNewResource(resDTO, "dummy", user);
			}
		}
		return result;
	}

	@DataProvider
	public Object[][] formResourceTypesWithParams() {
		Object[][] result = new Object[testRuns][];

		String resMask = "ResourceType#%03d";
		String[] paramTypes = new String[] { "linearParameters", "discreteParameters" };

		List<TypeParameterDTO> paramList = new ArrayList<TypeParameterDTO>();

		for (int i = 0; i < result.length; i++) {
			ResourceType res = new ResourceType(String.format(resMask, resId));
			res.setTypeId(resId++);

			TypeParameterDTO tpDTO = new TypeParameterDTO();
			tpDTO.setParametersType(paramTypes[i % 2]);
			tpDTO.setDescription("mockVolts");
			tpDTO.setUnitName(paramTypes[i % 2] + "Unit");

			paramList.add(tpDTO);

			result[i] = new Object[] { res, paramList };
		}
		return result;
	}
	// Tests

	@Test(dataProvider = "formResourceTypes")
	public void addResourceType(ResourceType res) {
		logger.info("Performing add and save operation tests");

		long size = resTypeRep.count();

		ResourceType formed = resTServ.addResourceType(res);

		Assert.assertEquals(res.getTypeName(), formed.getTypeName());
		Assert.assertEquals(res.getTypeId(), formed.getTypeId());
		Assert.assertEquals((size + 1), resTypeRep.count());
	}

	@Test(dataProvider = "formResourceTypes")
	public void findById(ResourceType res) {
		logger.info("Performing search by ID operation tests");

		ResourceType expected = resTypeRep.saveAndFlush(res), actual = resTServ.findById(res.getTypeId());

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTypeName(), actual.getTypeName());
		Assert.assertEquals(expected.getTypeId(), actual.getTypeId());
	}

	@Test(dataProvider = "formResourceTypes")
	public void findByName(ResourceType res) {
		logger.info("Performing search by ID operation tests");

		ResourceType expected = resTypeRep.saveAndFlush(res), actual = resTServ.findByName(res.getTypeName());

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTypeName(), actual.getTypeName());
		Assert.assertEquals(expected.getTypeId(), actual.getTypeId());
	}

	@Test(dataProvider = "formResourceTypes")
	public void findAll(ResourceType res) {
		logger.info("Performing repository overview operation tests");

		resTypeRep.saveAndFlush(res);

		long repSize = resTypeRep.count();
		List<ResourceType> expected = resTypeRep.findAll(), actual = resTServ.findAll();

		Assert.assertEquals(repSize, expected.size());
		Assert.assertEquals(repSize, actual.size());

		expected.removeAll(actual);

		Assert.assertEquals(expected.size(), 0);
	}

	@Test(dataProvider = "formResourceTypes")
	public void editResourceType(ResourceType res) {
		logger.info("Performing resource edit operation tests");

		ResourceTypeDTO expected = new ResourceTypeDTO();

		expected.setTypeName(res.getTypeName());
		expected.setParameters(new ArrayList<TypeParameterDTO>());

		ResourceTypeDTO actual = resTServ.editResourceType(expected);

		Assert.assertEquals(actual.getTypeName(), expected.getTypeName());
		Assert.assertEquals(actual.getParameters(), expected.getParameters());
	}

	@Test(dataProvider = "formAndFlushResourceTypes")
	public void delete(ResourceType res) {
		logger.info("Performing find and delete operation tests");

		List<Resource> resourcesOfGivenType = resServ.findByType(res);
		logger.info("expected size: " + resourcesOfGivenType.size());

		boolean expected, actual;

		if (resourcesOfGivenType.size() > 0) {
			expected = false;
			actual = resTServ.delete(res);
		} else {
			expected = true;
			actual = resTServ.delete(res);
		}
		Assert.assertEquals(actual, expected);

		if (resourcesOfGivenType.size() == 0) {
			ResourceType expectedToBeNull = resTypeRep.findOne(res.getTypeId());
			Assert.assertNull(expectedToBeNull);
		}
	}

	@Test(dataProvider = "formResourceTypesWithParams")
	public void addResourceTypeDTO(ResourceType res, List<TypeParameterDTO> paramList) {
		logger.info("Performing ResourceTypeDTO creation operation tests");

		resTypeRep.saveAndFlush(res);

		ResourceTypeDTO expected = new ResourceTypeDTO();
		expected.setTypeName(res.getTypeName());

		List<TypeParameterDTO> typeList = paramList;
		List<LinearParameter> linearParList = new ArrayList<LinearParameter>();
		List<DiscreteParameter> discreteParList = new ArrayList<DiscreteParameter>();

		for (TypeParameterDTO tpar : typeList) {
			if (("linearParameters").equals(tpar.getParametersType())) {
				linearParList.add(new LinearParameter(res, tpar.getDescription(), tpar.getUnitName()));
			}
			if (("discreteParameters").equals(tpar.getParametersType())) {
				discreteParList.add(new DiscreteParameter(res, tpar.getDescription(), tpar.getUnitName()));
			}
		}

		expected.setParameters(typeList);

		ResourceTypeDTO tmp = new ResourceTypeDTO();
		tmp.setTypeName(res.getTypeName());
		tmp.setParameters(paramList);

		ResourceTypeDTO actual = resTServ.addResourceTypeDTO(tmp);

		Assert.assertEquals(actual.getTypeName(), expected.getTypeName());

		List<TypeParameterDTO> actualParamList = actual.getParameters(), expectedParamList = expected.getParameters();
		Assert.assertEquals(actualParamList.size(), expectedParamList.size());

		for (int i = 0; i < actualParamList.size(); i++) {
			TypeParameterDTO arg0 = actualParamList.get(i), arg1 = expectedParamList.get(i);

			Assert.assertEquals(arg0.getParametersType(), arg1.getParametersType());
			Assert.assertEquals(arg0.getDescription(), arg1.getDescription());
			Assert.assertEquals(arg0.getUnitName(), arg1.getUnitName());
		}

	}

}
