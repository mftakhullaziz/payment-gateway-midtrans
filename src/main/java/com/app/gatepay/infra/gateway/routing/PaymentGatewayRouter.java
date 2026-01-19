package com.app.gatepay.infra.gateway.routing;

import com.app.gatepay.domain.payment.PaymentGatewayPort;
import com.app.gatepay.infra.config.GatepayPaymentProperties;
import com.app.gatepay.shared.enums.PaymentProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Router/registry for selecting a payment gateway adapter.
 *
 * Phase 1: only MIDTRANS is wired. This class is the single abstraction point
 * that will later:
 * - load active credentials (per merchant/provider)
 * - route by method/channel
 * - verify callback signatures per provider
 */
@Component
public class PaymentGatewayRouter {

    private final PaymentGatewayPort midtransGateway;
    private final GatepayPaymentProperties paymentProperties;

    public PaymentGatewayRouter(
        @Qualifier("midtransPaymentGateway") PaymentGatewayPort midtransGateway,
        GatepayPaymentProperties paymentProperties
    ) {
        this.midtransGateway = midtransGateway;
        this.paymentProperties = paymentProperties;
    }

    public PaymentGatewayPort gateway(PaymentProvider provider) {
        PaymentProvider resolved = provider != null ? provider : paymentProperties.getDefaultProvider();

        return switch (resolved) {
            case MIDTRANS -> midtransGateway;
            // Phase 1: not yet implemented
            case DOKU, XENDIT, FASPAY, OY -> throw new UnsupportedOperationException(
                "Provider not implemented yet: " + resolved
            );
        };
    }

    public PaymentGatewayPort defaultGateway() {
        return gateway(null);
    }
}
