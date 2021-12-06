package com.example.project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import android.util.Log;

@RunWith(MockitoJUnitRunner.class)
public class CustomerOwnerPresenterTest {
    private CustomerPresenter customerPresenter;
    private OwnerPresenter ownerPresenter;

    @Mock
    LoginCustomerActivity customerView;

    @Mock
    LoginOwnerActivity ownerView;

    @Mock
    LoginModel model;

    @Captor
    private ArgumentCaptor<LoginCallBack> callbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerPresenter = new CustomerPresenter(customerView, model);
        ownerPresenter = new OwnerPresenter(ownerView, model);
    }

    @Test
    public void testCustomerPresenterSubmitButtonCallToModel() {
        String email = "testing2@gmail.com";
        String password = "testing";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(model).setEmail(email);
        verify(model).setPassword(password);
        verify(model).isLoginSuccessful(any(LoginCallBack.class));
    }

    @Test
    public void testOwnerPresenterSubmitButtonCallToModel() {
        String email = "aryan@gmail.com";
        String password = "testing";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(model).setEmail(email);
        verify(model).setPassword(password);
        verify(model).isLoginSuccessful(any(LoginCallBack.class));
    }
}