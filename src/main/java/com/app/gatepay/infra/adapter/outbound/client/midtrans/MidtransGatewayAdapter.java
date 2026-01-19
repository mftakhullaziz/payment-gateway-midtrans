package com.app.gatepay.infra.adapter.outbound.client.midtrans;

import com.app.gatepay.domain.payment.Payment;
import com.app.gatepay.domain.payment.PaymentGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @deprecated Use {@link com.app.gatepay.infra.adapter.outbound.provider.midtrans.MidtransGatewayAdapter}.
 *
 * Kept for backward compatibility while packages are being reorganized.
 */
@Deprecated
@Component
@Primary
@RequiredArgsConstructor
public class MidtransGatewayAdapter implements PaymentGatewayPort {

    private final com.app.gatepay.infra.adapter.outbound.provider.midtrans.MidtransGatewayAdapter delegate;

    @Override
    public Payment executeTransferVA(Payment payment) {
        return delegate.executeTransferVA(payment);
    }

    @Override
    public Payment executeTransferEWallet(Payment payment) {
        return delegate.executeTransferEWallet(payment);
    }
}
