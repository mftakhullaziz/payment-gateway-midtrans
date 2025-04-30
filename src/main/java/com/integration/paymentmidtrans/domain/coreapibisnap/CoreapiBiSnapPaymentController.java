package com.integration.paymentmidtrans.domain.coreapibisnap;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Tag(name = "Payment - Core API BI Snap")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/coreapibisnap/payment")
public class CoreapiBiSnapPaymentController {
}
