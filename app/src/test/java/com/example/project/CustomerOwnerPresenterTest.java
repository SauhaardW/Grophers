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

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerPresenter = new CustomerPresenter(customerView, model);
        ownerPresenter = new OwnerPresenter(ownerView, model);
    }

    /*** All CustomerPresenter Test Cases ***/

    // Constructor test(s)
    @Test
    public void testCustomerPresenterConstructor() {
        assertEquals(customerPresenter.view, customerView);
        assertEquals(customerPresenter.model, model);
    }

    // backButtonClicked method test(s)
    @Test
    public void testCustomerPresenterBackButtonClicked() {
        customerPresenter.backButtonClicked();
        verify((LoginCustomerActivity) customerView).finish();
    }

    // registerButtonClicked method test(s)
    @Test
    public void testCustomerPresenterRegisterButtonClicked() {
        customerPresenter.registerButtonClicked();
        verify((LoginCustomerActivity) customerView).startActivity(any(Intent.class));
        /*

        //Intent intent = mock(Intent.class);
        //intent.setClass((LoginCustomerActivity) customerView, RegisterCustomerActivity.class);
        //verify((LoginCustomerActivity) customerView).startActivity(any(Intent.class));

        //Context mockContext = mock(Context.class);
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify((LoginCustomerActivity) customerView).startActivity(intentCaptor.capture());
        Intent intent = intentCaptor.getValue();
        assertNotNull(intent);

        // intent.setClass actually creates a Component object in the intent, so here I'm
        // making sure that it's the same class
        assertNotNull(intent.getComponent());
        ComponentName component = intent.getComponent();
        //System.out.println();
        assertEquals(component.getClassName(), RegisterCustomerActivity.class.getSimpleName());
        //assertThat(component.className).isEqualTo("com.mbcdev.folkets.WordActivity");

         */
    }

    // submitButtonClicked method test(s)
    @Test
    public void testCustomerPresenterSubmitButtonClickedEmptyEmailAndPassword() {
        String email = "";
        String password = "";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(customerView).setEmailEmptyError();
        verify(customerView).hideProgressBar();
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedEmptyEmail() {
        String email = "";
        String password = "testing";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(customerView).setEmailEmptyError();
        verify(customerView).hideProgressBar();
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedEmptyPassword() {
        String email = "testing2@gmail.com";
        String password = "";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(customerView).setPasswordEmptyError();
        verify(customerView).hideProgressBar();
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedCallToModelValid() {
        String email = "testing2@gmail.com";
        String password = "testing";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(model).setEmail(email);
        verify(model).setPassword(password);
        verify(model).isLoginSuccessful(any(LoginCallBack.class));

        //verify(model).
    }

    @Test
    public void testCustomerPresenterSubmitButtonClickedCallToModelFailed() {
        String email = "testing2@gmail.com";
        String password = "wrongPassword";

        customerPresenter.submitButtonClicked(email, password);
        verify(customerView).showProgressBar();
        verify(model).setEmail(email);
        verify(model).setPassword(password);
        verify(model).isLoginSuccessful(any(LoginCallBack.class));
    }

    /*** All OwnerPresenter Test Cases ***/

    // Constructor test(s)
    @Test
    public void testOwnerPresenterConstructor() {
        assertEquals(ownerPresenter.view, ownerView);
        assertEquals(ownerPresenter.model, model);
    }

    // backButtonClicked method test(s)
    @Test
    public void testOwnerPresenterBackButtonClicked() {
        ownerPresenter.backButtonClicked();
        verify((LoginOwnerActivity) ownerView).finish();
    }

    // registerButtonClicked method test(s)
    @Test
    public void testOwnerPresenterRegisterButtonClicked() {
        ownerPresenter.registerButtonClicked();
        verify((LoginOwnerActivity) ownerView).startActivity(any(Intent.class));
        /*

        //Intent intent = mock(Intent.class);
        //intent.setClass((LoginCustomerActivity) customerView, RegisterCustomerActivity.class);
        //verify((LoginCustomerActivity) customerView).startActivity(any(Intent.class));

        //Context mockContext = mock(Context.class);
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify((LoginCustomerActivity) customerView).startActivity(intentCaptor.capture());
        Intent intent = intentCaptor.getValue();
        assertNotNull(intent);

        // intent.setClass actually creates a Component object in the intent, so here I'm
        // making sure that it's the same class
        assertNotNull(intent.getComponent());
        ComponentName component = intent.getComponent();
        //System.out.println();
        assertEquals(component.getClassName(), RegisterCustomerActivity.class.getSimpleName());
        //assertThat(component.className).isEqualTo("com.mbcdev.folkets.WordActivity");

         */
    }

    // submitButtonClicked method test(s)
    @Test
    public void testOwnerPresenterSubmitButtonClickedEmptyEmailAndPassword() {
        String email = "";
        String password = "";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(ownerView).setEmailEmptyError();
        verify(ownerView).hideProgressBar();
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedEmptyEmail() {
        String email = "";
        String password = "testing";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(ownerView).setEmailEmptyError();
        verify(ownerView).hideProgressBar();
    }

    @Test
    public void testOwnerPresenterSubmitButtonClickedEmptyPassword() {
        String email = "testing2@gmail.com";
        String password = "";

        ownerPresenter.submitButtonClicked(email, password);
        verify(ownerView).showProgressBar();
        verify(ownerView).setPasswordEmptyError();
        verify(ownerView).hideProgressBar();
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