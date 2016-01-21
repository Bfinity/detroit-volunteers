package com.detroitlabs.detroitvolunteers.firebase;

import android.content.Context;

import com.detroitlabs.detroitvolunteers.models.User;
import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserAuthenticationTest {


    private VolunteerMatchFirebase mockVMFirebase;

    @Mock
    Firebase mockFirebaseRef;

    @Mock
    User mockUser;

    @Mock
    UserAuthCallBack mockAuthCallBack;

    @Mock
    Context mockContext;


    private UserAuthentication subject;

    private final String TEST_USER_EMAIL = "someone@email.com";
    private final String TEST_USER_PASSWORD = "password";


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        Firebase.setAndroidContext(mockContext);
        mockVMFirebase = mock(VolunteerMatchFirebase.class);
        subject = new UserAuthentication(mockVMFirebase);
        setMockUser();
        setMockFirebaseRef();
    }

    @Test
    public void createNewUser_should_create_user_account(){
 //       subject.createNewUser(mockUser);
  //      verify(mockAuthCallBack).onSuccess();
    }

    private void setMockUser(){
        when(mockUser.getUserEmail()).thenReturn(TEST_USER_EMAIL);
   //     when(mockUser.getUserPassword()).thenReturn(TEST_USER_PASSWORD);
    }

    private void setMockFirebaseRef(){
        when(mockVMFirebase.getFirebaseBaseRef()).thenReturn(mockFirebaseRef);
    }
}