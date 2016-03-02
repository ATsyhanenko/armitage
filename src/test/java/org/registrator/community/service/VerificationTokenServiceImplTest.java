package org.registrator.community.service.impl;

import static org.mockito.Matchers.any;
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
import org.registrator.community.dao.VerificationTokenRepository;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.VerificationTokenService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class VerificationTokenServiceImplTest {
	private VerificationTokenRepository tokenRep = mock(VerificationTokenRepository.class);
	private VerificationTokenService vtServ = new VerificationTokenServiceImpl();
	private final static int TESTROWS = 10;
	int plusTime = VerificationTokenServiceImpl.PASSWORD_TOKEN_EXPIRY_TIME;
	
	@BeforeClass
	public void bigBadMethod() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class<?> cls = vtServ.getClass();
		Field field = cls.getDeclaredField("verificationTokenRepository");
		field.setAccessible(true);
		field.set(vtServ, tokenRep);
	}
	@BeforeClass
	public void prepareMockVerTokenRep(){
		List<VerificationToken> mockForTokenRep = new ArrayList<VerificationToken>();
		/*
		 * new VerificationToken(token,userEmail,nowTime,TokenType.RECOVER_PASSWORD);
		 * ------------------------------------------------------------------------------
		 * VerificationToken findTokenByEmail(String) +
		 * void delete(VerificationToken)+
		 * VerificationToken findVerificationTokenByTokenAndTokenType(String, TokenType)+
		 * VerificationToken findVerificationTokenByToken(String) + 
		 * save(VerificationToken) +
		 * long count() +
		 */
		when(tokenRep.count()).then(new Answer<Long>(){
			public Long answer(InvocationOnMock invo){
				return (long)mockForTokenRep.size();
			}
		});
		
		when(tokenRep.save(any(VerificationToken.class))).then(new Answer<VerificationToken>(){
			public VerificationToken answer(InvocationOnMock invo){
				VerificationToken token = invo.getArgumentAt(0, VerificationToken.class);
				mockForTokenRep.add(token);
				return token;
			}
		});
		
		when(tokenRep.findVerificationTokenByToken(anyString())).then(new Answer<VerificationToken>(){
			public VerificationToken answer(InvocationOnMock invo){
				String tokenName = invo.getArgumentAt(0, String.class);
				for(VerificationToken token : mockForTokenRep){
					if(token.getToken() == tokenName){
						return token;
					}
				}
				return null;
			}
		});
		
		when(tokenRep.findVerificationTokenByTokenAndTokenType(anyString(), any(TokenType.class))).then(new Answer<VerificationToken>(){
			public VerificationToken answer(InvocationOnMock invo){
				String tokenName = invo.getArgumentAt(0, String.class);
				TokenType type = invo.getArgumentAt(1, TokenType.class);
				
				for(VerificationToken token : mockForTokenRep){
					if(token.getTokenType() == type && token.getToken() == tokenName){
						return token;
					}
				}
				return null;
			}
		});
		
		doAnswer(new Answer<Void>(){
			public Void answer(InvocationOnMock invo){
				VerificationToken token = invo.getArgumentAt(0, VerificationToken.class);
				mockForTokenRep.remove(token);
				return null;
			}
		}).when(tokenRep).delete(any(VerificationToken.class));
		
		when(tokenRep.findTokenByEmail(anyString())).then(new Answer<VerificationToken>(){
			public VerificationToken answer(InvocationOnMock invo){
				String email = invo.getArgumentAt(0, String.class);
				
				for(VerificationToken token : mockForTokenRep){
					if(token.getUserEmail().equals(email)){
						return token;
					}
				}
				return null;
			}
		});
	}

	// DataProviders
	
	@DataProvider(name="formDataForTokenCreation")
	public Object[][] formDataForTokens(){
		Object[][] tmp = new Object[TESTROWS][];
		Date now = new Date();
		Random rand = new Random();
		
		String fakeRandomUUID = vtServ.createHashForPasswordToken()+rand.nextInt(1000);
		String email = "someMailAddress#%03d@mail.me";
		
		for(int i = 0; i<tmp.length; i++){
			tmp[i] = new Object[]{fakeRandomUUID, String.format(email, rand.nextInt(100)), now};
		}
		
		return tmp;
	}
	
	// Tests
	
	@Test
	public void createHashForPasswordToken() {
	  for(int i = 0; i<TESTROWS; i++){
		  String generated = vtServ.createHashForPasswordToken();
		  Assert.assertNotNull(generated);
	  }
	}
	
	@Test(dataProvider="formDataForTokenCreation")
	public void saveAndDeletePasswordVerificationToken(String uuid, String email, Date date) {
		Date forSynt = new Date(date.getTime() + plusTime);
		
		VerificationToken syntetical = new VerificationToken(uuid, email, forSynt, TokenType.RECOVER_PASSWORD),
				          formed = vtServ.savePasswordVerificationToken(email, date);
		
		Assert.assertEquals(syntetical.getUserEmail(), formed.getUserEmail());
		Assert.assertEquals(syntetical.getExpiryDate(), formed.getExpiryDate());
		
		VerificationToken extraCheck = tokenRep.findTokenByEmail(formed.getUserEmail());
		Assert.assertNotNull(extraCheck);
		
		boolean isDeleted = vtServ.deletePasswordVerificationTokenByEmail(extraCheck.getUserEmail());
		Assert.assertEquals(isDeleted, true);
		extraCheck = tokenRep.findTokenByEmail(formed.getUserEmail());
		Assert.assertNull(extraCheck);
	}
	
	@Test(dataProvider="formDataForTokenCreation")
	public void deleteVerificationToken(String uuid, String email, Date date) {
		date.setTime(date.getTime() + plusTime);
		VerificationToken syntetical = new VerificationToken(uuid, email, date, TokenType.RECOVER_PASSWORD);
		tokenRep.save(syntetical);
		vtServ.deleteVerificationToken(syntetical);
		syntetical = tokenRep.findVerificationTokenByToken(syntetical.getToken());
		Assert.assertNull(syntetical);
	}
	
	@Test(dataProvider="formDataForTokenCreation")
	public void findVerificationTokenByTokenAndTokenType(String uuid, String email, Date date) {
		date.setTime(date.getTime() + plusTime);
		VerificationToken syntetical = new VerificationToken(uuid, email, date, TokenType.RECOVER_PASSWORD);
		tokenRep.save(syntetical);
		syntetical = vtServ.findVerificationTokenByTokenAndTokenType(syntetical.getToken(), syntetical.getTokenType());
		Assert.assertNotNull(syntetical);
	}
	
	@Test(dataProvider="formDataForTokenCreation")
	public void isExistValidVerificationToken(String uuid, String email, Date date) {
		date.setTime(date.getTime() + plusTime);
		VerificationToken syntetical = new VerificationToken(uuid, email, date, TokenType.RECOVER_PASSWORD);
		tokenRep.save(syntetical);
		boolean manualCheck = syntetical.getExpiryDate().getTime() > System.currentTimeMillis(),
				formedCheck = vtServ.isExistValidVerificationToken(syntetical.getToken());
		Assert.assertEquals(manualCheck, formedCheck);
	}
	

}
