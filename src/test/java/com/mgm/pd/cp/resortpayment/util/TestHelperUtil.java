package com.mgm.pd.cp.resortpayment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mgm.pd.cp.resortpayment.dto.capture.CPPaymentCaptureRequest;
import com.mgm.pd.cp.resortpayment.dto.cardvoid.CPPaymentCardVoidRequest;
import com.mgm.pd.cp.resortpayment.dto.incrementalauth.CPPaymentIncrementalRequest;
import com.mgm.pd.cp.resortpayment.dto.incrementalauth.IncrementalAuthorizationRouterResponse;
import com.mgm.pd.cp.resortpayment.dto.router.RouterResponseJson;
import com.mgm.pd.cp.resortpayment.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Optional;

public class TestHelperUtil {
	ObjectMapper mapper;

	@Value("classpath:initialAuthRequest.json")
	Resource testFile;

	public static CPPaymentIncrementalRequest getIncrementalAuthRequest() throws IOException {
		return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(new ClassPathResource("UC2/incrementalAuthRequest.json").getFile(),
				CPPaymentIncrementalRequest.class);
	}

	public static RouterResponseJson getIncrementalRouterResponseJson() {
		String mockResponse = "{\"dateTime\":\"2021-04-15T09:18:23.000-07:00\",\"totalAuthAmount\":898.07,\"cardType\":\"VS\",\"returnCode\":\"A\",\"sequenceNumber\":\"1234\",\"transDate\":\"2021041509:18:23\",\"vendorTranId\":\"0000192029\",\"approvalCode\":\"OK196Z\"}";
        return RouterResponseJson.builder().responseJson(mockResponse).build();
	}

	public static CPPaymentCaptureRequest getCapturePaymentRequest() throws IOException {
		return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(new ClassPathResource("UC4/capturePaymentRequest.json").getFile(),
				CPPaymentCaptureRequest.class);
	}

	public static RouterResponseJson getCaptureRouterResponseJson() {
		String mockResponse = "{\"dateTime\":\"2021-04-15T09:18:23.000-07:00\",\"totalAuthAmount\":898.07,\"cardType\":\"VS\",\"returnCode\":\"A\",\"sequenceNumber\":\"1234\",\"transDate\":\"2021-04-15T00:00:00.000-07:00\",\"vendorTranId\":\"0000192029\",\"approvalCode\":\"OK684Z\"}";
		return RouterResponseJson.builder().responseJson(mockResponse).build();
    }

	public static CPPaymentCardVoidRequest getVoidPaymentRequest() throws IOException {
		return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(new ClassPathResource("UC22/voidPaymentRequest.json").getFile(),
				CPPaymentCardVoidRequest.class);
	}

	public static RouterResponseJson getVoidRouterResponseJson() {
		String mockResponse = "{\"dateTime\":\"2021-04-15T09:18:23.000-07:00\",\"totalAuthAmount\":898.07,\"cardType\":\"VS\",\"returnCode\":\"A\",\"vendorTranId\":\"0000192029\",\"approvalCode\":\"OK196Z\"}";
		return RouterResponseJson.builder().responseJson(mockResponse).build();
	}

	public static IncrementalAuthorizationRouterResponse getIncrementalRouterResponse() throws JsonProcessingException {
		return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(getIncrementalRouterResponseJson().getResponseJson(), IncrementalAuthorizationRouterResponse.class);
	}

	public static String getOperaResponse() {
		return "{\"approvalCode\":\"OK196Z\",\"responseCode\":\"A\",\"gatewayInfo\":{},\"transactionDateTime\":\"2021041509:18:23\",\"transactionAmount\":{\"balanceAmount\":34.23,\"requestedAmount\":2000.0,\"cumulativeAmount\":898.07,\"currencyIndicator\":\"USD\",\"detailedAmount\":{}},\"card\":{\"cardType\":\"VS\",\"sequenceNumber\":\"1234\",\"isTokenized\":false},\"printDetails\":[{}]}";
	}

	public static Optional<Payment> getInitialPayment() throws IOException {
		Payment value = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(new ClassPathResource("Payments/initialPayment.json").getFile(), Payment.class);
		return Optional.of(value);
    }

	public static String getOperaResponseForCaptureOperation() {
		return "{\"approvalCode\":\"OK684Z\",\"responseCode\":\"A\",\"gatewayInfo\":{},\"transactionDateTime\":\"2021041509:18:23\",\"transactionAmount\":{\"authorizedAmount\":898.07,\"cumulativeAmount\":898.07,\"detailedAmount\":{}},\"card\":{\"cardType\":\"VS\",\"sequenceNumber\":\"1234\",\"isTokenized\":false},\"printDetails\":[{}]}";
	}
}