package application.core.processmeasureparticipant.dao.implementation;

import application.core.processmeasureparticipant.dao.ProcessMeasureParticipantDao;
import application.model.ProcessMeasureId;
import application.model.ProcessMeasureParticipant;
import application.model.ProcessMeasureParticipantId;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.query.Query;


import java.util.List;

/**
 * Created by alexiaborchgrevink on 5/17/18.
 */
@Service
public class ProcessMeasureParticipantDaoImpl implements ProcessMeasureParticipantDao {

    // We are gonna use a session-per-request pattern, for each dta access object (dao).
    // In order to use it, we need an session factory, that will provide us with sessions for each request.
    @Autowired
    private SessionFactory factory;

    @Override
    public ProcessMeasureParticipant findById(ProcessMeasureParticipantId id) {
        // Almost always use getCurrentSession, instead of openSession.
        // Only in very odd cases, should the latter be used.
        // For more information, read the following docs.
        // http://docs.jboss.org/hibernate/core/3.3/reference/en/html/transactions.html#transactions-basics-uow
        Session session = factory.getCurrentSession();

        // Try to retrieve date from the DB. If we fail, then we return null.
        try {
            return session.get(ProcessMeasureParticipant.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProcessMeasureParticipant> getAllProcessMeasureParticipants() {
        // Almost always use getCurrentSession, instead of openSession.
        // Only in very odd cases, should the latter be used.
        // For more information, read the following docs.
        // http://docs.jboss.org/hibernate/core/3.3/reference/en/html/transactions.html#transactions-basics-uow
        Session session = factory.getCurrentSession();

        // Try to retrieve date from the DB. If we fail, then we return null.
        try {
            return session.createQuery("from ProcessMeasureParticipant join fetch ProcessMeasure PM where PM.processMeasureId = ").list();
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public void insert(ProcessMeasureParticipant processMeasureParticipant) throws HibernateException {
        this.factory.getCurrentSession().persist(processMeasureParticipant);
    }

    @Override
    public void delete(ProcessMeasureParticipant processMeasureParticipant) throws HibernateException {
        this.factory.getCurrentSession().delete(processMeasureParticipant);
    }

    @Override
    public List<ProcessMeasureParticipant> getParticipantByProcessMeasure(ProcessMeasureId processMeasureId) {
        // Almost always use getCurrentSession, instead of openSession.
        // Only in very odd cases, should the latter be used.
        // For more information, read the following docs.
        // http://docs.jboss.org/hibernate/core/3.3/reference/en/html/transactions.html#transactions-basics-uow
        Session session = factory.getCurrentSession();

        // Try to retrieve date from the DB. If we fail, then we return null.
        try {
            Query query = session.createQuery("from ProcessMeasureParticipant PM join fetch PM.processMeasure join fetch PM.user where PM.processMeasure.processMeasureId =:processMeasureId");
            query.setParameter("processMeasureId", processMeasureId);
            return query.list();

        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public Integer countParticipantsByProcessMeasure(ProcessMeasureId processMeasureId) {
        Session session = factory.getCurrentSession();
        try {
            Query query = session.createQuery("select count(*) from ProcessMeasureParticipant");
            return ((Long) query.iterate().next()).intValue();
        } catch(Exception e) {
            return null;
        }
    }
}
