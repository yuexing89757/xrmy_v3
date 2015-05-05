package com.zzy.dao.dbtool;


public class HibernateTransactionStrategy  implements TransactionStrategy
{

  private static final ThreadLocal<TransactionStrategy> instance = new ThreadLocal();

  private org.hibernate.Transaction transaction = null;

  public static TransactionStrategy getInstance() {
    if (instance.get() == null) {
          instance.set(new HibernateTransactionStrategy());
    }
    return (TransactionStrategy)instance.get();
  }

 

  public void begin()
  {
    try {
      this.transaction = 
        HibernateManager.getCurrentSession().beginTransaction();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void commit()
  {
    if (this.transaction != null) {
       this.transaction.commit();
       HibernateManager.getCurrentSession().clear();
    }
  }

  public void rollback()
  {
    if ((this.transaction != null) && (this.transaction.isActive())) {
      this.transaction.rollback();
      HibernateManager.getCurrentSession().clear();
    }
  }

 
  @Deprecated
  public void close()
  {
  }

  @Deprecated
  public void open(boolean stateful)
  {
    if (!stateful)
      throw new IllegalArgumentException(
        "Do not support stateless session");
  }
}