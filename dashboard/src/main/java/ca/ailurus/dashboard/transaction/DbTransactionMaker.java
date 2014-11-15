package ca.ailurus.dashboard.transaction;

import com.google.inject.Singleton;
import org.mapdb.DBMaker;
import org.mapdb.TxMaker;

@Singleton
public class DbTransactionMaker implements TransactionMaker {
    private TxMaker txMaker;

    public DbTransactionMaker() {
        txMaker = DBMaker.newMemoryDB()
                         .closeOnJvmShutdown()
                         .makeTxMaker();
    }

    @Override
    public Transaction make() {
        return new Transaction(txMaker.makeTx());
    }
}
