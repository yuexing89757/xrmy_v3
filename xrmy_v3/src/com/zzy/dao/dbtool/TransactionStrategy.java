package com.zzy.dao.dbtool;

public abstract interface TransactionStrategy
{
  public abstract void open(boolean paramBoolean);

  public abstract void begin();

  public abstract void commit();

  public abstract void rollback();

  public abstract void close();

}