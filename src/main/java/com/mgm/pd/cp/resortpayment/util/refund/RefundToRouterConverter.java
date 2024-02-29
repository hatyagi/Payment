package com.mgm.pd.cp.resortpayment.util.refund;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgm.pd.cp.payment.common.constant.BooleanValue;
import com.mgm.pd.cp.payment.common.dto.opera.Card;
import com.mgm.pd.cp.payment.common.dto.opera.TransactionAmount;
import com.mgm.pd.cp.resortpayment.dto.CurrencyConversion;
import com.mgm.pd.cp.resortpayment.dto.Customer;
import com.mgm.pd.cp.resortpayment.dto.Merchant;
import com.mgm.pd.cp.resortpayment.dto.TransactionDetails;
import com.mgm.pd.cp.resortpayment.dto.refund.CPPaymentRefundRequest;
import com.mgm.pd.cp.resortpayment.dto.refund.RefundRouterRequestJson;
import com.mgm.pd.cp.resortpayment.dto.router.RouterRequest;
import com.mgm.pd.cp.resortpayment.util.common.PaymentProcessingServiceHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.mgm.pd.cp.payment.common.constant.ApplicationConstants.*;

@Component
@AllArgsConstructor
public class RefundToRouterConverter {
    ObjectMapper mapper;
    private PaymentProcessingServiceHelper helper;

    public RouterRequest convert(CPPaymentRefundRequest cpPaymentRefundRequest) {
        TransactionDetails transactionDetails = cpPaymentRefundRequest.getTransactionDetails();
        TransactionAmount transactionAmount = transactionDetails.getTransactionAmount();
        CurrencyConversion currencyConversion = transactionDetails.getCurrencyConversion();
        Card card = transactionDetails.getCard();
        Merchant merchant = transactionDetails.getMerchant();
        Customer customer = transactionDetails.getCustomer();
        String roomRate = helper.getValueFromSaleDetails(cpPaymentRefundRequest, ROOM_RATE);
        String originalTransactionIdentifier = cpPaymentRefundRequest.getOriginalTransactionIdentifier();
        Optional<RefundRouterRequestJson> requestJson= Optional.ofNullable(RefundRouterRequestJson.builder()
                .amount(transactionAmount.getDetailedAmount().getAmount())
                .totalAuthAmount(transactionAmount.getCumulativeAmount())
                .binCurrencyCode(currencyConversion.getBinCurrencyCode())
                .binRate(currencyConversion.getBinCurrencyCode())
                .cardType(card.getCardType())
                .cardPresent(BooleanValue.getEnumByString(transactionDetails.getIsCardPresent().toString()))
                .currencyIndicator(transactionAmount.getCurrencyIndicator())
                .arrivalDate(helper.getValueFromSaleDetails(cpPaymentRefundRequest, CHECK_IN_DATE))
                .cardExpirationDate(card.getExpiryDate())
                .dccAmount(Double.valueOf(currencyConversion.getAmount()))
                .dccControlNumber(Double.valueOf(currencyConversion.getConversionIdentifier()))
                .chainCode(helper.getValueFromSaleDetails(cpPaymentRefundRequest, PROPERTY_CHAIN_IDENTIFIER))
                .guestName(customer.getFullName())
                //.installments(transactionDetails.getSaleItem().getSaleDetails().getEstimatedDuration())
                .merchantID(merchant.getMerchantIdentifier())
                .propertyCode(helper.getValueFromSaleDetails(cpPaymentRefundRequest, PROPERTY_IDENTIFIER))
                .version(merchant.getVersion())
                .dCCFlag(currencyConversion.getConversionFlag())
                .corelationId(card.getCardIssuerName())
                .roomNum(helper.getValueFromSaleDetails(cpPaymentRefundRequest, ROOM_NUMBER))
                .roomRate(!roomRate.equals("null") ? Double.valueOf(roomRate) : null)
                .startDate(card.getStartDate())
                .cardNumber(card.getMaskedCardNumber())
                .trackIndicator(card.getTrack1())
                .usageType(card.getTokenValue())
                .workstation(merchant.getTerminalIdentifier())
                .departureDate(helper.getValueFromSaleDetails(cpPaymentRefundRequest, CHECK_OUT_DATE))
                .resvNameID(transactionDetails.getSaleItem().getSaleReferenceIdentifier())
                .vendorTranID(cpPaymentRefundRequest.getGatewayInfo().getGatewayTransactionIdentifier())
                .sequenceNumber(cpPaymentRefundRequest.getTransactionIdentifier())
                .originalAuthSequence(Objects.nonNull(originalTransactionIdentifier) ? Long.valueOf(originalTransactionIdentifier) : null)
                .transDate(cpPaymentRefundRequest.getTransactionDateTime())
                .messageType(cpPaymentRefundRequest.getTransactionType())
                .clientID(card.getCardIssuerName())
                .corelationId(card.getCardIssuerIdentification())
                .approvalCode(card.getTokenType())
                .clerkId(cpPaymentRefundRequest.getClerkId())
                .dateTime(cpPaymentRefundRequest.getDateTime())
                .billingZIP(customer.getBillingAddress().getPostCode())
                .build());
        String requestJsonAsString;
        try {
            requestJsonAsString = mapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return RouterRequest.builder()
                .operation(REFUND_OPERATION)
                .gatewayId(SHIFT4_GATEWAY_ID)
                .requestJson(requestJsonAsString).build();
    }
}