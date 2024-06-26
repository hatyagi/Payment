package com.mgm.pd.cp.resortpayment.service.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgm.pd.cp.payment.common.dto.*;
import com.mgm.pd.cp.payment.common.model.Payment;
import com.mgm.pd.cp.resortpayment.dto.authorize.AuthorizationRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.capture.CaptureRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.cardvoid.CardVoidRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.incrementalauth.IncrementalAuthorizationRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.refund.RefundRouterResponse;
import org.springframework.http.HttpHeaders;

/**
 *  This interface provides methods/contract about the requests which can be sent to Intelligent Router for Processing
 *  This class has only one Implementation Class(RouterHelperServiceImpl.java)
 *  It is responsible to define the request and response type for each of the operation.
 */
public interface RouterHelper {
    /**
     * This method takes the action for Authorization operation only.
     * It shouldn't be called without passing the Authorization Request.
     */
    AuthorizationRouterResponse sendAuthorizeRequestToRouter(CPPaymentAuthorizationRequest authorizationRequest, HttpHeaders headers) throws JsonProcessingException ;

    /**
     * This method takes the action for IncrementalAuthorization operation only.
     * It shouldn't be called without passing the IncrementalAuthorization Request.
     */
    IncrementalAuthorizationRouterResponse sendIncrementalAuthorizationRequestToRouter(CPPaymentIncrementalAuthRequest incrementalRequest, Payment initialPayment, HttpHeaders headers) throws JsonProcessingException;

    /**
     * This method takes the action for Capture operation only.
     * It shouldn't be called without passing the Capture Request.
     */
    CaptureRouterResponse sendCaptureRequestToRouter(CPPaymentCaptureRequest captureRequest, Payment initialPayment, HttpHeaders headers) throws JsonProcessingException;

    /**
     * This method takes the action for Card Void operation only.
     * It shouldn't be called without passing the Card Void Request.
     */
    CardVoidRouterResponse sendCardVoidRequestToRouter(CPPaymentCardVoidRequest cvRequest, Payment initialPayment, HttpHeaders headers) throws JsonProcessingException;

    /**
     * This method takes the action for Refund operation only.
     * It shouldn't be called without passing the Refund Request.
     */
    RefundRouterResponse sendRefundRequestToRouter(CPPaymentRefundRequest refundRequest, HttpHeaders headers) throws JsonProcessingException;
}
