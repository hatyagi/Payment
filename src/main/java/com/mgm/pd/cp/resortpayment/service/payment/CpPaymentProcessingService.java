package com.mgm.pd.cp.resortpayment.service.payment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgm.pd.cp.payment.common.dto.GenericResponse;
import com.mgm.pd.cp.resortpayment.dto.authorize.CPPaymentAuthorizationRequest;
import com.mgm.pd.cp.resortpayment.dto.capture.CPPaymentCaptureRequest;
import com.mgm.pd.cp.resortpayment.dto.cardvoid.CPPaymentCardVoidRequest;
import com.mgm.pd.cp.resortpayment.dto.incrementalauth.CPPaymentIncrementalRequest;
import org.springframework.http.ResponseEntity;

public interface CpPaymentProcessingService {
    ResponseEntity<GenericResponse> processIncrementalAuthorizationRequest(CPPaymentIncrementalRequest incrementalRequest) throws JsonProcessingException;
    ResponseEntity<GenericResponse> processAuthorizeRequest(CPPaymentAuthorizationRequest cpPaymentIncrementalRequest) throws JsonProcessingException;
    ResponseEntity<GenericResponse> processCaptureRequest(CPPaymentCaptureRequest captureRequest) throws JsonProcessingException;
    ResponseEntity<GenericResponse> processCardVoidRequest(CPPaymentCardVoidRequest voidRequest) throws JsonProcessingException;
}
