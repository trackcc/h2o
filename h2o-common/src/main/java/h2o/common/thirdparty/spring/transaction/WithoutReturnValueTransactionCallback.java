package h2o.common.thirdparty.spring.transaction;

import org.springframework.transaction.TransactionStatus;

public interface WithoutReturnValueTransactionCallback {

    void doInTransaction(TransactionStatus status);

}
