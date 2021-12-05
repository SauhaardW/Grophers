package com.example.project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;


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
    private ArgumentCaptor<LoginCallBack<Boolean>> callbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerPresenter = new CustomerPresenter(customerView, model);
        ownerPresenter = new OwnerPresenter(ownerView, model);
    }

    @Test
    public void testPresenter1() {
        when(customerView.getEmail()).thenReturn("testing2@gmail.com");
        when(customerView.getPassword()).thenReturn("testing");

        when(ownerView.getEmail()).thenReturn("aryan@gmail.com");
        when(ownerView.getPassword()).thenReturn("testing");

        customerPresenter.submitButtonClicked(customerView.getEmail(), customerView.getPassword());

        verify(model).isLoginSuccessful(callbackCaptor.capture());

        doAnswer(returnsFirstArg()).when(callbackCaptor.getValue()).loginSuccess(true);

        verify(customerView).loginSuccessfulToast();
    }
}