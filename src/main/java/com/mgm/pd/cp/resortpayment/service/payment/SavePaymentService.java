package com.mgm.pd.cp.resortpayment.service.payment;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mgm.pd.cp.payment.common.dto.*;
import com.mgm.pd.cp.payment.common.model.Payment;
import com.mgm.pd.cp.resortpayment.dto.authorize.AuthorizationRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.capture.CaptureRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.cardvoid.CardVoidRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.incrementalauth.IncrementalAuthorizationRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.refund.RefundRouterResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Methods which can be utilised to save different type of requests in Payment DB
 */
public interface SavePaymentService {
    @Transactional
    Payment saveAuthorizationPayment(CPPaymentAuthorizationRequest authRequest, AuthorizationRouterResponse irResponse) throws InvalidFormatException;
    @Transactional
    Payment saveIncrementalAuthorizationPayment(CPPaymentIncrementalAuthRequest incrementalRequest, IncrementalAuthorizationRouterResponse irResponse, Payment initialPayment) throws InvalidFormatException;
    @Transactional
    Payment saveCaptureAuthPayment(CPPaymentCaptureRequest captureRequest, CaptureRouterResponse crResponse, Payment initialPayment) throws InvalidFormatException;
    @Transactional
    Payment saveCardVoidAuthPayment(CPPaymentCardVoidRequest voidRequest, CardVoidRouterResponse vrResponse, Payment initialPayment) throws InvalidFormatException;
    @Transactional
    Payment saveRefundPayment(CPPaymentRefundRequest request, RefundRouterResponse rrResponse, Payment initialPayment) throws InvalidFormatException;
}
