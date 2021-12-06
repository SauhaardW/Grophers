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


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
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
    public void testPresenterSubmitButtonCallToModel() {
        when(customerView.getEmail()).thenReturn("testing2@gmail.com");
        when(customerView.getPassword()).thenReturn("testing");

        when(ownerView.getEmail()).thenReturn("aryan@gmail.com");
        when(ownerView.getPassword()).thenReturn("testing");

        customerPresenter.submitButtonClicked(customerView.getEmail(), customerView.getPassword());
        verify(customerView).showProgressBar();
        verify(model).setEmail(customerView.getEmail());
        verify(model).setPassword(customerView.getPassword());
        verify(model).isLoginSuccessful(any(LoginCallBack.class));
    }
}