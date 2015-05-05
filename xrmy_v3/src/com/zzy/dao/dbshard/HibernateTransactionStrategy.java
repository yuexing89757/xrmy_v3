package com.zzy.dao.dbshard;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.zzy.dao.dbtool.TransactionStrategy;
import com.zzy.util.Log;

public class HibernateTransactionStrategy implements TransactionStrategy {
	private static final Log log = Log.getLogger(HibernateTransactionStrategy.class);

	private static final ThreadLocal<HibernateTransactionStrategy> instance = new ThreadLocal<HibernateTransactionStrategy>();

	private Calendar transactionTimer = null;
	private Map<Long, Transaction> mapTransaction = new HashMap<Long, Transaction>();

	public static TransactionStrategy getInstance() {
		if (instance.get() == null) {
			instance.set(new HibernateTransactionStrategy());
		}
		return (TransactionStrategy) instance.get();
	}

	public static void clearCache() {
		Session s = HibernateManagerShard.getCurrentSession();
		if (s != null) {
			s.flush();
			s.clear();
		}
	}

	public void beginByAdvertiserId(Long advertiserId) {
		try {
			Transaction transaction = null;
			if (this.mapTransaction.containsKey(advertiserId)) {
				transaction = this.mapTransaction.get(advertiserId);
				if (transaction.isActive()) {
					return;
				}
			}

			Session session=HibernateManagerShard.getCurrentSessionByAdvertiserId(advertiserId);
			transaction=session.beginTransaction();
			log.debug("beginByAdvertiserId transaction:"+transaction);
			if (advertiserId == null) {
				advertiserId = HibernateManagerShard.getFirstKeyFromLocalSession();
				log.debug("advertiserId is null when beginByAdvertiserId");
			}
			this.mapTransaction.put(advertiserId, transaction);

			this.transactionTimer = Calendar.getInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("[{0}] Transaction begin.", new Object[] { Thread.currentThread().getName() });
	}

	public void begin() {
		this.beginByAdvertiserId(null);
	}

	public void commitByAdvertiserId(Long advertiserId) {
			long ts = Calendar.getInstance().getTimeInMillis() - this.transactionTimer.getTimeInMillis();
			if (this.mapTransaction.containsKey(advertiserId)&&this.mapTransaction.get(advertiserId).isActive()) {
				this.mapTransaction.get(advertiserId).commit();
			}
			HibernateManagerShard.getCurrentSessionByAdvertiserId(advertiserId).clear();
			log.info("[{0}] Transaction commit. [{1}ms]", new Object[] { Thread.currentThread().getName(),Long.valueOf(ts) });
	}


	public void commit() {
		this.commitByAdvertiserId(null);
	}

	public void rollbackByAdvertiserId(Long advertiserId) {
		if (this.mapTransaction.containsKey(advertiserId) && this.mapTransaction.get(advertiserId).isActive()) {
			log.debug("the rollByadvertiserId start and advertiserId is: {0}",advertiserId);
			this.mapTransaction.get(advertiserId).rollback();
		}

		HibernateManagerShard.getCurrentSessionByAdvertiserId(advertiserId).clear();

	}

	public void rollback() {
		this.rollbackByAdvertiserId(null);
	}

	public void mergeBegin() {
		this.mergeBeginByAdvertiserId(null);
	}

	public void mergeBeginByAdvertiserId(Long advertiserId) {
		try {
			Transaction transaction = null;
			if (this.mapTransaction.containsKey(advertiserId)) {
				transaction = this.mapTransaction.get(advertiserId);
			}

			transaction = HibernateManagerShard.getCurrentSessionByAdvertiserId(advertiserId).beginTransaction();
			this.mapTransaction.put(advertiserId, transaction);

			this.transactionTimer = Calendar.getInstance();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("[{0}] Transaction begin.", new Object[] { Thread.currentThread().getName() });
	}


	public void mergeCommit() {
		this.mergeCommitByAdvertiserId(null);
	}


	public void mergeCommitByAdvertiserId(Long advertiserId) {
		if (this.mapTransaction.containsKey(advertiserId) && this.mapTransaction.get(advertiserId).isActive()) {
			HibernateManagerShard.getCurrentSessionByAdvertiserId(advertiserId).flush();
			this.mapTransaction.get(advertiserId).commit();
		}
		HibernateManagerShard.getCurrentSessionByAdvertiserId(advertiserId).clear();
	}

	public void mergeRollback() {
		this.mergeCommitByAdvertiserId(null);
	}

	public void mergeRollbackByAdvertiserId(Long advertiserId) {
		if (this.mapTransaction.containsKey(advertiserId) && this.mapTransaction.get(advertiserId).isActive()) {
			this.mapTransaction.get(advertiserId).rollback();
		}
	}

	@Deprecated
	public void close() {
	}

	@Deprecated
	public void open(boolean stateful) {
		if (!stateful)
			throw new IllegalArgumentException("Do not support stateless session");
	}



}