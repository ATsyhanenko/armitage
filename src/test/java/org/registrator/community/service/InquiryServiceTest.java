package org.registrator.community.service;

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
import java.util.Random;

import org.junit.Assert;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.impl.InquiryServiceImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;




public class InquiryServiceTest {
  UserRepository userRep = mock(UserRepository.class);
  InquiryRepository inqRep = mock(InquiryRepository.class);
  ResourceRepository resRep = mock(ResourceRepository.class);
  InquiryService inqS = new InquiryServiceImpl();
  Date date = new Date();
  
  // java.lang.reflect repository preparations(WIP)
  
  @BeforeClass
  public void bigBadMethod() throws Exception{
  Class<?> cls = InquiryServiceImpl.class;
	  
	  Field field = cls.getDeclaredField("inquiryRepository");
	  field.setAccessible(true);
	  field.set(inqS, inqRep);
	
	  field = cls.getDeclaredField("userRepository");
	  field.setAccessible(true);
	  field.set(inqS, userRep);
	  
	  field = cls.getDeclaredField("resourceRepository");
	  field.setAccessible(true);
	  field.set(inqS, resRep);
	  
	  field = cls.getDeclaredField("logger");
	  field.setAccessible(true);
	  field.set(inqS, mock(field.getType()));

  }
  
  //  Mock repository preparations and bindings
  
  @BeforeClass
  public void prepareUserRepMock(){
	  List<User> mockForUserRep = new ArrayList<User>();
	  
	  when(userRep.count()).then(new Answer<Long>(){
		  public Long answer(InvocationOnMock invocation) throws Throwable{
			  return (long)mockForUserRep.size();
		  }
	  });
	  when(userRep.save(any(User.class))).then(new Answer<User>(){
		@Override
		public User answer(InvocationOnMock invocation) throws Throwable {
			User givenArg = (User) invocation.getArguments()[0];
			mockForUserRep.add(givenArg);
			return givenArg;
		}
		  
	  });
	  
	  when(userRep.findOne(anyInt())).then(new Answer<User>(){
		 @Override
		 public User answer(InvocationOnMock invocation) throws Throwable{
			 Integer num = invocation.getArgumentAt(0, Integer.class);
			 return mockForUserRep.get(num);
		 }
	  });
	  
	  when(userRep.findUserByLogin(anyString())).then(new Answer<User>(){
		 public User answer(InvocationOnMock invocation) throws Throwable{
			 String userName = invocation.getArgumentAt(0, String.class);
			 for(User usr: mockForUserRep){
				 if(usr.getLogin().equals(userName)){
					 return usr;
				 }
			 }
			 return null;
		 }
	  });
	  when((userRep.getUsersByRoleAndCommunity(any(RoleType.class),any(TerritorialCommunity.class)))).then(new Answer<List<User>>(){
			 public List<User> answer(InvocationOnMock invocation) throws Throwable{
				 List<User> result = new ArrayList<User>();
				 TerritorialCommunity tc = invocation.getArgumentAt(1, TerritorialCommunity.class);
				 
				 for(User usr : mockForUserRep){
					 if(usr.getRole().getType() == RoleType.REGISTRATOR && usr.getTerritorialCommunity() != null &&usr.getTerritorialCommunity().equals(tc)){
						 result.add(usr);
					 }
				 }
				 return result;
			 }
		  });
	  
	  doAnswer(new Answer<Void>(){
		 public Void answer(InvocationOnMock invocation) throws Throwable{
			 mockForUserRep.clear();
			 return null;
		 }
	  }).when(userRep).deleteAll();
  }
  
  @BeforeClass
  public void prepareResourceRepMock(){
	  List<Resource> mockForResRep = new ArrayList<Resource>();
	  
	  when(resRep.count()).then(new Answer<Long>(){
		  public Long answer(InvocationOnMock invocation) throws Throwable{
			  return (long)mockForResRep.size();
		  }
	  });
	  when(resRep.findByIdentifier(anyString())).then(new Answer<Resource>(){
		 public Resource answer(InvocationOnMock invocation) throws Throwable{
			 String resource = invocation.getArgumentAt(0, String.class);
			 for(Resource res : mockForResRep){
				 if(res.getIdentifier() == resource)return res;
			 }
			 return null;
		 }
	  });
	  when(resRep.save(any(Resource.class))).then(new Answer<Resource>(){
		 public Resource answer(InvocationOnMock invocation) throws Throwable{
			 Resource res = invocation.getArgumentAt(0, Resource.class);
			 mockForResRep.add(res);
			 return res;
		 }
	  });
  }
  
  @BeforeClass
  public void prepareInquiryRepMock(){
	  List<Inquiry> mockForInqRep = new ArrayList<Inquiry>();
	  
	  when(inqRep.count()).then(new Answer<Long>(){
		  public Long answer(InvocationOnMock invocation) throws Throwable{
			  return (long)mockForInqRep.size();
		  }
	  });
	  when(inqRep.save(any(Inquiry.class))).then(new Answer<Inquiry>(){
		 public Inquiry answer(InvocationOnMock invocation) throws Throwable{
			 Inquiry inq = invocation.getArgumentAt(0, Inquiry.class);
			 mockForInqRep.add(inq);
			 return inq;
		 }
	  });
	  when(inqRep.findOne(anyInt())).then(new Answer<Inquiry>(){
		  public Inquiry answer(InvocationOnMock invocation) throws Throwable{
			  Integer num = invocation.getArgumentAt(0, Integer.class);
			  return mockForInqRep.get(num);
		  }
	  });
	  doAnswer(new Answer<Void>(){
		 public Void answer(InvocationOnMock invocation) throws Throwable{
			 mockForInqRep.clear();
			 return null;
		 }
	  }).when(inqRep).deleteAll();
	  
	  when(inqRep.getOne(anyInt())).then(new Answer<Inquiry>(){
		  public Inquiry answer(InvocationOnMock invocation) throws Throwable{
			  Integer num = invocation.getArgumentAt(0, Integer.class);
			  for(Inquiry inq : mockForInqRep){
				  if(inq.getInquiryId() != null && inq.getInquiryId() == num){
					  return inq;
				  }
			  }
			  return null;
		  }
	  });
	  doAnswer(new Answer<Void>(){
		  public Void answer(InvocationOnMock invocation) throws Throwable{
			  Integer num = invocation.getArgumentAt(0, Integer.class);
				  for(int i = 0; i< mockForInqRep.size(); i++){
					  Inquiry inq = mockForInqRep.get(i);
					  if(inq.getInquiryId() == num){
							  mockForInqRep.remove(inq);
						  }					
				  }
			  return null;
		  }
	  }).when(inqRep).delete(anyInt());
	  
	  when(inqRep.findByUserAndInquiryType(org.mockito.Mockito.any(User.class), any(InquiryType.class))).then(new Answer<List<Inquiry>>(){
		  public List<Inquiry> answer(InvocationOnMock invocation) throws Throwable{
			  User user = invocation.getArgumentAt(0, User.class);
			  InquiryType inqT = invocation.getArgumentAt(1, InquiryType.class);
			  List<Inquiry> result = new ArrayList<Inquiry>();
			  
			  for(Inquiry inq : mockForInqRep){
				  if(inq.getUser().equals(user) && inq.getInquiryType().equals(inqT)){
					  result.add(inq);
				  }
			  }
			  
			  return result;
		  }
	  });
	  
	  when(inqRep.findByRegistratorAndInquiryType(any(User.class), any(InquiryType.class))).then(new Answer<List<Inquiry>>(){
		  	public List<Inquiry> answer(InvocationOnMock invocation) throws Throwable{
		  		//User user = invocation.getArgumentAt(0, User.class);
		  		InquiryType inqT = invocation.getArgumentAt(1, InquiryType.class);
		  		List<Inquiry> result = new ArrayList<Inquiry>();
		  		
		  		for(Inquiry inq : mockForInqRep){
				  if(inq.getInquiryType().equals(inqT)){
					  result.add(inq);
				  }
		  		}
		  		
		  		return result;
		  	}
	  });
  }
  
  // DataProviders
  
  @DataProvider(name="ProviderForInquiries")
  public Object[][] formDataForInquiries(){
	  int desiredResources = 10;
	  Object[][] tmp = new Object[desiredResources][3];
	  
	  Role[] roles = new Role[]{new Role(RoleType.REGISTRATOR,"desc"),new Role(RoleType.USER,"desc")};
	  
	  for(int i = 0; i<roles.length; i++){
		  User usr = new User("user."+roles[i].toString().toLowerCase(),"password", roles[i], roles[i].toString(), "is", "User", "m@il.ua", UserStatus.ACTIVE.toString());
		  if(userRep.findUserByLogin("user."+roles[i].toString().toLowerCase()) == null){
			  userRep.save(usr);
		  }
	  }
	  
	  User registrator = userRep.findUserByLogin("user."+RoleType.REGISTRATOR.toString().toLowerCase()),
		   user = userRep.findUserByLogin("user."+RoleType.USER.toString().toLowerCase());
	  String resIdent = "land#",
			 resDef = "This is land";
	  Tome tome = new Tome();
	  ResourceType rt = new ResourceType();
	  
	  for(int i = 0; i< desiredResources; i++){
		  Resource rs = new Resource(rt,resIdent+i, resDef, registrator, date, ResourceStatus.ACTIVE.toString(), tome, "Reason");
		  resRep.save(rs);
	  
		  tmp[i] = new Object[]{registrator, user, rs};
	  }
		  
	  return tmp;
  }
  
  @DataProvider(name="ProviderForListUserNameMethod")
  public Object[][] formDataForListTypeTests(){
	  int desiredResources = 10;
	  Object[][] tmp = new Object[desiredResources][1];
	  
	  Role[] roles = new Role[]{new Role(RoleType.REGISTRATOR,"desc")};
	  TerritorialCommunity tc = new TerritorialCommunity();
	  
	  for(int i = 0; i<desiredResources; i++){
		  String userLog = "user#"+i+"."+roles[0].toString().toLowerCase();
		  Role role = roles[0];
		  
		  User usr = new User(userLog,"password", role, role.toString(), "is", "User", "m@il.ua", UserStatus.ACTIVE.toString());
		  if(userRep.findUserByLogin(userLog) == null){
			  usr.setTerritorialCommunity(tc);
			  userRep.save(usr);
		  }else{
			  usr = userRep.findUserByLogin(userLog);
			  usr.setTerritorialCommunity(tc);
		  }
		  tmp[i] = new Object[]{usr};
	  }
	  return tmp;
  }
  
  @DataProvider(name="ProviderForInquiryListTests")
  public Object[][] formDataForInquiryListTypeTests(){
	  int desiredResources = 10;
	  InquiryType[] inqTypes = new InquiryType[]{InquiryType.INPUT, InquiryType.OUTPUT};
	  Role[] roles = new Role[]{new Role(RoleType.REGISTRATOR,"desc"),new Role(RoleType.USER, "desc")};
	  Tome tome = new Tome();
	  ResourceType rt = new ResourceType();
	  String resIdent = "land#",
		     resDef = "This is land";
	  
	  Object[][] tmp = new Object[desiredResources*inqTypes.length][2];
	  
	  User registrator = new User("userRegistratorForInqList","password", roles[0], roles[0].toString(), "is", "User", "m@il.ua", UserStatus.ACTIVE.toString());
	  userRep.save(registrator);
	  
	  int j = 0;
	  for(int i = 0; i<desiredResources; i++){
		  String userN = "user#"+i+"."+roles[1];
		  User user = new User(userN,"password", roles[0], roles[0].toString(), "is", "User", "m@il.ua", UserStatus.ACTIVE.toString());
		  userRep.save(user);
		  
		  Resource rs = new Resource(rt,resIdent+"#00"+i, resDef, registrator, date, ResourceStatus.ACTIVE.toString(), tome, "Reason");
		  for(int n = 0; n<inqTypes.length; n++){
			  Inquiry inquiry = new Inquiry(inqTypes[n].toString(), date, user, registrator, rs);
			  inqRep.save(inquiry);
  
			  User insU = (i%2 == 0) ? user : registrator;
			  tmp[j++] = new Object[]{inqTypes[n], insU};
		  }
	  }
	  return tmp;
  }
  
  // Tests
  
  @Test(dataProvider="ProviderForInquiryListTests")
  public void testForListInquiryUser(InquiryType inqT,User user){
	  List<InquiryListDTO> syntheticList = new ArrayList<InquiryListDTO>();
	  user = userRep.findUserByLogin(user.getLogin());
	  InquiryListDTO inquiryListDTO;
	  
	  List<Inquiry> inquiries;
	  if(user.getRole().getType().equals(RoleType.USER)){
		  inquiries = inqRep.findByUserAndInquiryType(user, inqT);
	  }else{
		  inquiries = inqRep.findByRegistratorAndInquiryType(user, inqT);
	  }
	  
	  for(Inquiry inq : inquiries){
		  inquiryListDTO = new InquiryListDTO(inq.getInquiryId(), inq.getInquiryType().toString(), inq.getDate(), null, null, inq.getResource().getIdentifier(), inq.getResource().getStatus());
		  User userFrom = inq.getUser();
		  inquiryListDTO.setUserName(userFrom.getLastName()+ " " +userFrom.getFirstName()
			+ " " +userFrom.getMiddleName());
		  User registrator = inq.getRegistrator();
		  inquiryListDTO.setRegistratorName(registrator.getLastName()+ " " +registrator.getFirstName()
						+ " " +registrator.getMiddleName());
		  syntheticList.add(inquiryListDTO);
	  }
	  
	  List<InquiryListDTO> formedList = inqS.listInquiryUser(user.getLogin(), inqT);
	  Assert.assertEquals(syntheticList.size(), formedList.size());
	  
	  for(int i = 0; i<syntheticList.size(); i++){
		  InquiryListDTO comp0 = syntheticList.get(i),
				  		 comp1 = formedList.get(i);
		  Assert.assertEquals(comp0.getInquiryType(), comp1.getInquiryType());
		  Assert.assertEquals(comp0.getRegistratorName(), comp1.getRegistratorName());
		  Assert.assertEquals(comp0.getResourceIdentifier(), comp1.getResourceIdentifier());
		  Assert.assertEquals(comp0.getUserName(), comp1.getUserName());
		  Assert.assertEquals(comp0.getRegistratorName(), comp1.getRegistratorName());
		  Assert.assertEquals(comp0.getResourceStatus(), comp1.getResourceStatus());
	  }
  }
  
  @Test(dataProvider="ProviderForListUserNameMethod")
  public void testForListUserNameFormation(User user){
	  User usr = userRep.findUserByLogin(user.getLogin());
	  TerritorialCommunity tc = usr.getTerritorialCommunity();
	  List<User> registrators = userRep.getUsersByRoleAndCommunity(RoleType.REGISTRATOR, tc);
	  
	  List<UserNameDTO> syntheticList = new ArrayList<UserNameDTO>(registrators.size());
	  UserNameDTO userNameDTO;
	  for(User registrator: registrators){
		  userNameDTO = new UserNameDTO(registrator.getFirstName(), registrator.getLastName(), registrator.getMiddleName(), registrator.getLogin());
		  syntheticList.add(userNameDTO);
	  }
	  
	  List<UserNameDTO> formedList = inqS.listUserNameDTO(user.getLogin());
	  Assert.assertEquals(syntheticList.size(), formedList.size());
	  formedList.removeAll(syntheticList);
	  Assert.assertEquals(formedList.size(), 0);
  }
  
  @Test(dataProvider="ProviderForInquiries")
  public void testForOutputInquiryFormation(User reg, User user, Resource res){
	  Inquiry synthetic = new Inquiry(InquiryType.OUTPUT.toString(),date, user, reg, res),
			  formed = inqS.addOutputInquiry(res.getIdentifier(), reg.getLogin(), user.getLogin());
	  
	  Assert.assertEquals(synthetic.getInquiryType(), formed.getInquiryType());
	  Assert.assertEquals(synthetic.getResource(), formed.getResource());
	  Assert.assertEquals(synthetic.getRegistrator(), formed.getRegistrator());
	  Assert.assertEquals(synthetic.getUser(), formed.getUser());
  }
  
  @AfterMethod
  public void cleanInquiryRepository(){
	
  }
  
  @Test(dataProvider="ProviderForInquiries")
  public void testForInputInquiryFormation(User reg, User user, Resource res){
	   long countBefore = inqRep.count() + 1;
	   inqS.addInputInquiry(user.getLogin(), res, reg);
	   Assert.assertEquals(countBefore, inqRep.count());
  }
  
  Random rand = new Random();
  @Test(dataProvider="ProviderForInquiries")
  public void testForRemoveInquiry(User reg, User user, Resource res){
	  Inquiry synthetic = new Inquiry(InquiryType.OUTPUT.toString(),date, user, reg, res);
	  
	  synthetic.setInquiryId(rand.nextInt(100));
	  inqRep.save(synthetic);

	  synthetic = inqRep.getOne(synthetic.getInquiryId());

	  Assert.assertNotNull(synthetic);
	  
	  inqS.removeInquiry(synthetic.getInquiryId());
	  synthetic = inqRep.getOne(synthetic.getInquiryId());
	  Assert.assertNull(synthetic);
  }
}
