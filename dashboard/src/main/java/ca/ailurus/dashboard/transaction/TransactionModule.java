package ca.ailurus.dashboard.transaction;

import com.google.inject.AbstractModule;

public class TransactionModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransactionMaker.class).to(DbTransactionMaker.class);
    }
}
