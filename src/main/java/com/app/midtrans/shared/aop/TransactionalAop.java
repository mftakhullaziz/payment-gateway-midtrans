package com.app.midtrans.shared.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Aspect
@Component
public class TransactionalAop {

  @Pointcut("@annotation(transactional)")
  public void transactionalMethods(Transactional transactional) {}

  @Before(value = "transactionalMethods(transactional)", argNames = "joinPoint,transactional")
  public void transactionalExecutor(JoinPoint joinPoint, Transactional transactional) {
    String transactionName = buildTransactionName(joinPoint);

    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

        @Override
        public void beforeCommit(boolean readOnly) {
          log.info("[transactionalExecutor] Committing transaction: {}", transactionName);
        }

        @Override
        public void afterCompletion(int status) {
          String statusText = switch (status) {
            case STATUS_COMMITTED -> "COMMITTED";
            case STATUS_ROLLED_BACK -> "ROLLED_BACK";
            case STATUS_UNKNOWN -> "UNKNOWN";
            default -> "OTHER";
          };

          log.info("[transactionalExecutor] Transaction '{}' completed with status: {}", transactionName, statusText);
          log.info("[transactionalExecutor] Finished transaction: {}", transactionName); // <- Final transaction finish log
        }
      });
    }

    log.info("[transactionalExecutor] Started transaction: {}", transactionName);
  }

  private String buildTransactionName(JoinPoint joinPoint) {
    return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
  }
}