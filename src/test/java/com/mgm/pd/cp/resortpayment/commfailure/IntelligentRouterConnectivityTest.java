package com.mgm.pd.cp.resortpayment.commfailure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mgm.pd.cp.resortpayment.dto.authorize.CPPaymentAuthorizationRequest;
import com.mgm.pd.cp.resortpayment.dto.capture.CPPaymentCaptureRequest;
import com.mgm.pd.cp.resortpayment.dto.cardvoid.CPPaymentCardVoidRequest;
import com.mgm.pd.cp.resortpayment.dto.incrementalauth.CPPaymentIncrementalAuthRequest;
import com.mgm.pd.cp.resortpayment.dto.refund.CPPaymentRefundRequest;
import com.mgm.pd.cp.resortpayment.service.payment.FindPaymentService;
import com.mgm.pd.cp.resortpayment.service.router.RouterClient;
import com.mgm.pd.cp.resortpayment.util.TestHelperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mgm.pd.cp.payment.common.constant.ApplicationConstants.INTELLIGENT_ROUTER_CONNECTION_EXCEPTION_MESSAGE;

@SpringBootTest
@AutoConfigureMockMvc
public class IntelligentRouterConnectivityTest {
    public static final String INCREMENTAL_AUTH_PATH = "/services/v1/payments/incrementalauth";
    public static final String AUTHORIZE_PATH = "/services/v1/payments/authorize";
    public static final String CAPTURE_PATH = "/services/v1/payments/capture";
    public static final String VOID_PATH = "/services/v1/payments/void";
    public static final String REFUND_PATH = "/services/v1/payments/refund";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    RouterClient routerClient;
    @MockBean
    FindPaymentService findPaymentService;

    @Test
    void fail_when_intelligent_router_is_down_for_incremental_auth_request () throws Exception {
        Mockito.when(findPaymentService.getPaymentDetails(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(TestHelperUtil.getInitialPayment());
        CPPaymentIncrementalAuthRequest mockRequest = TestHelperUtil.getIncrementalAuthRequest();
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(INCREMENTAL_AUTH_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockRequest));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        //then
        Assertions.assertEquals(INTELLIGENT_ROUTER_CONNECTION_EXCEPTION_MESSAGE, JsonPath.read(responseJson, "$.title"));
        Assertions.assertEquals(Integer.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), JsonPath.read(responseJson, "$.status"));
    }

    @Test
    void fail_when_intelligent_router_is_down_for_auth_request () throws Exception {
        CPPaymentAuthorizationRequest mockRequest = TestHelperUtil.getAuthorizationRequest();
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(AUTHORIZE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockRequest));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        //then
        Assertions.assertEquals(INTELLIGENT_ROUTER_CONNECTION_EXCEPTION_MESSAGE, JsonPath.read(responseJson, "$.title"));
        Assertions.assertEquals(Integer.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), JsonPath.read(responseJson, "$.status"));
    }

    @Test
    void fail_when_intelligent_router_is_down_for_capture_request () throws Exception {
        Mockito.when(findPaymentService.getPaymentDetails(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(TestHelperUtil.getInitialPayment());
        CPPaymentCaptureRequest mockRequest = TestHelperUtil.getCapturePaymentRequest();
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(CAPTURE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockRequest));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        //then
        Assertions.assertEquals(INTELLIGENT_ROUTER_CONNECTION_EXCEPTION_MESSAGE, JsonPath.read(responseJson, "$.title"));
        Assertions.assertEquals(Integer.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), JsonPath.read(responseJson, "$.status"));
    }

    @Test
    void fail_when_intelligent_router_is_down_for_card_void_request () throws Exception {
        Mockito.when(findPaymentService.getPaymentDetails(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(TestHelperUtil.getInitialPayment());
        CPPaymentCardVoidRequest mockRequest = TestHelperUtil.getVoidPaymentRequest();
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(VOID_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockRequest));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        //then
        Assertions.assertEquals(INTELLIGENT_ROUTER_CONNECTION_EXCEPTION_MESSAGE, JsonPath.read(responseJson, "$.title"));
        Assertions.assertEquals(Integer.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), JsonPath.read(responseJson, "$.status"));
    }

    @Test
    void fail_when_intelligent_router_is_down_for_refund_request () throws Exception {
        Mockito.when(findPaymentService.getPaymentDetails(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(TestHelperUtil.getInitialPayment());
        CPPaymentRefundRequest mockRequest = TestHelperUtil.getRefundPaymentRequest();
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(REFUND_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockRequest));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        //then
        Assertions.assertEquals(INTELLIGENT_ROUTER_CONNECTION_EXCEPTION_MESSAGE, JsonPath.read(responseJson, "$.title"));
        Assertions.assertEquals(Integer.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), JsonPath.read(responseJson, "$.status"));
    }
}
