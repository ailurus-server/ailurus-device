package ca.ailurus.dashboard.mock;

import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import org.mapdb.DBMaker;
import org.mapdb.TxMaker;

public class MemTransactionMaker implements TransactionMaker {
    private TxMaker txMaker;

    public MemTransactionMaker() {
        txMaker = DBMaker.newMemoryDB()
                .closeOnJvmShutdown()
                .makeTxMaker();
    }

    @Override
    public Transaction make() {
        return new Transaction(txMaker.makeTx());
    }
}