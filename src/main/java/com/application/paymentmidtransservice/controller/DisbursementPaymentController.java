package com.application.paymentmidtransservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Tag(name = "Disbursement Payments")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/disbursement")
public class DisbursementPaymentController {
}
