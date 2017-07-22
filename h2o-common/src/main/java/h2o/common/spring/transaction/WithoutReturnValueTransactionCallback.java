package h2o.common.spring.transaction;

import org.springframework.transaction.TransactionStatus;

public interface WithoutReturnValueTransactionCallback {

    void doInTransaction(TransactionStatus status);

}
